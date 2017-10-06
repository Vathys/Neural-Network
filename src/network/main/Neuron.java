package network.main;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.io.FileWriter;


public class Neuron {
	private BigDecimal neuron;
	
	private NeuralStatus status;
	
	private int numberOfConnectedNeurons;
	private BigDecimal learningRate;
	
	private BigDecimal[] weight;
	private BigDecimal[] weightDelta;
	
	private Random r;
	
	public Neuron(BigDecimal neuron, int numberOfConnectedNeurons, NeuralStatus status, BigDecimal learningRate){
		this.neuron = neuron;
		this.status = status;
		this.learningRate = learningRate;
		
		r = new Random();
		
		this.numberOfConnectedNeurons = numberOfConnectedNeurons;
		weight = new BigDecimal[numberOfConnectedNeurons];
		weightDelta = new BigDecimal[numberOfConnectedNeurons];
		
		if(status == NeuralStatus.Input || status == NeuralStatus.Hidden){
			for(int i = 0; i < weight.length; i++){
				weight[i] = new BigDecimal(String.valueOf(r.nextFloat() - .5f));
			}
		}
		else{
			weight = null;
			weightDelta = null;
		}
	}

	public Neuron(int numberOfConnectedNeurons, NeuralStatus status, BigDecimal learningRate){
		this.status = status;
		this.learningRate = learningRate;
		
		r = new Random();
		
		this.numberOfConnectedNeurons = numberOfConnectedNeurons;
		weight = new BigDecimal[numberOfConnectedNeurons];
		weightDelta = new BigDecimal[numberOfConnectedNeurons];
		
		if(status == NeuralStatus.Input || status == NeuralStatus.Hidden){
			for(int i = 0; i < weight.length; i++){
				weight[i] = new BigDecimal(String.valueOf(r.nextFloat() - .5f));
			}
		}
		else{
			weight = null;
			weightDelta = null;
		}
	}
	
	public Neuron(BigDecimal neuron, NeuralStatus status, BigDecimal learningRate){
		this.neuron = neuron;
		this.status = status;
		this.learningRate = learningRate;
		
		r = new Random();
	}
	
	public Neuron(BigDecimal neuron, NeuralStatus status){
		this.neuron = neuron;
		this.status = status;
		
		r = new Random();
	}
	
	public Neuron(BigDecimal neuron){
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
		weight = new BigDecimal[numberOfConnectedNeurons];
		weightDelta = new BigDecimal[numberOfConnectedNeurons];
		
		if(status == NeuralStatus.Input || status == NeuralStatus.Hidden){
			for(int i = 0; i < weight.length; i++){
				weight[i] = new BigDecimal(String.valueOf(r.nextFloat() - .5f));
			}
		}
		else{
			weight = null;
			weightDelta = null;
		}
	}

	public void initWeightDelta(BigDecimal[] gamma){
		for(int i = 0; i < numberOfConnectedNeurons; i++){
			weightDelta[i] = neuron.multiply(gamma[i]);
		}
	}

	public void updateStatus(NeuralStatus status){
		this.status = status;
	}
	
	public void updateLearningRate(BigDecimal learningRate){
		this.learningRate = learningRate;
	}
	
	public void updateWeights(){
		for(int i = 0; i < weight.length; i++){
			weight[i].subtract(weightDelta[i].multiply(learningRate));
		}
	}
	
	public void addToNeuron(BigDecimal update){
		neuron.toString();
		neuron.add(update);
	}
	
	public void tanHNeuron(){
		neuron = BigDecimal.valueOf(Math.tanh(neuron.doubleValue()));
	}
	
	public BigDecimal getNeuron(){
		return neuron;
	}
	
	public void setNeuron(BigDecimal neuron){
		this.neuron = neuron;
	}
	
	public BigDecimal FeedForward(int indexOfOutputNeuron){
		return neuron.multiply(weight[indexOfOutputNeuron]);
	}

	public void mutate(float chance){
		float rn = r.nextFloat() * 1000;
		float num = (chance / 4) * 1000;
		for(int i = 0; i < weight.length; i++){
			if(rn <= num){
				weight[i].negate();
			}
			else if(rn <= num * 2){
				weight[i] = new BigDecimal(String.valueOf(r.nextFloat() - .5f));
			}
			else if(rn <= num * 3){
				BigDecimal factor = new BigDecimal(String.valueOf(r.nextFloat() + 1f));
				weight[i].multiply(factor);
			}
			else if(rn <= num * 4){
				BigDecimal factor = new BigDecimal(String.valueOf(r.nextFloat()));
				weight[i].multiply(factor);
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
			BigDecimal f = weight[i];
			objects.add(f.toString());
		}
		
        FileWriter fileWriter = null;
        
        try {
        	fileWriter = new FileWriter(filename);
        	fileWriter.append(FILE_HEADER.toString());
        	fileWriter.append(NEW_LINE_SEPARATOR);
        	  	
        	for (int i = 0; i < weight.length; i++) {
        		BigDecimal f = weight[i];
        		
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
        
