package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.NoteCategoryDao;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.NoteCategory;

@Repository
@SuppressWarnings("unchecked")
public class NoteCategoryDaoImpl extends BaseDaoImpl<NoteCategory> implements
		NoteCategoryDao {

	public List<NoteCategory> queryList(NoteCategory noteCategory) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append(" from NoteCategory n where n.status=? ");
		args.add(Constant.STATUS_NORMAL);
		if(null != noteCategory) {
			if(null != noteCategory.getParentId()) {
				sql.append(" and n.parentId=? ");
				args.add(noteCategory.getParentId());
			}
			if(!StringUtils.isEmpty(noteCategory.getName())) {
				sql.append(" and n.name like ? ");
				args.add("%" + noteCategory.getName() + "%");
			}
		}
		return this.executeFind(sql.toString(), args.toArray());
	}
	
	
	public int queryNoteByName(NoteCategory noteCategory){
		String sql = "select count(id) from t_note_category where status = ?";
		List args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		if(noteCategory != null){
			if(!StringUtils.isEmpty(noteCategory.getName())){
				sql += " and name = ?";
				args.add(noteCategory.getName());
			}
			if(noteCategory.getParentId() != null){
				sql += "and parentId = ?";
				args.add(noteCategory.getParentId());
			}
		}
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		int count = 0;
		if(obj != null&&!obj.equals("")){
			count = Integer.parseInt(obj.toString());
		}
		return count;
	}
	
	
	public int updateNoteCategory(NoteCategory nc){
		String sql = "update t_note_category set name = ? ,createTime = ? where id = ?";
		List args = new ArrayList();
		args.add(nc.getName());
		args.add(nc.getCreateTime());
		args.add(nc.getId());
		return updateBySql(sql, args.toArray());
	}
}
