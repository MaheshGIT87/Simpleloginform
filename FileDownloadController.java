package com.fidelity.aws.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 
/**
 * @author kiran
 *
 */
@Controller
public class FileDownloadController {
	    
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "download/{arnValue}", produces = { "application/json" }, method = RequestMethod.GET)
	    public @ResponseBody ResponseEntity getFile(@PathVariable("arnValue") String arnValue,HttpServletRequest request) throws IOException{
	        ResponseEntity respEntity = null;

	        File file = new File("tempFile");
	        file.createNewFile();
	        
	        try (FileWriter filew = new FileWriter(file)) {
				filew.write(generateJSONObject());
			}

	        if(file.exists()){
	            InputStream inputStream = new FileInputStream(file);
	            byte[]out=org.apache.commons.io.IOUtils.toByteArray(inputStream);

	            HttpHeaders responseHeaders = new HttpHeaders();
	            responseHeaders.add("Content-Type","application/json");
	            responseHeaders.add("content-disposition", "attachment; filename=" + arnValue+".json");
	            respEntity = new ResponseEntity(out, responseHeaders,HttpStatus.OK);
	        }else{
	            respEntity = new ResponseEntity ("File Not Found", HttpStatus.OK);
	        }
	        return respEntity;
	    }

	    
	    @SuppressWarnings("unchecked")
		private String generateJSONObject() {
	    	JSONObject obj = new JSONObject();
			obj.put("Name", "crunchify.com");
			obj.put("Author", "App Shah");
	 
			JSONArray company = new JSONArray();
			company.add("Compnay: eBay");
			company.add("Compnay: Paypal");
			company.add("Compnay: Google");
			obj.put("Company List", company);
			
			return obj.toJSONString();
	    }
}
