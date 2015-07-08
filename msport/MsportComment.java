package com.bskcare.ch.vo.msport;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_sport_comment")
public class MsportComment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int COMMENT_TYPE_GOOD = 0;
	public static final int COMMENT_TYPE_COMPLAIN = 1;
	
	private Integer id;
	private Integer clientId;
	/**要评价好的人的id**/
	private Integer friendId;
	/**评价类型  0：好评    1：投诉**/
	private int type;
	private Date createTime;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getFriendId() {
		return friendId;
	}
	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
