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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

/**
 * A fragment containing the list of all available sensors (= table of contents)
 * displayed as a grid of icons. Each icon represents a particular sensor.
 */
public class ToCFragment extends Fragment {

    public ToCFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_toc, container, false);

        // set the text view (= show number of sensors)
        TextView textview = (TextView) fragment.findViewById(R.id.tv_count);
        int count = SensorUtil.getSensorCount(getActivity());
        int id = (count > 1) ? R.string.nb_items_pl : R.string.nb_items_sg;
        textview.setText(getResources().getString(id, count));

        // set the grid view (= list of all sensors as icons)
        GridView gridview = (GridView) fragment.findViewById(R.id.gridview);
        gridview.setAdapter(new SensorGridAdapter(getActivity(),
                SensorUtil.getSensorList(getActivity())));

        // set the listener for the grid view
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = SensorActivity.makeIntent(getActivity(), position);
                startActivity(intent);
            }
        });

        return fragment;
    }

    // inner class: adapter for the sensor grid view

    public static class SensorGridAdapter extends BaseAdapter {
        private Context mContext;
        private List<Sensor> mData;

        public SensorGridAdapter(Context c, List<Sensor> l) {
            mContext = c;
            mData = l;
        }

        public int getCount() {
            return mData.size();
        }

        public Object getItem(int position) {
            return mData.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new TextView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
            } else {
                textView = (TextView) convertView;
            }

            textView.setText(UiUtil.getLabelId(mData.get(position).getType()));

            // OLD CODE
            /*
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setImageResource(UiUtil.getIconId(mData.get(position).getType()));
            */

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, // start
                        UiUtil.getIconId(mData.get(position).getType()), // top
                        0, // end
                        0  // bottom
                );
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(
                        0, // left
                        UiUtil.getIconId(mData.get(position).getType()), // top
                        0, // right
                        0  // bottom
                );
            }
            return textView;
        }
    }
}
