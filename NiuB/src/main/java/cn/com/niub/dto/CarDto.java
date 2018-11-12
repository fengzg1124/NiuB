package cn.com.niub.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import cn.com.niub.domain.Car;
import lombok.Data;

//车
@Data
public class CarDto {

	private String id;
	private String orderId;
	
	private String cpinPai;
	private String cshuLiang;
	private String chaoMa;
	private String cnianFei;
	private String cgouRuFangShi;
	
	private String creater;
	private Date createTime;
	private String updater;
	private Date updateTime;
	
	public CarDto(){}
	
	public CarDto(Car car){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		if(null != car){
			BeanUtils.copyProperties(car, this);
		}
	}
}
