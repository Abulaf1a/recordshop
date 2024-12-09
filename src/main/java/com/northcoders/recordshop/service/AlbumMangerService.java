package com.northcoders.recordshop.service;

import com.northcoders.recordshop.model.Album;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface AlbumMangerService {

    List<Album> getAllAlbums();

    Album postAlbum(Album album);

    Optional<Album> getAlbumById(Long id, HttpServletRequest request);

    Album updateAlbumById(Album album, Long id);

    Boolean deleteAlbumById(Long id);

    List<Album> getAllAlbumsInStock();
}
