package cn.com.niub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.Log;
import cn.com.niub.mapper.LogMapper;

@Service
public class LogService {

	@Autowired
	LogMapper logMapper;
	
	public void saveLog(Log log){
		logMapper.insert(log);
	}
	
}
