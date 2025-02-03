package com.movieflix.controllers;

// Spring框架相关导入
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// JSON处理相关
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 业务逻辑相关
import com.movieflix.dto.MovieDTO;
import com.movieflix.service.MovieService;

// IO与响应处理
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 电影资源控制器（REST API端点）
 * 
 * @RestController 组合注解，包含：
 * 1. @Controller：标识为Spring MVC控制器
 * 2. @ResponseBody：自动将返回对象序列化为JSON
 * 
 * @RequestMapping 定义基础请求路径
 * 作用：将该控制器的所有端点路径前缀设置为/api/v1/movie
 */
@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
   // 依赖注入电影服务组件（通过构造器注入）
   private final MovieService movieService;

  /**
   * 构造器依赖注入
   * @param movieService 自动注入的MovieService实例
   * Spring会自动查找匹配类型的Bean进行注入
   */
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  /**
   * 新增电影接口
   * @param file 上传的电影文件（海报或其他资源）
   * @param movieDto 电影元数据的JSON字符串
   * @return 包含创建结果的响应实体
   * @throws IOException 可能发生的IO异常
   * 
   * @PostMapping 映射HTTP POST请求到/add-movie路径
   * 组合路径为：/api/v1/movie/add-movie
   * 
   * 参数注解说明：
   * @RequestBody 用于接收请求体内容（适用于非文件字段）
   * @RequestPart 用于处理multipart/form-data请求中的部件
   * 
   * 设计要点：
   * 1. 使用ResponseEntity封装响应状态和内容
   * 2. HttpStatus.CREATED(201)表示资源创建成功
   */
  @PostMapping("/add-movie")
  public ResponseEntity<MovieDTO> addMovieHandle(
    @RequestBody MultipartFile file,      // 可能需要调整注解为@RequestPart
    @RequestPart String movieDto
  ) throws IOException {
      // 将JSON字符串转换为DTO对象
      MovieDTO dto = convertToMovieDTO(movieDto);
      
      // 调用服务层并返回创建响应
      return new ResponseEntity<>(
          movieService.addMovie(dto, file), 
          HttpStatus.CREATED
      );
  }
  
  /**
   * JSON字符串到DTO对象的转换方法
   * @param movieDtoObj JSON格式的字符串
   * @return 反序列化的MovieDTO对象
   * @throws JsonProcessingException JSON解析异常
   * 
   * 使用说明：
   * 1. ObjectMapper是Jackson库的核心类
   * 2. readValue方法执行反序列化操作
   * 3. 需要确保JSON结构与DTO类定义匹配
   */
  private MovieDTO convertToMovieDTO(String movieDtoObj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(movieDtoObj, MovieDTO.class);
  }
}