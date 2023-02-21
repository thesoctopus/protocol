package com.strangeoctopus.protocol;

import java.util.HashMap;
import java.util.Map;
/**
 * 组件注册器
 */
public class ComponentRegister {
	private static ComponentRegister instance = new ComponentRegister();

	//注册列表类 String为包 Protocol通用调用函数的接口的实例化对象
	private Map<String, Protocol> map = new HashMap<String, Protocol>();

	public static ComponentRegister getInstance() {
		return instance;
	}
	/**
	 * 根据类名字获取对应的模块实例化对象
	 * @param name 调用模块名字
	 * @return  对应模块接口实例化对象
	 */
	public synchronized Protocol getComponent(String name) {
		return map.get(name);
	}
	/**
	 * 通用调用函数
	 * //具体模块初始化完成后会调用该接口完成注册 一般在xxxApplication中调用
	 * @param name 模块名字
	 * @param p 对应模块接口实例化对象
	 */
	public synchronized void setComponent(String name, Protocol p) {
		if(map.containsKey(name)) {
			map.remove(name);
		}
		map.put(name, p);
	}
}
