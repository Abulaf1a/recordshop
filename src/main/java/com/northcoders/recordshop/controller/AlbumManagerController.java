package com.northcoders.recordshop.controller;

import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.service.AlbumMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/album")
public class AlbumManagerController {

    @Autowired
    AlbumMangerService albumMangerService;

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums(){
        return new ResponseEntity<>(albumMangerService.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable(value = "id") Optional<Long> idOptionak){

        if(idOptionak.isPresent()){
            Long idReal = idOptionak.get();
            Optional<Album> album = albumMangerService.getAlbumById(idReal);
            if(album.isPresent()) return new ResponseEntity<>(album.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Album(), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Album> postAlbum(@RequestBody Album album){
        return new ResponseEntity<>(albumMangerService.postAlbum(album), HttpStatus.CREATED);
    }
}
