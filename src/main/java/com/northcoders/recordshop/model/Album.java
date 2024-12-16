package com.northcoders.recordshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //https://projectlombok.org/features/Data adds stuff like .toString()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private int releaseYear;

    @Column
    private String artist;

    @Column
    private Genre genre;

    //fetchType is a spring/bean specific thing
    //cascadeType is a HIBERNATE type stuff thing. 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "stock_album",
    joinColumns =
            {@JoinColumn (name = "album_id", referencedColumnName = "id")},
    inverseJoinColumns =
            {@JoinColumn (name = "stock_id", referencedColumnName = "id")})
    private Stock stock;



}
