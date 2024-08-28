package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Seller extends BaseModel{
    private String name;
    private String email;
    @ManyToOne
    private Address address;
}
