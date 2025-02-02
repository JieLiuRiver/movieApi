package com.movieflix.dto;

import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieDTO {

  private Integer movieId;

  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "Director is required")
  private String director;

  @NotBlank(message = "Studio is required")
  private String studio;

  private Set<String> movieCast;

  private Integer releaseYear;

  @NotBlank(message = "Poster is required")
  private String poster;

  @NotBlank(message = "Poster's url is required")
  private String posterUrl;
}
