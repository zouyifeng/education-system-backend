package com.laboratory.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.laboratory.dao.LessonMapper;
import com.laboratory.po.Lesson;

@Repository
public class LessonMapperImpl extends BaseDaoImpl<Lesson> implements LessonMapper{

	@Override
	public List<Lesson> selectClassLesson(int classId) {
		// TODO Auto-generated method stub
		return template.selectList("selectClassLesson", classId);
	}
	
	@Override
	public List<Lesson> findLessonByDateRange(Lesson lesson) {
		return template.selectList("findLessonByDateRange", lesson);
	}
}
