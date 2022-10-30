package com.atguigu.ggkt.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author shkstart
 * @create 2022-10-12 18:06
 */

public interface FileService {
//   文件上传
    String upload(MultipartFile file);
}
