package com.screenspot.userservice.controller;


import com.screenspot.userservice.dto.RatingDto;
import com.screenspot.userservice.exception.InvalidRatingException;
import com.screenspot.userservice.exception.MovieNotFoundException;
import com.screenspot.userservice.exception.MoviesNotFoundException;
import com.screenspot.userservice.model.Movie;
import com.screenspot.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping("/viewAllMovies")
    public ResponseEntity<List<Movie>> getAllMovies() throws MoviesNotFoundException {
        return ResponseEntity.ok().body(userService.fetchAllMovies());
    }


    @GetMapping("/search/movie/name/{name}")
    public ResponseEntity<Movie> getMovieByName(@PathVariable String name) throws MovieNotFoundException {
        return ResponseEntity.ok().body(userService.fetchMovieByName(name));

    }


    @GetMapping("/search/movie/date/{date}")
    public ResponseEntity<List<Movie>> getMovieByDate(@PathVariable String date) throws MoviesNotFoundException {
        return ResponseEntity.ok().body(userService.fetchMovieByDate(date));
    }


    @PostMapping("add/rating/movie/{movieid}/{userName}")
    public ResponseEntity<String> addRatingMovie(@Valid @RequestBody RatingDto ratingdto, @PathVariable Long movieid, @PathVariable String userName) throws InvalidRatingException, MovieNotFoundException {
        if (ratingdto.getRating() < 1 || ratingdto.getRating() > 10) {
            throw new InvalidRatingException("Invalid rating. Please provide a rating between 1 and 10");
        }

        boolean ratingAdded = userService.addMovieRating(ratingdto, movieid, userName);

        if (ratingAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Rating added successfully.");
        } else {
            throw new MovieNotFoundException("Failed to add rating, Movie not found");
        }

    }

}
