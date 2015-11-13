import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class RentDivision {
	private static int count;
	private static double mx;
	private static double my;
	private static double mz;
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter C and eps");
		Double C = sc.nextDouble();
		float eps = sc.nextFloat();
		int part =  (int) Math.ceil((2/eps));
		double step = C/(double)part;
		
		Random r = new Random();
		int choiceMade = r.nextInt(part-1)+1;
		mx = step*choiceMade;
		choiceMade = r.nextInt(part-1-choiceMade)+1;
		my = step*choiceMade;
		mz = C-mx-my;
		
		Map<Integer, List<Double>> door = new HashMap<Integer, List<Double>>();
				
		
		
		List<Double> point1 = new ArrayList<Double>();
		point1.add(0.0);
		point1.add(step);
		point1.add(C-step);
		
		door.put(1,point1);
		
		List<Double> point2 = new ArrayList<Double>();
		point2.add(step);
		point2.add(0.0);
		point2.add(C-step);
		
		door.put(2,point2);
		
		Map<Integer, Integer> PersonAsked = new HashMap<Integer,Integer>();
		PersonAsked.put(1, 2);
		PersonAsked.put(2,1);
		
		List<Double> previous = new ArrayList<Double>();
		previous.add(0.0);
		previous.add(0.0);
		previous.add(C);
		
		List<Double> opt = new ArrayList<Double>();
		opt.add(0.0);
		opt.add(0.0);
		opt.add(0.0);

		if(part<=1){
			queryResult(opt, C);
			return;
		}
		
		count = 0;
		while (true) {
			List<Double> point = queryNewPoint(door, previous,step);
			int renter = queryRenter(PersonAsked);
			int choice = queryPreference(renter, point.get(0), point.get(1), point.get(2));
			opt.set(renter-1, point.get(choice-1));
			//System.out.println(door.toString());
			//System.out.println(previous.toString());
			//System.out.println(point.toString());
			if(choice ==3){
				
				queryResult(opt, C);
				System.out.println(count);
				break;
			}
			
			previous.set(0,door.get(choice).get(0));
			previous.set(1,door.get(choice).get(1));
			previous.set(2,door.get(choice).get(2));
			door.get(choice).set(0,point.get(0));
			door.get(choice).set(1,point.get(1));
			door.get(choice).set(2,point.get(2));
			PersonAsked.put(choice, renter);		
		}
	}

	private static void queryResult(List<Double> opt, Double C) {
		double even = C - opt.get(0)-opt.get(1)-opt.get(2);
		System.out.printf("Result is %f %f %f \n", opt.get(0)+even/3.0, opt.get(1)+even/3.0, opt.get(2)+even/3.0 );
	}

	private static int queryRenter(Map<Integer, Integer> personAsked) {
		for(int i=1;i<4;i++){
			if(personAsked.get(1)!=i && personAsked.get(2)!=i ){
					return i;
				}
			
		}
		return 0;
	}

	private static List<Double> queryNewPoint(Map<Integer, List<Double>> door,
			List<Double> previous, double step) {
		
		List<Double> point1 = door.get(1);
		List<Double> point2 = door.get(2);
		List<Double> point = new ArrayList<Double>();
		//point.add(0.0);
		
		for(int i=0;i<3;i++){
			
			point.add(point1.get(i)+point2.get(i)-previous.get(i));
			
		}
		
		return point;
	}

	public static int queryPreference(int renter, double x, double y, double z) {
		
		if (x <= 0) {
			return 1;
		} else if (y <= 0) {
			return 2;
		} else if (z <= 0) {
			return 3;
		} else {
			Scanner sc = new Scanner(System.in);
			System.out.printf(
					"(%f,%f,%f), which room will renter %d choose? \n", x, y,
					z, renter);
			int choiceMade = sc.nextInt();
			//Random r = new Random();
			// choiceMade = r.nextInt(2)+1;
			count++;
			if(x==mx && y==my && z==mz){
				return 3;
			}
			return choiceMade;
		}
	}

	
}
