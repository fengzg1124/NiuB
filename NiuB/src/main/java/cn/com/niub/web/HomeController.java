package cn.com.niub.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.niub.domain.User;
import cn.com.niub.domain.UserExample;
import cn.com.niub.domain.UserExample.Criteria;
import cn.com.niub.service.UserService;



@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	
	/*@RequestMapping(value="/")
	public String goLogin(){
		return "login";
	}*/
	
	@RequestMapping(value="/login")
	public String login(Model model,User user,HttpServletRequest request){
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo("张三");
		List<User> users = userService.findUsers(example);
		if(users.size()<0){
			return "redirect:/index-";
		}else{
			user = users.get(0);
		}
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		return "redirect:/index-"+user.getPhoneNumber().toString().substring(user.getPhoneNumber().toString().length()-4, user.getPhoneNumber().toString().length());
	}
	
	@RequestMapping(value="/index-{phid}")
	public String index(Model model,@PathVariable(name="phid")String phid){
		model.addAttribute("phid", phid);
		return "index";
	}
	
	
}
