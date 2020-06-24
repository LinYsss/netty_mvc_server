package com.waya.demo.controller;

import java.util.List;

import com.waya.demo.pojo.UserBean;
import com.waya.demo.service.UserService;
import com.waya.demo.util.annotation.Autowired;
import com.waya.demo.util.annotation.Controller;
import com.waya.demo.util.annotation.RequestMapping;
import com.waya.demo.util.annotation.RequestMethod;
import com.waya.demo.util.resultData.RestResult;
import com.waya.demo.util.resultData.ResultGenerator;

@Controller
@RequestMapping("demo")

public class DemoController {
	
	private @Autowired UserService service;	

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public RestResult login(String loginName,String loginPwd) throws Throwable {
    	  System.out.println(loginName + "----------" + loginPwd);
    	  List<UserBean> userBean = service.getUserAll();
        return ResultGenerator.build().getSuccessResult("success", userBean);
    }


    
}
