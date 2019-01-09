package cn.com.niub.dto;

import cn.com.niub.domain.File;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class FileDto {

    private String id;
    private String type;//类型
    private String fileName;//附件名字
    private String fileUrl;//附件上传地址
    private String docmentName;//附件原本得名称
    private String mid;//主表id

    private String creater;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private String updater;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    private Integer delFlag;

    public FileDto(){}

    public FileDto(File file){
        //BeanUtils是org.springframework.beans.BeanUtils，前给后
        //BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
        if(null != file){
            BeanUtils.copyProperties(file, this);
        }
    }
}
