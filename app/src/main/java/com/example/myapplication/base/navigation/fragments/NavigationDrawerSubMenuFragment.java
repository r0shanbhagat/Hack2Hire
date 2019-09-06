package com.example.myapplication.base.navigation.fragments;

import android.view.View;


import androidx.databinding.ViewDataBinding;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.base.BaseViewModel;
import com.example.myapplication.base.OnItemClickListener;
import com.example.myapplication.base.navigation.BaseNavigationDrawerActivity;
import com.example.myapplication.base.navigation.adapter.NavigationDrawerMenuAdapter;
import com.example.myapplication.base.navigation.menubean.NavigationMenuModel;
import com.example.myapplication.databinding.NavigationDrawerSubmenuFragmentBinding;
import com.example.myapplication.utilities.AppUtils;

import java.util.List;

public class NavigationDrawerSubMenuFragment extends BaseFragment {
    public static final String TAG = NavigationDrawerSubMenuFragment.class.getSimpleName();
    private final String SCREEN_NAME = "";
    private String menuTitle;
    private List<NavigationMenuModel> listNavigationSubMenu;

    public void setData(List<NavigationMenuModel> listNavigationSubMenu, String menuTitle) {
        this.listNavigationSubMenu = listNavigationSubMenu;
        this.menuTitle = menuTitle;
    }



    @Override
    protected void onViewCreated(ViewDataBinding viewDataBinding) {
        NavigationDrawerSubmenuFragmentBinding navDrawerSubmenuBinding = (NavigationDrawerSubmenuFragmentBinding) viewDataBinding;
        navDrawerSubmenuBinding.textViewHeading.setText(menuTitle);
        NavigationDrawerMenuAdapter adapter = new NavigationDrawerMenuAdapter(listNavigationSubMenu, true);
        AppUtils.setRecyclerBasicProperties(getContext(), navDrawerSubmenuBinding.rvSubMenu);
        navDrawerSubmenuBinding.rvSubMenu.setAdapter(adapter);
        navDrawerSubmenuBinding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemViewClick(View view, Object viewModel) {
                NavigationMenuModel navigationMenuModel = (NavigationMenuModel) viewModel;
                ((BaseNavigationDrawerActivity) getActivity()).closeDrawerAndNavigateWithDelay(navigationMenuModel);


            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.navigation_drawer_submenu_fragment;
    }

    @Override
    public BaseViewModel getViewModel() {
        return null;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }


}
