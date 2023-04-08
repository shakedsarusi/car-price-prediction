import java.util.HashMap;
import java.util.Map;

public class Dictionaries {
	
	private Map<String, Double> fuel;
	private Map<String, Double> seller_type;
	private Map<String, Double> transmission;
	private Map<String, Double> owner;
	private Map<String, Double> Headers;
	private Map<String, Double> name;
	
	public Dictionaries() {

		 this.fuel = new HashMap<String, Double>();
		 fuel.put("Petrol", 0.0);
		 fuel.put("Diesel", 1.0);
		 fuel.put("CNG", 2.0);
		 fuel.put("LPG", 3.0);
		 
		 
		 
		 this.seller_type = new HashMap<String, Double>();
		 seller_type.put("Individual", 0.0);
		 seller_type.put("Dealer", 1.0);
		 
		 this.transmission = new HashMap<String, Double>();
		 transmission.put("Manual", 0.0);
		 transmission.put("Automatic", 1.0);
		 
		 this.owner = new HashMap<String, Double>();
		 owner.put("First Owner", 0.0);
		 owner.put("Second Owner", 1.0);
		 owner.put("Third Owner", 2.0);
		 owner.put("Fourth & Above Owner",3.0);
		 
		 this.Headers = new HashMap<String, Double>();
		 Headers.put("name", 0.0);
		 Headers.put("year", 1.0);
		 Headers.put("selling_price", 2.0);
		 Headers.put("km_driven", 3.0);
		 Headers.put("fuel", 4.0);
		 Headers.put("seller_type", 5.0);
		 Headers.put("transmission", 6.0);
		 Headers.put("owner", 7.0);
	}
	
	public void build_name_dictionary(Float[][] DataSet)
	{
		this.name = new HashMap<String, Double>();
		
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

	public Map<String, Double> getName() {
		return name;
	}
}