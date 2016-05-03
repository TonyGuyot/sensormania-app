/*
 * Copyright (C) 2016 Tony Guyot
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
package io.github.tonyguyot.sensormania;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment containing the retrieved values for a given sensor.
 */
public class SensorValuesFragment extends Fragment
        implements SensorEventListener {

    /**
     * Maximum number of different sensor values supported
     */
    private static final int MAX_VALUES = 3;

    /**
     * The sensor manager is needed to register/unregister listeners
     */
    private SensorManager mSensorManager;

    /**
     * The delay used to retrieve sensor information
     */
    private int mDelay = SensorManager.SENSOR_DELAY_NORMAL;
    // TODO: make this value changeable by the user in a future version

    /**
     * The number of sensor acquired values displayed
     */
    private boolean mFirstTime = true;

    /*
     * The UI elements
     */
    private TextView mAccuracyTextView;
    private TextView mUpdateTextView;
    private TextView[] mLabelTextView = new TextView[MAX_VALUES];
    private TextView[] mValueTextView = new TextView[MAX_VALUES];

    /**
     * List of units used for display
     */
    private String[] mUnits;

    /**
     * Number of values received by the sensor that we support
     */
    private int mValuesCount;

    /**
     * Last timestamp received
     */
    private long mPreviousTimestamp = -1L;

    /**
     * Link to the sensor from the activity
     */
    private Sensor mSensor;

    public SensorValuesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the view
        View fragment = inflater.inflate(R.layout.fragment_sensor_values, container, false);

        // retrieve references to the UI elements
        mAccuracyTextView = (TextView) fragment.findViewById(R.id.value_accuracy);
        mUpdateTextView = (TextView) fragment.findViewById(R.id.last_update);
        mLabelTextView[0] = (TextView) fragment.findViewById(R.id.label_value1);
        mValueTextView[0] = (TextView) fragment.findViewById(R.id.value1);
        mLabelTextView[1] = (TextView) fragment.findViewById(R.id.label_value2);
        mValueTextView[1] = (TextView) fragment.findViewById(R.id.value2);
        mLabelTextView[2] = (TextView) fragment.findViewById(R.id.label_value3);
        mValueTextView[2] = (TextView) fragment.findViewById(R.id.value3);

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // retrieve the sensor manager
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        // retrieve the selected sensor
        mSensor = ((SensorActivity) getActivity()).getSensor();

        // check that environment is correct
        if (mSensor == null || getView() == null)
            // there is nothing we can do!
            return;

        // retrieve the number of values reported by this sensor
        int valueCount = SensorInfo.getValuesCount(mSensor.getType());

        // change the visibility of fields according to the number of values
        if (valueCount > 2) {
            // if there are at least 3 values to display,
            // then render the second and third fields visible
            // (they are invisible by default)
            View v = getView().findViewById(R.id.row2);
            if (v != null)
                v.setVisibility(View.VISIBLE);
            v = getView().findViewById(R.id.row3);
            if (v != null)
                v.setVisibility(View.VISIBLE);
        } else {
            // if there is only one value (the only other alternative)
            // then show the single value with a bigger font
            TextView tv = (TextView) getView().findViewById(R.id.value1);
            if (tv != null) {
                tv.setTextSize(getResources().getDimension(R.dimen.font_value_big));
            }
        }

        // set the value names
        String[] labels = SensorInfo.getNames(mSensor.getType());
        if (labels.length > 0)
            mLabelTextView[0].setText(labels[0]);
        if (labels.length > 1)
            mLabelTextView[1].setText(labels[1]);
        if (labels.length > 2)
            mLabelTextView[2].setText(labels[2]);

        // retrieve the unit names and number of supported values
        mValuesCount = SensorInfo.getValuesCount(mSensor.getType());
        mUnits = SensorInfo.getUnits(mSensor.getType());
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, mDelay);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        // check that this event is not out-of-schedule
        if (mPreviousTimestamp > sensorEvent.timestamp)
            return;

        // retrieve and format the values
        // & update the value fields
        String valueString;
        for (int i = 0; i < mValuesCount; i++) {
            valueString = String.format("%.2f %s", sensorEvent.values[i], mUnits[i]);
            mValueTextView[i].setText(valueString);
        }

        // update the 'last update' field
        setLastUpdateField(sensorEvent.timestamp);

        // set the accuracy field, if not already set
        if (mFirstTime) {
            setAccuracy(sensorEvent.accuracy);
            mFirstTime = false;
        }
    }

    /**
     * Set the 'last update' field with the date/time from the sensor event.
     * The timestamps provided by the different sensors have no clear definition
     * of a base time. The base time is dependent of the sensor model and is
     * different from manufacturer to manufacturer. Therefore only difference
     * of timestamps should be used.
     *
     * @param timestamp (in nanoseconds)
     */
    private void setLastUpdateField(long timestamp) {
        if (mPreviousTimestamp < 0L) {
            // this is the first time stamp received
            // => we can not yet compute the difference between timestamps
            mUpdateTextView.setText(R.string.last_update_first_value);
        } else {
            // compute the difference between current timestamp and
            // previous timestamp (in seconds, not nanoseconds)
            float diff = (timestamp - mPreviousTimestamp) / 1E9f;
            int id = (diff > 1.0f) ? R.string.last_update_value_pl : R.string.last_update_value_sg;
            mUpdateTextView.setText(getResources().getString(id, diff));
        }
        mPreviousTimestamp = timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        setAccuracy(i);
    }

    private void setAccuracy(int accuracy) {
        // update the accuracy field
        mAccuracyTextView.setText(UiUtil.getAccuracyTextId(accuracy));
    }
}
