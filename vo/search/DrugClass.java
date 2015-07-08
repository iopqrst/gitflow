package com.bskcare.ch.vo.search;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_drug_class")
public class DrugClass {
		@Id
		@GeneratedValue
        private Integer id;//主键
		
		private String cmc;//药品分类名称
	    
		private String cbm;//数据库唯一表示
	    
		private Integer ifljb;// 3是2 的子菜单 
	    
		private String dfl;//表标识
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getCmc() {
			return cmc;
		}
		public void setCmc(String cmc) {
			this.cmc = cmc;
		}
		public String getCbm() {
			return cbm;
		}
		public void setCbm(String cbm) {
			this.cbm = cbm;
		}
		
		public Integer getIfljb() {
			return ifljb;
		}
		public void setIfljb(Integer ifljb) {
			this.ifljb = ifljb;
		}
		public String getDfl() {
			return dfl;
		}
		public void setDfl(String dfl) {
			this.dfl = dfl;
		}
		
	    
}
