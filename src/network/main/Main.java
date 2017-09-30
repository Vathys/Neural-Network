package network.main;

import java.util.Random;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		int[] layers = {2, 3, 3, 2};
		Neuron[] inputs = new Neuron[layers[0]];
		float[] expected = new float[layers[layers.length - 1]];
		
		Random r = new Random();
		NeuralNetwork n = new NeuralNetwork(layers, .033f);
		NeuronLayer output;
	}
}
