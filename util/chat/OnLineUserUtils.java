package com.bskcare.ch.util.chat;

import java.util.Date;
import java.util.Vector;

import com.bskcare.ch.vo.UserInfo;

@SuppressWarnings("unchecked")
public class OnLineUserUtils {
	private final static Object syncLock = new Object();
	/*
	 * OnLineUserUtils是一个单例，所以每次都只会是同一个
	 * 直接在Vector中存放整个对象而不是id，因为id是相同的，当用户多个地点登陆时判断的都是一个id，
	 * 直接添加对象，用户每次登陆都会创建一个新的对象，每个对象都不相同。所以可以分别判断用户不同地点登陆。
	 * vector就是所有当前在线的用户。可以统计人数啊、查看在线状态等等等等。。
	 * */
	private Vector users = new Vector();
	
	private static OnLineUserUtils userUtils;
	
	private OnLineUserUtils(){}
	
	public static OnLineUserUtils getOnLineUserUtils(){
		if(userUtils == null){
			synchronized (syncLock) {
				if(userUtils == null){
					userUtils = new OnLineUserUtils();
				}
			}
		}
		return userUtils;
	}
	/**
	 * 是否存在该对象
	 * @param id
	 * @return
	 */
	public boolean existUser(String id) {
		users.trimToSize();
		boolean existUser = false;
		for (int i = 0; i < users.capacity(); i++) {
			String str = ((UserInfo)users.get(i)).getId()+"";
			if (id.equals(str)) {
				existUser = true;
				break;
			}
		}
		return existUser;
	}
	/**
	 * 是否存在该对象
	 * @param user 直接在
	 * @return
	 */
	public boolean existUser(UserInfo user) {
		users.trimToSize();
		boolean existUser = false;
		for (int i = 0; i < users.capacity(); i++) {
			if (user.equals((UserInfo)users.get(i))) {
				existUser = true;
				break;
			}
		}
		return existUser;
	}
	/**
	 * 删除一个对象
	 * @param userinfo
	 * @return
	 */
	public boolean deleteUser(UserInfo userinfo) {
		System.out.println("session失效:"+userinfo.getName());
		users.trimToSize();
		if (existUser(userinfo.getId()+"")) {
			System.out.println("在线状态中有"+userinfo.getName());
			int currUserIndex = -1;
			for (int i = 0; i < users.capacity(); i++) {
				UserInfo uu = (UserInfo)users.get(i);
				if (userinfo.getId().equals(uu.getId())) {
					currUserIndex = i;
					break;
				}
			}
			if (currUserIndex != -1) {
				System.out.println("删除用户在线状态");
				users.remove(currUserIndex);
				users.trimToSize();
//				System.out.println("系统保存的登录状态还有"+users.size()+"个");
				return true;
			}
		}
		return false;
	}

	public Vector getOnLineUser() {
		return users;
	}
	
	public void addUser(UserInfo userinfo){
		users.trimToSize();
		System.out.println("添加用户登录状态，用户判断用户是否");
		System.out.println("请求：：：：：：：：：：：" + userinfo.getName());
		if (!existUser(userinfo)) {
			users.add(userinfo);
			System.out.println( userinfo.getName() + " 登入到系统 " + (new Date()));
//			System.out.println("系统保存的登录状态还有"+users.size()+"个");
		} else {
			System.out.println( userinfo.getName() + "已经存在");
		}
		
	}

}
