package com.bskcare.ch.service.impl.timeLine;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.RiskResultBean;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.OrderProductDao;
import com.bskcare.ch.dao.tg.MedicineReminderDao;
import com.bskcare.ch.dao.timeLine.EvaluatingResultDao;
import com.bskcare.ch.dao.timeLine.TimeLineContentDao;
import com.bskcare.ch.dao.timeLine.TimeLineLocationDao;
import com.bskcare.ch.dao.timeLine.TimeLineRuleCustomDao;
import com.bskcare.ch.dao.timeLine.TimeLineRuleDao;
import com.bskcare.ch.dao.timeLine.TimeLineSmsDao;
import com.bskcare.ch.dao.timeLine.TimeLineTaskDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.tg.TgCookedFoodService;
import com.bskcare.ch.service.tg.TgRptBaseInfoService;
import com.bskcare.ch.service.timeLine.TimeLineRuleService;
import com.bskcare.ch.service.timeLine.TimeLineSportService;
import com.bskcare.ch.service.timeLine.TimeLineTaskService;
import com.bskcare.ch.tg.TgMeals;
import com.bskcare.ch.timeLine.TimeLineContentUtils;
import com.bskcare.ch.timeLine.TimeLineTaskContent;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.RptUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.util.TimelineUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.TaskFlow;
import com.bskcare.ch.vo.order.OrderProduct;
import com.bskcare.ch.vo.tg.MedicineReminder;
import com.bskcare.ch.vo.timeLine.EvaluatingResult;
import com.bskcare.ch.vo.timeLine.TimeLineRule;
import com.bskcare.ch.vo.timeLine.TimeLineRuleCustom;
import com.bskcare.ch.vo.timeLine.TimeLineSms;
import com.bskcare.ch.vo.timeLine.TimeLineSport;
import com.bskcare.ch.vo.timeLine.TimeLineTask;
import com.bskcare.ch.vo.timeLine.TimelineLocation;

@Service
public class TimeLineRuleServiceImpl implements TimeLineRuleService {

	private static Logger log = Logger.getLogger(TimeLineRuleServiceImpl.class);
	
	@Autowired
	private TimeLineRuleDao ruleDao;
	@Autowired
	private TimeLineTaskDao lineTaskDao;
	@Autowired
	private EvaluatingResultDao evaluatingResultDao;
	@Autowired
	private TimeLineContentDao contentDao;
	@Autowired
	private TgCookedFoodService cookedFoodService;
	@Autowired
	private TimeLineSportService sportService;
	@Autowired
	private MedicineReminderDao medicineReminderDao;
	@Autowired
	private TimeLineTaskService taskService;
	@Autowired
	private OrderProductDao orderProductDao;
	@Autowired
	private TimeLineRuleCustomDao customDao;
	@Autowired
	private TgRptBaseInfoService baseInfoService;
	@Autowired
	private TimeLineLocationDao lineLocationDao;
	@Autowired
	private TimeLineSmsDao lineSmsDao;
	@Autowired
	private ClientInfoDao clientInfoDao;
	
	public List<TimeLineRule> queryList(TimeLineRule rule) {
		return ruleDao.queryList(rule);
	}

	public PageObject<TimeLineRule> queryList(TimeLineRule rule, QueryInfo info) {
		info.setSort("taskTime");
		info.setOrder(QueryInfo.ORDER_ASC);
		return ruleDao.queryList(rule, info);
	}

	public void saveOrUpdate(TimeLineRule rule) {
		ruleDao.saveOrUpdate(rule);
	}

