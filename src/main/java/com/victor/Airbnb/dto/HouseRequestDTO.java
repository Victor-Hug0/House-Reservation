package com.victor.Airbnb.dto;

import com.victor.Airbnb.entity.Address;
import com.victor.Airbnb.enuns.PropertyType;
import lombok.Data;

import java.util.List;


public record HouseRequestDTO(
        String title,
        String description,
        List<String> images,
        String propertyType,
        int capacity,
        int bedroomsNumber,
        int bedsNumber,
        int bathroomsNumber,
        double valueNight,
        boolean wifi,
        boolean kitchen,
        boolean airConditioning,
        boolean tv,
        boolean pool,
        boolean jacuzzi,
        Address address
) {
}
