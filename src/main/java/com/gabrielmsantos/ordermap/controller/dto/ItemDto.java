package com.gabrielmsantos.ordermap.controller.dto;

import com.gabrielmsantos.ordermap.domain.model.Item;

public record ItemDto(Long id, String name, String latitude, String longitude, int active) {

    public ItemDto(Item model) {
        this(model.getId(), model.getName(), model.getLatitude(), model.getLongitude(), model.getActive());
    }

    public Item toModel() {
        Item model = new Item();
        model.setId(this.id);
        model.setName(this.name);
        model.setLatitude(this.latitude);
        model.setLongitude(this.longitude);
        model.setActive(this.active);
        return model;
    }
}
