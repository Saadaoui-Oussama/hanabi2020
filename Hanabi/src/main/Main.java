package main;
import model.*;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hanabi");
		SimpleGameData data = new SimpleGameData(2);

		System.out.println(data.getDeck().toString());
			
	}
}
