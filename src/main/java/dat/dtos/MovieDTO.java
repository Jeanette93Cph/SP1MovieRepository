package dat.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;

    @JsonProperty("rating")
    private Double rating;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("vote_average")
    private double voteAverage;

    @JsonProperty("genres")
    private List<GenreDTO> genres;

    @JsonProperty("actors")
    private List<ActorDTO> actors;

    @JsonProperty("directors")
    private List<DirectorDTO> directors;

    public MovieDTO (Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.originalLanguage = movie.getOriginalLanguage();
        this.releaseDate = movie.getReleaseDate().toString();
        this.rating = movie.getRating();
        this.popularity = movie.getPopularity();
        this.voteAverage = movie.getVoteAverage();
        this.genres = new MovieDTO(movie).getGenres();
        this.actors = new MovieDTO(movie).getActors();
        this.directors = new MovieDTO(movie).getDirectors();
    }

    // convert from JSON to List of MovieDTO
    public static List<MovieDTO> convertToDTOFromJSONList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            MovieResponseDTO movieResponseDTO = objectMapper.readValue(json, MovieResponseDTO.class);
            return movieResponseDTO.getMovieList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}