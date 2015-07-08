package com.bskcare.ch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.NoteRepoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.NoteRepoService;
import com.bskcare.ch.vo.NoteRepo;
@Service
public class NoteRepoServiceImpl implements NoteRepoService{
	@Autowired
	private NoteRepoDao repoDao;

	public String queryTag() {
		String tags = "";
		List<String> list = repoDao.queryTag();
		Map<String, String> kMap = new HashMap<String, String>();
		if(!CollectionUtils.isEmpty(list)) {
			for (String str : list) {
				String[] subTags = str.split(Constant.RPT_TAG_SPLIT);
				for (String tagStr : subTags) {
//					if(!kMap.containsKey(tagStr)) {
						kMap.put(tagStr, tagStr);
//					}
				}
				if(kMap.size() > 0) {
					Set<String> keys = kMap.keySet();
					String temp = "";
					for (String k : keys) {
						int i = 0;
						temp += "{\"name\":\""+k+"\"},";
						i++;
					}
					if(!"".equals(temp) && Constant.RPT_TAG_SPLIT.equals(temp.substring(temp.length() -1 , temp.length()))) {
						temp = temp.substring(0,temp.length()-1);
					}
					tags = "[" + temp +"]";
				}
			}
		}
		return tags;
	}

	public NoteRepo add(NoteRepo noteRepo) {
		return repoDao.add(noteRepo);
	}

	public PageObject<NoteRepo> queryList(NoteRepo noteRepo,QueryInfo queryInfo) {
		return repoDao.queryList(noteRepo,queryInfo);
	}

	public void delete(Integer id) {
		repoDao.delete(id);
	}

	public NoteRepo findById(Integer id) {
		return repoDao.load(id);
	}

	public void update(NoteRepo noteRepo) {
		repoDao.update(noteRepo);
	}

	public List<NoteRepo> queryMassTextList(NoteRepo noteRepo) {
		return repoDao.queryMassTextList(noteRepo);
	}
}
