package com.bskcare.ch.service.impl.timeLine;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.BloodSugarDao;
import com.bskcare.ch.dao.timeLine.EvaluatingResultDao;
import com.bskcare.ch.dao.timeLine.TimeLineContentDao;
import com.bskcare.ch.dao.timeLine.TimeLineTaskDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientInfoService;
import com.bskcare.ch.service.score.ScoreRecordService;
import com.bskcare.ch.service.timeLine.TimeLineTaskService;
import com.bskcare.ch.timeLine.TimeLineContentUtils;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.WeatherUtils;
import com.bskcare.ch.util.jpush.JpushUtil;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.tg.MedicineReminder;
import com.bskcare.ch.vo.timeLine.EvaluatingResult;
import com.bskcare.ch.vo.timeLine.TimeLineRule;
import com.bskcare.ch.vo.timeLine.TimeLineTask;

@Service
@SuppressWarnings("unchecked")
public class TimeLineTaskServiceImpl implements TimeLineTaskService {
	
	private final static Logger log = Logger.getLogger(TimeLineTaskServiceImpl.class);

	@Autowired
	private TimeLineTaskDao timeLineTaskDao;
	@Autowired
	private TimeLineContentDao contentDao;
	@Autowired
	private BloodSugarDao bloodSugarDao;
	@Autowired
	private EvaluatingResultDao evaluatingResultDao;
	@Autowired
	private ClientInfoService clientInfoService;
	
	@Autowired
	private ScoreRecordService scoreService;

	public List<TimeLineTask> queryListToNoLogin() {
		return timeLineTaskDao.queryListToNoLogin();
	}

	public PageObject<TimeLineTask> queryListToNoLogin(TimeLineTask lineTask,
			QueryInfo info) {
		return timeLineTaskDao.queryListToNoLogin(lineTask, info);
	}

	public void addFixTask(TimeLineTask lineTask) {
		if (lineTask != null) {
			lineTask.setCreateTime(new Date());
			lineTask.setStauts(TimeLineTask.STAUTS_FIX);
			timeLineTaskDao.add(lineTask);
		}
	}

	public List<TimeLineTask> queryList(Integer cid, Integer day) {
		int evalStatus = 0;// 标记用户做过评测的状态
		EvaluatingResult result = new EvaluatingResult();
		result.setClientId(cid);
		result.setSoftType(Constant.SOFT_GUAN_XUE_TANG);
		
		//FIXME 这里其实只要查询一下count就行了，没必要把所有list都查询出来
		// 查询用户的上传的评测结果
		List<EvaluatingResult> results = evaluatingResultDao
				.queryResultsByClientId(result);
		
		if (!CollectionUtils.isEmpty(results)) {
//			result = results.get(0);
//			int daysum = 0; // 请求参数
//			if (day != null && day == 0) {// 如果等于零
//				daysum = -1;// 获得昨天日期
//			} else if (day != null && day == 2) {// 如果等于二
//				daysum = 1; // 过得明天日期
//			}// 否则获得今天
		} else {
			// 用户没有上传过评测结果
			evalStatus = TimeLineTask.TASKTYPE_NOTHAVE;
		}
		return timeLineTaskDao.queryList(cid, day, evalStatus);
	}

	public void overTask(Integer cid, Integer taskId, Integer stauts) {
		timeLineTaskDao.overTask(cid, taskId, stauts);
		
		TimeLineTask task = timeLineTaskDao.load(taskId);
		if(task.getConType() != null){
			Integer conType = task.getConType();
			if(conType == 1 || conType == 2 || conType == 3){
			}
		}
	}

