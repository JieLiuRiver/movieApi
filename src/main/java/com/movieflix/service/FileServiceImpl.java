package com.movieflix.service;

// 导入必要的IO和NIO类库
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

// Spring框架相关导入
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

/**
 * 文件服务实现类，实现文件上传和资源获取的核心逻辑
 * 
 * @Service 注解表明这是一个Spring服务层组件
 * 作用：1. 声明为Spring管理的Bean 2. 启用组件扫描时自动被识别 3. 通常用于业务逻辑处理
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * 配置文件中的海报存储路径（通过@Value注解注入）
     * 
     * @Value 注解说明：
     * 1. 从application.properties/yml中读取"project.poster"配置项的值
     * 2. 实现配置与代码分离，便于不同环境切换配置
     * 3. 支持动态配置，修改配置文件无需重新编译
     */
    @Value("${project.poster}")
    private String posterPath;

    /**
     * 文件上传方法实现
     * @param path 自定义存储路径（可选），若为空则使用配置的默认路径
     * @param file Spring封装的MultipartFile对象，包含上传文件的所有元数据
     * @return 上传后的文件名
     * @throws IOException 可能发生的IO异常（由调用方处理）
     * 
     * 方法逻辑说明：
     * 1. 路径处理：优先使用传入路径，否则使用配置路径
     * 2. 目录创建：确保目标目录存在（包含多级目录创建）
     * 3. 文件保存：使用NIO的Files.copy进行高效文件操作
     */
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // 路径优先级处理：参数路径 > 配置路径
        if (path == null || path.isEmpty()) {
            path = posterPath;
        }

        // 创建目标目录（包含父目录）
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); // 创建多级目录
        }

        // 获取原始文件名（包含扩展名）
        String fileName = file.getOriginalFilename();

        // 构建完整存储路径
        String filePath = path + File.separator + fileName;

        // 使用NIO进行文件复制（REPLACE_EXISTING表示覆盖同名文件）
        Files.copy(
            file.getInputStream(), // 上传文件的输入流
            Paths.get(filePath),   // 目标路径
            StandardCopyOption.REPLACE_EXISTING // 覆盖策略
        );

        return fileName;
    }

    /**
     * 获取资源文件的输入流
     * @param path 文件存储路径
     * @param filename 目标文件名
     * @return 文件输入流
     * @throws FileNotFoundException 当文件不存在时抛出
     * 
     * 方法说明：
     * 1. 主要用于读取已存储的文件内容
     * 2. 提供文件存在性检查机制
     * 3. 返回InputStream以便后续流式处理
     */
    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
        // 构建完整文件路径
        String filePath = path + File.separator + filename;
        File file = new File(filePath);

        // 显式文件存在检查
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        // 返回文件输入流（调用方需负责关闭流）
        return new FileInputStream(file);
    }
}