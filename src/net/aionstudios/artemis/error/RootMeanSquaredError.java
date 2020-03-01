package net.aionstudios.artemis.error;

public class RootMeanSquaredError implements IErrorFunction {

	@Override
	public float calculate(float[] predicted, float[] actual) {
		float n = actual.length; 
		float sum = 0.0f; 
		for (int i = 0; i < n; i++) { 
		    float diff = actual[i] - predicted[i]; 
		    sum = sum + diff * diff; 
		} 
		float mse = sum / n;
		return (float) Math.sqrt(mse);
	}

}
