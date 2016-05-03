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
import android.hardware.SensorManager;
import android.os.Build;
import android.util.SparseIntArray;

/**
 * Some utility functions for the user interface.
 * This class gives some matching between sensor elements and UI resources.
 */
@SuppressWarnings("deprecation")
public class UiUtil {

    // retrieve the long name text id from the sensor type
    public static int getLongTextId(int sensorType) {
        return sTextMap.get(sensorType, sUnknownTextId);
    }

    // retrieve the short name text id from the sensor type
    public static int getLabelId(int sensorType) {
        return sLabelMap.get(sensorType, sUnknownTextId);
    }

    // retrieve the description text id from the sensor type
    public static int getDescrId(int sensorType) {
        return sDescrMap.get(sensorType, R.string.descr_no_info);
    }

    // retrieve the icon id from the sensor type
    public static int getIconId(int sensorType) {
        return sIconMap.get(sensorType, sUnknownIconId);
    }

    // retrieve the text id from the accuracy type
    public static int getAccuracyTextId(int accuracy) {
        return sAccuracyMap.get(accuracy, sUnknownAccuracy);
    }

    // references to texts for the accuracies
    private final static int sUnknownAccuracy = R.string.accuracy_unknown;
    private final static SparseIntArray sAccuracyMap = new SparseIntArray(5);
    static {
        sAccuracyMap.put(SensorManager.SENSOR_STATUS_ACCURACY_LOW, R.string.accuracy_low);
        sAccuracyMap.put(SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM, R.string.accuracy_medium);
        sAccuracyMap.put(SensorManager.SENSOR_STATUS_ACCURACY_HIGH, R.string.accuracy_high);
        sAccuracyMap.put(SensorManager.SENSOR_STATUS_UNRELIABLE, R.string.accuracy_unreliable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            sAccuracyMap.put(SensorManager.SENSOR_STATUS_NO_CONTACT, R.string.accuracy_no_contact);
        }
    }

    // references to the icons & texts
    private final static int sUnknownIconId = R.mipmap.ic_help;
    private final static int sUnknownTextId = R.string.type_unknown;
    private final static SparseIntArray sIconMap = new SparseIntArray(20);
    private final static SparseIntArray sTextMap = new SparseIntArray(20);
    private final static SparseIntArray sLabelMap = new SparseIntArray(20);
    private final static SparseIntArray sDescrMap = new SparseIntArray(20);

