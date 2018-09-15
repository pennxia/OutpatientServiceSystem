/*
 *
 *  MIT License
 *
 *  Copyright (c) 2017 Alibaba Group
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package com.nobitastudio.materialdesign.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.activity.MainActivity;
import com.nobitastudio.materialdesign.bean.HospitalActivity;
import com.nobitastudio.materialdesign.util.Utility;

import java.util.List;

/**
 * Created by nobita on 18/07/10.
 */
public class HospitalActivityUltraPagerAdapter extends PagerAdapter {
    boolean isMultiScr;
    MainActivity activity;
    List<HospitalActivity> hospitalActivityList;

    public HospitalActivityUltraPagerAdapter(boolean isMultiScr, MainActivity activity, List<HospitalActivity> hospitalActivityList) {
        this.isMultiScr = isMultiScr;
        this.activity = activity;
        this.hospitalActivityList = hospitalActivityList;
    }

    @Override
    public int getCount() {
        return hospitalActivityList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.ultrapager_child_imageview, null);
        if (hospitalActivityList.size() > 0){
            ImageView imageView = linearLayout.findViewById(R.id.ultrapager_child_imageview);
            HospitalActivity hospitalActivity = hospitalActivityList.get(position);
            //by glide to load pic to imageview  http://www.nobitastudio.cn/myweb/pictures/healthnews_icon_img/20180710202712.png
            Glide.with(activity.getApplicationContext()).load(Utility.getHospitalActivityIconAddress(hospitalActivity.getHospitalActivityIconName())).into(imageView);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //enterHospitalActivityDetailsActivity();
                    Utility.showToastShort(activity,hospitalActivityList.get(position).getUrl());
                }
            });
        }

        container.addView(linearLayout);
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}
