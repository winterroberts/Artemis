package net.aionstudios.artemis.activation;

public class Step implements IActivationFunction {

	@Override
	public float activate(float value) {
		return value>0?1:0;
	}

}
