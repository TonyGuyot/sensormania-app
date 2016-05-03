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
import android.hardware.SensorManager;

import java.util.List;

/**
 * This static class groups several useful methods to deal with
 * sensor management.
 */
public class SensorUtil {

    /**
     * Check if there is at least one sensor available on the device.
     *
     * @param c     a context
     * @return      true if the sensor list reports at least one item.
     */
    public static boolean hasSensors(Context c) {
        return getSensorCount(c) > 0;
    }

    /**
     * Retrieve the list of all sensors available on the device.
     *
     * @param c     a context
     * @return      list of sensors or null
     */
    public static List<Sensor> getSensorList(Context c) {
        SensorManager sm = (c == null) ? null
                : (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
        return (sm == null) ? null : sm.getSensorList(Sensor.TYPE_ALL);
    }

    /**
     * Retrieve the number of sensors available on the device.
     *
     * @param c     a context
     * @return      the number of sensors
     */
    public static int getSensorCount(Context c) {
        List<Sensor> l = getSensorList(c);
        return (l == null) ? 0 : l.size();
    }

    /**
     * Retrieve a given sensor in the list of sensors.
     *
     * @param c     a context
     * @param pos   position of the sensor in the list
     * @return      the sensor at position 'pos' or null if position does not exist
     */
    public static Sensor getSensor(Context c, int pos) {
        List<Sensor> l = getSensorList(c);
        if (pos < 0 || pos >= l.size())
            return null;
        return l.get(pos);
    }
}
