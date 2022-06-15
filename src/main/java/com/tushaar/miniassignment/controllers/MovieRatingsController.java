package com.tushaar.miniassignment.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tushaar.miniassignment.models.MovieRating;
import com.tushaar.miniassignment.services.MovieRatingsService;

@RestController
@RequestMapping("/ratings")
public class MovieRatingsController {
	
	@Autowired
	private MovieRatingsService service;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody MovieRating rating, @RequestHeader Map<String, String> headers) {
		return service.save(headers, rating);
	}

	@GetMapping
	public ResponseEntity<?> getAllRatings(@RequestHeader Map<String, String> headers) {
		return service.getAllRatings(headers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@RequestHeader Map<String, String> headers, @PathVariable("id") String id) {
		return service.findById(headers, id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@RequestHeader Map<String, String> headers, @PathVariable("id") String id) {
		return service.deleteById(headers, id);
	}
	
	@GetMapping("/query1")
	public ResponseEntity<?> getMovieRatingByDirectorinGivenYearRange(@RequestParam String director, @RequestParam int start, @RequestParam int end) {
		return service.getMovieRatingByDirectorinGivenYearRange(director, start,end);	
	}
	
	
}
