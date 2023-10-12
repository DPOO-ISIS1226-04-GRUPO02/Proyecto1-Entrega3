package Console;

import java.util.Scanner;
import java.util.Calendar;

import Processing.CarRental;
import Model.Store;
import Model.Client;

public class View {
	
	private Store workplace;
	private int access;
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
						Client information = CarRental.getClient(login);
						if (information.equals(null)) {

							System.out.println("Debe crear su usuario...");
							System.out.println("\nIngrese su nombre: ");
							String name = scan.nextLine();
							System.out.println("Ingrese su número de telefono: ");
							long phone = scan.nextLong();
							System.out.println("Ingrese su email: ");
							String email = scan.nextLine();
							while (!email.contains("@")) {

								System.out.println("Ingrese un email válido");
								email = scan.nextLine();

							}
							System.out.println("Ingrese su fecha de nacimiento (dd/mm/aaaa): ");
							String dateBirthString = scan.nextLine();
							byte i = 0;
							int[] calendarValues = {0, 0, 0};
							for (String value: dateBirthString.split("/")) {

								calendarValues[i] = Integer.parseInt(value);
								i += 1;

							}
							Calendar dateBirth = Calendar.getInstance();
							dateBirth.set(calendarValues[0], calendarValues[1], calendarValues[2], 0, 0, 0);
							System.out.println("Ingrese su país de nacimiento: ");
							String nationality = scan.nextLine();
							System.out.println("Ingrese la ubicación de la foto de su identificación (en el computador): ");
							String idPhotoPath = scan.nextLine(); 
							// TODO: Retrieve the photo and move it to its corresponding folder

							System.out.println("-- DATOS DE LA TARJETA DE CREDITO PARA EL PAGO --");
							System.out.println("Ingrese el número de su tarjeta de crédito: ");
							long cardNumber = scan.nextLong();
							System.out.println("Ingrese la fecha de expiración de su tarjeta (dd/mm/aaaa): ");
							String cardExpiratioString = scan.nextLine();
							i = 0;
							for (String value: cardExpiratioString.split("/")) {

								calendarValues[i] = Integer.parseInt(value);
								i += 1;

							}
							Calendar cardExpiration = Calendar.getInstance();
							cardExpiration.set(calendarValues[0], calendarValues[1], calendarValues[2], 0, 0, 0);
							System.out.println("Ingrese el código trasero de la tarjeta de crédito: ");
							short cardCode = scan.nextShort();
							System.out.println("Ingrese el nombre del dueño de la tarjeta de crédito: ");
							String cardOwner = scan.nextLine();
							System.out.println("Ingrese la dirección de facturación de la tarjeta: ");
							String cardAddress = scan.nextLine();

							System.out.println("-- DATOS DE LA LICENCIA DE CONDUCCION --");
							System.out.println("Ingrese el número de la licencia: ");
							long licenceNumber = scan.nextLong();
							System.out.println("Ingrese el país en que se expidió la licencia: ");
							String licenceCountry = scan.nextLine();
							System.out.println("Ingrese la fecha de expiración de su licencia (dd/mm/aaaa): ");
							String licenceExpiratioString = scan.nextLine();
							i = 0;
							for (String value: licenceExpiratioString.split("/")) {

								calendarValues[i] = Integer.parseInt(value);
								i += 1;

							}
							Calendar licenceExpiration = Calendar.getInstance();
							licenceExpiration.set(calendarValues[0], calendarValues[1], calendarValues[2], 0, 0, 0);
							System.out.println("Ingrese la ubicación de la foto de su licencia (en el computador): ");
							String licencePhotoPath = scan.nextLine(); 
							// TODO: Retrieve the photo and move it to its corresponding folder

							CarRental.registerNewClient(name, phone, email, dateBirth, nationality, idPhotoPath, cardNumber, 
								cardExpiration, cardCode, cardOwner, cardAddress, login, licenceNumber, licenceCountry, 
								licenceExpiration, licencePhotoPath);

						} else {
							System.out.println("¿Qué desea modificar de su perfil?");
							System.out.println("1. Nombre");
							System.out.println("2. Teléfono");
							System.out.println("3. Email");
							System.out.println("4. Foto de identificación");
							System.out.println("5. Método de pago");
							System.out.println("6. Licencia");
							byte response = scan.nextByte();
							byte i = 0;
							Integer[] calendarValues = {0, 0, 0};
							switch (response) {

								case 1:
									System.out.println("Ingrese el nuevo valor para 'nombre': ");
									String name = scan.nextLine();
									information.setName(name);
									break;
								case 2:
									System.out.println("Ingrese el nuevo valor para 'teléfono': ");
									long phone = scan.nextLong();
									information.setPhone(phone);
									break;
								case 3:
									System.out.println("Ingrese el nuevo valor para 'email': ");
									String email = scan.nextLine();
									information.setEmail(email);
									break;
								case 4:
									System.out.println("Ingrese el nuevo camino de la foto de identificación: ");
									String newPath = scan.nextLine();
									information.setIdPhotoPath(newPath);
									break;
								case 5:
									System.out.println("Ingrese el número de su tarjeta de crédito: ");
									long cardNumber = scan.nextLong();
									System.out.println("Ingrese la fecha de expiración de su tarjeta (dd/mm/aaaa): ");
									String cardExpiratioString = scan.nextLine();
									for (String value: cardExpiratioString.split("/")) {

										calendarValues[i] = Integer.parseInt(value);
										i += 1;

									}
									Calendar cardExpiration = Calendar.getInstance();
									cardExpiration.set(calendarValues[0], calendarValues[1], calendarValues[2], 0, 0, 0);
									System.out.println("Ingrese el código trasero de la tarjeta de crédito: ");
									short cardCode = scan.nextShort();
									System.out.println("Ingrese el nombre del dueño de la tarjeta de crédito: ");
									String cardOwner = scan.nextLine();
									System.out.println("Ingrese la dirección de facturación de la tarjeta: ");
									String cardAddress = scan.nextLine();
									information.setPayment(cardNumber, cardExpiration, cardCode, cardOwner, cardAddress);
								case 6:
									System.out.println("-- DATOS DE LA LICENCIA DE CONDUCCION --");
									System.out.println("Ingrese el número de la licencia: ");
									long licenceNumber = scan.nextLong();
									System.out.println("Ingrese el país en que se expidió la licencia: ");
									String licenceCountry = scan.nextLine();
									System.out.println("Ingrese la fecha de expiración de su licencia (dd/mm/aaaa): ");
									String licenceExpiratioString = scan.nextLine();
									for (String value: licenceExpiratioString.split("/")) {
									
										calendarValues[i] = Integer.parseInt(value);
										i += 1;
									
									}
									Calendar licenceExpiration = Calendar.getInstance();
									licenceExpiration.set(calendarValues[0], calendarValues[1], calendarValues[2], 0, 0, 0);
									System.out.println("Ingrese la ubicación de la foto de su licencia (en el computador): ");
									String licencePhotoPath = scan.nextLine(); 
									CarRental.newLicence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath, login);
									break;
								default:
									System.out.println("Option not found.");
									break;

							}

						}
					
					case 2:
						// TODO: Call implemented option to reserve a car
						break;
					default:
						System.out.println("Option not found.");
				
				}
		
		}

		scan.close();
		
	}

}
