package com.nasatrainedmonkeys.duolingowidget.parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.google.gson.stream.JsonReader;
import com.nasatrainedmonkeys.duolingowidget.model.DuolingoLanguage;
import com.nasatrainedmonkeys.duolingowidget.model.DuolingoUser;

public class DuolingoParser {
  private static final String TAG = DuolingoParser.class
      .getCanonicalName();
  private static final String URL_FMT = "http://duolingo.com/users/%s";

  public DuolingoUser getInfoFromUser(String username) {
    HttpURLConnection urlConnection = null;
    DuolingoUser user = null;

    try {
      URL url = new URL(String.format(URL_FMT, username));
      urlConnection = (HttpURLConnection) url.openConnection();

      InputStream in = new BufferedInputStream(
          urlConnection.getInputStream());
      JsonReader reader = new JsonReader(new InputStreamReader(in,
          "UTF-8"));

      user = parseJson(reader);

    } catch (Exception e) {
      Log.e(TAG, "ERROR: " + e.getCause());
    } finally {
      urlConnection.disconnect();
    }

    return user;
  }

  private DuolingoUser parseJson(JsonReader reader) throws IOException {
    DuolingoUser user = new DuolingoUser();

    // Get the username
    reader.beginObject();
    reader.nextName();
    user.setUsername(reader.nextString());

    // Read up to language_data
    while (reader.hasNext()) {
      String name = reader.nextName();
      if (name.equals("language_data")) {
        break;
      } else {
        reader.skipValue();
      }
    }

    user.setLanguage(parseLanguage(reader));

    while ( reader.hasNext() ) {
      String name = reader.nextName();
      if ( name.equals("id") ) {
        user.setId(reader.nextInt());
      } else {
        reader.skipValue();
      }
    }

    reader.endObject();

    // Download the avatar
    AvatarDownloader avatarDownloader = new AvatarDownloader();
    user.setAvatar(avatarDownloader.downloadAvatar(user.getId()));

    return user;
  }

  private DuolingoLanguage parseLanguage(JsonReader reader)
      throws IOException {
    DuolingoLanguage language = new DuolingoLanguage();

    reader.beginObject();
    while (reader.hasNext()) {
      reader.nextName();

      reader.beginObject();
      while (reader.hasNext()) {
        String name = reader.nextName();
        if (name.equals("language_string")) {
          language.setName(reader.nextString());
        } else if (name.equals("level")) {
          language.setLevel(reader.nextInt());
        } else if (name.equals("level_progress")) {
          language.setLevelProgress(reader.nextInt());
        } else if (name.equals("level_left")) {
          language.setLevelLeft(reader.nextInt());
        } else if (name.equals("level_percent")) {
          language.setLevelPercent(reader.nextInt());
        } else if (name.equals("level_points")) {
          language.setLevelPoints(reader.nextInt());
        } else if (name.equals("points")) {
          language.setPoints(reader.nextInt());
        } else if (name.equals("sentences_translated")) {
          language.setSentencesTranslated(reader.nextInt());
        } else {
          reader.skipValue();
        }
      }

      reader.endObject();
    }

    reader.endObject();
    return language;
  }
}
