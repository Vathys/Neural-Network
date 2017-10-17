package network.main;


import java.io.IOException;
import java.math.BigDecimal;

public class NeuronLayer {
	
	private BigDecimal[] error;
	private BigDecimal[] gamma;
	
	private Neuron[] layerNeurons;
	private NeuralStatus layerStatus;
	
	private BigDecimal learningRate;
	private int numberOfLayerNeurons;
	
	
	//The output array should be deleted to reduce memory usage. 
	//The array will be accessed directly from the layer after it. 
	private int numberOfOutputs;
	private Neuron[] output;
	
	/**
	 * Constructor 
	 *  
	 * @param numberOfLayerNeurons
	 * 	the number of neurons in current layer
	 * @param numberOfOutputs
	 * 	the number of neuron
	 * @param layerStatus
	 * 	the status (eg. input, hidden, output)
	 * @param learningRate
	 * 	the learning rate
	 */
	public NeuronLayer(int numberOfLayerNeurons, int numberOfOutputs, NeuralStatus layerStatus, BigDecimal learningRate){
		this.numberOfOutputs = numberOfOutputs;
		this.numberOfLayerNeurons = numberOfLayerNeurons;
		this.layerStatus = layerStatus;
		this.learningRate = learningRate;
		
		layerNeurons = new Neuron[numberOfLayerNeurons];
		output = new Neuron[numberOfOutputs];
		
		gamma = new BigDecimal[numberOfOutputs];
		error = new BigDecimal[numberOfOutputs];
		
		for(int i = 0; i < numberOfLayerNeurons; i++){
			layerNeurons[i] = new Neuron(numberOfOutputs, layerStatus, learningRate);
		}
		for(int i = 0; i < numberOfOutputs; i++){
			output[i] = new Neuron(0, NeuralStatus.Unknown, learningRate);
			gamma[i] = BigDecimal.ZERO;
		}
	}
	
	/**
	 * Constructor 
	 * 
	 * @param numberOfOutputs
	 * 	the number of neuron
	 * @param layerStatus
	 * 	the status (eg. input, hidden, output)
	 * @param learningRate
	 * 	the learning rate
	 */
	public NeuronLayer(int numberOfOutputs, NeuralStatus layerStatus, BigDecimal learningRate){
		this.numberOfOutputs = numberOfOutputs;
		this.layerStatus = layerStatus;
		this.learningRate = learningRate;
		
		output = new Neuron[numberOfOutputs];
		
		gamma = new BigDecimal[numberOfOutputs];
		error = new BigDecimal[numberOfOutputs];
		
		for(int i = 0; i < numberOfOutputs; i++){
			output[i] = new Neuron(0, NeuralStatus.Unknown, learningRate);
		}
	}
	
	/**
	 * Constructor
	 * 
	 * @param neurons
	 * 	creates a neuron layer from an array of neurons by copying each neuron into layerNeuron
	 * @throws IOException
	 */
	public NeuronLayer(Neuron[] neurons) throws IOException{
		layerNeurons = new Neuron[neurons.length];
		for(int i = 0; i < neurons.length; i++){
			layerNeurons[i] = new Neuron(neurons[i]);
			layerNeurons[i].CSVwriter(i);
		}
	}
	
	/**
	 * Constructor
	 * 
	 * @param neuronLayer
	 * 	existing neuronLayer to copy
	 */
	public NeuronLayer(NeuronLayer neuronLayer){
		this.numberOfOutputs = neuronLayer.numberOfOutputs;
		this.layerNeurons = neuronLayer.layerNeurons;
		this.output = neuronLayer.output;
		this.layerStatus = neuronLayer.layerStatus;
		this.gamma = neuronLayer.gamma;
		this.error = neuronLayer.error;
		this.learningRate = neuronLayer.learningRate;
	}

	/**
	 * Back Propagation
	 * 	Back Propagation for every other layer
	 * 
	 * 	Back Propagation steps
	 * 		1. calculate gamma for weights
	 * 			gamma of neuron = tanHDer(summation (gamma of each weight of connected neuron * connected neuron))
	 * 		2. initialize WeightDelta array in layerNeuron by passing gamma
	 * @param forwardLayer
	 * 	neuronLayer that comes after this layer
	 */
	public void backPropHidden(NeuronLayer forwardLayer){
		for(int i = 0; i < forwardLayer.layerNeurons.length; i++){
			for(int j = 0; j < forwardLayer.gamma.length; j++){
				gamma[i] = gamma[i].add(forwardLayer.gamma[j].multiply(forwardLayer.layerNeurons[i].getNeuron()));
			}
			gamma[i] = tanHDer(gamma[i]);
		}
		for(int i = 0; i < layerNeurons.length; i++){
			layerNeurons[i].initWeightDelta(gamma);
			layerNeurons[i].updateWeights();
		}
	}
	
