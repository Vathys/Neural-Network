package network.main;

public class NeuralLayer {
	
	private int numberOfInputs;
	private int numberOfOutputs;
	
	private NeuralStatus layerStatus;
	
	private Neuron[] input;
	private Neuron[] output;
	private float[] gamma;
	private float[] error;
	
	public NeuralLayer(int numberOfInputs, int numberOfOutputs, NeuralStatus layerStatus){
		this.numberOfInputs = numberOfInputs;
		this.numberOfOutputs = numberOfOutputs;
		
		input = new Neuron[numberOfInputs];
		output = new Neuron[numberOfOutputs];
		
		
		
	}
}
