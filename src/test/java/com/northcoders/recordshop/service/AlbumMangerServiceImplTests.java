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

    @Test
    void getAllAlbumsInStock() {
        Album albumInStock = Album.builder().id(1L).title("album in stock").artist("artist")
                .releaseYear(2024)
                .genre(Genre.ROCK)
                .stock(new Stock(1L, 200, null)).build();

        List<Album> albums = List.of(albumInStock);

        when(mockAlbumManagerRepository.findAllByInStock()).thenReturn(List.of(albumInStock));

        List<Album> actual = albumMangerServiceImpl.getAllAlbumsInStock();

        assertThat(actual).isEqualTo(albums);
    }

    @Test
    void updateAlbumById() {

        Album albumA = new Album(1L, "original album", 2024, "artist", Genre.ROCK, null);
        Album albumB = new Album(1L, null, 0, "updated artist", Genre.ROCK, null);

        Album compositeAlbum = new Album(1L, "original album", 2024, "updated artist", Genre.ROCK, null);

        albumMangerServiceImpl.postAlbum(albumA);

        when(mockAlbumManagerRepository.save(albumB)).thenReturn(compositeAlbum);

        Album actual = albumMangerServiceImpl.updateAlbumById(albumB, 1L);

        assertThat(actual).isEqualTo(compositeAlbum);

    }

    @Test
    void deleteAlbumById() {
        Album album = new Album(1L, "original album", 2024, "artist", Genre.ROCK, null);
        Album album2 = new Album(1L, "second album", 2024, "artist", Genre.ROCK, null);

        albumMangerServiceImpl.postAlbum(album);
        albumMangerServiceImpl.postAlbum(album2);
        when(mockAlbumManagerRepository.findAll()).thenReturn(List.of(album, album2));
        List<Album> albums = albumMangerServiceImpl.getAllAlbums();
        assertThat(albums).hasSize(2);

        albumMangerServiceImpl.deleteAlbumById(1L);
        when(mockAlbumManagerRepository.findAll()).thenReturn(List.of(album2));
        List<Album> albumsAfterDelete = albumMangerServiceImpl.getAllAlbums();
        assertThat(albumsAfterDelete).hasSize(1);
    }
}