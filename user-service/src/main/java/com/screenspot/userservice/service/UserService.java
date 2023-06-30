package com.screenspot.userservice.service;


import com.screenspot.userservice.dto.RatingDto;
import com.screenspot.userservice.exception.MovieNotFoundException;
import com.screenspot.userservice.exception.MoviesNotFoundException;
import com.screenspot.userservice.model.Movie;
import com.screenspot.userservice.model.Rating;
import com.screenspot.userservice.repository.MovieRepository;
import com.screenspot.userservice.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService {

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


    public Movie fetchMovieByName(String name) throws MovieNotFoundException {
        Movie movie = movieRepository.findByMovieName(name);
        if(movie !=null) {
            return movie;
        } else {
            throw new MovieNotFoundException("Movie with name " + name + " not found");
        }
    }

    public List<Movie> fetchMovieByDate(String date) throws MoviesNotFoundException {
        List<Movie> movies = movieRepository.findAllByMovieReleaseDate(date);
        if (movies.isEmpty()) {
            throw new MoviesNotFoundException("No movies found with the release date: " + date);
        }
        return movies;
    }


    public boolean addMovieRating(RatingDto ratingDto, Long movieId, String userName) {
        Optional<Movie> op = movieRepository.findById(movieId);

        if(op.isPresent()) {

            Optional<List<Rating>> op2 =  ratingRepository.findByUserName(userName);
            List<Rating> ratingObjList = op2.get();
            int flag=0;
            for(Rating obj:ratingObjList) {
                if(obj.getMovieId()==movieId) {
                    obj.setRating(ratingDto.getRating());
                    obj.setMessage(ratingDto.getMessage());
                    flag=1;
                    ratingRepository.save(obj);
                }
            }

            if(flag==0) {
                Rating ratingObj = new Rating();
                ratingObj.setMovieId(movieId);
                ratingObj.setUserName(userName);
                ratingObj.setRating(ratingDto.getRating());
                ratingObj.setMessage(ratingDto.getMessage());
                ratingRepository.save(ratingObj);
            }

        } else {
            return false;
        }

        Movie movie = op.get();
        List<Rating> list = ratingRepository.findAllByMovieId(movieId);
        AtomicReference<Double> overallRate = new AtomicReference<>(0.0);
        list.forEach((e) -> overallRate.updateAndGet(currentRate -> currentRate + e.getRating()));
        overallRate.set(overallRate.get() / list.size());
        movie.setOverallRate(overallRate.get());
        movieRepository.save(movie);
        return true;
    }


}