	@Deprecated
	public void automaticallyAssignTimeLine(Integer soft) {
		log.info(LogFormat.f("更新今日临时任务状态开始"));
		lineTaskDao.updateTaskReset();// 更新临时任务的状态
		log.info(LogFormat.f("更新今日临时任务状态结束"));
		log.info(LogFormat.f("删除今日任务开始"));
		lineTaskDao.deleteTaskByUpevaluating(null, null, 2);// 删除今天全部任务
		log.info(LogFormat.f("删除今日任务结束"));
		// 查询客户最近上传的评测结果
		List<EvaluatingResult> evaluatingResults = evaluatingResultDao.getList();
		// 生成时间轴任务
		if (!CollectionUtils.isEmpty(evaluatingResults)) {
			log.info(LogFormat.f("查询所有用户最后一次评测，总数为：" + evaluatingResults.size()));
			for (EvaluatingResult evalResult : evaluatingResults) {
				addTaskByEvaluating(soft, evalResult, null);
			}
		} else {
			log.info(LogFormat.f("查询所有用户最后一次评测，总数为：0"));
		}
		
		log.info(LogFormat.f("开始生成服药提醒任务..."));
		// 生成服药提醒的任务
		List<MedicineReminder> remindersList = medicineReminderDao
				.getMedicineReminders(null);// 所有的服药提醒
		log.info(LogFormat.f("查询服药提醒任务共计 " 
				+ (CollectionUtils.isEmpty(remindersList) ? 0 : remindersList.size()) + " 条"));
		
		int succ = 0;
		for (MedicineReminder medicineReminder : remindersList) {
			int r = taskService.addMedicineReminderTask(medicineReminder, new Date());
			if(r >= 0) {
				succ++;
			}
		}
		log.info(LogFormat.f("服药提醒任务结束，共生成服药提醒{"+ succ +"}条"));
		
		// 生成天气预报的提醒
		TimelineLocation location = new TimelineLocation();
		if (soft != null) {
			location.setSoftType(soft);
		}
		List<TimelineLocation> locations = lineLocationDao.queryList(location);
		// 用来保存地区和天气，生成重复的不用再访问接口
		Map<String, String> map = new HashMap<String, String>();
		for (TimelineLocation timelineLocation : locations) {
			taskService.addWeatherTask(timelineLocation.getClientId(),
					timelineLocation.getLongitude(), timelineLocation
							.getLatitude(), timelineLocation.getSoftType(),
					timelineLocation.getCity(), map);
		}
		log.info("【时间轴生成记录】>>>>>>>>>>>>>>>>>>>>今日需要生成天气预报的人数"+locations.size());

		// 推送任务

		// 查询出需要往时间轴推送的信息
		List<TimeLineSms> lineSmslist = lineSmsDao.getListSMS(null);
		// 循环每条信息
		for (TimeLineSms timeLineSms : lineSmslist) {
			getClientList(timeLineSms);
		}
		//log.info("【时间轴生成记录】>>>>>>>>>>>>>>>>>>>>今日需要生成信息推送的人数"+clientNum+"，今日生成的信息总条数"+taskNum);
	}
	
	@Deprecated
	public void addTaskByEvaluating(Integer soft,
			EvaluatingResult evaluatingResult,Integer type) {
		log.info(LogFormat.f("根据单条评测结论生成时间轴， 开始......"));
		RiskResultBean bean = (RiskResultBean) JsonUtils.getObject4JsonString(
				evaluatingResult.getResults(), RiskResultBean.class);
		
		TgMeals meals = getMealsByEvaluationResult(soft, evaluatingResult.getClientId(), bean);
		generateTimeline(evaluatingResult, bean, meals, soft, type);
		log.info(LogFormat.f("根据单条评测结论生成时间轴， 结束......"));
	}

	/**
	 * 根据评测结果生成相应的膳食内容
	 * @param soft
	 * @param evaluatingResult
	 * @param bean
	 * @return
	 */
	private TgMeals getMealsByEvaluationResult(Integer soft, Integer clientId, 
			RiskResultBean bean) {
		
		// 用户bmi
		double height = bean.getHeight();
		double weight = bean.getWeight();
		// 计算公式：体重（kg）÷身高^2（m）
		double bmi = weight / ((height * height) / 10000); // BMI
		// 用户需要的总热量
		int cardinality = RptUtils.getCalCardinality(bmi + "", bean
				.getPhysicalType());
		double cal = RptUtils.getDailyCalorie(bean.getHeight() + "", bean
				.getAge(), bean.getGender(), cardinality);

		// 合并症信息 没有糖尿病，没有合并症返回"" 有糖尿病，没有合并症返回12 有多种合并症返回多种合并症
		// FIXME 合并症比较乱，需要重新整理一下
		String hebingDisease = "";
		if (!bean.getResult().equals("高危") && !bean.getResult().equals("低危")) {// (只有三种情况：糖尿病、低危、高危)
			if (StringUtils.isEmpty(bean.getComplications())) {
				hebingDisease = "0"; //没有合并症的标识
			} else {
				hebingDisease = bean.getComplications(); //类似：1,2,3...
			}
		}
		
		// 查询食谱的信息
		TgMeals meals = cookedFoodService.getMealsInfo(clientId,
				cal, soft, hebingDisease, bean);
		
		if (meals == null) {// 如果查询不到饮食信息
			meals = new TgMeals();
			String str = "由于您上次评测的身高体重腰围等填写不在标准范围之内，所以暂时无法为您出具饮食方案，请重新评测";
			meals.setWancan(str);
			meals.setWanjia(str);
			meals.setWucan(str);
			meals.setWujia(str);
			meals.setZaocan(str);
			meals.setZaojia(str);
		}
		return meals;
	}

