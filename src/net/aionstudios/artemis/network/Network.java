package net.aionstudios.artemis.network;

import java.util.Random;

import net.aionstudios.artemis.layer.HiddenLayer;
import net.aionstudios.artemis.layer.InputLayer;
import net.aionstudios.artemis.layer.Layer;
import net.aionstudios.artemis.layer.OperableLayer;
import net.aionstudios.artemis.layer.OutputLayer;
import net.aionstudios.artemis.perceptron.IPerceptron;
import net.aionstudios.artemis.perceptron.OperablePerceptron;

public class Network {
	
	private NetworkSchematic schematic;
	private Layer[] layers;
	
	private Random rand;
	
	public Network(NetworkSchematic schematic) {
		if(schematic.isFinished()||schematic.finish()) {
			this.schematic = schematic;
			layers = new Layer[schematic.getLayerCount()];
			layers[0] = new InputLayer(schematic.getInputLayerHeight(), this);
			for(int i = 1; i < layers.length-1; i++) {
				layers[i] = new HiddenLayer(schematic.getHiddenLayerHeights()[i-1],layers[i-1],this);
			}
			layers[layers.length-1] = new OutputLayer(schematic.getOutputLayerHeight(), layers[layers.length-2], this);
			rand = new Random();
		}
	}
	
	public Random getRand() {
		return rand;
	}
	
	public float[] execute(float... inputs) {
		((InputLayer) layers[0]).setInputs(inputs);
		for(int i = 1; i < layers.length; i++) {
			((OperableLayer) layers[i]).compute();
		}
		float[] output = new float[layers[layers.length-1].getPerceptrons().length];
		int perceptronIndex = 0;
		for(IPerceptron p : layers[layers.length-1].getPerceptrons()) {
			output[perceptronIndex] = ((OperablePerceptron) p).read();
			perceptronIndex++;
		}
		return output;
	}
	
	public void randomize(float randomLow, float randomHigh) {
		for(int i = 1; i < layers.length; i++) {
			((OperableLayer) layers[i]).randomize(randomLow, randomHigh);
		}
	}
	
	public Network breed(Network other) {
		for(int i = 1; i < this.layers.length; i++) {
			((OperableLayer) this.layers[i]).breed(((OperableLayer) other.layers[i]));
		}
		return this;
	}
	
	public Network mutate(float mutateChance, float randomLow, float randomHigh) {
		for(int i = 1; i < this.layers.length; i++) {
			((OperableLayer) this.layers[i]).mutate(mutateChance, randomLow, randomHigh);
		}
		return this;
	}
	
	public Network copy() {
		Network copy = new Network(schematic);
		for(int i = 1; i < this.layers.length-1; i++) {
			copy.layers[i] = ((OperableLayer) this.layers[i]).copy(copy.layers[i-1], copy);
		}
		copy.layers[copy.layers.length-1] = ((OperableLayer) this.layers[this.layers.length-1]).copy(copy.layers[this.layers.length-2], copy);
		return copy;
	}
	
	public NetworkSchematic getSchematic() {
		return schematic;
	}

}
