package com.uhmtech.reader.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.uhmtech.reader.R;
import com.uhmtech.reader.adapter.RecommendAdapter;
import com.uhmtech.reader.base.BaseDataBindingActivity;
import com.uhmtech.reader.bean.RecommendList;
import com.uhmtech.reader.component.log.RsLogger;
import com.uhmtech.reader.component.log.RsLoggerManager;
import com.uhmtech.reader.databinding.ActivityRecommendBinding;
import com.uhmtech.reader.network.BaseSubscriber;
import com.uhmtech.reader.network.RestClientFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kiefer on 2017/9/19.
 */

public class RecommendActivity extends BaseDataBindingActivity<ActivityRecommendBinding> {
        private RecommendAdapter mBookAdapter;
        private LinearLayoutManager mLayoutManager;
        RsLogger logger = RsLoggerManager.getLogger();
        public static final String TAG = RecommendActivity.class.getSimpleName();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_recommend);
                showContentView();
                setTitle("推荐列表");

                initUi();
        }

        private void initUi(){
                mLayoutManager = new LinearLayoutManager(this);
                bindingView.xrvBook.setLayoutManager(mLayoutManager);
                bindingView.xrvBook.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                                loadCustomData();
                        }
                }, 500);

        }

        private void loadCustomData(){
                addSubscription(RestClientFactory.createApi()
                        .getRecommendApp()
                        .subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR))
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<RecommendList>() {
                                @Override
                                public void onSuccess(RecommendList listBean) {
                                        if (listBean != null && listBean.getList().size() > 0) {
                                                if (mBookAdapter == null) {
                                                        mBookAdapter = new RecommendAdapter(RecommendActivity.this);
                                                }
                                                mBookAdapter.addAll(listBean.getList());
                                                bindingView.xrvBook.setAdapter(mBookAdapter);
                                                mBookAdapter.notifyDataSetChanged();

                                        }
                                }

                                @Override
                                public void onError(Throwable e) {
                                        super.onError(e);
                                        logger.e(TAG, e.getMessage());
                                }

                                @Override
                                public void onFinally(Throwable e) {
                                        super.onFinally(e);
                                }
                        }));
        }


        public static void start(Context mContext) {
                Intent intent = new Intent(mContext, RecommendActivity.class);
                mContext.startActivity(intent);
        }
        @Override
        public void onResume() {
                super.onResume();
        }

        @Override
        public void onPause() {
                super.onPause();
        }
}