	public String getDietTaskFillRate(Integer cid, Integer type) {
		Date newdate = DateUtils.getDateByType(DateUtils.getAppointDate(
				new Date(), 1), DateUtils.DATE_PATTERN);
		// 本周1日期
		Date thisMonDay = DateUtils.getDateByType(DateUtils.getThisWeekDay(1,
				false), DateUtils.DATE_PATTERN);

		// 上周1日期
		Date lastMonDay = DateUtils.getAppointDate(thisMonDay, -7);// 查询上周日期，结束时间应为本周一0点
		double dou = 0.0;
		// 上周饮食任务总数
		int LWcount = timeLineTaskDao.dietCount(cid, lastMonDay,thisMonDay, null, type);
		// 上周饮食任务完成数
		int LWTab = timeLineTaskDao.dietCount(cid, lastMonDay,thisMonDay, 1, type);

		// 本周周饮食任务总数
		int TWcount = timeLineTaskDao.dietCount(cid, thisMonDay,newdate, null, type);
		// 本周饮食任务完成数
		int TWTab = timeLineTaskDao.dietCount(cid, thisMonDay,newdate, 1, type);
		JSONObject jo = new JSONObject();
		jo.put("LWCount", LWcount);
		jo.put("LWTab", LWTab);
		jo.put("LWNOTTab", timeLineTaskDao.dietCount(cid, lastMonDay,thisMonDay, 2, type));
		if (LWcount == 0 || LWTab == 0) {
			jo.put("LWRatio", "0");
		} else {
			dou = LWTab * 100 / LWcount;
			jo.put("LWRatio", (int) dou);
		}
		jo.put("TWCount", TWcount);
		jo.put("TWTab", TWTab);
		jo.put("TWNOTTab", timeLineTaskDao.dietCount(cid, thisMonDay,newdate, 2, type));
		if (TWcount == 0 || TWTab == 0) {
			jo.put("TWRatio", "0");
		} else {
			dou = TWTab * 100 / TWcount;
			jo.put("TWRatio", (int) dou);
		}
		return jo.toString();
	}

	public int getTaskFillRate(Integer cid) {
		int LWcount = timeLineTaskDao.todayCount(cid, null);
		int LWTab = timeLineTaskDao.todayCount(cid, TimeLineTask.STAUTS_NO);
		int Ratio = 0;
		if (LWcount != 0 && LWTab != 0) {
			Ratio = LWTab * 100 / LWcount;
		}
		return Ratio;
	}

	public String getDietIdentical(Integer cid) {
		List list = timeLineTaskDao.getDietIdentical(cid, 3);
		Object[] objs;
		JSONArray ja = new JSONArray();
		JSONObject jo;
		for (Object object : list) {
			objs = (Object[]) object;
			jo = new JSONObject();
			jo.put("date", DateUtils.formatDate("yyyy-MM-dd", objs[1]));
			jo.put("tab", objs[0]);
			ja.add(jo);
		}
		return ja.toString();
	}

