package com.northcoders.recordshop.repository;

import com.northcoders.recordshop.model.Album;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.*;

@Repository
public interface AlbumManagerRepository extends CrudRepository<Album, Long> , AlbumManagerRepositoryInterface {

    @Override
    default List<Album> findAllByInStock(){
        Iterable<Album> albums = findAll();

        List<Album> inStock = new ArrayList<>();

        albums.forEach(s -> {
            //null check as it's very easy to add an album without any stock!
            if(s.getStock() != null && s.getStock().getQuantity() > 0){
                inStock.add(s);
            }
        });
        return inStock;
    }

}
