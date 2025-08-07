package com.gabrielmsantos.ordermap.service.impl;

import com.gabrielmsantos.ordermap.domain.model.Item;
import com.gabrielmsantos.ordermap.domain.repository.ItemRepository;
import com.gabrielmsantos.ordermap.service.ItemService;
import com.gabrielmsantos.ordermap.service.exception.BusinessException;
import com.gabrielmsantos.ordermap.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    
    public ItemServiceImpl(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public List<Item> findAll(){
        return this.itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Item findById(Long id) {
        return this.itemRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Item create(Item itemToCreate) {
        ofNullable(itemToCreate).orElseThrow(() -> new BusinessException("Item to create must not be null."));

        return this.itemRepository.save(itemToCreate);
    }

    @Transactional
    public Item update(Long id, Item itemToUpdate) {
        Item dbItem = this.findById(id);
        if (!dbItem.getId().equals(itemToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbItem.setName(itemToUpdate.getName());
        dbItem.setLatitude(itemToUpdate.getLatitude());
        dbItem.setLongitude(itemToUpdate.getLongitude());
        dbItem.setUrl_icon(itemToUpdate.getUrl_icon());
        dbItem.setActive(itemToUpdate.getActive());

        return this.itemRepository.save(dbItem);
    }

    @Transactional
    public void delete(Long id) {
        Item dbItem = this.findById(id);
        this.itemRepository.delete(dbItem);
    }
    
}