	public String getBDTaskFillRate(Integer cid) {

		double dou = 0.0;
		// 获得一周内上传血糖数量
		Date newdate = DateUtils.getDateByType(DateUtils.getAppointDate(
				new Date(), 1), DateUtils.DATE_PATTERN);
		// 本周1日期
		Date thisMonDay = DateUtils.getDateByType(DateUtils.getThisWeekDay(1,
				false), DateUtils.DATE_PATTERN);

		// 上周1日期
		Date lastMonDay = DateUtils.getAppointDate(thisMonDay, -7);// 查询上周日期，结束时间应为本周一0点

		// 本周上传次数
		int thisWeekNum = bloodSugarDao.quertBloodSugar(cid, thisMonDay,
				newdate, null);
		// 上周上传次数
		int lastWeekNum = bloodSugarDao.quertBloodSugar(cid, lastMonDay,thisMonDay, null);
		// 本周任务数量
		int TWcount = timeLineTaskDao.dietCount(cid, thisMonDay,newdate, null, 2);
		// 上周任务数量
		int LWcount = timeLineTaskDao.dietCount(cid, lastMonDay,thisMonDay, null, 2);
		JSONObject jo = new JSONObject();
		// 上周
		jo.put("LWCount", LWcount);
		jo.put("LWTest", lastWeekNum);
		if (lastWeekNum == 0 || LWcount == 0) {
			jo.put("LWRatio", 0);
		} else {
			dou = lastWeekNum * 100 / LWcount;
			jo.put("LWRatio", (int) dou);
		}
		// 本周
		jo.put("TWCount", TWcount);
		jo.put("TWTest", thisWeekNum);
		if (TWcount == 0 || thisWeekNum == 0) {
			jo.put("TWRatio", 0);
		} else {
			dou = thisWeekNum * 100 / TWcount;
			jo.put("TWRatio", (int) dou);
		}

		// 本周数据
		jo.put("TWIdeal", bloodSugarDao.quertBloodSugar(cid, thisMonDay,
				newdate, BloodSugar.IDEAL_STAUTS));
		jo.put("TWWell", bloodSugarDao.quertBloodSugar(cid, thisMonDay,
				newdate, BloodSugar.WELL_STAUTS));
		jo.put("TWBadness", bloodSugarDao.quertBloodSugar(cid, thisMonDay,
				newdate, BloodSugar.BADNESS_STAUTS));
		// 上周数据
		jo.put("LWIdeal", bloodSugarDao.quertBloodSugar(cid, lastMonDay,
				thisMonDay, BloodSugar.IDEAL_STAUTS));
		jo.put("LWWell", bloodSugarDao.quertBloodSugar(cid, lastMonDay,
				thisMonDay, BloodSugar.WELL_STAUTS));
		jo.put("LWBadness", bloodSugarDao.quertBloodSugar(cid, lastMonDay,
				thisMonDay, BloodSugar.BADNESS_STAUTS));
		return jo.toString();
	}

	public void del(Integer id) {
		timeLineTaskDao.delete(id);
	}

	public void update(TimeLineTask lineTask) {
		timeLineTaskDao.update(lineTask);
	}

	public TimeLineTask getLineTaskById(Integer id) {
		return timeLineTaskDao.load(id);
	}

	public void updateFixTask(TimeLineTask lineTask) {
		lineTask.setCreateTime(new Date());
		lineTask.setStauts(TimeLineTask.STAUTS_FIX);
		timeLineTaskDao.update(lineTask);
	}

	public void updateClienttMedicineReminder(Integer cid,
			List<MedicineReminder> list) {
		if (!CollectionUtils.isEmpty(list)) {
			timeLineTaskDao.deleteTask(cid, new Date(), DateUtils
					.getDateByType(DateUtils.getAppointDate(new Date(), 1),
							"yyyy-MM-dd"), TimeLineRule.CONTYPE_EATMEDICINE);
			for (MedicineReminder reminder : list) {
				reminder.setClientId(cid);
				if (!StringUtils.isEmpty(reminder.getAlertTime())
						&& !StringUtils.isEmpty(reminder.getDrugName())) {
					addMedicineReminderTask(reminder, new Date());// 今天的
				}

			}
		}
	}

