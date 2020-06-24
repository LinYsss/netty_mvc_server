package com.waya.demo.util.resultData;

import com.waya.demo.util.annotation.RequestMapping;
import com.waya.demo.util.annotation.RequestMethod;

public class Path {
	
	 private RequestMethod method;
	 private String uri;
	 private String headers;
	 private String produces;
	 
   public static Path make(RequestMapping annotation){
        return new Path(annotation);
   }
	 
   public Path(RequestMapping annotation){
        method = annotation.method();
        uri = annotation.value();
        headers = annotation.headers();
        produces = annotation.produces();
       
    }

	public RequestMethod getMethod() {
		return method;
	}
	
	public void setMethod(RequestMethod method) {
		this.method = method;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getHeaders() {
		return headers;
	}
	
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	
	public String getProduces() {
		return produces;
	}
	
	public void setProduces(String produces) {
		this.produces = produces;
	}
	
    @Override
    public String toString(){
        return  method + " " + uri;
    }
    @Override
    public int hashCode(){
        return  ("HTTP " + method+ " " + uri).hashCode();
    }

}
