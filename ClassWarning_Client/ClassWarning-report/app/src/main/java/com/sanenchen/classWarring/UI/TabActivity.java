package com.sanenchen.classWarring.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.sanenchen.classWarring.R;

public class TabActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    public static Activity instance;
    int lastSelectedPosition = 0;
    private AllWarningActivity classManagerActivity;
    private ClassWarringUpLoad classWarringUpLoad;
    private MineActivity mineActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.inflateMenu(R.menu.bottom_nav_menu);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        BottomNavigationBar bottomNavigationBar = findViewById(R.id.bottom_navigation_bar_First);
        bottomNavigationBar
                .setTabSelectedListener(TabActivity.this)
                .setMode(BottomNavigationBar.MODE_DEFAULT)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setActiveColor(R.color.colorAccent) //选中颜色
                .setInActiveColor("#888888") //未选中颜色
                .setBarBackgroundColor(R.color.card_view_background);//导航栏背景色

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_home_black_24dp, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_cloud_upload_black_24dp, "上报"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_account_circle_black_24dp, "我的"))
                .setFirstSelectedPosition(lastSelectedPosition )
                .initialise(); //initialise 一定要放在 所有设置的最后一项

        setDefaultFragment();//设置默认导航栏

        instance = this;
    }

    /**
     * 设置默认导航栏
     */
    private void setDefaultFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        classManagerActivity = new AllWarningActivity();
        transaction.replace(R.id.frame, classManagerActivity);
        transaction.commit();
    }

    /**
     * 设置导航选中的事件
     */
    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = this.getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (classManagerActivity == null) {
                    classManagerActivity = new AllWarningActivity();
                }
                transaction.replace(R.id.frame, classManagerActivity);
                break;
            case 1:
                if (classWarringUpLoad == null) {
                    classWarringUpLoad = new ClassWarringUpLoad();
                }
                transaction.replace(R.id.frame, classWarringUpLoad);
                break;
            case 2:
                if (mineActivity == null) {
                    mineActivity = new MineActivity();
                }
                transaction.replace(R.id.frame, mineActivity);
                break;

            default:
                break;
        }
        transaction.commit();// 事务提交
    }

    /**
     * 设置未选中Fragment 事务
     */
    @Override
    public void onTabUnselected(int position) { }

    /**
     * 设置释放Fragment 事务
     */
    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Intent intent = new Intent(TabActivity.this, ClassWarningSearchActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}