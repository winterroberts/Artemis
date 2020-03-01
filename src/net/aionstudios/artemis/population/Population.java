package net.aionstudios.artemis.population;

import net.aionstudios.artemis.exception.NetworkParameterException;
import net.aionstudios.artemis.network.Network;
import net.aionstudios.artemis.network.NetworkSchematic;

public class Population {
	
	private final NetworkSchematic schematic;
	
	private final Network[] individuals;
	
	private final float randomLow;
	private final float randomHigh;
	private final float generationRetainment;
	private final float mutationPercent;
	
	public Population(NetworkSchematic schematic, int size, float randomLow, float randomHigh, float generationRetainment, float mutationPercent) {
		this.schematic = schematic;
		if(size<1) {
			throw new NetworkParameterException("Population size must be a positive integer!");
		}
		if(randomHigh<=randomLow) {
			throw new NetworkParameterException("Population random high must be larger that random low!");
		}
		if(generationRetainment<=0.0f||generationRetainment>=1.0f) {
			throw new NetworkParameterException("Generation retainment cannot exceed 0, 1.0f boundary!");
		}
		if(mutationPercent<0.0f||mutationPercent>1.0f) {
			throw new NetworkParameterException("Mutation percent cannot exceed 0, 1.0f boundary!");
		}
		this.randomLow = randomLow;
		this.randomHigh = randomHigh;
		this.generationRetainment = generationRetainment;
		this.mutationPercent = mutationPercent;
		individuals = new Network[size];
		for(int i = 0; i < size; i++) {
			individuals[i] = new Network(schematic);
			individuals[i].randomize(randomLow, randomHigh);
		}
	}
	
	public Population(NetworkSchematic schematic, int size, float randomLow, float randomHigh) {
		this(schematic, size, randomLow, randomHigh, 0.5f, 0.02f);
	}
	
	public Population(NetworkSchematic schematic, int size) {
		this(schematic, size, -1.0f, 1.0f, 0.5f, 0.02f);
	}

	public NetworkSchematic getSchematic() {
		return schematic;
	}

	public Network[] getIndividuals() {
		return individuals;
	}

	public float getRandomLow() {
		return randomLow;
	}

	public float getRandomHigh() {
		return randomHigh;
	}

	public float getGenerationRetainment() {
		return generationRetainment;
	}

	public float getMutationPercent() {
		return mutationPercent;
	}

}
