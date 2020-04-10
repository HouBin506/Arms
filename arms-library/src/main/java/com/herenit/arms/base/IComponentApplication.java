package com.herenit.arms.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by HouBin
 * 2020/3/31 10:34
 * Describe: 组件化之Application接口，当主项目已经具有自定义的Application，
 * 导入的Module也具有自定义的Application，可尝试实现此类，在具体的方法中处理对应的代码即可
 */
public interface IComponentApplication {

    /**
     * 这里会在 onCreate 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    void attachBaseContext(Context base);

    void onCreate(Application application);

    /**
     * 在模拟环境中程序终止时会被调用
     */
    void onTerminate(Application application);

}
