package cn.com.niub.utils;

public enum AbleStatus {

	usable_1("有效",1),disabled_0("无效",0);
	
	private final String name;
	private final Integer code;
    
    private AbleStatus(String name,Integer code)
    {
    	this.name = name;
    	this.code = code;
    }

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}
    
}
