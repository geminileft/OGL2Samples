package demo.OGL2Samples;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class TEManagerTexture {
	private static TEManagerTexture mSharedInstance;
	private static final int FLOAT_SIZE = 4;
	private static final int MAX_TEXTURE_SIZE = 1024;
	//private static int maPositionHandle;
	//private static int maTextureHandle;
	private static HashMap<Long, FloatBuffer> mPositionMap = new HashMap<Long, FloatBuffer>();
	private static HashMap<Long, FloatBuffer> mCropMap = new HashMap<Long, FloatBuffer>();
	private Context mContext;
	
	public TEManagerTexture() {
		super();
	}
	
	public void setContext(Context context) {
		mContext = context;
	}
	
	public static TEManagerTexture sharedInstance() {
		if (mSharedInstance == null) {
			mSharedInstance = new TEManagerTexture();
		}
		return mSharedInstance;
	}
	/*
	public static void setPositionHandle(int handle) {
		maPositionHandle = handle;
	}
	
	public static void setCropHandle(int handle) {
		maTextureHandle = handle;
	}
	*/
	
	public int getTexture2D(int resourceId) {
		//GL10 gl = TEManagerGraphics.getGL();

		int textures[] = new int[1];
		GLES20.glGenTextures(1, textures, 0);
		InputStream is = mContext.getResources().openRawResource(resourceId);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        //gl.glTexEnvf(GLES20.GL_TEXTURE_ENV, GLES20.GL_TEXTURE_ENV_MODE, GLES20.GL_MODULATE); //GL10.GL_REPLACE);

		Bitmap bitmap = null;
		try {
			//BitmapFactory is an Android graphics utility for images
			bitmap = BitmapFactory.decodeStream(is);

		} finally {
			//Always clear and close
			try {
				is.close();
				is = null;
			} catch (IOException e) {
			}
		}
		
		final int bitmapHeight = bitmap.getHeight();
		final int bitmapWidth = bitmap.getWidth();
		final int textureHeight = closestPowerOf2(bitmapHeight);
		final int textureWidth = closestPowerOf2(bitmapWidth);
		if ((bitmapHeight == textureHeight) && (bitmapWidth == textureWidth)) {
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);			
		} else {
			Bitmap adjustedBitmap = Bitmap.createScaledBitmap(bitmap, textureHeight, textureWidth, false);
	        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, adjustedBitmap, 0);
	        adjustedBitmap.recycle();
		}
		bitmap.recycle();
		return textures[0];
	}
	
	public static FloatBuffer getPositionBuffer(long hash) {
		return mPositionMap.get(hash);
	}
	
	public static FloatBuffer getCropBuffer(long hash) {
		return mCropMap.get(hash);
	}

	private static int closestPowerOf2(int n) {
		int c = 1;
		while (c < n && c < MAX_TEXTURE_SIZE)
			c <<= 1;
		return c;
	}

	public static FloatBuffer getPositionBuffer(TESize size) {
		final float halfWidth = size.width / 2;
		final float halfHeight = size.height / 2;
		final int FLOAT_SIZE = 4;
        final float[] verticesData = {
                // X, Y, Z, U, V
        	halfWidth,  -halfHeight
    		, -halfWidth, -halfHeight
    		, -halfWidth, halfHeight
    		, halfWidth, halfHeight
        };
        FloatBuffer positionBuffer = ByteBuffer.allocateDirect(verticesData.length
                * FLOAT_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
        positionBuffer.put(verticesData).position(0);
        return positionBuffer;
	}

	public static FloatBuffer getCoordsBuffer(TESize size) {
        final float[] coordData = {
                // X, Y, Z, U, V
        	0.0f,  0.0f
    		, 1.0f, 0.0f
    		, 0.0f, 1.0f
    		, 1.0f, 1.0f
        };
        
        FloatBuffer positionBuffer = ByteBuffer.allocateDirect(coordData.length
                * FLOAT_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
        positionBuffer.put(coordData).position(0);
        return positionBuffer;
	}
}
