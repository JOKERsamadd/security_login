package cn.net.zhipeng.shop.controller;

import cn.net.zhipeng.entity.Msg;
import cn.net.zhipeng.utils.FastDFSClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    public static final String FILE_SERVER_URL = "http://192.168.25.133/";

    @PostMapping("/upload")
    public Msg upload(MultipartFile file) {
        FastDFSClient fastDFSClient = null;
        try {
            fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            String extName = StringUtils.substringAfter(file.getOriginalFilename(), ".");
            String fileId = fastDFSClient.uploadFile(file.getBytes(), extName);
            return Msg.success().add("imageUrl", FILE_SERVER_URL + fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Msg.fail("上传失败");
    }
}
