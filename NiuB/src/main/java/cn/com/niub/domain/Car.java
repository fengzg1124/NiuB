package cn.com.niub.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import cn.com.niub.dto.CarDto;
import lombok.Data;

//车
@Entity
@Data
@Table(name="TBL_CAR")
public class Car {

	@Id
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
	
	public Car(){}
	
	public Car(CarDto carDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(carDto, this);
	}
}
