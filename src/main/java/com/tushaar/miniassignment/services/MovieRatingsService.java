package com.tushaar.miniassignment.services;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tushaar.miniassignment.exceptions.MovieRatingNotFoundException;
import com.tushaar.miniassignment.filters.ResponseTimeFilter;
import com.tushaar.miniassignment.models.MovieRating;
import com.tushaar.miniassignment.repository.MovieRatingRepository;
import com.tushaar.miniassignment.utilities.Utilities;

@Service
public class MovieRatingsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieRatingsService.class);

	@Autowired
	private MovieRatingRepository repository;

	@PostConstruct
	public void initialize() throws IOException {
		new Utilities().initialLoad(this);
	}

	public void initialLoad(List<MovieRating> movies) {
		LOGGER.info("Loading initial data from file to database " + this.getClass().getName());
		for (MovieRating movie : movies) {
			repository.save(movie);
		}
	}

	public ResponseEntity<?> save(MovieRating rating) {
		LOGGER.info("Save MovieRating to database " + this.getClass().getName());
		try {
			MovieRating r = repository.save(rating);
			return new ResponseEntity<>(r, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getAllRatings() {
		LOGGER.info("Get All MovieRatings from database " + this.getClass().getName());
		try {
			List<MovieRating> r = repository.getAllRatings();
			return new ResponseEntity<>(r, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occured.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> findById(String id) {
		LOGGER.info("Fetch MovieRating from database using ID " + this.getClass().getName());
		try {
			MovieRating r = repository.findById(id);
			if (r == null)
				throw new MovieRatingNotFoundException("No Movie Rating found");
			return new ResponseEntity<>(r, HttpStatus.OK);
		} catch (MovieRatingNotFoundException e) {
			return new ResponseEntity<>("No Movie Rating found", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> deleteById(String id) {
		LOGGER.info("Delete MovieRating from database using ID " + this.getClass().getName());
		try {
			MovieRating r = repository.deleteById(id);
			return new ResponseEntity<>(r, HttpStatus.OK);
		} catch (MovieRatingNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getMovieRatingByDirectorinGivenYearRange(String director, String start, String end) {
		LOGGER.info("Used to fetch MovieRating for a particular director in given year range " + this.getClass().getName());
		return repository.getMovieRatingByDirectorinGivenYearRange(director, start, end);
	}

	public ResponseEntity<?> getMoviesWithReviewsGreaterThanGivenReview(int review) {
		LOGGER.info("Used to fetch List of MovieRatings with user reviews greater than a given number " + this.getClass().getName());
		try {
			List<MovieRating> r = repository.getMoviesWithReviewsGreaterThanGivenReview(review);
			return new ResponseEntity<>(r, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getMovieWithHighestBudgetInYearAndCountry(String year, String country) {
		LOGGER.info("Used to fetch Movie Rating with highest budget in given year and country " + this.getClass().getName());
		try {
			MovieRating r = repository.getMovieWithHighestBudgetInYearAndCountry(year, country);
			return new ResponseEntity<>(r, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
