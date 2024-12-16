package com.northcoders.recordshop.service;

import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.DTO.AlbumStockDTO;
import com.northcoders.recordshop.repository.AlbumManagerRepository;
import com.northcoders.recordshop.repository.StockManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumMangerServiceImpl implements AlbumMangerService {

    @Autowired
    AlbumManagerRepository albumManagerRepository;

    @Autowired
    StockManagerRepository stockManagerRepository;

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
    public Optional<Album> getAlbumById(Long id){
        return albumManagerRepository.findById(id);
    }

    @Override
    public Album updateAlbumById(Album album, Long id){

        album.setId(id);

        Optional<Album> currentOptional = getAlbumById(id);
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
