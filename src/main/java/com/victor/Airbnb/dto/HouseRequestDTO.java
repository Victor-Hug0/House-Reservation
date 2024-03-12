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
        Integer capacity,
        Integer bedroomsNumber,
        Integer bedsNumber,
        Integer bathroomsNumber,
        Double valueNight,
        Boolean wifi,
        Boolean kitchen,
        Boolean airConditioning,
        Boolean tv,
        Boolean pool,
        Boolean jacuzzi,
        Boolean gym,
        Boolean cradle,
        Boolean kingBed,
        Boolean grill,
        Boolean eletricCarCharger,
        Boolean washingMachine,
        Boolean seaSide,

        Address address
) {
}
