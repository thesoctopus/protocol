package com.strangeoctopus.protocol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.List;


/**
 * @author strangeOctopus
 * 加载Library Application加载
 */
public class PluginClient {
    private static PluginClient sInstance;
    private List<String> listConfig;

    private PluginClient() {
    }

    public static PluginClient getInstance() {
        if (sInstance == null) {
            synchronized (PluginClient.class) {
                if (sInstance == null) {
                    sInstance = new PluginClient();
                }
            }
        }
        return sInstance;
    }

    /**
     * 在app初始化的时候调用
     * 通过json加载所有模块
     * 调用所有application的oncreate方法
     * 将所有模块通过setComponent注册
     * 实现模块间通信
     *     json : <string name="component_list_json">[\"com.xx.xx.xx.xxxx\",\"com.xxx.xxx.xxx\"]"
     * @param json
     */
    public void init(Context context, String json) {
        readConfig(json);
        initPlugins(context);
    }

    /**
     * 读取所有的lib app
     */
    private void readConfig(String json) {
//        listConfig = GsonHelper.parseListToArraryList(json, String.class);
    }

    /**
     * 初始化lib app
     */
    private void initPlugins(Context context) {
        for (String bean : listConfig) {
            try {
                //获取类名字 完整路径
                Class aClass = Class.forName(bean);
                //按照类aClass.newInstance()实例化对象，强制转换为基类
                ProtocolApplication application = (ProtocolApplication) aClass.newInstance();
                //用基类进行初始化创建
                application.onCreate(context);
            } catch (Exception e) {
                //如果没有加载该模块或者主进程没有加载对应module则会实例化失败
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转页面
     */
    public void startActivity(Context context, String pluginID, String activityName, String json) {
        Intent intent = new Intent();
        try {
            intent.putExtra("Args", json);
            Class cls = Class.forName(activityName);
            intent.setClass(context, cls);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转页面 用于跨模块启动activity等
     */
    public void startActivityForResult(Activity context, String pluginID, String activityName, String json, int requestCode) {
        Intent intent = new Intent();
        try {
            Class cls = Class.forName(activityName);
            intent.setClass(context, cls);
            intent.putExtra("Args", json);
            context.startActivityForResult(intent, requestCode);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取意图 用于跨模块启动activity等
     *
     *
     *   页面跳转
     *   Intent intent = PluginClient.getInstance().getActivityIntent(this, "thisPackage", ActivityName.Home_MainActivity);
     *   startActivity(intent);
     *
     *
     */
    public Intent getActivityIntent(Activity context, String pluginID, String activityName) {
        Intent intent = new Intent();
        try {
            Class cls = Class.forName(activityName);
            intent.setClass(context, cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return intent;
    }

    /**
     * 获取意图
     */
    public Intent getActivityIntent(Context context, String activityName) {
        Intent intent = new Intent();
        try {
            Class cls = Class.forName(activityName);
            intent.setClass(context, cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return intent;
    }
}
