var exec = require('cordova/exec');

function showMenu() {
    exec(
        function(success) { console.log(success); },
        function(error) { console.error(error); },
        'MenuPlugin',
        'showMenu',
        []
    );
}
