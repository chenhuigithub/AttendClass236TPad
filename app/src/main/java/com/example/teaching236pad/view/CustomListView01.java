package com.example.teaching236pad.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * @toTODO 类描述： 解决 ListView中嵌套ScrollView，ScrollView拦截ListView的Item点击事件的解决办法
 *         在listview中嵌套ScrollView
 *         ，发现横滑竖滑都正常，但是无法单击Listview的Item。查询Android分发机制后解决，
 *         继承Listview重写Listview的onInterceptTouchEvent。
 *         onInterceptTouchEvent中总是调用listview的onTouchEvent保证listview的事件都执行，
 *         super.onInterceptTouchEvent(ev)不会拦截需要传递给ScrollView的横滑。
 * @author chenhui摘自博客 2018.10.11
 * 
 */
public class CustomListView01 extends ListView {
	private int flag = 0;

	private float StartX;

	private float StartY;

	public CustomListView01(Context context) {
		super(context);
	}

	public CustomListView01(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomListView01(Context context, AttributeSet attrs,
                            int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@SuppressLint("NewApi")
	public CustomListView01(Context context, AttributeSet attrs,
                            int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 总是调用listview的touch事件处理
		onTouchEvent(ev);
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			StartX = ev.getX();
			StartY = ev.getY();
			return false;
		}
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			float ScollX = ev.getX() - StartX;
			float ScollY = ev.getY() - StartY;
			// 判断是横滑还是竖滑，竖滑的话拦截move事件和up事件（不拦截会由于listview和scrollview同时执行滑动卡顿）
			if (Math.abs(ScollX) < Math.abs(ScollY)) {
				flag = 1;
				return true;
			}
			return false;
		}
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			if (flag == 1) {

				return true;
			}
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}

}
