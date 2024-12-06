package com.northcoders.recordshop.service;

import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.model.Genre;
import com.northcoders.recordshop.model.Stock;
import com.northcoders.recordshop.repository.AlbumManagerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
class AlbumMangerServiceImplTests {

    @Mock
    private AlbumManagerRepository mockAlbumManagerRepository;

    @InjectMocks
    private AlbumMangerServiceImpl albumMangerServiceImpl;

    @Test
    void getAllAlbums() {
        //Arrange
        List<Album> mockData = new ArrayList<>();
        Stock stockA = new Stock(1L, 30, new Album());
        Album albumA = new Album(1L, "Mellon Collie and the Infinite Sadness", 1995, "Smashing Pumpkins", Genre.ROCK, stockA);
        stockA.setAlbum(albumA);

        mockData.add(albumA);
        Stock stockB = new Stock(2L, 10, new Album());
        Album albumB= new Album(2L, "Bookends", 1968, "Simon and Garfunkel", Genre.POP, stockB);
        mockData.add(albumB);

        when(mockAlbumManagerRepository.findAll()).thenReturn(mockData);

        //Act
        List<Album> actual = albumMangerServiceImpl.getAllAlbums();

        //Assert
        assertThat(actual).hasSize(2);
        assertThat(actual).isEqualTo(mockData);
    }

    @Test
    void postAlbum() {
        //Arrange
        Stock stock = new Stock(1L, 10, new Album());
        Album album = new Album(1L, "Bookends", 1968, "Simon and Garfunkel", Genre.POP, stock);
        stock.setAlbum(album);
        when(mockAlbumManagerRepository.save(album)).thenReturn(album);

        //Act
        Album actual = albumMangerServiceImpl.postAlbum(album);

        //Assert
        assertThat(actual).isEqualTo(album);
    }

    @Test
    @DisplayName("Get album by id happy path - album exists")
    void getAlbumById(){
        //Arrange
        Optional<Album> album = Optional.of(Album.builder().id(1L).title("album name").artist("artist")
                .releaseYear(2024)
                .genre(Genre.ROCK)
                .stock(new Stock(1L, 200, null)).build());

        when(mockAlbumManagerRepository.findById(1L)).thenReturn(album);

        //Act
        Optional<Album> actual = albumMangerServiceImpl.getAlbumById(1L);

        //Assert
        assertThat(actual).isEqualTo(album);
    }
}