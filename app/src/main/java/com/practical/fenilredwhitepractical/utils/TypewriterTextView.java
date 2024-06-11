package com.practical.fenilredwhitepractical.utils;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class TypewriterTextView extends AppCompatTextView {

    private CharSequence mText;
    private int mIndex;
    private long mDelay = 30; // Default 150ms delay

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));
            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public TypewriterTextView(Context context) {
        super(context);
    }

    public TypewriterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TypewriterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;
        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }
}