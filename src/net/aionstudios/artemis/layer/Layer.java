package net.aionstudios.artemis.layer;

import net.aionstudios.artemis.network.Network;
import net.aionstudios.artemis.perceptron.IPerceptron;

public abstract class Layer {
	
	private final int size;
	private final Network network;
	private final IPerceptron[] perceptrons;

	public Layer(int size, Network network) {
		this.size = size;
		this.network = network;
		perceptrons = new IPerceptron[size];
	}
	
	public int getSize() {
		return size;
	}
	
	public IPerceptron[] getPerceptrons() {
		return perceptrons;
	}
	
	public Network getNetwork() {
		return network;
	}

}
