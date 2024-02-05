package com.victor.Airbnb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "adress")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id @GeneratedValue
    private Long id;
    private String zipCode;
    private String state;
    private String city;
    private String street;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    private String number;
}
