package com.strangeoctopus.protocol;

/**
 *
 */
public interface Protocol {
	/**
	 * 通用调用函数
	 * @param pluginName 调用模块名字
	 * @param functype 调用函数名字
	 * @param arg 调用函数参数
	 * @return  返回数据
	 */
	Object call(String pluginName,String functype,Object... arg);
}
