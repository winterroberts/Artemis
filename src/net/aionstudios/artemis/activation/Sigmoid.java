package net.aionstudios.artemis.activation;

public class Sigmoid implements IActivationFunction {

	@Override
	public float activate(float value) {
		return (float) (1/(1+Math.pow(Math.E, -value)));
	}

}
