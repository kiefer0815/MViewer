package com.uhmtech.reader.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.uhmtech.reader.R;
import com.uhmtech.reader.base.BaseDataBindingActivity;
import com.uhmtech.reader.databinding.ActivityWelfareBinding;
import com.uhmtech.reader.fragment.WelfareFragment;

/**
 * Created by kiefer on 2017/11/2.
 */

public class WelfareActivity extends BaseDataBindingActivity<ActivityWelfareBinding> {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_welfare);
                showContentView();

                setTitle("福利");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.welfare_container, new WelfareFragment()).commit();
        }

        public static void start(Context mContext) {
                Intent intent = new Intent(mContext, WelfareActivity.class);
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
