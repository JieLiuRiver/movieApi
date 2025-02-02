package com.movieflix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

  // public Movie findByTitle(String title);
  // public List<Movie> findByDirector(String director);
  // public List<Movie> findByReleaseYear(Integer releaseYear);
  // public List<Movie> findByAudio(String audio);
  // public List<Movie> findByMovieCast(String movieCast);

}
