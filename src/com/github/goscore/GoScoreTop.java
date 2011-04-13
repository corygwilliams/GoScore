/*
 * Copyright (C) 2011 Cory Williams
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

package com.github.goscore;

import com.github.goscore.R;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

public class GoScoreTop extends Activity implements OnClickListener {
	private Camera mCamera;
	private Preview mPreview;
	private PreviewProcessor mProcessor;
	private CameraOverlay mOverlay;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        FrameLayout picFrameLayout = ((FrameLayout) findViewById(R.id.picFrameLayout));
        mPreview = new Preview(this);
        picFrameLayout.addView(mPreview, 0);
        
        mOverlay = new CameraOverlay(this);
        picFrameLayout.addView(mOverlay);
        
        mProcessor = new PreviewProcessor((CameraOverlay) mOverlay);
        findViewById(R.id.shutterButton).setOnClickListener(this);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
        mCamera = Camera.open();
        mPreview.setCamera(mCamera);
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	if (mCamera != null) {
    		mPreview.setCamera(null);
    		mCamera.release();
    		mCamera = null;
    	}
    }

	public void onClick(View v) {
		mPreview.setOneShotPreviewCallback(mProcessor);
	}
}