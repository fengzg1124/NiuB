package cn.com.niub.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import cn.com.niub.domain.Room;
import cn.com.niub.domain.Spouse;
import lombok.Data;

//配偶
@Data
public class SpouseDto {

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
	
	public SpouseDto(){}
	
	public SpouseDto(Spouse spouse){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		if(null != spouse){
			BeanUtils.copyProperties(spouse, this);
		}
	}
}
