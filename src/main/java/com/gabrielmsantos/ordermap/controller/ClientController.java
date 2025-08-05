package com.gabrielmsantos.ordermap.controller;

import com.gabrielmsantos.ordermap.controller.dto.ClientDto;
import com.gabrielmsantos.ordermap.service.ClientService;
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
@RequestMapping("/clients")
@Tag(name = "Client Controller", description = "RESTFULL API for managing Client.")
public record ClientController(ClientService clientService) {
    
    @GetMapping
    @Operation(summary = "Get all Clients", description = "Retrieve a list of all registered Clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<ClientDto>> findAll() {
        var clients = clientService.findAll();
        var clientsDto = clients.stream().map(ClientDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(clientsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Client by ID", description = "Retrieve a specific Client based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientDto> findById(@PathVariable Long id) {
        var client = clientService.findById(id);
        return ResponseEntity.ok(new ClientDto(client));
    }

    @PostMapping
    @Operation(summary = "Create a new Client", description = "Create a new Client and return the created Client's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid Client data provided")
    })
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto ClientDto) {
        var Client = clientService.create(ClientDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(Client.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ClientDto(Client));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Client", description = "Update the data of an existing Client based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "422", description = "Invalid Client data provided")
    })
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto ClientDto) {
        var client = clientService.update(id, ClientDto.toModel());
        return ResponseEntity.ok(new ClientDto(client));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Client", description = "Delete an existing Client based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
