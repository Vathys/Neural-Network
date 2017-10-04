package network.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class Main {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		int[] layers = {2, 25, 25, 1};
		Neuron[] inputs = new Neuron[layers[0]];
		float[] expected = new float[layers[layers.length - 1]];
		
		Random r = new Random();
		NeuralNetwork n = new NeuralNetwork(layers, 1f);
		NeuronLayer output;
		/*
		 * Testing 
		 * f(x, y) = (x + 5^(1/2))/y
		 **/
		
		inputs[0] = new Neuron(1f);
		inputs[0].CSVwriter(0);
		inputs[1] = new Neuron(2f);
		inputs[1].CSVwriter(1);
		for(int i = 1; i < 100; i++){
			System.out.println("Trial: " + i);
			n.FeedForward(inputs);
			expected[0] = (float) ((inputs[0].getNeuron() + Math.sqrt(5.0)) / inputs[1].getNeuron());
			n.backProp(expected);
			inputs[0].CSVwriter(0);
			inputs[1].CSVwriter(1);
			output = new NeuronLayer(n.getOutputLayer());
			System.out.println(output + " : "+ expected[0]);
		}
		/*
		for(int i = 1; i < 100; i++){
			System.out.println("Trial: " + i);
			for(int j = 0; j < inputs.length; j++){
				inputs[j] = new Neuron(r.nextFloat());
			}
			n.FeedForward(inputs);
			expected[0] = (float) ((inputs[0].getNeuron() + Math.sqrt(5.0)) / inputs[1].getNeuron());
			n.backProp(expected);
			output = new NeuronLayer(n.getOutputLayer());
			System.out.println(output + " : "+ expected[0]);
		}
		*/
	}
}
