package cn.com.niub.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;


//Druid内置提供一个StatFilter，用于统计监控信息。
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",  
	initParams={  
	    @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//忽略资源  
	}  
)  
public class DruidStatFilter extends WebStatFilter {  

} 
