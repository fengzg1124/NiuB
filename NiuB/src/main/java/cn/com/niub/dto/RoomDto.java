package cn.com.niub.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import cn.com.niub.domain.Room;
import lombok.Data;

@Data
public class RoomDto {

	private String id;
	private String orderId;
	
	private String ffangChanType;
	private String fzunJin;
	private String ffangZheng;
	private String fmianJi;
	private String fjiaGe;
	private String fjianZhuNianFen;
	private String fanJieYinHang;
	private String fdaiKuanNianXian;
	private String fyueGong;
	private String fdaiKuanZongE;
	private String fqianKuan;
	
	private String creater;
	private Date createTime;
	private String updater;
	private Date updateTime;
	
	public RoomDto(){}
	
	public RoomDto(Room room){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		if(null != room){
			BeanUtils.copyProperties(room, this);
		}
	}
}
