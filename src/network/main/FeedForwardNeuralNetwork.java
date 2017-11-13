package network.main;

import java.math.BigDecimal;

public class FeedForwardNeuralNetwork {
	private int[] layers;
	private BigDecimal learningRate;
	private NeuronLayer[] neuronLayer;
	
	/**
	 * Constructor
	 * 
	 * @param layers	
	 *  array containing the number of layers(length of array) and the size of each layer (nth element)
	 * @param learningRate
	 * 	the learning rate
	 */
	public FeedForwardNeuralNetwork(int[] layers, BigDecimal learningRate){
		this.layers =  new int[layers.length];
		this.learningRate = learningRate;
		for(int i = 0; i < layers.length; i++){
			this.layers[i] = layers[i];
		}
		initLayer();
	}
	
	/**
	 * Back Prop
	 * initial Back Propagation function is called for the second to last layer because it contains both the output and the previous layer
	 * normal Back Propagation is called for all the other layers
	 * 
	 * @param expected
	 * 	expected output values for input
	 */
	public void backProp(BigDecimal[] expected){
		neuronLayer[neuronLayer.length - 2].backPropInitial(expected);
		for(int i = neuronLayer.length - 3; i >= 0; i--){
			neuronLayer[i].backPropHidden(neuronLayer[i + 1]);
		}
	}

	/**
	 * FeedForward
	 * 
	 * @param inputs
	 * 	input of neurons
	 * @return the output neuronLayer
	 */
	public NeuronLayer FeedForward(Neuron[] inputs){
		neuronLayer[0].FeedForward(inputs);
		
		for(int i = 1; i < neuronLayer.length; i++){
			neuronLayer[i].FeedForward(neuronLayer[i - 1].getOutput());
		}

		return neuronLayer[neuronLayer.length - 1];
	}
	
	/**
	 * Getter Function
	 * 
	 * @return last Neuron Layer
	 */
	public NeuronLayer getOutputLayer(){
		return neuronLayer[neuronLayer.length - 1];
	}
	
	/**
	 * Getter Function
	 * 	gets the error that the output of the particular input has given
	 * 
	 * @param expected
	 * 	the expected values of the output
	 * @return
	 */
	public BigDecimal getError(BigDecimal[] expected){
		return neuronLayer[neuronLayer.length - 2].calculateError(expected);
	}
		
	/**
	 * Initializing Function
	 * 
	 * initializes Neuron Layers from information give in constructor
	 */
	private void initLayer(){
		neuronLayer = new NeuronLayer[layers.length];
		neuronLayer[0] = new NeuronLayer(layers[0], layers[1], NeuralStatus.Input, learningRate);
		neuronLayer[layers.length - 1] = new NeuronLayer(layers[layers.length - 1], 0, NeuralStatus.Output, learningRate);
		
		for(int i = 1; i < layers.length - 1; i++){
			 neuronLayer[i] = new NeuronLayer(layers[i], layers[i + 1], NeuralStatus.Hidden, learningRate);
		}
	}

	/**
	 * Mutate
	 * calls the mutate function for each neuronLayer
	 * 
	 * @param chance
	 */
	public void mutate(float chance){
		for(int i = 0; i < neuronLayer.length - 1; i++){
			neuronLayer[i].mutate(chance);
		}
	}
}