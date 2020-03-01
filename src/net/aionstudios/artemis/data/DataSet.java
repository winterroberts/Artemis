package net.aionstudios.artemis.data;

import java.util.LinkedList;
import java.util.List;

import net.aionstudios.artemis.exception.DataBoundaryException;

public class DataSet {
	
	private final List<DataEntry> entries;
	
	private final int inputs;
	private final int outputs;
	
	public DataSet(int inputs, int outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
		entries = new LinkedList<>();
	}
	
	public void makeEntry(float[] in, float[] out) {
		if(inputs!=in.length) {
			throw new DataBoundaryException("Data set expected "+inputs+" inputs, but received "+in.length+"!");
		}
		if(outputs!=out.length) {
			throw new DataBoundaryException("Data set expected "+outputs+" outputs, but received "+out.length+"!");
		}
		entries.add(new DataEntry(in,out));
	}

	public int countEntries() {
		return entries.size();
	}
	
	public DataEntry getEntry(int index) {
		return entries.get(index);
	}

	public int getInputs() {
		return inputs;
	}

	public int getOutputs() {
		return outputs;
	}

}
