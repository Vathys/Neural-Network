package network.main;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.io.FileWriter;


public class Neuron {
	private float neuron;
	/* wait can i edit this at all*/
	private NeuralStatus status;
	
	private int numberOfConnectedNeurons;
	private float learningRate;
	
	private float[] weight;
	private float[] weightDelta;
	
	private Random r;
	
	public Neuron(float neuron, int numberOfConnectedNeurons, NeuralStatus status, float learningRate){
		this.neuron = neuron;
		this.status = status;
		this.learningRate = learningRate;
		
		r = new Random();
		
		this.numberOfConnectedNeurons = numberOfConnectedNeurons;
		weight = new float[numberOfConnectedNeurons];
		weightDelta = new float[numberOfConnectedNeurons];
		
		if(status == NeuralStatus.Input || status == NeuralStatus.Hidden){
			for(int i = 0; i < weight.length; i++){
				weight[i] = r.nextFloat() - .5f;
			}
		}
		else{
			weight = null;
			weightDelta = null;
		}
	}

	public Neuron(int numberOfConnectedNeurons, NeuralStatus status, float learningRate){
		this.status = status;
		this.learningRate = learningRate;
		
		r = new Random();
		
		this.numberOfConnectedNeurons = numberOfConnectedNeurons;
		weight = new float[numberOfConnectedNeurons];
		weightDelta = new float[numberOfConnectedNeurons];
		
		if(status == NeuralStatus.Input || status == NeuralStatus.Hidden){
			for(int i = 0; i < weight.length; i++){
				weight[i] = r.nextFloat() - .5f;
			}
		}
		else{
			weight = null;
			weightDelta = null;
		}
	}
	
	public Neuron(float neuron, NeuralStatus status, float learningRate){
		this.neuron = neuron;
		this.status = status;
		this.learningRate = learningRate;
		
		r = new Random();
	}
	
	public Neuron(float neuron, NeuralStatus status){
		this.neuron = neuron;
		this.status = status;
		
		r = new Random();
	}
	
	public Neuron(float neuron){
		this.neuron = neuron;
		
		r = new Random();
	}
	
	public Neuron(Neuron neuron){
		this.neuron = neuron.neuron;
		this.status = neuron.status;
		this.numberOfConnectedNeurons = neuron.numberOfConnectedNeurons;
		this.weight = neuron.weight;
		this.weightDelta = neuron.weightDelta;
		this.learningRate = neuron.learningRate;
		
		r = new Random();
	}
	
	public void initWeights(int numberOfConnectedNeurons){
		this.numberOfConnectedNeurons = numberOfConnectedNeurons;
		weight = new float[numberOfConnectedNeurons];
		weightDelta = new float[numberOfConnectedNeurons];
		
		if(status == NeuralStatus.Input || status == NeuralStatus.Hidden){
			for(int i = 0; i < weight.length; i++){
				weight[i] = (r.nextFloat() * 2) - 1f;
			}
		}
		else{
			weight = null;
			weightDelta = null;
		}
	}

	public void initWeightDelta(float[] gamma){
		for(int i = 0; i < numberOfConnectedNeurons; i++){
			weightDelta[i] = neuron * gamma[i];
		}
	}

	public void updateStatus(NeuralStatus status){
		this.status = status;
	}
	
	public void updateLearningRate(float learningRate){
		this.learningRate = learningRate;
	}
	
	public void updateWeights(){
		for(int i = 0; i < weight.length; i++){
			weight[i] -= weightDelta[i] * learningRate;
		}
	}
	
	public void addFloatToNeuron(float update){
		neuron += update;
	}
	
	public void tanHNeuron(){
		neuron = (float) Math.tanh(neuron);
	}
	
	public float getNeuron(){
		return neuron;
	}
	
	public void setNeuron(float neuron){
		this.neuron = neuron;
	}
	
	public float FeedForward(int indexOfOutputNeuron){
		return neuron * weight[indexOfOutputNeuron];
	}

	public void mutate(float chance){
		float rn = r.nextFloat() * 1000;
		float num = (chance / 4) * 1000;
		for(int i = 0; i < weight.length; i++){
			if(rn <= num){
				weight[i] *= -1;
			}
			else if(rn <= num * 2){
				weight[i] = r.nextFloat() - .5f;
			}
			else if(rn <= num * 3){
				float factor = r.nextFloat() + 1f;
				weight[i] *= factor;
			}
			else if(rn <= num * 4){
				float factor = r.nextFloat();
				weight[i] *= factor;
			}
		}
	}
	
	public void CSVwriter(int number){
		String NEW_LINE_SEPARATOR = "/n";
		String COMMA_DELIMITER = ",";
		
		Object[] FILE_HEADER = { "Weight Number", "Weights"};
		String filename = "Neuron" + number + ".csv";
		ArrayList<String> objects = new ArrayList<String>();
		for(int i = 0; i < weight.length; i++){
			Float f = weight[i];
			objects.add(f.toString());
		}
		
        FileWriter fileWriter = null;
        
        try {
        	fileWriter = new FileWriter(filename);
        	fileWriter.append(FILE_HEADER.toString());
        	fileWriter.append(NEW_LINE_SEPARATOR);
        	  	
        	for (int i = 0; i < weight.length; i++) {
        		Float f = weight[i];
        		
        		fileWriter.append(String.valueOf(i));
        		fileWriter.append(COMMA_DELIMITER);
        	    fileWriter.append(String.valueOf(f));
        	    fileWriter.append(NEW_LINE_SEPARATOR);
        	}
        	
        	System.out.println("CSV file was created successfully !!!");
        } catch (Exception e) {
        	System.out.println("Error in CsvFileWriter !!!");
        	e.printStackTrace();
        } finally {
        	try {
        		fileWriter.flush();
        	    fileWriter.close();
        	} catch (Exception e) {
        		System.out.println("Error while flushing/closing fileWriter !!!");
        	    e.printStackTrace();
        	}
        }	
	}
        	
	public String toString(){
		String toString = String.valueOf(neuron);
		return toString;
	}
}
        
