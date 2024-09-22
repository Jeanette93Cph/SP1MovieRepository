package dat.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    //Store genre_ids as a List<Long> since that's what the API provides
    @JsonProperty("genre_ids")
    private List<Long> genreIDs;

    @JsonProperty("cast")
    private List<CrewMemberDTO> crew;

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
        this.genreIDs = movie.getGenreIDs();

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

        for(ActorDTO actorDTO : this.actors)
        {
            Actor actor = new Actor(actorDTO);
            movie.addActor(actor);
        }


//        if (this.actors != null) {
//            for (ActorDTO actorDTO : this.actors) {
//                Actor actor = new ActorDTO();
//                actorDTO.setId(actor.getId());
//                actorDTO.setName(actor.getName());
//
//                // Add the actor to the movie using the helper method
//                movie.addActor(actor);
//            }
//        }

    }





}