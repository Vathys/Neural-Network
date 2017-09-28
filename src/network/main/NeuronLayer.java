package network.main;

public class NeuronLayer {
	
	private int numberOfOutputs;
	
	private NeuralStatus layerStatus;
	
	private Neuron[] input;
	private Neuron[] output;
	
	private float[] gamma;
	private float[] error;
	
	public NeuronLayer(int numberOfOutputs, NeuralStatus layerStatus){
		this.numberOfOutputs = numberOfOutputs;
		this.layerStatus = layerStatus;
		
		output = new Neuron[numberOfOutputs];
		
		gamma = new float[numberOfOutputs];
		error = new float[numberOfOutputs];
	}
	
	public NeuronLayer(Neuron[] neurons){
		input = new Neuron[neurons.length];
		for(int i = 0; i < neurons.length; i++){
			input[i] = new Neuron(neurons[i]);
		}
	}
	
	public NeuronLayer(NeuronLayer neuronLayer){
		this.numberOfOutputs = neuronLayer.numberOfOutputs;
		this.input = neuronLayer.input;
		this.output = neuronLayer.output;
		this.layerStatus = neuronLayer.layerStatus;
		this.gamma = neuronLayer.gamma;
		this.error = neuronLayer.error;
	}

	public Neuron[] getInput(){
		return input;
	}
	
	public Neuron[] getOutput(){
		return output;
	}

	public Neuron[] FeedForward(Neuron[] inputs){
		input = new Neuron[inputs.length];
		for(int i = 0; i < inputs.length; i++){
			input[i] = new Neuron(inputs[i].getNeuron(), layerStatus);
			input[i].initializeWeights(numberOfOutputs);
		}
		for(int i = 0; i < output.length; i++){
			output[i] = new Neuron(0, NeuralStatus.Unknown);
			for(int j = 0; j < input.length; j++){
				output[i].addFloatToNeuron(input[j].FeedForward(i));
			}
			output[i].tanHNeuron();
		}
		return output;
	}
	
	public void mutate(float chance){
		for(int i = 0; i < input.length; i++){
			input[i].mutate(chance);
		}
	}
	
	public String toString(){
		String toString = "Neurons: [";
		for(int i = 0; i < input.length - 1; i++){
			toString += input[i].toString() + ", \t\t";
		}
		toString += input[input.length - 1].toString() + "]";
		return toString;
	}
}