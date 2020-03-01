package net.aionstudios.artemis.perceptron;

public class InputPerceptron implements IPerceptron {
	
	private float input;

	public void setValue(float input) {
		this.input = input;
	}
	
	@Override
	public float read() {
		return input;
	}

}
