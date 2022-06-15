package com.tushaar.miniassignment.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.tushaar.miniassignment.exceptions.MovieRatingNotFoundException;
import com.tushaar.miniassignment.models.MovieRating;

@Repository
public class MovieRatingRepository {
	
	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	
	public MovieRating save(MovieRating rating) {
		dynamoDBMapper.save(rating);
		return rating;
	}
	
	public List<MovieRating> saveAll(List<MovieRating> ratings) {
		dynamoDBMapper.batchSave(ratings);
		return ratings;
	}

	public List<MovieRating> getAllRatings() {
		return dynamoDBMapper.scan(MovieRating.class, new DynamoDBScanExpression());
	}

	public MovieRating findById(String id) {
		MovieRating rating =  dynamoDBMapper.load(MovieRating.class, id);
		return rating;
	}

	public MovieRating deleteById(String id) {
		MovieRating rating = dynamoDBMapper.load(MovieRating.class, id);
		if (rating == null) {
			throw new MovieRatingNotFoundException("No Movie Rating found");
		}
		dynamoDBMapper.delete(rating);
		return rating;	
	}
	
	public String updateById(String id, MovieRating rating) {
		dynamoDBMapper.save(rating, new DynamoDBSaveExpression().withExpectedEntry("id", new ExpectedAttributeValue(new AttributeValue().withS(id))));
		return id;
	}

	public ResponseEntity<?> getMovieRatingByDirectorinGivenYearRange(String director, String start, String end) {
		try {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(director));
		eav.put(":val2", new AttributeValue().withS(start));
		eav.put(":val3", new AttributeValue().withS(end));
		
		Map<String, String> keyMap = new HashMap<>();
		keyMap.put("#year", "year");
		
		DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
	            .withFilterExpression("director = :val1 and #year between :val2 and :val3")
	            .withExpressionAttributeValues(eav)
	            .withExpressionAttributeNames(keyMap);
		
		List<MovieRating> ratings = dynamoDBMapper.scan(MovieRating.class, queryExpression);
		
		return new ResponseEntity<>(ratings, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public List<MovieRating> getMoviesWithReviewsGreaterThanGivenReview(int review) {
		HashMap<String, AttributeValue> eav = new HashMap<>();
		eav.put(":review", new AttributeValue().withN(String.valueOf(review)));
		
		DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
									.withFilterExpression("reviews_from_users > :review")
									.withExpressionAttributeValues(eav);
		
		List<MovieRating> result = dynamoDBMapper.scan(MovieRating.class, queryExpression);
		List<MovieRating> movies = new ArrayList<>();
		movies.addAll(result);
		Collections.sort(movies, new Comparator<MovieRating>() {
			@Override
			public int compare(MovieRating m1, MovieRating m2) {
				return m2.getReviews_from_users() - m1.getReviews_from_users();
			}
		});	
		return movies;
	}

	public MovieRating getMovieWithHighestBudgetInYearAndCountry(String year, String country) {
		HashMap<String, AttributeValue> eav = new HashMap<>();
		eav.put(":val1", new AttributeValue().withS(year));
		eav.put(":val2", new AttributeValue().withS(country));
		
		Map<String, String> keyMap = new HashMap<>();
		keyMap.put("#year", "year");
		
		DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
	            .withFilterExpression("#year = :val1 and country = :val2")
	            .withExpressionAttributeValues(eav)
	            .withExpressionAttributeNames(keyMap);;
	            
		List<MovieRating> result = dynamoDBMapper.scan(MovieRating.class, queryExpression);
		MovieRating movieWithHighestBudget = null;
		Integer max = Integer.MIN_VALUE;
		for (MovieRating movie: result) {
			int budget = Integer.parseInt(movie.getBudget().replaceAll("[^\\d]", ""));
			if (budget > max) {
				max = budget;
				movieWithHighestBudget = movie;
			}
		}
		return movieWithHighestBudget;
	}
	

}
