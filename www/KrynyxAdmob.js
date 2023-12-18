var exec = require('cordova/exec');


var KrynyxAdmob = {
	showBanner: function (bannerId) {
		var options = {};
		options.bannerId = bannerId;
		exec(null, null, 'KrynyxAdmob', 'showBanner', [options]);		
	},
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
