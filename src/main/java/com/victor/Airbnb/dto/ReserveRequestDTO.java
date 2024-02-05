package com.victor.Airbnb.dto;

import com.victor.Airbnb.entity.House;
import com.victor.Airbnb.entity.User;
import com.victor.Airbnb.enuns.StatusReserve;

import java.time.LocalDate;

public record ReserveRequestDTO(House house, User user, LocalDate checkInDay, LocalDate checkOutDay,
                                StatusReserve statusReserve, Double totalValue) {
}
