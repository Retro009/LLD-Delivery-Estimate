package com.example.ecom.adapters;

import com.example.ecom.libraries.GoogleMapsApi;
import com.example.ecom.libraries.MapsApi;
import com.example.ecom.libraries.models.GLocation;
import com.example.ecom.models.Address;
import org.springframework.stereotype.Component;

@Component
public class GoogleMapsApiAdapter implements MapsApi {
    @Override
    public int distanceBetween(Address source, Address destination) {
        GLocation src = new GLocation();
        src.setLatitude(source.getLatitude());
        src.setLongitude(source.getLongitude());

        GLocation dest = new GLocation();
        dest.setLatitude(destination.getLatitude());
        dest.setLongitude(destination.getLongitude());

        return new GoogleMapsApi().estimate(src,dest);
    }
}
