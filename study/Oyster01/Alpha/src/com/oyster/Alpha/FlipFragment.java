package com.oyster.Alpha;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * @author bamboo
 * @since 3/5/14 6:52 PM
 */
public class FlipFragment extends Fragment {


    private ViewFlipper mFlipper;
    private TextView mTextView1, mTextView2;
    private int mCurrentLayoutState, mCount;
    private GestureDetector mGestureDetector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flip, container, false);

        mCurrentLayoutState = 0;
        mCount = 0;

        mFlipper = (ViewFlipper) v.findViewById(R.id.fliper);
        mTextView1 = (TextView) v.findViewById(R.id.textView1);
        mTextView2 = (TextView) v.findViewById(R.id.textView2);

        mTextView1.setText(String.valueOf(mCount));

        mGestureDetector = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                        if (velocityX < -10.0f) {
                            switchLayoutStateToRight();
                        } else if (velocityX > 10.0f) {
                            switchLayoutStateToLeft();
                        }

                        return true;
                    }
                });

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });

        return v;
    }

    private void switchLayoutStateToRight() {

        mCurrentLayoutState++;
        mCurrentLayoutState %= 2;

        mFlipper.setInAnimation(getAnimation(1.0f, 0.0f, -1.0f, 0.0f));
        mFlipper.setOutAnimation(getAnimation(0.0f, -1.0f, 0.0f, 1.0f));

        mCount++;
        (mCurrentLayoutState == 0 ? mTextView1 : mTextView2).setText(String.valueOf(mCount));
        mFlipper.showPrevious();
    }


    private void switchLayoutStateToLeft() {

        mCurrentLayoutState++;
        mCurrentLayoutState %= 2;

        mFlipper.setInAnimation(getAnimation(-1.0f, 0.0f, 1.0f, 0.0f));
        mFlipper.setOutAnimation(getAnimation(0.0f, 1.0f, 0.0f, -1.0f));

        mCount--;

        (mCurrentLayoutState == 0 ? mTextView1 : mTextView2).setText(String.valueOf(mCount));

        mFlipper.showPrevious();
    }

    private Animation getAnimation(float fromXValue, float toXValue,
                                   float fromYValue, float toYValue) {
        Animation outToLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, fromXValue,
                Animation.RELATIVE_TO_PARENT, toXValue,
                Animation.RELATIVE_TO_PARENT, fromYValue,
                Animation.RELATIVE_TO_PARENT, toYValue);

        outToLeft.setDuration(300);
        outToLeft.setInterpolator(new LinearInterpolator());

        return outToLeft;
    }

}