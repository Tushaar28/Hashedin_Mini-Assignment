package com.tushaar.miniassignment.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tushaar.miniassignment.controllers.MovieRatingsController;
import com.tushaar.miniassignment.models.MovieRating;
import com.tushaar.miniassignment.services.MovieRatingsService;

public class Utilities {
	
	private final String dataFile = "src/main/resources/movies.csv";
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieRatingsController.class);
	
	public void initialLoad(MovieRatingsService movieRatingsService) {
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			br.readLine(); // Skipping header
			List<MovieRating> movies = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				MovieRating m = MovieRating.createObject(data);
				System.out.println(m);
				movies.add(m);
			}
			movieRatingsService.initialLoad(movies);
			LOGGER.info(String.format("%s movies initially loaded",movies.size()));
			System.out.println(String.format("%s movies initially loaded",movies.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
