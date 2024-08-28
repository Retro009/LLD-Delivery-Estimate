package com.example.ecom.services;

import com.example.ecom.exceptions.AddressNotFoundException;
import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.libraries.MapsApi;
import com.example.ecom.models.Address;
import com.example.ecom.models.DeliveryHub;
import com.example.ecom.models.Product;
import com.example.ecom.models.Seller;
import com.example.ecom.repositories.AddressRepository;
import com.example.ecom.repositories.DeliveryHubRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private DeliveryHubRepository deliveryHubRepository;
    @Autowired
    private MapsApi mapsApi;
    @Override
    public Date estimateDeliveryDate(int productId, int addressId) throws ProductNotFoundException, AddressNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
        Address destination = addressRepository.findById(addressId).orElseThrow(()-> new AddressNotFoundException("Address Not Found"));

        Seller seller = product.getSeller();
        Address source = seller.getAddress();

        DeliveryHub deliveryHub = deliveryHubRepository.findByAddress_ZipCode(source.getZipCode()).orElseThrow(()-> new AddressNotFoundException("Delivery Hub Not Found"));
        Address deliveryHubAddress = deliveryHub.getAddress();

        int srcToHubTime = mapsApi.distanceBetween(source,deliveryHubAddress);
        int hubToDestTime = mapsApi.distanceBetween(deliveryHubAddress,destination);

        long sourceToDeliveryHubTime = (long)srcToHubTime*1000;
        long deliveryHubtoDestinationTime = (long)hubToDestTime*1000;

        long totalRequiredTime = sourceToDeliveryHubTime+deliveryHubtoDestinationTime+System.currentTimeMillis();

        return new Date(totalRequiredTime);
    }
}
