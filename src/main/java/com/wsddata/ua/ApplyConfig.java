package com.wsddata.ua;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.wsddata.ua.Config;

@Configuration
public class ApplyConfig {
	
	@Autowired
	private Config conf;
	
	@Bean
	public FilterRegistrationBean regUAFilter(UserAuthentication filter){
		FilterRegistrationBean registration = new FilterRegistrationBean(filter);
		registration.setOrder(FilterRegistrationBean.REQUEST_WRAPPER_FILTER_MAX_ORDER+2);
		Collection<String> urlPatterns=new ArrayList<String>();
		String applyPath=conf.applyPath;
		if(applyPath!=null&&!applyPath.equals("")){
	    	String[] applyPaths=applyPath.split(";");
	    	if(applyPaths.length>0){
		    	for(int i=0;i<applyPaths.length;i++){
		    		if(!applyPaths[i].equals("")&&applyPaths[i].startsWith("/")){
		    			//加角色保存
		    			urlPatterns.add(applyPaths[i]);
		    			System.out.println("用户验证应用在： "+applyPaths[i]);
		    		}
		    	}
		    }
	    }	    
	    registration.setUrlPatterns(urlPatterns);//只对需要用户认证的服务路径应用验证
	    return registration;
	}
}
