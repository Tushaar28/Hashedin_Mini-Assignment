package com.tushaar.miniassignment.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
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

	public ResponseEntity<?> getMovieRatingByDirectorinGivenYearRange(String director, int start, int end) {
		try {
		String partitionKey = director;
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(partitionKey));
		eav.put(":val2", new AttributeValue().withN(String.valueOf(start)));
		eav.put(":val3", new AttributeValue().withN(String.valueOf(end)));
		
		DynamoDBQueryExpression<MovieRating> queryExpression = new DynamoDBQueryExpression<MovieRating>()
	            .withKeyConditionExpression("director = :val1 and year between :val2 and :val3")
	            .withExpressionAttributeValues(eav);
		
		List<MovieRating> ratings = dynamoDBMapper.query(MovieRating.class, queryExpression);
		
		return new ResponseEntity<>(ratings, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
