package com.northcoders.recordshop.repository;

import com.northcoders.recordshop.model.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumManagerRepositoryInterface  {

    List<Album> findAllByInStock();
}
