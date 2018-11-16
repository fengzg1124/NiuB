package cn.com.niub.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.Order;
import cn.com.niub.dto.CarDto;
import cn.com.niub.dto.ContactsDto;
import cn.com.niub.dto.JobDto;
import cn.com.niub.dto.OrderDto;
import cn.com.niub.dto.RoomDto;
import cn.com.niub.dto.SpouseDto;
import cn.com.niub.dto.SupplementaryDto;
import cn.com.niub.jpa.OrderRepository;
import cn.com.niub.utils.AbleStatus;
import cn.com.niub.utils.Pagination;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	RoomService roomService;
	@Autowired
	CarService carService;
	@Autowired
	JobService jobService;
	@Autowired
	SpouseService spouseService;
	@Autowired
	SupplementaryService supplementaryService;
	@Autowired
	ContactsService contactsService;
	
	public OrderDto findOrderById(String id){
		return new OrderDto(orderRepository.findOne(id));
	}
	
	public OrderDto findOrderAllById(String id){
		OrderDto dto = new OrderDto(orderRepository.findOne(id));
		//房
		RoomDto room = roomService.findRoomById(id);
		//车
		CarDto car = carService.findCarById(id);
		//职业
		JobDto job = jobService.findJobById(id);
		//补充材料
		SupplementaryDto supplementary = supplementaryService.findSupplementaryById(id);
		//配偶信息
		SpouseDto spouse = spouseService.findSpouseById(id);
		//联系人
		ContactsDto contacts = contactsService.findContactsById(id);

		dto.setDto(room, car, job, supplementary, spouse, contacts);
		
		return dto;
	}
	
	public void saveOrder(OrderDto dto){
		Order order = new Order(dto);
		order = orderRepository.saveAndFlush(order);
		
		RoomDto rd = dto.getRoom();
		if(null != rd){
			rd.setOrderId(order.getId());
			rd.setId(order.getId());
			roomService.saveRoom(rd);
		}
		
		
		CarDto cd = dto.getCar();
		if(null != cd){
			cd.setOrderId(order.getId());
			cd.setId(order.getId());
			carService.saveCar(cd);
		}
		
		JobDto jd = dto.getJob();
		if(null != jd){
			jd.setOrderId(order.getId());
			jd.setId(order.getId());
			jobService.saveJob(jd);
		}
		
		SpouseDto sd = dto.getSpouse();
		if(null != sd){
			sd.setOrderId(order.getId());
			sd.setId(order.getId());
			spouseService.saveSpouse(sd);
		}
		
		SupplementaryDto sud = dto.getSupplementary();
		if(null != sud){
			sud.setOrderId(order.getId());
			sud.setId(order.getId());
			supplementaryService.saveSupplementary(sud);
		}
		
		ContactsDto cod = dto.getContacts();
		if(null != cod){
			cod.setOrderId(order.getId());
			cod.setId(order.getId());
			contactsService.saveContacts(cod);
		}
		
	}
	
	public Pagination<OrderDto> findOrder(final OrderDto dto, Pagination<OrderDto> pag){
		
		//排序
		List<Sort.Order> orders = new ArrayList<Sort.Order>();
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "updateTime");
        orders.add(order);
        Sort sort = new Sort(orders);
        
        PageRequest request = new PageRequest(pag.getPageNum() - 1, pag.getPageSize(), sort);
        Page<Order> page;
        page = orderRepository.findAll(new Specification<Order>() {
			
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> pl = new ArrayList<Predicate>();
				
				/*//用户名
				if(StringUtils.isNotBlank(wafUserDto.getUserName())){
					pl.add(cb.like(root.<String>get("userName"), "%"+wafUserDto.getUserName()+"%"));
				}*/
				
				if(StringUtils.isNotBlank(dto.getCreater())){
					pl.add(cb.equal(root.<String>get("creater"), dto.getCreater()));
				}
                if(StringUtils.isNotBlank(dto.getStatus())){
                    List<String> st = Arrays.asList(dto.getStatus().split(","));
                    pl.add(root.<String>get("status").in(st));
                }
				pl.add(cb.equal(root.<String>get("delFlag"), AbleStatus.usable_1.getCode()));
				return cb.and(pl.toArray(new Predicate[0]));
			}
		},request);
        
        List<Order> wafUsers = page.getContent();
        List<OrderDto> wafUserDtos = new ArrayList<OrderDto>();
        for(Order wafUser:wafUsers){
        	wafUserDtos.add(new OrderDto(wafUser));
        }
		pag.setResult(wafUserDtos);
	    pag.setTotal(page.getTotalPages());
	    pag.setPages((int)page.getTotalElements());
	    
	    return pag;
	}

}
