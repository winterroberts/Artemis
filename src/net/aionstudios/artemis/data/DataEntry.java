package net.aionstudios.artemis.data;

public class DataEntry {
	
	private final float[] inputs;
	private final float[] outputs;
	
	public DataEntry(float[] inputs, float[] outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
	}
	
	public float[] getInputs() {
		return inputs;
	}
	
	public float[] getOutputs() {
		return outputs;
	}

}
