package dat.services;

import dat.dtos.MovieDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class GetAllPagesService {

	private static final int THREAD_POOL_SIZE = 10;

	// Main method to fetch all Danish movies with their details using ExecutorService
	public static List<MovieDTO> getDanishMoviesWithDetails() {
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		List<Future<MovieDTO>> futures = new ArrayList<>();

		try {
			// Fetch first page raw JSON and the list of MovieDTOs
			String firstPageRawJson = MovieService.getMoviesJSONRaw(1); // Get the raw JSON response
			List<MovieDTO> firstPageMovies = MovieService.parseMoviesFromJson(firstPageRawJson); // Parse into MovieDTOs

			if (firstPageMovies == null || firstPageMovies.isEmpty()) {
				throw new RuntimeException("No movies found on page 1.");
			}

			int totalPages = MovieService.getTotalPages(firstPageRawJson); // Use raw JSON to get total pages

			// Submit tasks to fetch all movies across all pages concurrently
			for (int page = 1; page <= totalPages; page++) {
				final int currentPage = page;
				Future<List<MovieDTO>> futureMoviesPage = executorService.submit(() -> {
					String pageJson = MovieService.getMoviesJSONRaw(currentPage);
					return MovieService.parseMoviesFromJson(pageJson);
				});

				try {
					List<MovieDTO> moviesPage = futureMoviesPage.get(); // Get movie list for each page
					// For each movie, submit tasks to fetch its details (actors, directors, genres)
					for (MovieDTO movie : moviesPage) {
						Future<MovieDTO> movieFuture = executorService.submit(() -> fetchMovieDetails(movie));
						futures.add(movieFuture); // Collect all futures for final aggregation
					}
				} catch (InterruptedException | ExecutionException e) {
					System.err.println("Error fetching movie page: " + e.getMessage());
				}
			}

			// Collect results and aggregate into final list of MovieDTOs
			List<MovieDTO> allMoviesWithDetails = new ArrayList<>();
			for (Future<MovieDTO> future : futures) {
				try {
					MovieDTO movie = future.get();
					allMoviesWithDetails.add(movie);
				} catch (InterruptedException | ExecutionException e) {
					System.err.println("Error fetching movie details: " + e.getMessage());
				}
			}

			return allMoviesWithDetails;

		} finally {
			// Shutdown the executor service
			executorService.shutdown();
		}
	}

	// Helper method to fetch all details (actors, directors, genres) for a movie
	private static MovieDTO fetchMovieDetails(MovieDTO movie) {
		movie.setActors(ActorService.getActorsJSON(movie.getId()));
		movie.setDirector(DirectorService.getDirectorsJSON(movie.getId()));
		movie.setGenres(GenreService.getGenresJSON());
		return movie;
	}
}
