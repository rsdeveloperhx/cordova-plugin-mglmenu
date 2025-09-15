package ch.migros.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;

import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class cordova_plugin_mglmenu extends CordovaPlugin {

    private static CordovaWebView sWebView;
    private static List<String> menuItems = new ArrayList<>();

    @Override
    public void initialize(org.apache.cordova.CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        sWebView = webView;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("addMenuItem".equals(action)) {
            String item = args.getString(0);
            menuItems.add(item);
            cordova.getActivity().runOnUiThread(() -> {
                cordova.getActivity().invalidateOptionsMenu(); // refresh menu
            });
            callbackContext.success("Menu item added: " + item);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        for (String item : menuItems) {
            menu.add(item);
        }
        menu.add("Help");  // optional default items
        menu.add("About");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        Log.d("NativeMenuPlugin", "Menu selected: " + title);

        if (sWebView != null) {
            sWebView.loadUrl("javascript:window.NativeMenuPlugin.menuSelected('" + title + "');");
        }

        return true;
    }
}
