package carPricePrediction;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DB db = new DB(4341, 8,"C:\\Users\\roy\\Desktop\\CAR DETAILS FROM CAR DEKHO.csv" ); 
		//db.printStringData();
		db.convertDataToDouble();
		db.cleanData();
		//db.printDoubleData(db.cleanDoubleData);
		db.buildYSet();
		db.buildXSet();
		//db.descricsStats();
		//db.printDoubleData(db.getX_Set());
		
		//db.printCleanDoubleData();
		
		
		
		multipleLinearRegression mlr = new multipleLinearRegression(db.getX_Set(),db.getY_Set());
		mlr.gradientDescent();
		System.out.println(mlr.percentAccuracy());
		
		
		
	}

}
