package com.movieflix.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.movieflix.dto.MovieDTO;

public interface MovieService {
  MovieDTO addMovie(MovieDTO MovieDto, MultipartFile file) throws IOException;

  MovieDTO getMovie(Integer movieId);

  List<MovieDTO> getAllMovies();
}
