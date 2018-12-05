package com.aliwh.android.quickyicha.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 定义Viewpager的滑动动作
com.cmsz.android.baymax.module
 * @author Brian
2015年10月26日 下午3:54:42
 */
public class FixedSpeedScroller extends Scroller {  
	   private int mDuration = 0;  
	   
	    public FixedSpeedScroller(Context context) {  
	        super(context);  
	    }  
	  
	    public FixedSpeedScroller(Context context, Interpolator interpolator) {  
	        super(context, interpolator);  
	    }  
	  
	    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {  
	        super(context, interpolator, flywheel);  
	    }  
	  
	    @Override  
	    public void startScroll(int startX, int startY, int dx, int dy, int duration) {  
	        super.startScroll(startX, startY, dx, dy, mDuration);  
	    }  
	  
	    @Override  
	    public void startScroll(int startX, int startY, int dx, int dy) {  
	        super.startScroll(startX, startY, dx, dy, mDuration);  
	    }  
}