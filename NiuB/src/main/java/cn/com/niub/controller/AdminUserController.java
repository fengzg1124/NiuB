package cn.com.niub.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;

import cn.com.niub.domain.RoleMenu;
import cn.com.niub.domain.RoleUser;
import cn.com.niub.domain.User;
import cn.com.niub.domain.UserExample;
import cn.com.niub.domain.UserExample.Criteria;
import cn.com.niub.dto.MenuDto;
import cn.com.niub.dto.RoleDto;
import cn.com.niub.dto.UserDto;
import cn.com.niub.service.RoleMenuService;
import cn.com.niub.service.RoleService;
import cn.com.niub.service.RoleUserService;
import cn.com.niub.service.UserService;
import cn.com.niub.utils.ControllerUtils;

@Controller
@RequestMapping(value="/adminUser")
public class AdminUserController {

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	RoleUserService roleUserService;
	
	@RequestMapping(value="/userList")
	public String getUserList(Model model,HttpServletRequest request,String formData,
			String pageNum,String pageSize) {
		
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		//String formData = request.getParameter("formData");
		
		UserDto dto = new UserDto();
		
		if(StringUtils.isNotBlank(formData)){
			dto = JSON.parseObject(formData,UserDto.class);
		}
		
		//分页页码
		pageNum = StringUtils.isBlank(pageNum)?"1":pageNum;
		//列表行数
		pageSize = StringUtils.isBlank(pageSize)?"10":pageSize;
		
		//查询用户列表
		Page<User> users = getUserList(adminuser.getId(),dto,true,Integer.valueOf(pageNum),Integer.valueOf(pageSize));
		
		if(!"ee3653fe07cc4513b41319090a2516be".equals(adminuser.getId())){
			for(User us:users){
				if(us.getState()==1){
					us.setUserName(us.getUserName().substring(0,1));
					us.setPhoneNumber("***********");
				}
			}
		}
		
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", users);
		return "admin/user/userList";
	}
	
	
	@RequestMapping(value="/toUserManage")
	public String toUserManage(Model model,HttpServletRequest request,String formData,
			String pageNum,String pageSize) {
		
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		//String formData = request.getParameter("formData");
		
		UserDto dto = new UserDto();
		
		if(StringUtils.isNotBlank(formData)){
			dto = JSON.parseObject(formData,UserDto.class);
		}
		
		//分页页码
		pageNum = StringUtils.isBlank(pageNum)?"1":pageNum;
		//列表行数
		pageSize = StringUtils.isBlank(pageSize)?"10":pageSize;
		
		//查询用户列表
		Page<User> users = getUserList(adminuser.getId(),dto,false,Integer.valueOf(pageNum),Integer.valueOf(pageSize));
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", users);
		return "admin/system/user/userList";
	}
	
	@RequestMapping(value="/toAddRoleList")
	private String toAddRoleList(Model model,String id,String pageNum,String pageSize) {
		
		//分页页码
		pageNum = StringUtils.isBlank(pageNum)?"1":pageNum;
		//列表行数
		pageSize = StringUtils.isBlank(pageSize)?"10":pageSize;
		String ids = "'"+id.replace(",", "','")+"'";
		//查询权限列表
		Page<RoleDto> roles =  roleService.findRoleAndUserFlag(ids,Integer.valueOf(pageNum),Integer.valueOf(pageSize));
		//List<RoleDto> roles =  roleService.findRoleAndUserFlag(id,Integer.valueOf(pageNum),Integer.valueOf(pageSize));
		
		model.addAttribute("userId", id);
		model.addAttribute("page", roles);
		return "admin/system/user/addRoleList";
	}
	
	@RequestMapping(value="/saveUserRole")
	@ResponseBody
	private String saveUserRole(HttpServletRequest request,String userIds,String roleIds) {
		
		String message="1";
		
		List<String> userIdList = Arrays.asList(userIds.split(","));  
		
		for(String userId:userIdList){
			
			roleUserService.deleteRoleUserByUserId(userId);
			
			List<String> roleIdList = Arrays.asList(roleIds.split(","));  
			for(String roleId:roleIdList){
				if(StringUtils.isNotBlank(roleId)){
					RoleUser ru = new RoleUser();
					ru.setRoleid(roleId);
					ru.setUserid(userId);
					ru.setId(ControllerUtils.getUUID());
					roleUserService.saveRoleUser(ru);
				}
			}
		}
		
		return message;
	}
	
	//查询用户列表
	public Page<User> getUserList(String userid,UserDto dto,Boolean hierarchyFlag,int pageNum,int pageSize){
		//查询用户信息
		UserExample examplec = new UserExample();
		Criteria criteriac = examplec.createCriteria();
		
		if(hierarchyFlag){
			criteriac.andHierarchyIdLike("%"+userid+"%");
		}
		
		if(dto!=null){
			if(StringUtils.isNotBlank(dto.getUserName())){
				criteriac.andUserNameLike("%"+dto.getUserName()+"%");
			}
			if(StringUtils.isNotBlank(dto.getPhoneNumber())){
				criteriac.andPhoneNumberEqualTo(dto.getPhoneNumber());
			}
			if(null!=dto.getStartTime()){
				criteriac.andCreateTimeGreaterThanOrEqualTo(dto.getStartTime());
			}
			if(null!=dto.getEndTime()){
				criteriac.andCreateTimeLessThanOrEqualTo(dto.getEndTime());
			}
			if(null!=dto.getState()){
				criteriac.andStateEqualTo(dto.getState());
			}else{
				List<Integer> st = new ArrayList<Integer>();
				st.add(1);
				st.add(2);
				criteriac.andStateIn(st);
			}
			if(StringUtils.isNotBlank(dto.getType())){
				criteriac.andTypeEqualTo(dto.getType());
			}
			if(StringUtils.isNotBlank(dto.getParentName())){
				UserExample exam = new UserExample();
				Criteria crit = exam.createCriteria();
				crit.andUserNameLike("%"+dto.getParentName()+"%");
				List<User> pUser = userService.findUsers(exam);
				List<String> pId = new ArrayList<>();
				for(User pu:pUser){
					pId.add(pu.getId());
				}
				if(pId.size()>0){
					criteriac.andParentIdIn(pId);
				}
			}
		}
		
		
		Page<User> users = userService.findUsersPage(examplec,pageNum,pageSize);
		
		for(User us:users){
			if(StringUtils.isNotBlank(us.getParentId())){
				us.setParentId(userService.findUserById(us.getParentId()).getUserName());
			}
		}
		return users;
	}
}
