package cn.com.niub.utils;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    //校验后台
    public static boolean extIsValid(String filename, String extArray) {
        // 声明要校验的后缀名数组
        String extStr = "rar,zip,doc,ppt,xls,pdf,PDF,docx,xlsx,jpg,jpeg,png,gif,bmp";
        if (!"".equals(extArray)) {
            extStr = extArray;
        }
        String exttype = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();

        return extStr.contains(exttype);
    }

    //保存附件
    public static void saveFile(MultipartFile file, String fileurl) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
            File outfile = new File(fileurl);
            FileCopyUtils.copy(bytes, outfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
