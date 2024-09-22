package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreDTO {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String name;

	private List<GenreDTO> genres;

	public GenreDTO (Genre genre) {
		this.id = genre.getGenre_id();
		this.name = genre.getName();
	}
}