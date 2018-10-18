package cn.com.niub.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import cn.com.niub.domain.Job;
import lombok.Data;

//工作
@Data
public class JobDto {

	private String id;
	private String orderId;
	
	private String zname;
	private String zshi;
	private String zqu;
	private String zlu;
	private String zhao;
	private String zyouBian;
	private String zdanWeiType;
	private String zdanWeiPhone;
	private String zdanWeiUrl;
	private String zzhiWei;
	private String zGongLing;
	private String zbuMen;
	private String zgongZi;
	private String zfaGongZiRi;
	private String zZhiFuFangShi;
	
	private String creater;
	private Date createTime;
	private String updater;
	private Date updateTime;
	
	public JobDto(){}
	
	public JobDto(Job job){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(job, this);
	}
}
