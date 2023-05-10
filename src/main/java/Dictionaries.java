import java.util.HashMap;
import java.util.Map;

public class Dictionaries 
{
	
	private Map<String, Double> fuel;//A dictionary where the key is the type of fuel and the value is its numerical value
	private Map<String, Double> seller_type;//A dictionary whose key is the type of seller and the value is its numerical value
	private Map<String, Double> transmission;//A dictionary where the key is the transmission type and the value is its numerical value
	private Map<String, Double> owner;//A dictionary where the key is some hand of the car and the value is its numerical value
	private Map<String, Double> Headers;//A dictionary whose key is features and the value is its numerical value
	
	public Dictionaries()
	{

		 this.fuel = new HashMap<String, Double>();//init fuel dictionary
		 fuel.put("Petrol", 0.0);
		 fuel.put("Diesel", 1.0);
		 fuel.put("CNG", 2.0);
		 fuel.put("LPG", 3.0);
		 
		 this.seller_type = new HashMap<String, Double>();//init seller_type dictionary
		 seller_type.put("Individual", 0.0);
		 seller_type.put("Dealer", 1.0);
		 
		 this.transmission = new HashMap<String, Double>();//init transmission dictionary
		 transmission.put("Manual", 0.0);
		 transmission.put("Automatic", 1.0);
		 
		 this.owner = new HashMap<String, Double>();//init owner dictionary
		 owner.put("First Owner", 0.0);
		 owner.put("Second Owner", 1.0);
		 owner.put("Third Owner", 2.0);
		 owner.put("Fourth And Above Owner",3.0);
		 
		 this.Headers = new HashMap<String, Double>();//init Headers dictionary
		 Headers.put("name", 0.0);
		 Headers.put("year", 1.0);
		 Headers.put("selling_price", 2.0);
		 Headers.put("km_driven", 3.0);
		 Headers.put("fuel", 4.0);
		 Headers.put("seller_type", 5.0);
		 Headers.put("transmission", 6.0);
		 Headers.put("owner", 7.0);
	}
	

	public Map<String, Double> getFuel() {
		return fuel;
	}

	public Map<String, Double> getSeller_type() {
		return seller_type;
	}

	public Map<String, Double> getTransmission() {
		return transmission;
	}

	public Map<String, Double> getOwner() {
		return owner;
	}

	public Map<String, Double> getHeaders() {
		return Headers;
	}

	
}