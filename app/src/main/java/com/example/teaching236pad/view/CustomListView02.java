package com.example.teaching236pad.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class CustomListView02 extends GridView {
    public CustomListView02(Context context) {
        super(context);
    }

    public CustomListView02(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView02(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
