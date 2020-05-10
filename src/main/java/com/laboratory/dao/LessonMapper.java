package com.laboratory.dao;

import java.util.List;

import com.laboratory.po.Lesson;

public interface LessonMapper extends BaseDao<Lesson>{
	
	List<Lesson> selectClassLesson(int classId);	
	
	List<Lesson> findLessonByDateRange(Lesson lesson);
	
}
