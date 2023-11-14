package com.strangeoctopus.protocol;

import android.content.Context;

/**
 *  @author strangeOctopus
 * 每个模块继承此类 如accountApplication
 * 体现出此模块所提供的能力 实现模块间解耦
 */

public abstract class ProtocolApplication {

    abstract public void onCreate(Context context);
}
