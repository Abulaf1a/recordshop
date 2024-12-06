package com.northcoders.recordshop.service;

import com.northcoders.recordshop.model.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumMangerService {

    List<Album> getAllAlbums();

    Album postAlbum(Album album);

    Optional<Album> getAlbumById(Long id);

    Album updateAlbumById(Album album, Long id);

    Boolean deleteAlbumById(Long id);

    List<Album> getAllAlbumsInStock();
}
