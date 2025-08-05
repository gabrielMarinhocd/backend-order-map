package com.gabrielmsantos.ordermap.service.impl;

import com.gabrielmsantos.ordermap.domain.model.Client;
import com.gabrielmsantos.ordermap.domain.repository.ClientRepository;
import com.gabrielmsantos.ordermap.service.ClientService;
import com.gabrielmsantos.ordermap.service.exception.BusinessException;
import com.gabrielmsantos.ordermap.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public List<Client> findAll(){
        return this.clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return this.clientRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Client create(Client ClientToCreate) {
        ofNullable(ClientToCreate).orElseThrow(() -> new BusinessException("Client to create must not be null."));

        return this.clientRepository.save(ClientToCreate);
    }

    @Transactional
    public Client update(Long id, Client ClientToUpdate) {
        Client dbClient = this.findById(id);
        if (!dbClient.getId().equals(ClientToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbClient.setName(ClientToUpdate.getName());
        dbClient.setLatitude(ClientToUpdate.getLatitude());
        dbClient.setLongitude(ClientToUpdate.getLongitude());
        dbClient.setActive(ClientToUpdate.getActive());

        return this.clientRepository.save(dbClient);
    }

    @Transactional
    public void delete(Long id) {
        Client dbClient = this.findById(id);
        this.clientRepository.delete(dbClient);
    }
}
