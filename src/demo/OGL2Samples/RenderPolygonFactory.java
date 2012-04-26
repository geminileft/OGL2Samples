package demo.OGL2Samples;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import TEEngine.Util.TEColor4;
import TEEngine.Util.TEUtilSize;

public class RenderPolygonFactory {
	public RenderPolygon roundedRectCorner(TEColor4 color, float radius, int density) {
    final float halfHeight = radius;
    final float halfWidth = radius;
    final int offset = 4;
    final int vertexCount = offset + density;
    float vertices[] = new float[vertexCount * 2];
    vertices[0] = 0;
    vertices[1] = 0;
    vertices[2] = 0;
    vertices[3] = halfHeight;
    float x;
    float y;
    if (density > 0) {
        float theta = 90.0f / (density + 1);
        float angle;
        for (int i = 1; i <= density; ++i) {
            angle = 90 - (theta * i);
            double lCos = Math.cos(deg2rad(angle));
            double lSin = Math.sin(deg2rad(angle));
            x = (float)(lCos * radius);
            y = (float)(lSin * radius);
            vertices[offset + (i - 1) * 2] = x;
            vertices[offset + (i - 1) * 2 + 1] = y;
        }
    }
    vertices[(vertexCount - 2) * 2] = halfWidth;
    vertices[((vertexCount - 2) * 2) + 1] = 0;
    vertices[(vertexCount - 1) * 2] = vertices[0];
    vertices[((vertexCount - 1) * 2) + 1] = vertices[1];

    RenderPolygon rf = new RenderPolygon();
    
    FloatBuffer positionBuffer = ByteBuffer.allocateDirect(vertices.length
            * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    positionBuffer.put(vertices).position(0);

    rf.setVertices(positionBuffer, vertexCount);
    rf.setColor(color);
    
    return rf;
}

	public static RenderPolygon roundedRectPolygon(TEUtilSize size, TEColor4 color, float radius) {    
	    final float halfHeight = (float)size.height / 2;
	    final float halfWidth = (float)size.width / 2;
	    final int vertexCount = 9;
	    float vertices[] = new float[vertexCount * 2];
	    vertices[0] = -halfWidth + radius;
	    vertices[1] = -halfHeight;
	    vertices[2] = halfWidth - radius;
	    vertices[3] = -halfHeight;
	    
	    
	    vertices[4] = halfWidth;
	    vertices[5] = -halfHeight + radius;
	    vertices[6] = halfWidth;
	    vertices[7] = halfHeight - radius;
	    
	    final int offset = 8;
	    
	    vertices[offset] = halfWidth - radius;
	    vertices[offset + 1] = halfHeight;
	    vertices[offset + 2] = -halfWidth + radius;
	    vertices[offset + 3] = halfHeight;
	    
	    vertices[offset + 4] = -halfWidth;
	    vertices[offset + 5] = halfHeight - radius;
	    vertices[offset + 6] = -halfWidth;
	    vertices[offset + 7] = -halfHeight + radius;
	    
	    vertices[(vertexCount - 1) * 2] = vertices[0];
	    vertices[((vertexCount - 1) * 2) + 1] = vertices[1];
	        
	    RenderPolygon rf = new RenderPolygon();
	    
	    FloatBuffer positionBuffer = ByteBuffer.allocateDirect(vertices.length
	            * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
	    positionBuffer.put(vertices).position(0);

	    rf.setVertices(positionBuffer, vertexCount);
	    rf.setColor(color);
	    return rf;
	}

	public RenderPolygon roundedRect(TEUtilSize size, TEColor4 color, float radius, int density) {    
    final float halfHeight = (float)size.height / 2;
    final float halfWidth = (float)size.width / 2;
    final int vertexCount = 9 + (4 * density);
    float vertices[] = new float[vertexCount * 2];
    float x;
    float y;
    vertices[0] = -halfWidth + radius;
    vertices[1] = -halfHeight;
    vertices[2] = halfWidth - radius;
    vertices[3] = -halfHeight;    
    
    int offset = 4;
    
    if (density > 0) {
        float theta = 90.0f / (density + 1);
        float angle;
        for (int i = 1; i <= density; ++i) {
            angle = theta * i;
            float lCos = (float)Math.cos(deg2rad(angle));
            float lSin = (float)Math.sin(deg2rad(angle));
            y = lCos * radius;
            x = lSin * radius;
            vertices[offset + (i - 1) * 2] = halfWidth - radius + x;
            vertices[offset + (i - 1) * 2 + 1] = -halfHeight + radius - y;
        }
    }
    
    offset += density * 2;

    vertices[offset] = halfWidth;
    vertices[offset + 1] = -halfHeight + radius;
    vertices[offset + 2] = halfWidth;
    vertices[offset + 3] = halfHeight - radius;
    
    offset += 4;
    
    if (density > 0) {
        float theta = 90.0f / (density + 1);
        float angle;
        for (int i = 1; i <= density; ++i) {
            angle = theta * i;
            float lCos = (float)Math.cos(deg2rad(angle));
            float lSin = (float)Math.sin(deg2rad(angle));
            x = lCos * radius;
            y = lSin * radius;
            vertices[offset + (i - 1) * 2] = halfWidth - radius + x;
            vertices[offset + (i - 1) * 2 + 1] = halfHeight - radius + y;
        }
    }

    offset += density * 2;
    
    vertices[offset] = halfWidth - radius;
    vertices[offset + 1] = halfHeight;
    vertices[offset + 2] = -halfWidth + radius;
    vertices[offset + 3] = halfHeight;
    
    offset += 4;

    if (density > 0) {
        float theta = 90.0f / (density + 1);
        float angle;
        for (int i = 1; i <= density; ++i) {
            angle = theta * i;
            float lCos = (float)Math.cos(deg2rad(angle));
            float lSin = (float)Math.sin(deg2rad(angle));
            y = lCos * radius;
            x = lSin * radius;
            vertices[offset + (i - 1) * 2] = -halfWidth + radius - x;
            vertices[offset + (i - 1) * 2 + 1] = halfHeight - radius + y;
        }
    }
    
    offset += density * 2;

    vertices[offset] = -halfWidth;
    vertices[offset + 1] = halfHeight - radius;
    vertices[offset + 2] = -halfWidth;
    vertices[offset + 3] = -halfHeight + radius;

    offset += 4;
    
    if (density > 0) {
        float theta = 90.0f / (density + 1);
        float angle;
        for (int i = 1; i <= density; ++i) {
            angle = theta * i;
            float lCos = (float)Math.cos(deg2rad(angle));
            float lSin = (float)Math.sin(deg2rad(angle));
            x = lCos * radius;
            y = lSin * radius;
            vertices[offset + (i - 1) * 2] = -halfWidth + radius - x;
            vertices[offset + (i - 1) * 2 + 1] = -halfHeight + radius - y;
        }
    }
    
    offset += density * 2;
    
    vertices[(vertexCount - 1) * 2] = vertices[0];
    vertices[((vertexCount - 1) * 2) + 1] = vertices[1];
    
    RenderPolygon rf = new RenderPolygon();
    
    FloatBuffer positionBuffer = ByteBuffer.allocateDirect(vertices.length
            * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    positionBuffer.put(vertices).position(0);

    rf.setVertices(positionBuffer, vertexCount);
    rf.setColor(color);
    return rf;
}

	private double deg2rad(double deg) {
	    return (deg * 3.14159265358979323846f / 180.0f);
	}
}