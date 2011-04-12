package com.github.goscore;

import com.github.goscore.R;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class GoScoreTop extends Activity {
	protected Camera mCamera;
	protected Preview mPreview;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        mPreview = new Preview(this);
        ((FrameLayout) findViewById(R.id.picFrameLayout)).addView(mPreview, 0);
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
}