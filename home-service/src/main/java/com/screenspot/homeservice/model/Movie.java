package com.screenspot.homeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MOVIES_TABLE")
public class Movie {

    @Column(name="movie_id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long movieId;

    @Column(name="movie_name")
    private String movieName;

    @Column(name="movie_director")
    private String movieDirector;

    @Column(name="movie_genre")
    private String movieGenre;

    @Column(name="movie_release_date")
    private String movieReleaseDate;

    @Column(name="movie_language")
    private String movieLanguage;

    @Column(name="duration")
    private String duration;

    @Column(name="country")
    private String country;

    @Column(name="description")
    private String movieDescription;

    @Column(name="overall_rate")
    private double overallRate;

    @Column(name="image_url")
    private String imageUrl;

}