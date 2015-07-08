package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.ClientUploadExtend;
import com.bskcare.ch.bo.CmcTaskExtend;
import com.bskcare.ch.bo.TaskListPage;
import com.bskcare.ch.bo.UploadExtend;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.ClientUploadDao;
import com.bskcare.ch.dao.CmcTaskDao;
import com.bskcare.ch.dao.HealthLevelDao;
import com.bskcare.ch.dao.MonitoringDataDao;
import com.bskcare.ch.dao.TaskListDao;
import com.bskcare.ch.dao.TaskFlowDao;
import com.bskcare.ch.dao.UnusualDataDao;
import com.bskcare.ch.dao.online.OnlineSubscribeDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.TaskListService;
import com.bskcare.ch.service.UserInfoService;
import com.bskcare.ch.service.online.OnlineRecoredService;
import com.bskcare.ch.service.rpt.AutoGenerateRptService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.CmcTask;
import com.bskcare.ch.vo.HealthLevel;
import com.bskcare.ch.vo.TaskFlow;
import com.bskcare.ch.vo.TaskList;
import com.bskcare.ch.vo.UnusualDataExtends;
import com.bskcare.ch.vo.online.OnlineRecored;
import com.bskcare.ch.vo.online.OnlineSubscribe;
import com.bskcare.ch.vo.rpt.RptBaseInfo;

@Service
@SuppressWarnings("unchecked")
public class TaskListServiceImpl implements TaskListService {
	
	private static Logger log = Logger.getLogger(TaskListServiceImpl.class);
	
	@Autowired
	private TaskListDao taskListDao;
	@Autowired
	private TaskFlowDao taskFlowDao;
	@Autowired
	private ClientInfoDao clientInfoDao;
	@Autowired
	private HealthLevelDao healthLevelDao;
	@Autowired
	private CmcTaskDao cmcTaskDao;  //中医体质辨识任务
	@Autowired
	private ClientUploadDao clientUploadDao;  //用户上传数据任务
	@Autowired
	private MonitoringDataDao monitoringDataDao; //上传数据任务
	@Autowired
	private UnusualDataDao unusualDataDao;  //异常报警数据
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private OnlineSubscribeDao osubDao;

	@Autowired
	private AutoGenerateRptService autoRptService;
	
	@Autowired
	OnlineRecoredService onlineRecoredService;

	/**
	 * 添加任务
	 */
	public void addTask(TaskList task) {
		taskListDao.addTask(task);
	}

	/**
	 * 我的任务
	 */
	public PageObject<TaskListPage> myTaskList(TaskList task, QueryInfo info,List<String> types ,ClientInfo clientInfo,QueryCondition condition) {
		info.setOrder(QueryInfo.ORDER_ASC);
		info.setSort("t.creationTime");
		return taskListDao.myTaskList(task, info,  TaskList.STATUS_STATUS_OVER,types,clientInfo , condition);
	}

	/**
	 * 我的历史任务
	 */
	public PageObject<TaskListPage> myTaskListhistory(TaskList task, QueryInfo info,List<String> types ,ClientInfo clientInfo,QueryCondition condition) {
		info.setOrder(QueryInfo.ORDER_DESC);
		info.setSort("t.accomplishTime");
		return taskListDao.myTaskList(task, info,TaskList.TASK_STATUS_OFF,types,clientInfo,condition);
	}

	/**
	 * 自建任务列表
	 */

	public PageObject newTaskList(int userId, QueryInfo info) {
		//info.setOrder(QueryInfo.ORDER_ASC);
		//info.setSort("t.creationTime");
		return taskListDao.newTaskList(userId, info);
	}

	/**
	 * 添加自定任务流程
	 */
	public void addFlow(TaskFlow taskFlow) {
		taskFlowDao.addFlow(taskFlow);
	}

