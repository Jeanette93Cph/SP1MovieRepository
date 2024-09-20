package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	public GenreDTO(Genre genre) {
		this.id = genre.getId();
		this.name = genre.getName();
	}

	@Override
	public String toString() {
		return "GenreDTO{id=" + id + ", name='" + name + "'}";
	}

	public static List<GenreDTO> convertToDTOFromJSONList (String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, GenreDTO.class));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
