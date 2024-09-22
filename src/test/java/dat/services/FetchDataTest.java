package dat.services;

import dat.config.HibernateConfig;
import dat.dtos.MovieDTO;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FetchDataTest {

	private static FetchData fetchData;
	private static EntityManagerFactory emf;

	/**
	 * HibernateConfig.getEntityManagerFactoryForTest() uses a containerized PostgreSQL database, which is managed by Testcontainers.
	 * This ensures that each test runs with a fresh database and leaves no state behind, making the tests reliable and isolated.
	 */

	@BeforeAll
	static void setUpAll() {
		// Initialize the EntityManagerFactory using the test configuration
		emf = HibernateConfig.getEntityManagerFactoryForTest();
		fetchData = new FetchData();

		// Insert some initial data into the test database
		insertTestData();
	}

	@AfterAll
	static void tearDownAll() {
		// Close the EntityManagerFactory after all tests
		if (emf != null) {
			emf.close();
		}
	}

	@BeforeEach
	void setUp() {
		// Clear data before each test if needed, or set up specific test data
	}

	@AfterEach
	void tearDown() {
		// Clean up the database after each test to reset the state
		clearTestData();
	}

	@Test
	void getAllMovies() {
		// Act: Retrieve all movies
		List<MovieDTO> movies = fetchData.getAllMovies();

		// Assert: Verify that the movies were fetched correctly
		assertNotNull(movies);
		assertEquals(20, movies.size(), "There should be 20 movies in the database.");
		assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().equals("Inception")));
		assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().equals("Dreambuilders")));
	}

	@Test
	void persistEntity() {
		// Arrange: Create a new MovieDTO
		MovieDTO newMovie = new MovieDTO();
		newMovie.setTitle("New Movie");
		newMovie.setVoteAverage(7.8);
		newMovie.setPopularity(8.5);

		// Act: Persist the new movie
		fetchData.persistEntity(newMovie);

		// Assert: Verify that the movie was added
		List<MovieDTO> movies = fetchData.getAllMovies();
		assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().equals("New Movie")));
	}

	@Test
	void findEntity() {
		// Arrange: Use an existing ID from the inserted test data
		long movieId = 1113448L;

		// Act: Find the movie by its ID
		MovieDTO movie = fetchData.findEntity(movieId);

		// Assert: Verify the movie details
		assertNotNull(movie);
		assertEquals("Ehrengard: The Art of Seduction", movie.getTitle());
		assertEquals(6.2, movie.getVoteAverage());
	}

	@Test
	void updateEntity() {
		// Arrange: Find a movie, update its title, and persist the change
		long movieId = 1113448L;
		MovieDTO movie = fetchData.findEntity(movieId);
		movie.setTitle("Updated Ehrengard");

		// Act: Update the movie entity
		fetchData.updateEntity(movie, movieId);

		// Assert: Verify that the movie was updated
		MovieDTO updatedMovie = fetchData.findEntity(movieId);
		assertEquals("Updated Ehrengard", updatedMovie.getTitle());
	}

	@Test
	void removeEntity() {
		// Arrange: Use an existing ID from the inserted test data
		long movieId = 1113448L;

		// Act: Remove the movie
		fetchData.removeEntity(movieId);

		// Assert: Verify that the movie was removed
		MovieDTO movie = fetchData.findEntity(movieId);
		assertNull(movie, "Movie should be removed from the database.");
	}

	// Helper method to insert initial test data into the database
	private static void insertTestData() {

		// Create a list of MovieDTO objects
		List<MovieDTO> movies = List.of(
				new MovieDTO(1113448L, "Ehrengard: The Art of Seduction", 6.2, 13.269, "2023-09-14"),
				new MovieDTO(650031L, "The Shadow in My Eye", 7.82, 16.039, "2021-10-28"),
				new MovieDTO(571655L, "Valhalla", 5.156, 11.0, "2019-10-10"),
				new MovieDTO(586742L, "Dreambuilders", 7.6, 9.326, "2020-02-06"),
				new MovieDTO(1232827L, "The Girl with the Needle", 10.0, 10.843, "2024-09-06"),
				new MovieDTO(1325024L, "My Eternal Summer", 0.0, 21.375, "2024-09-23"),
				new MovieDTO(519465L, "Queen of Hearts", 6.956, 45.635, "2019-03-27"),
				new MovieDTO(1119173L, "A Beautiful Life", 6.929, 21.204, "2023-06-01"),
				new MovieDTO(983524L, "Birthday Girl", 5.8, 9.934, "2024-04-04"),
				new MovieDTO(833339L, "Speak No Evil", 6.603, 49.032, "2022-03-17"),
				new MovieDTO(545330L, "Hacker", 6.746, 10.297, "2019-03-28"),
				new MovieDTO(580175L, "Another Round", 7.643, 24.483, "2020-09-24"),
				new MovieDTO(980026L, "The Promised Land", 7.786, 28.151, "2023-10-05"),
				new MovieDTO(1119197L, "Paranoia", 4.75, 19.864, "2024-02-08"),
				new MovieDTO(990691L, "Loving Adults", 6.254, 16.106, "2022-08-26"),
				new MovieDTO(1085218L, "Darkland: The Return", 6.42, 26.069, "2023-04-13"),
				new MovieDTO(934287L, "The Last Client", 7.289, 10.879, "2022-06-20"),
				new MovieDTO(1078249L, "Boundless", 5.846, 26.203, "2024-02-01"),
				new MovieDTO(1115377L, "The Angel Maker", 5.394, 24.819, "2023-06-08"),
				new MovieDTO(680813L, "Flee", 7.8, 13.539, "2021-06-17")
		);

		// Persist each movie in the test database
		for (MovieDTO movie : movies) {
			fetchData.persistEntity(movie);
		}
	}

	// Helper method to clear test data
	private static void clearTestData() {
		// Remove all movies or use an EntityManager to delete all entities
		List<MovieDTO> allMovies = fetchData.getAllMovies();
		for (MovieDTO movie : allMovies) {
			fetchData.removeEntity(movie.getId());
		}
	}
}
