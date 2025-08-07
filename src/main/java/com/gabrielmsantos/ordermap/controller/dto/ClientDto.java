package com.gabrielmsantos.ordermap.controller.dto;

import com.gabrielmsantos.ordermap.domain.model.Client;

public record ClientDto(Long id, String name, String latitude, String longitude, String url_icon, int active) {

    public ClientDto(Client model) {
        this(model.getId(), model.getName(), model.getLatitude(), model.getLongitude(), model.getUrl_icon(), model.getActive());
    }

    public Client toModel() {
       Client model = new Client();
       model.setId(this.id);
       model.setName(this.name);
       model.setLatitude(this.latitude);
       model.setLongitude(this.longitude);
       model.setUrl_icon(this.url_icon);
       model.setActive(this.active);
       return model;
    }
}
