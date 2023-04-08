import java.util.HashMap;
import java.util.Map;

public class carEncoderDecoder {
    
    private Map<String, Double> carToFloat;
    private Map<Double, String> floatToCar;
    private Double nextDouble;
    
    public carEncoderDecoder() {
        this.carToFloat = new HashMap<String, Double>();
        this.floatToCar = new HashMap<Double, String>();
        this.nextDouble = 0.0;
    }
    
    public Double encode(String carName) {
        if (!carToFloat.containsKey(carName)) {
        	carToFloat.put(carName, nextDouble);
        	floatToCar.put(nextDouble, carName);
            nextDouble++;
        }
        return carToFloat.get(carName);
    }
    
    public String decode(int encodedCar) {
        if (!floatToCar.containsKey(encodedCar)) {
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