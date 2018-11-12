package cn.com.niub.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import cn.com.niub.domain.Contacts;
import lombok.Data;

//联系人
@Data
public class ContactsDto {

	private String id;
	private String orderId;
	
	private String lname;
	private String lguanXi;
	private String licard;
	private String lshi;
	private String lqu;
	private String llu;
	private String lhao;
	private String lphone;
	private String ldwName;
	private String ldwPhone;
	private String lzhiWei;
	
	private String creater;
	private Date createTime;
	private String updater;
	private Date updateTime;
	
	public ContactsDto(){}
	
	public ContactsDto(Contacts contacts){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		if(null != contacts){
			BeanUtils.copyProperties(contacts, this);
		}
	}
}
