package dat.services;

import dat.config.HibernateConfig;
import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.entities.Movie;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Genre;
import jakarta.persistence.EntityManagerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FetchData
{
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db");



    public List<MovieDTO> getAllMovies()
    {
        MovieDAO movieDAO = new MovieDAO(emf);
        List<MovieDTO> listOfMovieDTO = movieDAO.findAll();
        listOfMovieDTO.forEach(System.out::println);

        return listOfMovieDTO;
    }


    // Get the average rating of all movies
    public void getAverageRatingOfAllMovies () {
        List<MovieDTO> movies = getAllMovies();
        double averageRating = movies.stream()
                .mapToDouble(MovieDTO::getVoteAverage)
                .average()
                .orElse(0);

        System.out.println("\nAverage rating of all movies: " + averageRating);
    }

    // List of all genres
    public List<GenreDTO> getAllGenres ()
    {
        GenreDAO genreDAO = new GenreDAO(emf);

        // Returns entities
        List<GenreDTO> genreDTOs = genreDAO.findAll();
        genreDTOs.forEach(System.out::println);

        return genreDTOs;
    }


    // List of all directors
    public List<DirectorDTO> getAllDirectors() {
        DirectorDAO directorDAO = new DirectorDAO(emf);

        // Returns entities
        List<DirectorDTO> directorDTOs = directorDAO.findAll();
        directorDTOs.forEach(System.out::println);

        return directorDTOs;
    }



    // Get the top 10 lowest-rated movies
    public List<MovieDTO> getTop10LowestRatedMovies () {


        MovieDAO movieDAO = new MovieDAO(emf);
        List<MovieDTO> allMovies = movieDAO.findAll();

        System.out.println("Top 10 lowest-rated Movies");


        // Filter out movies where voteAverage is null
        return allMovies.stream()
                .filter(movie -> movie.getVoteAverage() != null)
                .sorted(Comparator.comparing(MovieDTO::getVoteAverage))
                .limit(10)
                .peek(movie -> System.out.println(
                        "Movie ID: " + movie.getId()
                                + ", Title: " + movie.getTitle()
                                + ", Vote Average: " + movie.getVoteAverage()
                ))
                .collect(Collectors.toList());
    }


    // Get the top 10 highest-rated movies
    public List<MovieDTO> getTop10HighestRatedMovies() {

        MovieDAO movieDAO = new MovieDAO(emf);
        List<MovieDTO> allMovies = movieDAO.findAll();

        System.out.println("Top 10 highest-rated Movies");

        // Filter out movies where voteAverage is null
        return allMovies.stream()
                .filter(movie -> movie.getVoteAverage() != null)
                .sorted(Comparator.comparing(MovieDTO::getVoteAverage).reversed())
                .limit(10)
                .peek(movie -> System.out.println(
                        "Movie ID: " + movie.getId()
                                + ", Title: " + movie.getTitle()
                                + ", Vote Average: " + movie.getVoteAverage()
                ))
                .collect(Collectors.toList());

    }


    // Get the top 10 most popular movies
    public List<MovieDTO> getTop10MostPopularMovies () {
        MovieDAO movieDAO = new MovieDAO(emf);
        List<MovieDTO> allMovies = movieDAO.findAll();

        System.out.println("Top 10 highest-rated Movies");

        // Filter out movies where voteAverage is null
        return allMovies.stream()
                .filter(movie -> movie.getPopularity() != null)
                .sorted(Comparator.comparing(MovieDTO::getPopularity).reversed())
                .limit(10)
                .peek(movie -> System.out.println(
                        "Movie ID: " + movie.getId()
                                + ", Title: " + movie.getTitle()
                                + ", Popularity: " + movie.getPopularity()
                ))
                .collect(Collectors.toList());
    }



    public MovieDTO findGenreInASpecificMovie(String title)
    {
        MovieDAO movieDAO = new MovieDAO(emf);
        return movieDAO.findGenreInASpecificMovie(title);

    }





    // Search for a movie by title (case-insensitive)
//    public List<MovieDTO> getMovieByTitle (String title)
//    {
//        List<MovieDTO> movies = getAllMovies();
//
//        System.out.println("All movies with title " + title + ":");

//        allMovies.stream()
//                .filter(movie -> movie.getVoteAverage() != null)
//                .sorted(Comparator.comparing(MovieDTO::getVoteAverage).reversed())
//                .limit(10)
//                .peek(movie -> System.out.println(
//                        "Movie ID: " + movie.getId()
//                                + ", Title: " + movie.getTitle()
//                                + ", Vote Average: " + movie.getVoteAverage()
//                ))
//                .collect(Collectors.toList());


//
//       movies.stream()
//                .filter(movie -> movie.getTitle() != null).sorted(Comparator.comparing())
//                        .toLowerCase()
//
//                        .equals(title.toLowerCase()))
//                .anyMatch(movieDTO -> movieDTO.getTitle().equals(title))
//
//        return movies;
//    }


//
//    // Search for a movie by string (can match in title, genre, director)
//    public void searchForMovieByString (String s) {
//        List<MovieDTO> movies = getAllMovies();
//
//        System.out.println("All movies with:  " + s);
//
//        movies.stream()
//                .filter(movie -> movie.getTitle().toLowerCase().contains(s.toLowerCase())
//                        movie.getGenres().stream().anyMatch(genre -> genre.getName().toLowerCase().contains(s.toLowerCase()))
//                        movie.getDirector().getName().toLowerCase().contains(s.toLowerCase()))
//                .forEach(System.out::println);
//    }

        // public List<MovieDTO> getMovieByGenre (String genre) {
        //     List<MovieDTO> movies = MovieDAO.searchForMovieByGenre(genre);
        //
        //     System.out.println("All movies with genre " + genre + ":");
        //     movies.forEach(System.out::println);
        //     return movies;}













}
