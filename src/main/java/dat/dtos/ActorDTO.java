package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.entities.Actor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	public String name;

	@JsonProperty("known_for_department")
	public String department;

	@JsonProperty("character")
	public String character;

	@JsonProperty("popularity")
	public Double popularity;

	@ToString.Exclude
	public List<ActorDTO> actors;

	public ActorDTO (Actor actor) {
		this.name = actor.getName();;
		this.department = actor.getDepartment();
		this.character = actor.getCharacter();
		this.popularity = actor.getPopularity();
	}

	// convert from JSON to List of ActorDTO
	public static List<ActorDTO> convertToDTOFromJSONList (String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		// enabling it to properly serialize / deserialize date and time like LocalDate
		objectMapper.registerModule(new JavaTimeModule());

		try {
			return objectMapper.readValue(json, new TypeReference<List<ActorDTO>>() {});
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to convert JSON to ActorDTO list.");
		}
	}

	// // help method to convertToDTOFromJSONList()
	// private static String convertPlainStringToJSONArray (String plainString) {
	// 	return Arrays.stream(plainString.split(",\\s*"))
	// 			.map(name -> String.format("{\"name\": \"%s\"}", name.trim()))
	// 			.collect(Collectors.joining(",", "[", "]"));
	// }
}