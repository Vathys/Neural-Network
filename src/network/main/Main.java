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
	
	public static void main(String[] args) throws InterruptedException {
		int[] layers = {2, 4, 4, 1};
		Neuron[] inputs = new Neuron[layers[0]];
		Neuron[] dinputs = new Neuron[layers[0]];
		BigDecimal[] expected = new BigDecimal[layers[layers.length - 1]];
		BigDecimal[] dexpected = new BigDecimal[layers[layers.length - 1]];
		double learningRate = .05;
		Random r = new Random();
		NeuralNetwork n = new NeuralNetwork(layers, BigDecimal.valueOf(learningRate));
		NeuronLayer output;
		int numberOfIterations = 100;
        
		/*
		 * Testing 
		 * f(x, y) = (x + 5^(1/2))/y
		 **/
        
        inputs[0] = new Neuron(1f);
        dinputs[0] = new Neuron(reduce(inputs[0].getNeuron()));
        inputs[0].CSVwriter(0);
        inputs[1] = new Neuron(2f);
        dinputs[1] = new Neuron(reduce(inputs[1].getNeuron()));
        inputs[1].CSVwriter(1);
		long startTime = System.nanoTime();
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
            inputs[0].CSVwriter(0);
            inputs[1].CSVwriter(1);
            
			System.out.println((output.getLayerNeurons()[0].getNeuron()) + " : "+ dexpected[0]);
			//System.out.println(inverseReduce(dexpected[0]));
			System.out.println("-------------------------------------------------------------");
		}
		long endTime = System.nanoTime();
		System.out.println(endTime - startTime);
		/*
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
	}
}
