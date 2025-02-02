package com.movieflix.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movieflix.dto.MovieDTO;
import com.movieflix.entities.Movie;
import com.movieflix.repositories.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

  private final MovieRepository movieRepository;
  
  private final FileService fileService;

  @Value("${project.poster}")
  private String path;

  @Value("${base.url}")
  private String baseUrl;

  public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
    this.movieRepository = movieRepository;
    this.fileService = fileService;
  }

  @Override
  public MovieDTO addMovie(MovieDTO movieDto, MultipartFile file) throws IOException {
    String uploadedFileName = fileService.uploadFile(path, file);
    movieDto.setPoster(uploadedFileName);
    Movie movie = new Movie(
      null,
      movieDto.getTitle(),
      movieDto.getDirector(),
      movieDto.getStudio(),
      movieDto.getMovieCast(),
      movieDto.getReleaseYear(),
      movieDto.getPoster()
    );
    Movie savedMovie = movieRepository.save(movie);
    String posterUrl = baseUrl + "/file/" + uploadedFileName;
    MovieDTO response = new MovieDTO(
      savedMovie.getMovieId(),
      savedMovie.getTitle(),
      savedMovie.getDirector(),
      savedMovie.getStudio(),
      savedMovie.getMovieCast(),
      savedMovie.getReleaseYear(),
      savedMovie.getPoster(),
      posterUrl
    );
    
    return response;
  }

  @Override
  public MovieDTO getMovie(Integer movieId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getMovie'");
  }

  @Override
  public List<MovieDTO> getAllMovies() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllMovies'");
  }
  
}
