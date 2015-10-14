package com.gu.rxjavastudydemo;

import android.os.Bundle;
import android.widget.ListView;

import com.gu.baselibrary.baseui.BaseActivity;
import com.gu.baselibrary.utils.LogUtils;
import com.gu.baselibrary.utils.NetUtils;
import com.gu.baselibrary.utils.SDCardUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

import butterknife.InjectView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
        File[] files = new File[2];
        files[0] = new File("/storage/sdcard0/相机/");
        files[1] = new File("/storage/sdcard0/截屏/");
        showToast("length==>" + files[0].listFiles().length);


//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onNext(String s) {
//                LogUtils.d(TAG_LOG, "Item: " + s);
//            }
//
//            @Override
//            public void onCompleted() {
//                LogUtils.d(TAG_LOG, "Completed!");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtils.d(TAG_LOG,"Error!");
//            }
//        };


        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String url) {
                LogUtils.d(TAG_LOG, "Item: " + url);
                imgs.add(url);
            }

            @Override
            public void onCompleted() {
                LogUtils.d(TAG_LOG, "Completed!");
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d(TAG_LOG, "Error!" + e.toString());
            }
        };

        // 方式1
        //        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        //            @Override
        //            public void call(Subscriber<? super String> subscriber) {
        //                subscriber.onNext("Hello");
        //                subscriber.onNext("Hi");
        //                subscriber.onNext("Aloha");
        //                subscriber.onCompleted();
        //            }
        //        });
        // 方式2
        //        Observable observable = Observable.just("Hello", "Hi", "Aloha");
        // 将会依次调用：
        // onNext("Hello");
        // onNext("Hi");
        // onNext("Aloha");
        // onCompleted();
        //方式3
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable observable = Observable.from(words);


//        observable.subscribe(subscriber);
        Observable.just(files[0])
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".jpg");
                    }
                })
                .map(new Func1<File, String>() {
                    @Override
                    public String call(File file) {
                        return "//storage/sdcard0/相机/" + file.getName();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String url) {
//                        LogUtils.d(TAG_LOG, "url==>" + url);
//                        imgs.add(url);
//                    }
//                });
                .subscribe(subscriber);
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
