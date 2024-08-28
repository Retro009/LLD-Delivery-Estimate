package com.example.ecom.libraries;

import com.example.ecom.models.Address;

public interface MapsApi {
    int distanceBetween(Address source, Address destination);
}
