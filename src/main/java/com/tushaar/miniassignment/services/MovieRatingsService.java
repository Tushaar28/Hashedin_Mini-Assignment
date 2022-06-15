package com.tushaar.miniassignment.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tushaar.miniassignment.exceptions.MovieRatingNotFoundException;
import com.tushaar.miniassignment.exceptions.UnauthorizedException;
import com.tushaar.miniassignment.models.MovieRating;
import com.tushaar.miniassignment.repository.MovieRatingRepository;

@Service
public class MovieRatingsService {

	@Autowired
	private MovieRatingRepository repository;

	private final String token = "token";

	public ResponseEntity<?> save(Map<String, String> headers, MovieRating rating) {
		long time = System.currentTimeMillis();
		try {
			if (!headers.containsKey("x-auth-token")
					|| (headers.containsKey("x-auth-token") && !headers.get("x-auth-token").equals(token))) {
				throw new UnauthorizedException("Invalid access token");
			}
			MovieRating r = repository.save(rating);
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>(r, responseHeaders, HttpStatus.CREATED);
		} catch (UnauthorizedException e) {
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>(e.getMessage(), responseHeaders, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>("An error occured", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getAllRatings(Map<String, String> headers) {
		long time = System.currentTimeMillis();
		try {
			if (!headers.containsKey("x-auth-token")
					|| (headers.containsKey("x-auth-token") && !headers.get("x-auth-token").equals(token))) {
				throw new UnauthorizedException("Invalid access token");
			}
			List<MovieRating> r = repository.getAllRatings();
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>(r, responseHeaders, HttpStatus.OK);
		} catch (UnauthorizedException e) {
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>(e.getMessage(), responseHeaders, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>("An error occured.", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> findById(Map<String, String> headers, String id) {
		long time = System.currentTimeMillis();
		try {
			if (!headers.containsKey("x-auth-token")
					|| (headers.containsKey("x-auth-token") && !headers.get("x-auth-token").equals(token))) {
				throw new UnauthorizedException("Invalid access token");
			}
			MovieRating r = repository.findById(id);
			if (r == null)
				throw new MovieRatingNotFoundException("No Movie Rating found");
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>(r, responseHeaders, HttpStatus.OK);
		} catch (UnauthorizedException e) {
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>(e.getMessage(), responseHeaders, HttpStatus.UNAUTHORIZED);
		} catch (MovieRatingNotFoundException e) {
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>("No Movie Rating found", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>("An error occured", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> deleteById(Map<String, String> headers, String id) {
		long time = System.currentTimeMillis();
		try {
			if (!headers.containsKey("x-auth-token")
					|| (headers.containsKey("x-auth-token") && !headers.get("x-auth-token").equals(token))) {
				throw new UnauthorizedException("Invalid access token");
			}
			MovieRating r = repository.deleteById(id);
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>(r, responseHeaders, HttpStatus.OK);
		} catch (UnauthorizedException e) {
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>(e.getMessage(), responseHeaders, HttpStatus.UNAUTHORIZED);
		} catch (MovieRatingNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			time = System.currentTimeMillis() - time;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("X-TIME-TO-EXECUTE", String.valueOf(time));
			return new ResponseEntity<>("An error occured", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getMovieRatingByDirectorinGivenYearRange(String director, int start, int end) {
		return repository.getMovieRatingByDirectorinGivenYearRange(director, start, end);
	}
}
