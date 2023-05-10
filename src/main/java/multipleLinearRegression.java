import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class multipleLinearRegression
{
	private double[][] X; // input features
    private double[] y; // output values
    private double[] theta; // model parameters

    private double alpha; // learning rate
    private int iterations; // number of iterations for gradient descent

    
    /**
     * Constructor for the MultipleLinearRegression class.
     * @param X The feature matrix.
     * @param y The target vector.
     */
    public multipleLinearRegression(double[][] X, double[] y)
    {
        this.X = X;
        this.y = y;
        this.alpha = 0.00006;
        this.iterations = 840;
        this.theta = new double[X[0].length]; // initialize model parameters to 0
        
    }
    
    
    /**
     * Calculates the predicted value for a given set of input features (x) using the learned model parameters (theta).
     * 
     * @param x The input features for which to predict the output value.
     * @return The predicted output value based on the learned model parameters.
     */
    private double hypothesis(double[] x)
    {
        double prediction = 0;
        for (int i = 0; i < theta.length; i++) 
        {
            prediction += theta[i] * x[i];
        }
        return prediction;
    }
    
    
    /**
     * A private helper method that calculates the gradient (derivative) of the cost function with respect to each coefficient.
     * @return The gradient vector.
     */
    private double[] computeGradient()
    {
        double[] gradient = new double[theta.length];
        for (int j = 0; j < theta.length; j++) 
        {
            double sum = 0;
            for (int i = 0; i < X.length; i++)
            {
                double error = hypothesis(X[i]) - y[i];
                sum += error * X[i][j];
            }
            gradient[j] = sum / X.length;
        }
        return gradient;
    }
    
    /**
     * A public method that performs gradient descent to minimize the cost function and determine the coefficients.
     */
    public void gradientDescent() {
        for (int i = 0; i < iterations; i++) 
        {
            double[] gradient = computeGradient();
            for (int j = 0; j < theta.length; j++) 
            {
                theta[j] -= alpha * gradient[j];
            }
        }
    }
    
    
    /**
     * A public method that calculates the percentage accuracy of the model by comparing the predicted values to the actual values.
     * @return The percentage accuracy.
     */
    public double percentAccuracy()
    {
    	double sum = 0;
    	for(int i=0; i<X.length; i++)
    	{
    		double predicted = hypothesis(X[i]);//predicted price using given set(X)
    		double reallity = y[i];// the real price of given set
    		
    		if(reallity<predicted)
    		{
    			sum += (reallity/predicted)*100;
    		}
    		else
    		{
        		sum += (predicted/reallity)*100;
    		}
    	}
 
        sum = sum/X.length;
    	return sum; 
    }
    
    
    /**
     * A public method that predicts the price of a car based on the input features.
     * @param x The input vector.
     * @return The predicted price.
     */
    public double predictPrice(double[] x)
    {
        double prediction = 0;
        for (int i = 0; i < theta.length; i++) 
        {
            prediction += theta[i] * x[i];
        }
        return prediction*10000;
    }
    
    
    /**
     * A public method that predicts the car model based on the input features.
     * @param x The input vector.
     * @param map The map of the car names and their numerical representation
     * @return The predicted car model.
     */
    public String predictCarModel(double[] x, Map<Double, String> map)
    {
        double prediction = x[0];
        for (int i = 1; i < theta.length; i++) 
        {
            prediction -= theta[i] * x[i];
        }
        prediction = prediction/theta[0];
        
        //Checks the closest key in the map to the key we predicted
        Double closestKey = null;
        double smallestDifference = Double.MAX_VALUE;
        for (Double key : map.keySet())
        {
            double difference = Math.abs(prediction - key);
            if (difference < smallestDifference)
            {
                smallestDifference = difference;
                closestKey = key;
            }
        }
        return map.get(closestKey);
    }
    
}
