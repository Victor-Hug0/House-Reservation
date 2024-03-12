package com.victor.Airbnb.controller;

import com.victor.Airbnb.assembler.HouseModelAssembler;
import com.victor.Airbnb.dto.HouseRequestDTO;
import com.victor.Airbnb.entity.Address;
import com.victor.Airbnb.entity.House;
import com.victor.Airbnb.enuns.PropertyType;
import com.victor.Airbnb.exception.HouseNotFountException;
import com.victor.Airbnb.repository.AddressRepository;
import com.victor.Airbnb.repository.HouseRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/houses")
public class HouseController {

    private final HouseRepository houseRepository;
    private final AddressRepository addressRepository;
    private final HouseModelAssembler assembler;

    public HouseController(HouseRepository houseRepository, AddressRepository addressRepository, HouseModelAssembler assembler) {
        this.houseRepository = houseRepository;
        this.assembler = assembler;
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public CollectionModel<EntityModel<House>> all(){
        List<EntityModel<House>> houses = houseRepository.findAll().stream()
                .map(assembler::toModel).toList();

        return CollectionModel.of(houses,
                linkTo(methodOn(HouseController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<House> one(@PathVariable Long id){
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new HouseNotFountException(id));

        return assembler.toModel(house);
    }

    @PostMapping
    public ResponseEntity<?> newHouse(@RequestBody HouseRequestDTO houseRequestDTO){
        Address address = houseRequestDTO.address();
        System.out.println(address);
        addressRepository.save(address);

        House house = new House();
        house.setTitle(houseRequestDTO.title());
        house.setDescription(houseRequestDTO.description());
        house.setImages(houseRequestDTO.images());
        house.setPropertyType(PropertyType.valueOf(houseRequestDTO.propertyType()));
        house.setCapacity(houseRequestDTO.capacity());
        house.setBedroomsNumber(houseRequestDTO.bedroomsNumber());
        house.setBedsNumber(houseRequestDTO.bedsNumber());
        house.setBathroomsNumber(houseRequestDTO.bathroomsNumber());
        house.setValueNight(houseRequestDTO.valueNight());
        house.setWifi(houseRequestDTO.wifi());
        house.setKitchen(houseRequestDTO.kitchen());
        house.setAirConditioning(houseRequestDTO.airConditioning());
        house.setTv(houseRequestDTO.tv());
        house.setPool(houseRequestDTO.pool());
        house.setJacuzzi(houseRequestDTO.jacuzzi());
        house.setAddress(address);
        house.setGym(houseRequestDTO.gym());
        house.setCradle(houseRequestDTO.cradle());
        house.setKingBed(houseRequestDTO.kingBed());
        house.setGrill(houseRequestDTO.grill());
        house.setEletricCarCharger(houseRequestDTO.eletricCarCharger());
        house.setWashingMachine(houseRequestDTO.washingMachine());
        house.setSeaSide(houseRequestDTO.seaSide());

        House savedHouse = houseRepository.save(house);

        EntityModel<House> houseEntityModel = assembler.toModel(savedHouse);

        return ResponseEntity.created(houseEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(houseEntityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHouse(@PathVariable Long id, @RequestBody House newHouse){
        House updatedHouse = houseRepository.findById(id)
                .map(house -> {
                    house.setTitle(newHouse.getTitle());
                    house.setDescription(newHouse.getDescription());
                    house.setImages(newHouse.getImages());
                    house.setPropertyType(newHouse.getPropertyType());
                    house.setCapacity(newHouse.getCapacity());
                    house.setBedroomsNumber(newHouse.getBedroomsNumber());
                    house.setBedsNumber(newHouse.getBedsNumber());
                    house.setBathroomsNumber(newHouse.getBathroomsNumber());
                    house.setValueNight(newHouse.getValueNight());
                    house.setWifi(newHouse.getWifi());
                    house.setKitchen(newHouse.getKitchen());
                    house.setAirConditioning(newHouse.getAirConditioning());
                    house.setTv(newHouse.getTv());
                    house.setPool(newHouse.getPool());
                    house.setJacuzzi(newHouse.getJacuzzi());
                    house.setAddress(newHouse.getAddress());
                    house.setReserveList(newHouse.getReserveList());
                    house.setGym(newHouse.getGym());
                    house.setCradle(newHouse.getCradle());
                    house.setKingBed(newHouse.getKingBed());
                    house.setGrill(newHouse.getGrill());
                    house.setEletricCarCharger(newHouse.getEletricCarCharger());
                    house.setWashingMachine(newHouse.getWashingMachine());
                    house.setSeaSide(newHouse.getSeaSide());
                    return houseRepository.save(house);
                })
                .orElseGet(() -> {
                    newHouse.setId(id);
                    return houseRepository.save(newHouse);
                });

        EntityModel<House> houseEntityModel = assembler.toModel(updatedHouse);

        return ResponseEntity.created(houseEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(houseEntityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHouse(@PathVariable Long id){
        houseRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
