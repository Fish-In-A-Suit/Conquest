package models;

public class Quad {
	
	public float[] positions = {
            -0.5f, 0.5f, 0f, //VO
            -0.5f, -0.5f, 0f, //V1
            0.5f, -0.5f, 0f, //V2
            0.5f, 0.5f, 0f //V3
	};
	
	public int[] indices = {
		0, 1, 3, //top left triangle (V0, V1, V3)
		3, 1, 2 //bottom right triangle (V3, V1, V2)
	};
	
	public float[] colours = {
			0.1f, 0.3f, 0.9f,
			0.8f, 0.3f, 0.2f,
			0.8f, 0.6f, 0.8f,
			0.2f, 0.7f, 0.4f
	};

}
