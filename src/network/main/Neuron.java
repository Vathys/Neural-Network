package network.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;


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
				weightDelta[i] = BigDecimal.ONE;
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
				weightDelta[i] = BigDecimal.ONE;
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
		// you need to set the size of weight in here, as well as all the rest of the Constructors that you use. 
		
		weight = new BigDecimal[numberOfConnectedNeurons]; // maybe not the size, but a test case
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
				System.out.println(weight[i]);
			}
		}
		else{
			weight = null;
			weightDelta = null;
		}
	}

	public void initWeightDelta(BigDecimal[] gamma){
		for(int i = 0; i < numberOfConnectedNeurons; i++){
			//System.out.println("Before Delta " + i + " : " + weightDelta[i]);
			//System.out.println("Gamma " + i + " : " + gamma[i]);
			//System.out.println("Neuron " + i + " : " + neuron);
			weightDelta[i] = neuron.multiply(gamma[i]);
			//System.out.println("Times Gamma " + i + " : " + weightDelta[i]);
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
			//System.out.println("Before Change " + i + " : " + weight[i]);
			//System.out.println(weightDelta[i].multiply(learningRate));
			weight[i] = weight[i].subtract(weightDelta[i].multiply(learningRate));
			//System.out.println("After Change " + i + " : " + weight[i]);
		}
	}
	
	public void addToNeuron(BigDecimal update){
		neuron = neuron.add(update);
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
		//System.out.println(weight[indexOfOutputNeuron]);
		this.neuron = neuron.multiply(weight[indexOfOutputNeuron]);
		return this.neuron;
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
    
	public void CSVwriter(int number) throws FileNotFoundException,  IOException{

		String NEW_LINE_SEPARATOR = "/n";
		String COMMA_DELIMITER = ",";
		
		Object[] FILE_HEADER = { "Weight Number", "Weights"};
		String filename = "Neuron" + number + ".csv";
		//File csv = new File("filename");
		//csv.createNewFile();
		ArrayList<String> objects = new ArrayList<String>();

		for(int i = 0; i < weight.length; i++){
			BigDecimal f = weight[i];
			objects.add(f.toString());
		}

        FileWriter fileWriter = null;
        
        //try {
        				PrintWriter pw = new PrintWriter(new File(filename));
        	            //fileWriter = new FileWriter(filename);
        	            pw.append("Weight Number");
        	            pw.append(",");
        	            pw.append("Weights");
        	            System.out.println(filename);
        	            pw.append("\n");
        	  	

        	            //for (int i = 0; i < weight.length; i++) {
        	//Float f = weight[i];
        	                pw.append("test");
        	
        	                pw.append(",");
       
        	                pw.append("Test2");
        	
        	                pw.append("\n");
        	
        	            //}
        	
        	            System.out.println("CSV file was created successfully !!!");
        	            pw.close();
        	
        	       // } catch (Exception e) {
        	
        	            //System.out.println("Error in CsvFileWriter !!!");
        
        	            //e.printStackTrace();
        	
        	        //} finally {
        	
        	            //try {
        	
        	                //fileWriter.flush();
        	
        	               // fileWriter.close();
        	
        	            //} catch (IOException e) {
        	
        	               // System.out.println("Error while flushing/closing fileWriter !!!");
        	
        	               // e.printStackTrace();
        	
        	          //  }
        	
        	       // }
        	
        	    }
        	
	public String toString(){
		String toString = String.valueOf(neuron);
		return toString;
	}
}
        
