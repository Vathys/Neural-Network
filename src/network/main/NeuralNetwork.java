package network.main;

import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {
	
	private int[] layers;
	private float [][] neurons;
	private float [][][] weights;
	private Random r;
	public NeuralNetwork(int[] layers){
		this.layers =  new int[layers.length];
		for(int i = 0; i < layers.length; i++){
			this.layers[i] = layers[i];
		}
		
		r = new Random();
		
		initNeurons();
		initWeights();
	}
	
	public NeuralNetwork(NeuralNetwork copyNetwork){
		this.layers = new int[copyNetwork.layers.length];
		for(int i = 0; i < copyNetwork.layers.length; i++){
			this.layers[i] = copyNetwork.layers[i];
		}
		
		initNeurons();
		initWeights();
		copyWeights(copyNetwork.weights);
	}
	
	private void copyWeights(float[][][]copyWeight){
		for(int i = 0; i < copyWeight.length; i++){
			for(int j = 0; j < copyWeight[i].length; j++){
				for(int k = 0; k < copyWeight[i][j].length; k++){
					weights[i][j][k] = copyWeight[i][j][k];
				}
			}
		}
	}
	
	private void initNeurons(){
		ArrayList <float[]> neuronsList = new ArrayList<float[]>();
		for(int i = 0; i < layers.length; i++){
			neuronsList.add(new float[layers[i]]);
		}
		for(int i = 0; i < neuronsList.size(); i++){
			neurons[i] = neuronsList.get(i);
		}
	}
	
	private void initWeights(){
		ArrayList<float[][]> weightsList = new ArrayList<float[][]>();
		float[][] temp = null;
		for(int i = 1; i < layers.length; i++){
			ArrayList<float[]> layersWeight = new ArrayList<float[]>();
			int nIPL = layers[i-1]; // # of neurons in previous layer
			for(int j = 0; j < neurons[i].length; j++){
				float[] neuronsWeight = new float[nIPL];
				for(int k = 0; k < nIPL; k++){
					neuronsWeight[k] = r.nextFloat() - .5f;
				}
				layersWeight.add(neuronsWeight);
			}
			for(int j = 0; j < layersWeight.size(); j++){
				temp = new float[layersWeight.size()][];
				temp[j] = layersWeight.get(j);
			}
			weightsList.add(temp);
		}
		for(int i = 0; i < weightsList.size(); i++){
			weights[i] = weightsList.get(i);
		}
	}
	
	public float[] FeedForward(float[] inputs){
		
		for(int i = 0; i < inputs.length; i++){
			neurons[0][i] = inputs[i];
		}
		
		for(int i = 1; i < layers.length; i++){
			for(int j = 0; j < neurons[i].length; j++){
				double val = .25;
				for(int k = 0; k < neurons[i-1].length;  k++){
					val += weights[i-1][j][k] * neurons[i-1][j];
				}
				neurons[i][j] = (float)Math.tanh(val);
			}
		}
		return neurons[neurons.length - 1];
	}
	
	public void Mutate(){
		for(int i = 0; i < weights.length; i++){
			for (int j = 0; j < weights[i].length; j++){
				for(int k = 0; k < weights[i][j].length; k++){
					float weight = weights[i][j][k];
					float rn = r.nextFloat() * 1000;
					if(rn <= 2){
						weight *= -1;
					}
					else if(rn <= 4){
						weight = r.nextFloat() - .5f;
					}
					else if(rn <= 6){
						float factor = r.nextFloat() + 1f;
						weight *= factor;
					}
					else if(rn <= 8){
						float factor = r.nextFloat();
						weight *= factor;
					}
					weights[i][j][k] = weight;
				}
			}
		}
	}
}


