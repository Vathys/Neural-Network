package network.main;

import java.math.BigDecimal;

public class NeuronLayer {
	
	private int numberOfOutputs;
	private int numberOfInputs;
	
	private NeuralStatus layerStatus;
	private BigDecimal learningRate;
	
	private Neuron[] layerNeurons;
	private Neuron[] output;
	
	private BigDecimal[] gamma;
	private BigDecimal[] error;
	
	public NeuronLayer(int numberOfInputs, int numberOfOutputs, NeuralStatus layerStatus, BigDecimal learningRate){
		this.numberOfOutputs = numberOfOutputs;
		this.numberOfInputs = numberOfInputs;
		this.layerStatus = layerStatus;
		this.learningRate = learningRate;
		
		layerNeurons = new Neuron[numberOfInputs];
		output = new Neuron[numberOfOutputs];
		
		gamma = new BigDecimal[numberOfOutputs];
		error = new BigDecimal[numberOfOutputs];
		
		for(int i = 0; i < numberOfInputs; i++){
			layerNeurons[i] = new Neuron(numberOfOutputs, layerStatus, learningRate);
		}
		for(int i = 0; i < numberOfOutputs; i++){
			output[i] = new Neuron(0, NeuralStatus.Unknown, learningRate);
			gamma[i] = BigDecimal.ZERO;
		}
	}
	
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
	
	public NeuronLayer(Neuron[] neurons){
		layerNeurons = new Neuron[neurons.length];
		for(int i = 0; i < neurons.length; i++){
			layerNeurons[i] = new Neuron(neurons[i]);
		}
	}
	
	public NeuronLayer(NeuronLayer neuronLayer){
		this.numberOfOutputs = neuronLayer.numberOfOutputs;
		this.layerNeurons = neuronLayer.layerNeurons;
		this.output = neuronLayer.output;
		this.layerStatus = neuronLayer.layerStatus;
		this.gamma = neuronLayer.gamma;
		this.error = neuronLayer.error;
		this.learningRate = neuronLayer.learningRate;
	}

	public Neuron[] getLayerNeurons(){
		return layerNeurons;
	}
	
	public Neuron[] getOutput(){
		return output;
	}

	public BigDecimal[] getGamma(){
		return gamma;
	}
	
	public BigDecimal[] getError(){
		return error;
	}
	
	public NeuralStatus getStatus(){
		return layerStatus;
	}
	
	public Neuron[] FeedForward(Neuron[] inputs){
		for(int i = 0; i < numberOfInputs; i++){
			layerNeurons[i].setNeuron(inputs[i].getNeuron());
		}
		for(int i = 0; i < output.length; i++){
			output[i].setNeuron(BigDecimal.ZERO);
			for(int j = 0; j < layerNeurons.length; j++){
				output[i].addToNeuron(layerNeurons[j].FeedForward(i));
			}
			output[i].tanHNeuron();
			//System.out.println(output[i]);
		}
		return output;
	}
	
	public void mutate(float chance){
		for(int i = 0; i < layerNeurons.length; i++){
			layerNeurons[i].mutate(chance);
		}
	}
	
	public BigDecimal tanHDer(BigDecimal input){
		return input.multiply(input).subtract(BigDecimal.ONE);
	}
	
	public void backPropInitial(BigDecimal[] expected){
		for(int i = 0; i < numberOfOutputs; i++){
			error[i] = output[i].getNeuron().subtract(expected[i]);
			//System.out.println("Error " + i + " : " + error[i].toString());
		}
		for(int i = 0; i < numberOfOutputs; i++){
			gamma[i] = error[i].multiply(tanHDer(output[i].getNeuron()));
			//System.out.println("Gamma " + i + " : " + gamma[i].toString());
		}
		for(int i = 0; i < layerNeurons.length; i++){
			//System.out.println("Neuron Before " + i + " : " + layerNeurons[i]);
			layerNeurons[i].initWeightDelta(gamma);
			layerNeurons[i].updateWeights();
			//System.out.println("Neuron After " + i + " : " + layerNeurons[i]);
		}
	}
	
	public void backPropHidden(NeuronLayer forwardLayer){
		for(int i = 0; i < forwardLayer.layerNeurons.length; i++){
			for(int j = 0; j < forwardLayer.gamma.length; j++){
				//System.out.println("Before Gamma " + i + " : " + gamma[i]);
				gamma[i] = gamma[i].add(forwardLayer.gamma[j].multiply(forwardLayer.layerNeurons[i].getNeuron()));
				//System.out.println(forwardLayer.gamma[j].multiply(forwardLayer.layerNeurons[i].getNeuron()));
				//System.out.println("After Gamma " + i + " : " + gamma[i]);
				//System.out.println(forwardLayer.gamma[j].multiply(forwardLayer.layerNeurons[i].getNeuron()));
			}
			gamma[i] = tanHDer(gamma[i]);
		}
		for(int i = 0; i < layerNeurons.length; i++){
			layerNeurons[i].initWeightDelta(gamma);
			layerNeurons[i].updateWeights();
		}
	}
	
	public String toString(){
		String toString = "Neurons: [";
		for(int i = 0; i < layerNeurons.length - 1; i++){
			toString += layerNeurons[i] + ", \t\t";
		}
		toString += layerNeurons[layerNeurons.length - 1] + "]";
		return toString;
	}
}
