
import java.util.Set;

import io.javalin.Javalin;
import io.javalin.http.Header;


public class main {
	

	public static void main(String[] args) {
		
		int ROWS = 4341;//number of rows in the data
		int COLS = 8;// number of columns in the data(features)
		DB db = new DB(ROWS, COLS,"C:\\Users\\Tal Goldbach\\Desktop\\CAR DETAILS FROM CAR DEKHO.csv" ); //create DB instance
		db.convertDataToDouble();//convert data from String to Double
		db.cleanData();// clean the data from illegal rows
		db.buildYSet();// build Y Set contain only price column
		db.buildXSet();// build X Set contain all columns except of price column
		
		multipleLinearRegression mlr = new multipleLinearRegression(db.getX_Set(),db.getY_Set()); // create multipleLinearRegression instance
		mlr.gradientDescent(); // activate the model
		System.out.println(mlr.percentAccuracy()); //Calculate percent accuracy of the model
		System.out.println();
		
		// Create a new Javalin instance
        Javalin app = Javalin.create(config -> 
        {
            config.plugins.enableCors(cors ->
            {
                cors.add(it ->
                {
                    it.anyHost();
                });
            });
        });

        

        // Set up a simple GET route
        app.get("/", ctx -> {
            ctx.result("Hello, world!");
        });
        
        // Set up a GET route to get input from client and return the car price prediction
        app.get("/price", ctx -> 
        {
            String CarName = ctx.queryParam("CarName");
            String Year = ctx.queryParam("Year");
            String Kilometer = ctx.queryParam("Kilometer");
            String Fuel = ctx.queryParam("Fuel");
            String SellerType = ctx.queryParam("SellerType");
            String Transmisson = ctx.queryParam("Transmisson");
            String Owner = ctx.queryParam("Owner");
            String [] input = {CarName, Year, Kilometer, Fuel, SellerType, Transmisson, Owner};
            double result = mlr.predictPrice(DB.convertInputToDoublePrice(input)); // Predicting the price of the vehicle according to the data entered by the user
            int resultInteger = (int)result;
            ctx.result(Integer.toString(resultInteger));//Returning the predicted car price to the user
          
        });	
        
        // Set up a GET route to get input from client and return the car model prediction
        app.get("/carName", ctx ->
        {
            String Price = ctx.queryParam("Price");
            String Year = ctx.queryParam("Year");
            String Kilometer = ctx.queryParam("Kilometer");
            String Fuel = ctx.queryParam("Fuel");
            String SellerType = ctx.queryParam("SellerType");
            String Transmisson = ctx.queryParam("Transmisson");
            String Owner = ctx.queryParam("Owner");
            String [] input = {Price, Year, Kilometer, Fuel, SellerType, Transmisson, Owner};
            String result = mlr.predictCarModel(DB.convertInputToDoubleCarModel(input), DB.getFloatToCar());// Predicting the car model of the according to the data entered by the user
            ctx.result(result);//Returning the predicted car model to the user
        });
        
        // Set up a GET route to get send frontend the list of cars
        app.get("/listOfCars", ctx -> 
        {
        	
        	Set<String> listOfCars = DB.getCarToFloat().keySet();
        	 // allocate memory for string array
            String[] array = new String[listOfCars.size()];
     
            // copy elements from set to string array
            int i = 0;
            for (String s: listOfCars) {
                array[i++] = s;
            }
            
            String str = String.join(",", array);
            ctx.result(str);
            
            System.out.println();
        });
        

        // Start the server on port 8000
        app.start(8000);
    }
}


