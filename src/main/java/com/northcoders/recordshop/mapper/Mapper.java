package com.northcoders.recordshop.mapper;

import com.northcoders.recordshop.DTO.AlbumStockDTO;
import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.model.Stock;
import com.northcoders.recordshop.repository.StockManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    @Autowired
    StockManagerRepository stockManagerRepository;

    public Album toAlbum(AlbumStockDTO albumStock){

        Stock stock = new Stock(albumStock.getStock(), null);

        Album album = Album.builder()
            .id(albumStock.getId())
                .title(albumStock.getTitle())
                .artist(albumStock.getArtist())
                .genre(albumStock.getGenre())
                .releaseYear(albumStock.getReleaseYear())
                .stock(stock)
                .build();

        stock.setAlbum(album);

        stockManagerRepository.save(stock);

        return album;
    }

    public AlbumStockDTO albumStockDTO(Album album){

        AlbumStockDTO albumStockDTO = AlbumStockDTO.builder()
                .id(album.getId())
                .title(album.getArtist())
                .artist(album.getArtist())
                .releaseYear(album.getReleaseYear())
                .genre(album.getGenre())
                .stock(album.getStock().getQuantity())
                .build();

        return albumStockDTO;
    }
}