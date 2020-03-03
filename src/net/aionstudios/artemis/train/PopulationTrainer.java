package net.aionstudios.artemis.train;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

import net.aionstudios.artemis.data.DataEntry;
import net.aionstudios.artemis.data.DataSet;
import net.aionstudios.artemis.error.IErrorFunction;
import net.aionstudios.artemis.exception.NetworkParameterException;
import net.aionstudios.artemis.network.Network;
import net.aionstudios.artemis.population.Population;

public class PopulationTrainer {
	
	public static float train(Population population, DataSet trainSet, IErrorFunction errorFunction, float trainSubset, boolean threadded) {
		if(trainSubset<=0f||trainSubset>1.0f) {
			throw new NetworkParameterException("Training subset must be between 0.0 and 1.0");
		}
		if(population.getSchematic().getInputLayerHeight()!=trainSet.getInputs()) {
			throw new NetworkParameterException("Network expected "+population.getSchematic().getInputLayerHeight()+" inputs, but received "+trainSet.getInputs()+"!");
		}
		if(population.getSchematic().getOutputLayerHeight()!=trainSet.getOutputs()) {
			throw new NetworkParameterException("Network expected "+population.getSchematic().getOutputLayerHeight()+" inputs, but received "+trainSet.getOutputs()+"!");
		}
		List<Float> errs = new LinkedList<>();
		List<Network> errNets = new LinkedList<>();
		if(threadded) {
			int cores = Runtime.getRuntime().availableProcessors();
			int perThread = (int) Math.ceil((double) population.getIndividuals().length/cores);
			List<Thread> threads = new LinkedList<>();
			ReentrantLock tLock = new ReentrantLock();
			for(int t = 0; t < cores; t++) {
				final int ti = t;
				Thread r = new Thread(new Runnable(){

					@Override
					public void run() {
						for(int j = 0; j < perThread; j++) {
							int id = perThread*ti+j;
							if(id<population.getIndividuals().length) {
								float errorSum = 0f;
								Network n = population.getIndividuals()[id];
								for(int i = 0; i < trainSet.countEntries(); i++) {
									if(n.getRand().nextFloat()<trainSubset) {
										DataEntry d = trainSet.getEntry(i);
										float error = errorFunction.calculate(n.execute(d.getInputs()), d.getOutputs());
										errorSum += error;
									}
								}
								errorSum /= (float) trainSet.countEntries();
								int idx = 0;
								tLock.lock();
								while(errs.size()>idx&&errs.get(idx)<errorSum) {
									idx++;
								}
								errs.add(idx, errorSum);
								errNets.add(idx, n);
								tLock.unlock();
							}
						}
					}
						
				});
				r.setName("ArtemisTrain-"+t);
				threads.add(r);
				r.start();
			}
			for(Thread r : threads) {
				while(r.isAlive()) {
					
				}
			}
		} else {
			for(Network n : population.getIndividuals()) {
				float errorSum = 0f;
				for(int i = 0; i < trainSet.countEntries(); i++) {
					if(n.getRand().nextFloat()<trainSubset) {
						DataEntry d = trainSet.getEntry(i);
						float error = errorFunction.calculate(n.execute(d.getInputs()), d.getOutputs());
						errorSum += error;
					}
				}
				errorSum /= (float) trainSet.countEntries();
				int idx = 0;
				while(errs.size()>idx&&errs.get(idx)<errorSum) {
					idx++;
				}
				errs.add(idx, errorSum);
				errNets.add(idx, n);
			}
		}
		int retain = (int) Math.floor(population.getGenerationRetainment()*population.getIndividuals().length);
		List<Network> nextGen = errNets.subList(0, retain);
		Random r = new Random();
		while(nextGen.size()<population.getIndividuals().length) {
			int pair1 = r.nextInt(population.getIndividuals().length);
			int pair2 = r.nextInt(population.getIndividuals().length-1);
			if(pair1==pair2) {
				pair2++;
			}
			nextGen.add(population.getIndividuals()[pair1].copy().breed(population.getIndividuals()[pair2]).mutate(population.getMutationPercent(), population.getRandomLow(), population.getRandomHigh()));
		}
		for(int i = 0; i < population.getIndividuals().length; i++) {
			population.getIndividuals()[i] = nextGen.get(i);
		}
		return errs != null?errs.get(0):1.0f;
	}
	
	public static float train(Population population, DataSet trainSet, IErrorFunction errorFunction, boolean threadded) {
		return train(population, trainSet, errorFunction, 1.0f, threadded);
	}

}
