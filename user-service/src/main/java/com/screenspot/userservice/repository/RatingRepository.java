package com.screenspot.userservice.repository;

import com.screenspot.userservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findAllByMovieId(Long movieId);

    Optional<List<Rating>> findByUserName(String userName);

}
