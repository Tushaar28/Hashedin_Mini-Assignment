//package com.tushaar.miniassignment.configuration;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import com.tushaar.miniassignment.models.MovieRating;
//import com.tushaar.miniassignment.repository.MovieRatingRepository;
//
//@Component
//public class InitialLoad {
//	
//	@Autowired
//	private MovieRatingRepository repository;
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void runAfterStartup() {
//		String line = "";
//		try {
//			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/movies.csv"));
//			String headerLine = br.readLine();
//			while ((line = br.readLine()) != null) {
//				String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//				MovieRating m = new MovieRating(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4], data[5], Integer.parseInt(data[6]), data[7], data[8], data[9], data[10]);
//				repository.save(m);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