	/**
	 * Initial Back Propagation
	 * 	Back Propagation for the last hidden layer
	 * 		
	 * 	Back Propagation steps
	 * 		1. calculate error array
	 * 		2. calculate gamma array
	 * 			error * TanHDer(output)
	 * 		3. initialize weightDelta in layerNeuron by passing gamma
	 * 
	 * @param expected
	 * 	array of expected values for the inputs
	 */
	public void backPropInitial(BigDecimal[] expected){
		for(int i = 0; i < numberOfOutputs; i++){
			error[i] = output[i].getNeuron().subtract(expected[i]);		
		}
		for(int i = 0; i < numberOfOutputs; i++){
			gamma[i] = error[i].multiply(tanHDer(output[i].getNeuron()));
		}
		for(int i = 0; i < layerNeurons.length; i++){
			layerNeurons[i].initWeightDelta(gamma);
			layerNeurons[i].updateWeights();
		}
	}

	/**
	 * FeedForward
	 * @param inputs
	 * 	neurons to be put into layerNeurons
	 * @return output array
	 * 	each element in the output array is the summation of all the neurons in the layerNeuron * the corresponding weight
	 */
	public Neuron[] FeedForward(Neuron[] inputs){
		for(int i = 0; i < numberOfLayerNeurons; i++){
			layerNeurons[i].setNeuron(inputs[i].getNeuron());
		}
		for(int i = 0; i < output.length; i++){
			output[i].setNeuron(BigDecimal.ZERO);
			for(int j = 0; j < layerNeurons.length; j++){
				output[i].addToNeuron(layerNeurons[j].FeedForward(i));
			}
			output[i].tanHNeuron();
		}
		return output;
	}
	
	/**
	 * Update Function
	 * 	calculate the error from the expected values
	 * 
	 * @param expected
	 * 	the expected values of the output
	 */
	public BigDecimal calculateError(BigDecimal[] expected){
		BigDecimal err = new BigDecimal(0);
		for(int i = 0; i < numberOfOutputs; i++){
			error[i] = output[i].getNeuron().subtract(expected[i]);
			err = err.add(BigDecimal.valueOf(Math.pow(error[i].doubleValue(), 2)));
		}
		err = BigDecimal.valueOf(Math.sqrt(err.doubleValue()));
		return err;
	}
	
	/**
	 * Getter Function
	 * 
	 * @return error
	 */
	public BigDecimal[] getError(){
		return error;
	}
	
	/**
	 * Getter Function
	 * 
	 * @return gamma
	 */
	public BigDecimal[] getGamma(){
		return gamma;
	}
	
	/**
	 * Getter Function
	 * 
	 * @return layerNeuron
	 */
	public Neuron[] getLayerNeurons(){
		return layerNeurons;
	}
	
	/**
	 * Getter Function
	 * 
	 * @return output
	 */
	public Neuron[] getOutput(){
		return output;
	}
	
	/**
	 * Getter Function
	 * 
	 * @return layerStatus
	 */
	public NeuralStatus getStatus(){
		return layerStatus;
	}
	
	/**
	 * Mutate
	 * 
	 * activates the mutate function for each of the neurons in the neuronLayer
	 * @param chance
	 */
	public void mutate(float chance){
		for(int i = 0; i < layerNeurons.length; i++){
			layerNeurons[i].mutate(chance);
		}
	}
	
	/**
	 * Derivative of Activation Function
	 * 
	 * d(tanh(x)) / dx
	 * 
	 * @param input
	 * @return f'(input)
	 * 	where f(x) = tanh(x)
	 */
	public BigDecimal tanHDer(BigDecimal input){
		return input.multiply(input).subtract(BigDecimal.ONE);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String toString = "Neurons: [";
		for(int i = 0; i < layerNeurons.length - 1; i++){
			toString += layerNeurons[i] + ", \t\t";
		}
		toString += layerNeurons[layerNeurons.length - 1] + "]";
		return toString;
	}
}
