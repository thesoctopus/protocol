package com.strangeoctopus.protocol.UseSample;

import android.content.Context;

import com.strangeoctopus.protocol.ComponentRegister;
import com.strangeoctopus.protocol.FunctypeName;
import com.strangeoctopus.protocol.PackagetName;
import com.strangeoctopus.protocol.Protocol;
import com.strangeoctopus.protocol.ProtocolApplication;

/**
 * 实现了业务的模块继承
 * 其他模块通过pluginName调用本模块方法
 */
public class mineApplication extends ProtocolApplication {
    @Override
    public void onCreate(Context context) {
        //1.实现接口 暴露模块提供的能力
        Protocol MyInterface = new Protocol() {
            @Override
            public Object call(String pluginName, String functype, Object... arg) {
                Object obj = null;
                switch (functype) {
                    case FunctypeName.GetTestData: {
//                        obj =  MyAbility.getInstance().GetTestDate();
                        break;
                    }
                    case FunctypeName.SetTestData: {
//                        MyAbility.getInstance().setTestDate((String) arg[0]);
                        int a;
                        break;
                    }
                }
                return obj;
            }
        };

        //2.注册监听，注册到监听表中
        //PluginClient中的Init方法会依次实现各模块的此方法
        ComponentRegister.getInstance().setComponent(PackagetName.WifiHelper, MyInterface);
    }
}

