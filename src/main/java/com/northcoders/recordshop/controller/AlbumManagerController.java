package com.northcoders.recordshop.controller;

import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.service.AlbumMangerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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

    @GetMapping("/in-stock")
    public ResponseEntity<List<Album>> getAllAlbumsInStock(){
        return new ResponseEntity<>(albumMangerService.getAllAlbumsInStock(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable(value = "id") Optional<Long> idOptional, HttpServletRequest request, HttpServletResponse response, HttpSession session){

        if(idOptional.isPresent()){
            Long idReal = idOptional.get();
            Optional<Album> album = albumMangerService.getAlbumById(idReal, request);
            if(album.isPresent()) return new ResponseEntity<>(album.get(), HttpStatus.OK);
        }

        //TODO: get the service layer to throw an exception,
        //spring will recognise this exception has been thrown then automaticlly sends a response
        //you have to create a ControllerAdvice layer -- google this!

        return new ResponseEntity<>(new Album(), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Album> postAlbum(@RequestBody Album album){
        return new ResponseEntity<>(albumMangerService.postAlbum(album), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable(value = "id") Optional<Long> idOptional, @RequestBody Album album){
        if(idOptional.isPresent()){
            Album updatedAlbum = albumMangerService.updateAlbumById(album, idOptional.get());
            return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Album(), HttpStatus.NOT_FOUND);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable(value = "id") Optional<Long> idOptional){
        if(idOptional.isPresent()){
            Boolean isDeleted = albumMangerService.deleteAlbumById(idOptional.get());
            if(isDeleted) return new ResponseEntity<>("Album with id %d has been deleted".formatted(idOptional.get()), HttpStatus.OK);
            return new ResponseEntity<>("No album with id %d to delete".formatted(idOptional.get()),HttpStatus.NOT_FOUND);
        }
        //this is never returned - if I don't enter an id the returned status 404 NOT FOUND
        return new ResponseEntity<>("No id entered, please enter an id for the album you wish to delete", HttpStatus.NO_CONTENT);
    }
}
