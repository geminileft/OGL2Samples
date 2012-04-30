package TEEngine.Util;

public class TEUtilColor4 {
    public float r;
    public float g;
    public float b;
    public float a;
    
    TEUtilColor4(float fR, float fG, float fB, float fA) {
    	r = fR;
    	g = fG;
    	b = fB;
    	a = fA;
    }
    
    public static TEUtilColor4 make(float fR, float fG, float fB, float fA) {
    	return new TEUtilColor4(fR, fG, fB, fA);
    }
    
}
