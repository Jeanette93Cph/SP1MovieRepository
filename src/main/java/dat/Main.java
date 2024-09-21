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
import dat.services.ActorService;
import dat.services.DirectorService;
import dat.services.GenreService;
import dat.services.MovieService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        //the_movie_db


        //Printing all danish movies for the recent 5 years in JSON
        String jsonAllMovies = MovieService.getAllMoviesJSON(1);
        // System.out.println(jsonAllMovies);
        //
        // Printing all danish movies for the recent 5 years as MovieDTO's
        List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(jsonAllMovies);
        // moviesDTOs.forEach(System.out::println);

        //persist movieList to database
        MovieDAO movieDAO = MovieDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
        movieDAO.persistListOfMovies(moviesDTOs);

        //persist one movie to database
//        MovieDTO movieDTO = new MovieDTO();
//        movieDTO.setId(222L);
//        movieDTO.setTitle("En rigtig god film");
//        MovieDAO.persistEntity(movieDTO);

        //find all movies in the database
        //MovieDAO movieDAO1 = MovieDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
        //movieDAO1.findAll().forEach(System.out::println);

        //find movie by id
       // System.out.println(MovieDAO.findEntity(545330L));

        //update movie
//        MovieDTO movieDTO1 = MovieDAO.findEntity(222L);
//        movieDTO1.setTitle("Mega dårlig film");
//        MovieDAO.updateEntity(movieDTO1, 222L);
//        System.out.println(MovieDAO.findEntity(222L));

        //delete movie
        //MovieDAO.removeEntity(222L);

        // /* ACTORS */
        // //Printing all cast from the danish movies for the recent 5 years
        //List<ActorDTO> actorDTOS = ActorService.getAllActorsFromJSON(1);
        //actorDTOS.forEach(System.out::println);

        //persist actorList to database
        //ActorDAO actorDAO = ActorDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
       //actorDAO.persistListOfActors(actorDTOS);

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


        /* DIRECTORS */
        //Printing all directors from the danish movies for the recent 5 years
        //List<DirectorDTO> allDirectorsDTO = DirectorService.getAllDirectorsFromJSON(1);
        //allDirectorsDTO.forEach(System.out::println);

        //persist directorList to database
        //DirectorDAO directorDAO = DirectorDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
        //directorDAO.persistListOfDirectors(allDirectorsDTO);

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


        /* GENRE */
        //Printing all genres
        //String allGenres = GenreService.getAllGenresJSON();
        //List<GenreDTO> genreDTOS = GenreDTO.convertToDTOFromJSONList(allGenres);

        //persist genreList to database
        //GenreDAO genreDAO = GenreDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
        //genreDAO.persistListOfGenres(genreDTOS);

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
}