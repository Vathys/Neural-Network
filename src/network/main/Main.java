package network.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

public class Main {
	
	public static BigDecimal testFunc(BigDecimal input1, BigDecimal input2){
		BigDecimal result = BigDecimal.ONE;
		
		result = input1.add(BigDecimal.valueOf(Math.sqrt(5))).divide(input2); 
		
		return result;
	}
	
	public static BigDecimal reduce(BigDecimal input){
		BigDecimal result = BigDecimal.ONE;
		
		result = BigDecimal.valueOf((2 * Math.atan(input.doubleValue()) / Math.PI));
		
		return result;
	}
	
	public static BigDecimal inverseReduce(BigDecimal input){
		BigDecimal result = BigDecimal.ONE;
		
		result = BigDecimal.valueOf(Math.tan(input.doubleValue() * Math.PI) / 2);
		
		return result;
	}
	
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
		int[] layers = {2, 3, 1};
		Neuron[] inputs = new Neuron[layers[0]];
		Neuron[] dinputs = new Neuron[layers[0]];
		BigDecimal[] expected = new BigDecimal[layers[layers.length - 1]];
		BigDecimal[] dexpected = new BigDecimal[layers[layers.length - 1]];

		double learningRate = .05;
		FeedForwardNeuralNetwork n = new FeedForwardNeuralNetwork(layers, BigDecimal.valueOf(learningRate));
		NeuronLayer output;
		int numberOfIterations = 200;
		double stopValue = .35;

		/*
		 * Testing 
		 * f(x, y) = (x + 5^(1/2))/y
		 **/
		
		inputs[0] = new Neuron(BigDecimal.ONE);
        dinputs[0] = new Neuron(reduce(inputs[0].getNeuron()));
        //inputs[0].CSVwriter(0);
        inputs[1] = new Neuron(BigDecimal.ONE.add(BigDecimal.ONE));
        dinputs[1] = new Neuron(reduce(inputs[1].getNeuron()));
        expected[0] = testFunc(inputs[0].getNeuron(), inputs[1].getNeuron());
		dexpected[0] = reduce(expected[0]);
        // inputs[1].CSVwriter(1);
		

		int j = 1;
		int numberOfGoodResults = 0;
		while(true){
			long startTime = System.nanoTime();
			n = new FeedForwardNeuralNetwork(layers, BigDecimal.valueOf(learningRate));
			double averageError = 1;
			while(Math.abs(averageError) > stopValue){
				n.mutate(1f);
				n.FeedForward(dinputs);
				BigDecimal error = n.getError(dexpected);
				averageError = error.doubleValue();
				//System.out.println(averageError);
			}
			for(int i = 1; i <= numberOfIterations; i++){
				
			    n.FeedForward(dinputs);
			   
				n.backProp(dexpected);
			    
			}
			System.out.println("Trial: " + j);
			
			output = new NeuronLayer(n.getOutputLayer());
			System.out.println((output.getLayerNeurons()[0].getNeuron()) + " : "+ dexpected[0]);
			if(n.getError(dexpected).floatValue() < .01f){
				numberOfGoodResults++;
			}
			System.out.println("Good Results: " + numberOfGoodResults);
			long endTime = System.nanoTime();
			System.out.println("Time: " + (endTime - startTime) / 1000000000);
			j++;
			Thread.sleep(300);
		}
	}

	/**
	 * Test 1
	for(int i = 1; i < 100; i++){
		System.out.println("Trial: " + i);
		for(int j = 0; j < inputs.length; j++){
			inputs[j] = new Neuron(r.nextFloat());
		}
		n.FeedForward(inputs);
		expected[0] = (float) ((inputs[0].getNeuron() + Math.sqrt(5.0)) / inputs[1].getNeuron());
		n.backProp(expected);
		output = new NeuronLayer(n.getOutputLayer());
		System.out.println(output + " : "+ expected[0]);
	}
	*/
	
	/**
	 * Test 2
	for(int i = 1; i <= numberOfIterations; i++){
	inputs[0] = new Neuron(BigDecimal.ONE);
	inputs[1] = new Neuron(BigDecimal.ONE.add(BigDecimal.ONE));
	dinputs[0] = new Neuron(reduce(inputs[0].getNeuron()));
	dinputs[1] = new Neuron(reduce(inputs[1].getNeuron()));

	System.out.println("Trial: " + i);
	
    n.FeedForward(dinputs);
	output = new NeuronLayer(n.getOutputLayer());
    
	expected[0] = testFunc(inputs[0].getNeuron(), inputs[1].getNeuron());
	dexpected[0] = reduce(expected[0]);
    
	n.backProp(dexpected);
    //inputs[0].CSVwriter(0);
    //inputs[1].CSVwriter(1);
    
	System.out.println((output.getLayerNeurons()[0].getNeuron()) + " : "+ dexpected[0]);
	//System.out.println(inverseReduce(dexpected[0]));
	System.out.println("-------------------------------------------------------------");

	}
	*/
}
