package com.victor.Airbnb.entity;

import com.victor.Airbnb.enuns.StatusReserve;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity(name = "reserves")
@Table(name = "reserves")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserve {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate checkInDay;
    private LocalDate checkOutDay;
    private Double totalValue;

    public Double calculateTotalValue(){
        if (checkInDay != null && checkOutDay != null && house != null && house.getValueNight() != null){
            Long days = ChronoUnit.DAYS.between(checkInDay, checkOutDay);
            return days * house.getValueNight();
        }

        return null;
    }
}
