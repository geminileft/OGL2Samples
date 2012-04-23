package demo.OGL2Samples;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class TEManagerFile {
	public static Context mContext;
	
	public static void setContext(Context context) {
		mContext = context;
	}

    public static String readFileContents(String filename) {
		StringBuffer resultBuffer = new StringBuffer();
		try {
			final int BUFFER_SIZE = 1024;
			char buffer[] = new char[BUFFER_SIZE];
			int charsRead;
			InputStream stream = mContext.getAssets().open(filename);
			InputStreamReader reader = new InputStreamReader(stream);
    		resultBuffer = new StringBuffer();
			while ((charsRead = reader.read(buffer, 0, BUFFER_SIZE)) != -1) {
				resultBuffer.append(buffer, 0, charsRead);
			}
			reader.close();
			stream.close();
		} catch (Exception e) {
			Log.v("info", "very bad");
		}
		return resultBuffer.toString();
    }    

}
