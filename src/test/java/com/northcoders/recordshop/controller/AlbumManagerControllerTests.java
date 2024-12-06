package com.northcoders.recordshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.model.Genre;
import com.northcoders.recordshop.model.Stock;
import com.northcoders.recordshop.service.AlbumMangerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class AlbumManagerControllerTests {

    @Mock
    private AlbumMangerServiceImpl mockAlbumMangerService;

    @InjectMocks
    private AlbumManagerController albumManagerController;

    @Autowired
    MockMvc mockMvcController;

    ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(albumManagerController).build();
        mapper = new ObjectMapper();
    }

    @Test
    void getAllAlbums() throws Exception {
        //Arrange
        List<Album> mockData = new ArrayList<>();
        Stock stockA = new Stock(1L, 30, new Album());
        Album albumA = new Album(1L, "Mellon Collie and the Infinite Sadness", 1995, "Smashing Pumpkins", Genre.POP, stockA);
        //stockA.setAlbum(albumA);
        // including the above line will cause a JSON response body error with the following exception trace snippet:
            // net.minidev.json.parser.ParseException: Malicious payload, having non natural depths, parsing stoped on { at position 31969.
            // at com.jayway.jsonpath.spi.json.JsonSmartJsonProvider.parse(JsonSmartJsonProvider.java:64)

        mockData.add(albumA);

        Stock stockB = new Stock(2L, 10, new Album());
        Album albumB= new Album(2L, "Bookends", 1968, "Simon and Garfunkel", Genre.POP, stockB);
        stockB.setAlbum(albumA);
        mockData.add(albumB);

        when(mockAlbumMangerService.getAllAlbums()).thenReturn(mockData);

        //Act
        //Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/album")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Mellon Collie and the Infinite Sadness"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Bookends"))
                .andReturn();

        verify(mockAlbumMangerService, times(1)).getAllAlbums();
    }

    @Test
    void postAlbum() throws Exception {
        //Arrange
        Album album = Album.builder().id(1L)
                .title("Mellon collie")
                .genre(Genre.POP)
                .stock(new Stock(1L, 10, null))
                .artist("Smashing Pumpkins")
                .releaseYear(1995)
                .build();

        when(mockAlbumMangerService.postAlbum(album)).thenReturn(album);
        //Act
        //Assert
        this.mockMvcController.perform(post("/album")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(album)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // this verifies the method was called only once.
        verify(mockAlbumMangerService, times(1)).postAlbum(album);
    }

    @Test
    @DisplayName("Get album by id happy path")
    void getAlbumById() throws Exception {
        Optional<Album> album = Optional.of(Album.builder().id(1L).title("album name").artist("artist")
                .releaseYear(2024)
                .genre(Genre.ROCK)
                .stock(new Stock(1L, 200, null)).build());

        when(mockAlbumMangerService.getAlbumById(1L)).thenReturn(album);

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/album/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("album name"))
                .andDo(print())
                .andReturn();

        verify(mockAlbumMangerService, times(1)).getAlbumById(1L);
    }

    @Test
    @DisplayName("Get album by id unhappy path - no album with that id")
    void getAlbumById_invalidId() throws Exception {

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/album/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        verify(mockAlbumMangerService, times(1)).getAlbumById(1L);
    }
}