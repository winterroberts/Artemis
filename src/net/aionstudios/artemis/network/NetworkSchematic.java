package net.aionstudios.artemis.network;

import net.aionstudios.artemis.activation.IActivationFunction;
import net.aionstudios.artemis.exception.LayerHeightException;
import net.aionstudios.artemis.exception.SchematicModificationException;

public class NetworkSchematic {

	private IActivationFunction activationFunction;
	
	private int inputLayerHeight;
	private int[] hiddenLayerHeights;
	private int outputLayerHeight;
	
	private boolean finished;
	
	public NetworkSchematic(IActivationFunction activationFunction, int inputLayerHeight, int outputLayerHeight, int... hiddenLayerHeights) {
		finished = false;
		if(inputLayerHeight<1) {
			throw new LayerHeightException("Input layer cannot be empty!");
		}
		if(outputLayerHeight<1) {
			throw new LayerHeightException("Output layer cannot be empty!");
		}
		this.inputLayerHeight = inputLayerHeight;
		this.hiddenLayerHeights = hiddenLayerHeights;
		this.outputLayerHeight = outputLayerHeight;
		this.activationFunction = activationFunction;
	}

	public boolean finish() {
		if(!finished) {
			finished = true;
			return true;
		}
		return false;
	}

	public IActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(IActivationFunction activationFunction) {
		if(!finished) {
			this.activationFunction = activationFunction;
		}
		throw new SchematicModificationException("Attempted to modify network settings for a finalized schematic!");
	}

	public int getInputLayerHeight() {
		return inputLayerHeight;
	}

	public void setInputLayerHeight(int inputLayerHeight) {
		if(!finished) {
			if(inputLayerHeight<1) {
				throw new LayerHeightException("Input layer cannot be empty!");
			}
			this.inputLayerHeight = inputLayerHeight;
		}
		throw new SchematicModificationException("Attempted to modify network settings for a finalized schematic!");
	}

	public int[] getHiddenLayerHeights() {
		return hiddenLayerHeights;
	}

	public void setHiddenLayerHeights(int... hiddenLayerHeights) {
		if(!finished) {
			this.hiddenLayerHeights = hiddenLayerHeights;
		}
		throw new SchematicModificationException("Attempted to modify network settings for a finalized schematic!");
	}

	public int getOutputLayerHeight() {
		return outputLayerHeight;
	}

	public void setOutputHeight(int outputLayerHeight) {
		if(!finished) {
			if(outputLayerHeight<1) {
				throw new LayerHeightException("Output layer cannot be empty!");
			}
			this.outputLayerHeight = outputLayerHeight;
		}
		throw new SchematicModificationException("Attempted to modify network settings for a finalized schematic!");
	}

	public boolean isFinished() {
		return finished;
	}
	
	public int getLayerCount() {
		return hiddenLayerHeights!=null?hiddenLayerHeights.length+2:2;
	}
	
}
