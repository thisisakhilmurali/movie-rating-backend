package com.screenspot.homeservice.controller;

import com.screenspot.homeservice.exception.MovieNotFoundException;
import com.screenspot.homeservice.exception.MoviesNotFoundException;
import com.screenspot.homeservice.model.Movie;
import com.screenspot.homeservice.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/viewAllMovies")
    public ResponseEntity<List<Movie>> getAllMovies() throws MoviesNotFoundException {
        return ResponseEntity.ok().body(homeService.fetchAllMovies());
    }

    @GetMapping("/search/movie/name/{name}")
    public ResponseEntity<List<Movie>> getMovieByName(@PathVariable String name) throws MoviesNotFoundException {
        return ResponseEntity.ok().body(homeService.fetchMovieByName(name));

    }

    @GetMapping("/search/movie/date/{date}")
    public ResponseEntity<List<Movie>> getMovieByDate(@PathVariable String date) throws MoviesNotFoundException {
        return ResponseEntity.ok().body(homeService.fetchMovieByDate(date));
    }

    @GetMapping("/search/movie/genre/{genre}")
    public ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre) throws MoviesNotFoundException {
        return ResponseEntity.ok().body(homeService.fetchMovieByGenre(genre));
    }

}
