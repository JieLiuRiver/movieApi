package com.movieflix.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movieflix.dto.MovieDTO;
import com.movieflix.entities.Movie;
import com.movieflix.repositories.MovieRepository;

/**
 * MovieService 接口的实现类
 * 负责处理电影相关的业务逻辑
 */
@Service
public class MovieServiceImpl implements MovieService {

  /** 电影数据访问接口 */
  private final MovieRepository movieRepository;
  
  /** 文件服务接口 */
  private final FileService fileService;

  /** 海报文件存储路径，从配置文件中注入 */
  @Value("${project.poster}")
  private String path;

  /** 基础URL，用于生成海报访问链接，从配置文件中注入 */
  @Value("${base.url}")
  private String baseUrl;

  /**
   * 构造函数，通过依赖注入初始化
   * @param movieRepository 电影数据访问接口
   * @param fileService 文件服务接口
   */
  public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
    this.movieRepository = movieRepository;
    this.fileService = fileService;
  }

  /**
   * 添加新电影
   * @param movieDto 电影数据传输对象
   * @param file 电影海报文件
   * @return 包含完整信息的MovieDTO对象
   * @throws IOException 文件上传失败时抛出
   */
  @Override
  public MovieDTO addMovie(MovieDTO movieDto, MultipartFile file) throws IOException {
    // 上传海报文件
    String uploadedFileName = fileService.uploadFile(path, file);
    movieDto.setPoster(uploadedFileName);
    
    // 创建Movie实体对象
    Movie movie = new Movie(
      null,  // ID由数据库自动生成
      movieDto.getTitle(),
      movieDto.getDirector(),
      movieDto.getStudio(),
      movieDto.getMovieCast(),
      movieDto.getReleaseYear(),
      movieDto.getPoster()
    );
    
    // 保存到数据库
    Movie savedMovie = movieRepository.save(movie);
    
    // 生成海报访问URL
    String posterUrl = baseUrl + "/file/" + uploadedFileName;
    
    // 构建返回的DTO对象
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

  /**
   * 根据ID获取电影信息
   * @param movieId 电影ID
   * @return MovieDTO对象
   */
  @Override
  public MovieDTO getMovie(Integer movieId) {
    // TODO: 实现根据ID获取电影信息的逻辑
    throw new UnsupportedOperationException("Unimplemented method 'getMovie'");
  }

  /**
   * 获取所有电影信息
   * @return 包含所有电影的列表
   */
  @Override
  public List<MovieDTO> getAllMovies() {
    // TODO: 实现获取所有电影信息的逻辑
    throw new UnsupportedOperationException("Unimplemented method 'getAllMovies'");
  }
  
}
