package network.main;

import java.util.Random;

public class NeuralNetwork {
	private int[] layers;
	private NeuronLayer[] neuronLayer;
	private Random r;
	
	public NeuralNetwork(int[] layers){
		this.layers =  new int[layers.length];
		for(int i = 0; i < layers.length; i++){
			this.layers[i] = layers[i];
		}
		
		r = new Random();
		initLayer();
	}
	
	private void initLayer(){
		neuronLayer = new NeuronLayer[layers.length];
		neuronLayer[0] = new NeuronLayer(layers[1], NeuralStatus.Input);
		neuronLayer[layers.length - 1] = new NeuronLayer(0, NeuralStatus.Output);
		
		for(int i = 1; i < layers.length - 1; i++){
			 neuronLayer[i] = new NeuronLayer(layers[i + 1], NeuralStatus.Hidden);
		}
	}

	public NeuronLayer FeedForward(Neuron[] inputs){
		neuronLayer[0].FeedForward(inputs);
		
		for(int i = 1; i < neuronLayer.length; i++){
			neuronLayer[i].FeedForward(neuronLayer[i - 1].getOutput());
		}
		
		return neuronLayer[neuronLayer.length - 1];
	}

	public void mutate(float chance){
		for(int i = 0; i < neuronLayer.length - 1; i++){
			neuronLayer[i].mutate(chance);
		}
	}
}