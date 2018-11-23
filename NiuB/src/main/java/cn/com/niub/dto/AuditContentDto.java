package cn.com.niub.dto;

import cn.com.niub.domain.AuditContent;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AuditContentDto {

    private String id;
    private String orderId;
    private String status;
    private String content;

    private String creater;
    private Date createTime;
    private String updater;
    private Date updateTime;

    public AuditContentDto(){}

    public AuditContentDto(AuditContent auditContent){
        //BeanUtils是org.springframework.beans.BeanUtils，前给后
        //BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
        if(null != auditContent){
            BeanUtils.copyProperties(auditContent, this);
        }
    }

}
