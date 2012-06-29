package com.nasatrainedmonkeys.duolingowidget.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.nasatrainedmonkeys.duolingowidget.service.WidgetUpdateService;

public class DuolingoWidgetProvider extends AppWidgetProvider {

  @Override
  public void onUpdate(Context context,
      AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    context
        .startService(new Intent(context, WidgetUpdateService.class));
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    final int N = appWidgetIds.length;
    SharedPreferences prefs = PreferenceManager
        .getDefaultSharedPreferences(context);
    Editor editor = prefs.edit();

    for (int i = 0; i < N; i++) {
      editor.remove("" + appWidgetIds[i]);
    }

    editor.commit();
  }
}
