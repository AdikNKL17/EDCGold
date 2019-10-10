package id.dev.birifqa.edcgold.model.nav_drawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class MenuModel {
    public String menuName;
    public Drawable imageIcon;
    public boolean hasChildren, isGroup;

    public MenuModel(String menuName, boolean hasChildren, boolean isGroup) {
        this.menuName = menuName;
        this.hasChildren = hasChildren;
        this.isGroup = isGroup;
    }

    public MenuModel(String menuName, Drawable imageIcon, boolean hasChildren, boolean isGroup) {
        this.menuName = menuName;
        this.imageIcon = imageIcon;
        this.hasChildren = hasChildren;
        this.isGroup = isGroup;
    }
}
