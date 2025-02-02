package com.movieflix.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.dto.MovieDTO;
import com.movieflix.service.MovieService;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
   private final MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @PostMapping("/add-movie")
  public ResponseEntity<MovieDTO> addMovieHandle(
    @RequestBody MultipartFile file, 
    @RequestPart String movieDto
  ) throws IOException {
      MovieDTO dto = convertToMovieDTO(movieDto);
      return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
  }
  
  private MovieDTO convertToMovieDTO(String movieDtoObj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(movieDtoObj, MovieDTO.class);
  }

}
