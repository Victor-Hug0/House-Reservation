package com.victor.Airbnb.controller;

import com.victor.Airbnb.assembler.ReserveModelAssembler;
import com.victor.Airbnb.dto.ReserveRequestDTO;
import com.victor.Airbnb.entity.House;
import com.victor.Airbnb.entity.Reserve;
import com.victor.Airbnb.entity.User;
import com.victor.Airbnb.exception.HouseNotFountException;
import com.victor.Airbnb.exception.ReserveNotFoundException;
import com.victor.Airbnb.exception.UserNotFoundException;
import com.victor.Airbnb.repository.HouseRepository;
import com.victor.Airbnb.repository.ReserveRepository;
import com.victor.Airbnb.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/reserves")
public class ReserveController {

    private final ReserveRepository reserveRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    private final ReserveModelAssembler assembler;

    public ReserveController(ReserveRepository reserveRepository, UserRepository userRepository, HouseRepository houseRepository, ReserveModelAssembler assembler) {
        this.reserveRepository = reserveRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
        this.assembler = assembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<Reserve>> all(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            // Trate o caso em que o usuário não está autenticado ou o principal não é UserDetails
            throw new AuthenticationException("Usuário não autenticado") {
            };
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        List<EntityModel<Reserve>> reserveList = reserveRepository.findByUserEmail(email)
                .stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(reserveList,
                linkTo(methodOn(ReserveController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Reserve> one(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            // Trate o caso em que o usuário não está autenticado ou o principal não é UserDetails
            throw new AuthenticationException("Usuário não autenticado") {
            };
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Reserve reserve = reserveRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ReserveNotFoundException(id));

        return assembler.toModel(reserve);
    }

    @PostMapping
    public ResponseEntity<?> newReserve(@RequestBody ReserveRequestDTO reserveRequestDTO){
        User user = userRepository.findById(reserveRequestDTO.user().getId())
                .orElseThrow(() -> new UserNotFoundException(reserveRequestDTO.user().getId()));

        House house = houseRepository.findById(reserveRequestDTO.house().getId())
                .orElseThrow(() -> new HouseNotFountException(reserveRequestDTO.house().getId()));

        LocalDate checkInDay = reserveRequestDTO.checkInDay();
        LocalDate checkOutDay = reserveRequestDTO.checkOutDay();

        if(house.isReservationConflict(checkInDay, checkOutDay)){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflito de reserva. A casa já está reservada para esse período.");
        }

        Reserve reserve = new Reserve();
        reserve.setHouse(house);
        reserve.setUser(user);
        reserve.setCheckInDay(checkInDay);
        reserve.setCheckOutDay(checkOutDay);
        reserve.setTotalValue(reserve.calculateTotalValue());

        EntityModel<Reserve> reserveEntityModel = assembler.toModel(reserveRepository.save(reserve));

        return ResponseEntity.created(reserveEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(reserveEntityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserve(@PathVariable Long id){
        reserveRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
