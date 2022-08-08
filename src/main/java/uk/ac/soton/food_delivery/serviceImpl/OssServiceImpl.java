package uk.ac.soton.food_delivery.serviceImpl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.config.ConstantPropertiesUtils;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;
import uk.ac.soton.food_delivery.service.OssService;

import java.io.InputStream;

/**
 * @Author: ZhanX
 * @Date: 2020-12-28 22:56:50
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFile(String filePath, MultipartFile file, String style) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        try {
            // 创建OSS实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            int begin = file.getOriginalFilename().indexOf(".");
            int last = file.getOriginalFilename().length();
            //获得文件后缀名
            String extension = file.getOriginalFilename().substring(begin, last);
            filePath += extension;

            //调用oss方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName, filePath, inputStream);
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            return "https://oss.fd.shimonzhan.com/" + filePath + style;
        } catch (Exception e) {
            throw new ServicesException(ResultCode.FILE_UPLOAD_FAILED);
        }
    }
}
