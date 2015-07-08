package com.bskcare.ch.service.msport;

import com.bskcare.ch.vo.msport.MsportFriend;


public interface MsportFriendService{
	/**添加好友**/
	public String addMsportFriend(MsportFriend friend);
	
	/**根据用户id和好友id删除好友**/
	public String deleteMsportFriend(Integer cid,Integer pid);
}
