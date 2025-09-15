package ch.migros.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.view.Menu;
import android.view.MenuItem;

public class cordova_plugin_mglmenu extends CordovaPlugin {

    private Menu menu;
    private List<MenuItemData> menuItems = new ArrayList<>();

    private static class MenuItemData {
        int id;
        String title;

        MenuItemData(int id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("createMenuItem".equals(action)) {
            JSONObject obj = args.getJSONObject(0);
            int id = obj.getInt("id");
            String title = obj.getString("title");
            menuItems.add(new MenuItemData(id, title));
            callbackContext.success("MenuItem added");
            return true;
        } else if ("showMenu".equals(action)) {
            cordova.getActivity().runOnUiThread(() -> {
                cordova.getActivity().invalidateOptionsMenu();
            });
            callbackContext.success("Menu shown");
            return true;
        }
        return false;
    }

    // Wird von MainActivity weitergeleitet
    public void onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        menu.clear(); // vorherige Items löschen

        for (MenuItemData itemData : menuItems) {
            MenuItem item = menu.add(Menu.NONE, itemData.id, Menu.NONE, itemData.title);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    // Wird von MainActivity weitergeleitet
    public boolean onOptionsItemSelected(MenuItem item) {
        for (MenuItemData menuItem : menuItems) {
            if (menuItem.id == item.getItemId()) {
                webView.loadUrl("javascript:cordova.fireDocumentEvent('pluginMenuClicked', {id:" + menuItem.id + "});");
                return true;
            }
        }
        return false;
    }
    
}
