package com.example.myapplication.base.navigation.menubean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NavigationDrawerMenu {

    @SerializedName("name")
    @Expose
    private String menuName;
    @SerializedName("slug")
    @Expose
    private String id;
    @SerializedName("iconUrl")
    @Expose
    private Object menuImageUrl;
    @SerializedName("url")
    @Expose
    private String navigationUrl;
    @SerializedName("subMenus")
    @Expose
    private List<NavigationDrawerMenu> navSubMenuList;
    @SerializedName("footerItems")
    @Expose
    private List<NavigationDrawerMenu> footerItems;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getMenuImageUrl() {
        return menuImageUrl;
    }

    public void setMenuImageUrl(Object menuImageUrl) {
        this.menuImageUrl = menuImageUrl;
    }

    public String getNavigationUrl() {
        return navigationUrl;
    }

    public void setNavigationUrl(String navigationUrl) {
        this.navigationUrl = navigationUrl;
    }

    public List<NavigationDrawerMenu> getNavSubMenuList() {
        return navSubMenuList;
    }

    public void setNavSubMenuList(List<NavigationDrawerMenu> navSubMenuList) {
        this.navSubMenuList = navSubMenuList;
    }

    public List<NavigationDrawerMenu> getFooterItems() {
        return footerItems;
    }

    public void setFooterItems(List<NavigationDrawerMenu> footerItems) {
        this.footerItems = footerItems;
    }
}
