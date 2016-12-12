package com.course.selection.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.selection.common.base.ServiceSupport;
import com.course.selection.service.SelectionService;
import com.course.selection.dao.SelectionDao;
import com.course.selection.dao.TimePlaceDao;
import com.course.selection.entity.Selection;
import com.course.selection.entity.TimePlace;
import com.course.selection.entity.User;

@Service
public class SelectionServiceImpl extends ServiceSupport<Selection> implements SelectionService {
	
	@Autowired
	TimePlaceDao timePlaceDao;
	
	@Autowired
	SelectionDao selectionDao;
	
	@Autowired
	HttpSession httpSession;
	
	@Override
	public int[][] buildTimetable(List<Selection> myCourses) {
		int i,j,k;
		
		boolean nlisten;
		int courseId;
		int weekDay;
		int classNo;
		int num;
		int count;        //记录一门课有几个上课时间段
		
		Map<String, Object> params = new HashMap<>();
		
		int[][] timetable = new int[12][7] ;
		
		for(i=0 ; i< myCourses.size() ; i++) {
			
			//将每门课的courseId,nlisten放入 courseId数组中
			nlisten = myCourses.get(i).getNlisten();
			courseId = myCourses.get(i).getCourseId();
			
			params.put("courseId", courseId);
			List<TimePlace> timePlace = timePlaceDao.findList(params); 
			
			count = timePlace.size();
			
			for(j=0; j<count; j++){
				weekDay = timePlace.get(j).getWeekDay();
				classNo = timePlace.get(j).getClassNo();
				num = timePlace.get(j).getNum();
				
				if(!nlisten){
					for(k=0; k<num; k++){
						timetable[classNo+k-1][weekDay-1] = 1;
					}
				}
			}
		}
		
		return timetable;
	}

	@Override
	public String changeDatabase(Integer out_courseId, Integer in_courseId, Integer out_userId) {
		boolean in_nlisten;        //交换进来的课程的nlisten
		boolean out_nlisten;       //交换出去的课程的nlisten
		boolean in_compulsory;     //交换进来的课程的compulsory
		boolean out_compulsory;    //交换出去的课程的compulsory
		
		String msg = "课程交换成功";
		
		Map<String, Object> params = new HashMap<>();
		
		User user = (User)httpSession.getAttribute("user");
		
		params.put("userId", out_userId);
		params.put("courseId", in_courseId);
		
		List<Selection> selections = selectionDao.findList(params);
		
		in_nlisten = selections.get(0).getNlisten();
		in_compulsory = selections.get(0).getCompulsory();
		
		params.clear();		
		params.put("userId", user.getUserId());
		params.put("courseId", out_courseId);
		
		selections.clear();
		selections = selectionDao.findList(params);
		
        out_nlisten = selections.get(0).getNlisten();
        out_compulsory = selections.get(0).getCompulsory();
        
        params.clear();
        params.put("update_courseId", in_courseId);
        params.put("update_nlisten", in_nlisten);
        params.put("update_compulsory", in_compulsory);
        params.put("where_userId", user.getUserId());
        params.put("where_courseId", out_courseId);
        
        selectionDao.updateByMap(params);
        
        params.clear();
        params.put("update_courseId", out_courseId);
        params.put("update_nlisten", out_nlisten);
        params.put("update_compulsory", out_compulsory);
        params.put("where_userId", out_userId);
        params.put("where_courseId", in_courseId);
        
        selectionDao.updateByMap(params);
						
		return msg;
	}
}
