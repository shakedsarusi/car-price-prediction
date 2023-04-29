
import java.util.Set;

import io.javalin.Javalin;
import io.javalin.http.Header;


public class main {
	

	public static void main(String[] args) {
		
		DB db = new DB(4341, 8,"C:\\Users\\Tal Goldbach\\Desktop\\CAR DETAILS FROM CAR DEKHO.csv" ); 
		//db.printStringData();
		db.convertDataToDouble();
		db.cleanData();
		//db.normelizeData();
		//db.printDoubleData(db.cleanDoubleData);
		db.buildYSet();
		db.buildXSet();
//		//db.descricsStats();
		//db.printDoubleData(db.getX_Set());
//		
//		//db.printCleanDoubleData();
		
		multipleLinearRegression mlr = new multipleLinearRegression(db.getX_Set(),db.getY_Set());
		mlr.gradientDescent();
		System.out.println(mlr.percentAccuracy());
		System.out.println();
		
		// Create a new Javalin instance
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                });
            });
        });

        

        // Set up a simple GET route
        app.get("/", ctx -> {
            ctx.result("Hello, world!");
        });
        
        app.get("/price", ctx -> {
            String CarName = ctx.queryParam("CarName");
            String Year = ctx.queryParam("Year");
            String Kilometer = ctx.queryParam("Kilometer");
            String Fuel = ctx.queryParam("Fuel");
            String SellerType = ctx.queryParam("SellerType");
            String Transmisson = ctx.queryParam("Transmisson");
            String Owner = ctx.queryParam("Owner");
            String [] input = {CarName, Year, Kilometer, Fuel, SellerType, Transmisson, Owner};
            double result = mlr.predictPrice(DB.convertInputToDoublePrice(input));
            int resultInteger = (int)result;
            ctx.result(Integer.toString(resultInteger));
          
        });	
        
        app.get("/carName", ctx -> {
        	//ctx.header(Header.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            String Price = ctx.queryParam("Price");
            String Year = ctx.queryParam("Year");
            String Kilometer = ctx.queryParam("Kilometer");
            String Fuel = ctx.queryParam("Fuel");
            String SellerType = ctx.queryParam("SellerType");
            String Transmisson = ctx.queryParam("Transmisson");
            String Owner = ctx.queryParam("Owner");
            String [] input = {Price, Year, Kilometer, Fuel, SellerType, Transmisson, Owner};
            String result = mlr.predictCarModel(DB.convertInputToDoubleCarModel(input), DB.getFloatToCar());
            ctx.result(result);
        });
        
        app.get("/listOfCars", ctx -> {
        	
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


