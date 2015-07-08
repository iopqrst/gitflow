package com.bskcare.ch.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.ClientDiseaseFamilyDao;
import com.bskcare.ch.dao.ClientDiseaseSelfDao;
import com.bskcare.ch.dao.ClientHobbyDao;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.ClientLatestPhyDao;
import com.bskcare.ch.dao.HealthLevelDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.HealthLevelService;
import com.bskcare.ch.util.RptUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.HealthLevel;
import com.bskcare.ch.vo.client.ClientDiseaseFamily;
import com.bskcare.ch.vo.client.ClientDiseaseSelf;
import com.bskcare.ch.vo.client.ClientHobby;
import com.bskcare.ch.vo.client.ClientLatestPhy;

@Service("healthLevelService")
public class HealthLevelServiceImpl implements HealthLevelService {

	@Autowired
	private HealthLevelDao healthLevelDao;
	@Autowired
	private ClientInfoDao clientInfoDao;// 用户
	@Autowired
	private ClientLatestPhyDao clientLatestPhyDao;// 
	@Autowired
	private ClientDiseaseSelfDao diseaseSelfDao;// 自己病史
	@Autowired
	private ClientDiseaseFamilyDao diseaseFamilyDao;// 家族病史
	@Autowired
	private ClientHobbyDao clientHobbyDao;

	/**
	 * 获得健康等级列表
	 */
	public PageObject<HealthLevel> getHealhtLevelList(QueryInfo queryInfo,
			HealthLevel level) {
		return healthLevelDao.getHealhtLevelList(queryInfo,level);
	}

	/**
	 * 添加，修改健康等级
	 */
	public void saveOrUpdateHL(HealthLevel healthLevel) {
		healthLevelDao.saveOrUpdateHL(healthLevel);
	}

	public HealthLevel load(int healthLevelId) {
		return healthLevelDao.getHealthLevel(healthLevelId);
	}

	public void delHL(int healthLevelId) {
		healthLevelDao.delHL(healthLevelId);
	}

	public HealthLevel getHealthLevelByHealthIndex(Integer HealthIndex) {
		return healthLevelDao.getHealthLevelByHealthIndex(HealthIndex);
	}

	public JSONObject getHealthEvaluate(ClientInfo clientInfo) {
		JSONObject json = new JSONObject();
		// 根据年龄，计算健康评估得分
		json.put("age", getHealthEvaluateByAge(clientInfo));
		// 根据用户BMI和腰臀比的值，计算健康评估得分
		json.put("bmi", getHealthEvaluateByHWWB(clientInfo));
		// 根据用户自己疾病，计算健康评估得分
		json.put("selfHistory", getHealthEvaluateBySD(clientInfo));
		// 根据遗传史，计算出健康评估得分
		json.put("familyHistory", getHealthEvaluateByFD(clientInfo));
		// 根据用户生活嗜好，计算出健康评估得分
		int lifeData[] = getHealthEvaluateByLH(clientInfo);
		for (int i = 0; i < lifeData.length; i++) {
			if (i == 0) {
				json.put("life", lifeData[0]);
			}
			if (i == 1) {
				json.put("sleep", lifeData[1]);
			}
		}
		return json; 
	}
	
	/**
	 * 根据年龄，计算年龄健康评估
	 * 年龄在18至45岁之间,评估得分为0
	 * 在46至65岁之间,评估得分为1
	 * 在66至72岁之间,评估得分为2
	 * 在73至83岁之间,评估得分为3
	 * 大于84岁,评估得分为4
	 * @param clientInfo
	 * @return
	 */
	public int getHealthEvaluateByAge(ClientInfo clientInfo) {
		List<ClientInfo> list = clientInfoDao.findClientInfo(clientInfo);
		Integer age = null;
		int ageHealthGrade = 0;// 年龄评估得分
		if (!CollectionUtils.isEmpty(list)) {
			age = list.get(0).getAge();
		}
		if (null != age) {
			if (age > 18 && age <= 45) {
				ageHealthGrade = 0;
			} else if (age > 46 && age <= 65) {
				ageHealthGrade = 1;
			} else if (age > 66 && age <= 72) {
				ageHealthGrade = 2;
			} else if (age > 73 && age <= 83) {
				ageHealthGrade = 3;
			} else if (age > 84) {
				ageHealthGrade = 4;
			} else {
				ageHealthGrade = 0;
			}
		}
		return ageHealthGrade;
	}
	
