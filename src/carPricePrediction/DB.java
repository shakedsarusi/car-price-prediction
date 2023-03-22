package carPricePrediction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class DB {
	private int rows;
	private int cols;
	private String path;
	private String[][] stringData;
	public double[][] doubleData;
	public double[][] cleanDoubleData;
	public double[] Y_Set;
	public double[][] X_Set;
	
	
	public DB(int rows, int cols, String path) {
		
		this.rows = rows;
		this.cols = cols;
		this.path = path;
		this.stringData = new String[this.rows][this.cols];
		
		 String line;
	        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {

	            int i = 0;
	            while ((line = br.readLine()) != null) {
	                String[] fields = line.split(","); // split the line into an array of strings using comma as the delimiter
	                for (int j = 0; j < fields.length; j++) {
	                    this.stringData[i][j] = fields[j];
	                }
	                i++;
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }		
	}
	
	
	public void convertDataToDouble()
	{
		this.doubleData = new double[this.rows-1][this.cols];
		this.Y_Set = new double[this.rows-1];
		Dictionaries DC = new Dictionaries();
		carEncoderDecoder CED = new carEncoderDecoder();
		double[] copyYSet = new double[this.rows-1];
		int j; //represented the feature
		
		j=1;// converts year feature - 1
		for(int i=0; i<this.rows-1; i++)
		{
			
			this.doubleData[i][j] = 2020 - Double.parseDouble(this.stringData[i+1][j]);
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
	}
	
	public void printStringData() 
	{
	    // Determine the maximum length of each column
	    int[] maxColumnLengths = new int[this.stringData[0].length];
	    for (int i = 0; i < this.stringData.length; i++) {
	        for (int j = 0; j < this.stringData[i].length; j++) {
	            if (this.stringData[i][j].length() > maxColumnLengths[j]) {
	                maxColumnLengths[j] = this.stringData[i][j].length();
	            }
	        }
	    }
	    
	    // Print the table
	    for (int i = 0; i < this.stringData.length; i++) {
	        for (int j = 0; j < this.stringData[i].length; j++) {
	            System.out.printf("%-" + (maxColumnLengths[j] + 2) + "s", this.stringData[i][j]);
	        }
	        System.out.println();
	    }
	}
	
	
	
	public void cleanData()
	{
		int count=0;// count illegal rows
		ArrayList<Integer> illegalRowsIndex = new ArrayList<>();
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
		this.cleanDoubleData = new double[this.rows-count-1][this.cols];
		int t=0;
		for(int i=0; i<this.rows-1; i++)
		{
			for(int j=0; j<this.cols; j++)
			{
				if(!illegalRowsIndex.contains(i))
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
	public void printDoubleData(double[][] data) {
		 int[] maxColumnLengths = new int[data[0].length];
		    for (int i = 0; i < data.length; i++) {
		        for (int j = 0; j < data[i].length; j++) {
		            String valueStr = Double.toString(data[i][j]);
		            int valueLength = valueStr.length();
		            if (valueLength > maxColumnLengths[j]) {
		                maxColumnLengths[j] = valueLength;
		            }
		        }
		    }
		    
		    // Print the table
		    for (int i = 0; i < data.length; i++) {
		        for (int j = 0; j < data[i].length; j++) {
		            System.out.printf("%-" + (maxColumnLengths[j] + 2) + "s", data[i][j]);
		        }
		        System.out.println();
		    }
	}
	
	public void descricsStats()
	{
		int numRows = this.cleanDoubleData.length;
	    int numCols = this.cleanDoubleData[0].length;
	    Dictionaries DC = new Dictionaries();
	    PearsonsCorrelation corr = new PearsonsCorrelation();
	    double[] currentCol = new double[this.cleanDoubleData.length];
	    
	    for (int j = 0; j < numCols; j++) {
	        DescriptiveStatistics colStats = new DescriptiveStatistics();
	        for (int i = 0; i < numRows; i++) {
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
	        
	        double correlation = corr.correlation(currentCol, this.Y_Set);
	        System.out.println("Correlation between columns " + this.stringData[0][j] + " and selling_price: " + correlation);
	        System.out.println();
	        
	        
	    }
	
	    
	}
	
	public void buildYSet()
    {
		this.Y_Set = new double[this.cleanDoubleData.length];
    	for(int i=0; i<this.cleanDoubleData.length; i++)
    	{
    		this.Y_Set[i] = this.cleanDoubleData[i][2];
    	}
    }
	
	
	public void buildXSet()
	{
		this.X_Set = new double[this.cleanDoubleData.length][this.cols-1];
		int t=0;
		for(int i=0; i<this.cols-1; i++)
    	{
			if(t==2)
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


	public double[] getY_Set() {
		return Y_Set;
	}


	public double[][] getX_Set() {
		return X_Set;
	}
	
	
	
	
}
