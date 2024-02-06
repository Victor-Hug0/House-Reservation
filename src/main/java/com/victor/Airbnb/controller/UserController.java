package com.victor.Airbnb.controller;

import com.victor.Airbnb.assembler.UserModelAssembler;
import com.victor.Airbnb.entity.User;
import com.victor.Airbnb.exception.UserNotFoundException;
import com.victor.Airbnb.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserModelAssembler assembler;

    public UserController(UserRepository userRepository, UserModelAssembler assembler) {
        this.userRepository = userRepository;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(assembler::toModel).toList();

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<User> one(@PathVariable UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toModel(user);
    }

    @PostMapping
    public ResponseEntity<?> newUser(@RequestBody User user){
        if(this.userRepository.findByEmail(user.getEmail()) != null) return ResponseEntity.badRequest().build();

        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);

        EntityModel<User> userEntityModel = assembler.toModel(userRepository.save(user));

        return ResponseEntity.created(userEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userEntityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable UUID id){
        User updatedUser = userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setName(newUser.getFirstName() + " " + newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                    String encodedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
                    user.setPassword(encodedPassword);
                    user.setPhoneNumber(newUser.getPhoneNumber());
                    user.setAge(newUser.getAge());
                    user.setSexType(newUser.getSexType());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });

        EntityModel<User> userEntityModel = assembler.toModel(updatedUser);

        return ResponseEntity.created(userEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userEntityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id){
        userRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
