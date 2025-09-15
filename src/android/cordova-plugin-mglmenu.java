package ch.migros.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.view.Menu;
import android.view.MenuItem;

public class cordova_plugin_mglmenu extends CordovaPlugin {

    private Menu menu; 

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("showMenu".equals(action)) {
            cordova.getActivity().runOnUiThread(() -> {
                cordova.getActivity().invalidateOptionsMenu();
            });
            callbackContext.success("Menu updated");
            return true;
        }
        return false;
    }
    
}
