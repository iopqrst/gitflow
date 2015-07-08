package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.NoteRepoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.NoteRepo;
@Repository
@SuppressWarnings("unchecked")
public class NoteRepoDaoImpl extends BaseDaoImpl<NoteRepo> implements NoteRepoDao{

	public List<String> queryTag() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct(tag) from t_note_repo where length(tag) > 0 ");
		return this.executeNativeQuery(sql.toString());
	}

	public PageObject<NoteRepo> queryList(NoteRepo noteRepo,QueryInfo queryInfo) {
		ArrayList args = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_note_repo where 1=1 ");
		if(null != noteRepo) {
			if(!StringUtils.isEmpty(noteRepo.getTitle())) {
				sql.append(" and title like ? ");
				args.add("%" + noteRepo.getTitle().trim() + "%");
			}
			if(!StringUtils.isEmpty(noteRepo.getContent())) {
				sql.append(" and content like ? ");
				args.add("%" + noteRepo.getContent().trim() + "%");
			}
			if(!StringUtils.isEmpty(noteRepo.getTag())) {
				sql.append(" and tag like ? ");
				args.add("%" + noteRepo.getTag().trim() + "%");
			}
			if(null != noteRepo.getType()) {
				sql.append(" and type like ? ");
				args.add("%" + noteRepo.getType() + "%");
			}
		}
		sql.append(" order by createTime desc ");
		return this.queryObjectsBySql(sql.toString(), null, null, args.toArray(), queryInfo, NoteRepo.class);
	}

	public List<NoteRepo> queryMassTextList(NoteRepo noteRepo) {
		ArrayList args = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_note_repo where 1=1 ");
		if(null != noteRepo) {
			if(!StringUtils.isEmpty(noteRepo.getTitle())) {
				sql.append(" and title like ? ");
				args.add("%" + noteRepo.getTitle().trim() + "%");
			}
			if(!StringUtils.isEmpty(noteRepo.getContent())) {
				sql.append(" and content like ? ");
				args.add("%" + noteRepo.getContent() + "%");
			}
			if(!StringUtils.isEmpty(noteRepo.getTag())) {
				sql.append(" and tag=? ");
				args.add(noteRepo.getTag());
			}
			if(null != noteRepo.getType()) {
				sql.append(" and type=? ");
				args.add(noteRepo.getType());
			}
			if(null != noteRepo.getCategory() && -1 != noteRepo.getCategory()) {
				sql.append(" and category=? ");
				args.add(noteRepo.getCategory());
			}
			if(null != noteRepo.getSubCategory() && -1 != noteRepo.getSubCategory()) {
				sql.append(" and subCategory=? ");
				args.add(noteRepo.getSubCategory());
			}
			if(null != noteRepo.getThirdCategory() && -1 != noteRepo.getThirdCategory()) {
				sql.append(" and thirdCategory=? ");
				args.add(noteRepo.getThirdCategory());
			}
		}
		sql.append(" and LENGTH(title) > 0 ");
//		if(!StringUtils.isEmpty(noteRepo.getTitle())) {
//			sql.append(" GROUP BY title ");
//		}
		sql.append(" order by createTime desc ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), NoteRepo.class);
	}

}
