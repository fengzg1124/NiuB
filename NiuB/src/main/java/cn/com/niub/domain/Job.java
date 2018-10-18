package cn.com.niub.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import cn.com.niub.dto.JobDto;
import lombok.Data;

//工作
@Entity
@Data
@Table(name="TBL_JOB")
public class Job {

	@Id
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
	
	public Job(){}
	
	public Job(JobDto jobDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(jobDto, this);
	}
}
