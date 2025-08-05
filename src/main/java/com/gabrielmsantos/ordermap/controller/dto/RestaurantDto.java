package com.gabrielmsantos.ordermap.controller.dto;

import com.gabrielmsantos.ordermap.domain.model.Restaurant;

public record RestaurantDto(Long id, String name, String latitude, String longitude, int active) {

    public RestaurantDto(Restaurant model) {
        this(model.getId(), model.getName(), model.getLatitude(), model.getLongitude(), model.getActive());
    }

    public Restaurant toModel() {
        Restaurant model = new Restaurant();
        model.setId(this.id);
        model.setName(this.name);
        model.setLatitude(this.latitude);
        model.setLongitude(this.longitude);
        model.setActive(this.active);
        return model;
    }
}
