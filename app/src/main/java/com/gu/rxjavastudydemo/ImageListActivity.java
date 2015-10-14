package com.gu.rxjavastudydemo;

import android.os.Bundle;
import android.widget.ListView;

import com.gu.baselibrary.baseui.BaseActivity;
import com.gu.baselibrary.utils.NetUtils;
import com.gu.baselibrary.utils.SDCardUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Nate on 2015/10/14.
 */
public class ImageListActivity extends BaseActivity {

    @InjectView(R.id.imageLV)
    ListView mListView;

    private ImageListAdapter mAdapter;

    private List<String> imgs = new ArrayList<>();

    /**
     * 绑定布局文件
     *
     * @return id of layout resource
     */
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_list_activity_layout;
    }

    /**
     * 是否开启应用的全屏展示
     *
     * @return
     */
    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    /**
     * 是否绑定了EventBus
     *
     * @return
     */
    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    /**
     * 处理Bundle传参
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    /**
     * @return true--自定义页面的切换动画   false--不自定义
     */
    @Override
    protected boolean isCustomPendingTransition() {
        return false;
    }

    /**
     * @return 返回自定义的动画切换方式
     */
    @Override
    protected TransitionMode getCustomPendingTransitionType() {
        return null;
    }

    /**
     * 初始化所有布局和event事件
     */
    @Override
    protected void initViewsAndEvents() {
        mAdapter = new ImageListAdapter(this, R.layout.image_list_item_layout, imgs);
        mListView.setAdapter(mAdapter);

        //写一个方法获取所有的图片路径
        getAllImageFiles();
    }

    private void getAllImageFiles() {

    }

    /**
     * 网络连接连起来了
     *
     * @param type
     */
    @Override
    protected void doOnNetworkConnected(NetUtils.NetType type) {

    }

    /**
     * 网络连接断开
     */
    @Override
    protected void doOnNetworkDisConnected() {

    }
}
