package com.gu.rxjavastudydemo;

import android.content.Context;

import com.gu.baselibrary.baseadapter.BaseViewHolder;
import com.gu.baselibrary.baseadapter.MyBaseAdapter;

import java.util.List;

/**
 * Created by Nate on 2015/10/14.
 */
public class ImageListAdapter extends MyBaseAdapter<String> {

    public ImageListAdapter(Context context, int resource, List<String> list) {
        super(context, resource, list);
    }

    /**
     * @param viewHolder
     * @param s
     * @return void 返回类型
     * @Title: setConvert
     * @Description: 抽象方法，由子类去实现每个itme如何设置
     */
    @Override
    public void setConvert(BaseViewHolder viewHolder, String s) {
        viewHolder.setFileNormalImg(R.id.itemIV,s);
    }
}
