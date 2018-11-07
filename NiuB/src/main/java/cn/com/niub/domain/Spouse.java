package cn.com.niub.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import cn.com.niub.dto.SpouseDto;
import lombok.Data;

//配偶
@Entity
@Data
@Table(name="TBL_SPOUSE")
@EntityListeners(AuditingEntityListener.class)
public class Spouse {

	@Id
	private String id;
	private String orderId;
	
	private String pname;
	private String pphone;
	private String pshouRu;
	private String piCard;
	private String pdanWeiName;
	private String pzhiwei;
	private String pdwshi;
	private String pdwqu;
	private String pdwlu;
	private String pdwhao;
	private String pdwPhone;
	
	private String creater;
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private String updater;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	public Spouse(){}
	
	public Spouse(SpouseDto spouseDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(spouseDto, this);
	}
}
