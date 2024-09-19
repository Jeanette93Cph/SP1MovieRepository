package dat;

import dat.services.FetchData;

public class Application {

    public static void main(String[] args) {

        //RUN THE APPLICATION
        System.out.println("TMBD API Application");

        FetchData f = new FetchData();

        f.fetchAllMovies();
        f.fetchAllActors();
        f.fetchAllDirectors();
        f.fetchAllGenres();
        f.fetchMoviesByGenre("genre");
        f.fetchMoviesByActor("actor name");
        f.fetchMoviesByDirector("director name");
        f.updateMovieByTitle("title");
        f.updateMovieByReleaseDate("1999-03-31");
        f.deleteMovieByTitle("title");
        f.deleteMovieByReleaseDate("1999-03-31");
        f.searchMovieByTitle("title");
        f.getAverageRating("title");
        f.getAverageRatingOfAllMovies();
        f.getTop10HighestRatedMovies();
        f.getTop10LowestRatedMovies();
        f.getTop10MostPopularMovies();

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