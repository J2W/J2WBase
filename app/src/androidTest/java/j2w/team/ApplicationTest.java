package j2w.team;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import j2w.team.common.log.L;

@RunWith(RobolectricTestRunner.class) public class ApplicationTest {

	@Test public void print_log() throws Exception {
        L.init(true);

        L.i("打印思密达");
    }
}