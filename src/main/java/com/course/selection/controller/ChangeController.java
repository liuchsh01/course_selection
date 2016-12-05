package com.course.selection.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.entity.User;
import com.course.selection.entity.Selection;
import com.course.selection.entity.TimePlaceKey;
import com.course.selection.service.TimePlaceKeyService;
import com.course.selection.service.SelectionService;

@Controller
@RequestMapping(value="/changeCourse/")
public class ChangeController {
	
    @Autowired
    private SelectionService selectionService;
    
    @Autowired
	private TimePlaceKeyService timePlaceKeyService;
    
    @Autowired
	private HttpSession httpSession;
    
	@RequestMapping(value="changeCourse.do")
	public ModelAndView changeCourse(int out_courseId , int in_courseId){   
		
		//out_courseId 换出去的课程 ， in_courseId换进来的课程
		
		int i,j;

		Map<String, Object> param = new HashMap<>();
		param.put("courseId", out_courseId);
		
		List<Selection> selections_u = selectionService.findList(param); 
		User user = (User)httpSession.getAttribute("user");
		for(Selection u:selections_u){
			if(u.getUserId().equals(user.getUserId()))
				break;
			else
				return new ModelAndView("redirect:/changeCourse/change.do");
		}		
		
		int[][] timetable = new int[7][7] ;    //获得课程表W1
		
		param.clear();   //清空map的所有映射
		param.put("userId", user.getUserId());
		
		List<Selection> selection_c = selectionService.findList(param); 
		
		int[] courseId = new int[20];
		
		int[] weekDay = new int[20];
	    int[] classNo = new int[20];
	    int[] num     = new int[20];
	    boolean[] nlisten = new boolean[20];
		
		for(i=0 ; i< selection_c.size() ; i++) {
			//将每门课的courseId,nlisten放入 courseId数组中
			nlisten[i] = selection_c.get(i).getNlisten();
			courseId[i] = selection_c.get(i).getCourseId();
			
			param.clear();
			param.put("courseId", courseId[i]);
			List<TimePlaceKey> timePlaceKey = timePlaceKeyService.findList(param); 
			weekDay[i] = timePlaceKey.get(0).getWeekDay();
			classNo[i] = timePlaceKey.get(0).getWeekDay();
			num[i] = timePlaceKey.get(0).getNum();
		}
		
		//初始化课程表
		for(i=0 ; i<7 ; i++)              
			for(j=0 ; j<7 ; j++)
				timetable[i][j] = 0;
		
		//建立课程表
		for(i=0 ; i<courseId.length ; i++){
			int nums = num[i];
			for(j=0 ; j<nums ; j++) {
				timetable[classNo[i]+j][weekDay[i]] = 1; 
			}                  
		}                       
		
		param.clear();
		param.put("courseId", out_courseId);
		
		//获得将要交换出去的课程的weekDay、classNo、num
		int out_weekDay = timePlaceKeyService.findList(param).get(0).getWeekDay(); 
		int out_classNo = timePlaceKeyService.findList(param).get(0).getClassNo(); 
		int out_num = timePlaceKeyService.findList(param).get(0).getNum();         
		
		for(i=0 ; i<out_num ; i++)
			timetable[out_classNo+i][out_weekDay] = 0 ;   //步骤6
		
		param.clear();
		param.put("courseId", in_courseId);
		
		//获得将要交换进来的课程的weekDay、classNo、num
		int in_weekDay = timePlaceKeyService.findList(param).get(0).getWeekDay();     //参数in_courseId
		int in_classNo = timePlaceKeyService.findList(param).get(0).getClassNo();     //参数in_courseId
		int in_num = timePlaceKeyService.findList(param).get(0).getNum();              //参数in_courseId
		
		int conflict = 0;  // 没有冲突等于0
		
		for(i=0 ; i < in_num ; i++){
			if(timetable[in_classNo+i][in_weekDay] == 1) {
				conflict = 1 ;
				break;
			}		
		} 
		
		if(conflict == 1)
			return new ModelAndView("redirect:/changeCourse/change.do");
		else
			//修改数据库
		
		return new ModelAndView("redirect:/changeCourse/info.do");
	}
	
}
