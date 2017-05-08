package com.wsddata.ua;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config{
	@Value("${wac.address}")
	public String wac_address;
	
	@Value("${version}")
	public String version;
	
	@Value("${applyPath}")
	public String applyPath;
	
	@Value("${systemId}")
	public String systemId;
}