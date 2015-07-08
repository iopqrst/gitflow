package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.ClientUploadExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ClientUploadDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.client.ClientUpload;

@Repository
@SuppressWarnings("unchecked")
public class ClientUploadDaoImpl extends BaseDaoImpl<ClientUpload> implements
		ClientUploadDao {

	public PageObject<ClientUpload> findClientUpload(ClientUpload cu,
			QueryInfo queryInfo) {
		String hql = "from ClientUpload cu where cu.clientId=? order by id desc";
		Object[] obj = { cu.getClientId() };
		return queryObjects(hql, obj, queryInfo);
	}

	public void addClientUpload(ClientUpload clientUpload) {
		add(clientUpload);
	}

	public ClientUpload findClinetUploadById(Integer id, Integer clientId) {
		String hql = "from ClientUpload where id=? and clientId=?";
		Object[] obj = { id, clientId };
		List<ClientUpload> lst = executeFind(hql, obj);
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}

	public PageObject<ClientUploadExtend> findClientUploadFiles(String areaChain,QueryInfo queryInfo,
			ClientUploadExtend c) {
		StringBuffer strsql = new StringBuffer();
		ArrayList args = new ArrayList();
		
		strsql.append("select tc.`name` AS clientName, tc.gender, tc.id, tc.mobile, tc.age, tc.areaId,tcu.id uploadId,tcu.clientId,tcu.uploadTime,tcu.filePath,tcu.`status` uploadStatus" +
				" from t_clientinfo tc LEFT JOIN t_client_upload tcu ON tc.id = tcu.clientId" +
				" WHERE tcu.id IS NOT NULL AND tc.STATUS = ?");
		args.add(Constant.STATUS_NORMAL);
		
		if(null != c) {
			if(null != c.getUploadStatus() && -1 != c.getUploadStatus()) {
				strsql.append(" AND tcu.status = ?");
				args.add(c.getUploadStatus());
			}
			if(!StringUtils.isEmpty(c.getClientName())) {
				strsql.append(" AND tc.name like ?");
				args.add(c.getClientName().trim()+"%");
			}
			if(!StringUtils.isEmpty(c.getMobile())) {
				strsql.append(" AND tc.mobile like ?");
				args.add(c.getMobile().trim()+"%");
			}
			if(null != c.getAreaId()) {
				strsql.append(" AND tc.areaId = ?");
				args.add(c.getAreaId());
			}
		}
		
		if(!StringUtils.isEmpty(areaChain)) {
			String[] areaChains = areaChain.split("#");
			strsql.append(" and ( ");
			
			if(areaChains.length > 1){
				for (int i = 0; i < areaChains.length; i++) {
					strsql.append(" tc.areaChain like ? ");
					args.add(areaChains[i]+"%");
					if(i != areaChains.length- 1) {
						strsql.append(" or");
					}
				}
			} else {
				strsql.append(" tc.areaChain like ? ");
				args.add(areaChains[0]+"%");
			}
			strsql.append(")");
		}
		
		strsql.append(" ORDER BY tcu.uploadTime desc ");
		
		return this.queryObjectsBySql(strsql.toString(), null, null, args.toArray(), queryInfo, ClientUploadExtend.class);
	}

	public int update(Integer id) {
		String hql = "from ClientUpload cu where cu.id=?";
		ArrayList args = new ArrayList();
		args.add(id);
		return this.updateByHql(hql, args);
	}

	
	public int findClientUploadFiles(String areaChain, ClientUploadExtend c) {
		StringBuffer strsql = new StringBuffer();
		ArrayList args = new ArrayList();
		strsql.append(" select count(*) from t_clientinfo t1" +
				" inner join t_client_upload t2 on t1.id = t2.clientId where t1.status = ?");
		
		args.add(Constant.STATUS_NORMAL);
		
		if(!StringUtils.isEmpty(areaChain)) {
			String[] areaChains = areaChain.split("#");
			strsql.append(" and ( ");
			
			if(areaChains.length > 1){
				for (int i = 0; i < areaChains.length; i++) {
					strsql.append(" t1.areaChain like ? ");
					args.add(areaChains[i]+"%");
					if(i != areaChains.length- 1) {
						strsql.append(" or");
					}
				}
			} else {
				strsql.append(" t1.areaChain like ? ");
				args.add(areaChains[0]+"%");
			}
			strsql.append(")");
		}
		if(null != c) {
			if(null != c.getUploadStatus() && -1 != c.getUploadStatus()) {
				strsql.append(" and t2.status = ?");
				args.add(c.getUploadStatus());
			}
		}
		
		Object obj = findUniqueResultByNativeQuery(strsql.toString(), args.toArray());
		if(obj != null && !obj.equals("")){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
}
