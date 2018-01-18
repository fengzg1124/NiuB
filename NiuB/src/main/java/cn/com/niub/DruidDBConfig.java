package cn.com.niub;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration  
public class DruidDBConfig {  
    private Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);  
      
    @Value("${spring.datasource.mybaties.url}")  
    private String dbUrl;  
      
    @Value("${spring.datasource.mybaties.username}")  
    private String username;  
      
    @Value("${spring.datasource.mybaties.password}")  
    private String password;  
      
    @Value("${spring.datasource.mybaties.driverClassName}")  
    private String driverClassName;  
      
    @Value("${spring.datasource.mybaties.initialSize}")  
    private int initialSize;  
      
    @Value("${spring.datasource.mybaties.minIdle}")  
    private int minIdle;  
      
    @Value("${spring.datasource.mybaties.maxActive}")  
    private int maxActive;  
      
    @Value("${spring.datasource.mybaties.maxWait}")  
    private int maxWait;  
      
    @Value("${spring.datasource.mybaties.timeBetweenEvictionRunsMillis}")  
    private int timeBetweenEvictionRunsMillis;  
      
    @Value("${spring.datasource.mybaties.minEvictableIdleTimeMillis}")  
    private int minEvictableIdleTimeMillis;  
      
    @Value("${spring.datasource.mybaties.validationQuery}")  
    private String validationQuery;  
      
    @Value("${spring.datasource.mybaties.testWhileIdle}")  
    private boolean testWhileIdle;  
      
    @Value("${spring.datasource.mybaties.testOnBorrow}")  
    private boolean testOnBorrow;  
      
    @Value("${spring.datasource.mybaties.testOnReturn}")  
    private boolean testOnReturn;  
      
    @Value("${spring.datasource.mybaties.poolPreparedStatements}")  
    private boolean poolPreparedStatements;  
      
    @Value("${spring.datasource.mybaties.maxPoolPreparedStatementPerConnectionSize}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
      
    @Value("${spring.datasource.mybaties.filters}")  
    private String filters;  
      
    @Value("{spring.datasource.mybaties.connectionProperties}")  
    private String connectionProperties;  
      
    @Bean     //声明其为Bean实例  
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource  
    public DataSource dataSource(){  
        DruidDataSource datasource = new DruidDataSource();  
          
        datasource.setUrl(this.dbUrl);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        datasource.setDriverClassName(driverClassName);  
          
        //configuration  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            logger.error("druid configuration initialization filter", e);  
        }  
        datasource.setConnectionProperties(connectionProperties);  
          
        return datasource;  
    }  
}  

