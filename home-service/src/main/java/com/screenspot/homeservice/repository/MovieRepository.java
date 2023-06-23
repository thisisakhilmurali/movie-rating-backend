package com.screenspot.homeservice.repository;

import com.screenspot.homeservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findByMovieName(String name);

    List<Movie> findAllByMovieReleaseDate(String date);

    List<Movie> findAllByMovieGenre(String genre);

}