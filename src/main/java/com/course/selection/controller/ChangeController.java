package com.course.selection.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.entity.User;
import com.course.selection.entity.Selection;

import com.course.selection.service.SelectionService;
import com.course.selection.service.TimePlaceService;

@Controller
@RequestMapping(value="/changeCourse/")
public class ChangeController {
	
    @Autowired
    private SelectionService selectionService;
    
    @Autowired
    private TimePlaceService timePlaceService;
    
    @Autowired
	private HttpSession httpSession;
    
    @RequestMapping(value="test.do")
    public String test() {
    	return "change/test";
    }
    
	@RequestMapping(value="changeCourse.do")
	public ModelAndView changeCourse(@RequestParam(value="out_courseId") Integer out_courseId , @RequestParam(value="in_courseId") Integer in_courseId,@RequestParam(value="out_userId") Integer out_userId){   
		
		//out_courseId 换出去的课程 ， in_courseId换进来的课程
		
		int i;
        String msg;
		
		Map<String, Object> params = new HashMap<>();
		params.put("courseId", out_courseId);
		
		List<Selection> selections_u = selectionService.findList(params);  //得到所有选out_courseId这门课的人
		User user = (User)httpSession.getAttribute("user");
		
		//判断用户有无out_courseId这门课
		for(Selection u:selections_u){
			if(u.getUserId().equals(user.getUserId()))
				break;
			else{
				msg = "你没有这门课可供交换";
				return new ModelAndView("change/info", "msg", msg);
			}
				
		}		
		
		int[][] timetable = new int[12][7] ;    //获得课程表
		
		params.clear();   
		params.put("userId", user.getUserId());
		
		//获得用户选的所有课程
		List<Selection> my_selection = selectionService.findList(params); 
		
		//获得用户的课程表
		timetable = selectionService.buildTimetable(my_selection);
		
		params.clear();
		params.put("courseId", out_courseId);
		
		//获得将要交换出去的课程的weekDay、classNo、num
		int out_weekDay = timePlaceService.findList(params).get(0).getWeekDay(); 
		int out_classNo = timePlaceService.findList(params).get(0).getClassNo(); 
		int out_num = timePlaceService.findList(params).get(0).getNum();         
		
		//在用户的课程表中删除要交换出去的课
		for(i=0 ; i<out_num ; i++)
			timetable[out_classNo+i-1][out_weekDay-1] = 0 ;   
		
		params.clear();
		params.put("courseId", in_courseId);
		
		//获得将要交换进来的课程的weekDay、classNo、num
		int in_weekDay = timePlaceService.findList(params).get(0).getWeekDay();    
		int in_classNo = timePlaceService.findList(params).get(0).getClassNo();     
		int in_num = timePlaceService.findList(params).get(0).getNum();             
		
		int conflict = 0;  // 没有冲突等于0
		
		//检查课程有无冲突
		for(i=0 ; i < in_num ; i++){
			if(timetable[in_classNo+i-1][in_weekDay-1] == 1) {
				conflict = 1 ;
				break;
			}		
		} 
		
		if(conflict == 1){
			msg = "课程冲突";
			return new ModelAndView("change/info", "msg", msg);
		}
		else{
			//在数据库修改选课表
			msg = selectionService.changeDatabase(out_courseId, in_courseId, out_userId);
		}
			
		
		return new ModelAndView("change/info", "msg", msg);
	}
	
}