	/**
	 * 根据用户BMI和腰臀比的值，计算HWWB健康评估得分
	 * HWWB为height,weight,waist,breech
	 * @param clientInfo
	 * @return
	 */
	public int getHealthEvaluateByHWWB(ClientInfo clientInfo) {
		int bmiGrade = 0;// 体质指数评估得分
		int wbGrade = 0;// 腰臀比评估得分
		int bmiWbGrade = 0;// 体质指数评估得分与腰臀比评估得分的和
		ClientLatestPhy clientLatestPhy = clientLatestPhyDao.getClientLastestPhy(clientInfo.getId());
		if (null != clientLatestPhy) {
			// 身高和体重
			if (!StringUtils.isEmpty(clientLatestPhy.getHeight()) && !StringUtils.isEmpty(clientLatestPhy.getWeight())) {
				// bmi为体质指数
				double bmi = RptUtils.getDoubleBMI(clientLatestPhy.getHeight(), clientLatestPhy.getWeight());
				if (bmi <= 18.5) {
					bmiGrade = 2;
				} else if (bmi > 18.5 && bmi <= 23.9) {
					bmiGrade = 0;
				} else if (bmi > 24 && bmi <= 27.9) {
					bmiGrade = 4;
				} else if (bmi > 28 && bmi <= 30) {
					bmiGrade = 7;
				} else if (bmi > 30) {
					bmiGrade = 10;
				}
			}
			// 腰围和臀围
			if (!StringUtils.isEmpty(clientLatestPhy.getWaist()) && !StringUtils.isEmpty(clientLatestPhy.getBreech())) {
				double wb = RptUtils.getDoubleWb(clientLatestPhy.getWaist(), clientLatestPhy.getBreech());
				if (clientInfo.getGender().equals("0")) {// 0男,1女 
					if (wb > 0.8) {
						wbGrade = 4;
					}
				} else {
					if (wb > 0.9) {
						wbGrade = 4;
					}
				}
			}
		}
		// 体质指数与腰臀比评估得分之和大于14,则两项值和为14
		bmiWbGrade = bmiGrade + wbGrade;
		if (bmiWbGrade > 14) {
			bmiWbGrade = 14;
		}
		return bmiWbGrade;
	}
	
	/**
	 * 根据用户自己疾病，计算健康评估得分
	 * SD为self disease
	 * @param clientInfo
	 * @return
	 */
	public int getHealthEvaluateBySD(ClientInfo clientInfo) {
		int selfHealthGrade = 0;// 用户自己疾病评估得分
		int selfDiseaseCount = 0;// 统计用户得病个数
		int specialDiseaseCount = 0;// 统计用户得高血压和糖尿病个数
		boolean daixieFlag = false;// 标记代谢综合症(fasle没有代谢综合症)
		List<ClientDiseaseSelf> diseaseSelfList = diseaseSelfDao.queryDiseaseSelfByClientId(clientInfo.getId());
		if (!CollectionUtils.isEmpty(diseaseSelfList)) {
			for (ClientDiseaseSelf clientDiseaseSelf : diseaseSelfList) {
				// 按照用户自己疾病不同,累加家族评估得分
				if (null != clientDiseaseSelf.getDisease()) {
					if (clientDiseaseSelf.getDisease().equals("38")) {// 38为代谢综合症编号
						selfHealthGrade += 25;
						selfDiseaseCount ++;
						daixieFlag = true;// 有代谢综合症
						continue;
					} 
					if (clientDiseaseSelf.getDisease().equals("13")) {// 13为高血压编号
						selfHealthGrade += 20;
						specialDiseaseCount ++;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("1")) {// 1为糖尿病编号
						selfHealthGrade += 20;
						specialDiseaseCount ++;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("4")) {// 4为肿瘤编号
						selfHealthGrade += 30;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("29")) {// 29为冠心病编号
						selfHealthGrade += 20;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("30")) {// 30为脑卒中编号
						selfHealthGrade += 20;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("11")) {// 11为动脉粥样硬化
						selfHealthGrade += 15;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("10")) {// 10为痛风编号
						selfHealthGrade += 12;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("14")) {// 14为胃病编号
						selfHealthGrade += 12;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("18")) {// 18为甲亢编号
						selfHealthGrade += 12;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("23")) {// 23为肝病编号
						selfHealthGrade += 12;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("35")) {// 35为心率失常编号
						selfHealthGrade += 12;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("17")) {// 17为肾病编号
						selfHealthGrade += 10;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("19")) {// 19为甲减编号
						selfHealthGrade += 10;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("27")) {// 27为低血压编号
						selfHealthGrade += 10;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("9")) {// 9为呼吸系统疾病编号
						selfHealthGrade += 10;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("2")) {// 2为高血脂编号
						selfHealthGrade += 10;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("21")) {// 21为前列腺疾病编号
						selfHealthGrade += 8;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("8")) {// 8为骨关节疾病编号
						selfHealthGrade += 8;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("16")) {// 16为贫血编号
						selfHealthGrade += 8;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("40")) {// 40为高尿酸血症编号
						selfHealthGrade += 10;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("39")) {// 39为低血糖编号
						selfHealthGrade += 8;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("6")) {// 6为类风湿性关节炎编号
						selfHealthGrade += 8;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("5")) {// 5为风湿性关节炎编号
						selfHealthGrade += 8;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("22")) {// 22为妇科疾病编号
						selfHealthGrade += 8;
						selfDiseaseCount ++;
						continue;
					}
					if (clientDiseaseSelf.getDisease().equals("24")) {// 24为妇科疾病编号
						selfHealthGrade += 5;
						selfDiseaseCount ++;
						continue;
					}
				}
			}
		}
		// daixieFlag(代谢综合征)为true(有),则用户有代谢综合症,减去糖尿病和高血压相对应分值,统计用户得病个数减去高血压和糖尿病相应个数
		if (daixieFlag) {
			selfHealthGrade -= 20 * specialDiseaseCount;
			selfDiseaseCount -= specialDiseaseCount;
		}
		// 统计用户得病个数超过3,则评估得分加5
		if (selfDiseaseCount > 3) {
			selfHealthGrade += 5;
		}
		// 评估得分超过60,则评分为60
		if (selfHealthGrade > 60) {
			selfHealthGrade = 60;
		}
		return selfHealthGrade;
	}
	
