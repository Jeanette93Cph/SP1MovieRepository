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

public class Application {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db");
        FetchData f = new FetchData(emf);

        System.out.println("THE MOVIE DB API APPLICATION");

        //f.fetchAllActors();
        //f.fetchAllMovies();
        //f.getAverageRatingOfAllMovies();
        //f.getAverageRating("The Promised Land");
        //f.getTop10MostPopularMovies();
        //f.getTop10HighestRatedMovies();
        //f.getTop10LowestRatedMovies();

        //populateDatabase();
//-------------------------/

        //persist one movie to database
//        MovieDTO movieDTO = new MovieDTO();
//        movieDTO.setId(222L);
//        movieDTO.setTitle("En rigtig god film");
//        MovieDAO.persistEntity(movieDTO);

        //find movie by id
       // System.out.println(MovieDAO.findEntity(545330L));

        //update movie
//        MovieDTO movieDTO1 = MovieDAO.findEntity(222L);
//        movieDTO1.setTitle("Mega dårlig film");
//        MovieDAO.updateEntity(movieDTO1, 222L);
//        System.out.println(MovieDAO.findEntity(222L));

        //delete movie
        //MovieDAO.removeEntity(222L);

        //persist one actor to database
//        ActorDTO actorDTO = new ActorDTO();
//        actorDTO.setId(222L);
//        actorDTO.setName("Jørgen Jørgensen");
//        ActorDAO.persistEntity(actorDTO);

        //find all actors in the database
        //ActorDAO.findAll().forEach(System.out::println);

        //find actor by id
        //System.out.println(ActorDAO.findEntity(2383415L));

        //update actor
//        ActorDTO actorDTO1 = ActorDAO.findEntity(222L);
//        actorDTO1.setName("Morten Jørgensen");
//        ActorDAO.updateEntity(actorDTO1, 222L);
//        System.out.println(ActorDAO.findEntity(222L));

        //delete actor
        //ActorDAO.removeEntity(222L);


        //persist one director to database
        //DirectorDTO directorDTO = new DirectorDTO();
        //directorDTO.setName("Hans Hansen");
        //directorDTO.setId(333L);
        //directorDAO.persistEntity(directorDTO);

       //find all directors in the database
        //DirectorDAO.findAll().forEach(System.out::println);

        //find director by id
        //System.out.println(DirectorDAO.findEntity(558205L));

        //update director
//        DirectorDTO directorDTO1 = DirectorDAO.findEntity(558205L);
//        directorDTO1.setName("Julie Sørensen");
//        DirectorDAO.updateEntity(directorDTO1, 558205L);
//        System.out.println(DirectorDAO.findEntity(558205L));

        //delete director
       //DirectorDAO.removeEntity(333L);

        //persist one genre to database
//        GenreDTO genreDTO = new GenreDTO();
//        genreDTO.setName("Bedste genre");
//        genreDTO.setId(333L);
//        genreDAO.persistEntity(genreDTO);

        //find all genres in the database
        //GenreDAO.findAll().forEach(System.out::println);

        //find genre by id
        //System.out.println(GenreDAO.findEntity(36L));

        //update genre
//        GenreDTO genreDTO1 = GenreDAO.findEntity(333L);
//        genreDTO1.setName("Fantastisk genre");
//        GenreDAO.updateEntity(genreDTO1, 333L);
//        System.out.println(GenreDAO.findEntity(333L));

        //delete genre
        //GenreDAO.removeEntity(333L);

    }

    private static void populateDatabase (EntityManagerFactory emf) {

        //persist directors to database
        List<DirectorDTO> allDirectorsDTO = DirectorService.getAllDirectorsFromJSON(1); //fetch all data from the movie db api
        DirectorDAO directorDAO = DirectorDAO.getInstance(emf);
        directorDAO.persistListOfDirectors(allDirectorsDTO);

        //persist genres to database
        String allGenres = GenreService.getAllGenresJSON();
        List<GenreDTO> genreDTOS = GenreDTO.convertToDTOFromJSONList(allGenres);
        GenreDAO genreDAO = GenreDAO.getInstance(emf);
        genreDAO.persistListOfGenres(genreDTOS);

        //persist actors to database
        List<ActorDTO> actorDTOS = ActorService.getAllActorsFromJSON(1);
        ActorDAO actorDAO = ActorDAO.getInstance(emf);
        actorDAO.persistListOfActors(actorDTOS);

        //persist movieList to database
        String jsonAllMovies = MovieService.getAllMoviesJSON(1);
        List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(jsonAllMovies);
        MovieDAO movieDAO = MovieDAO.getInstance(emf);
        movieDAO.persistListOfMovies(moviesDTOs);
    }