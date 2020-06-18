package com.quickblox.sample.chat.java.ui.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.quickblox.sample.chat.java.R;
import com.quickblox.sample.chat.java.services.LoginService;
import com.quickblox.sample.chat.java.utils.SharedPrefsHelper;

public class SplashActivity1 extends BaseActivity1 {
    private static final int SPLASH_DELAY = 1500;

    private SharedPrefsHelper sharedPrefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);

        fillVersion();
        sharedPrefsHelper = SharedPrefsHelper.getInstance();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPrefsHelper.hasQbUser()) {
                    LoginService.start(SplashActivity1.this, sharedPrefsHelper.getQbUser());
                    OpponentsActivity.start(SplashActivity1.this);
                } else {
                    LoginActivity.start(SplashActivity1.this);
                }
                finish();
            }
        }, SPLASH_DELAY);
    }

    private void fillVersion() {
        String appName = getString(R.string.app_name);
        ((TextView) findViewById(R.id.text_splash_app_title)).setText(appName);
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            ((TextView) findViewById(R.id.text_splash_app_version)).setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            showErrorSnackbar(R.string.error, e, null);
        }
    }
}