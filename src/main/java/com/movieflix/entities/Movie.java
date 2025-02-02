package com.movieflix.entities;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer movieId;

  @Column(nullable = false)
  @NotBlank(message = "Title is required")
  private String title;

  @Column(nullable = false)
  @NotBlank(message = "Director is required")
  private String director;

  @Column(nullable = false)
  @NotBlank(message = "Audio is required")
  private String audio;

  @ElementCollection
  @CollectionTable(name = "movie_cast")
  private Set<String> movieCast;

  @Column(nullable = false)
  @NotBlank(message = "Release year is required")
  private Integer releaseYear;

  @Column(nullable = false)
  @NotBlank(message = "Poster year is required")
  private String poster;
}
