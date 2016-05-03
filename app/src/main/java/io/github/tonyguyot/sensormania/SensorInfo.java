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
import android.os.Build;
import android.util.SparseArray;

/**
 * This static class gives information about the various sensors.
 */
@SuppressWarnings("deprecation")
public class SensorInfo {

    private static class Properties {
        // number of supported values
        // (may be different than the real number of values reported
        //  by the sensor)
        private int mCount;

        // names of values if any (empty string if no name)
        private String[] mNames;

        // names of units if any (empty string if no unit)
        private String[] mUnits;

        // indicates whether the sensor provides uncalibrated values
        private boolean mIsUncalibrated = false;

        // setters
        private Properties count(int count) {
            mCount = count;
            return this;
        }
        private Properties names(String... names) {
            mNames = new String[names.length];
            for (int i = 0; i < names.length; i++) {
                mNames[i] = names[i];
            }
            return this;
        }
        private Properties units(String... units) {
            mUnits = new String[units.length];
            for (int i = 0; i < units.length; i++) {
                mUnits[i] = units[i];
            }
            return this;
        }
        private Properties isUncalibrated(boolean isUncalibrated) {
            mIsUncalibrated = isUncalibrated;
            return this;
        }
    }

    private static Properties sDefaultProperties = new Properties();
    static {
        sDefaultProperties.count(1).names(" ").units(" ");
    }

    private static SparseArray<Properties> sInfos = new SparseArray<>(20);
    static {
        sInfos.put(Sensor.TYPE_ACCELEROMETER,
                new Properties().count(3)
                                .names("x =", "y =", "z =")
                                .units("m/s²", "m/s²", "m/s²"));

        sInfos.put(Sensor.TYPE_GRAVITY,
                new Properties().count(3)
                                .names("x =", "y =", "z =")
                                .units("m/s²", "m/s²", "m/s²"));

        sInfos.put(Sensor.TYPE_LINEAR_ACCELERATION,
                new Properties().count(3)
                                .names("x =", "y =", "z =")
                                .units("m/s²", "m/s²", "m/s²"));

        sInfos.put(Sensor.TYPE_MAGNETIC_FIELD,
                new Properties().count(3)
                                .names("x =", "y =", "z =")
                                .units("µT", "µT", "µT"));

        sInfos.put(Sensor.TYPE_GYROSCOPE,
                new Properties().count(3)
                                .names("x =", "y =", "z =")
                                .units("rad/s", "rad/s", "rad/s"));

        sInfos.put(Sensor.TYPE_LIGHT,
                new Properties().count(1)
                                .names(" ")
                                .units("lux"));

        sInfos.put(Sensor.TYPE_PRESSURE,
                new Properties().count(1)
                                .names(" ")
                                .units("hPa (millibar)"));

        sInfos.put(Sensor.TYPE_PROXIMITY,
                new Properties().count(1)
                                .names(" ")
                                .units("cm"));

        sInfos.put(Sensor.TYPE_ROTATION_VECTOR,
                new Properties().count(3)
                                .names("x*sin(θ) =", "y*sin(θ) =", "z*sin(θ) =")
                                .units(" ", " ", " "));

        // deprecated but still found on some devices
        sInfos.put(Sensor.TYPE_ORIENTATION,
                new Properties().count(3)
                        .names("azimuth =", "pitch =", "roll =")
                        .units("°", "°", "°"));

        sInfos.put(Sensor.TYPE_RELATIVE_HUMIDITY,
                new Properties().count(1)
                                .names("")
                                .units("%"));

        sInfos.put(Sensor.TYPE_AMBIENT_TEMPERATURE,
                new Properties().count(1)
                                .names("")
                                .units("°C"));

        // deprecated but may be found on some devices
        sInfos.put(Sensor.TYPE_TEMPERATURE,
                new Properties().count(1)
                        .names("")
                        .units("°C"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            sInfos.put(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED,
                    new Properties().count(3)
                                    .names("x =", "y =", "z =")
                                    .units("µT", "µT", "µT")
                                    .isUncalibrated(true));

            sInfos.put(Sensor.TYPE_GAME_ROTATION_VECTOR,
                    new Properties().count(3)
                                    .names("x*sin(θ) =", "y*sin(θ) =", "z*sin(θ) =")
                                    .units(" ", " ", " "));

            sInfos.put(Sensor.TYPE_GYROSCOPE_UNCALIBRATED,
                    new Properties().count(3)
                                    .names("x =", "y =", "z =")
                                    .units("rad/s", "rad/s", "rad/s")
                                    .isUncalibrated(true));
        }
    }

    public static String[] getNames(int sensorType) {
        return sInfos.get(sensorType, sDefaultProperties).mNames;
    }

    public static String[] getUnits(int sensorType) {
        return sInfos.get(sensorType, sDefaultProperties).mUnits;
    }

    public static int getValuesCount(int sensorType) {
        return sInfos.get(sensorType, sDefaultProperties).mCount;
    }

    public static boolean isUncalibrated(int sensorType) {
        return sInfos.get(sensorType, sDefaultProperties).mIsUncalibrated;
    }
}
