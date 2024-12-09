package com.northcoders.recordshop.service;

import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.repository.AlbumManagerRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumMangerServiceImpl implements AlbumMangerService {

    @Autowired
    AlbumManagerRepository albumManagerRepository;

    //id,               optional album
    private HashMap<Long, Optional<Album>> cache = new HashMap<>();

    private HashMap<Long, Integer> cacheCounter = new HashMap<>();

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        albumManagerRepository.findAll().forEach(albums::add);
        return albums;
    }

    @Override
    public List<Album> getAllAlbumsInStock(){

        return albumManagerRepository.findAllByInStock();
    }

    @Override
    public Album postAlbum(Album album) {
        return albumManagerRepository.save(album);
    }

    @Override
    public Optional<Album> getAlbumById(Long id, HttpServletRequest request){
        //update cache counter key: 1, value: 3
        if(cacheCounter.containsKey(id))
            cacheCounter.replace(id, cacheCounter.get(id)+1); //add one every time.
        else cacheCounter.put(id, 1);

        if(cache.containsKey(id)){
            Optional<Album> albumOpt = cache.get(id);
            Album album = albumOpt.get();
            album.setArtist(album.getArtist() + " this was cached!");
            return Optional.of(album);
        }
        else{
            Optional<Album> album = albumManagerRepository.findById(id);
            if(cacheCounter.get(id) > 5) cache.put(id, album);
            return album;
        }
    }

    @Override
    public Album updateAlbumById(Album album, Long id){ //TODO ADD HTTP

        album.setId(id); //make sure that if you include an id in the album it is overriden by the path variable

        Optional<Album> currentOptional = getAlbumById(id, null); //TODO - FIX THIS, SO THAT WE CAN UPDATE THE CACHE WHEN WE UPDATE AN ALBUM
        if(currentOptional.isPresent()){
            Album current = currentOptional.get();
            if(album.getArtist() == null){
                album.setArtist(current.getArtist());
            }
            if(album.getTitle() == null){
                album.setTitle(current.getTitle());
            }
            if(album.getGenre() == null){
                album.setGenre(current.getGenre());
            }
            if(album.getReleaseYear() == 0){
                album.setReleaseYear(current.getReleaseYear());
            }
            if(album.getStock() == null){
                album.setStock(current.getStock());
            }
        }

        cache.remove(id); //removes the previous album from the cache.

        return albumManagerRepository.save(album);

    }

    public Boolean deleteAlbumById(Long id){
        if(albumManagerRepository.findById(id).isPresent()){
            albumManagerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
