package com.screenspot.homeservice.service;

import com.screenspot.homeservice.exception.MovieNotFoundException;
import com.screenspot.homeservice.exception.MoviesNotFoundException;
import com.screenspot.homeservice.model.Movie;
import com.screenspot.homeservice.repository.MovieRepository;
import com.screenspot.homeservice.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    RatingRepository ratingRepository;

    public List<Movie> fetchAllMovies() throws MoviesNotFoundException {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            throw new MoviesNotFoundException("No movies found");
        }
        return movies;
    }


    public Movie fecthMovieByName(String name) throws MovieNotFoundException {
        Movie movie = movieRepository.findByMovieName(name);
        if(movie !=null) {
            return movie;
        } else {
            throw new MovieNotFoundException("Movie with name " + name + " not found");
        }
    }

    public List<Movie> fecthMovieByDate(String date) throws MoviesNotFoundException {
        List<Movie> movies = movieRepository.findAllByMovieReleaseDate(date);
        if (movies.isEmpty()) {
            throw new MoviesNotFoundException("No movies found with the release date: " + date);
        }
        return movies;
    }

    public List<Movie> fetchMovieByGenre(String genre) throws MoviesNotFoundException {
        List<Movie> movies = movieRepository.findAllByMovieGenre(genre);
        if (movies.isEmpty()) {
            throw new MoviesNotFoundException("No movies found with the genre: " + genre);
        }
        return movies;
    }
}
