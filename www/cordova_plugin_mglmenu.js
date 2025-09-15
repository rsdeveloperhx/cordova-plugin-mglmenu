var exec = require('cordova/exec');

var MenuPlugin = {
    createMenuItem: function(id, title, success, error) {
        exec(success, error, "MenuPlugin", "createMenuItem", [{id: id, title: title}]);
    },
    showMenu: function(success, error) {
        exec(success, error, "MenuPlugin", "showMenu", []);
    }
};
