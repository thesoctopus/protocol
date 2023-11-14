##  一个Android项目模块化，跨模块调用的解决方法。
# 背景原因
随着模块越来越多，历史代码导致模块间耦合度增加，且项目暂时没有重构的计划，存在互相调用的情况，且模块间引用导致编译速度变慢。
# 解决思路
模块间取消引用，使用反射的方法找到所需的类，调用对应方法
# 实践

   1.各模块抽象出自身提供的能力（其他模块需要的能力）,并注册到注册表中  
   如：A模块提供`GetTestData` 和 `SetTestData`
   方法，则需继承`ProtocolApplication`类，实现对应方法：
   ```java
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
                    default:
                        break;
                }
                return obj;
            }
        };

        //2.注册监听，注册到监听表中
        //PluginClient中的Init方法会依次实现各模块的此方法
        ComponentRegister.getInstance().setComponent(PackagetName.WifiHelper, MyInterface);
    }
}
   ```

   2.在合适的地方（初始化时）初始化注册表。调用PluginClient.getInstance().init(json)方法。
   这一步是通过json文件（或其他形式，目的是通过反射找到对应的application），初始化所有的application，调用application的OnCreate初始化所有接口的能力，并注册到缓存中。

   3.至此，可以直接跨模块调用
   如：模块B需要调用上述模块的`GetTestData`方法，则需要这么做：
   ```java
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
   ```
先从缓存种获取到对应application对象，通过方法名直接调用到对应方法。

# 总结
总的来说，每个模块多实现两个类，一是自身的继承自application的类，用于体现自身提供给外部的能力，二是ModeAPI类，用于将使用到的其他模块的能力集合起来，增加部分范例代码，却可以实现模块间的解耦，同时如果严格遵循这种写法，也会在实现业务的时候，考虑模块间的耦合问题。

# 后续使用情况
发现了几个缺点：
1. 项目越来越大，竟然出现了在Application中实现业务的现象，需要严格杜绝，主要原因是人员流动，导致很多人对这种方法产生不解...
2. 由于需要将各模块引用放到缓存中，通过内存分析，发现占用了部分内存，但可以接受。


