package com.screenspot.homeservice.repository;

import com.screenspot.homeservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findAllByMovieId(Long movieId);

    Optional<List<Rating>> findByUserId(Long userid);

}