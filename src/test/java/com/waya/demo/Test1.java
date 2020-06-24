package com.waya.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1 {
	
	/**
     * @param args
     */
    public static void main(String[] args) {
          
        Map<String,String> map = new HashMap<String,String>();
        map.put("A", "A1");
        map.put("B", "B2");
        map.put("C", "C3");
        map.put("D", "D4");
        map.put("E", "E5");
        //List<String> valuesList = (List<String>) map.values();
        List<String> valuesList = new ArrayList<String>(map.values());
        for(String str:valuesList){
            System.out.println(str);
        }
       
        System.out.println(Thread.currentThread().getContextClassLoader());

    }

}
