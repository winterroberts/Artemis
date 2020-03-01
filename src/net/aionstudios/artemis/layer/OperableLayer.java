package net.aionstudios.artemis.layer;

import net.aionstudios.artemis.exception.LayerBoundaryException;
import net.aionstudios.artemis.exception.PerceptronBoundaryException;
import net.aionstudios.artemis.network.Network;
import net.aionstudios.artemis.perceptron.IPerceptron;
import net.aionstudios.artemis.perceptron.OperablePerceptron;

public abstract class OperableLayer extends Layer {
	
	private final Layer previousLayer;

	public OperableLayer(int size, Layer previous, Network network) {
		super(size, network);
		this.previousLayer = previous;
		for(int i = 0; i < this.getPerceptrons().length; i++) {
			this.getPerceptrons()[i] = new OperablePerceptron(this, previous.getSize());
		}
	}
	
	public void compute() {
		for(IPerceptron p : this.getPerceptrons()) {
			((OperablePerceptron)p).compute();
		}
	}
	
	public void randomize(float randomLow, float randomHigh) {
		for(IPerceptron p : this.getPerceptrons()) {
			((OperablePerceptron)p).randomize(randomLow, randomHigh);
		}
	}
	
	public OperableLayer breed(OperableLayer other) {
		if(other.getPerceptrons().length!=this.getPerceptrons().length) {
			throw new LayerBoundaryException("Breeding Layer expected "+this.getPerceptrons().length+" perceptrons, but received "+other.getPerceptrons().length+"!");
		}
		for(int i = 0; i < this.getPerceptrons().length; i++) {
			((OperablePerceptron) this.getPerceptrons()[i]).breed(((OperablePerceptron) other.getPerceptrons()[i]));
		}
		return this;
	}
	
	public OperableLayer mutate(float mutateChance, float randomLow, float randomHigh) {
		for(int i = 0; i < this.getPerceptrons().length; i++) {
			((OperablePerceptron) this.getPerceptrons()[i]).mutate(mutateChance, randomLow, randomHigh);
		}
		return this;
	}
	
	public OperableLayer copy(Layer previousCopy, Network networkCopy) {
		OperableLayer copy;
		if(this instanceof HiddenLayer) {
			copy = new HiddenLayer(this.getSize(), previousCopy, networkCopy);
		} else {
			copy = new OutputLayer(this.getSize(), previousCopy, networkCopy);
		}
		for(int i = 0; i < this.getPerceptrons().length; i++) {
			copy.getPerceptrons()[i] = ((OperablePerceptron) this.getPerceptrons()[i]).copy(copy);
			((OperablePerceptron) copy.getPerceptrons()[i]).setBias(((OperablePerceptron) this.getPerceptrons()[i]).getBias());
		}
		return copy;
	}
	
	public Layer getPreviousLayer() {
		return previousLayer;
	}

}
