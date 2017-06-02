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

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

/**
 * 权限监听
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/8/16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SampleBackThreadPermissionsListener implements MultiplePermissionsListener {
    @Override
    public void onPermissionsChecked(final MultiplePermissionsReport report) {
        if (listener == null) return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
                    listener.showPermissionGranted(response.getPermissionName());
                }

                for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
                    listener.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
                }

                if (report.areAllPermissionsGranted()) {
                    listener.showPermissionAllGranted(report.getGrantedPermissionResponses());
                }
            }
        });
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, final PermissionToken token) {
        if (listener == null) return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.showPermissionRationale(token);
            }
        });
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    private final OnPermissionsListener listener;

    public SampleBackThreadPermissionsListener(OnPermissionsListener listener) {
        this.listener = listener;
    }
}
