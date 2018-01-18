package cn.com.niub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@MapperScan("cn.com.niub.mapper")
public class NiuBApplication extends WebMvcConfigurerAdapter{

	
	// 启动的时候要注意，由于我们在controller中注入了RestTemplate，所以启动的时候需要实例化该类的一个实例  
    @Autowired  
    private RestTemplateBuilder builder;
    
    
	public static void main(String[] args) {
		SpringApplication.run(NiuBApplication.class, args);
	}
	
	//设置编码方式，此过滤器排在所有过滤器最后
	@Bean
	public FilterRegistrationBean encodeRegistrationBean() {
		CharacterEncodingFilter encodeFilter = new CharacterEncodingFilter();
		
		//encoding：字符集，即将过滤到的request的字符集设置为encoding指定的值，如UTF-8等相当于：request.setCharacterEncoding
		encodeFilter.setEncoding("UTF-8");
		
		/*forceEncoding：字面意思是强制字符集，但你大可不必按字面意思理解，
		因为这个参数的值只不过是指定response的字符集是否也设置成encoding所指定的字符集，
		所以你可以选择设置为true或false，
		当值为true时，相当于request.setCharacterEncoding("UTF-8");response.setCharacterEncoding("UTF-8");
		当值为false时，相当于：request.setCharacterEncoding("UTF-8");  */
		encodeFilter.setForceEncoding(true);
		
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(encodeFilter);
		//排在所有过滤器最后
		registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registrationBean;
	}
	
	// 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例  
    @Bean  
    public RestTemplate restTemplate() {  
        return builder.build();  
    }  
    
    //设置url直接访问的页面，不通过controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	//registry.addViewController("/login").setViewName("login");
    	registry.addViewController("/adminLogin").setViewName("admin/login");
	}
}
