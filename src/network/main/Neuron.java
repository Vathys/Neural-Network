package network.main;

import java.util.Random;

public class Neuron {
	private float neuron;
	
	private NeuralStatus status;
	
	private int numberOfConnectedNeurons;
	
	private float[] weight;
	private float[] weightDelta;
	
	private Random r;
	
	public Neuron(int numberOfConnectedNeurons, NeuralStatus status){
		this.numberOfConnectedNeurons = numberOfConnectedNeurons;
		this.status = status;
		
		Random r = new Random();
		
		weight = new float[numberOfConnectedNeurons];
		weightDelta = new float[numberOfConnectedNeurons];
		
		if(status == NeuralStatus.Input || status == NeuralStatus.Hidden){
			for(int i = 0; i < weight.length; i++){
				weight[i] = r.nextFloat() - .5f;
			}
		}
		else{
			weight = null;
			weightDelta = null;
		}
		
	}
	
	public float FeedForward(int indexOfOutputNeuron){
		return neuron * weight[indexOfOutputNeuron];
	}
}
