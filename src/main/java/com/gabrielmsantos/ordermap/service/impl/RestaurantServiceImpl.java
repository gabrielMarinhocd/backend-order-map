package com.gabrielmsantos.ordermap.service.impl;

import com.gabrielmsantos.ordermap.domain.model.Restaurant;
import com.gabrielmsantos.ordermap.domain.repository.RestaurantRepository;
import com.gabrielmsantos.ordermap.service.RestaurantService;
import com.gabrielmsantos.ordermap.service.exception.BusinessException;
import com.gabrielmsantos.ordermap.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional(readOnly = true)
    public List<Restaurant> findAll(){
        return this.restaurantRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Restaurant findById(Long id) {
        return this.restaurantRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Restaurant create(Restaurant RestaurantToCreate) {
        ofNullable(RestaurantToCreate).orElseThrow(() -> new BusinessException("Restaurant to create must not be null."));

        return this.restaurantRepository.save(RestaurantToCreate);
    }

    @Transactional
    public Restaurant update(Long id, Restaurant RestaurantToUpdate) {
        Restaurant dbRestaurant = this.findById(id);
        if (!dbRestaurant.getId().equals(RestaurantToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbRestaurant.setName(RestaurantToUpdate.getName());
        dbRestaurant.setLatitude(RestaurantToUpdate.getLatitude());
        dbRestaurant.setLongitude(RestaurantToUpdate.getLongitude());
        dbRestaurant.setActive(RestaurantToUpdate.getActive());

        return this.restaurantRepository.save(dbRestaurant);
    }

    @Transactional
    public void delete(Long id) {
        Restaurant dbRestaurant = this.findById(id);
        this.restaurantRepository.delete(dbRestaurant);
    }
    
}
