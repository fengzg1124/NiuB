package cn.com.niub.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import cn.com.niub.dto.SpouseDto;
import lombok.Data;

//配偶
@Entity
@Data
@Table(name="TBL_SPOUSE")
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
	private Date createTime;
	private String updater;
	private Date updateTime;
	
	public Spouse(){}
	
	public Spouse(SpouseDto spouseDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(spouseDto, this);
	}
}
