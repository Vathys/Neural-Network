package network.main;

public class NeuronLayer {
	
	private int numberOfOutputs;
	private int numberOfInputs;
	
	private NeuralStatus layerStatus;
	private float learningRate;
	
	private Neuron[] layerNeurons;
	private Neuron[] output;
	
	private float[] gamma;
	private float[] error;
	
	public NeuronLayer(int numberOfInputs, int numberOfOutputs, NeuralStatus layerStatus, float learningRate){
		this.numberOfOutputs = numberOfOutputs;
		this.numberOfInputs = numberOfInputs;
		this.layerStatus = layerStatus;
		this.learningRate = learningRate;
		
		layerNeurons = new Neuron[numberOfInputs];
		output = new Neuron[numberOfOutputs];
		
		gamma = new float[numberOfOutputs];
		error = new float[numberOfOutputs];
		
		for(int i = 0; i < numberOfInputs; i++){
			layerNeurons[i] = new Neuron(numberOfOutputs, layerStatus, learningRate);
		}
	}
	
	public NeuronLayer(int numberOfOutputs, NeuralStatus layerStatus, float learningRate){
		this.numberOfOutputs = numberOfOutputs;
		this.layerStatus = layerStatus;
		this.learningRate = learningRate;
		
		output = new Neuron[numberOfOutputs];
		
		gamma = new float[numberOfOutputs];
		error = new float[numberOfOutputs];
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

	public float[] getGamma(){
		return gamma;
	}
	
	public float[] getError(){
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
			output[i] = new Neuron(0, NeuralStatus.Unknown, learningRate);
			for(int j = 0; j < layerNeurons.length; j++){
				output[i].addFloatToNeuron(layerNeurons[j].FeedForward(i));
			}
			output[i].tanHNeuron();
		}
		return output;
	}
	
	public void mutate(float chance){
		for(int i = 0; i < layerNeurons.length; i++){
			layerNeurons[i].mutate(chance);
		}
	}
	
	public float tanHDer(float input){
		return (1 - (input * input));
	}
	
	public void backPropInitial(float[] expected){
		for(int i = 0; i < numberOfOutputs; i++){
			error[i] = output[i].getNeuron() - expected[i];
		}
		for(int i = 0; i < numberOfOutputs; i++){
			gamma[i] = error[i] * tanHDer(output[i].getNeuron());
		}
		for(int i = 0; i < layerNeurons.length; i++){
			layerNeurons[i].initWeightDelta(gamma);
		}
	}
	
	public void backPropHidden(NeuronLayer forwardLayer){
		for(int i = 0; i < forwardLayer.layerNeurons.length; i++){
			gamma[i] = 0;
			for(int j = 0; j < forwardLayer.gamma.length; j++){
				gamma[i] += forwardLayer.gamma[j] * forwardLayer.layerNeurons[i].getNeuron();
			}
			gamma[i] = tanHDer(gamma[i]);
		}
		for(int i = 0; i < layerNeurons.length; i++){
			layerNeurons[i].initWeightDelta(gamma);
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