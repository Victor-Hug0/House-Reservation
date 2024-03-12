package com.victor.Airbnb.entity;

import com.victor.Airbnb.enuns.PropertyType;
import com.victor.Airbnb.enuns.StatusReserve;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "houses")
@Table(name = "houses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class House {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ElementCollection
    private List<String> images;
    private PropertyType propertyType;
    private Integer capacity;
    private Integer bedroomsNumber;
    private Integer bedsNumber;
    private Integer bathroomsNumber;
    private Double valueNight;
    private Boolean wifi;
    private Boolean kitchen;
    private Boolean airConditioning;
    private Boolean tv;
    private Boolean pool;
    private Boolean jacuzzi;
    private Boolean gym;
    private Boolean cradle;
    private Boolean kingBed;
    private Boolean grill;
    private Boolean eletricCarCharger;
    private Boolean washingMachine;
    private Boolean seaSide;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany
    private List<Reserve> reserveList;

    public boolean isReservationConflict(LocalDate checkInDay, LocalDate checkOutDay) {
        if (reserveList != null) {
            for (Reserve existingReserve : reserveList) {
                if (checkInDay.isBefore(existingReserve.getCheckOutDay()) &&
                        checkOutDay.isAfter(existingReserve.getCheckInDay())) {
                    return true;
                }
            }
        }
        return false;
    }
}
