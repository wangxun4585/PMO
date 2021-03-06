package com.pmo.dashboard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pmo.dashboard.entity.CSDept;
import com.pmo.dashboard.entity.Demand;
import com.pmo.dashboard.entity.HSBCDept;
import com.pmo.dashboard.entity.PageCondition;
import com.pom.dashboard.service.CSDeptService;
import com.pom.dashboard.service.DemandService;
import com.pom.dashboard.service.HSBCDeptService;

/**
 * 招聘需求的controller
 * 
 * @author tianzhao
 * @version1.0 2017-8-25 14:54:57
 */
@Controller
@RequestMapping(value="/demand")
public class DemandController {
	
	@Resource
	DemandService demandService;
	
	@Resource
	HSBCDeptService hsbcDeptService;
	
	@Resource
	CSDeptService csDeptService;

	private static Logger logger = LoggerFactory.getLogger(DemandController.class);
	
	@RequestMapping("/demandInfo")
	public String demandInfo(){
		return "/demand/demandQuery";
	}
	
	/**
	 * 加载Department信息
	 * @return
	 */
	@RequestMapping("/loadDepartment")
	@ResponseBody
	public List<HSBCDept> loadDepartment(){
		List<HSBCDept> list = hsbcDeptService.queryHSBCDeptName();
		return list;
	}
	
	/**
	 * 加载SubDepartment信息
	 * @param hsbcDeptName
	 * @return
	 */
	@RequestMapping("/loadSubDepartment")
	@ResponseBody
	public List<HSBCDept> loadSubDepartment(String hsbcDeptName){
		List<HSBCDept> list = hsbcDeptService.queryHSBCSubDeptNameByDeptName(hsbcDeptName);
		return list;
	}
	
	/**
	 * 加载交付部信息
	 * @param csBuName
	 * @return
	 */
	@RequestMapping("/loadScSubDeptName")
	@ResponseBody
	public List<CSDept> loadScSubDeptName(String csBuName){
		List<CSDept> list = csDeptService.queryCSSubDeptNameByCsBuName(csBuName);
		return list;
	}
	
	/**
	 * 按条件查询招聘需求和分页功能
	 * @param demand
	 * @param pageCondition
	 * @return
	 */
	@RequestMapping("/queryDemandList")
	@ResponseBody
	public Object demandQuery(String csBuName,Demand demand,PageCondition pageCondition){
		if("".equals(pageCondition.getCurrPage()) || pageCondition.getCurrPage() == null){
			pageCondition.setCurrPage(1);
		}
		//String csBuName = request.getParameter("csBuName");
		List<Demand> list = demandService.queryDemandList(demand,pageCondition,csBuName);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("list", list);
		result.put("pageCondition", pageCondition);
		return result;
	}
}
