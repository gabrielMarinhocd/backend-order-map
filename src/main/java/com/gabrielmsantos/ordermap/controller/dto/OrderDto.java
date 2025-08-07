package com.gabrielmsantos.ordermap.controller.dto;

import com.gabrielmsantos.ordermap.domain.model.Client;
import com.gabrielmsantos.ordermap.domain.model.Order;
import com.gabrielmsantos.ordermap.domain.model.Item;

import java.sql.Timestamp;

public record OrderDto (Long id, Timestamp dt, String description, String status, int active, Client client, Item item){

    public OrderDto(Order model) {
        this(model.getId(), model.getDt(), model.getDescription(), model.getStatus(), model.getActive(), model.getClient(), model.getItem());
    }

    public Order toModel() {
        Order model = new Order();
        model.setId(this.id);
        model.setDt(this.dt);
        model.setDescription(this.description);
        model.setStatus(this.status);
        model.setActive(this.active);
        model.setClient(this.client);
        model.setItem(this.item);
        return model;
    }

}
