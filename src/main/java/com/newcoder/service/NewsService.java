package com.newcoder.service;

import com.newcoder.dao.NewsDAO;
import com.newcoder.model.News;
import com.newcoder.utils.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * Created by mzx on 17.4.6.
 */

@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLastedNews(int id, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(id, offset, limit);
    }


    public String saveFile(MultipartFile file) throws IOException {
        //检查文件类型
        int dopoint = file.getOriginalFilename().lastIndexOf('.');
        if (dopoint < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dopoint + 1);
        if (!ToutiaoUtil.isPictureAccepted(fileExt)) {
            return null;
        }
        //
        String filename = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        String path = ToutiaoUtil.FILE_PATH + filename;
        Files.copy(file.getInputStream(), new File(path).toPath(), StandardCopyOption.REPLACE_EXISTING);

        return ToutiaoUtil.APP_IMG_PATH + filename;
    }
}
