package com.screenspot.homeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RATING_TABLE")
public class Rating {

    @Id
    @GeneratedValue
    private long ratingId;

    private long userId;

    private long movieId;

    private double rating;

    private String message;

}