	/**
	 * 根据遗传史，计算出健康评估得分
	 * FD为family disease
	 * @param clientInfo
	 * @return
	 */
	public int getHealthEvaluateByFD(ClientInfo clientInfo) {
		int familyHealthGrade = 0;
		List<ClientDiseaseFamily> diseaseFamilieList = diseaseFamilyDao.queryDiseaseFamilyByClientId(clientInfo.getId());
		if (!CollectionUtils.isEmpty(diseaseFamilieList)) {
			for (ClientDiseaseFamily clientDiseaseFamily : diseaseFamilieList) {
				// 按照用户家族疾病不同,累加家族评估得分
				if (!StringUtils.isEmpty(clientDiseaseFamily.getDisease())) {
					if (clientDiseaseFamily.getDisease().equals("13")) {// 13为高血压编号
						familyHealthGrade += 5;
						continue;
					}
					if (clientDiseaseFamily.getDisease().equals("1")) {// 1为糖尿病编号
						familyHealthGrade += 5;
						continue;
					}
					if (clientDiseaseFamily.getDisease().equals("29")) {// 29为冠心病编号
						familyHealthGrade += 5;
						continue;
					}
					if (clientDiseaseFamily.getDisease().equals("31")) {// 31为慢支编号
						familyHealthGrade += 5;
						continue;
					}
					if (clientDiseaseFamily.getDisease().equals("32")) {// 32为哮喘编号
						familyHealthGrade += 5;
						continue;
					}
					if (clientDiseaseFamily.getDisease().equals("23")) {// 23为肝病编号
						familyHealthGrade += 5;
						continue;
					}
				} else {
					if (clientDiseaseFamily.getDisease() == null) {// 13为其他疾病
						familyHealthGrade = 5;
						continue;
					}
				}
			}
		}
		// 家族健康评估得分超过10,家族健康评估得分为10
		if (familyHealthGrade > 10) {
			familyHealthGrade = 10;
		}
		return familyHealthGrade;
	}
	
	/**
	 * 根据用户生活嗜好，计算出健康评估得分
	 * LH为life hobby
	 * @param clientInfo
	 * @return
	 */
	public int[] getHealthEvaluateByLH(ClientInfo clientInfo) {
		int lifeGrade = 0;// 生活评估得分
		int sleepGrade = 0;// 睡眠评估得分
		// 声明数组
		int lifeData[] = new int[2];
		ClientHobby clientHobby = clientHobbyDao.getClientHobby(clientInfo.getId());
		if (null != clientHobby) {
			// 用户吸烟为1,lifeGrade(生活评估得分)加4;用户吸烟不为1,lifeGrade(生活评估得分)为0
			if (!StringUtils.isEmpty(clientHobby.getSmoke() + "") && clientHobby.getSmoke() == 1) {
				lifeGrade += 4;
			}
			// 用户嗜酒为1,lifeGrade(生活评估得分)加4;用户嗜酒不为1,lifeGrade(生活评估得分)为0
			if (!StringUtils.isEmpty(clientHobby.getDrink() + "") && clientHobby.getDrink() == 1) {
				lifeGrade += 4;
			}
			// 用户睡眠情况
			if (!StringUtils.isEmpty(clientHobby.getSleeping() + "")) {
				int sleepTime = clientHobby.getSleeping();
				// 睡眠在6至8小时之内,睡眠充足,sleepGrade(睡眠评估得分为0)
				if (sleepTime > 6 && sleepTime <= 8) {
					sleepGrade = 0;
				}
				// 睡眠在3至6小时之内,睡眠不足,sleepGrade(睡眠评估得分为2)
				if (sleepTime > 3 && sleepTime <= 6) {
					sleepGrade = 2;
				}
				// 睡眠在0至3小时之内,严重失眠,sleepGrade(睡眠评估得分为4)
				if (sleepTime > 0 && sleepTime <= 3) {
					sleepGrade = 4;
				}
			}
		}
		// lifeData传入生活评估得分和睡眠评估得分
		lifeData[0] = lifeGrade;
		lifeData[1] = sleepGrade;
		return lifeData;
	}
}
