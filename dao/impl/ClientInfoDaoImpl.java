package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.ClientInfoExtend;
import com.bskcare.ch.bo.ClientQueryObject;
import com.bskcare.ch.bo.ClientRegEval;
import com.bskcare.ch.bo.ClientUploadStat;
import com.bskcare.ch.bo.DoctorInfo;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.HealthLevel;
import com.bskcare.ch.vo.TaskFlow;

@Repository
@SuppressWarnings("unchecked")
public class ClientInfoDaoImpl extends BaseDaoImpl<ClientInfo> implements
		ClientInfoDao {

	// 更新用户
	public Integer updateClient(ClientInfo account) {
		return updateByHql(
				"update ClientInfo set account=?,password=? where id=?",
				new Object[] { account.getAccount(), account.getPassword(),
						account.getId() });
	}

	// 根据亲情账号id修改用户表状态
	public void deleteFamilyInfoById(int id) {
		String hql = "update ClientInfo set status=? where id=?";
		Object[] obj = { ClientInfo.STATUS_DELETE, id };
		updateByHql(hql, obj);
	}

	public void updateFamilyInfo(int id, String name, String mobile,
			String email) {
		String hql = "update ClientInfo set name=?,mobile=?,email=? where id=?";
		Object[] obj = { name, mobile, email, id };
		updateByHql(hql, obj);
	}

	// 根据条件查找客户
	public List<ClientInfo> findClientInfo(ClientInfo client) {
		ArrayList args = new ArrayList();
		String hql = "from ClientInfo t1 where t1.status=?";
		args.add(Constant.STATUS_NORMAL);

		if (null != client) {
			if (client.getId() != null) {
				hql += " and t1.id = ?";
				args.add(client.getId());
			}

			if (!StringUtils.isEmpty(client.getAccount())) {
				hql += " and t1.account = ?";
				args.add(client.getAccount());
			}

			if (!StringUtils.isEmpty(client.getPassword())) {
				hql += " and t1.password = ?";
				args.add(client.getPassword());
			}

			if (!StringUtils.isEmpty(client.getMobile())) {
				hql += " and t1.mobile=?";
				args.add(client.getMobile());
			}

			if (!StringUtils.isEmpty(client.getEmail())) {
				hql += " and t1.email = ?";
				args.add(client.getEmail());
			}

			if (!StringUtils.isEmpty(client.getVipCard())) {
				hql += " and t1.vipCard = ?";
				args.add(client.getVipCard());
			}
			if (!StringUtils.isEmpty(client.getName())) {
				hql += " and t1.name = ?";
				args.add(client.getName());
			}
			if (null != client.getAreaId()) {
				hql += " and t1.areaId = ?";
				args.add(client.getAreaId());
			}
			if(null != client.getCompSource()) {
				hql += " and t1.compSource = ?";
				args.add(client.getCompSource());
			}
			if(!StringUtils.isEmpty(client.getUid())) {
				hql += " and t1.uid = ?";
				args.add(client.getUid());
			}
		}
		return executeFind(hql, args.toArray());

	}

	public List<ClientInfo> findClientInfo(ClientInfo client,
			HealthLevel healthLevel, TaskFlow flow, int type) {
		ArrayList args = new ArrayList();
		String sql = "";
		if (null != flow) {

			if (flow.getIscycle() == null)
				return null;// 循环方式为空，设置无效，退出

			if (TaskFlow.ISCYCLE_LOGIN_NO == flow.getIscycle()) { // 从注册开始计算，
																	// 不循环任务
				sql = "SELECT * FROM t_clientinfo t1 where t1.status=?  and DATEDIFF(NOW(),t1.createTime) = ? ";// 如果是根据注册时间，不需要链表查询
				args.add(Constant.STATUS_NORMAL);
				args.add((flow.getIntervals() - 1));
			} else if (TaskFlow.ISCYCLE_LOGIN_YES == flow.getIscycle()) { // 从注册开始计算，循环任务
				if (1 != flow.getCycle()) { // 循环周期不等于1时
					// 如果是根据注册时间，不需要链表查询
					sql = "SELECT * FROM t_clientinfo t1 where t1.status=? and "
							+ "(DATEDIFF(NOW(),t1.createTime)%?) = ? and DATEDIFF(NOW(),t1.createTime) > ? ";
					args.add(Constant.STATUS_NORMAL);
					args.add(flow.getCycle());
					args.add((flow.getIntervals() - 1));
					args.add((flow.getIntervals() - 1));
				}

				if (1 == flow.getCycle()) { // 循环周期等于1时
					// 如果是根据注册时间，不需要链表查询
					sql = "SELECT * FROM t_clientinfo t1 where t1.status=?";
					args.add(Constant.STATUS_NORMAL);
				}
			} else { // 从升级vip开始
				if (null != flow.getClientType() && -1 != flow.getClientType()) { // 对应用户类型不能为空或者-1
					// 从升级vip开始计算，链表查询t_order_product，查询升级vip时间
					sql = "SELECT t1.* FROM t_clientinfo t1, t_order_product t2 where t1.id = t2.clientId and t1.status=? ";// 如果确定查某种卡的类型，根据激活时间需要链表查询时间
					args.add(Constant.STATUS_NORMAL);

					if (flow.getClientType() == TaskFlow.CLIENTTYPE_VIP) { // 所有vip用户，包含各种卡的类型
						sql += " and ( t1.availableProduct != '' or t1.availableProduct is not null ) ";
					} else if (flow.getClientType() == TaskFlow.CLIENTTYPE_COMMON) { // 普通用户，卡类型空
						sql += " and ( t1.availableProduct = '' or t1.availableProduct is null ) ";
					} else { // 根据单种卡类型判断
						sql += " and t1.availableProduct like '%"
								+ flow.getClientType() + ",%' ";
						sql += " and t2.productId = ? ";
						args.add(flow.getClientType());
					}

					// 判断循环周期，从升级vip开始计时
					if (TaskFlow.ISCYCLE_VIP_YES == flow.getIscycle()) {
						if (1 != flow.getCycle()) { // 如果循环周期为1
							sql += " and (DATEDIFF(NOW(),t2.createTime)%?) = ? and DATEDIFF(NOW(),t2.createTime) > ? ";
							args.add(flow.getCycle());
							args.add((flow.getIntervals() - 1));
							args.add((flow.getIntervals() - 1));
						}
						if (1 == flow.getCycle()) { // 循环周围为1
							// ...
						}
					}

					if (TaskFlow.ISCYCLE_VIP_NO == flow.getIscycle()) {// 从vip开始算，不循环
						sql += " and DATEDIFF(NOW(),t2.createTime) = "
								+ (flow.getIntervals() - 1);
					}

				}
			}

		}

		if (null != healthLevel && healthLevel.getMaxGrade() != null
				&& healthLevel.getMinGrade() != null) {
			sql += " and t1.healthIndex >= ? and  t1.healthIndex <= ?";
			args.add(healthLevel.getMinGrade());
			args.add(healthLevel.getMaxGrade());
		}

		if (type == 1) { // 标记发不发任务
			sql += " and t1.bazzaarGrade != 0 and t1.bazzaarGrade != 6 and t1.bazzaarGrade != 7"; // 0：黑户
																			// 6：过期用户
		}

		if (null != client) {
			if (client.getId() != null) {
				sql += " and t1.id = ?";
				args.add(client.getId());
			}

			if (!StringUtils.isEmpty(client.getMobile())) {
				sql += " and t1.mobile=?";
				args.add(client.getMobile());
			}

			if (null != client.getAreaId()) {
				sql += " and t1.areaId = ?";
				args.add(client.getAreaId());
			}
		}
		logger.debug("sql==>" + sql + ";参数:" + args.toString() + "time:"
				+ DateUtils.format(new Date()));
		// 调试直接用参数执行sql，将now()可以换成当前时间。
		return executeNativeQuery(sql, args.toArray(), ClientInfo.class);

	}

	// 保存客户信息
	public void save(ClientInfo account) {
		this.add(account);
	}

	// 根据用户Id修改密码
	public void updatePwdByClientId(int id, String password) {
		String hql = "update ClientInfo set password=? where id=?";
		Object[] obj = { password, id };
		updateByHql(hql, obj);
	}

	public List findFamilyInfoByPcCode(String pcCode) {
		String hql = "select t1, t2 from ClientInfo t1, ClientPcard t2 where t1.id = t2.clientId and t2.type = ? and t1.status = ? and t2.pcCode = ? order by t1.createTime desc";
		Object[] obj = { ClientInfo.TYPE_FAMILY, ClientInfo.STATUS_NORMAL,
				pcCode };
		List list = executeFind(hql, obj);
		return list;
	}

	/**
	 * 根据区域id(areaId)查询区域下的用户数量
	 */
	public int findUserCountByareaId(Integer areaId) {
		String hql = "select count(id) from ClientInfo where areaId=? and status=?";
		Object[] object = { areaId, ClientInfo.STATUS_NORMAL };
		Object obj = findUniqueResult(hql, object);
		if ("" != obj) {
			return Integer.parseInt(obj.toString());
		} else
			return 0;
	}

	public PageObject queryClientsByUserId(QueryInfo queryInfo, ClientInfo ci,
			String areaChain, QueryCondition queryCondition) {
		ArrayList args = new ArrayList();

		String csql = "SELECT tc.id, tc.gender, tc.age, tc.type, tc.account, tc. NAME userName, tc.mobile, tc.areaId, tc.email, tc.levelId, tc.bazzaarGrade, "
				+ "tc.createTime, tc.finishPercent, tc.principalId , tc.availableProduct , te.lastFollowTime, te.lastUploadDateTime, te.source, te.flag"
				+ " FROM t_clientinfo tc LEFT JOIN t_client_extend te ON te.clientId = tc.id WHERE tc.`status` =  ?";

		args.add(ClientInfo.STATUS_NORMAL);

		if (!StringUtils.isEmpty(areaChain)) {
			String[] areaChains = areaChain.split("#");

			csql += " and (";

			if (areaChains.length > 1) {
				for (int i = 0; i < areaChains.length; i++) {
					csql += " tc.areaChain like ?";

					args.add(areaChains[i] + "%");

					if (i != areaChains.length - 1) {
						csql += " or";
					}
				}
			} else {
				csql += " tc.areaChain like ?";
				args.add(areaChains[0] + "%");
			}
			csql += ")";

		}

		if (null != ci) {
			if (ci.getId() != null) {
				csql += " and tc.id = ?";
				args.add(ci.getId());
			}
			if (!StringUtils.isEmpty(ci.getName())) {
				csql += " and tc.name like ?";
				args.add("%" + ci.getName().trim() + "%");
			}

			if (!StringUtils.isEmpty(ci.getMobile())) {
				csql += " and tc.mobile = ?";
				args.add(ci.getMobile().trim());
			}

			if (!StringUtils.isEmpty(ci.getEmail())) {
				csql += " and tc.email like ?";
				args.add("%" + ci.getEmail().trim() + "%");
			}

			if (!StringUtils.isEmpty(ci.getType())) {
				csql += " and tc.type = ?";
				args.add(ci.getType());
			}

			if (null != ci.getLevelId()) {
				csql += " and tc.levelId = ?";
				args.add(ci.getLevelId());
			}

			if (null != queryCondition) {
				if (null != queryCondition.getBeginTime()) {
					csql += " and tc.createTime >= ?";
					args.add(queryCondition.getBeginTime());
				}

				if (null != queryCondition.getEndTime()) {
					csql += " and tc.createTime <= ?";
					args.add(DateUtils.getAppointDate(queryCondition
							.getEndTime(), 1));
				}
			}
			if (!StringUtils.isEmpty(ci.getAvailableProduct())) {
				csql += " and tc.availableProduct like ?";
				args.add("%" + ci.getAvailableProduct() + "%");
			}

			if (queryCondition != null
					&& !StringUtils.isEmpty(queryCondition
							.getBazzaarGradeQuery())) {
				String bazz = queryCondition.getBazzaarGradeQuery();
				String[] bazzStr = bazz.split(",");
				csql += " and (";
				if (bazzStr.length > 1) {
					for (int i = 0; i < bazzStr.length; i++) {
						csql += " tc.bazzaarGrade = ?";

						args.add(bazzStr[i]);

						if (i != bazzStr.length - 1) {
							csql += " or";
						}
					}
				} else {
					csql += " tc.bazzaarGrade = ?";
					args.add(bazzStr[0]);
				}
				csql += ")";
			}
		}

		if (queryCondition != null) {
			if (queryCondition.getNoUploadWeek() == 1) {
				csql += " and te.lastUploadDateTime < date_sub(now(), INTERVAL 1 WEEK)";
			}
			if (!StringUtils.isEmpty(queryCondition.getRegSource())) {
				if (queryCondition.getRegSource().trim().equals("web")) {
					csql += " and (te.source = ? or te.source is null)";
				} else {
					csql += " and te.source = ?";
				}
				args.add(queryCondition.getRegSource().trim());
			}
			
			if(!StringUtils.isEmpty(queryCondition.getFlag())) {
				String flag = queryCondition.getFlag();
				String[] flags = flag.split(",");
				csql += " and (";
				if (flags.length > 1) {
					for (int i = 0; i < flags.length; i++) {
						csql += " te.flag = ?";

						args.add(flags[i]);

						if (i != flags.length - 1) {
							csql += " or";
						}
					}
				} else {
					csql += " te.flag = ?";
					args.add(flags[0]);
				}
				csql += ")";
			}
		}

		String asql = "";

		if (queryCondition != null) {
			if (!StringUtils.isEmpty(queryCondition.getHasDisease())) {
				String hasDisease = queryCondition.getHasDisease();
				String[] disease = hasDisease.split(",");
				if (disease.length == 1) {
					asql = "select DISTINCT clientId as cid from t_client_disease_self where disease = ?";
					args.add(disease[0]);
				}
				if (disease.length == 2) {
					asql = "select DISTINCT clientId as cid from t_client_disease_self where disease = ? "
							+ "and t_client_disease_self.clientId in "
							+ "(select clientId  from t_client_disease_self where disease = ?)";
					args.add(disease[0]);
					args.add(disease[1]);
				}
				if (disease.length == 3) {
					asql = "select DISTINCT clientId as cid from t_client_disease_self where disease = ? "
							+ "and t_client_disease_self.clientId in "
							+ "(select clientId  from t_client_disease_self where disease = ? "
							+ "and t_client_disease_self.clientId in "
							+ "(select clientId from t_client_disease_self where disease = ? ))";
					args.add(disease[0]);
					args.add(disease[1]);
					args.add(disease[2]);
				}
			}
		}

		String sql = "";
		if (StringUtils.isEmpty(asql)) {
			sql = csql;
			sql += " order by tc.id";
		} else {
			sql = "select m.* from (" + csql + ") m join (" + asql
					+ ") n on m.id = n.cid";

			sql += " order by m.id";
		}

		return this.queryObjectsBySql(sql, null, null, args.toArray(),
				queryInfo, ClientQueryObject.class);
	}

	public int updateBirthdayToAge() {
		String sql = "update t_clientinfo set age = ((year(now())-year(birthday)-1)+( DATE_FORMAT(birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') )) "
				+ "where birthday is not null";
		return updateBySql(sql);
	}

	public int updateLevel(String tmpName) {
		String sql1 = "update t_clientinfo set levelId = null";
		int effect = -1;
		if (updateBySql(sql1) > 0) {
			String sql2 = "update t_clientinfo t1 ," + tmpName + " t2 "
					+ "set t1.levelId = t2.levelId where t1.id = t2.clientId";
			effect = updateBySql(sql2);
		}
		return effect;
	}

	public int updateLevelByClientId(Integer clientId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update t_clientinfo set levelid = (");
		sb.append("select levelid from");
		sb.append("(");
		sb.append("select t1.productId, t3.id as levelid, t3.priority from (");
		sb
				.append("select productId, expirestime, clientid from t_order_product where clientid = ? and expirestime > now()");
		sb.append(") t1");
		sb.append(" left join t_product t2 on t1.productId = t2.id");
		sb.append(" left join t_product_level t3 on t2.levelid = t3.id");
		sb.append(" where t2. status = 0 order by t3.priority asc limit 1");
		sb.append(") t");
		sb.append(") where id = ?");

		return updateBySql(sb.toString(), new Object[] { clientId, clientId });
	}

	public List getClientInfoAndArea(ClientInfo c) {
		String hql = "select t1, t2 from ClientInfo t1, AreaInfo t2 where t1.areaId = t2.id and t1.status = ?";
		ArrayList args = new ArrayList();
		args.add(ClientInfo.STATUS_NORMAL);
		if (c != null) {
			if (!StringUtils.isEmpty(c.getName())) {
				hql += " and t1.name = ? ";
				args.add(c.getName());
			}
			if (!StringUtils.isEmpty(c.getMobile())) {
				hql += " and t1.mobile = ? ";
				args.add(c.getMobile());
			}
			if (null != c.getAreaId()) {
				hql += " and t1.areaId = ?";
				args.add(c.getAreaId());
			}

		}

		hql += " order by t1.createTime desc";
		List list = executeFind(hql, args.toArray());
		return list;
	}

	public List getClientInfoAndArea(ClientInfo c, String areaCahin) {
		String hql = "select t1, t2 from ClientInfo t1, AreaInfo t2 where t1.areaId = t2.id and t1.status = ?";
		ArrayList args = new ArrayList();
		args.add(ClientInfo.STATUS_NORMAL);
		if (!StringUtils.isEmpty(areaCahin)) {
			String[] areaChains = areaCahin.split("#");
			hql += " and (";
			if (areaChains.length > 1) {
				for (int i = 0; i < areaChains.length; i++) {
					hql += " t1.areaChain like ?";

					args.add(areaChains[i] + "%");

					if (i != areaChains.length - 1) {
						hql += " or";
					}
				}
			} else {
				hql += " t1.areaChain like ?";
				args.add(areaChains[0] + "%");
			}
			hql += ")";
		}
		if (c != null) {
			if (!StringUtils.isEmpty(c.getName())) {
				hql += " and t1.name like ? ";
				args.add("%" + c.getName() + "%");
			}
			if (!StringUtils.isEmpty(c.getMobile())) {
				hql += " and t1.mobile like ? ";
				args.add("%" + c.getMobile() + "%");
			}
			if (null != c.getAreaId()) {
				hql += " and t1.areaId = ?";
				args.add(c.getAreaId());
			}

		}

		hql += " order by t1.createTime desc";
		List list = executeFind(hql, args.toArray());
		return list;
	}

	public void updateClientInfoAndroid(ClientInfo clientInfo) {
		String sql = "update t_clientinfo set name = ?,gender = ?,birthday = ?,usualAddress = ? where id = ?";
		Object[] obj = { clientInfo.getName(), clientInfo.getGender(),
				clientInfo.getBirthday(), clientInfo.getUsualAddress(),
				clientInfo.getId() };
		updateBySql(sql, obj);
	}

	public void updateAreaChain(ClientInfo clientInfo) {
		String hql = "update t_clientinfo set areaChain=? where areaId=? ";
		Object[] obj = { clientInfo.getAreaChain(), clientInfo.getAreaId() };
		updateBySql(hql, obj);
	}

	public void updateFinishPercent(Integer clientId, double finishPercent) {
		String hql = "update t_clientinfo set finishPercent=? where id=? ";
		Object[] obj = { finishPercent, clientId };
		updateBySql(hql, obj);
	}

	/**
	 * 修改用户基本信息（运动部分）
	 * 
	 * @throws Exception
	 */
	public void updateClientInfo(ClientInfo clientInfo) {
		List args = new ArrayList();
		if (clientInfo != null) {
			String sql = "update t_clientinfo set name = ?,age = ?,gender = ?,mobile = ?,nickName = ? where id = ?";
			args.add(clientInfo.getName());
			args.add(clientInfo.getAge());
			args.add(clientInfo.getGender());
			args.add(clientInfo.getMobile());
			args.add(clientInfo.getNickName());
			args.add(clientInfo.getId());
			updateBySql(sql, args.toArray());
		}
	}

	public void alterBazzer(Integer client, Integer bazzer) {
		String sql = "update t_clientinfo set bazzaarGrade = ? where id = ?";
		List args = new ArrayList();
		args.add(bazzer);
		args.add(client);
		updateBySql(sql, args.toArray());
	}

	public void alterHealth(Integer client, Integer health) {
		String sql = "update t_clientinfo set healthIndex = ? where id = ?";
		List args = new ArrayList();
		args.add(health);
		args.add(client);
		updateBySql(sql, args.toArray());
	}

	public void updateAreaChainByClientId(ClientInfo clientInfo) {
		String sql = "update t_clientinfo set areaId = ?,areaChain = ? where id = ?";
		Object[] obj = { clientInfo.getAreaId(), clientInfo.getAreaChain(),
				clientInfo.getId() };
		updateBySql(sql, obj);
	}

	public void alterPrincipal(Integer client, Integer PrincipalId) {
		String sql = "update t_clientinfo set principalId = ? where id = ?";
		List args = new ArrayList();
		args.add(PrincipalId);
		args.add(client);
		updateBySql(sql, args.toArray());
	}

	public int updateClientInfoByCrm(ClientInfo clientInfo) {
		String sql = "update t_clientinfo set name = ?,age = ?,gender = ?,birthday = ?,idCards = ?,mobile = ?,"
				+ "phone = ?,email = ?,address = ?,workUnits = ?,vipCard = ?,areaId = ?,areaChain = ? where id = ?";

		List args = new ArrayList();
		args.add(clientInfo.getName());
		args.add(clientInfo.getAge());
		args.add(clientInfo.getGender());
		args.add(clientInfo.getBirthday());
		args.add(clientInfo.getIdCards());
		args.add(clientInfo.getMobile());
		args.add(clientInfo.getPhone());
		args.add(clientInfo.getEmail());
		args.add(clientInfo.getAddress());
		args.add(clientInfo.getWorkUnits());
		args.add(clientInfo.getVipCard());
		args.add(clientInfo.getAreaId());
		args.add(clientInfo.getAreaChain());
		args.add(clientInfo.getId());
		return updateBySql(sql, args.toArray());
	}

	public void updateHeadPortrairByClientId(Integer clientId,
			String HeadPortrairName) {
		String sql = "update t_clientinfo set headPortrait = ? where id = ?";
		List args = new ArrayList();
		args.add(HeadPortrairName);
		args.add(clientId);
		updateBySql(sql, args.toArray());
	}

	public ClientInfo getClientByMobile(ClientInfo client) {
		ArrayList args = new ArrayList();
		String hql = "from ClientInfo t1 where t1.status=?";
		args.add(Constant.STATUS_NORMAL);

		if (null != client) {
			if (!StringUtils.isEmpty(client.getMobile())) {
				hql += " and t1.mobile=?";
				args.add(client.getMobile());
			}
			if (!StringUtils.isEmpty(client.getEmail())) {
				hql += " and t1.email=?";
				args.add(client.getEmail());
			}
			if (!StringUtils.isEmpty(client.getNickName())) {
				hql += " and t1.nickName = ?";
				args.add(client.getNickName().trim());
			}
			if (!StringUtils.isEmpty(client.getUid())) {
				hql += " and t1.uid = ?";
				args.add(client.getUid());
			}

		}
		List<ClientInfo> list = executeFind(hql, args.toArray());
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	public void updateInfoByProductExpire(ClientInfo u) {
		ArrayList args = new ArrayList();
		if (null != u) {
			String sql = "update t_clientinfo set type = ?,availableProduct = ? where id = ?";
			args.add(u.getType());
			args.add(u.getAvailableProduct());
			args.add(u.getId());
			updateBySql(sql, args.toArray());
		}
	}

	public ClientInfo queryClientInfo(ClientInfo clientInfo) {
		List args = new ArrayList();
		String hql = "from ClientInfo where status = ?";
		args.add(ClientInfo.STATUS_NORMAL);
		if (clientInfo != null) {
			if (!StringUtils.isEmpty(clientInfo.getNickName())) {
				hql += " and nickName = ?";
				args.add(clientInfo.getNickName());
			}
			if (clientInfo.getId() != null) {
				hql += " and id != ?";
				args.add(clientInfo.getId());
			}
			if (!StringUtils.isEmpty(clientInfo.getMobile())) {
				hql += " and mobile = ?";
				args.add(clientInfo.getMobile());
			}
		}
		List<ClientInfo> lst = executeFind(hql, args.toArray());
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}

	public void updateClientComp(ClientInfo clientInfo) {
		List args = new ArrayList();
		if (clientInfo != null) {
			String sql = "update t_clientinfo";
			if (!StringUtils.isEmpty(clientInfo.getUid())) {
				sql += " set uid = ?";
				args.add(clientInfo.getUid());
			}
			if (clientInfo.getCompSource() != null) {
				sql += ",compSource = ?";
				args.add(clientInfo.getCompSource());
			}
			if (!StringUtils.isEmpty(clientInfo.getNickName())) {
				sql += ",nickName = ?";
				args.add(clientInfo.getNickName());
			}
			if (!StringUtils.isEmpty(clientInfo.getMobile())) {
				sql += ",mobile = ?";
				args.add(clientInfo.getMobile());
			}
			if (clientInfo.getCompSource() != null) {
				sql += ",areaId = ?";
				args.add(clientInfo.getAreaId());
				if (!StringUtils.isEmpty(clientInfo.getAreaChain())) {
					sql += ",areaChain = ?";
					args.add(clientInfo.getAreaChain());
				}
				sql += ",compSource = ?";
				args.add(clientInfo.getCompSource());
			}
			sql += " where id = ?";
			args.add(clientInfo.getId());

			updateBySql(sql, args.toArray());
		}
	}

	public void updateMobile(Integer cid, String mobile) {
		List args = new ArrayList();
		String sql = "update t_clientinfo set mobile = ? where id = ?";
		args.add(mobile);
		args.add(cid);
		updateBySql(sql, args.toArray());
	}

	public PageObject<ClientUploadStat> queryUploadClientInfo(long days,
			ClientInfo clientInfo, QueryInfo queryInfo) {
		List args = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append(" select m.*, n.name, n.mobile, n.source from (");
		sb.append("	SELECT");
		sb.append("	`m_blood_pressure`.`id` AS `id`,");
		sb.append("	`m_blood_pressure`.`clientId` AS `clientId`,");
		sb.append("	`m_blood_pressure`.`testDateTime` AS `testDateTime`,");
		sb.append(" `m_blood_pressure`.`uploadDateTime` AS `uploadDateTime`,");
		sb.append("	`m_blood_pressure`.`sbp` AS `val1`,");
		sb.append(" `m_blood_pressure`.`dbp` AS `val2`,");
		sb.append(" `m_blood_pressure`.`state` AS `state`,");
		sb.append(" `m_blood_pressure`.`dispose` AS `dispose`,");
		sb.append(" 1 AS `type`,");
		sb.append("	0 AS `stype`");
		sb.append("	FROM");
		sb.append("	`m_blood_pressure`");
		sb.append("	WHERE");
		sb.append("	(");
		sb.append(" (1 = 1)");
		sb.append("	AND (");
		sb.append("	`m_blood_pressure`.`uploadDateTime` >= date_format(");
		sb.append("	(now() + INTERVAL -(?) DAY),");
		sb.append("	'%Y-%m-%d'");
		sb.append("	)");
		sb.append("	)");
		sb.append("	AND (");
		sb.append("	`m_blood_pressure`.`uploadDateTime` <= now()");
		sb.append("	)");
		sb.append("	)");
		sb.append("	UNION");
		sb.append("	SELECT");
		sb.append("	`m_blood_oxygen`.`id` AS `id`,");
		sb.append("	`m_blood_oxygen`.`clientId` AS `clientId`,");
		sb.append("	`m_blood_oxygen`.`testDateTime` AS `testDateTime`,");
		sb.append("	`m_blood_oxygen`.`uploadDateTime` AS `uploadDateTime`,");
		sb.append("	`m_blood_oxygen`.`bloodOxygen` AS `val1`,");
		sb.append("	`m_blood_oxygen`.`heartbeat` AS `val2`,");
		sb.append("	`m_blood_oxygen`.`state` AS `state`,");
		sb.append("	`m_blood_oxygen`.`dispose` AS `dispose`,");
		sb.append("	2 AS `type`,");
		sb.append("	0 AS `stype`");
		sb.append("	FROM");
		sb.append("	`m_blood_oxygen`");
		sb.append("	WHERE");
		sb.append("	(");
		sb.append("	(1 = 1)");
		sb.append("	AND (");
		sb.append("	`m_blood_oxygen`.`uploadDateTime` >= date_format(");
		sb.append("	(now() + INTERVAL -(?) DAY),");
		sb.append("	'%Y-%m-%d'");
		sb.append("	)");
		sb.append("	)");
		sb.append("	AND (");
		sb.append("	`m_blood_oxygen`.`uploadDateTime` <= now()");
		sb.append("	)");
		sb.append("	)");
		sb.append("	UNION");
		sb.append("	SELECT");
		sb.append("	`m_electrocardiogram`.`id` AS `id`,");
		sb.append("	`m_electrocardiogram`.`clientId` AS `clientId`,");
		sb.append("	`m_electrocardiogram`.`testDateTime` AS `testDateTime`,");
		sb
				.append("	`m_electrocardiogram`.`uploadDateTime` AS `uploadDateTime`,");
		sb.append("	`m_electrocardiogram`.`averageHeartRate` AS `val1`,");
		sb.append("	`m_electrocardiogram`.`attachmentUrl` AS `val2`,");
		sb.append("	`m_electrocardiogram`.`state` AS `state`,");
		sb.append("	`m_electrocardiogram`.`dispose` AS `dispose`,");
		sb.append("	3 AS `type`,");
		sb.append("	0 AS `stype`");
		sb.append("	FROM");
		sb.append("	`m_electrocardiogram`");
		sb.append("	WHERE");
		sb.append("	(");
		sb.append("	(1 = 1)");
		sb.append("	AND (");
		sb.append("	`m_electrocardiogram`.`uploadDateTime` >= date_format(");
		sb.append("	(now() + INTERVAL -(?) DAY),");
		sb.append("	'%Y-%m-%d'");
		sb.append("	)");
		sb.append("	)");
		sb.append("	AND (");
		sb.append("	`m_electrocardiogram`.`uploadDateTime` <= now()");
		sb.append("	)");
		sb.append("	)");
		sb.append("	UNION");
		sb.append("	SELECT");
		sb.append("	`m_blood_sugar`.`id` AS `id`,");
		sb.append("	`m_blood_sugar`.`clientId` AS `clientId`,");
		sb.append("	`m_blood_sugar`.`testDateTime` AS `testDateTime`,");
		sb.append("	`m_blood_sugar`.`uploadDateTime` AS `uploadDateTime`,");
		sb.append("	`m_blood_sugar`.`bloodSugarValue` AS `val1`,");
		sb.append("	'' AS `val2`,");
		sb.append("	`m_blood_sugar`.`state` AS `state`,");
		sb.append("	`m_blood_sugar`.`dispose` AS `dispose`,");
		sb.append("	4 AS `type`,");
		sb.append("	`m_blood_sugar`.`bloodSugarType` AS `stype`");
		sb.append("	FROM");
		sb.append("	`m_blood_sugar`");
		sb.append("	WHERE");
		sb.append("	(");
		sb.append("	(1 = 1)");
		sb.append("	AND (");
		sb.append("	`m_blood_sugar`.`uploadDateTime` >= date_format(");
		sb.append("	(now() + INTERVAL -(?) DAY),");
		sb.append("	'%Y-%m-%d'");
		sb.append("	)");
		sb.append("	)");
		sb.append("	AND (");
		sb.append("	`m_blood_sugar`.`uploadDateTime` <= now()");
		sb.append("	)");
		sb.append("	)");
		sb.append("	UNION");
		sb.append("	SELECT");
		sb.append("	`m_temperature`.`id` AS `id`,");
		sb.append("	`m_temperature`.`clientId` AS `clientId`,");
		sb.append("	`m_temperature`.`testDateTime` AS `testDateTime`,");
		sb.append("	`m_temperature`.`uploadDateTime` AS `uploadDateTime`,");
		sb.append("	`m_temperature`.`temperature` AS `val1`,");
		sb.append("	'' AS `val2`,");
		sb.append("	`m_temperature`.`state` AS `state`,");
		sb.append("	`m_temperature`.`dispose` AS `dispose`,");
		sb.append("	7 AS `type`,");
		sb.append("	0 AS `stype`");
		sb.append("	FROM");
		sb.append("	`m_temperature`");
		sb.append("	WHERE");
		sb.append("	(");
		sb.append("	(1 = 1)");
		sb.append("	AND (");
		sb.append("	`m_temperature`.`uploadDateTime` >= date_format(");
		sb.append("	(now() + INTERVAL -(?) DAY),");
		sb.append("	'%Y-%m-%d'");
		sb.append("	)");
		sb.append("	)");
		sb.append("	AND (");
		sb.append("	`m_temperature`.`uploadDateTime` <= now()");
		sb.append("	)");
		sb.append("	)");
		sb.append("	) m");
		sb.append("	LEFT JOIN (");
		sb.append("	SELECT");
		sb.append("	m.clientId,");
		sb.append("	m.source,");
		sb.append("	n. NAME,");
		sb.append("	n.mobile");
		sb.append("	FROM");
		sb.append("	t_client_extend m");
		sb.append("	LEFT JOIN t_clientinfo n ON m.clientId = n.id");
		sb.append("	) n ON m.clientId = n.clientId");
		sb.append("	WHERE");
		sb.append("	n.source IN (");
		sb.append("	'bsksugar',");
		sb.append("	'php_sugar',");
		sb.append("	'DiabetesManagement'");
		sb.append("	)");

		args.add(days);
		args.add(days);
		args.add(days);
		args.add(days);
		args.add(days);

		if (clientInfo != null) {
			if (!StringUtils.isEmpty(clientInfo.getName())) {
				sb.append(" and n.name like ?");
				args.add("%"+clientInfo.getName()+"%");
			}

			if (!StringUtils.isEmpty(clientInfo.getMobile())) {
				sb.append(" and n.mobile like ?");
				args.add("%"+clientInfo.getMobile()+"%");
			}
		}

		sb.append("	GROUP BY");
		sb.append("	m.clientId");

		Map scalars = new LinkedHashMap();
		scalars.put("id", StandardBasicTypes.INTEGER);
		scalars.put("clientId", StandardBasicTypes.INTEGER);
		scalars.put("testDateTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("uploadDateTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("val1", StandardBasicTypes.DOUBLE);
		scalars.put("val2", StandardBasicTypes.STRING);
		scalars.put("state", StandardBasicTypes.INTEGER);
		scalars.put("dispose", StandardBasicTypes.INTEGER);
		scalars.put("type", StandardBasicTypes.INTEGER);
		scalars.put("stype", StandardBasicTypes.INTEGER);
		scalars.put("name", StandardBasicTypes.STRING);
		scalars.put("mobile", StandardBasicTypes.STRING);
		scalars.put("source", StandardBasicTypes.STRING);

		return this.queryObjectsBySql(sb.toString(), null, scalars, args
				.toArray(), queryInfo, ClientUploadStat.class);
	}

	public PageObject queryClientUploadCount(ClientInfo clientInfo, Date beginTime, Date endTime, QueryInfo queryInfo){
		
		List args = new ArrayList();
		StringBuffer sb = new StringBuffer();
			sb.append("SELECT");
				sb.append(" n. name,");
				sb.append(" n.mobile,");
				sb.append(" count(clientId) AS total");
			sb.append("	FROM" );
				sb.append("	(");
					sb.append("	SELECT");
						sb.append("	`m_blood_pressure`.`id` AS `id`,");
						sb.append("	`m_blood_pressure`.`clientId` AS `clientId`,");
						sb.append("	`m_blood_pressure`.`testDateTime` AS `testDateTime`,");
						sb.append("	`m_blood_pressure`.`uploadDateTime` AS `uploadDateTime`,");
						sb.append("	`m_blood_pressure`.`sbp` AS `val1`,");
						sb.append("	`m_blood_pressure`.`dbp` AS `val2`,");
						sb.append("	`m_blood_pressure`.`state` AS `state`,");
						sb.append("	`m_blood_pressure`.`dispose` AS `dispose`,");
						sb.append("	1 AS `type`,");
						sb.append("	0 AS `stype`");
					sb.append("	FROM");
						sb.append("	`m_blood_pressure`");
					sb.append("	WHERE");
						sb.append("	(");
							sb.append("	(1 = 1)");
							if(beginTime != null && endTime != null){
								sb.append("	AND (");
									sb.append("	`m_blood_pressure`.`uploadDateTime` >= ?");
								sb.append("	)");
								sb.append(" AND (");
									sb.append("	`m_blood_pressure`.`uploadDateTime` <= ?");
								sb.append("	)");
								
								args.add(beginTime);
								args.add(endTime);
							}
						sb.append("	)");
					sb.append("	UNION");
						sb.append("	SELECT");
							sb.append("	`m_blood_oxygen`.`id` AS `id`,");
							sb.append("	`m_blood_oxygen`.`clientId` AS `clientId`,");
							sb.append("	`m_blood_oxygen`.`testDateTime` AS `testDateTime`,");
							sb.append("	`m_blood_oxygen`.`uploadDateTime` AS `uploadDateTime`,");
							sb.append("	`m_blood_oxygen`.`bloodOxygen` AS `val1`,");
							sb.append("	`m_blood_oxygen`.`heartbeat` AS `val2`,");
							sb.append("	`m_blood_oxygen`.`state` AS `state`,");
							sb.append("	`m_blood_oxygen`.`dispose` AS `dispose`,");
							sb.append("	2 AS `type`,");
							sb.append("	0 AS `stype`");
						sb.append("	FROM");
							sb.append("	`m_blood_oxygen`");
						sb.append("	WHERE");
							sb.append("	(");
								sb.append("	(1 = 1)");
								if(beginTime != null && endTime != null){
									sb.append("	AND (");
										sb.append("	`m_blood_oxygen`.`uploadDateTime` >= ?");
									sb.append("	)");
									sb.append("	AND (");
										sb.append("	`m_blood_oxygen`.`uploadDateTime` <= ?");
									sb.append("	)");
									
									args.add(beginTime);
									args.add(endTime);
								}
							sb.append("	)");
						sb.append("	UNION");
							sb.append("	SELECT");
								sb.append("	`m_electrocardiogram`.`id` AS `id`,");
								sb.append("	`m_electrocardiogram`.`clientId` AS `clientId`,");
								sb.append("	`m_electrocardiogram`.`testDateTime` AS `testDateTime`,");
								sb.append("	`m_electrocardiogram`.`uploadDateTime` AS `uploadDateTime`,");
								sb.append("	`m_electrocardiogram`.`averageHeartRate` AS `val1`,");
								sb.append("	`m_electrocardiogram`.`attachmentUrl` AS `val2`,");
								sb.append("	`m_electrocardiogram`.`state` AS `state`,");
								sb.append("	`m_electrocardiogram`.`dispose` AS `dispose`,");
								sb.append("	3 AS `type`,");
								sb.append("	0 AS `stype`");
							sb.append("	FROM");
								sb.append("	`m_electrocardiogram`");
							sb.append("	WHERE");
								sb.append("	(");
									sb.append("	(1 = 1)");
									if(beginTime != null && endTime != null){
										sb.append("	AND (");
											sb.append("	`m_electrocardiogram`.`uploadDateTime` >= ?");
										sb.append("	)");
										sb.append("	AND (");
											sb.append("	`m_electrocardiogram`.`uploadDateTime` <= ?");
										sb.append("	)");
										
										args.add(beginTime);
										args.add(endTime);
									}
								sb.append("	)");
							sb.append("	UNION");
								sb.append("	SELECT");
									sb.append("	`m_blood_sugar`.`id` AS `id`,");
									sb.append("	`m_blood_sugar`.`clientId` AS `clientId`,");
									sb.append("	`m_blood_sugar`.`testDateTime` AS `testDateTime`,");
									sb.append("	`m_blood_sugar`.`uploadDateTime` AS `uploadDateTime`,");
									sb.append("	`m_blood_sugar`.`bloodSugarValue` AS `val1`,");
									sb.append("	'' AS `val2`,");
									sb.append("	`m_blood_sugar`.`state` AS `state`,");
									sb.append("	`m_blood_sugar`.`dispose` AS `dispose`,");
									sb.append("	4 AS `type`,");
									sb.append("	`m_blood_sugar`.`bloodSugarType` AS `stype`");
								sb.append("	FROM");
									sb.append("	`m_blood_sugar`");
								sb.append("	WHERE");
									sb.append("	(");
										sb.append("	(1 = 1)");
										if(beginTime != null && endTime != null){
											sb.append("	AND (");
												sb.append("	`m_blood_sugar`.`uploadDateTime` >= ?");
											sb.append("	)");
											sb.append("	AND (");
												sb.append("	`m_blood_sugar`.`uploadDateTime` <= ?");
											sb.append("	)");
											
											args.add(beginTime);
											args.add(endTime);
										}
									sb.append("	)");
								sb.append("	UNION");
									sb.append("	SELECT");
										sb.append("	`m_temperature`.`id` AS `id`,");
										sb.append("	`m_temperature`.`clientId` AS `clientId`,");
										sb.append("	`m_temperature`.`testDateTime` AS `testDateTime`,");
										sb.append("	`m_temperature`.`uploadDateTime` AS `uploadDateTime`,");
										sb.append("	`m_temperature`.`temperature` AS `val1`,");
										sb.append("	'' AS `val2`,");
										sb.append("	`m_temperature`.`state` AS `state`,");
										sb.append("	`m_temperature`.`dispose` AS `dispose`,");
										sb.append("	6 AS `type`,");
										sb.append("	0 AS `stype`");
									sb.append("	FROM");
										sb.append("	`m_temperature`");
									sb.append("	WHERE");
										sb.append("	(");
											sb.append("	(1 = 1)");
											if(beginTime != null && endTime != null){
												sb.append("	AND (");
													sb.append("	`m_temperature`.`uploadDateTime` >= ?");
												sb.append("	)");
												sb.append("	AND (");
													sb.append("	`m_temperature`.`uploadDateTime` <= ?");
												sb.append("	)");
												
												args.add(beginTime);
												args.add(endTime);
											}
										sb.append("	)");
				sb.append("	) m");
			sb.append("	LEFT JOIN t_clientinfo n ON m.clientId = n.id");
			sb.append("	WHERE");
				sb.append("	n.id != 10001");
				if(clientInfo != null){
					if(!StringUtils.isEmpty(clientInfo.getName())){
						sb.append(" and n.name like ?");
						String name = clientInfo.getName();
						args.add("%"+name.trim()+"%");
					}
					if(!StringUtils.isEmpty(clientInfo.getMobile())){
						sb.append(" and n.mobile like ?");
						String mobile = clientInfo.getMobile();
						args.add("%"+mobile.trim()+"%");
					}
					if(!StringUtils.isEmpty(clientInfo.getAvailableProduct())){
						sb.append(" and n.availableProduct like ?");
						String avail = clientInfo.getAvailableProduct();
						args.add("%"+avail.trim()+"%");
					}
				}
				
			sb.append("	GROUP BY clientId");
			sb.append(" order by count(clientId) desc");
			
			Map scalars = new LinkedHashMap();
			scalars.put("name", StandardBasicTypes.STRING);
			scalars.put("mobile", StandardBasicTypes.STRING);
			scalars.put("total", StandardBasicTypes.INTEGER);

			return this.queryObjectsBySql(sb.toString(), null, scalars, 
					args.toArray(), queryInfo, null);
			
	}
	
	
	/**
	 * 生成新版血糖高管健康报告
	 */
	public List<ClientInfo> queryClientInfo(){
		List args = new ArrayList();
		String sql = "select n.* from (select DISTINCT(clientId) id from tg_evaluating_result) m " +
					"LEFT JOIN t_clientinfo n on m.id = n.id where n.areaId != 1811 and n.`status` = ?";
				args.add(ClientInfo.STATUS_NORMAL);
		return executeNativeQuery(sql, args.toArray(), ClientInfo.class);
	}
	
	
	public List<DoctorInfo> queryDoctorInviteClient(String mobile){
		List args = new ArrayList();
		String sql = "select n.id, n.phone mobile, n.`name` from (select * from t_doctor_invite_client where mobile = ? and isInvite = 0) m " +
				"LEFT JOIN bskdoc.doc_doctorinfo n on m.doctorId = n.id";
		args.add(mobile);
		
		return executeNativeQuery(sql, args.toArray(), DoctorInfo.class);
	}
	
	public int queryRegClientCount(Date startDate, Date endDate){
		List args = new ArrayList();
		String sql = "select count(DISTINCT(clientId)) from t_client_extend where source in('bsksugar','php_sugar','DiabetesManagement')";
		if(startDate != null){
			sql += " and regTime >= ?";
			args.add(startDate);
		}
		if(endDate != null){
			sql += " and regTime <= ?";
			args.add(endDate);
		}
		
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	
	
	public int queryClientPictureCount(Date startDate, Date endDate){
		List args = new ArrayList();
		String sql = "select count(*) from bskdoc.doc_picture_consulting_info where 1 = 1";
		if(startDate != null){
			sql += " and createTime >= ?";
			args.add(startDate);
		}
		if(endDate != null){
			sql += " and createTime <= ?";
			args.add(endDate);
		}
		
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	
	public int queryDoctorInviteClientCount(String date){
		List args = new ArrayList();
		String sql = "select count(DISTINCT(clientId)) from (select * from t_client_extend where" +
				" source in('bsksugar','php_sugar','DiabetesManagement') and regTime LIKE ? " +
				" and passivityinvite is NOT NULL) m JOIN bskdoc.doc_doctorinfo n ON m.passivityinvite = n.askCode";
		
		args.add(date+"%");
		
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}

	public int queryClientInviteCount(String date){
		List args = new ArrayList();
		String sql = "select count(DISTINCT(m.clientId)) from (select * from t_client_extend where" +
				" source in('bsksugar','php_sugar','DiabetesManagement') and regTime LIKE ? and passivityinvite is NOT NULL) m" +
				" JOIN t_client_extend n ON m.passivityinvite = n.initiativeinvite";
		
		args.add(date+"%");
		
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	
	public int queryRegSelfCount(String date){
		List args = new ArrayList();
		String sql = "select count(DISTINCT(clientId)) from t_client_extend where source in('bsksugar','php_sugar','DiabetesManagement') and regTime LIKE ? and passivityinvite is NULL;";
		args.add(date+"%");
		
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	
	public PageObject<ClientRegEval> queryAllInfoDate(Date startDate, Date endDate, QueryInfo queryInfo){
		List args = new ArrayList();
		String sql = "select m.createTime from ( " +
				" select DATE_FORMAT(createTime,'%Y-%m-%d') createTime from t_clientinfo GROUP BY DATE_FORMAT(createTime,'%Y-%m-%d')" +
				" UNION ALL" +
				" select DATE_FORMAT(testDate,'%Y-%m-%d') createTime from tg_evaluating_result GROUP BY  DATE_FORMAT(testDate,'%Y-%m-%d')" +
				" UNION ALL" +
				" select  DATE_FORMAT(testDateTime,'%Y-%m-%d') createTime from m_blood_sugar GROUP BY  DATE_FORMAT(testDateTime,'%Y-%m-%d')" +
				" UNION ALL" +
				" select DATE_FORMAT(createTime,'%Y-%m-%d') createTime from bskdoc.doc_picture_consulting_info GROUP BY  DATE_FORMAT(createTime,'%Y-%m-%d')" +
				") m where 1 = 1";
		
		if(startDate != null){
			sql += " and createTime >= ?";
			args.add(startDate);
		}
		if(endDate != null){
			sql += " and createTime <= ?";
			args.add(endDate);
		}
		sql += " GROUP BY m.createTime ORDER BY m.createTime DESC";
		
		Map scalars = new LinkedHashMap();
		scalars.put("createTime", StandardBasicTypes.TIMESTAMP);
		
		return queryObjectsBySql(sql, null, scalars, args.toArray(), queryInfo, ClientRegEval.class);
	}
	
	
	public List<ClientRegEval> queryAllInfoDetail(Date startDate, Date endDate){
		List args = new ArrayList();
		String sql = "select m.* from (" +
				" select DATE_FORMAT(regTime,'%Y-%m-%d') createTime, 1 as type, COUNT(*) count from t_client_extend where source in('bsksugar','php_sugar','DiabetesManagement') GROUP BY DATE_FORMAT(regTime,'%Y-%m-%d')" +
				" UNION ALL" +
				" select DATE_FORMAT(testDate,'%Y-%m-%d') createTime, 2 as type, COUNT(*) count from tg_evaluating_result GROUP BY DATE_FORMAT(testDate,'%Y-%m-%d')" +
				" UNION ALL" +
				" select  DATE_FORMAT(testDateTime,'%Y-%m-%d') createTime, 3 as type, COUNT(*) count from m_blood_sugar GROUP BY  DATE_FORMAT(testDateTime,'%Y-%m-%d')" +
				" UNION ALL" +
				" select DATE_FORMAT(createTime,'%Y-%m-%d') createTime, 4 as type, COUNT(*) count  from bskdoc.doc_picture_consulting_info GROUP BY  DATE_FORMAT(createTime,'%Y-%m-%d')" +
				" ) m where 1 = 1";
		
		if(startDate != null){
			sql += " and createTime >= ?";
			args.add(startDate);
		}
		if(endDate != null){
			sql += " and createTime <= ?";
			args.add(endDate);
		}
		sql += " ORDER BY m.createTime DESC";
		
		Map scalars = new LinkedHashMap();
		scalars.put("createTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("type", StandardBasicTypes.INTEGER);
		scalars.put("count", StandardBasicTypes.INTEGER);
		
		return executeNativeQuery(sql, null, scalars, args.toArray(), ClientRegEval.class);
	}
	
	
	public List<ClientRegEval> querySumInfoDetail(Date startDate, Date endDate){
		List args = new ArrayList();
		String sql = "select m.type, sum(m.count) count from (" +
				" select 1 as type, COUNT(*) count from t_client_extend where source in('bsksugar','php_sugar','DiabetesManagement')";
				if(startDate != null){
					sql += " and regTime >= ?";
					args.add(startDate);
				}
				if(endDate != null){
					sql += " and regTime <= ?";
					args.add(endDate);
				}
				sql += " GROUP BY  DATE_FORMAT(regTime,'%Y-%m-%d')";
		  sql+= " UNION ALL"+
				" select 2 as type, COUNT(*) count from tg_evaluating_result where 1 = 1";
				if(startDate != null){
					sql += " and testDate >= ?";
					args.add(startDate);
				}
				if(endDate != null){
					sql += " and testDate <= ?";
					args.add(endDate);
				}
				sql += " GROUP BY  DATE_FORMAT(testDate,'%Y-%m-%d')";
		  sql+= " UNION ALL"+
				" select 3 as type, COUNT(*) count from m_blood_sugar where 1 = 1";
			    if(startDate != null){
					sql += " and testDateTime >= ?";
					args.add(startDate);
				}
				if(endDate != null){
					sql += " and testDateTime <= ?";
					args.add(endDate);
				}
				sql += " GROUP BY  DATE_FORMAT(testDateTime,'%Y-%m-%d')";
		  sql+= " UNION ALL"+
				" select 4 as type, COUNT(*) count  from bskdoc.doc_picture_consulting_info" +
				" where 1 = 1";
			    if(startDate != null){
					sql += " and createTime >= ?";
					args.add(startDate);
				}
				if(endDate != null){
					sql += " and createTime <= ?";
					args.add(endDate);
				}
				sql += " GROUP BY  DATE_FORMAT(createTime,'%Y-%m-%d')";
		  sql += ") m GROUP BY m.type ORDER BY m.type";
		
		Map scalars = new LinkedHashMap();
		scalars.put("type", StandardBasicTypes.INTEGER);
		scalars.put("count", StandardBasicTypes.INTEGER);
		
		return executeNativeQuery(sql, null, scalars, args.toArray(), ClientRegEval.class);
	}
	
	
	public void updateClientInfo(String data, int type, Integer cid){
		if(!StringUtils.isEmpty(data) && type != 0){
			List args = new ArrayList();
			String hql = "update ClientInfo";
			if(type == 1){
				hql += " set mobile = ?";
				args.add(data);
			}else if(type == 2){
				hql += " set name = ?";
				args.add(data);
			}else if(type == 3){
				hql += " set gender = ?";
				args.add(Integer.parseInt(data));
			}else if(type == 4){
				hql += " set birthday = ?";
				args.add(DateUtils.parseDate(data, "yyy-MM-dd"));
			}
			
			hql += " where id = ?";
			args.add(cid);
			
			updateByHql(hql, args.toArray());
		}
	}
	
	
	public ClientInfoExtend queryClientExtend(Integer clientId){
		String sql = "select m.*, n.balance, n.totalScore from (select * from t_clientinfo where id = ?)" +
				" m LEFT JOIN (SELECT * from  t_client_extend where clientId = ?) n on m.id = n.clientId;";
		List args = new ArrayList();
		args.add(clientId);
		args.add(clientId);
		
		List<ClientInfoExtend> lst = executeNativeQuery(sql, args.toArray(), ClientInfoExtend.class);
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}
}
