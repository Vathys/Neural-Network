package network.main;

public class Main {

	public static void main(String[] args) {
		int[] layers = {4, 4, 4, 3, 3};
		float[] inputs = {1f, 1f, 1f, 1f};
		
		/*
		 * Simple Test to check Neural Code is working properly.
		 * Prints out 20 iterations of a Feed Forward after a Mutation where there is a 50 percentage chance of a mutation
		 * */
		
		NeuralNetwork n = new NeuralNetwork(layers);
		float[] output = n.FeedForward(inputs);
		for(int j = 0; j < output.length; j++){
			System.out.print(output[j] + " \t");
		}
		System.out.println();
		for(int i = 0; i < 20; i++){
			n.Mutate(.5f); //enter the chance of mutation in decimal
			output = n.FeedForward(output);
			for(int j = 0; j < output.length; j++){
				System.out.print(output[j] + " \t");
			}
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
