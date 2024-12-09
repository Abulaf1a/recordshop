package com.northcoders.recordshop.repository;

import com.northcoders.recordshop.model.Album;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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

    @Override
    @Cacheable("Albums")
    default Optional<Album> findById(Long id){

            try {
                long time = 5000L;
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }

            Iterable<Album> albums = findAll();

            Optional<Album> retAlbum = Optional.empty();

            for(Album album: albums){
                if(Objects.equals(album.getId(), id)){
                    retAlbum = Optional.of(album);
                }
            }

            return retAlbum;
    }

}
