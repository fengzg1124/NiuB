package cn.com.niub.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import cn.com.niub.dto.OrderDto;
import lombok.Data;

@Entity
@Data
@Table(name="TBL_ORDER")
@EntityListeners(AuditingEntityListener.class)
public class Order {

	@Id
	@GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid.hex")
	private String id;
	private String daiKuanYongTu;
	private String daikuanType;
	private String jieKuanSum;
	//private String detailId;
	
	//基本信息
	private String name;
	private String sex;
	private Date chuShengRiQi;
	private String age;
	private String icard;
	private String jiGuan;
	private String diZhi;
	private String hunYin;
	private String xueLi;
	private String sheBao;
	private String phone;
	private String zuoJi;
	
	private String creater;
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private String updater;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	private Integer delFlag;
	private String status;
	
	//房
	
	//车
	
	//职业
	
	//补充材料
	
	//配偶信息
	
	//联系人
	public Order(){}
	
	public Order(OrderDto orderDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(orderDto, this);
	}
}
