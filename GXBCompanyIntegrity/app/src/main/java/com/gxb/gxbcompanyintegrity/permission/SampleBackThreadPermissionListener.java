/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gxb.gxbcompanyintegrity.permission;

import android.os.Handler;
import android.os.Looper;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * 权限监听
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/8/16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SampleBackThreadPermissionListener implements PermissionListener {

    private Handler handler = new Handler(Looper.getMainLooper());
    private final OnPermissionListener listener;

    public SampleBackThreadPermissionListener(OnPermissionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onPermissionGranted(final PermissionGrantedResponse response) {
        if(listener==null)return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.showPermissionGranted(response.getPermissionName());
            }
        });
    }

    @Override
    public void onPermissionDenied(final PermissionDeniedResponse response) {
        if(listener==null)return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
            }
        });
    }

    @Override
    public void onPermissionRationaleShouldBeShown(final PermissionRequest permission,
                                                   final PermissionToken token) {
        if(listener==null)return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.showPermissionRationale(token);
            }
        });
    }
}
