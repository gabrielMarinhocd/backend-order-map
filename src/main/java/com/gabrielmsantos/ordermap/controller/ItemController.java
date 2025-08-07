package com.gabrielmsantos.ordermap.controller;

import com.gabrielmsantos.ordermap.controller.dto.ItemDto;
import com.gabrielmsantos.ordermap.service.ItemService;
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
@RequestMapping("/items")
@Tag(name = "Item Controller", description = "RESTFULL API for managing Item.")
public record ItemController(ItemService itemService) {
    
    @GetMapping
    @Operation(summary = "Get all Items", description = "Retrieve a list of all registered Items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<ItemDto>> findAll() {
        var items = itemService.findAll();
        var itemsDto = items.stream().map(ItemDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(itemsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Item by ID", description = "Retrieve a specific Item based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<ItemDto> findById(@PathVariable Long id) {
        var item = itemService.findById(id);
        return ResponseEntity.ok(new ItemDto(item));
    }

    @PostMapping
    @Operation(summary = "Create a new Item", description = "Create a new Item and return the created Item's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid Item data provided")
    })
    public ResponseEntity<ItemDto> create(@RequestBody ItemDto ItemDto) {
        var Item = itemService.create(ItemDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(Item.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ItemDto(Item));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Item", description = "Update the data of an existing Item based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "422", description = "Invalid Item data provided")
    })
    public ResponseEntity<ItemDto> update(@PathVariable Long id, @RequestBody ItemDto ItemDto) {
        var item = itemService.update(id, ItemDto.toModel());
        return ResponseEntity.ok(new ItemDto(item));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Item", description = "Delete an existing Item based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
