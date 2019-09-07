package com.hack.easyhomeloan.base.navigation.menubean;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.hack.easyhomeloan.utilities.AppUtils;

import java.util.List;


public class NavigationMenuModel extends BaseObservable implements Parcelable {


    public static final Creator<NavigationMenuModel> CREATOR = new Creator<NavigationMenuModel>() {
        @Override
        public NavigationMenuModel createFromParcel(Parcel source) {
            return new NavigationMenuModel(source);
        }

        @Override
        public NavigationMenuModel[] newArray(int size) {
            return new NavigationMenuModel[size];
        }
    };
    private Object menuImageUrl;
    private String menuName;
    private String navigationUrl;
    private String menuId;
    private boolean isSelected;
    private List<NavigationMenuModel> subMenuList;
    private List<NavigationMenuModel> footerItems;

    public NavigationMenuModel() {

    }

    protected NavigationMenuModel(Parcel in) {
        this.menuImageUrl = in.readValue(null);
        this.menuName = in.readString();
        this.navigationUrl = in.readString();
        this.menuId = in.readString();
    }

    @BindingAdapter(value = {"imageUrl", "placeholder"}, requireAll = false)
    public static void loadImage(ImageView imageView, Object imageUrl, Drawable placeHolder) {
        if (placeHolder == null) {
            AppUtils.loadImage(imageView.getContext(), imageUrl, imageView);
        } else {
           // AppUtils.loadImage(imageView.getContext(), imageUrl, imageView, placeHolder);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(menuImageUrl);
        dest.writeString(this.menuName);
        dest.writeString(this.navigationUrl);
        dest.writeString(this.menuId);
    }

    public Object getMenuImageUrl() {
        return menuImageUrl;
    }

    public void setMenuImageUrl(Object menuImageUrl) {
        this.menuImageUrl = menuImageUrl;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getNavigationUrl() {
        return navigationUrl;
    }

    public void setNavigationUrl(String navigationUrl) {
        this.navigationUrl = navigationUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<NavigationMenuModel> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<NavigationMenuModel> subMenuList) {
        this.subMenuList = subMenuList;
    }

    public List<NavigationMenuModel> getFooterItems() {
        return footerItems;
    }

    public void setFooterItems(List<NavigationMenuModel> footerItems) {
        this.footerItems = footerItems;
    }


}
