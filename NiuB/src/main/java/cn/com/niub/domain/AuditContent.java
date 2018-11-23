package cn.com.niub.domain;

import cn.com.niub.dto.AuditContentDto;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name="TBL_AUDIT_CONTENT")
@EntityListeners(AuditingEntityListener.class)
public class AuditContent implements Serializable {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid.hex")
    private String id;

    private String orderId;
    private String status;
    private String content;

    private String creater;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    private String updater;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public AuditContent(){}

    public AuditContent(AuditContentDto auditContentDto){
        //BeanUtils是org.springframework.beans.BeanUtils，前给后
        //BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
        if(null != auditContentDto){
            BeanUtils.copyProperties(auditContentDto, this);
        }
    }
}
