package com.kince.andevui;

import java.util.ArrayList;

import com.kince.andevui.entity.CodeClass;
import com.kince.andevui.entity.ItemModel;

/**
 * @author kince
 * @category 程序所用常量
 * 
 * 
 */
public class Constants {

	/** 人人第三方登陆 */
	public static final String RENREN_APP_ID = "269679";
	public static final String RENREN_API_KEY = "c3d7ba63565f4626be799613b326df2b";
	public static final String RENREN_SECRET_KEY = "c3e4354ecd7f4d3bbfdcb8dbe2a643ed";
	
    /** 根目录 */
    static final String ROOT_DIR = "gfan";

    /** 缓存目录 */
    static final String IMAGE_CACHE_DIR = ROOT_DIR + "/.cache";

    /** 截屏图片缓存目录 */
    static final String IMAGE_SNAPSHOT_DIR = ROOT_DIR + "/.snapshot";
    
	/** mark=0 ：推荐 */
	public final static int mark_recom = 0;
	/** mark=1 ：热门 */
	public final static int mark_hot = 1;
	/** mark=2 ：首发 */
	public final static int mark_frist = 2;
	/** mark=3 ：独家 */
	public final static int mark_exclusive = 3;
	/** mark=4 ：收藏 */
	public final static int mark_favor = 4;

	public static final CodeClass[] JXX_IMAGES_CODECLASS = new CodeClass[] {

			new CodeClass(
					"http://m2.img.srcdd.com/farm4/d/2014/0515/11/1A2EEFA26898EA170DCB57DD05B4E961_B500_900_500_400.png",
					"xiuxian"),
			new CodeClass(
					"http://m2.img.srcdd.com/farm4/d/2014/0515/11/55237F77D8F7E6368EA641AD2A8699C5_B250_400_250_111.png",
					"xiuxian"),
			new CodeClass(
					"http://m2.img.srcdd.com/farm4/d/2014/0515/11/DEFCB08E651C170D89205995811C502F_B250_400_250_321.png",
					"xiuxian"),
			new CodeClass(
					"http://m2.img.srcdd.com/farm5/d/2014/0515/11/ED068AC97AB4D31841A9C608E1883EA7_B500_900_500_324.png",
					"xiuxian"),
			new CodeClass(
					"http://m3.img.srcdd.com/farm5/d/2014/0515/11/D7980D4C983E89C38BDCD947D2DA37E3_B250_400_250_257.png",
					"xiuxian"),
			new CodeClass(
					"http://m2.img.srcdd.com/farm5/d/2014/0515/11/5C5BC88D474CCF89C4EFF0D89E457ED0_B250_400_250_231.png",
					"xiuxian") };

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	public static class Extra {
		public static final String IMAGES = "com.nostra13.example.universalimageloader.IMAGES";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	}

	public static final String ANIMATION_FRAGMENT = "动画";
	public static final String POPULAR_FRAGMENT = "流行";
	public static final String RECOMMEND_FRAGMENT = "推荐";
	public static final String REQUIREMENT_FRAGMENT = "需求";
	public static final String IMITATE_FRAGMENT = "模仿";
	public static final String IDEA_FRAGMENT = "创意";
	public static final String HOT_FRAGMENT = "热点";
	public static final String DESIGN_FRAGMENT = "设计";
	public static final String COURSE_FRAGMENT = "教程";

	// 装机必备模式提示次数
    static final String ALERT = "提醒";
    // 通过提示进入装机必备模式的次数
    static final String ALERT_CLICK = "点击提醒";
    
