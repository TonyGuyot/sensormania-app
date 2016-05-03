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

import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment containing the general information about a given sensor.
 */
public class SensorInfoFragment extends Fragment {

    public SensorInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_sensor_info, container, false);

        // retrieve the sensor
        Sensor sensor = ((SensorActivity) getActivity()).getSensor(); // XXX to be changed

        // set the different information fields
        if (sensor != null) {
            // set the description text
            TextView tvDescr = (TextView) fragment.findViewById(R.id.info_description);
            tvDescr.setText(UiUtil.getDescrId(sensor.getType()));

            // set the name
            TextView tvName = (TextView) fragment.findViewById(R.id.info_name);
            tvName.setText(sensor.getName());

            // set the vendor
            TextView tvVendor = (TextView) fragment.findViewById(R.id.info_vendor);
            tvVendor.setText(sensor.getVendor());

            // set the version
            TextView tvVersion = (TextView) fragment.findViewById(R.id.info_version);
            tvVersion.setText(String.valueOf(sensor.getVersion()));

            // set the power
            TextView tvPower = (TextView) fragment.findViewById(R.id.info_power);
            tvPower.setText(sensor.getPower()+" "+getResources().getString(R.string.power_unit));

            // set the resolution
            TextView tvReso = (TextView) fragment.findViewById(R.id.info_resolution);
            tvReso.setText(String.valueOf(sensor.getResolution()));

            // set the max range
            TextView tvType = (TextView) fragment.findViewById(R.id.info_max_range);
            tvType.setText(String.valueOf(sensor.getMaximumRange()));
        }

        return fragment;
    }
}
