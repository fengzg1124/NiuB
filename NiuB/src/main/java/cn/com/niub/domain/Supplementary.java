package cn.com.niub.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import cn.com.niub.dto.SupplementaryDto;
import lombok.Data;

//补充材料
@Entity
@Data
@Table(name="TBL_SUPPLEMENTARY")
public class Supplementary {

	@Id
	private String id;
	private String orderId;
	
	private String bjingYingType;
	private String bgongShangHao;
	private String bchangSuo;
	private String byueZu;
	private String bchengLiDate;
	private String bshuiZhengNumber;
	private String brenShu;
	private String bnianYingLi;
	
	private String creater;
	private Date createTime;
	private String updater;
	private Date updateTime;
	
	public Supplementary(){}
	
	public Supplementary(SupplementaryDto supplementaryDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(supplementaryDto, this);
	}
}
