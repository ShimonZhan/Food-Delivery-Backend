package uk.ac.soton.food_delivery.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ZhanX
 * @Date: 2020-12-29 12:04:58
 */
public interface OssService {

    //上传头像到oss
    String uploadFile(String filePath, MultipartFile file, String style);
}
