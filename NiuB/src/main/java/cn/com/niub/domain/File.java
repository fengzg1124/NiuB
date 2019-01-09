package cn.com.niub.domain;

import cn.com.niub.dto.FileDto;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="TBL_FILE")
@EntityListeners(AuditingEntityListener.class)
public class File {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid.hex")
    private String id;
    private String type;//类型
    private String fileName;//附件名字
    private String fileUrl;//附件上传地址
    private String docmentName;//附件原本得名称
    private String mid;//主表id
    private String creater;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    private String updater;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    private Integer delFlag;

    public File(){}

    public File(FileDto fileDto){
        //BeanUtils是org.springframework.beans.BeanUtils，前给后
        //BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
        BeanUtils.copyProperties(fileDto, this);
    }
}
