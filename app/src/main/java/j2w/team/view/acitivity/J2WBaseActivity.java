package j2w.team.view.acitivity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import j2w.team.common.log.L;

/**
 * Created by sky on 15/1/26.
 */
public abstract class J2WBaseActivity extends ActionBarActivity {

	/**
	 * TAG标记
	 */
	abstract String getTag();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.tag(getTag());
        L.i("onCreate()");
    }

	@Override protected void onStart() {
		super.onStart();
        L.tag(getTag());
        L.i("onStart()");
	}

	@Override protected void onResume() {
		super.onResume();
        L.tag(getTag());
        L.i("onResume()");
	}

	@Override protected void onPause() {
		super.onPause();
        L.tag(getTag());
        L.i("onPause()");
	}

	@Override protected void onRestart() {
		super.onRestart();
        L.tag(getTag());
        L.i("onRestart()");
	}

	@Override protected void onStop() {
		super.onStop();
        L.tag(getTag());
        L.i("onStop()");
	}

	@Override protected void onDestroy() {
		super.onDestroy();
        L.tag(getTag());
        L.i("onDestroy()");
	}
}
