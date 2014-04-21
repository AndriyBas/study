package com.oyster.Alpha;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author bamboo
 * @since 3/3/14 11:30 PM
 */
public class AlphaFragment extends Fragment implements View.OnTouchListener {

    private static final int MIN_DXDY = 2;
    private static final int MAX_TOUCHES = 20;

    private static int mTouches = 0;

    private final static Random mRandom = new Random();

    private static final LinkedList<MarkerView> mInactiveMarkers =
            new LinkedList<MarkerView>();

    private static final HashMap<Integer, MarkerView> mActiveMarkers =
            new HashMap<Integer, MarkerView>();


    private FrameLayout mFrameLayout;

    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_alpha, container, false);

        initMarkers();

        mFrameLayout = (FrameLayout) v.findViewById(R.id.frameLayout);
        mFrameLayout.setOnTouchListener(this);

        mTextView = (TextView) v.findViewById(R.id.textViewCount);

        return v;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {

            // Show new MarkerView

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {

                int pointerIndex = event.getActionIndex();
                int pointerID = event.getPointerId(pointerIndex);

                MarkerView marker = mInactiveMarkers.remove();

                if (null != marker) {
                    mActiveMarkers.put(pointerID, marker);

                    marker.setX(event.getX(pointerIndex));
                    marker.setY(event.getY(pointerIndex));

                    updateCounts();

                    mFrameLayout.addView(marker);
                }
                break;
            }

            // Remove one MarkerView

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {

                int pointerIndex = event.getActionIndex();
                int pointerID = event.getPointerId(pointerIndex);

                MarkerView marker = mActiveMarkers.remove(pointerID);

                if (null != marker) {

                    mInactiveMarkers.add(marker);

                    updateCounts();

                    mFrameLayout.removeView(marker);
                }
                break;
            }


            // Move all currently active MarkerViews

            case MotionEvent.ACTION_MOVE: {

                for (int idx = 0; idx < event.getPointerCount(); idx++) {

                    int ID = event.getPointerId(idx);

                    MarkerView marker = mActiveMarkers.get(ID);
                    if (null != marker) {

                        // Redraw only if finger has travel ed a minimum distance
                        if (Math.abs(marker.getX() - event.getX(idx)) > MIN_DXDY
                                || Math.abs(marker.getY()
                                - event.getY(idx)) > MIN_DXDY) {

                            // Set new location

                            marker.setX(event.getX(idx));
                            marker.setY(event.getY(idx));

                            // Request re-draw
                            marker.invalidate();
                        }
                    }
                }

                break;
            }

            default:

                Log.i(getClass().getSimpleName(), "unhandled action");
        }

        return true;

    }

    private void updateCounts() {
        mTouches = mActiveMarkers.size();
        mTextView.setText("Touches : " + mTouches);
    }

    private void initMarkers() {
        for (int i = 0; i < MAX_TOUCHES; i++) {
            mInactiveMarkers.add(new MarkerView(getActivity(), -1.0f, -1.0f));
        }
    }


    private class MarkerView extends View {


        private float mX, mY;
        private final static int MAX_SIZE = 400;
        private final Paint mPaint = new Paint();

        public MarkerView(Context context, float x, float y) {
            super(context);

            mX = x;
            mY = y;

            mPaint.setStyle(Paint.Style.FILL);

            mPaint.setARGB(255, mRandom.nextInt(256),
                    mRandom.nextInt(256), mRandom.nextInt(256));
        }


        @Override
        public float getY() {
            return mY;
        }

        @Override
        public void setY(float y) {
            mY = y;
        }

        @Override
        public float getX() {
            return mX;
        }

        @Override
        public void setX(float x) {
            mX = x;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(mX, mY, MAX_SIZE / mTouches, mPaint);
        }
    }


}