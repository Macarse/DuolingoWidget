package com.nasatrainedmonkeys.duolingowidget.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;

import com.nasatrainedmonkeys.duolingowidget.R;
import com.nasatrainedmonkeys.duolingowidget.model.DuolingoLanguage;
import com.nasatrainedmonkeys.duolingowidget.model.DuolingoUser;
import com.nasatrainedmonkeys.duolingowidget.parser.DuolingoParser;
import com.nasatrainedmonkeys.duolingowidget.widget.DuolingoWidgetProvider;

public class WidgetUpdateService extends IntentService {

  private static final String URL_FMT = "http://duolingo.com/#/%s";
  private static final String TAG = WidgetUpdateService.class
      .getCanonicalName();

  public WidgetUpdateService() {
    this(WidgetUpdateService.class.getCanonicalName());
  }

  public WidgetUpdateService(String name) {
    super(name);
  }

  @Override
  protected void onHandleIntent(Intent intent) {

    AppWidgetManager appWidgetManager = AppWidgetManager
        .getInstance(this);

    ComponentName thisWidget = new ComponentName(this, DuolingoWidgetProvider.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    final DuolingoParser parser = new DuolingoParser();

    final String progressFmt = getString(R.string.widget_progress);

    for (int widgetId : allWidgetIds) {

      String username = prefs.getString("" + widgetId, null);
      if ( username == null ) {
        continue;
      }

      final DuolingoUser user = parser.getInfoFromUser(username);
      if ( user == null ) {
        Log.e(TAG, "user " + username + " couldn't be parsed!");
        continue;
      }

      final DuolingoLanguage language = user.getLanguage();

      RemoteViews remoteViews = new RemoteViews(this
          .getApplicationContext().getPackageName(),
          R.layout.widget);

      final String progressText = String.format(progressFmt,
          language.getLevelProgress(), language.getLevelPoints(),
          language.getLevel());

      remoteViews.setTextViewText(R.id.widget_username, user.getUsername());
      remoteViews.setTextViewText(R.id.widget_learning, language.getName());
      remoteViews.setTextViewText(R.id.widget_points, "" + language.getPoints());
      remoteViews.setTextViewText(R.id.widget_translations, "" + language.getSentencesTranslated());
      remoteViews.setTextViewText(R.id.widget_progress_text, Html.fromHtml(progressText));
      remoteViews.setProgressBar(R.id.widget_progress, 100, language.getLevelPercent(), false);

      byte[] avatar = user.getAvatar();
      if ( avatar != null ) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
        remoteViews.setBitmap(R.id.widget_avatar, "setImageBitmap", bitmap);
      }

      Intent urlIntent = new Intent(Intent.ACTION_VIEW,
          Uri.parse(String.format(URL_FMT, user.getUsername())));
      PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, urlIntent, 0);
      remoteViews.setOnClickPendingIntent(R.id.widget_root, pendingIntent);
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }

    stopSelf();
  }

}
