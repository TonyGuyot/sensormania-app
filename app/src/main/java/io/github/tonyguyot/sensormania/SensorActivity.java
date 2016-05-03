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
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;


public class SensorActivity extends ActionBarActivity {

    /**
     * The key for the extra in the incoming intent.
     * It enables to retrieve the sensor position in the sensor list.
     */
    public final static String EXTRA_SENSOR_INDEX = "io.github.tonyguyot.sensormania.SENSOR_INDEX";

    /**
     * The sensor we want to display
     */
    private Sensor mSensor;

    public static Intent makeIntent(Context context, int position) {
        Intent intent = new Intent(context, SensorActivity.class);
        intent.putExtra(SensorActivity.EXTRA_SENSOR_INDEX, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        // retrieve the sensor
        mSensor = SensorUtil.getSensor(this, getIntent().getIntExtra(EXTRA_SENSOR_INDEX, 0));

        // set the title line + icon
        TextView title = (TextView) findViewById(R.id.tv_sensor_type);
        title.setText(UiUtil.getLongTextId(mSensor.getType()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            title.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    UiUtil.getIconId(mSensor.getType()), // start
                    0, 0, 0 // top, end, bottom -> no icon
            );
        } else {
            title.setCompoundDrawablesWithIntrinsicBounds(
                    UiUtil.getIconId(mSensor.getType()), // left
                    0, 0, 0 // top, right, bottom -> no icon
            );
        }

        // create the fragments
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_frame_sensor_values, new SensorValuesFragment())
                    .add(R.id.container_frame_sensor_info, new SensorInfoFragment())
                    .commit();
        }
    }

    public Sensor getSensor() {
        return mSensor;
    }
}