	/**
	 * 生成时间轴 （与任务分配刚好相反，任务分配是按照规则找相应的人，
	 * 而这里是按照“人”（其实是按照人上传的评测）查找相应的规则））
	 * 
	 * @param evaluatingResult 评测信息
	 * @param bean 评测结果
	 * @param soft 软件类型
	 * @param meals 一天食谱信息
	 * @param type 1,代表升级vip重新生成，不会生产报告
	 */
	private void generateTimeline(EvaluatingResult evaluatingResult,
			RiskResultBean bean , TgMeals meals, Integer soft, Integer type) {
		
		String consequence = bean.getResult(); //评测结论： 疑似1型糖尿病 、疑似2型糖尿病
		//并合症
		int complication = TimelineUtils.getComplication(bean.getComplications());

		Date serviceDate = null;
		Integer isPay = null; // 是否已付费
		Date testDate = evaluatingResult.getTestDate();
		
		//FIXME 
		List<OrderProduct> opList = orderProductDao
				.queryOrderProductByPids(evaluatingResult.getClientId(),
						SystemConfig.getString("visited_bloodsugar_product"));
		
		if (null != opList && opList.size() > 0) {
			isPay = TimeLineRule.ISPAY_YES;
			serviceDate = opList.get(0).getCreateTime();// 购买服务时间
		} else {
			isPay = TimeLineRule.ISPAY_NO;
		}
		
		log.info(LogFormat.f("Params info : isPay = " + isPay + ",testDate = " 
				+ testDate + ",serviceDate = " + serviceDate
				+ ", consequence = " + consequence + ", complication = " + complication
				+ ", type  = " + type));
		
		// 查询用户自定义提醒时间
		TimeLineRuleCustom custom = new TimeLineRuleCustom();
		custom.setClientId(evaluatingResult.getClientId());
		custom.setSoftType(soft);
		List<TimeLineRuleCustom> customsList = customDao.getlineRuleCustoms(custom);// 用户自定义时间查询
		
		log.info(LogFormat.f("查询到用户自己定制个性数为：" 
				+ (CollectionUtils.isEmpty(customsList) ? 0 : customsList.size() ) + "条"));

		String currentDate = DateUtils.formatDate(DateUtils.DATE_PATTERN,
				new Date()); // 获得今天的日期(yyyy-mm-dd)

		// 查询出用户符合的运动
		TimeLineSport lineSport = sportService.getLineSportByEvalResult(bean, soft);
		TimeLineTask lineTask;// 发布的任务

		// 查询适合用户的时间轴规则
		List<TimeLineRule> lineRulesList = ruleDao.queryListByTestDate(consequence,
				testDate, soft, isPay, serviceDate, complication);
		
		log.info(LogFormat.f("从时间轴规则表中匹配到与该用户相关的规则有：" +
				(CollectionUtils.isEmpty(lineRulesList) ? 0 : lineRulesList.size() ) 
				+ "个"));
		
		int gtask = 0; //生成多少个时间轴内容
		
		// 根据时间轴规则查询具体时间轴上的内容
		for (TimeLineRule timeLineRule : lineRulesList) {
			if (timeLineRule != null) {
				lineTask = new TimeLineTask();

				// 判断这条任务是否要添加 （循环判断用户“个性化设置时间”）
				boolean needCreate = true;
				String selfDefinedTime = null; //当前类型时间轴用户自定义的时间
				for (TimeLineRuleCustom ruleCustom : customsList) {// 查询用户如果设置过这条任务的类型
					if (ruleCustom.getConType().equals(timeLineRule.getConType())) {
						// 如果用户设置关闭某个类型的提醒就不在添加该时间轴（下面做了处理）
						if(TimeLineRuleCustom.STAUTS_CALLOFF.equals(ruleCustom.getStatus())) {
							log.info(LogFormat.f("用户["+ ruleCustom.getClientId() +"]关闭了某项设置" + ruleCustom.getConType()));
							needCreate = false;
						} else {
							selfDefinedTime = ruleCustom.getTaskTime();
						}
						break;
					}
				}
				
				if (TimeLineRule.CONTYPE_SPORT == timeLineRule.getConType() 
						&& null == lineSport) {// 如果这是运动任务但是运动库没有适合用户的运动信息
					needCreate = false;
					log.warn("【WARN】运动信息为空了需要注意一下是什么原因引起【WARN!!】");
				}
				
				//FIXME 这部分下面也做了判断，后续还要优化
				//当前时间轴的类型为报告，如果不需要生成报告直接不往下进行了， （其实最后做了判断，之所以在这里再加一次
				//是为了防止计算了一大坨的内容，到最后你告诉我不用生成这个时间轴，无语...）
				if(TimeLineRule.CONTYPE_ASSESSRPT == timeLineRule.getConType() 
						&& (null != type && type ==1)) {
					needCreate = false;
					log.info(LogFormat.f("报告类型的时间轴，要求不要生成报告。"));
				}
				
				if (!needCreate) {// needCreate == false
					continue;
				}
				
				if (lineSport != null // 如果是用户运动类型不为空，并且任务是运动任务，任务时间从运动库抽取
						&& timeLineRule.getConType() == TimeLineRule.CONTYPE_SPORT) {
					lineTask.setTaskTime(lineSport.getTaskTime());
					lineTask.setUrl(lineSport.getId() + ""); //之所以要在运动类的时间轴设置url为sport.id 是为计算完成率（热卡）预备
				} else {// 如果不是运动任务
					
					if(StringUtils.isEmpty(selfDefinedTime)) { //如果用户自定义时间null 或者 为空 就用当时间轴规则的时间
						lineTask.setTaskTime(timeLineRule.getTaskTime()); 
					} else { //不为空说明用户自己定了了，就要用用户设置的时间
						lineTask.setTaskTime(selfDefinedTime);
					}
				}

				lineTask.setTaskDate(DateUtils.parseDate(currentDate + " "
						+ lineTask.getTaskTime(), "yyyy-MM-dd HH:mm")); //2014-07-28 08:10:00

				lineTask.setConType(timeLineRule.getConType());
				lineTask.setClientId(evaluatingResult.getClientId());
				lineTask.setCreateTime(new Date());
				lineTask.setIsAlert(timeLineRule.getIsAlert());
				lineTask.setSoftType(soft);
				lineTask.setStauts(TimeLineRule.STAUTS_YES);
				lineTask.setOpenMode(timeLineRule.getOpenMode());
				lineTask.setTaskType(TimeLineTask.TASKTYPE_LOGIN);
				lineTask.setConType(timeLineRule.getConType());
				lineTask.setSkipGoal(timeLineRule.getSkipGoal());
				lineTask.setResetType(TimeLineTask.RESETTYPE_AUTO);

				// 生成任务内容的生成接口 （找到对应时间轴生成选择器）
				// 根据时间轴规则的类型查询时间轴内容content信息
				TimeLineTaskContent content = TimeLineContentUtils
						.choiceContent(timeLineRule);// 获得用户对应的任务类型
				
				lineTask = content.setetTaskCon(soft, isPay, consequence,
						contentDao, timeLineRule, meals, lineTask);// 根据用户信息生成对应的任务内容

				if (lineSport != null
						&& timeLineRule.getConType() == TimeLineRule.CONTYPE_SPORT) {
					String contentStr = "";
					if (isPay == TimeLineRule.ISPAY_YES // 如果用户是付费用户生成具体的运动内容
							|| timeLineRule.getIsPay() == TimeLineRule.ISPAY_ALL) { // 如果运动是所有人都有的直接生成具体内容
						contentStr = sportService.getTimeLineConBySport(
								bean, lineSport, evaluatingResult.getClientId());
					}
					// 运动追加生成运动任务内容,追加运动库提取运动部分。
					lineTask.setContent(lineTask.getContent() + contentStr);
				}

				// 针对是否付费对内容进行处理 如果是付费用户正常生成内容，非付费用户发出升级提示，点击跳转到升级vip,内容后面添加提示
				if (isPay == TimeLineRule.ISPAY_NO
						&& timeLineRule.getIsPay() == TimeLineRule.ISPAY_FREE_HINT) {

					lineTask.setOpenMode(TimeLineRule.OPENMODE_OPENCONTENTSKIP);
					lineTask.setSkipGoal(TimeLineRule.SKIPGOAL_PAYSERVICE);
					lineTask.setContent(lineTask.getContent()
							+ "\n点击升级VIP会员查看更多");

				}
				
				//上面也添加了判断，这里之所有没去，是为了暂时的兼容性，下次即可直接考虑删除
				if (timeLineRule.getConType() == TimeLineRule.CONTYPE_ASSESSRPT) {
					if(type !=null && type ==1 ) return ;//如果传入1，不生成报告
					// 评测报告
					lineTask.setOpenMode(TimeLineRule.OPENMODE_DIRECTSKIP);
					lineTask.setSkipGoal(TimeLineRule.SKIPGOAL_TGRPT);
					String url = baseInfoService.createTgRptBaseInfo(
							evaluatingResult.getClientId(), bean, evaluatingResult
									.getId());
					if(StringUtils.isEmpty(url)){
						continue;//生成评估报告失败，跳过。
					}
					lineTask.setUrl(url);
				}
				lineTaskDao.add(lineTask);
				gtask++;
			}
		}
		
		log.info(LogFormat.f("评测id = " + evaluatingResult.getId() + ", clientId = "
				+ evaluatingResult.getClientId() 
				+ "，此评测结果共生成 " + gtask + " 时间轴内容。"));
	}

