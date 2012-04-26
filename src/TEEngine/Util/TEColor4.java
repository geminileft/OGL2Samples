package TEEngine.Util;

public class TEColor4 {
    public float r;
    public float g;
    public float b;
    public float a;
    
    TEColor4(float fR, float fG, float fB, float fA) {
    	r = fR;
    	g = fG;
    	b = fB;
    	a = fA;
    }
    
    public static TEColor4 make(float fR, float fG, float fB, float fA) {
    	return new TEColor4(fR, fG, fB, fA);
    }
    
}
