package dat;

import dat.config.HibernateConfig;
import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.*;
import dat.entities.Movie;
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

        //fetchData.getAllMovies();

        //fetchData.getAllDirectors();

        //fetchData.getAllActors();

        //fetchData.getAllGenre();

//        Movie movie = new Movie();
//        movie.setTitle("God film");
//        movie.setId(333L);
//        fetchData.persistEntity(new MovieDTO(movie));

//        MovieDTO movieDTO = fetchData.findEntity(333L);
//        movieDTO.setTitle("Fantastisk film");
//        fetchData.updateEntity(movieDTO, 333L);

        //fetchData.removeEntity(333L);

        //fetchData.findDirectorInASpecificMovie("Boundless");

        //fetchData.findActorsInASpecificMovie("Boundless");

        //fetchData.findGenreInASpecificMovie("Boundless");

        //fetchData.findAllMoviesInASpecificGenre("Horror");

        //fetchData.getMoviesByTitle("bound");

        //fetchData.getAverageRatingOfAllMovies();

        //fetchData.getTop10LowestRatedMovies();

        //fetchData.getTop10HighestRatedMovies();

        //fetchData.getTop10MostPopularMovies();


    }

    public static void addDataToDatabase()
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db");

        List<DirectorDTO> allDirectorsDTO = DirectorService.getAllDirectorsFromJSON(1);
        DirectorDAO directorDAO = DirectorDAO.getInstance(emf);
        directorDAO.persistListOfDirectors(allDirectorsDTO);

        String allGenres = GenreService.getAllGenresJSON();
        List<GenreDTO> genreDTOS = GenreService.convertToDTOFromJSONList(allGenres);
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
        List<MovieDTO> moviesDTOs = MovieService.convertToDTOFromJSONList(jsonAllMovies);
        MovieDAO movieDAO = MovieDAO.getInstance(emf);
        movieDAO.persistListOfMovies(moviesDTOs);
    }




}