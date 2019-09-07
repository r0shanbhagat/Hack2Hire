package com.hack.easyhomeloan.base.navigation.fragments;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.base.BaseFragment;
import com.hack.easyhomeloan.base.BaseViewModel;
import com.hack.easyhomeloan.base.OnItemClickListener;
import com.hack.easyhomeloan.base.navigation.BaseNavigationDrawerActivity;
import com.hack.easyhomeloan.base.navigation.adapter.IMenuItemSelected;
import com.hack.easyhomeloan.base.navigation.adapter.NavigationDrawerMenuAdapter;
import com.hack.easyhomeloan.base.navigation.menubean.NavigationMenuModel;
import com.hack.easyhomeloan.databinding.NavigationDrawerMenuFragmentBinding;
import com.hack.easyhomeloan.utilities.AppUtils;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerMenuFragment extends BaseFragment implements IMenuItemSelected {
    public static final String TAG = NavigationDrawerMenuFragment.class.getSimpleName();
    private List<NavigationMenuModel> listDrawerMenu = new ArrayList<>();

    public void setData(List<NavigationMenuModel> listDrawerMenu) {
        this.listDrawerMenu = listDrawerMenu;
    }


    @Override
    public int getLayoutId() {
        return  R.layout.navigation_drawer_menu_fragment;
    }

    @Override
    public BaseViewModel getViewModel() {
        return null;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onViewCreated(ViewDataBinding viewDataBinding) {
        NavigationDrawerMenuFragmentBinding  navigationDrawerMenuBinding = (NavigationDrawerMenuFragmentBinding) viewDataBinding;
        final NavigationDrawerMenuAdapter adapter = new NavigationDrawerMenuAdapter(listDrawerMenu, false);
        AppUtils.setRecyclerBasicProperties(getContext(), navigationDrawerMenuBinding.rvNavMenu);
        navigationDrawerMenuBinding.rvNavMenu.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemViewClick(View view, Object viewModel) {
                NavigationMenuModel navSliderModel = (NavigationMenuModel) viewModel;


                if (AppUtils.isListNotEmpty(navSliderModel.getSubMenuList())) {
                    ((BaseNavigationDrawerActivity) getActivity()).setSubMenu(navSliderModel.getSubMenuList(), navSliderModel.getMenuName());
                } else {
                    ((BaseNavigationDrawerActivity) getActivity()).closeDrawerAndNavigateWithDelay(navSliderModel);
                }
            }
        });
    }

    @Override
    public void onMenuItemSelected(String menuSlug) {
        if (!menuSlug.isEmpty() && listDrawerMenu != null) {
            for (int position = 0; position < listDrawerMenu.size(); position++) {
                listDrawerMenu.get(position).setSelected(false);
                if (listDrawerMenu.get(position).getMenuName().equals(menuSlug)) {
                    listDrawerMenu.get(position).setSelected(true);

                }
            }
        }
    }

}
