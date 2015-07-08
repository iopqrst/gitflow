package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 体质分析(体质项目管理)
 * @author Administrator
 */

@Entity
@Table(name="t_cmc_analysis")
public class CmcAnalysis implements Serializable{
		private int id;
		private String name;
		private Date createTime;
		private int creator;
		/**
		 * 总体特征
		 */
		private String zongTiTeZheng;
		/**
		 * 形体特征
		 */
		private String xingTiTeZheng;
		/**
		 * 常见表现
		 */
		private String changJianBiaoXian;
		/**
		 * 心理特征
		 */
		private String xinLiTeZheng;
		/**
		 * 发病倾向
		 */
		private String faBingQingXiang;
		/**
		 * 适应能力
		 */
		private String shiYingNengLi;
		/**
		 * 总结
		 */
		private String summarize;
		
		/**
		 * 状态0启用   1禁用
		 */
		private int status;
		
		/**
		 * 正常
		 */
		public static final int ANALYSIS_NORMAL=0;
		/**
		 * 异常
		 */
		public static final int ANALYSIS_NOTNORMAL=1;
		
		
		
		@Id
		@GeneratedValue
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public int getCreator() {
			return creator;
		}
		public void setCreator(int creator) {
			this.creator = creator;
		}
		public String getZongTiTeZheng() {
			return zongTiTeZheng;
		}
		public void setZongTiTeZheng(String zongTiTeZheng) {
			this.zongTiTeZheng = zongTiTeZheng;
		}
		public String getXingTiTeZheng() {
			return xingTiTeZheng;
		}
		public void setXingTiTeZheng(String xingTiTeZheng) {
			this.xingTiTeZheng = xingTiTeZheng;
		}
		public String getChangJianBiaoXian() {
			return changJianBiaoXian;
		}
		public void setChangJianBiaoXian(String changJianBiaoXian) {
			this.changJianBiaoXian = changJianBiaoXian;
		}
		public String getXinLiTeZheng() {
			return xinLiTeZheng;
		}
		public void setXinLiTeZheng(String xinLiTeZheng) {
			this.xinLiTeZheng = xinLiTeZheng;
		}
		public String getFaBingQingXiang() {
			return faBingQingXiang;
		}
		public void setFaBingQingXiang(String faBingQingXiang) {
			this.faBingQingXiang = faBingQingXiang;
		}
		public String getShiYingNengLi() {
			return shiYingNengLi;
		}
		public void setShiYingNengLi(String shiYingNengLi) {
			this.shiYingNengLi = shiYingNengLi;
		}
		public String getSummarize() {
			return summarize;
		}
		public void setSummarize(String summarize) {
			this.summarize = summarize;
		}
		
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
}