	/**
	 * 根据任务id获得任务包括用户信息
	 */
	public PageObject getTaskListById(int id, QueryInfo info) {
		return taskListDao.getTaskListById(id, info);

	}
	/**
	 * 更新任务
	 */
	public void updatask(TaskList task) {
		taskListDao.updateTask(task);
	}
	/**
	 * 根据任务id过得任务
	 */
	public TaskList getTaskById(int taskList) {
		return taskListDao.getTask(taskList);
	}
	/**
	 * 获得任务流程列表
	 */
	public PageObject getTaskFlowList(QueryInfo info) {
		return taskFlowDao.getTaskFlowList(info);
	}
	/**
	 * 删除任务流程
	 */
	public void deleteFlow(int id) {
		taskFlowDao.deleteFlow(id);
	}
	/**
	 * 自定义任务流程自动发布任务
	 */
	public void automaticallyAssignTasks() {
		log.info(LogFormat.b("自定义任务分发开始"));
		//发布自建的定时任务
		int count = taskListDao.releaseSelfTask();
		log.info(LogFormat.f("发布定时任务个数：" + count));
		
		// 定义的自动任务列表
		List<TaskFlow> taskFlows = taskFlowDao.getTaskFlowList();

		// 临时保存从crm接口获取相应管理员，避免相同数据发送多条请求
		Map<String, JSONArray> map = new HashMap<String, JSONArray>();
		
		int result = 0; //执行添加分配任务结果
		int total = 0, singleFlowCount = 0 ; //任务总数， 当个任务流总数量

		//循环每条流程
		for (TaskFlow taskFlow : taskFlows) {
			List<ClientInfo> clientLst = getClientListByTaskFlow(taskFlow);
			if(!CollectionUtils.isEmpty(clientLst))
				System.out.println("----renshu-----"+clientLst.size()+"---------");
			
			if(null == clientLst || clientLst.size() == 0) continue;
			
			for (ClientInfo clientInfo : clientLst) {
				System.out.println("-----id-----"+clientInfo.getId());
				System.out.println("-----available-----"+clientInfo.getAvailableProduct());
				result = assifnTasks(map,clientInfo, taskFlow);
					
				if(1 == result) { //添加成功
					total++;
					singleFlowCount++;
				}
				
				result = -1; //重置一下
			}
			log.info(LogFormat.f("符合当前任务流程的客户总数为：" + clientLst.size()) + ", 分配任务数为：" + singleFlowCount);
			singleFlowCount = 0; //重新开始
		}

		log.info(LogFormat.b("自定义任务分发结束------> 总过分配任务：" + total + "个"));
	}

	// 根据自定义任务计划发任务
	public List<ClientInfo> getClientListByTaskFlow(TaskFlow flow) {
		// 跟据定义计划条件，获得客户列表
		ClientInfo info = null;
		HealthLevel healthLevel = null;
		if (flow.getClientType() != -1) {
			if (info == null)
				info = new ClientInfo();
			info.setType(flow.getClientType() + "");
		}
		if (flow.getLevelId() != -1) {
			if (info == null)
				info = new ClientInfo();
			info.setLevelId(flow.getLevelId());
		}
		if (flow.getHealthLevel() != -1) {
			healthLevel = new HealthLevel();
			healthLevel = healthLevelDao.getHealthLevel(flow.getHealthLevel());
		}
		
		return clientInfoDao.findClientInfo(info,
				healthLevel, flow, 1); //1是为了标识黑户不发信息
	}

	/**
	 * 发布任务
	 * @param map 存放管理员的map (根据角色和区域标识)
	 * @param clientInfo client 
	 * @param taskFlow 任务流程
	 * @return 0: 失败 1：成功
	 */
	public int assifnTasks(Map<String, JSONArray> map , ClientInfo clientInfo, TaskFlow taskFlow) {
		TaskList task = new TaskList();
		task.setClientId(clientInfo.getId());
		task.setCreationUser(-1); //系统自动创建为-1
		task.setCreationTime(new Date());
		task.setTparticulars(taskFlow.getParticulars());
		task.setType(TaskList.TASK_TYPE_URGENCY);
		task.setStatus(TaskList.TASK_STATUS_OFF);

		if(taskFlow.getRoleId().toString().equals(SystemConfig.getString("role_type_principal"))){//如果接收人事代理商负责人
			if(clientInfo.getPrincipalId()!=null){
				task.setReceiveUser(clientInfo.getPrincipalId());
			}else{
				return 0;
			}
		} else {//如果接收人不是负责人
			int ruser = userInfoService.getUserId(map,taskFlow.getRoleId(), clientInfo.getAreaId());
			task.setReceiveUser(ruser);// 接收人 根据地区id && 角色类型 获得
		}
		taskListDao.addTask(task);
		return 1;
	}
	
	/**
	 * 新升vip提醒，倒计时任务1小时，助理医生
	 */
	public void levelUp(int areaId, int clientInfo){
//		int userId = userInfoService.getUserId(null,Integer.parseInt(SystemConfig.getString("role_type_doc")),areaId);//获得医生的id
//		levelUpAll(clientInfo,userId);//医生的提醒
		int userId = userInfoService.getUserId(null,Integer.parseInt(SystemConfig.getString("role_type_health_manger")),areaId);//获得健康管理师的id
		levelUpAll(clientInfo,userId);//健康管理师的提醒
	}
	/**
	 * 为其他角色添加升级提醒
	 */
	public void levelUpAll(Integer cid,Integer userid){
		TaskList taskList=new TaskList();
		taskList.setClientId(cid);
		taskList.setTparticulars("该用户已经升级vip，请工作人员注意 ， 添加随访问候。");
		taskList.setType(TaskList.TASK_TYPE_COUNTDOWN);
        Calendar now = Calendar.getInstance();  
        now.setTime(new Date());  
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + 1);  
		taskList.setCountDown(now.getTime());
		taskList.setCreationTime(new Date());
		taskList.setCreationUser(-1);
		taskList.setStatus(0);
		
		log.info(LogFormat.f("用户升级，添加提醒 >> userId "+userid));
		
