package net.aionstudios.artemis.activation;

public class RectifiedLinearUnit implements IActivationFunction {

	@Override
	public float activate(float value) {
		return Math.max(0, value);
	}

}
