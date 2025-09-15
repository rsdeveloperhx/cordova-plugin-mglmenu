var exec = require('cordova/exec');

var cordova_plugin_mglmenu = {
    // Ein Menüitem erstellen (id und Titel)
    createMenuItem: function(id, title, success, error) {
        exec(success, error, "MenuPlugin", "createMenuItem", [{ id: id, title: title }]);
    },

    // Menü anzeigen / aktualisieren
    showMenu: function(success, error) {
        exec(success, error, "MenuPlugin", "showMenu", []);
    },

    // Menüitems zurücksetzen (optional)
    clearMenu: function(success, error) {
        exec(success, error, "MenuPlugin", "clearMenu", []);
    }
};

// Event für Klicks auf Menüitems
document.addEventListener('pluginMenuClicked', function(e) {
    console.log('Menu item clicked with id:', e.id);
});

module.exports = cordova_plugin_mglmenu;
