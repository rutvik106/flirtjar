package instagram.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.flirtjar.App;
import com.app.flirtjar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static instagram.InstaConstants.CALLBACKURL;
import static instagram.InstaConstants.authURLString;

/**
 * Created by rutvik on 3/20/2017 at 10:48 AM.
 */

public class InstagramLoginDialog extends Dialog
{

    private static final String TAG = App.APP_TAG + InstagramLoginDialog.class.getSimpleName();

    @BindView(R.id.wv_instaLogin)
    WebView wvInstaLogin;

    private String request_token;

    public InstagramLoginDialog(@NonNull Context context)
    {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_insta_login);

        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        wvInstaLogin.setVerticalScrollBarEnabled(false);
        wvInstaLogin.setHorizontalScrollBarEnabled(false);
        wvInstaLogin.setWebViewClient(new AuthWebViewClient());
        wvInstaLogin.getSettings().setJavaScriptEnabled(true);
        wvInstaLogin.loadUrl(authURLString);
        wvInstaLogin.clearCache(true);
        CookieSyncManager.createInstance(getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    class AuthWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if (url.startsWith(CALLBACKURL))
            {
                Log.i(TAG, "URL STARTING WITH CALLBACKURL :) !!!!!!");
                System.out.println(url);
                String parts[] = url.split("=");
                request_token = parts[1];  //This is your request token.
                InstagramLoginDialog.this.dismiss();
                Log.i(TAG, "REQUEST TOKEN: " + request_token);
                final ShowUserPhotosDialog instaUserDialog =
                        new ShowUserPhotosDialog(getContext(), request_token);
                instaUserDialog.show();
                return true;
            } else
            {
                Log.i(TAG, "URL NOT STARTING WITH CALLBACKURL :(");
                return false;
            }
        }
    }

}