	public void againFound(Integer cid, Integer softType,Integer type) {
		lineTaskDao.deleteTaskByUpevaluating(cid,
				TimeLineRule.CONTYPE_EATMEDICINE, 1);
		EvaluatingResult evaluatingResult = getEvaluatingResultfinally(cid,
				softType);
		if (evaluatingResult != null) {
			addTaskByEvaluating(softType, evaluatingResult,type);
		}
	}

	public List<TimeLineRule> queryList(Integer cid, Integer softType) {
		System.out.println("-----查询用户最后一次风险评估");
		log.info(LogFormat.f("查询用户[" + cid + "]最后一次风险评估，开始..."));
		EvaluatingResult evaluatingResult = getEvaluatingResultfinally(cid,
				softType);
		if (evaluatingResult != null) {
			
			RiskResultBean bean = (RiskResultBean) JsonUtils
					.getObject4JsonString(evaluatingResult.getResults(),
							RiskResultBean.class);
			
			int complication = TimelineUtils.getComplication(bean.getComplications());
			
			Integer isPay = null; // 是否已付费
			Date serviceDate = null;
			List<OrderProduct> oproductList = orderProductDao
					.queryOrderProductByPids(cid, SystemConfig
									.getString("visited_bloodsugar_product"));// Integer.parseInt(SystemConfig.getString("product_timeline_xuetang")));
			
			if (CollectionUtils.isEmpty(oproductList)) {
				log.info("cid = " + cid + "为非付费用户。");
			} else {
				isPay = TimeLineRule.ISPAY_YES;
				serviceDate = oproductList.get(0).getCreateTime();
			}
			// 查询适合用户的时间轴规则
			List<TimeLineRule> lineRules = ruleDao.queryListByTestDate(bean
					.getResult(), evaluatingResult.getTestDate(), softType,
					isPay, serviceDate, complication);
			log.info(LogFormat.f("查询用户[" + cid + "]最后一次风险评估，结束，共返回  "
					+ (CollectionUtils.isEmpty(lineRules) ? 0 : lineRules.size()) +
					" 条"));
			return lineRules;
		}
		return null;
	}

