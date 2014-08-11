package com.kince.andevui.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3DAnimation extends Animation {
	// 3d rotate
	private float mFromDegrees;
	private float mToDegrees;
	private float mCenterX;
	private float mCenterY;
	
	private Camera mCamera;
	
    public Rotate3DAnimation(float fromDegrees, float toDegrees) 
    {
    	mFromDegrees = fromDegrees;
    	mToDegrees = toDegrees;
    }

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight)
	{
		super.initialize(width, height, parentWidth, parentHeight);
		mCenterX = width / 2;
		Log.i("mCenterX", mCenterX+"");
		mCenterY = height / 2;
		Log.i("mCenterY", mCenterY+"");
		mCamera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t)
	{
		
		final float fromDegrees = mFromDegrees;
		float degrees = fromDegrees + (mToDegrees - mFromDegrees) * interpolatedTime;
		Log.i("degrees", degrees+"");
		final Matrix matrix = t.getMatrix();
		
		mCamera.save();
		
		mCamera.rotateY(degrees);
		mCamera.getMatrix(matrix);
		mCamera.restore();
		//
		matrix.preTranslate(-mCenterX, -mCenterY);
		matrix.postTranslate(mCenterX, mCenterY);
	}
}