	public int addMedicineReminderTask(MedicineReminder reminder, Date date) {
		if (reminder == null || reminder.getClientId() == null
				|| StringUtils.isEmpty(reminder.getAlertTime())
				|| StringUtils.isEmpty(reminder.getDrugName())) {
			log.warn("params has empty!");
			return -1;
		}
		
		TimeLineTask lineTask = new TimeLineTask();
		lineTask.setTaskDate(DateUtils.parseDate(DateUtils.formatDate(
				DateUtils.DATE_PATTERN, date)
				+ " " + reminder.getAlertTime(), "yyyy-MM-dd HH:mm"));
		
		//FIXME 这种情况会导致凌晨0点到3点之前的服药提醒不会在时间轴里出现，解决方法可以把3点的定时任务改到0点执行即可 
		if (lineTask.getTaskDate().after(new Date())
				|| lineTask.getTaskDate().equals(new Date())) {

			lineTask.setClientId(reminder.getClientId());
			lineTask.setContent(TimeLineContentUtils.getTimeLineContent(null,
					TimeLineRule.CONTYPE_EATMEDICINE, null, contentDao)
					.getContent()
					+ "\n本次服用药物：" + reminder.getDrugName());
			
			lineTask.setConType(TimeLineRule.CONTYPE_EATMEDICINE);
			lineTask.setCreateTime(new Date());
			lineTask.setTitle("服药提醒");
			lineTask.setTaskTime(reminder.getAlertTime());
			lineTask.setTaskType(TimeLineTask.TASKTYPE_LOGIN);
			lineTask.setIsAlert(TimeLineRule.ISALERT_YES);
			lineTask.setStauts(TimeLineTask.STAUTS_NO);
			lineTask.setOpenMode(TimeLineRule.OPENMODE_OPENCONTENT);
			lineTask.setSkipGoal(TimeLineRule.SKIPGOAL_NO);
			lineTask.setResetType(TimeLineTask.RESETTYPE_AUTO);
			
			timeLineTaskDao.add(lineTask);
			return 0;
		} else {
			log.info(LogFormat.f("由于时间的限制导致该服药提醒任务没有添加成功。"));
			return -2;
		}
	}

	public void deleteTaskByUpEvaluating(Integer cid) {
		timeLineTaskDao.deleteTaskByUpevaluating(cid,
				TimeLineRule.CONTYPE_EATMEDICINE, 1);
	}
	public String getTimeLineTask(Integer cid, Integer day) {
		List<TimeLineTask> lineTasks = null;
		ClientInfo clientInfo = null;
		if (cid != null) {
			clientInfo = clientInfoService.get(cid);
		}
		if (clientInfo != null) {
			lineTasks = queryList(cid, day);
		} else {
			lineTasks = queryListToNoLogin();
		}
		JSONArray js = new JSONArray();
		JSONObject jdata = null;
		for (TimeLineTask task : lineTasks) {
			jdata = new JSONObject();
			jdata.put("id", task.getId());
			jdata.put("title", task.getTitle());
			jdata.put("taskTime", task.getTaskTime());
			if (day == null || day == 1) {// 如果请求的是今天的正常显示
				jdata.put("openMode", task.getOpenMode());
			} else {// 如果请求不是今天，没有跳转按钮
				jdata.put("openMode", TimeLineRule.OPENMODE_OPENCONTENT);
			}
			jdata.put("stauts", task.getStauts());
			jdata.put("skipGoal", task.getSkipGoal());
			jdata.put("content", task.getContent());
			if (task.getConType() != null) {
				jdata.put("conType", task.getConType());
			} else {
				jdata.put("conType", 0);
			}
			if(StringUtils.isEmpty(task.getUrl())){
				jdata.put("url", "");
			}else{
				jdata.put("url", task.getUrl());
			}
			js.add(jdata);
		}
		return js.toString();
	}

	public void addClientCustomsTask(String tanks, Integer cid) {
		timeLineTaskDao.deleteClientCustomsTask(cid, null);
		if (!StringUtils.isEmpty(tanks)) {
			List<TimeLineTask> list = JsonUtils.getListJsonDate(tanks,
					TimeLineTask.class, DateUtils.DATE_PATTERN);
			if (!CollectionUtils.isEmpty(list)) {
				for (TimeLineTask task : list) {
					task.setClientId(cid);
					task.setId(null);
					task.setConType(TimeLineRule.CONTYPE_USERCUSTOM);
					task.setCreateTime(new Date());
					task.setIsAlert(TimeLineRule.ISALERT_NO);
					task.setOpenMode(TimeLineRule.OPENMODE_OPENCONTENT);
					task.setSkipGoal(TimeLineRule.SKIPGOAL_NO);
					task.setSoftType(Constant.SOFT_GUAN_XUE_TANG);
					task.setStauts(TimeLineTask.STAUTS_NO);
					if (task.getTaskDate() != null) {
						task
								.setTaskDate(DateUtils.parseDate(DateUtils
										.formatDate(DateUtils.DATE_PATTERN,
												task.getTaskDate())
										+ " " + task.getTaskTime(),
										"yyyy-MM-dd HH:mm"));
					}
					task.setTaskTime(task.getTaskTime());
					task.setTaskType(TimeLineTask.TASKTYPE_LOGIN);
					timeLineTaskDao.add(task);
				}
			}
		}

	}

