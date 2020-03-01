package net.aionstudios.artemis.layer;

import net.aionstudios.artemis.exception.LayerBoundaryException;
import net.aionstudios.artemis.network.Network;
import net.aionstudios.artemis.perceptron.IPerceptron;
import net.aionstudios.artemis.perceptron.InputPerceptron;

public class InputLayer extends Layer {

	public InputLayer(int size, Network network) {
		super(size, network);
		for(int i = 0; i < this.getPerceptrons().length; i++) {
			this.getPerceptrons()[i] = new InputPerceptron();
		}
	}
	
	public void setInputs(float[] inputs) {
		if(this.getPerceptrons().length!=inputs.length) {
			throw new LayerBoundaryException("Layer expected "+this.getPerceptrons().length+" inputs, but received "+inputs.length+"!");
		}
		int perceptronIndex = 0;
		for(IPerceptron p : this.getPerceptrons()) {
			((InputPerceptron)p).setValue(inputs[perceptronIndex]);
			perceptronIndex++;
		}
	}
	
}
