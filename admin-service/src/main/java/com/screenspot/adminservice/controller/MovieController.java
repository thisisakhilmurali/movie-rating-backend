package com.screenspot.adminservice.controller;


import com.screenspot.adminservice.exception.MovieNotFoundException;
import com.screenspot.adminservice.model.Movie;
import com.screenspot.adminservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping(value = "/addMovie")
    public ResponseEntity<String> addMovie(@RequestParam("movieName")
                                           String name,
                                           @RequestParam("movieDirector")
                                           String director,
                                           @RequestParam("movieGenre")
                                           String genre,
                                           @RequestParam("movieReleaseDate")
                                           String releaseDate,
                                           @RequestParam("movieLanguage")
                                           String language,
                                           @RequestParam("duration")
                                           String duration,
                                           @RequestParam("country")
                                           String country,
                                           @RequestParam("description")
                                           String description,
                                           @RequestParam("overallRate")
                                           double overallrate,
                                           @RequestParam("file")
                                           MultipartFile imageFile) {
        try {
            movieService.addMovie(name, director, genre, releaseDate, language, duration, country,description,overallrate, imageFile);
            return ResponseEntity.ok("Movie added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add movie");
        }
    }


    @GetMapping("/viewAllMovies")
    public List<Movie> viewAllMovies() {
        return movieService.view();
    }

    @GetMapping("/get/{movieId}")
    public ResponseEntity<Movie> getById(@PathVariable long movieId) throws MovieNotFoundException {
        return ResponseEntity.ok(movieService.fetchById(movieId));
    }

    @PutMapping("/updateAMovie/{movieId}")
    public ResponseEntity<String> updateMovie(@PathVariable long movieId,
                                              @RequestParam("movieName")
                                              String name,
                                              @RequestParam("movieDirector")
                                              String director,
                                              @RequestParam("movieGenre")
                                              String genre,
                                              @RequestParam("movieReleaseDate")
                                              String releaseDate,
                                              @RequestParam("movieLanguage")
                                              String language,
                                              @RequestParam("duration")
                                              String duration,
                                              @RequestParam("country")
                                              String country,
                                              @RequestParam("description")
                                              String description,
                                              @RequestParam("overallRate")
                                              double overallrate,
                                              @RequestParam("file")
                                              MultipartFile imageFile)  throws MovieNotFoundException {

        try {
            movieService.updateMovie(movieId,name, director, genre, releaseDate, language, duration, country,description,overallrate, imageFile);

            return ResponseEntity.ok("Movie updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update movie");
        }
    }

    @DeleteMapping("/deleteAMovie/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long movieId) {
        try {
            movieService.deleteMovieById(movieId);
            return ResponseEntity.ok("Movie deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete movie");
        }
    }


}
