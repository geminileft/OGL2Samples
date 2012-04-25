package TEEngine.Manager;

import android.os.SystemClock;

public class TEManagerTime {
	
	public static long currentTime() {
		return SystemClock.uptimeMillis();
	}
}
