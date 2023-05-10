import java.util.HashMap;
import java.util.Map;

public class carEncoderDecoder 
{
    
    private Map<String, Double> carToFloat;//A dictionary whose key is the name of the car and value is its numeric value
    private Map<Double, String> floatToCar;//A dictionary whose key is the numerical value of the car and value is the model of the car
    private Double nextDouble;// Marks the next entry in the dictionary
    
    
    //init function
    public carEncoderDecoder() 
    {
        this.carToFloat = new HashMap<String, Double>();
        this.floatToCar = new HashMap<Double, String>();
        this.nextDouble = 0.0;
    }
    
    
    //The function receives a car name, converts it to a number and 
    //puts it in the dictionaries carToFloat and floatToCar
    public Double encode(String carName)
    {
        if (!carToFloat.containsKey(carName))//check if the car is already in the dictionaries
        {
        	carToFloat.put(carName, nextDouble);
        	floatToCar.put(nextDouble, carName);
            nextDouble++;
        }
        return carToFloat.get(carName);
    }
    
    
    //The function accepts the numeric value of the car and returns the model of the car
    public String decode(int encodedCar)
    {
        if (!floatToCar.containsKey(encodedCar))//check if the car is in the dictionaries
        {
            throw new IllegalArgumentException("Invalid encoded car: " + encodedCar);
        }
        return floatToCar.get(encodedCar);
    }
    

	public Map<String, Double> getCarToFloat() {
		return carToFloat;
	}

	public Map<Double, String> getFloatToCar() {
		return floatToCar;
	}
	
	
}