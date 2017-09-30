package network.main;

import java.util.Random;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		int[] layers = {2, 24, 24, 1};
		Neuron[] inputs = new Neuron[layers[0]];
		float[] expected = new float[layers[layers.length - 1]];
		/*
		 * OR
		 * 
		 * 0 || 0 -> 0
		 * 1 || 0 -> 1
		 * 0 || 1 -> 1
		 * 1 || 1 -> 1
		 * */
		
		Random r = new Random();
		NeuralNetwork n = new NeuralNetwork(layers, .033f);
		NeuronLayer output;
		for(int i = 0; i < 100; i++){
			for(int j = 0; j < inputs.length; j++){
				float f = Math.round(r.nextFloat());
				inputs[j] = new Neuron(f, NeuralStatus.Input);
			}
			if(inputs[0].getNeuron() == inputs[1].getNeuron() && inputs[0].getNeuron() == 0){
				expected[0] = 0f;
			}else{
				expected[0] = 1f;
			}
			output = new NeuronLayer(n.FeedForward(inputs));
			System.out.println(output.toString());
			n.backProp(expected);
		}
		
		/*
		NeuralNetwork n = new NeuralNetwork(layers, 0.033f);
		NeuronLayer output = new NeuronLayer(n.FeedForward(inputs));
		System.out.println(output.toString());
		Thread.sleep(1000);
		for(int i = 0; i < 20; i++){
			n.mutate(.5f); //enter the chance of mutation in decimal
			output = new NeuronLayer(n.FeedForward(inputs));
			System.out.println(output.toString());
			Thread.sleep(1000);
		}
		*/
	}
}
