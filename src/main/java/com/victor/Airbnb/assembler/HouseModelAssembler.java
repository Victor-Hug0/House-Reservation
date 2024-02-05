package com.victor.Airbnb.assembler;

import com.victor.Airbnb.controller.HouseController;
import com.victor.Airbnb.controller.UserController;
import com.victor.Airbnb.entity.House;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HouseModelAssembler implements RepresentationModelAssembler<House, EntityModel<House>> {
    @Override
    public EntityModel<House> toModel(House house) {
        return EntityModel.of(house,
                linkTo(methodOn(HouseController.class).one(house.getId())).withSelfRel(),
                linkTo(methodOn(HouseController.class).all()).withRel("houses"));
    }
}
