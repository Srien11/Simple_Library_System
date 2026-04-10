package edu.cupk.simple_library_system.controller;

import edu.cupk.simple_library_system.common.PageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/update")
public class UploadController {

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/jpg");

    @PostMapping("/updateImg")
    public PageResponse<String> uploadImg(@RequestParam("file") MultipartFile file) {
        // 1. 检查文件是否为空
        if (file == null || file.isEmpty()) {
            return PageResponse.fail("文件不能为空");
        }

        // 2. 检查文件大小
        if (file.getSize() > MAX_FILE_SIZE) {
            return PageResponse.fail("文件大小超过10MB限制");
        }

        // 3. 检查文件类型
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            return PageResponse.fail("仅支持 JPG、PNG 格式的图片");
        }

        // 4. 获取文件扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // 5. 生成新的文件名（添加时间戳防止重复）
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String newFileName = dateStr + "_" + UUID.randomUUID().toString().substring(0, 8) + extension;

        // 6. 确保上传目录存在
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            return PageResponse.fail("创建上传目录失败");
        }

        // 7. 保存文件
        Path targetPath = uploadPath.resolve(newFileName);
        try {
            file.transferTo(targetPath.toFile());
        } catch (IOException e) {
            return PageResponse.fail("文件保存失败");
        }

        // 8. 返回访问路径（单个字符串，非数组）
        String fileUrl = "/upload/" + newFileName;
        return PageResponse.success(0, fileUrl);
    }
}