		taskList.setReceiveUser(userid);
		if(taskList.getReceiveUser()!=null){
			taskListDao.addTask(taskList);
		}
	}
	
	

	/**
	 * 删除任务
	 */
	public void deleteTask(int id) {
		taskListDao.deleteTask(id);
	}

	//查询用户代办事项信息
	public String queryBacklog(String menus,String areaChain,Integer userId){
		String menuList[]= menus.split(",");
		List<String> menuData = new ArrayList<String>();
        for (int i = 0; i < menuList.length; i++) {
            String s = menuList[i];
            if (!menuData.contains(s)) {
            	menuData.add(s);
            }
        }
		JSONObject json = new JSONObject();
		if(menuList != null&&menuList.length>0){
			JSONObject jo = new JSONObject();
			for (String menu : menuData) {
				if(menu.equals("cmcTask")){
					CmcTaskExtend taskExtend = new CmcTaskExtend();
					taskExtend.setStatus(CmcTask.CMC_TASK_NOT_DEAL);
					int count = cmcTaskDao.findCmcTaskExtend(taskExtend, areaChain);
					jo.put("backlog_cmcTask", count);
					continue;
				}
				if(menu.equals("upload")){
					ClientUploadExtend uploadExtend = new ClientUploadExtend();
					uploadExtend.setUploadStatus(0);
					int count = clientUploadDao.findClientUploadFiles(areaChain, uploadExtend);
					jo.put("backlog_upload", count);
					continue;
				}
				if(menu.equals("uploadData")){
					UploadExtend uploadExtend = new UploadExtend();
					uploadExtend.setDispose(0);
					QueryCondition queryCondition = new QueryCondition();
					if(!StringUtils.isEmpty(areaChain)){
						queryCondition.setAreaChain(areaChain);
						setQueryConditionTime(queryCondition);
					}
					int count = monitoringDataDao.queryObject(uploadExtend, queryCondition);
					jo.put("backlog_uploadData", count);
					continue;
				}
				if(menu.equals("unusualData")){
					UnusualDataExtends dataExtends = new UnusualDataExtends();
					dataExtends.setStatus(0);
					int count = unusualDataDao.queryUnusualDataList(dataExtends, areaChain);
					jo.put("backlog_unusualData", count);
					continue;
				}
				if(menu.equals("mytask")){
					TaskList task = new TaskList();
					task.setReceiveUser(userId);
					task.setStatus(TaskList.TASK_STATUS_OFF);
					int count = taskListDao.myTaskList(task, 1, null, null, null);
					jo.put("backlog_mytask", count);
					continue;
				}
				if(menu.equals("osub")){
					OnlineSubscribe osub = new OnlineSubscribe();
					osub.setStatus(OnlineSubscribe.STATUS_SUBSCRIBING);
					QueryCondition query = new QueryCondition();
					query.setUserId(userId);
					int count = osubDao.querySubscribeByUserId(osub, query);
					jo.put("backlog_osub", count);
				}
				if(menu.equals("noRead")){
					//未读消息
					OnlineRecored recored = new OnlineRecored();
					recored.setStatus(OnlineRecored.STATUS_UNREAD);
					recored.setType(OnlineRecored.TYPE_SEND_TO_DOC);
					recored.setReceiver("d_"+userId);
					int count = onlineRecoredService.queryOnlineRecoredCount(recored);
					jo.put("noRead", count);
				}
			}
			json.put("backlogList", jo.toString());
		}
		return json.toString();
	}
	
	/**
	 * 设置查询时间范围
	 * @param queryCondition
	 */
	private void setQueryConditionTime(QueryCondition queryCondition){
		Date current = new Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		
		Date beginTime = DateUtils.getDateByType(calendar.getTime(), DateUtils.DATE_PATTERN);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date endTime = DateUtils.getDateByType(calendar.getTime(), DateUtils.DATE_PATTERN);
		
		queryCondition.setBeginTime(beginTime);
		queryCondition.setEndTime(endTime);

	}
	
	public RptBaseInfo dealRptTask(Integer clientId, Integer taskId){
		RptBaseInfo rptBase = autoRptService.generateRpt(clientId);
		if(taskId != null && taskId != 0){
			taskListDao.updateTaskById(taskId);
		}
		return rptBase;
	}
	
	public void registerRemind(int areaId, int clientId) {
		int userId = userInfoService.getUserId(null,Integer.parseInt(SystemConfig.getString("role_type_health_manger")),areaId);//获得健康管理师的id
		TaskList taskList=new TaskList();
		taskList.setClientId(clientId);
		taskList.setTparticulars("体验用户注册，请管理人员给予关注。");
		taskList.setType(TaskList.TASK_TYPE_URGENCY);
//        Calendar now = Calendar.getInstance();  
//        now.setTime(new Date());  
//        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + 1);  
//		taskList.setCountDown(now.getTime());
		taskList.setCreationTime(new Date());
		taskList.setCreationUser(-1);
		taskList.setStatus(0);
		System.out.println("用户注册，添加提醒 >> userId "+userId);
		taskList.setReceiveUser(userId);
		if(taskList.getReceiveUser()!=null){
			taskListDao.addTask(taskList);
		}
	}
}
