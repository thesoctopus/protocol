package com.strangeoctopus.protocol.UseSample;

import com.strangeoctopus.protocol.ComponentRegister;
import com.strangeoctopus.protocol.FunctypeName;
import com.strangeoctopus.protocol.PackagetName;

public class ModeApi {
    /**
     * 调用其他模块的模块实现，此方法可以用于本模块调用
     * 从而调用其他模块方法
     * modeApi 和 Application在不同模块 实现调用application的能力。
     * 但application
     */
    public static  void  GetOthorComponet(String date){
        ComponentRegister.getInstance().getComponent(PackagetName.WifiHelper).call(PackagetName.Protocal, FunctypeName.GetTestData, date);
    }
}
