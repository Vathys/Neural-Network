package network.main;

public class Main {

	public static void main(String[] args) {
		int[] layers = {3, 3};
		float[] inputs = {1f, 1f, 1f};
		NeuralNetwork n = new NeuralNetwork(layers);
		float[] output = n.FeedForward(inputs);
		System.out.println(output);
		
		for(int i = 0; i < 10; i++){
			n.Mutate();
			output = n.FeedForward(output);
			System.out.println(output);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
