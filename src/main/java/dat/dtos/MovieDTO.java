package dat.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.daos.GenreDAO;
import dat.entities.Actor;
import dat.entities.Genre;
import dat.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {

    //used in method MovieDTO.convertToDTOFromJSONList
    private GenreDAO genreDAO;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("genre_ids")
    private List<Long> genreIDs;

    @JsonProperty("genres")
    private List<GenreDTO> genres;

    @JsonProperty("actors")
    private List<ActorDTO> actors;

    @JsonProperty("director")
    private DirectorDTO director;

    public MovieDTO (Movie movie)
    {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.originalLanguage = movie.getOriginalLanguage();
        this.releaseDate = movie.getReleaseDate();
        this.popularity = movie.getPopularity();
        this.voteAverage = movie.getVoteAverage();

        if (movie.getDirector() != null) {
            this.director = new DirectorDTO(movie.getDirector());
        } else {
            this.director = null;
        }

        // Initialize genres and actors based on the Movie entity

        this.genres = new ArrayList<>();
        for (Genre genre : movie.getGenres()) {
            this.genres.add(new GenreDTO(genre));
        }

        // Initialize actors
        this.actors = new ArrayList<>();
        for (Actor actor : movie.getActors()) {
            this.actors.add(new ActorDTO(actor));
        }
    }


    // convert from JSON to List of MovieDTO
    public List<MovieDTO> convertToDTOFromJSONList(String json, GenreDAO genreDAO) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        this.genreDAO = genreDAO;

        try {
            MovieResponseDTO movieResponseDTO = objectMapper.readValue(json, MovieResponseDTO.class);
            List<MovieDTO> movieDTOList = movieResponseDTO.getMovieList();

            //List to hold converted movie entites
            List<Movie> movies = new ArrayList<>();

            // Loop through each MovieDTO and convert to Movie entity
            for(MovieDTO movieDTO : movieDTOList)
            {
                Movie movie = new Movie(movieDTO);

                // Fetch genres using genreIds via GenreDAO
                if (movieDTO.getGenreIDs() != null && !movieDTO.getGenreIDs().isEmpty()) {
                    List<Genre> genres = genreDAO.findByIds(movieDTO.getGenreIDs());
                    // Set the fetched genres in the movie
                    movie.setGenres(genres);
                }
                movies.add(movie);
            }

            return  movieDTOList;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}