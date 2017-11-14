package network.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Random;
import java.io.PrintWriter;

public class Neuron {
	private BigDecimal learningRate;

	private BigDecimal neuron;

	private int numberOfConnectedNeurons;
	private Random r;

	private NeuralStatus status;
	private BigDecimal[] weight;

	private BigDecimal[] weightDelta;

	/**
	 * Constructor
	 * 
	 * @param neuron
	 *            the value of neurons
	 */
	public Neuron(BigDecimal neuron) {
		this.neuron = neuron;

		r = new Random();
	}

	/**
	 * Constructor
	 * 
	 * @param neuron
	 *            the value of neuron
	 * @param numberOfConnectedNeurons
	 *            the number of connected neurons in the second layer
	 * @param status
	 *            the status (eg. input, hidden, output)
	 * @param learningRate
	 *            the learning rate
	 */
	public Neuron(BigDecimal neuron, int numberOfConnectedNeurons, NeuralStatus status, BigDecimal learningRate) {
		this.neuron = neuron;
		this.status = status;
		this.learningRate = learningRate;

		r = new Random();

		this.numberOfConnectedNeurons = numberOfConnectedNeurons;
		weight = new BigDecimal[numberOfConnectedNeurons];
		weightDelta = new BigDecimal[numberOfConnectedNeurons];

		if (status == NeuralStatus.Input || status == NeuralStatus.Hidden) {
			for (int i = 0; i < weight.length; i++) {
				weight[i] = new BigDecimal(String.valueOf(r.nextFloat() - .5f));
				weightDelta[i] = BigDecimal.ONE;
			}
		} else {
			weight = null;
			weightDelta = null;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param neuron
	 *            the value of neurons
	 * @param status
	 *            the status (eg. input, hidden, output)
	 */
	public Neuron(BigDecimal neuron, NeuralStatus status) {
		this.neuron = neuron;
		this.status = status;

		r = new Random();
	}

	/**
	 * Constructor
	 * 
	 * @param neuron
	 *            the value of neurons
	 * @param status
	 *            the status (eg. input, hidden, output)
	 * @param learningRate
	 *            the learning rate
	 */
	public Neuron(BigDecimal neuron, NeuralStatus status, BigDecimal learningRate) {
		this.neuron = neuron;
		this.status = status;
		this.learningRate = learningRate;

		r = new Random();
	}

	/**
	 * Constructor
	 * 
	 * @param numberOfConnectedNeurons
	 *            the number of connected neurons in the second layer
	 * @param status
	 *            the status (eg. input, hidden, output)
	 * @param learningRate
	 *            the learning rate
	 */
	public Neuron(int numberOfConnectedNeurons, NeuralStatus status, BigDecimal learningRate) {
		this.status = status;
		this.learningRate = learningRate;

		r = new Random();

		this.numberOfConnectedNeurons = numberOfConnectedNeurons;
		weight = new BigDecimal[numberOfConnectedNeurons];
		weightDelta = new BigDecimal[numberOfConnectedNeurons];

		if (status == NeuralStatus.Input || status == NeuralStatus.Hidden) {
			for (int i = 0; i < weight.length; i++) {
				weight[i] = new BigDecimal(String.valueOf(r.nextFloat() - .5f));
				weightDelta[i] = BigDecimal.ONE;
			}
		} else {
			weight = null;
			weightDelta = null;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param neuron
	 *            existing Neuron class to copy
	 */
	public Neuron(Neuron neuron) {
		this.neuron = neuron.neuron;
		this.status = neuron.status;
		this.numberOfConnectedNeurons = neuron.numberOfConnectedNeurons;
		this.weight = neuron.weight;
		this.weightDelta = neuron.weightDelta;
		this.learningRate = neuron.learningRate;

		r = new Random();
	}

	/**
	 * Update Function 
	 * 	adds the given BigDecimal to the neuron
	 * 
	 * @param addition
	 */
	public void addToNeuron(BigDecimal addition) {
		neuron = neuron.add(addition, MathContext.DECIMAL64);
	}

	/**
	 * FeedForward
	 * 
	 * @param indexOfOutputNeuron
	 *            the index of the output neuron that the neuron must feed
	 *            itself forward to
	 * 
	 * @return neuron * weight[indexOfOutputNeuron]
	 */
	public BigDecimal FeedForward(int indexOfOutputNeuron) {
		//System.out.println(weight[indexOfOutputNeuron]);
		return neuron.multiply(weight[indexOfOutputNeuron], MathContext.DECIMAL64);
	}

	/**
	 * Getter Function
	 * 
	 * @return neuron
	 */
	public BigDecimal getNeuron() {
		return neuron;
	}

	/**
	 * Initializing Function 
	 * Initializes the weightDelta array by calculating
	 * the weightDetla from an array of gamma.
	 * 
	 * @param gamma
	 *            array of gamma to calculate weightDelta
	 */
	public void initWeightDelta(BigDecimal[] gamma) {
		for (int i = 0; i < numberOfConnectedNeurons; i++) {
			//System.out.println("Neuron : " + neuron);
			weightDelta[i] = neuron.multiply(gamma[i], MathContext.DECIMAL64);
			//System.out.println("weightDelta : " + weightDelta[i]);
		}
	}

	/**
	 * Mutates 
	 * mutates all the weights on the basis of the given chance param
	 * 
	 * Types of Mutations 1. weight * -1 2. new weight 3. increase by a percent
	 * 4. decrease by a percent
	 * 
	 * @param chance
	 *            chance of mutation (given in decimal from 0 <-> 1)
	 */
	public void mutate(float chance) {
		float rn = r.nextFloat() * 1000;
		float num = (chance / 4) * 1000;
		for (int i = 0; i < weight.length; i++) {
			if (rn <= num) {
				weight[i].negate();
			} else if (rn <= num * 2) {
				weight[i] = new BigDecimal(String.valueOf(r.nextFloat() - .5f));
			} else if (rn <= num * 3) {
				BigDecimal factor = new BigDecimal(String.valueOf(r.nextFloat() + 1f));
				weight[i].multiply(factor, MathContext.DECIMAL64);
			} else if (rn <= num * 4) {
				BigDecimal factor = new BigDecimal(String.valueOf(r.nextFloat()));
				weight[i].multiply(factor, MathContext.DECIMAL64);
			}
		}
	}

	/**
	 * Setter Function 
	 * Set learning rate
	 * 
	 * @param learningRate
	 */
	public void setLearningRate(BigDecimal learningRate) {
		this.learningRate = learningRate;
	}

	/**
	 * Setter Function
	 * 
	 * @param neuron
	 */
	public void setNeuron(BigDecimal neuron) {
		this.neuron = neuron;
	}

	/**
	 * Setter Function Set status
	 * 
	 * @param status
	 */
	public void setStatus(NeuralStatus status) {
		this.status = status;
	}

	/**
	 * Activation Function 
	 * applies the activation function of TanH on the itself
	 */
	public void tanHNeuron() {
		neuron = BigDecimal.valueOf(Math.tanh(neuron.doubleValue()));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String toString = String.valueOf(neuron);
		return toString;
	}

	/**
	 * Update Function 
	 * updates weights by using weightDelta and learning rate
	 * 
	 * Weight sub(i) = weight sub(i) - (weightDelta sub(i) * learningRate)
	 */
	public void updateWeights() {
		for (int i = 0; i < weight.length; i++) {
			weight[i] = weight[i].subtract(weightDelta[i].multiply(learningRate), MathContext.DECIMAL64);
			//System.out.println("weight : " + weight[i]);
		}
	}
	
	public void CSVwriter(int number) throws FileNotFoundException, IOException {

		String NEW_LINE_SEPARATOR = "/n";
		String COMMA_DELIMITER = ",";

		Object[] FILE_HEADER = { "Weight Number", "Weights" };
		String filename = "Neuron" + number + ".csv";
		// File csv = new File("filename");
		// csv.createNewFile();
		ArrayList<String> objects = new ArrayList<String>();

		for (int i = 0; i < weight.length; i++) {
			BigDecimal f = weight[i];
			objects.add(f.toString());
		}

		FileWriter fileWriter = null;

		// try {
		PrintWriter pw = new PrintWriter(new File(filename));
		// fileWriter = new FileWriter(filename);
		pw.append("Weight Number");
		pw.append(",");
		pw.append("Weights");
		System.out.println(filename);
		pw.append("\n");

		// for (int i = 0; i < weight.length; i++) {
		// Float f = weight[i];
		pw.append("test");

		pw.append(",");

		pw.append("Test2");

		pw.append("\n");

		// }

		System.out.println("CSV file was created successfully !!!");
		pw.close();

		// } catch (Exception e) {

		// System.out.println("Error in CsvFileWriter !!!");

		// e.printStackTrace();

		// } finally {

		// try {

		// fileWriter.flush();

		// fileWriter.close();

		// } catch (IOException e) {

		// System.out.println("Error while flushing/closing fileWriter !!!");

		// e.printStackTrace();

		// }

		// }

	}
}
