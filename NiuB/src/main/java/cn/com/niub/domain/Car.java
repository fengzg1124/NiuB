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

import cn.com.niub.dto.CarDto;
import lombok.Data;

//车
@Entity
@Data
@Table(name="TBL_CAR")
@EntityListeners(AuditingEntityListener.class)
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
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private String updater;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	public Car(){}
	
	public Car(CarDto carDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(carDto, this);
	}
}
