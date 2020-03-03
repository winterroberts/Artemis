package net.aionstudios.artemis.error;

public class MeanAbsoluteError implements IErrorFunction {
	
	@Override
	public float calculate(float[] predicted, float[] actual) {
		float n = actual.length; 
		float sum = 0.0f; 
		for (int i = 0; i < n; i++) { 
		    float diff = Math.abs(actual[i] - predicted[i]); 
		    sum = sum + diff; 
		} 
		return sum / (float) n;
	}

}
