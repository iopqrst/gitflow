package com.bskcare.ch.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.NoteCategoryDao;
import com.bskcare.ch.service.NoteCategoryService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.NoteCategory;

@Service
public class NoteCategoryServiceImpl implements NoteCategoryService {

	@Autowired
	private NoteCategoryDao noteCategoryDao;

	public String queryList(NoteCategory noteCategory) {
		String noteStr = "";
		List<NoteCategory> list = noteCategoryDao.queryList(noteCategory);
		if (!CollectionUtils.isEmpty(list)) {
			noteStr = JsonUtils.getJsonString4JavaList(list);
		}
		return noteStr;
	}
	
	
	public String queryListForZtree(){
		JSONArray ja = new JSONArray();
		List<NoteCategory> list = noteCategoryDao.queryList(null);
		for (NoteCategory nc : list) {
			JSONObject jo = new JSONObject();
			jo.put("id", nc.getId());
			jo.put("name", nc.getName());
			jo.put("pId", nc.getParentId());
			jo.put("level", nc.getLevel());
			ja.add(jo);
		}
		return ja.toString();
	}

	public String addNodeCategory(NoteCategory nc){
		JSONObject jo = new JSONObject();
		nc.setCreateTime(new Date());
		NoteCategory note = noteCategoryDao.add(nc);
		if(note != null){
			jo.put("status", "success");
			jo.put("id", note.getId());
		}
		return jo.toString();
	}

	public String deleteNodeCategory(NoteCategory nc){
		JSONObject jo = new JSONObject();
		noteCategoryDao.delete(nc.getId());
		jo.put("status", "success");
		return jo.toString();
	}
	
	
	public String queryNoteByName(NoteCategory noteCategory){
		JSONObject jo = new JSONObject();
		int count = noteCategoryDao.queryNoteByName(noteCategory);
		if(count >0){
			jo.put("status", "success");
		}else{
			jo.put("status", "fail");
		}
		return jo.toString();
	}
	
	public String updateNoteCategory(NoteCategory nc){
		JSONObject jo = new JSONObject();
		nc.setCreateTime(new Date());
		int count = noteCategoryDao.updateNoteCategory(nc);
		if(count >0){
			jo.put("status", "success");
		}else{
			jo.put("status", "fail");
		}
		return jo.toString();
	}
}
