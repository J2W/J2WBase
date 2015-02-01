package j2w.team;

import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class) @Config(manifest = "./src/main/AndroidManifest.xml",emulateSdk = 18) public class ApplicationTest {

	@Test public void testSample() {

        Log.i("asdfasdf","asdfasdf");

        Assert.assertEquals("asdf", "asdf");
	}
}