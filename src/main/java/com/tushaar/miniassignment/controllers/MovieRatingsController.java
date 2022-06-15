package com.tushaar.miniassignment.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tushaar.miniassignment.models.MovieRating;
import com.tushaar.miniassignment.services.MovieRatingsService;

@RestController
@RequestMapping("/ratings")
public class MovieRatingsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieRatingsController.class);
	
	@Autowired
	private MovieRatingsService service;
	
	/**
	 * Used to save a MovieRating to database
	 * @param rating
	 * @param headers
	 * @return ResponseEntity
	 */
	@PostMapping
	public ResponseEntity<?> save(@RequestBody MovieRating rating) {
		LOGGER.info("save MovieRating to database " + this.getClass().getName());
		return service.save(rating);
	}

	/**
	 * Used to fetch all MovieRatings
	 * @param headers
	 * @return ResponseEntity
	 */
	@GetMapping
	public ResponseEntity<?> getAllRatings() {
		LOGGER.info("Fetch all MovieRating from database " + this.getClass().getName());
		return service.getAllRatings();
	}
	
	/**
	 * Used to fetch specific MovieRating
	 * @param headers
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") String id) {
		LOGGER.info("Fetch a sepcific MovieRating from database using given ID " + this.getClass().getName());
		return service.findById(id);
	}
	
	/**
	 * Used to delete a specific MovieRating
	 * @param headers
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
		LOGGER.info("Delete a specific MovieRating from database using given ID " + this.getClass().getName());
		return service.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@RequestBody MovieRating rating, @PathVariable("id") String id) {
		LOGGER.info("Update a specific MovieRating from database using given ID " + this.getClass().getName());
		return service.updateById(id, rating);
	}
	
	/**
	 * Used to fetch MovieRating for a particular director in given year range
	 * @param director
	 * @param start
	 * @param end
	 * @return ResponseEntity
	 */
	@GetMapping("/query1")
	public ResponseEntity<?> getMovieRatingByDirectorinGivenYearRange(@RequestParam String director, @RequestParam String start, @RequestParam String end) {
		LOGGER.info("Used to fetch MovieRating for a particular director in given year range " + this.getClass().getName());
		return service.getMovieRatingByDirectorinGivenYearRange(director, start, end);	
	}
	
	/**
	 * Used to fetch List of MovieRatings with user reviews greater than a given number 
	 * @param review
	 * @return ResponseEntity
	 */
	@GetMapping("/query2")
	public ResponseEntity<?> getMoviesWithReviewsGreaterThanGivenReview(@RequestParam int review) {
		LOGGER.info("Used to fetch List of MovieRatings with user reviews greater than a given number " + this.getClass().getName());
		return service.getMoviesWithReviewsGreaterThanGivenReview(review);
	}
	
	/**
	 * Used to fetch Movie Rating with highest budget in given year and country
	 * @param year
	 * @param country
	 * @return ResponseEntity
	 */
	@GetMapping("/query3")
	public ResponseEntity<?> getMovieWithHighestBudgetInYearAndCountry(@RequestParam String year, @RequestParam String country) {
		LOGGER.info("Used to fetch Movie Rating with highest budget in given year and country " + this.getClass().getName());
		return service.getMovieWithHighestBudgetInYearAndCountry(year, country);
	}
}
