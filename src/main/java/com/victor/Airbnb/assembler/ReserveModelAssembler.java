package com.victor.Airbnb.assembler;


import com.victor.Airbnb.controller.ReserveController;
import com.victor.Airbnb.entity.Reserve;
import com.victor.Airbnb.entity.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReserveModelAssembler implements RepresentationModelAssembler<Reserve, EntityModel<Reserve>> {
    @Override
    public EntityModel<Reserve> toModel(Reserve reserve) {
        return EntityModel.of(reserve,
                linkTo(methodOn(ReserveController.class).one(reserve.getId())).withSelfRel(),
                linkTo(methodOn(ReserveController.class).all()).withRel("reserves"));
    }
}
