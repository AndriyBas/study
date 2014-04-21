package com.oyster.Alpha;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author bamboo
 * @since 3/13/14 11:45 PM
 */
public class SensorFragment extends Fragment
        implements SensorEventListener {

    private static final int UPDATE_TRESHHOLD = 500;

    private TextView mTextViewSensorX;
    private TextView mTextViewSensorY;
    private TextView mTextViewSensorZ;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;

    private long mLastUpdate;


    private LinearLayout mFrame;

    // Storage for Sensor readings
    private float[] mGravity = null;
    private float[] mGeomagnetic = null;

    // Rotation around the Z axis
    private double mRotationInDegress;

    // View showing the compass arrow
    private CompassArrowView mCompassArrow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sensors, container, false);

        mFrame = (LinearLayout) v;

        mTextViewSensorX = (TextView) v.findViewById(R.id.textView_sensor_x);
        mTextViewSensorY = (TextView) v.findViewById(R.id.textView_sensor_y);
        mTextViewSensorZ = (TextView) v.findViewById(R.id.textView_sensor_z);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


        mCompassArrow = new CompassArrowView(getActivity());
        mFrame.addView(mCompassArrow);

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();

        mSensorManager.registerListener(
                this,
                mAccelerometer,
                SensorManager.SENSOR_DELAY_UI);


        mSensorManager.registerListener(
                this,
                mMagnetometer,
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long time = System.currentTimeMillis();

            if (time - mLastUpdate > UPDATE_TRESHHOLD) {
                mLastUpdate = time;

                mTextViewSensorX.setText("X : " + String.valueOf(event.values[0]));
                mTextViewSensorY.setText("Y : " + String.valueOf(event.values[1]));
                mTextViewSensorZ.setText("Z : " + String.valueOf(event.values[2]));
            }


            mGravity = new float[3];
            System.arraycopy(event.values, 0, mGravity, 0, 3);

        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = new float[3];
            System.arraycopy(event.values, 0, mGeomagnetic, 0, 3);
        }

        if (mGravity != null && mGeomagnetic != null) {

            float[] rotationMatrix = new float[9];


            boolean success = SensorManager.getRotationMatrix(rotationMatrix,
                    null, mGravity, mGeomagnetic);

            if (success) {

                float[] orientationMatrix = new float[3];

                SensorManager.getOrientation(rotationMatrix, orientationMatrix);

                float rotationInRadians = orientationMatrix[0];

                mRotationInDegress = Math.toDegrees(rotationInRadians);

                mCompassArrow.invalidate();

                mGravity = null;
                mGeomagnetic = null;
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public class CompassArrowView extends View {

        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.arrow);
        int mBitmapWidth = mBitmap.getWidth();

        // Height and Width of Main View
        int mParentWidth;
        int mParentHeight;

        // Center of Main View
        int mParentCenterX;
        int mParentCenterY;

        // Top left position of this View
        int mViewTopX;
        int mViewLeftY;

        public CompassArrowView(Context context) {
            super(context);
        }

        ;

        // Compute location of compass arrow
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            mParentWidth = mFrame.getWidth();
            mParentHeight = mFrame.getHeight();

            mParentCenterX = mParentWidth / 2;
            mParentCenterY = mParentHeight / 2;

            mViewLeftY = mParentCenterX - mBitmapWidth / 2;
            mViewTopX = mParentCenterY - mBitmapWidth / 2;
        }

        // Redraw the compass arrow
        @Override
        protected void onDraw(Canvas canvas) {

            // Save the canvas
            canvas.save();

            // Rotate this View
            canvas.rotate((float) -mRotationInDegress, mParentCenterX,
                    mParentCenterY);

            // Redraw this View
            canvas.drawBitmap(mBitmap, mViewLeftY, mViewTopX, null);

            // Restore the canvas
            canvas.restore();

        }
    }
}