    static {
        // sensors supported since version 4.0 (API 14) or before
        sIconMap.put(Sensor.TYPE_ACCELEROMETER, R.mipmap.ic_rocket);
        sTextMap.put(Sensor.TYPE_ACCELEROMETER, R.string.type_accelerometer);
        sLabelMap.put(Sensor.TYPE_ACCELEROMETER, R.string.label_accelerometer);
        sDescrMap.put(Sensor.TYPE_ACCELEROMETER, R.string.descr_accelerometer);

        sIconMap.put(Sensor.TYPE_AMBIENT_TEMPERATURE, R.mipmap.ic_temperature);
        sTextMap.put(Sensor.TYPE_AMBIENT_TEMPERATURE, R.string.type_ambient_temperature);
        sLabelMap.put(Sensor.TYPE_AMBIENT_TEMPERATURE, R.string.label_ambient_temperature);
        sDescrMap.put(Sensor.TYPE_AMBIENT_TEMPERATURE, R.string.descr_ambient_temperature);

        // TEMPERATURE is deprecated and has been replaced by AMBIENT_TEMPERATURE
        // but we may still encounter it
        sIconMap.put(Sensor.TYPE_TEMPERATURE, R.mipmap.ic_temperature);
        sTextMap.put(Sensor.TYPE_TEMPERATURE, R.string.type_ambient_temperature);
        sLabelMap.put(Sensor.TYPE_TEMPERATURE, R.string.label_ambient_temperature);
        sDescrMap.put(Sensor.TYPE_TEMPERATURE, R.string.descr_ambient_temperature);

        sIconMap.put(Sensor.TYPE_GRAVITY, R.mipmap.ic_arrow_bottom);
        sTextMap.put(Sensor.TYPE_GRAVITY, R.string.type_gravity);
        sLabelMap.put(Sensor.TYPE_GRAVITY, R.string.label_gravity);
        sDescrMap.put(Sensor.TYPE_GRAVITY, R.string.descr_gravity);

        sIconMap.put(Sensor.TYPE_GYROSCOPE, R.mipmap.ic_turn_left);
        sTextMap.put(Sensor.TYPE_GYROSCOPE, R.string.type_gyroscope);
        sLabelMap.put(Sensor.TYPE_GYROSCOPE, R.string.label_gyroscope);
        sDescrMap.put(Sensor.TYPE_GYROSCOPE, R.string.descr_gyroscope);

        sIconMap.put(Sensor.TYPE_LIGHT, R.mipmap.ic_bulb);
        sTextMap.put(Sensor.TYPE_LIGHT, R.string.type_light);
        sLabelMap.put(Sensor.TYPE_LIGHT, R.string.label_light);
        sDescrMap.put(Sensor.TYPE_LIGHT, R.string.descr_light);

        sIconMap.put(Sensor.TYPE_LINEAR_ACCELERATION, R.mipmap.ic_rocket);
        sTextMap.put(Sensor.TYPE_LINEAR_ACCELERATION, R.string.type_linear_acceleration);
        sLabelMap.put(Sensor.TYPE_LINEAR_ACCELERATION, R.string.label_linear_acceleration);
        sDescrMap.put(Sensor.TYPE_LINEAR_ACCELERATION, R.string.descr_linear_acceleration);

        sIconMap.put(Sensor.TYPE_MAGNETIC_FIELD, R.mipmap.ic_magnet);
        sTextMap.put(Sensor.TYPE_MAGNETIC_FIELD, R.string.type_magnetic_field);
        sLabelMap.put(Sensor.TYPE_MAGNETIC_FIELD, R.string.label_magnetic_field);
        sDescrMap.put(Sensor.TYPE_MAGNETIC_FIELD, R.string.descr_magnetic_field);

        sIconMap.put(Sensor.TYPE_PRESSURE, R.mipmap.ic_balloon);
        sTextMap.put(Sensor.TYPE_PRESSURE, R.string.type_pressure);
        sLabelMap.put(Sensor.TYPE_PRESSURE, R.string.label_pressure);
        sDescrMap.put(Sensor.TYPE_PRESSURE, R.string.descr_pressure);

        sIconMap.put(Sensor.TYPE_PROXIMITY, R.mipmap.ic_circles);
        sTextMap.put(Sensor.TYPE_PROXIMITY, R.string.type_proximity);
        sLabelMap.put(Sensor.TYPE_PROXIMITY, R.string.label_proximity);
        sDescrMap.put(Sensor.TYPE_PROXIMITY, R.string.descr_proximity);

        sIconMap.put(Sensor.TYPE_RELATIVE_HUMIDITY, R.mipmap.ic_blob);
        sTextMap.put(Sensor.TYPE_RELATIVE_HUMIDITY, R.string.type_relative_humidity);
        sLabelMap.put(Sensor.TYPE_RELATIVE_HUMIDITY, R.string.label_relative_humidity);
        sDescrMap.put(Sensor.TYPE_RELATIVE_HUMIDITY, R.string.descr_relative_humidity);

        sIconMap.put(Sensor.TYPE_ROTATION_VECTOR, R.mipmap.ic_turn_left);
        sTextMap.put(Sensor.TYPE_ROTATION_VECTOR, R.string.type_rotation_vector);
        sLabelMap.put(Sensor.TYPE_ROTATION_VECTOR, R.string.label_rotation_vector);
        sDescrMap.put(Sensor.TYPE_ROTATION_VECTOR, R.string.descr_rotation_vector);

        // ORIENTATION is deprecated but it is still found on some devices
        sIconMap.put(Sensor.TYPE_ORIENTATION, R.mipmap.ic_turn_left);
        sTextMap.put(Sensor.TYPE_ORIENTATION, R.string.type_orientation);
        sLabelMap.put(Sensor.TYPE_ORIENTATION, R.string.label_orientation);
        sDescrMap.put(Sensor.TYPE_ORIENTATION, R.string.descr_orientation);

        // sensors supported since version 4.3 (API 18)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            sIconMap.put(Sensor.TYPE_GAME_ROTATION_VECTOR, R.mipmap.ic_turn_left);
            sTextMap.put(Sensor.TYPE_GAME_ROTATION_VECTOR, R.string.type_game_rotation_vector);
            sLabelMap.put(Sensor.TYPE_GAME_ROTATION_VECTOR, R.string.label_game_rotation_vector);
            sDescrMap.put(Sensor.TYPE_GAME_ROTATION_VECTOR, R.string.descr_game_rotation_vector);

            sIconMap.put(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, R.mipmap.ic_turn_left);
            sTextMap.put(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, R.string.type_gyroscope_uncalibrated);
            sLabelMap.put(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, R.string.label_gyroscope_uncalibrated);
            sDescrMap.put(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, R.string.descr_gyroscope_uncalibrated);

            sIconMap.put(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, R.mipmap.ic_magnet);
            sTextMap.put(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, R.string.type_magnetic_field_uncalibrated);
            sLabelMap.put(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, R.string.label_magnetic_field_uncalibrated);
            sDescrMap.put(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, R.string.descr_magnetic_field_uncalibrated);

            sIconMap.put(Sensor.TYPE_SIGNIFICANT_MOTION, R.mipmap.ic_location_2);
            sTextMap.put(Sensor.TYPE_SIGNIFICANT_MOTION, R.string.type_significant_motion);
            sLabelMap.put(Sensor.TYPE_SIGNIFICANT_MOTION, R.string.label_significant_motion);
            sDescrMap.put(Sensor.TYPE_SIGNIFICANT_MOTION, R.string.descr_significant_motion);
        }

