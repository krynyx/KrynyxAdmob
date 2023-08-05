package io.github.krynyx.cordova.admob.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import androidx.annotation.NonNull;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class KrynyxAdmob extends CordovaPlugin {
	private InterstitialAd mInterstitialAd;
	private String adsId;
	private static final String ADS_TEST_ID = "ca-app-pub-3940256099942544/1033173712";
	private static final String TAG = "KrynyxAdmob";
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Log.d(TAG, "plugin inicializado com sucesso");
	}

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		if (action.equals("showAds")) {
			Log.d(TAG, "executando a funcao showAds");
			this.showAds(callbackContext);
			
		} else if (action.equals("loadAds")) {
			try {
				JSONObject options = args.getJSONObject(0);
				this.adsId = options.getString("adsId");
				
				Log.d(TAG, "adsId configurado com sucesso");
			} catch (JSONException e) {
				Log.w(TAG, "Erro ao processar os argumentos");
				callbackContext.error("Error encountered: " + e.getMessage());
				return false;
			}
			
			Log.d(TAG, "executando a funcao loadAds");
			this.loadAds(callbackContext);			
		}
		
		return true;
	}
	
	private void loadAds(CallbackContext callbackContext) {
		
		cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
				MobileAds.initialize(cordova.getActivity(), new OnInitializationCompleteListener() {
					@Override
					public void onInitializationComplete(InitializationStatus initializationStatus) {}
				});
				AdRequest adRequest = new AdRequest.Builder().build();

				InterstitialAd.load(cordova.getActivity(), adsId, adRequest,
				new InterstitialAdLoadCallback() {
					@Override
					public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
						// The mInterstitialAd reference will be null until
						// an ad is loaded.
						mInterstitialAd = interstitialAd;
						Log.d(TAG, "anuncio carregado com sucesso");
						// Send a positive result to the callbackContext
						PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
						callbackContext.sendPluginResult(pluginResult);
					}

					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
					// Handle the error
						Log.d(TAG, loadAdError.toString());
						mInterstitialAd = null;
						PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR);
						callbackContext.sendPluginResult(pluginResult);
					}
				});
			}
		});
	}
	

	private void showAds(CallbackContext callbackContext) {
		cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
				if (mInterstitialAd != null) {
					mInterstitialAd.show(cordova.getActivity());
				} else {
					Log.d(TAG, "o anuncio ainda nao foi carregado");
				}
            }
        });
	}
}
