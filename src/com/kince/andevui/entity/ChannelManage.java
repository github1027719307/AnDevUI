package com.kince.andevui.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kince.andevui.Constants;
import com.kince.andevui.db.SQLHelper;

import android.database.SQLException;
import android.util.Log;
/**
 * @author kince
 * @category 频道数据管理
 * @since 2014.7.3
 * @version v1.0.0
 *
 */
public class ChannelManage {

	public static ChannelManage channelManage;
	/** 默认的用户选择频道列表 */
	public static List<ChannelItem> defaultUserChannels;
	/** 默认的其他频道列表 */
	public static List<ChannelItem> defaultOtherChannels;

	private ChannelDao channelDao;
	/** 判断数据库中是否存在用户数据 */
	private boolean userExist = false;

	static {
		defaultUserChannels = new ArrayList<ChannelItem>();
		defaultOtherChannels = new ArrayList<ChannelItem>();

		defaultUserChannels.add(new ChannelItem(1,
				Constants.RECOMMEND_FRAGMENT, 1, 1));
		defaultUserChannels
				.add(new ChannelItem(2, Constants.HOT_FRAGMENT, 2, 1));
		defaultUserChannels.add(new ChannelItem(3, Constants.POPULAR_FRAGMENT,
				3, 1));
		defaultUserChannels.add(new ChannelItem(4, Constants.IMITATE_FRAGMENT,
				4, 1));
		defaultUserChannels.add(new ChannelItem(5, Constants.IDEA_FRAGMENT, 5,
				1));
		defaultUserChannels.add(new ChannelItem(6,
				Constants.ANIMATION_FRAGMENT, 6, 1));
		defaultUserChannels.add(new ChannelItem(7,
				Constants.REQUIREMENT_FRAGMENT, 7, 1));

		defaultOtherChannels.add(new ChannelItem(8, Constants.DESIGN_FRAGMENT,
				1, 0));
		defaultOtherChannels.add(new ChannelItem(9, Constants.COURSE_FRAGMENT,
				2, 0));

	}

	private ChannelManage(SQLHelper paramDBHelper) throws SQLException {
		if (channelDao == null)
			channelDao = new ChannelDao(paramDBHelper.getContext());
		// NavigateItemDao(paramDBHelper.getDao(NavigateItem.class));
		return;
	}

	/**
	 * 初始化频道管理类
	 * 
	 * @param paramDBHelper
	 * @throws SQLException
	 */
	public static ChannelManage getManage(SQLHelper dbHelper)
			throws SQLException {
		if (channelManage == null)
			channelManage = new ChannelManage(dbHelper);
		return channelManage;
	}

	/**
	 * 清除所有的频道
	 * 
	 */
	public void deleteAllChannel() {
		channelDao.clearFeedTable();
	}

	/**
	 * 获取其他的频道
	 * 
	 * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
	 */
	public List<ChannelItem> getUserChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?",
				new String[] { "1" });
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			userExist = true;
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			List<ChannelItem> list = new ArrayList<ChannelItem>();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate = new ChannelItem();
				navigate.setId(Integer
						.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(
						SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(
						SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		initDefaultChannel();
		return defaultUserChannels;
	}

	/**
	 * 获取其他的频道
	 * 
	 * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
	 */
	public List<ChannelItem> getOtherChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?",
				new String[] { "0" });
		List<ChannelItem> list = new ArrayList<ChannelItem>();
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate = new ChannelItem();
				navigate.setId(Integer
						.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(
						SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(
						SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		if (userExist) {
			return list;
		}
		cacheList = defaultOtherChannels;
		return (List<ChannelItem>) cacheList;
	}

	/**
	 * 保存用户频道到数据库
	 * 
	 * @param userList
	 */
	public void saveUserChannel(List<ChannelItem> userList) {
		for (int i = 0; i < userList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) userList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(1));
			channelDao.addCache(channelItem);
		}
	}

	/**
	 * @category 保存其他频道到数据库
	 * @param otherList
	 * 
	 */
	public void saveOtherChannel(List<ChannelItem> otherList) {
		for (int i = 0; i < otherList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) otherList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(0));
			channelDao.addCache(channelItem);
		}
	}

	/**
	 * @category 初始化数据库内的频道数据
	 * 
	 */
	private void initDefaultChannel() {
		Log.d("deleteAll", "deleteAll");
		deleteAllChannel();
		saveUserChannel(defaultUserChannels);
		saveOtherChannel(defaultOtherChannels);
	}

}