        // sensors supported since version 4.4 (API 19)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sIconMap.put(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, R.mipmap.ic_turn_left);
            sTextMap.put(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, R.string.type_geomagnetic_rotation_vector);
            sLabelMap.put(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, R.string.label_geomagnetic_rotation_vector);
            sDescrMap.put(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, R.string.descr_geomagnetic_rotation_vector);

            sIconMap.put(Sensor.TYPE_STEP_COUNTER, R.mipmap.ic_location_2);
            sTextMap.put(Sensor.TYPE_STEP_COUNTER, R.string.type_step_counter);
            sLabelMap.put(Sensor.TYPE_STEP_COUNTER, R.string.label_step_counter);
            sDescrMap.put(Sensor.TYPE_STEP_COUNTER, R.string.descr_step_counter);

            sIconMap.put(Sensor.TYPE_STEP_DETECTOR, R.mipmap.ic_location_2);
            sTextMap.put(Sensor.TYPE_STEP_DETECTOR, R.string.type_step_detector);
            sLabelMap.put(Sensor.TYPE_STEP_DETECTOR, R.string.label_step_detector);
            sDescrMap.put(Sensor.TYPE_STEP_DETECTOR, R.string.descr_step_detector);
        }

        // sensors supported since version 4.4W (API 20)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            sIconMap.put(Sensor.TYPE_HEART_RATE, R.mipmap.ic_heart);
            sTextMap.put(Sensor.TYPE_HEART_RATE, R.string.type_heart_rate);
            sLabelMap.put(Sensor.TYPE_HEART_RATE, R.string.label_heart_rate);
            sDescrMap.put(Sensor.TYPE_HEART_RATE, R.string.descr_heart_rate);
        }
    }
}
