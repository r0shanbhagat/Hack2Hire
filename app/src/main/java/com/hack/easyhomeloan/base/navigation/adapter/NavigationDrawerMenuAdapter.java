package com.hack.easyhomeloan.base.navigation.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.base.BaseRecyclerAdapter;
import com.hack.easyhomeloan.base.OnItemClickListener;
import com.hack.easyhomeloan.base.navigation.menubean.NavigationMenuModel;
import com.hack.easyhomeloan.databinding.NavigationDrawerDividerBinding;
import com.hack.easyhomeloan.databinding.NavigationDrawerFooterBinding;
import com.hack.easyhomeloan.databinding.NavigationDrawerItemBinding;
import com.hack.easyhomeloan.utilities.AppUtils;

import java.util.List;


public class NavigationDrawerMenuAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private final int TYPE_MENU = 0;
    private final int TYPE_DIVIDER = 1;
    private final int TYPE_FOOTER = 2;
    private List<NavigationMenuModel> listNavigationDrawerMenu;
    private OnItemClickListener onItemClickListener;
    private boolean isFromSubMenu;


    public NavigationDrawerMenuAdapter(List<NavigationMenuModel> listNavigationDrawerMenu, boolean isFromSubMenu) {
        super(listNavigationDrawerMenu);
        this.listNavigationDrawerMenu = listNavigationDrawerMenu;
        this.isFromSubMenu = isFromSubMenu;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_MENU) {
            NavigationDrawerItemBinding navigationDrawerItemBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.navigation_drawer_item, viewGroup, false);
            return new NavigationDrawerViewHolder(navigationDrawerItemBinding, onItemClickListener);
        } else if (viewType == TYPE_FOOTER) {
            NavigationDrawerFooterBinding navigationDrawerItemBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.navigation_drawer_footer, viewGroup, false);
            return new NavigationDrawerFooterHolder(navigationDrawerItemBinding, onItemClickListener);
        } else {
            NavigationDrawerDividerBinding navigationDrawerDividerBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.navigation_drawer_divider, viewGroup, false);
            return new NavigationDrawerDividerHolder(navigationDrawerDividerBinding);
        }
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder viewHolder, Object itemVal) {
        if (viewHolder.getItemViewType() == TYPE_DIVIDER) {
            NavigationDrawerDividerHolder drawerDividerHolder = (NavigationDrawerDividerHolder) viewHolder;
            drawerDividerHolder.bindView((NavigationMenuModel) itemVal);
        } else if (viewHolder.getItemViewType() == TYPE_FOOTER) {
            NavigationDrawerFooterHolder navigationDrawerFooterHolder = (NavigationDrawerFooterHolder) viewHolder;
            navigationDrawerFooterHolder.bindView((NavigationMenuModel) itemVal);
        } else {
            NavigationDrawerViewHolder drawerViewHolder = (NavigationDrawerViewHolder) viewHolder;
            drawerViewHolder.bindView((NavigationMenuModel) itemVal);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (listNavigationDrawerMenu.get(position).getMenuId().equalsIgnoreCase("divider")) {
            return TYPE_DIVIDER;
        } else if (listNavigationDrawerMenu.get(position).getMenuId().equalsIgnoreCase("footer")) {
            return TYPE_FOOTER;
        } else {
            return TYPE_MENU;
        }
    }


    class NavigationDrawerViewHolder extends RecyclerView.ViewHolder {
        private NavigationDrawerItemBinding viewDataBinding;
        private NavigationMenuModel navigationMenuModel;

        public NavigationDrawerViewHolder(NavigationDrawerItemBinding viewDataBinding, final OnItemClickListener onItemClickListener) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
            viewDataBinding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null == onItemClickListener) {
                        AppUtils.showLog("Holder", "Trying to work on a null object ,returning.");
                        return;
                    }
                    onItemClickListener.onItemViewClick(v, navigationMenuModel);
                }
            });

        }

        public void bindView(NavigationMenuModel navigationMenuModel) {
            if (navigationMenuModel == null) {
                AppUtils.showLog("Holder", "Trying to work on a null object ,returning.");
                return;
            }
            this.navigationMenuModel = navigationMenuModel;
            viewDataBinding.setViewModel(navigationMenuModel);
            if (isFromSubMenu) {
                viewDataBinding.imageViewMenuIcon.setVisibility(View.GONE);
            } else {
                viewDataBinding.imageViewMenuIcon.setVisibility(View.VISIBLE);
            }
        }

    }

    class NavigationDrawerDividerHolder extends RecyclerView.ViewHolder {
        private NavigationDrawerDividerBinding viewDataBinding;

        public NavigationDrawerDividerHolder(NavigationDrawerDividerBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
            viewDataBinding.executePendingBindings();

        }

        public void bindView(NavigationMenuModel NavigationMenuModel) {
            if (NavigationMenuModel == null) {
                AppUtils.showLog("Holder", "Trying to work on a null object ,returning.");
                return;
            }
            if (!TextUtils.isEmpty(NavigationMenuModel.getMenuName())) {
                viewDataBinding.textViewMenusHeading.setVisibility(View.VISIBLE);
                viewDataBinding.textViewMenusHeading.setText(NavigationMenuModel.getMenuName());
            } else {
                viewDataBinding.textViewMenusHeading.setVisibility(View.GONE);
            }

        }
    }


    class NavigationDrawerFooterHolder extends RecyclerView.ViewHolder {
        private NavigationDrawerFooterBinding viewDataBinding;
        private OnItemClickListener onItemClickListener;

        public NavigationDrawerFooterHolder(NavigationDrawerFooterBinding viewDataBinding, final OnItemClickListener onItemClickListener) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
            this.onItemClickListener = onItemClickListener;
            viewDataBinding.executePendingBindings();

        }

        public void bindView(NavigationMenuModel navigationMenuModel) {
            if (AppUtils.isListNotEmpty(navigationMenuModel.getFooterItems())) {
                List<NavigationMenuModel> footerItems = navigationMenuModel.getFooterItems();
                viewDataBinding.linearLayoutFooterContainer.removeAllViews();
                for (NavigationMenuModel footerItem : footerItems) {
                    View layout = LayoutInflater.from(viewDataBinding.layoutFooter.getContext())
                            .inflate(R.layout.nav_footer_item, viewDataBinding.linearLayoutFooterContainer, false);
                    TextView textViewItem = layout.findViewById(R.id.textViewItem);
                    ImageView imageViewItem = layout.findViewById(R.id.imageViewItem);
                    textViewItem.setText(footerItem.getMenuName());
                 //   AppUtils.loadImage(viewDataBinding.layoutFooter.getContext(), R.drawable.ic_share_white, imageViewItem);
                    viewDataBinding.linearLayoutFooterContainer.addView(layout);
                   /// layout.setTag(R.string.navigation_url, footerItem);
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (onItemClickListener != null) {
                      //          onItemClickListener.onItemViewClick(view, view.getTag(R.string.navigation_url), 0);
                            }
                        }
                    });
                }
            } else {
                viewDataBinding.layoutFooter.setVisibility(View.GONE);
            }
        }
    }
}
