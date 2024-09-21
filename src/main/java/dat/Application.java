package dat;

import dat.config.HibernateConfig;
import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.services.*;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Application
{
    public static void main(String[] args)
    {

        //the_movie_db

        //addDataToDatabase();

        //addMoviesToDatabase();

        FetchData fetchData = new FetchData();

        //fetchData.getAverageRatingOfAllMovies();

        //fetchData.getAllGenres();

        //fetchData.getAllDirectors();

        //fetchData.getTop10LowestRatedMovies();

        //fetchData.getTop10HighestRatedMovies();

        //fetchData.getTop10MostPopularMovies();

        //fetchData.getMovieByTitle("Boundless");

        fetchData.findGenreInASpecificMovie("Boundless");

    }

    public static void addDataToDatabase()
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db");

        List<DirectorDTO> allDirectorsDTO = DirectorService.getAllDirectorsFromJSON(1);
        DirectorDAO directorDAO = DirectorDAO.getInstance(emf);
        directorDAO.persistListOfDirectors(allDirectorsDTO);

        String allGenres = GenreService.getAllGenresJSON();
        List<GenreDTO> genreDTOS = GenreDTO.convertToDTOFromJSONList(allGenres);
        GenreDAO genreDAO = GenreDAO.getInstance(emf);
        genreDAO.persistListOfGenres(genreDTOS);

        List<ActorDTO> actorDTOS = ActorService.getAllActorsFromJSON(1);
        ActorDAO actorDAO = ActorDAO.getInstance(emf);
        actorDAO.persistListOfActors(actorDTOS);

    }


    public static void addMoviesToDatabase()
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db");

        String jsonAllMovies = MovieService.getAllMoviesJSON(1);
        List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(jsonAllMovies);
        MovieDAO movieDAO = MovieDAO.getInstance(emf);
        movieDAO.persistListOfMovies(moviesDTOs);
    }




}