    // 搜索打开次数
    static final String OPEN_SEARCH = "点击进入搜索";
    // 搜索页点击搜索按钮次数
    static final String CLICK_SEARCH = "点击搜索按钮";
    // 点击热门关键词次数
    static final String CLICK_SEARCH_KEYWORDS = "点击热门关键词";
    // 搜索结果页点击论坛tab的次数
    static final String CLICK_SEARCH_BBS = "点击论坛TAB";
    // 搜索结果页附件下载次数
    static final String CLICK_SEARCH_BBS_APK = "附件下载";
    // 首页头部广告图9张，每一张的点击
    static final String CLICK_RECOMMEND_TOP = "点击顶部推荐位 -> ";
    // 分类菜单点击次数
    static final String CLICK_CATEGORY_TAB = "点击分类TAB";
    // 排行菜单点击次数
    static final String CLICK_RANK_TAB = "点击排行TAB";
    // 管理菜单点击次数
    static final String CLICK_MANAGER_TAB = "点击管理TAB";
    // 首页菜单点击次数
    static final String CLICK_HOME_TAB = "点击首页TAB";

    // static final String 首页30条应用已安装的比例
    // static final String 首页30条应用点击应用名称及图标区域的次数
    // 首页30条应用点击直接下载、更新的次数
    static final String DIRECT_DOWNLOAD = "直接下载、更新";

    // 装机必备入口点击数
    static final String ENTRY = "点击装机必备";
    // 专题入口点击次数
    static final String CLICK_TOPIC_ENTRY = "点击专题";
    // 每个专题点击次数
    static final String CLICK_SUB_TOPIC = "点击专题 -> ";
    // 每个分类、子分类点击次数
    static final String CLICK_CATEGORY_ITEM = "点击分类 -> ";
    // 每个子分类下“最新”tab点击次数
    static final String CLICK_SUB_CATEGORY_NEW_TAB = "点击最新TAB";
    // 每个子分类最热、最新产品列表的下一页读取次数
    static final String PRODUCT_LAZY_LOAD = "产品延迟加载";
    // 排行下各个tab点击次数
    static final String CLICK_RANK_APP = "点击应用排行";
    static final String CLICK_RANK_GAME = "点击游戏排行";
    static final String CLICK_RANK_BOOK = "点击电子书排行";
    static final String CLICK_RANK_POP = "点击风向标排行";

    // 已下载软件管理点击次数
    static final String CLICK_FILE_MANAGER = "点击文件管理";
    // 应用管理界面，更新点击次数
    static final String CLICK_UPDATE = "点击更新";
    // 应用管理界面，卸载点击次数
    static final String CLICK_UNINSTALL = "点击卸载";
    // 应用管理界面，应用详情页点击次数
    static final String CLICK_DETAIL = "点击应用详情";
    // 详情页打开次数
    static final String OPEN_PRODUCT_DETAIL = "打开详情页";
    // 在详情页点击下载或更新次数
    static final String DETAIL_DOWNLOAD = "点击下载或更新";
    // 详情页介绍，“更多”点击次数
    static final String DETAIL_CLICK_MORE = "点击更多介绍";
    // 详情页，评论tab点击次数
    static final String DETAIL_CLICK_COMMENT = "查看评论";
    // 详情页，发起评论点击次数
    static final String DETAIL_POST_COMMENT = "发表评论";
    // 账号点击次数
    static final String MENU_CLICK_ACCOUNT = "点击用户中心";
    // 登录次数
    static final String LOGIN = "点击登录";
    // 注册次数
    static final String REGISTER = "点击注册";
    // 登录成功次数
    static final String LOGIN_SUCCESS = "登录成功";
    // 注册成功次数
    static final String REGISTER_SUCCESS = "注册成功";
    // 云推送开启次数
    static final String OPEN_PUSH = "开启云推送";
    // 云推送关闭次数
    static final String CLOSE_PUSH = "关闭云推送";

    // 反馈点击次数
    static final String MENU_CLICK_FEEDBACK = "打开反馈页";
    // 反馈发送次数
    static final String SEND_FEEDBACK = "发送反馈";
    // 设置点击次数
    static final String MENU_CLICK_SETTINGS = "打开设置";
    // 缓存清除次数
    static final String CLICK_CLEAR_CACHE = "清除缓存";
    // 搜索历史清除次数
    static final String CLICK_CLEAR_SEARCH_HISTORY = "清除搜索历史";
    

}