	public void deleteClientCustomsTask(Integer cid, Integer taskId) {
		timeLineTaskDao.deleteClientCustomsTask(cid, taskId);
	}

	public String getClientCustomsTask(Integer cid, Integer softType) {
		JSONArray ja = new JSONArray();
		List<TimeLineTask> lineTasks = timeLineTaskDao.getClientCustomsTask(
				cid, softType);
		if (CollectionUtils.isEmpty(lineTasks))
			return null;
		JSONObject jo = null;
		for (TimeLineTask timeLineTask : lineTasks) {
			jo = new JSONObject();
			jo.put("id", timeLineTask.getId());
			jo.put("title", timeLineTask.getTitle());
			if (StringUtils.isEmpty(timeLineTask.getContent())) {
				jo.put("content", "");
			} else {
				jo.put("content", timeLineTask.getContent());
			}
			jo.put("taskDate", DateUtils.formatDate(DateUtils.DATE_PATTERN,
					timeLineTask.getTaskDate()));
			if (StringUtils.isEmpty(timeLineTask.getTaskTime())) {
				jo.put("taskTime", "");
			} else {
				jo.put("taskTime", timeLineTask.getTaskTime());
			}
			jo.put("resetType", timeLineTask.getResetType());
			ja.add(jo);
		}
		return ja.toString();
	}

	public void add(TimeLineTask timeLineTask) {
		timeLineTaskDao.add(timeLineTask);
	}

	public int addWeatherTask(Integer cid, double longitude, double latitude,
			Integer soft,String city, Map<String, String> map) {
		// 判断用户今日有没有天气预报时间轴
		//int con = timeLineTaskDao.queryTaskIsHas(cid,
		//		TimeLineRule.CONTYPE_WEATHER);
		//if (con <= 0)
		//	return;
		// 获得天气
		String weather ="";
		
		//FIXME map 存放相同的经纬度，避免重复请求
		//判断map不为空并且有对应城市，直接获取
//		if(map !=null && map.containsKey(city)){
//				weather = map.get(city);
//		} else {//否则请求接口
			//获得天气预报，如果返回错误，从新请求，最多请求3次
			for (int i = 0; i < 3; i++) {
				weather = getLineTaskWeather(longitude+","+latitude,city);
				if(!StringUtils.isEmpty(weather)) {
					break;
				} else {
//					try {
//						Thread.sleep(500);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
				}
			}
			if(StringUtils.isEmpty(weather)) return 3 ;//如果请求3次仍无法获得天气信息，返回3，不添加天气信息
			
			//判断map不为空，放到map里，否则直接进行任务生成
//			if(map == null) map = new HashMap<String, String>();
//			map.put(city, weather);
//		}
		
		//如果天气信息不为空，添加天气预报，返回2
		TimeLineTask lineTask = new TimeLineTask();
		lineTask.setTaskDate(DateUtils.parseDate(DateUtils.formatDate(
				DateUtils.DATE_PATTERN, new Date())
				+ " 08:10", "yyyy-MM-dd HH:mm"));
		if (lineTask.getTaskDate().after(new Date())
				|| lineTask.getTaskDate().equals(new Date())) {
		}
		lineTask.setClientId(cid);
		lineTask.setContent(weather);
		lineTask.setConType(TimeLineRule.CONTYPE_WEATHER);
		lineTask.setCreateTime(new Date());
		lineTask.setTitle("今日天气预报");
		lineTask.setTaskTime("08:10");
		lineTask.setTaskType(TimeLineTask.TASKTYPE_LOGIN);
		lineTask.setIsAlert(TimeLineRule.ISALERT_YES);
		lineTask.setStauts(TimeLineTask.STAUTS_NO);
		lineTask.setSoftType(soft);
		lineTask.setOpenMode(TimeLineRule.OPENMODE_OPENCONTENT);
		lineTask.setSkipGoal(TimeLineRule.SKIPGOAL_NO);
		lineTask.setResetType(TimeLineTask.RESETTYPE_AUTO);
		timeLineTaskDao.add(lineTask);
		return 2;
	}

