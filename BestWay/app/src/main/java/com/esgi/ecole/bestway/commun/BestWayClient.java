package com.esgi.ecole.bestway.commun;

import android.content.Context;
import android.content.SharedPreferences;

import com.esgi.ecole.bestway.models.Session;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by mohsen raeisi on 09/09/2016.
 */



public class BestWayClient {


    public static final String BASE_URL = "https://bestway.clementpeyrabere.net:8001/";
    public static final String REGISTER = "public/register";
    public static final String LOGIN = "public/login";
    public static final String CALCUL = "api/v1/transports";


    private static AsyncHttpClient client = new AsyncHttpClient();



    public static void getWithSession(String url,Session session, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setSSLSocketFactory(
                new SSLSocketFactory(getSslContext(),
                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

        client.get(url, params, responseHandler);
        client.addHeader("x-access-token",session.getToken());
        client.addHeader("x-user-id",session.getUserId());
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        client.setSSLSocketFactory(
                new SSLSocketFactory(getSslContext(),
                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

        client.post(url, params, responseHandler);
    }


    public static void postWithSession(String url, RequestParams params,Session session, AsyncHttpResponseHandler responseHandler) {

        client.setSSLSocketFactory(
                new SSLSocketFactory(getSslContext(),
                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

        client.post(url, params, responseHandler);
        client.addHeader("x-access-token",session.getToken());
        client.addHeader("x-user-id",session.getUserId());
    }



    public static void postWithSession(Context context, String url, StringEntity entity, Session session, AsyncHttpResponseHandler responseHandler) {

        client.setSSLSocketFactory(
                new SSLSocketFactory(getSslContext(),
                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

        client.post(context, url, entity, "application/json", responseHandler);
        client.addHeader("x-access-token",session.getToken());
        client.addHeader("x-user-id",session.getUserId());
    }

    public static void saveSession(Context context,Session session){
        SharedPreferences.Editor editor = context.getSharedPreferences("SESSION", Context.MODE_PRIVATE).edit();
        editor.putString("token", session.getToken());
        editor.putString("userId", session.getUserId());
        editor.apply();
    }

    public static Session getSession(Context context){
        SharedPreferences prefs = context.getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        Session session = new Session();
        session.setToken(prefs.getString("token", null));
        session.setUserId(prefs.getString("userId", null));
        return session;

    }



    public static SSLContext getSslContext() {

        TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        } };

        SSLContext sslContext=null;

        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext;
    }

    /*


           BestWayClient.post(url, params  , new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.e("SUCCES",response.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("ERROR","REGISTER");

            }
        });


     */

}
