package dat.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/* this class is used instead of MovieDTO for converting,
because the JSON response contains more data (pages, totalresults) beside the list of movies. */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResponseDTO {

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_results")
    private Integer totalResults;

    @JsonProperty("results")
    private List<MovieDTO> movieList;
}