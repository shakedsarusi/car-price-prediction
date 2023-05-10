import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class DB 
{
	private int rows; // number of rows in the data
	private int cols; // number of columns in the data
	private String path; // The path to the file that contains the data
	private String[][] stringData; // data in String format
	private double[][] doubleData; // data in Double format
	private double[][] cleanDoubleData;// data in Double format after clean
	private double[] Y_Set; // A one-dimensional array containing only the price column
	private double[][] X_Set; // A two-dimensional array containing all columns except the price column
	public static Map<String, Double> carToFloat;// A dictionary that contains the name of the car as a key and the numerical representation of the car as a value
	public static Map<Double, String> floatToCar;// A dictionary that contains the numerical representation of the price as a key and the name of the car as the value
	
	
	//init function 
	public DB(int rows, int cols, String path) 
	{
		this.rows = rows;
		this.cols = cols;
		this.path = path;
		this.stringData = new String[this.rows][this.cols];
		this.carToFloat = new HashMap<String, Double>();
		this.floatToCar = new HashMap<Double, String>();
		
		String line;
	        try (BufferedReader br = new BufferedReader(new FileReader(this.path)))
	        {
	            int i = 0;
	            while ((line = br.readLine()) != null) 
	            {
	                String[] fields = line.split(","); // split the line into an array of strings using comma as the delimiter
	                for (int j = 0; j < fields.length; j++) 
	                {
	                    this.stringData[i][j] = fields[j];
	                }
	                i++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }		
	}
	
	
	
//	An operation that converts all the values in the database 
//	from string values to double values, the function uses the
//	dictionaries class and the carEncoderDecoder 
//	class to convert each value to its numerical representation
	public void convertDataToDouble()
	{
		this.doubleData = new double[this.rows-1][this.cols];//Initialize a 2D double array
		this.Y_Set = new double[this.rows-1];//Initialize a double array contains price column
		Dictionaries DC = new Dictionaries();//Creating an instance of Dictionaries
		carEncoderDecoder CED = new carEncoderDecoder();//Creating an instance of carEncoderDecoder
		double[] copyYSet = new double[this.rows-1];
		int j; //represented the feature
		
		j=1;// converts year feature - 1
		for(int i=0; i<this.rows-1; i++)
		{
			
			this.doubleData[i][j] = (2020 - Double.parseDouble(this.stringData[i+1][j]));
		}
		
		j=2;// converts selling price feature - 2
		for(int i=0; i<this.rows-1; i++)
		{
			
			this.doubleData[i][j] = Double.parseDouble(this.stringData[i+1][j])/10000;
			copyYSet[i] = Double.parseDouble(this.stringData[i+1][j]);
		}
		
		j=3;// converts km_driven feature - 3
		for(int i=0; i<this.rows-1; i++)
		{
			
			this.doubleData[i][j] = Double.parseDouble(this.stringData[i+1][j])/10000;
		}
		
		j=4;// converts fuel feature - 4
		for(int i=0; i<this.rows-1; i++)
		{
			if(DC.getFuel().containsKey(this.stringData[i+1][j]))
			{
				this.doubleData[i][j] = DC.getFuel().get(this.stringData[i+1][j]);
			}
			else
			{
				this.doubleData[i][j] = -999;
			}
		}
		
		j=5;// converts seller_type feature - 5
		for(int i=0; i<this.rows-1; i++)
		{
			if(DC.getSeller_type().containsKey(this.stringData[i+1][j]))
			{
				this.doubleData[i][j] = DC.getSeller_type().get(this.stringData[i+1][j]);
			}
			else
			{
				this.doubleData[i][j] = -999;
			}
		}
		
		j=6;// converts transmission feature - 6
		for(int i=0; i<this.rows-1; i++)
		{
			if(DC.getTransmission().containsKey(this.stringData[i+1][j]))
			{
				this.doubleData[i][j] = DC.getTransmission().get(this.stringData[i+1][j]);
			}
			else
			{
				this.doubleData[i][j] = -999;
			}
		}
		
		
		j=7;// converts owner feature - 7
		for(int i=0; i<this.rows-1; i++)
		{
			if(DC.getOwner().containsKey(this.stringData[i+1][j]))
			{
				this.doubleData[i][j] = DC.getOwner().get(this.stringData[i+1][j]);
			}
			else
			{
				this.doubleData[i][j] = -999;
			}
		}
		
		
		//Converts the car name to a number so that the car name with 
		//the highest price gets the highest number and the car with the 
		//lowest price gets the number 0.
		j=0;// converts car name feature - 0
		for(int i=0; i<this.rows-1; i++)
		{
			int minIndex = 0;
			double minValue = Double.MAX_VALUE;
		     for (int z = 0; z < copyYSet.length; z++)
		     {
		        if (copyYSet[z] < minValue) 
		        {
		       	   minIndex = z;
		           minValue = copyYSet[z];
		        }
		     }
		    copyYSet[minIndex] = Double.MAX_VALUE;
		    this.doubleData[minIndex][j] = CED.encode(this.stringData[minIndex+1][0])/100;
		}
		this.carToFloat = CED.getCarToFloat();
		this.floatToCar = CED.getFloatToCar();
		
	}
	
	public void printStringData() 
	{
	    // Determine the maximum length of each column
	    int[] maxColumnLengths = new int[this.stringData[0].length];
	    for (int i = 0; i < this.stringData.length; i++)
	    {
	        for (int j = 0; j < this.stringData[i].length; j++) 
	        {
	            if (this.stringData[i][j].length() > maxColumnLengths[j])
	            {
	                maxColumnLengths[j] = this.stringData[i][j].length();
	            }
	        }
	    }
	    
	    // Print the table
	    for (int i = 0; i < this.stringData.length; i++) 
	    {
	        for (int j = 0; j < this.stringData[i].length; j++) 
	        {
	            System.out.printf("%-" + (maxColumnLengths[j] + 2) + "s", this.stringData[i][j]);
	        }
	        System.out.println();
	    }
	}
	
	
	//The function clears all rows that contain errors in the data
	public void cleanData()
	{
		int count=0;// count illegal rows
		ArrayList<Integer> illegalRowsIndex = new ArrayList<>();//arrayList that contains all the indexes (rows) where there are errors in the data
		for(int i=0; i<this.rows-1; i++)
			{
				for(int j=0; j<this.cols; j++)
				{
					if(this.doubleData[i][j] == -999)
					{
						illegalRowsIndex.add(i);
						count++;
					}
					
				}
			}
		this.cleanDoubleData = new double[this.rows-count-1][this.cols];//Initializes cleanDoubleData which 
																		//contains the data after cleaning the values with the errors
		int t=0;
		for(int i=0; i<this.rows-1; i++)
		{
			for(int j=0; j<this.cols; j++)
			{
				if(!illegalRowsIndex.contains(i))//Checks whether the current row is valid or not
				{
					this.cleanDoubleData[t][j] = this.doubleData[i][j];
				}
				
			}
			if(!illegalRowsIndex.contains(i))
			{
				t++;
			}
		}
		
	}
	
	public void printDoubleData(double[][] data)
	{
		 int[] maxColumnLengths = new int[data[0].length];
		    for (int i = 0; i < data.length; i++) 
		    {
		        for (int j = 0; j < data[i].length; j++)
		        {
		            String valueStr = Double.toString(data[i][j]);
		            int valueLength = valueStr.length();
		            if (valueLength > maxColumnLengths[j]) 
		            {
		                maxColumnLengths[j] = valueLength;
		            }
		        }
		    }
		    
		    // Print the table
		    for (int i = 0; i < data.length; i++)
		    {
		        for (int j = 0; j < data[i].length; j++) 
		        {
		            System.out.printf("%-" + (maxColumnLengths[j] + 2) + "s", data[i][j]);
		        }
		        System.out.println();
		    }
	}
	
//	This function prints the statistics of each column and the
//	linear relationship of each column with the price column.
//	The statistics:
//	1. Quantity
//	2. Average
//	3. Static standard
//	4. Minimum
//	5. Maximum
//	6. The 25th percentile
//	7. The 50th percentile
//	8. The 75th percentile
	public void descricsStats()
	{
		int numRows = this.cleanDoubleData.length;
	    int numCols = this.cleanDoubleData[0].length;
	    PearsonsCorrelation corr = new PearsonsCorrelation();//Creating an instance of PearsonsCorrelation to calculate the correlation 
	    													 //between the price column and the other columns
	    double[] currentCol = new double[this.cleanDoubleData.length];//the current column
	    
	    for (int j = 0; j < numCols; j++) 
	    {
	        DescriptiveStatistics colStats = new DescriptiveStatistics();//Creating an instance of DescriptiveStatistics to calculate the statistics
	        for (int i = 0; i < numRows; i++)
	        {
	            colStats.addValue(this.cleanDoubleData[i][j]);
	            currentCol[i] = this.cleanDoubleData[i][j];
	        }
	        
	        System.out.println("Column: " + this.stringData[0][j]);
	        System.out.println("Count: " + colStats.getN());
	        System.out.println("Mean: " + colStats.getMean());
	        System.out.println("Standard deviation: " + colStats.getStandardDeviation());
	        System.out.println("Minimum: " + colStats.getMin());
	        System.out.println("Maximum: " + colStats.getMax());
	        System.out.println("25th percentile: " + colStats.getPercentile(25));
	        System.out.println("Median: " + colStats.getPercentile(50));
	        System.out.println("75th percentile: " + colStats.getPercentile(75));
	        
	        double correlation = corr.correlation(currentCol, this.Y_Set);//The correlation between the convenience column and the price column
	        System.out.println("Correlation between columns " + this.stringData[0][j] + " and selling_price: " + correlation);
	        System.out.println();
	        
	        
	    }
	
	    
	}
	
	//Normalize the database according to the following formula: x=(x-mean)/std
	//x - the current value
	//mean - the average of the column where the value x is found
	//std - static The standard of the column in which the value x is found
	public void normelizeData()
	{
		int numRows = this.cleanDoubleData.length;// num of rows
	    int numCols = this.cleanDoubleData[0].length;// num of cols
	    
		 for (int j = 0; j < numCols; j++) 
		 {
		        DescriptiveStatistics colStats = new DescriptiveStatistics();//Creating an instance of DescriptiveStatistics to calculate the mean and standard deviation
		        for (int i = 0; i < numRows; i++)
		        {
		            colStats.addValue(this.cleanDoubleData[i][j]);//current column
		        }
		        double mean = colStats.getMean();//Calculate the average of the current column
		        double std = colStats.getStandardDeviation();//Calculation of the standard deviation of the current column
		        for (int i = 0; i < numRows; i++)
		        {
		            this.cleanDoubleData[i][j] = (this.cleanDoubleData[i][j]- mean)/std;//The normalization operation
		        }  
		 }
	}
	
	
	//Building a Y_Set that contains only the price column
	public void buildYSet()
    {
		this.Y_Set = new double[this.cleanDoubleData.length];
    	for(int i=0; i<this.cleanDoubleData.length; i++)
    	{
    		this.Y_Set[i] = this.cleanDoubleData[i][2];
    	}
    }
	
	
	//Building an X_Set that contains the data except the price column
	public void buildXSet()
	{
		this.X_Set = new double[this.cleanDoubleData.length][this.cols-1];
		int t=0;
		for(int i=0; i<this.cols-1; i++)
    	{
			if(t==2)//Checks with the current column is the price column
			{
				t++;
			}
			for(int j=0; j<this.cleanDoubleData.length; j++)
	    	{
				
	    		this.X_Set[j][i] = this.cleanDoubleData[j][t];
	    	}
			t++;
    		
    	}
	}
	
	
	//This function converts the user input which is in string format to double format
	public static double[] convertInputToDoublePrice(String[] input)
	{
		Dictionaries DC = new Dictionaries();
		double[] doubleData = new double[7];
		doubleData[0] = carToFloat.get(input[0])/100;// car name
		doubleData[1] = 2020 - Double.parseDouble(input[1]); //age
		doubleData[2] = Double.parseDouble(input[2])/10000;// kilometer
		doubleData[3] = DC.getFuel().get(input[3]);// fuel
		doubleData[4] = DC.getSeller_type().get(input[4]);// seller type
		doubleData[5] = DC.getTransmission().get(input[5]);// transmission
		doubleData[6] = DC.getOwner().get(input[6]);// owner
		
		System.out.print("");
		return doubleData;
	}
	
	
	//This function converts the user input which is in string format to double format
	public static double[] convertInputToDoubleCarModel(String[] input)
	{
		Dictionaries DC = new Dictionaries();
		double[] doubleData = new double[7];
		doubleData[0] = Double.parseDouble(input[0])/10000;// price
		doubleData[1] = 2020 - Double.parseDouble(input[1]); //age
		doubleData[2] = Double.parseDouble(input[2])/10000;// kilometer
		doubleData[3] = DC.getFuel().get(input[3]);// fuel
		doubleData[4] = DC.getSeller_type().get(input[4]);// seller type
		doubleData[5] = DC.getTransmission().get(input[5]);// transmission
		doubleData[6] = DC.getOwner().get(input[6]);// owner
		
		System.out.print("");
		return doubleData;
	}
	
	
	public double[] getY_Set() {
		return Y_Set;
	}


	public double[][] getX_Set() {
		return X_Set;
	}


	public static Map<Double, String> getFloatToCar() {
		return floatToCar;
	}


	public static Map<String, Double> getCarToFloat() {
		return carToFloat;
	}
	
	
	
	
	
}