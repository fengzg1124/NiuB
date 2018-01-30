package cn.com.niub.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.niub.domain.Log;
import cn.com.niub.domain.User;
import cn.com.niub.domain.UserExample;
import cn.com.niub.domain.UserExample.Criteria;
import cn.com.niub.service.LogService;
import cn.com.niub.service.UserService;
import cn.com.niub.utils.ControllerUtils;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	LogService logService;
	
	@RequestMapping(value="/loanDetail-{type}")
	public String loanDetail(Model model,HttpServletRequest request,@PathVariable(name="type")String type){
		model.addAttribute("type", type);
		return "loan_Detail";
	}
	
	@RequestMapping(value="/saveLoanUser-{type}")
	public String saveLoanUser(Model model,User user, HttpServletRequest request,@PathVariable(name="type")String type){
		
		//记录日志
		Log log = new Log();
		log.setId(ControllerUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("saveLoanUser");
		
		//用户访问ip
		String ip=ControllerUtils.getIp(request);
				
		if(StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPhoneNumber())){
			model.addAttribute("mes", "提交失败，填写信息不完整！");
        	log.setLog("贷款用户信息提交失败，填写信息不完整,头信息"+request.getHeader("User-Agent")+",ip:"+ip+";"+request.getHeader("X-Forwarded-For"));
    		log.setEndTime(new Date());
    		logService.saveLog(log);
    		return "index";
		}
		
		//同一ip30天内不能重复提交
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andIpEqualTo(ip);//ip
		
		Calendar theCa = Calendar.getInstance();
		theCa.setTime(new Date());
		theCa.add(theCa.DATE, -30);
		criteria.andCreateTimeGreaterThanOrEqualTo(theCa.getTime());//申请时间30天以内
		
		criteria.andStateEqualTo(2);//申请标记
		criteria.andTypeEqualTo(type);
		List<User> users = userService.findUsers(example);
        if(users.size()>0){
        	model.addAttribute("mes", "提交失败，您在近期已提交过此贷款类型的申请，请勿重复提交哦！");
        	log.setLog("贷款用户信息提交失败，30天内申请信息重复，用户名："+user.getUserName()+",手机号："+user.getPhoneNumber()+",头信息"+request.getHeader("User-Agent")+",ip:"+ip+";"+request.getHeader("X-Forwarded-For"));
    		log.setEndTime(new Date());
    		logService.saveLog(log);
    		return "index";
        }
        
        //同一手机号半年内不能重复提价
		UserExample example1 = new UserExample();
		Criteria criteria1 = example1.createCriteria();
		
		Calendar theCa1 = Calendar.getInstance();
		theCa1.setTime(new Date());
		theCa1.add(theCa1.DATE, -180);
		criteria1.andCreateTimeGreaterThanOrEqualTo(theCa1.getTime());//申请时间半年以内
		criteria1.andPhoneNumberEqualTo(user.getPhoneNumber());//手机号
		criteria1.andStateEqualTo(2);//申请标记
		criteria1.andTypeEqualTo(type);//贷款类型
		List<User> users1 = userService.findUsers(example1);
        if(users1.size()>0){
        	model.addAttribute("mes", "提交失败，您手机号在近期已提交过此贷款类型的申请，请勿重复提交哦！");
        	log.setLog("贷款用户信息提交失败，180天内申请手机号重复，用户名："+user.getUserName()+",手机号："+user.getPhoneNumber()+",头信息"+request.getHeader("User-Agent")+",ip:"+ip+";"+request.getHeader("X-Forwarded-For"));
    		log.setEndTime(new Date());
    		logService.saveLog(log);
    		return "index";
        }
        
        
		user.setId(ControllerUtils.getUUID());
		String phid = (String) request.getSession().getAttribute("Tphid");
		user.setParentId(phid);
		User userp = userService.findUserById(phid);
		if(null != userp){
			if(StringUtils.isNotBlank(userp.getHierarchyId())){
				user.setHierarchyId(userp.getHierarchyId()+"-"+phid);
			}else{
				user.setHierarchyId(phid);
			}
		}
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		
		user.setIp(ip);
		userService.saveUser(user);
		model.addAttribute("mes", "提交成功，感谢您相信猎多金，财富经理将尽快联系您！");
		
		log.setLog("贷款用户信息提交成功，用户名："+user.getUserName()+",手机号："+user.getPhoneNumber()+",头信息"+request.getHeader("User-Agent")+",ip:"+ip+";"+request.getHeader("X-Forwarded-For"));
		log.setEndTime(new Date());
		logService.saveLog(log);
		
		User userc = (User) request.getSession().getAttribute("user");
		if(null != userc){
			model.addAttribute("phid", userc.getId());
			model.addAttribute("userName", userc.getUserName());
		}
		return "index";
	}
	
}
