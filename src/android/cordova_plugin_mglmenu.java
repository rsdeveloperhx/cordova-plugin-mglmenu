package ch.migros.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class cordova_plugin_mglmenu extends CordovaPlugin {
    private static cordova_plugin_mglmenu instance;
    private Menu menu;
    private List<MenuItemData> menuItems = new ArrayList<>();

    public cordova_plugin_mglmenu() {
        instance = this;
    }

    public static cordova_plugin_mglmenu getInstance() {
        return instance;
    }

    public void addMenuItem(int id, String title) {
        menuItems.add(new MenuItemData(id, title));
        if (menu != null) {
            cordova.getActivity().runOnUiThread(() -> {
                cordova.getActivity().invalidateOptionsMenu();
            });
        }
    }

    public void onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        menu.clear();
        for (MenuItemData itemData : menuItems) {
            MenuItem item = menu.add(Menu.NONE, itemData.id, Menu.NONE, itemData.title);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        for (MenuItemData data : menuItems) {
            if (data.id == item.getItemId()) {
                webView.loadUrl("javascript:cordova.fireDocumentEvent('pluginMenuClicked',{id:" + data.id + "});");
                return true;
            }
        }
        return false;
    }

    private static class MenuItemData {
        int id;
        String title;
        MenuItemData(int id, String title) { this.id = id; this.title = title; }
    }
}
