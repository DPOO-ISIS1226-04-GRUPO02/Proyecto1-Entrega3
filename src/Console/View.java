package Console;

import java.util.Scanner;

import Processing.CarRental;
import Model.Store;

public class View {
	
	private Store workplace;
	private int access;
	private CarRental rental;
	private String login;
	
	View(int access, Store workplace, String login) {
		
		this.access = access;
		this.workplace = workplace;
		this.login = login;
		
	}
	
	public void optionSelection() {
		
		Scanner scan = new Scanner(System.in);
		byte selection;
		
		switch (access) {
		
			case 0:
				System.out.println("1. Agregar o cambiar información personal");
				System.out.println("2. Reservar un carro");
				selection = scan.nextByte();
				switch (selection) {
				
					case 1:
						
				
				}
		
		}

		scan.close();
		
	}

}
