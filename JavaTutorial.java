import java.util.*;

interface Eatable {
	public void eat();
}

public class JavaTutorial {
	public static void main(String[] args) {
		// Creating variables and printing with concatenation
		String a = "Hello World!";
		System.out.println(a);
		
		// Instantiating objects and calling their constructors
		Apple grannySmith = new Apple();
		grannySmith.setExpirationDate("09/28/67");
		System.out.println(grannySmith.getExpirationDate());
		
		//Manipulation of ArrayLists...
		Apple red = new Apple();
		Apple gala = new Apple();
		List<Apple> basket = new ArrayList<Apple>();
		basket.add(red);
		basket.add(gala);
		System.out.println(basket.get(0).getExpirationDate());
		
		// Upcasting
		Fruit fruit = (Fruit)grannySmith;
		Fruit f = new Fruit();
		f.eat();
		gala.eat();
	}
}

class Fruit implements Eatable {
	// Encapsulation and the importance of access modifiers
	private String expirationDate = "01/10/01";
	
	// Creating the constructor
	public Fruit() {
		System.out.println("I'm a fruit.");
	}
	
	// Defining a method (setter)
	public void setExpirationDate(String expDate) {
		this.expirationDate = expDate;
	}
	
	// Defining a method (getter)
	public String getExpirationDate() {
		return this.expirationDate;
	}
	
	public void eat() {
		System.out.println("Nom");
	}
}

class Apple extends Fruit { 
	@Override
	public void eat() {
		System.out.println("Om nom");
	}
}
