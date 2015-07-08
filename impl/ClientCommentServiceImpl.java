package com.bskcare.ch.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.ClientCommentDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientCommentService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientComment;

@Service
@SuppressWarnings("unchecked")
public class ClientCommentServiceImpl implements ClientCommentService{
		
	@Autowired
	private ClientCommentDao commentDao;
	
	/**
	 * 查询用户id查询的备注信息
	 */
	public List<ClientComment> queryClinetComment(ClientComment comment){
		return commentDao.queryClinetComment(comment);
	}
	
	
	/**
	 * 根据用户id给用户添加用户备注
	 */
	public void addClientComment(ClientComment comment){
		if(comment != null){
			if(comment.getLevel() == 1){
				List<ClientComment> lst = commentDao.queryClinetComment(comment);
				if(!CollectionUtils.isEmpty(lst)){
					ClientComment commentInfo = lst.get(0);
					commentInfo.setComment(comment.getComment());
					commentInfo.setTitle(comment.getTitle());
					commentInfo.setUserId(comment.getUserId());
					commentInfo.setCreateTime(new Date());
					commentDao.update(commentInfo);
				}else{
					commentDao.add(comment);
				}
			}else{
				commentDao.add(comment);
			}
		}
	}
	
	public List queryClinetCommentUserName(Integer clientId,ClientComment comment){
		return commentDao.queryClinetCommentUserName(clientId,comment);
	}
	
	public PageObject queryClientCommentUserInfo(ClientComment comment,QueryInfo queryInfo){
		return commentDao.queryClientCommentUserInfo(comment, queryInfo);
	}
	
	
	/**查询用户备注**/
	public String ajaxQueryClientComment(Integer clientId,ClientComment comment){
		
		JSONObject json = new JSONObject();
		List<Object> lst = (List<Object>)commentDao.queryClinetCommentUserName(clientId, comment);
		if(!CollectionUtils.isEmpty(lst)){
			JSONArray ja = new JSONArray();
			for (Object objs : lst) {
				Object[] obj = (Object[])objs;
				ClientComment comm = (ClientComment)obj[0];
				JSONObject jo = new JSONObject();
				if(comm != null){
					jo.put("id", comm.getId());
					jo.put("comment", comm.getComment());
					jo.put("level", comm.getLevel());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(!StringUtils.isEmpty(comm.getTitle())){
						jo.put("title", comm.getTitle());
					}else{
						jo.put("title", "");
					}
					jo.put("createTime", sdf.format(comm.getCreateTime()));
				}
				if(obj[1] != null){
					jo.put("userName", obj[1]);
				}else{
					jo.put("userName", "");
				}
				
				ja.add(jo);
			}
			
			json.put("list", ja);
		}else{
			json.put("list", "");
		}
		
		return json.toString();
	}
	
	public String queryClientComment(ClientComment comment){
		JSONObject jo = new JSONObject();
		if(comment != null && comment.getClientId() != null){
			List<ClientComment> lst = commentDao.queryClinetComment(comment);
			if(!CollectionUtils.isEmpty(lst)){
				String data = JsonUtils.getJsonString4JavaListDate(lst, "yyyy-MM-dd HH:mm:ss");
				jo.put("data", data);
			}else{
				jo.put("data", "");
			}
		}
		return jo.toString();
	}
}
