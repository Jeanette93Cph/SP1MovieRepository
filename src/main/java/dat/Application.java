package dat;

import dat.services.FetchData;

public class Application {

    public static void main(String[] args) {

        //RUN THE APPLICATION
        System.out.println("TMBD API Application");

        FetchData.fetchAllMovies();
        // FetchData.fetchAllActors();
        //FetchData.fetchAllDirectors();
        //FetchData.fetchAllGenres();
        //FetchData.fetchMoviesByGenre("genre");
        //FetchData.fetchMoviesByActor("actor name");
        //FetchData.fetchMoviesByDirector("director name");
        //FetchData.updateMovieByTitle("title");
        //FetchData.updateMovieByReleaseDate("1999-03-31");
        //FetchData.deleteMovieByTitle("title");
        //FetchData.deleteMovieByReleaseDate("1999-03-31");
        //FetchData.searchMovieByTitle("title");
        //FetchData.getAverageRating("title");
        //FetchData.getAverageRatingOfAllMovies();
        //FetchData.getTop10HighestRatedMovies();
        //FetchData.getTop10LowestRatedMovies();
        //FetchData.getTop10MostPopularMovies();


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