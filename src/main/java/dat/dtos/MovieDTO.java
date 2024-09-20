package dat.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("genres")
    private List<GenreDTO> genres;

    @JsonProperty("actors")
    private List<ActorDTO> actors;

    @JsonProperty("directors")
    private DirectorDTO director;

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.originalLanguage = movie.getOriginalLanguage();
        this.releaseDate = movie.getReleaseDate();
        this.popularity = movie.getPopularity();
        this.voteAverage = movie.getVoteAverage();

    }

    public static List<MovieDTO> convertToDTOFromJSONList (String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, MovieDTO.class));
        } catch (JsonProcessingException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // @Override
    // public String toString() {
    //     return String.format(
    //             "%-15s | %-40s | %-25s | %-30s | %-22s | %-22s",
    //             "Movie id: " + id,
    //             "Title: " + title,
    //             "Original Language: " + originalLanguage,
    //             "Release Date: " + releaseDate,
    //             "Popularity: " + popularity,
    //             "Vote Average: " + voteAverage
    //     );
    // }
}