	/**
	 * 返回用户最后一次
	 * 
	 * @param cid
	 * @param softType
	 * @return
	 */
	public EvaluatingResult getEvaluatingResultfinally(Integer cid,
			Integer softType) {
		EvaluatingResult evaluatingResult = new EvaluatingResult();
		evaluatingResult.setClientId(cid);
		evaluatingResult.setSoftType(softType);
		List<EvaluatingResult> list = evaluatingResultDao
				.queryResultsByClientId(evaluatingResult);
		evaluatingResult = null;
		if (!CollectionUtils.isEmpty(list)) {
			if (list.size() > 0) {
				evaluatingResult = list.get(0);
			}
		}
		return evaluatingResult;

	}

	//
	public void getClientList(TimeLineSms lineSms) {
		TaskFlow flow = new TaskFlow();
		flow.setIscycle(lineSms.getIscycle());
		if (lineSms.getIscycle() == TaskFlow.ISCYCLE_LOGIN_NO
				|| lineSms.getIscycle() == TaskFlow.ISCYCLE_VIP_NO) {
			flow.setIntervals(lineSms.getCycle());
		} else if (lineSms.getIscycle() == TaskFlow.ISCYCLE_LOGIN_YES
				|| lineSms.getIscycle() == TaskFlow.ISCYCLE_VIP_YES) {
			flow.setIntervals(1);
			flow.setCycle(lineSms.getCycle());
		}
		flow.setRoleId(1);
		flow.setClientType(lineSms.getClientType());
		List<ClientInfo> clientInfos = clientInfoDao.findClientInfo(null, null,
				flow,0);
		
		for (ClientInfo clientInfo : clientInfos) {
			addTimeLineSmsTask(clientInfo, lineSms);
		}
	}

