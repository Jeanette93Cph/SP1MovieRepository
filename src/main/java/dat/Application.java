package dat;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.services.FetchData;
import jakarta.persistence.EntityManagerFactory;

public class Application {

    public static void main(String[] args) {

        //RUN THE APPLICATION
        System.out.println("TMBD API Application");

        // Create an EntityManagerFactory for the persistence unit
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db");

        FetchData f = new FetchData(emf);

        //f.fetchAllMovies();
        // f.fetchAllActors();
        // f.fetchAllDirectors();
        // f.fetchAllGenres();
        // f.fetchMoviesByGenre("genre");
        // f.fetchMoviesByActor("actor name");
        // f.fetchMoviesByDirector("director name");
        // f.updateMovieByTitle("title");
        // f.updateMovieByReleaseDate("1999-03-31");
        // f.deleteMovieByTitle("title");
        // f.deleteMovieByReleaseDate("1999-03-31");
        // f.searchMovieByTitle("title");
        // f.getAverageRating("title");
        // f.getAverageRatingOfAllMovies();
        // f.getTop10HighestRatedMovies();
        // f.getTop10LowestRatedMovies();
        // f.getTop10MostPopularMovies();

        // Close the EntityManagerFactory when done
        emf.close();

        //PERSISTING DATA TO DATABASE

        //------------------------------------------------------------/

        //ADDING MOVIES TO DATABASE
        /* MovieDAO movieDAO = MovieDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
        List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(MovieService.getAllMoviesJSON(1));
        for (MovieDTO moviesDTO : moviesDTOs) {
           movieDAO.persistEntity(moviesDTO);
        }  */

       //------------------------------------------------------------/

        //ADDING GENRES TO DATABASE
        /*    GenreDAO genreDAO = GenreDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
        List<GenreDTO> genreDTOS = GenreDTO.convertToDTOFromJSONList(GenreService.getAllGenresJSON());
        System.out.println(genreDTOS);

        for (GenreDTO genreDTO : genreDTOS) {
            genreDAO.persistEntity(genreDTO);
        } */

        //------------------------------------------------------------/


    }
}