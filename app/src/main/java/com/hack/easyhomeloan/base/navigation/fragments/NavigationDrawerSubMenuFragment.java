package com.hack.easyhomeloan.base.navigation.fragments;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.base.BaseFragment;
import com.hack.easyhomeloan.base.BaseViewModel;
import com.hack.easyhomeloan.base.OnItemClickListener;
import com.hack.easyhomeloan.base.navigation.BaseNavigationDrawerActivity;
import com.hack.easyhomeloan.base.navigation.adapter.NavigationDrawerMenuAdapter;
import com.hack.easyhomeloan.base.navigation.menubean.NavigationMenuModel;
import com.hack.easyhomeloan.databinding.NavigationDrawerSubmenuFragmentBinding;
import com.hack.easyhomeloan.utilities.AppUtils;

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
