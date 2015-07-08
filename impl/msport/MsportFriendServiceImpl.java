package com.bskcare.ch.service.impl.msport;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.msport.MsportFriendDao;
import com.bskcare.ch.service.msport.MsportFriendService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.msport.MsportFriend;

@Service
public class MsportFriendServiceImpl implements MsportFriendService{

	@Autowired
	private MsportFriendDao friendDao;
	
	public String addMsportFriend(MsportFriend friend){
		JSONObject json = new JSONObject();
		if(friend != null){
			int count = friendDao.queryFriendById(friend.getClientId(), friend.getFriendId());
			if(count<=0){
				friendDao.add(friend);
				MsportFriend mfriend = new MsportFriend();
				mfriend.setClientId(friend.getFriendId());
				mfriend.setFriendId(friend.getClientId());
				mfriend.setCreateTime(friend.getCreateTime());
				friendDao.add(mfriend);
				json.put("code", 1);
				json.put("msg", "添加成功");
				json.put("data", JsonUtils.getJsonObject4JavaPOJO(friend));
			}else{
				json.put("code", 0);
				json.put("msg", "已经添加此好友，不能再添加");
				json.put("data", JsonUtils.getJsonObject4JavaPOJO(friend));
			}
		}else{
			json.put("code", 0);
			json.put("msg", "失败");
			json.put("data", "");
		}
		return json.toString();
	}
	
	
	public String deleteMsportFriend(Integer cid,Integer pid){
		JSONObject json = new JSONObject();
		int count = friendDao.deleteMsportFriend(cid, pid);
		int fcount = friendDao.deleteMsportFriend(pid, cid);
		if(count>=0&&fcount>0){
			json.put("code", 1);
			json.put("msg", "成功");
			json.put("data", "");
		}else{
			json.put("code", 0);
			json.put("msg", "失败");
			json.put("data", "");
		}
		return json.toString();
	}
}
