package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
		this.id = genre.getId();
		this.name = genre.getName();
	}

	// convert from JSON to List of ActorDTO
	public static List<GenreDTO> convertToDTOFromJSONList (String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		// enabling it to properly serialize / deserialize date and time like LocalDate
		objectMapper.registerModule(new JavaTimeModule());

		try {
			GenreDTO genreDTO = objectMapper.readValue(json, GenreDTO.class);
			return genreDTO.genres;
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error converting JSON to GenreDTO: " + e.getMessage());
		}
	}
}