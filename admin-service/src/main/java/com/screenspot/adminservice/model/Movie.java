package com.screenspot.adminservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="build")
@Entity
@Table(name="MOVIES_TABLE")
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
    private String description;
    @Column(name="overall_rate")
    private double overallRate;
    @Column(name="image_url")
    private String imageUrl;

    public Movie(String movieName, String movieDirector, String movieGenre, String movieReleaseDate,
                 String movieLanguage, String duration, String country,String description, double overallRate, String imageUrl) {
        super();
        this.movieName = movieName;
        this.movieDirector = movieDirector;
        this.movieGenre = movieGenre;
        this.movieReleaseDate = movieReleaseDate;
        this.movieLanguage = movieLanguage;
        this.duration = duration;
        this.country = country;
        this.description = description;
        this.overallRate = overallRate;
        this.imageUrl = imageUrl;
    }

}
