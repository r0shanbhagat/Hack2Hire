package com.hack.easyhomeloan.base.navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.login.LoginActivity;
import com.hack.easyhomeloan.base.BaseActivity;
import com.hack.easyhomeloan.base.navigation.fragments.NavigationDrawerMenuFragment;
import com.hack.easyhomeloan.base.navigation.fragments.NavigationDrawerSubMenuFragment;
import com.hack.easyhomeloan.base.navigation.menubean.NavigationDrawerMenu;
import com.hack.easyhomeloan.base.navigation.menubean.NavigationMenuModel;
import com.hack.easyhomeloan.utilities.AppUtils;
import com.hack.easyhomeloan.utilities.Navigator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseNavigationDrawerActivity extends BaseActivity {
    public static String TAG = BaseNavigationDrawerActivity.class.getSimpleName();
    public Toolbar toolbar;
    protected SmoothActionBarDrawerToggle mDrawerToggle;
    protected LinearLayout navigationViewDrawer;
    protected DrawerLayout mDrawerLayout;
    protected boolean flag_temp = false;
    protected long back_pressed;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    @Override
    protected void onActivityReady() {
        setupNavDrawer();
        setMenuData();
    }

    private void setupNavDrawer() {
        mDrawerLayout = findViewById(R.id.drawerLayout);
        if (null != toolbar) {
            mDrawerToggle = new SmoothActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mDrawerLayout.addDrawerListener(mDrawerToggle);
            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!popOrCloseDrawer()) {
                            openDrawer();
                        }
                    }
                });
            }
            mDrawerToggle.syncState();
        }
    }


    protected void openDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void setMenuData() {
        DisposableObserver<List<NavigationMenuModel>> response = new DisposableObserver<List<NavigationMenuModel>>() {
            @Override
            public void onNext(List<NavigationMenuModel> navigationMenuModels) {
                setDataInFragment(navigationMenuModels);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

        };
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response);
    }

    private Observable<List<NavigationMenuModel>> getObservable() {
        return Observable.just(true)
                .map(List -> {
                    List<NavigationMenuModel> navigationMenuModelList = new ArrayList<>();
                    Type listType = new TypeToken<ArrayList<NavigationDrawerMenu>>() {
                    }.getType();
                    String menuJson = AppUtils.loadJSONFromAsset(this, "navSlider.json");
                    if (!TextUtils.isEmpty(menuJson)) {
                        List<NavigationDrawerMenu> navSliderModelList =getList(menuJson, listType);
                        if (AppUtils.isListNotEmpty(navSliderModelList)) {
                            for (NavigationDrawerMenu baseSliderDataModel : navSliderModelList) {
                                navigationMenuModelList.add(getHomeMenuItemMapper(baseSliderDataModel));
                            }
                        }
                    }
                    return navigationMenuModelList;
                });
    }


    private  NavigationMenuModel getHomeMenuItemMapper(NavigationDrawerMenu navigationDrawerMenu) {
        NavigationMenuModel navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setMenuName(navigationDrawerMenu.getMenuName());
        navigationMenuModel.setMenuImageUrl(navigationDrawerMenu.getMenuImageUrl());
        navigationMenuModel.setNavigationUrl(navigationDrawerMenu.getNavigationUrl());
        navigationMenuModel.setMenuId(navigationDrawerMenu.getId());
        if (AppUtils.isListNotEmpty(navigationDrawerMenu.getNavSubMenuList())) {
            List<NavigationMenuModel> subMenuList = new ArrayList<>();
            for (NavigationDrawerMenu subMenuModel : navigationDrawerMenu.getNavSubMenuList()) {
                subMenuList.add(getHomeMenuItemMapper(subMenuModel));
            }
            navigationMenuModel.setSubMenuList(subMenuList);
        }
        if (AppUtils.isListNotEmpty(navigationDrawerMenu.getFooterItems())) {
            List<NavigationMenuModel> footerItemList = new ArrayList<>();
            for (NavigationDrawerMenu footerItemModel : navigationDrawerMenu.getFooterItems()) {
                footerItemList.add(getHomeMenuItemMapper(footerItemModel));
            }
            navigationMenuModel.setFooterItems(footerItemList);
        }
        return navigationMenuModel;
    }


    private <T>List<T> getList(String json, Type founderListType) {
        if (null == json) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, founderListType);
    }


    public void initToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_drawer);
        }
    }

    protected Toolbar getActionBarToolbar() {
        if (toolbar == null) {
            toolbar = findViewById(R.id.toolbar_actionbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                initToolBar();
            }
        }
        return toolbar;
    }

    private void setDataInFragment(List<NavigationMenuModel> navSliderList) {
        NavigationDrawerMenuFragment mainMenuFragment = new NavigationDrawerMenuFragment();
        mainMenuFragment.setData(navSliderList);
        addMenu(mainMenuFragment);
    }

    private void addMenu(final NavigationDrawerMenuFragment mainMenuFragment) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.navigationViewDrawer, mainMenuFragment, NavigationDrawerMenuFragment.TAG);
                if (!isFinishing()) {
                    ft.commitAllowingStateLoss();
                }
            }
        });
    }

    private void addSubMenu(NavigationDrawerSubMenuFragment subMenuFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.nav_right_in, R.anim.nav_left_out, R.anim.nav_left_in, R.anim.nav_right_out);
        ft.replace(R.id.navigationViewDrawer, subMenuFragment, NavigationDrawerSubMenuFragment.TAG);
        ft.addToBackStack(NavigationDrawerSubMenuFragment.TAG);
        ft.commitAllowingStateLoss();
    }

    public void setSubMenu(List<NavigationMenuModel> navSliderSubMenuList, String menuTitle) {
        NavigationDrawerSubMenuFragment subMenuFragment = new NavigationDrawerSubMenuFragment();
        subMenuFragment.setData(navSliderSubMenuList, menuTitle);
        addSubMenu(subMenuFragment);
    }


    protected boolean isNavDrawerOpen() {
        return (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START));
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    protected boolean popOrCloseDrawer() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
            return true;
        }
        return false;
    }

    protected boolean isClosedNavDrawer() {
        if (isNavDrawerOpen()) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.navigationViewDrawer);
            if (fragment instanceof NavigationDrawerSubMenuFragment) {
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                closeNavDrawer();
            }
            return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (!isClosedNavDrawer()) {
            if (getSupportFragmentManager() != null && !getSupportFragmentManager().popBackStackImmediate()) {
                super.onBackPressed();
            } else {
                supportFinishAfterTransition();
            }
        }

    }

    public void closeDrawerAndNavigateWithDelay(final NavigationMenuModel navigationMenuModel) {
        closeNavDrawer();
        if (TextUtils.isEmpty(navigationMenuModel.getNavigationUrl())) {
            return;
        }
        mDrawerToggle.runWhenIdle(new Runnable() {
            @Override
            public void run() {
                if (navigationMenuModel.getNavigationUrl().equals("hackhire://logout")) {
                    Intent loginIntent = new Intent(BaseNavigationDrawerActivity.this, LoginActivity.class);
                    Navigator.launchActivityWithFinishAll(BaseNavigationDrawerActivity.this, loginIntent);
                }
             //   handle click here
               // intent.setData(Uri.parse(navigationMenuModel.getNavigationUrl()));

            }
        });
    }


    public class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {

        private Runnable runnable;

        public SmoothActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes,
                                           int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
            hideKeyboard();
            supportInvalidateOptionsMenu();
        }

        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            invalidateOptionsMenu();
            supportInvalidateOptionsMenu();
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
            if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
                runnable.run();
                runnable = null;
            }
        }

        public void runWhenIdle(Runnable runnable) {
            this.runnable = runnable;
        }
    }
}
