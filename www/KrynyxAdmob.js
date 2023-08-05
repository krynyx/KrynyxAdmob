var exec = require('cordova/exec');


var KrynyxAdmob = {
	loadAds: function (adsId, success, error) {
		var options = {};
		options.adsId = adsId;
		exec(success, error, 'KrynyxAdmob', 'loadAds', [options]);
	},
	showAds: function() {
		exec(null, null, 'KrynyxAdmob', 'showAds', null);
	}
};

module.exports = KrynyxAdmob;
