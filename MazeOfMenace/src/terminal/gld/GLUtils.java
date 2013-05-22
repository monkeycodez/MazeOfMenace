package terminal.gld;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class GLUtils {
	
	public static FloatBuffer toBuf(float[] f){
		return (FloatBuffer) BufferUtils.createFloatBuffer(f.length).put(f).flip();
	}

}