	public String getTaskIdentical(Integer cid, Date mo) {
		Date beginDate = DateUtils.getDateByType(mo, "yyyy-MM"); 
		Date endDate = DateUtils.getBeforeMonth(DateUtils.formatDate(DateUtils.DATE_PATTERN, beginDate), 1);
		//查询出所有的任务
		List allTaskList = timeLineTaskDao.getTaskIdentical(cid, beginDate,endDate, null);
		//查询出完成的任务
		List finishTaskList = timeLineTaskDao.getTaskIdentical(cid, beginDate,endDate, TimeLineTask.STAUTS_NO);
		Object[] allobjs;
		Object[] finishobjs;
		JSONArray ja=new JSONArray();
		JSONObject jo =null;
		
		for (Object allobject : allTaskList) {
			allobjs = (Object[]) allobject;
			jo =new JSONObject();
			jo.put("date", DateUtils.formatDate(DateUtils.DATE_PATTERN, allobjs[1]));
			double finishTask = 0;
			for (Object finishobject : finishTaskList) {
				finishobjs = (Object[]) finishobject;
				if(allobjs[1].equals(finishobjs[1])){
					finishTask = Integer.parseInt(String.valueOf(finishobjs[0]));
					break;
				}
			}
			if(finishTask==0){
				jo.put("rate", 0);
			}else{
				jo.put("rate",(int) ((finishTask/Integer.parseInt(String.valueOf(allobjs[0])))*100));
			}
			ja.add(jo);
		}
		return ja.toString();
	}
	
	public String getLineTaskWeather(String location, String city) {
		return WeatherUtils.getLineTaskWeather(location,city);
	}
	
	public void addZiXunTask(String title, String id, boolean push) {
		addZixunTimeLineTask(title, id);
		if(push) {
			new JpushUtil().sendTTZiXun(title, id); //推送信息
		}
	}
	
	/**
	 * 在时间轴上添加信息
	 */
	private void addZixunTimeLineTask(String title, String id) {
		List<TimeLineTask> list = timeLineTaskDao
		.queryGlobalTask(TimeLineRule.CONTYPE_TT_ZIXUN);

		if(!CollectionUtils.isEmpty(list)) {
		
			TimeLineTask tlt = list.get(0);
			tlt.setUrl(id);
			tlt.setTitle(title);
			tlt.setCreateTime(new Date());
			tlt.setTaskTime(DateUtils.formatDate("HH:mm", new Date()));
			
			timeLineTaskDao.update(tlt);
		} else {
			TimeLineTask task = new TimeLineTask();
			task.setClientId(null);
			task.setTitle(title);
			task.setContent(null);
			task.setUrl(id);
			task.setConType(TimeLineRule.CONTYPE_TT_ZIXUN);
			task.setCreateTime(new Date());
			task.setIsAlert(TimeLineRule.ISALERT_NO);
			task.setOpenMode(TimeLineRule.OPENMODE_DIRECTSKIP);
			task.setSkipGoal(TimeLineRule.SKIPGOAL_TT_ZIXUN);
			task.setSoftType(Constant.SOFT_GUAN_XUE_TANG);
			task.setStauts(TimeLineTask.STAUTS_NO);
			task.setTaskDate(null);
			
			task.setTaskTime(DateUtils.formatDate("HH:mm", new Date()));
			task.setTaskType(TimeLineTask.TASKTYPE_ALL);
			timeLineTaskDao.add(task);
		}
	}

}