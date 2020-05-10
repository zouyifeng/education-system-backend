package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.LessonMapper;
import com.laboratory.po.Lesson;

@Service
public class LessonService {

	@Autowired
	LessonMapper lessonMapper;
	
	public List<Lesson> selectClassLesson(int classId) {
		return lessonMapper.selectClassLesson(classId);
	}
	
	public int updateLesson(Lesson lesson) {
		return lessonMapper.update(lesson);
	}
	
	public int deleteLesson(int id) {
		return lessonMapper.delete(id);
	}
	
	public int addLesson(Lesson lesson) {
		return lessonMapper.insert(lesson);
	}
	
	public List<Lesson> findLessonByDateRange(Lesson lesson) {
		return lessonMapper.findLessonByDateRange(lesson);
	}
}
