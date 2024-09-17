package dat.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "movies")
public class Movie
{
    @Id
    @Column(name = "id")
    private Long id;  //USe the API ID as the primary key

    private String title;

    private String originalLanguage;

    private String releaseDate;

    private double voteAverage;


    @ManyToMany
    @JoinTable
    (
    name = "movie_genre",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;


    @ManyToMany
    @JoinTable
    (
    name = "movie_actor",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;


    @ManyToMany(mappedBy = "movies")
    @JoinTable
    (
    name = "movie_director",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private List<Director> directors;


}
