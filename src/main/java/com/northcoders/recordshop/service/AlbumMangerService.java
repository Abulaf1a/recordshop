package com.northcoders.recordshop.service;

import com.northcoders.recordshop.model.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumMangerService {

    public List<Album> getAllAlbums();

    public Album postAlbum(Album album);

    Optional<Album> getAlbumById(Long id);
}
