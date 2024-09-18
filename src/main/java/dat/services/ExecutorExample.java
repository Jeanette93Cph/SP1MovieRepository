package dat.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorExample {

	// Your TMDb API key
	private static final String API_KEY = System.getenv("api_key");
	private static final String BASE_URL = "https://api.themoviedb.org/3/movie/popular";

	// HttpClient setup
	private static final HttpClient httpClient = HttpClient.newHttpClient();

	// Method to fetch movies for a single page
	public static CompletableFuture<String> fetchMovies(int page) {
		String url = BASE_URL + "?api_key=" + API_KEY + "&language=en-US&page=" + page;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();

		return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.thenApply(HttpResponse::body)
				.exceptionally(e -> {
					System.out.println("Failed to fetch page " + page + ": " + e.getMessage());
					return null;
				});
	}

	// Method to fetch movies in parallel
	public static List<String> fetchMoviesInParallel(int startPage, int endPage, int maxThreads) throws InterruptedException {
		// Create a thread pool with the specified max number of threads
		ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

		List<CompletableFuture<String>> futures = new ArrayList<>();

		// Submit requests for each page
		for (int page = startPage; page <= endPage; page++) {
			CompletableFuture<String> future = fetchMovies(page);
			futures.add(future);
		}

		// Wait for all futures to complete
		List<String> results = futures.stream()
				.map(CompletableFuture::join)  // Blocking call to wait for completion
				.toList();

		// Shutdown the executor
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);  // Ensure all tasks complete within a minute

		return results;
	}

	public static void main(String[] args) throws InterruptedException {
		int startPage = 1;
		int endPage = 5;
		int maxThreads = 3;  // Limit the number of concurrent requests

		// Fetch movies from pages 1 to 5 with a limit of 3 parallel requests
		List<String> moviesData = fetchMoviesInParallel(startPage, endPage, maxThreads);

		// Print out the movie data for each page
		for (int i = 0; i < moviesData.size(); i++) {
			System.out.println("Page " + (i + startPage) + ": " + moviesData.get(i));
		}
	}
}
