package com.flyersoft.moonreader.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;
import com.flyersoft.moonreader.R;
import com.flyersoft.moonreader.adapter.AlbumAdapter;
import com.flyersoft.moonreader.base.BaseDataBindingActivity;
import com.flyersoft.moonreader.base.Constants;
import com.flyersoft.moonreader.bean.AlbumsList;
import com.flyersoft.moonreader.component.log.RsLogger;
import com.flyersoft.moonreader.component.log.RsLoggerManager;
import com.flyersoft.moonreader.databinding.ActivityAlbumBinding;
import com.flyersoft.moonreader.network.BaseSubscriber;
import com.flyersoft.moonreader.network.RestClientFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kiefer on 2017/9/19.
 */

public class AlbumActivity extends BaseDataBindingActivity<ActivityAlbumBinding> {
        RsLogger logger = RsLoggerManager.getLogger();
        public static final String TAG = AlbumActivity.class.getSimpleName();
        private AlbumAdapter mAlbumAdapter;
        private GridLayoutManager mLayoutManager;

        BannerView bv;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_album);
                showContentView();

                setTitle("分类列表");
                initUi();
                initAd();
                bv.loadAD();
        }

        private void initAd() {
                bv = new BannerView(this, ADSize.BANNER, Constants.APPID, Constants.BannerPosID);
                // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
                // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
                bv.setRefresh(0);
                bv.setShowClose(true);
                bv.setADListener(new AbstractBannerADListener() {


                        @Override
                        public void onNoAD(AdError adError) {
                                Log.i("AD_DEMO", "BannerNoAD，eCode=" + adError.getErrorMsg());
                        }

                        @Override
                        public void onADReceiv() {
                                Log.i("AD_DEMO", "ONBannerReceive");
                        }
                });
                bindingView.rlReadBottom.addView(bv);
        }

        private void initUi() {
                mLayoutManager = new GridLayoutManager(this, 3);
                bindingView.xrvAblums.setLayoutManager(mLayoutManager);

                addSubscription(RestClientFactory.createApi()
                        .getAlbums()
                        .subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR))
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<AlbumsList>() {
                                @Override
                                public void onSuccess(AlbumsList listBean) {
                                        if (listBean !=null && listBean.getList().size() > 0) {
                                                if (mAlbumAdapter == null) {
                                                        mAlbumAdapter = new AlbumAdapter();
                                                }

                                                mAlbumAdapter.addAll(listBean.getList());
                                                mAlbumAdapter.notifyDataSetChanged();
                                                bindingView.xrvAblums.setAdapter(mAlbumAdapter);
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

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.activity_search, menu);
                return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();
                if (id == R.id.search) {
                        Intent intent = new Intent(AlbumActivity.this, BookSearchActivity.class);
                        startActivity(intent);
                }

                return super.onOptionsItemSelected(item);
        }

        public static void start(Context mContext) {
                Intent intent = new Intent(mContext, AlbumActivity.class);
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

