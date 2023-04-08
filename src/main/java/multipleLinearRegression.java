import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class multipleLinearRegression {
	private double[][] X; // input features
    private double[] y; // output values
    private double[] theta; // model parameters

    private double alpha; // learning rate
    private int iterations; // number of iterations for gradient descent

    public multipleLinearRegression(double[][] X, double[] y) {
        this.X = X;
        this.y = y;
        this.alpha = 0.000057;
        this.iterations = 930;
        this.theta = new double[X[0].length]; // initialize model parameters to 0
        initializeTheta();
    }
    
    private void initializeTheta() {
        Random rand = new Random();
        for (int i = 0; i < theta.length; i++) {
            theta[i] = rand.nextDouble();
        }
    }
    
    private double hypothesis(double[] x)
    {
        double prediction = 0;
        for (int i = 0; i < theta.length; i++) 
        {
            prediction += theta[i] * x[i];
        }
        return prediction;
    }
    
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
    
    
    public double percentAccuracy()
    {
    	double sum = 0;
    	for(int i=0; i<X.length; i++)
    	{
    		double predicted = hypothesis(X[i]);
    		double reallity = y[i];
    		
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
    
    public double predictPrice(double[] x)
    {
        double prediction = 0;
        for (int i = 0; i < theta.length; i++) 
        {
            prediction += theta[i] * x[i];
        }
        return prediction*10000;
    }
    
    public String predictCarModel(double[] x, Map<Double, String> map)
    {
        double prediction = x[0];
        for (int i = 1; i < theta.length; i++) 
        {
            prediction -= theta[i] * x[i];
        }
        prediction = prediction/theta[0];
        
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
