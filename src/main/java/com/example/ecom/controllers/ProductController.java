package com.example.ecom.controllers;

import com.example.ecom.dtos.DeliveryEstimateRequestDto;
import com.example.ecom.dtos.DeliveryEstimateResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.exceptions.AddressNotFoundException;
import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.services.ProductService;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public DeliveryEstimateResponseDto estimateDeliveryTime(DeliveryEstimateRequestDto requestDto){
        DeliveryEstimateResponseDto responseDto = new DeliveryEstimateResponseDto();
        try {
            responseDto.setExpectedDeliveryDate(productService.estimateDeliveryDate(requestDto.getProductId(), requestDto.getAddressId()));
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (ProductNotFoundException | AddressNotFoundException e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
