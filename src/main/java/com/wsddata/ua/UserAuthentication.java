package com.wsddata.ua;

import java.io.IOException;
import java.net.URL;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.wsddata.ua.Config;
import net.sf.json.JSONObject;

@Component
public class UserAuthentication implements Filter{

	private String wac_address=null;
	private String version=null;
	private String systemId=null;
	
	@Autowired
	private Config conf;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		wac_address=conf.wac_address;
		version=conf.version;
		systemId=conf.systemId;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(wac_address==null||wac_address.equals("")){
			wac_address="localhost";
		}
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		
		String token=request.getParameter("token");
		
		//System.out.println(req.getPathInfo());
		
		URL url=new URL("http://"+wac_address+"/"+version+"/checkToken?token="+token);
		System.out.println(systemId);
		
		if(!token.equals("")){
			//HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(req);
	        //requestWrapper.addHeader("systemId", systemId);
			
			////用此token到认证中心认证，返回successful=true，且角色符合，则提供服务，否则拒绝服务
			//HttpClient url
			String strResult=null;
			
			JSONObject jsonO = JSONObject.fromObject(strResult);
			Result r=new Result();
			r.setSuccessful((Boolean) jsonO.get("successful"));
			r.setMessage((String) jsonO.get("message"));
			r.setData( (String) jsonO.get("data"));
			if(r.getSuccessful()&&r.getMessage().equals("ok")){
				
		        chain.doFilter(req, response);
			}else{
				resp.sendError(401);
			}
		}else{
			resp.sendError(401);
		}
	}

	@Override
	public void destroy() {
		wac_address=null;
		version=null;
		systemId=null;
	}

}
