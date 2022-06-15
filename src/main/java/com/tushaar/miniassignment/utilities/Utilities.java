package com.tushaar.miniassignment.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	private String getMovieRatingAsCsvRecord(MovieRating r) {
		return r.getImdb_title_id() + "," + r.getTitle() + "," + r.getOriginal() + "," + r.getYear() + "," + r.getDatePublished() + "," + r.getGenre() + "," + String.valueOf(r.getDuration()) + "," + r.getCountry() + "," + r.getLanguage() + "," + r.getDirector() + "," + r.getWriter() + "," + r.getProduction_company() + "," + r.getActors() + "," + r.getDescription() + "," + String.valueOf(r.getAvg_vote()) + String.valueOf(r.getVotes()) + "," + r.getBudget() + "," + r.getUsa_gross_income() + "," + String.valueOf(r.getMetascore()) + "," + String.valueOf(r.getReviews_from_users()) + "," + String.valueOf(r.getReviews_from_critics());
	}
	
	public void insertRecordInFile(MovieRating r) {
		File file = new File(dataFile);
		try {
			FileWriter fw = new FileWriter(dataFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			Scanner x = new Scanner(new File(dataFile));
			x.useDelimiter("[,\n]");
			while (x.hasNext()) {
				String id = x.next();
				if (id.equals(r.getImdb_title_id())) {
					pw.println(getMovieRatingAsCsvRecord(r));
				}
			}
		} catch (Exception e) {
			LOGGER.error("Unable to insert new rcord into file");
		}
	}
}
