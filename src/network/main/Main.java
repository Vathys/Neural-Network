package network.main;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		int[] layers = {3, 4, 3};
		Neuron[] inputs = new Neuron[layers[0]];
		
		for(int i = 0; i < inputs.length; i++){
			inputs[i] = new Neuron(1f, NeuralStatus.Input);
		}
		/*
		 * Simple Test to check Neural Code is working properly.
		 * Prints out 20 iterations of a Feed Forward after a Mutation where there is a 50 percentage chance of a mutation
		 * */
		
		NeuralNetwork n = new NeuralNetwork(layers);
		NeuronLayer output = new NeuronLayer(n.FeedForward(inputs));
		System.out.println(output.toString());
		Thread.sleep(1000);
		for(int i = 0; i < 20; i++){
			n.mutate(.5f); //enter the chance of mutation in decimal
			output = new NeuronLayer(n.FeedForward(inputs));
			System.out.println(output.toString());
			Thread.sleep(1000);
		}
	}
}
