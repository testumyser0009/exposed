package com.fd.xposed.util;

/**
 * Created by dxx on 2017/11/8.
 */

public class ApiConstants {

//    public static final String GankHost = "https://gank.io/";
    /*测试环境*/
//    public static final String GankHost = "http://72.167.224.114:82/";
    public static final String GankHost = "http://aa.sviptb.com/";

    /*生产环境*/
//    public static final String GankHost = "http://60.167.239.115:8093/";
    /*开发环境*/
//    public static final String GankHost = "http://192.168.0.203:5002/";
//    public static final String GankHost = "http://139.217.232.49:8086/";
    // 高德地图地址解析接口
    // https://restapi.amap.com/v3/geocode/regeo?key=您的key&location=116.481488,39.990464&poitype=&radius=&extensions=all&batch=false&roadlevel=0
    public static final String GdHost = "https://restapi.amap.com/";

    // websocket 实时计费接口
    public static final String socketUrl = "wss://www.huisonglin.top/app/websocket/";
//    public static final String socketUrl = "ws://139.217.232.49:8086/app/websocket/";

    // 自动升级地址
    public static final String updateUrl = "https://www.huisonglin.top/app/update.xml";

}
