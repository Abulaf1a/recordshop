package com.northcoders.recordshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private int quantity;

    //TODO: investigate whether this should include cascading setting
    //perhaps use @JsonBackReference, and also FetchType = LAZY;
    @JsonIgnore
    @OneToOne(mappedBy = "stock", cascade = CascadeType.ALL)
    private Album album;

    public Stock(int quantity, Album album){
        this.quantity = quantity;
        this.album = album;
    }
}
