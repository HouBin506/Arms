/*
 * Copyright 2017 herenitYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.herenit.arms.mvp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.herenit.arms.utils.ArmsUtils;

import static com.herenit.arms.utils.Preconditions.checkNotNull;

/**
 * ================================================
 * 框架要求框架中的每个 View 都需要实现此类, 以满足规范
 * <p>
 * 为了满足部分人的诉求以及向下兼容, {@link IView} 中的部分方法使用 JAVA 1.8 的默认方法实现, 这样实现类可以按实际需求选择是否实现某些方法
 * 不实现则使用默认方法中的逻辑, 不清楚默认方法的请自行学习
 *
 * @see <a href="https://github.com/herenitYanCoding/MVPArms/wiki#2.4.2">View wiki 官方文档</a>
 * Created by herenitYan on 4/22/2016
 * <a href="mailto:herenit.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/herenitYanCoding">Follow me</a>
 * ================================================
 */
public interface IView {

    /**
     * 显示加载
     */
    default void showLoading() {

    }

    /**
     * 隐藏加载
     */
    default void hideLoading() {

    }

    /**
     * 页面显示错误
     *
     * @param message
     */
    default void showError(@NonNull String message) {

    }

    /**
     * 页面显示错误
     *
     * @param title   标题
     * @param message 错误信息
     */
    default void showError(@NonNull String title, @Nullable String message) {

    }

    /**
     * 发出错误声音
     */
    default void showErrorVoice() {

    }

    /**
     * 发出错误声音
     */
    default void showSuccessVoice() {

    }


    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    default void showMessage(@NonNull String message) {

    }

    /**
     * 跳转 {@link Activity}
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    default void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    /**
     * 杀死自己
     */
    default void killMyself() {

    }

}
