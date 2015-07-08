package com.bskcare.ch.service;

import java.util.List;
import java.util.Map;

import com.bskcare.ch.vo.AreaInfo;

public interface AreaInfoService {
	/**
	 * 根据id获得地区信息
	 * @param areaInfo
	 * @return
	 */
	public AreaInfo get(Integer areaInfo);
	/**
	 * 获取所有区域的信息 ,转行成json字符串格式保存
	 * 
	 * @param areaInfo
	 * @return
	 */
	public String getString4Ztree(AreaInfo areaInfo);

	/**
	 * 根据区域id删除此区域
	 * 
	 * @param id
	 *            区域id
	 * @return
	 */
	public String deleteAreaInfo(int id);

	/**
	 * 根据区域id更新区域name
	 * 
	 * @param id
	 *            区域id
	 * @param name
	 *            区域name
	 * @return
	 */
	public boolean updateByIdName(int id, String name);

	/**
	 * 添加区域信息
	 * 
	 * @param areaInfo
	 */
	public int addAreaInfo(AreaInfo areaInfo);

	/**
	 * 查询最大的区域id
	 * 
	 * @return
	 */
	public int findMaxId();
	
//	public String getAreaInfoForEditor(AreaInfo areaInfo ,int userId);
	/**
	 * 根据父ID找到子ID,依次查找，返回区域链
	 */
	public AreaInfo findAreaByParentId(Integer parentId);

	/**
	 * 根据主键编号和parentId添加区域链
	 */
	public void addAreaChain(Integer id);
	
	/**
	 * 传入区域，返回相应的区域链
	 * @param areaId 区域
	 * @return 区域链
	 */
	public String getAreaChainByAreaId(Integer areaId);
	
	/**
	 * 全查
	 */
	public List<AreaInfo> queryList(AreaInfo areaInfo);
	
	/**
	 * 删除(逻辑删除)
	 */
	public String deleteAreaInfo(AreaInfo areaInfo);
	
	/**
	 * 修改
	 */
	public String updateAreaInfo(AreaInfo areaInfo);
	
	/**
	 * 根据管理员区域链查询数据
	 */
	public String getAdminArea(List<String> list);
	/**
	 * 查询区域链下所有的区域名称
	 * @param areaChain 区域链
	 * @return
	 */
	public Map<String, String> getAllAreaName(String areaChain);
}
