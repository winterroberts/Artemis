package net.aionstudios.artemis.perceptron;

import net.aionstudios.artemis.exception.LayerBoundaryException;
import net.aionstudios.artemis.exception.PerceptronBoundaryException;
import net.aionstudios.artemis.layer.OperableLayer;

public class OperablePerceptron implements IPerceptron {
	
	private final OperableLayer parentLayer;
	private final float[] weights;
	private float bias;
	
	private float value;
	
	public OperablePerceptron(OperableLayer parentLayer, int inputs) {
		this.parentLayer = parentLayer;
		this.weights = new float[inputs];
	}

	public void compute() {
		IPerceptron[] inputs = parentLayer.getPreviousLayer().getPerceptrons();
		if(inputs.length!=weights.length) {
			value = 0;
			throw new PerceptronBoundaryException("Computing perceptron expected "+weights.length+" inputs, but received "+inputs.length+"!");
		} else {
			float output = 0F;
			for(int i = 0; i < inputs.length; i++) {
				output += inputs[i].read() * weights[i];
			}
			value = parentLayer.getNetwork().getSchematic().getActivationFunction().activate(output-bias);
		}
	}
	
	public void randomize(float randomLow, float randomHigh) {
		if(randomHigh<=randomLow) {
			
		} else {
			for(int i = 0; i < weights.length; i++) {
				weights[i] = (parentLayer.getNetwork().getRand().nextFloat()*(randomHigh-randomLow))+randomLow;
			}
			bias = (parentLayer.getNetwork().getRand().nextFloat()*(randomHigh-randomLow))+randomLow;
		}
	}
	
	public OperablePerceptron breed(OperablePerceptron other) {
		if(other.weights.length!=this.weights.length) {
			throw new PerceptronBoundaryException("Breeding perceptron expected "+this.weights.length+" inputs, but received "+other.weights.length+"!");
		}
		for(int i = 0; i < this.weights.length; i++) {
			this.weights[i] = parentLayer.getNetwork().getRand().nextFloat()>0.5f?this.weights[i]:other.weights[i];
		}
		this.bias = parentLayer.getNetwork().getRand().nextFloat()>0.5f?this.bias:other.bias;
		return this;
	}
	
	public OperablePerceptron mutate(float mutateChance, float randomLow, float randomHigh) {
		if (mutateChance > 1.0f || mutateChance < 0f) {
			
		} else if(randomHigh<=randomLow) {
			
		} else {
			for(int i = 0; i < weights.length; i++) {
				if(parentLayer.getNetwork().getRand().nextFloat()<mutateChance) {
					weights[i] = (parentLayer.getNetwork().getRand().nextFloat()*(randomHigh-randomLow))+randomLow;
				}
			}
			if(parentLayer.getNetwork().getRand().nextFloat()<mutateChance) {
				bias = (parentLayer.getNetwork().getRand().nextFloat()*(randomHigh-randomLow))+randomLow;
			}
		}
		return this;
	}
	
	public OperablePerceptron copy(OperableLayer ol) {
		OperablePerceptron copy = new OperablePerceptron(ol, this.weights.length);
		for(int i = 0; i < this.weights.length; i++) {
			copy.weights[i] = this.weights[i];
		}
		copy.bias = this.bias;
		return copy;
	}
	
	public float getBias() {
		return bias;
	}

	public void setBias(float bias) {
		this.bias = bias;
	}

	public OperableLayer getParentLayer() {
		return parentLayer;
	}

	public float[] getWeights() {
		return weights;
	}

	@Override
	public float read() {
		return value;
	}

}
