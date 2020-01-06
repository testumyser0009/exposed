package com.wz.xposedalipay.util;

/**
 * Created by weizhi on 2017/1/18.
 */
public class Constant {

//    是否登陆
    public static final String login = "login";// 登陆
    /*FD App*/
    public static final String VIDEOTYPE = "video/";
    public static final String PICTYPE = "image/";


    public static final String Token = "Token";

    /* 账号 */
    public static final String LoginName = "LoginName";


    /*
    * 状态码
    * */
    public static final String HTTP_200 = "200";

    /*
    * 用户所属部门和部门ID
    * */
    public static final String DEPART = "DEPART";
    public static final String DEPARTID = "DEPARTID";
    public static final String DEPARTCODE = "DEPARTCODE";

    public static final String LOGINNAME = "LOGINNAME";
    public static final String DEPARTNAME = "DEPARTNAME";


    public static final String UserId = "UserId";
    public static final String UserName = "UserName";



    // 线索首页任务列表


    /*
    * 勘验点类型
    * */
    public static final String WATERPOLLUTION = "water_pollution"; // 水质污染
    public static final String SOLIDPOLLUTION = "soil_pollution"; // 土壤污染
    public static final String FOODDRUGPOLLUTION = "food_drug_safety"; // 食品药品安全
    public static final String GASPOLLUTION = "gas_pollution"; // 气体污染
    public static final String NOISEPOLLUTION = "noise_pollution"; // 噪音扰民
    public static final String LANDPOLLUTION = "land_violation"; // 国有土地使用违规


    public static final String KYDCOMMONDATA = "KYDCOMMONDATA"; // 勘验点常用数据

    /*勘验点*/
    public static final String ADDKYD = "ADDKYD"; // 新增勘验点
    public static final String EDITKYD = "EDITKYD"; // 编辑勘验点

    /*跳轉頁面*/
    public static final String JUMPTOHOME = "JUMPTOHOME"; // 跳轉都首頁
    public static final String JUMPTOREPORT = "JUMPTOREPORT"; // 跳轉到勘驗報告頁面
    public static final String JUMPTOMESSAGE = "JUMPTOMESSAGE"; // 跳轉到消息頁面

    public static final String HISTORYACCOUNTMSG = "HISTORYACCOUNTMSG"; // 历史用户信息

    /*
    * 线索录入界面 视频图片上传状态
    * */
    public static final String UPLOADLOADING = "UPLOADLOADING"; // 上传中
    public static final String UPLOADERROR = "UPLOADERROR"; // 上传失败
    public static final String UPLOADSUCCESS = "UPLOADSUCCESS"; // 上传成功

    /*
    * ftp文件服务器BaseUrl
    * */
    public static final String FTPBASEURL = "http://36.7.159.17:9099"; // 上传成功
//    public static final String FTPBASEURL = "http://60.167.239.115:8092/"; // 上传成功

    /*
    * 跳转到勘验点适配器的来源
    * */
    public static final String FROMCLUEDETAIL = "FROMCLUEDETAIL"; // 来源于详情
    public static final String FROMCLUEEDIT = "FROMCLUEEDIT"; // 来源于编辑页面


    public static final String KANYANDIANTAGPARAM = "KANYANDIANTAGPARAM"; // 勘验点tag数据

    /*
    * 跳转到任务详情页面的来源
    * */
    public static final String FromKyReport = "FromKyReport"; // 来自勘验报告
    public static final String FromClueList = "FromClueList"; // 来自线索列表

    /*
    * 新增设备页面跳转来源 新增或编辑 pageJumpAddType  pageJumpEditType
    * */
    public static final String addDevicePageJumpType = "addDevicePageJumpType";
    public static final String pageJumpAddType = "pageJumpAddType";
    public static final String pageJumpEditType = "pageJumpEditType";



    public static final String loginErrorTips = "用户名或密码错误";


    /*
    * 设备管理条件筛选
    * */
    public static final String DeviceManagerFilterAdd = "DeviceManagerFilterAdd";
    public static final String DeviceManagerFilterDelete = "DeviceManagerFilterDelete";

}