	public void addTimeLineSmsTask(ClientInfo clientInfo, TimeLineSms lineSms) {
		TimeLineTask lineTask = new TimeLineTask();
		lineTask.setConType(TimeLineRule.CONTYPE_SMS);
		lineTask.setIsAlert(TimeLineRule.ISALERT_NO);
		lineTask.setCreateTime(new Date());
		lineTask.setTitle(lineSms.getTitle());
		lineTask.setTaskTime(lineSms.getTaskTime());
		lineTask.setSoftType(lineSms.getSoftType());
		lineTask.setTaskType(TimeLineTask.TASKTYPE_LOGIN);
		lineTask.setStauts(TimeLineRule.STAUTS_YES);
		lineTask.setOpenMode(TimeLineRule.OPENMODE_OPENCONTENT);
		lineTask.setSkipGoal(TimeLineRule.SKIPGOAL_NO);
		lineTask.setResetType(TimeLineTask.RESETTYPE_AUTO);
		lineTask.setClientId(clientInfo.getId());
		lineTask.setUrl("");
		lineTask.setContent(lineSms.getContent());
		// 获得当前时间
		String currentDate = "";//
		if ((DateUtils.getWeekOfDate(new Date()) == 5 || DateUtils
				.getWeekOfDate(new Date()) == 6)
				&& lineSms.getRest() == 2) {// 如果今天是周五或周六，将任务推迟到周日
			currentDate = DateUtils.formatDate(DateUtils.DATE_PATTERN,
					DateUtils.getThisWeekDay(7, false));

		} else {
			currentDate = DateUtils.formatDate(DateUtils.DATE_PATTERN,
					new Date());// 否则正常
		}
		Date send = DateUtils.parseDate(currentDate + " "
				+ lineTask.getTaskTime(), "yyyy-MM-dd HH:mm");
		lineTask.setTaskDate(send);
		
		//过滤替换符
		//过滤姓名
		String name = !StringUtils.isEmpty(clientInfo.getName()) ? clientInfo.getName() + (clientInfo.getGender() != null ? (clientInfo.getGender() == 0 ? "先生" : "女士") : "" ) : clientInfo.getMobile();
//		String name = "" ;
//		if(!StringUtils.isEmpty(clientInfo.getName())){
//			name=clientInfo.getName();
//			if(clientInfo.getGender()!=null){
//				if(clientInfo.getGender()==0){
//					name+="先生";
//				}else{
//					name+="女士";
//				}
//			}else{
//				name+="";
//			}
//		}else{
//			name =clientInfo.getMobile()+"用户";
//		}
		lineTask.setContent(StringUtils.replace(lineTask.getContent(), TimeLineSms.TCSHSHELL_NAME, name));
		//过滤日期
		lineTask.setContent(StringUtils.replace(lineTask.getContent(), TimeLineSms.TCSHSHELL_DATE, DateUtils.formatDate(DateUtils.DATE_PATTERN,DateUtils.getAppointDate(send, 1))));
		
		lineTaskDao.add(lineTask);
		//taskNum++;
	}

	public void delete(Integer id) {
		ruleDao.delete(id);
	}

	public TimeLineRule getLineRuleById(Integer id) {
		return ruleDao.load(id);
	}

}
