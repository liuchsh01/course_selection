package com.course.selection.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.entity.User;
import com.course.selection.entity.Change;
import com.course.selection.entity.Course;
import com.course.selection.entity.Selection;
import com.course.selection.entity.TimePlace;
import com.course.selection.entity.ChangList;
import com.course.selection.service.ChangeService;
import com.course.selection.service.CourseService;
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
    private ChangeService changeService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
	private HttpSession httpSession;
    
    @RequestMapping(value="test.do")
    public String test() {
    	return "change/test";
    }
    
    @RequestMapping(value="showChange.do")
    public ModelAndView showChange(){
    	User user = (User)httpSession.getAttribute("user");
    	List<Change> changeList = changeService.findListByUserId(user.getUserId());
    	List<ChangList> changeInfo = new ArrayList<>();
    	for(int i=0; i<changeList.size(); i++){
    		 ChangList cl = new ChangList();
    		 Map<String, Object> params = new HashMap<>();
    		 
    		 cl.setOut_userId(changeList.get(i).getUserId());
    		 
    		 params.put("courseId", changeList.get(i).getInCourseId());
    		 List<Course> courseInfo = courseService.findList(params);
    		 
    		 cl.setOut_courseCode(courseInfo.get(0).getCourseCode());
    		 cl.setOut_courseName(courseInfo.get(0).getCourseName());
    		 cl.setOut_courseId(courseInfo.get(0).getCourseId());
    		 
    		 List<TimePlace> time_place = timePlaceService.findList(params);
    		 int weekday,classNo,num;
    		 String tp;
    		 weekday = time_place.get(0).getWeekDay();
    		 classNo = time_place.get(0).getClassNo();
    		 num = time_place.get(0).getNum();
    		 
    		 tp = "周"+weekday+"第";
    		 
    		 for(int j=0; j<num ;j++){
    			 int snum = classNo+j;
    			 if(j == (num-1)){
    				 tp = tp+snum;
    			 }else{
    			 tp = tp+snum+',';
    			 }
    		 }
    		 
    		 tp = tp+'节';
    		 
    		 cl.setOut_time(tp);
    		 
    		 params.clear();
    		 params.put("courseId", changeList.get(i).getOutCourseId());
    		 courseInfo = courseService.findList(params);
    		 
    		 cl.setin_courseCode(courseInfo.get(0).getCourseCode());
    		 cl.setin_courseName(courseInfo.get(0).getCourseName());
    		 cl.setin_courseId(courseInfo.get(0).getCourseId());
    		 
    		 time_place = timePlaceService.findList(params);
    		 weekday = time_place.get(0).getWeekDay();
    		 classNo = time_place.get(0).getClassNo();
    		 num = time_place.get(0).getNum();
    		 
    		 tp = "周"+weekday+"第";
    		 
    		 for(int j=0; j<num ;j++){
    			 int snum = classNo+j;
    			 if(j == (num-1)){
    				 tp = tp+snum;
    			 }else{
    			 tp = tp+snum+',';
    			 }
    		 }
    		 
    		 tp = tp+'节';
    		 
    		 cl.setin_time(tp);
    		 
    		 changeInfo.add(cl);
    	     
    	}
    	return new ModelAndView("change/show","changeInfo",changeInfo);
    }
    
    @RequestMapping(value="changeRequest.do")
    @ResponseBody
    public Map<String,String> changeRequest(@RequestParam(value="out_courseId") Integer out_courseId, @RequestParam(value="in_courseId") Integer in_courseId){
    	Map<String, String> info = new HashMap<>();
    	String msg="";
    	int i,j;    //循环变量
    	Map<String, Object> params = new HashMap<>();
    	int[][] timetable = new int[12][7];
    	
    	User user = (User) httpSession.getAttribute("user");
    	
    	params.put("userId", user.getUserId());
    	params.put("out_courseId", out_courseId);
    	if(changeService.findListByMap(params).size() != 0){
    		msg = "这门课已经在交换申请列表了";
    		info.put("msg", msg);
    		return info;
    	}
    	
    	params.clear();
    	params.put("userId", user.getUserId());
    	
    	List<Selection> my_selection = selectionService.findList(params);
    	
    	for(i=0; i<my_selection.size(); i++){
    		if(my_selection.get(i).getCourseId() == in_courseId){
    			msg = "你已经有这门课了";
    			info.put("msg", msg);
    			return info;
    		}
    	}
    	
    	timetable = selectionService.buildTimetable(my_selection);
    	
    	int weekDay, classNo, num, count;
    	
    	params.clear();
    	params.put("courseId", out_courseId);
    	
    	//获得交换出去的课程的TimePlace
    	
    	List<TimePlace> timePlace = timePlaceService.findList(params);
    	
    	count = timePlace.size();
    	
    	//在课程表删除将要交换出去的课程的记录
    	for(i=0; i<count; i++){
    		weekDay = timePlace.get(i).getWeekDay();
    		classNo = timePlace.get(i).getClassNo();
    		num = timePlace.get(i).getNum();
    		
    		for(j=0; j<num; j++){
    			timetable[classNo+j-1][weekDay-1] = 0;
    		}
    	}
    	
    	int conflict = 0; //没有冲突等于0
    	
        params.put("courseId", in_courseId);
        
        //获得交换进来的课程的TimePlace
        
        timePlace = timePlaceService.findList(params);
        
        count = timePlace.size();
        
        //检查是否冲突
        
        for(i=0; i<count; i++){
        	weekDay = timePlace.get(i).getWeekDay();
        	classNo = timePlace.get(i).getClassNo();
        	num = timePlace.get(i).getNum();
        	
        	for(j=0; j<num; j++){
        		if(timetable[classNo+j-1][weekDay-1] == 1){
        			conflict = 1;
        			break;
        		}
        	}
        }
    	
        if(conflict == 1){
        	msg = "课程有冲突";
        	info.put("msg", msg);
        }
        else{
        	Change change = new Change();
        	change.setUserId(user.getUserId());
        	change.setInCourseId(in_courseId);
        	change.setOutCourseId(out_courseId);
        	
        	//保存该课程交换请求
        	changeService.save(change);
        	msg = "课程交换申请成功";
        	info.put("msg", msg);
        }
        
    	return info;
    }
    
    
	@RequestMapping(value="changeCourse.do")
	@ResponseBody
	public Map<String, Object> changeCourse(@RequestParam(value="out_courseId") Integer out_courseId , @RequestParam(value="in_courseId") Integer in_courseId,@RequestParam(value="out_userId") Integer out_userId){   
		
		//out_courseId 换出去的课程 ， in_courseId换进来的课程
		
		int i,j;
        String msg;
        Map<String, Object> info = new HashMap<>();
		
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
				info.put("msg", msg);
				return info;
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
		
		// TODO 考虑count
		
		int count;
		count = timePlaceService.findList(params).size();
		
		for(i=0; i<count; i++){
			//获得将要交换出去的课程的weekDay、classNo、num
			int out_weekDay = timePlaceService.findList(params).get(i).getWeekDay(); 
			int out_classNo = timePlaceService.findList(params).get(i).getClassNo(); 
			int out_num = timePlaceService.findList(params).get(i).getNum(); 
			
			//在用户的课程表中删除要交换出去的课
			for(j=0 ; j<out_num ; j++)
				timetable[out_classNo+j-1][out_weekDay-1] = 0 ; 
		}
			 		
		params.clear();
		params.put("courseId", in_courseId);
		
		int conflict = 0;  // 没有冲突等于0
	
		count = timePlaceService.findList(params).size();
		
		for(i=0; i<count; i++){
			//获得将要交换进来的课程的weekDay、classNo、num
			int in_weekDay = timePlaceService.findList(params).get(i).getWeekDay();    
			int in_classNo = timePlaceService.findList(params).get(i).getClassNo();     
			int in_num = timePlaceService.findList(params).get(i).getNum();
		
			for(j=0 ; j < in_num ; j++){
				if(timetable[in_classNo+j-1][in_weekDay-1] == 1) {
					conflict = 1 ;
					break;
				}		
			}
			
			if(conflict == 1)
				break;
		}
		
		//检查课程有无冲突
		if(conflict == 1){
			msg = "课程冲突";
			info.put("msg", msg);
			return info;
		}
		else{
			//修改选课表数据
			msg = selectionService.changeDatabase(out_courseId, in_courseId, out_userId);
			//修改change_request表数据
			Change change = new Change();
			change.setUserId(out_userId);
			change.setOutCourseId(in_courseId);
			changeService.deleteByEntity(change);
		}
			
		//TODO 修改Change表数据
		
		info.put("msg", msg);
		
		return info;
	}
	
}
