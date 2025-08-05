package com.gabrielmsantos.ordermap.controller;

import com.gabrielmsantos.ordermap.controller.dto.RestaurantDto;
import com.gabrielmsantos.ordermap.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/restaurants")
@Tag(name = "Restaurant Controller", description = "RESTFULL API for managing Restaurant.")
public record RestaurantController(RestaurantService restaurantService) {
    
    @GetMapping
    @Operation(summary = "Get all Restaurants", description = "Retrieve a list of all registered Restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<RestaurantDto>> findAll() {
        var restaurants = restaurantService.findAll();
        var restaurantsDto = restaurants.stream().map(RestaurantDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(restaurantsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Restaurant by ID", description = "Retrieve a specific Restaurant based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<RestaurantDto> findById(@PathVariable Long id) {
        var restaurant = restaurantService.findById(id);
        return ResponseEntity.ok(new RestaurantDto(restaurant));
    }

    @PostMapping
    @Operation(summary = "Create a new Restaurant", description = "Create a new Restaurant and return the created Restaurant's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid Restaurant data provided")
    })
    public ResponseEntity<RestaurantDto> create(@RequestBody RestaurantDto RestaurantDto) {
        var Restaurant = restaurantService.create(RestaurantDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(Restaurant.getId())
                .toUri();
        return ResponseEntity.created(location).body(new RestaurantDto(Restaurant));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Restaurant", description = "Update the data of an existing Restaurant based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "422", description = "Invalid Restaurant data provided")
    })
    public ResponseEntity<RestaurantDto> update(@PathVariable Long id, @RequestBody RestaurantDto RestaurantDto) {
        var restaurant = restaurantService.update(id, RestaurantDto.toModel());
        return ResponseEntity.ok(new RestaurantDto(restaurant));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Restaurant", description = "Delete an existing Restaurant based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
