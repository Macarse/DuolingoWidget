package com.nasatrainedmonkeys.duolingowidget.parser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;

public class AvatarDownloader {
  private static final String URL_FMT = "http://duolingo.com/avatars/%d/large";
  private static final String TAG = AvatarDownloader.class.getCanonicalName();

  public byte[] downloadAvatar(int userId) {
    HttpURLConnection urlConnection = null;
    byte [] ret = null;

    try {
      URL url = new URL(String.format(URL_FMT, userId));
      urlConnection = (HttpURLConnection) url.openConnection();

      InputStream is = urlConnection.getInputStream();
      BufferedInputStream bis = new BufferedInputStream(is, 128);
      ByteArrayBuffer baf = new ByteArrayBuffer(128);
      // get the bytes one by one
      int current = 0;
      while ((current = bis.read()) != -1) {
        baf.append((byte) current);
      }
      ret = baf.toByteArray();
    } catch (Exception e) {
      Log.d(TAG, "Couldn't download avatar for id: " + userId);
    } finally {
      urlConnection.disconnect();
    }

    return ret;
  }
}
