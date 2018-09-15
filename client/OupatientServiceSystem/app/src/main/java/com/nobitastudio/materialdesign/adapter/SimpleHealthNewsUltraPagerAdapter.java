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
import com.nobitastudio.materialdesign.activity.HealthNewsActivity;
import com.nobitastudio.materialdesign.activity.MainActivity;
import com.nobitastudio.materialdesign.bean.SimpleHealthNews;
import com.nobitastudio.materialdesign.util.Utility;

import java.util.List;

/**
 * Created by nobita on 18/07/10.
 */
public class SimpleHealthNewsUltraPagerAdapter extends PagerAdapter {
    boolean isMultiScr;
    HealthNewsActivity activity;
    List<SimpleHealthNews> simpleHealthNewsList;

    public SimpleHealthNewsUltraPagerAdapter(boolean isMultiScr, HealthNewsActivity activity, List<SimpleHealthNews> simpleHealthNewsList) {
        this.isMultiScr = isMultiScr;
        this.activity = activity;
        this.simpleHealthNewsList = simpleHealthNewsList;
    }

    @Override
    public int getCount() {
        return simpleHealthNewsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.ultrapager_child_imageview, null);
        if (simpleHealthNewsList.size() > 0) {
            ImageView imageView = linearLayout.findViewById(R.id.ultrapager_child_imageview);
            SimpleHealthNews simpleHealthNews = simpleHealthNewsList.get(position);
            Glide.with(activity.getApplicationContext()).load(Utility.getSimpleHealthNewsIconAddress(simpleHealthNews.getSimpleHealthNewsIconName())).into(imageView);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //enterSimpleHealthNewsDetailsActivity();
                    Utility.showToastShort(activity,simpleHealthNewsList.get(position).getUrl());
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
