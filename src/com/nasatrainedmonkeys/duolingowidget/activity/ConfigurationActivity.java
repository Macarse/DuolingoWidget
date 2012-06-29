package com.nasatrainedmonkeys.duolingowidget.activity;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nasatrainedmonkeys.duolingowidget.R;
import com.nasatrainedmonkeys.duolingowidget.service.WidgetUpdateService;

public class ConfigurationActivity extends Activity {
  private int mAppWidgetId;
  private EditText mEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setResult(RESULT_CANCELED);

    setContentView(R.layout.configuration);

    mEditText = (EditText) findViewById(R.id.configuration_username);
    Intent intent = getIntent();
    Bundle extras = intent.getExtras();
    if (extras != null) {
      mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
          AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
      finish();
    }
  }

  public void onCreateClick(View v) {
    String username = mEditText.getText().toString();
    if (username == null || username.length() == 0) {
      Toast.makeText(this,
          getString(R.string.configuration_username_error),
          Toast.LENGTH_LONG).show();
      return;
    }
    username = username.trim().toLowerCase();
    SharedPreferences prefs = PreferenceManager
        .getDefaultSharedPreferences(this);
    Editor editor = prefs.edit();
    editor.putString("" + mAppWidgetId, username);
    editor.commit();

    startService(new Intent(this, WidgetUpdateService.class));

    Intent resultValue = new Intent();
    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
        mAppWidgetId);
    setResult(RESULT_OK, resultValue);
    finish();
  }
}
