package vn.edu.ifi.utilities;

import java.util.Scanner;

import vn.edu.ifi.javabean.Banque;
import vn.edu.ifi.javabean.Client;

public class OperationUtilities {
	private Banque banque;
	private int choice = 0;
	private Scanner reponse = new Scanner(System.in);

	public OperationUtilities() {
		banque = new Banque();
		choice = 0;
		reponse = new Scanner(System.in);
	}

	public void BankSession() {
		@SuppressWarnings("resource")
		final String[] mainChoice = { "MENU PRINCIPALE", "Compte", "Transaction", "Rapport", "Calc Taux" };
		// -------------------------------------------------------------------------------
		final String subMenuCompte[] = { "MENU COMPTE", "Créer", "Rechercher" };
		final String subSubMenuCompte1[] = { "MENU CREER", "Nouv. client", "Déjà client" };
		final String subSubMenuCompte2[] = { "MENU RECHERCHER", "Lister", "Rechercher" };
		final String[] menuTransaction = { "MENU TRANS", "DEPOT", "RETRAIT" };
		// -------------------------------------------------------------------------------
		final String[] compteParams1 = { "Nom du client", "Taux" };
		final String[] compteParams2 = { "ID du client", "Taux" };
		// -------------------------------------------------------------------------------
		final String[] searchParams = { "Numero d'intification du client" };
		// -------------------------------------------------------------------------------
		final String[] transactionParams = { "Numero de compte", "Montant" };
		// -------------------------------------------------------------------------------
		printMenuChoice(mainChoice);

		reqLine();

		while (choice != '0') {
			switch (choice) {
			case '1':
				printMenuChoice(subMenuCompte);
				reqLine();
				switch (choice) {
				case '1':
					printMenuChoice(subSubMenuCompte1);
					reqLine();
					switch (choice) {
					case '1':// Nouveau client
						String[] tab1 = requestCreateParams(compteParams1);
						try {
							banque.signUp(tab1[0], Float.parseFloat(tab1[1]));
						} catch (NumberFormatException e) {
							System.err.println("Veuillez saisir les données normalement!");
						}
						choice = 5;
						break;
					case '2':// Déja client
						String[] tab2 = requestCreateParams(compteParams2);
						try {
							banque.signUp(Integer.parseInt(tab2[0]), Float.parseFloat(tab2[1]));
						} catch (NumberFormatException e) {
							System.err.println("Veuillez saisir les données normalement!");
						}
						choice = 5;
						break;
					default:
						choice = 5;
						break;
					}
					break;
				case '2':
					printMenuChoice(subSubMenuCompte2);
					reqLine();
					switch (choice) {
					case '1':// Listing
						banque.printAccountList();
						choice = 5;
						break;
					case '2':// Rechercher
						String[] tab = requestCreateParams(searchParams);
						try {
							Client c = banque.findClient(Integer.parseInt(tab[0]));
							if (c == null) {
								System.err.println("Client introuvable");
							} else {
								if (c.getAccounts().isEmpty()) {
									System.out.println("Ce client n'a pas de compte");
								} else if (c.getAccounts().size() == 1) {
									System.out.println("Il a un compte");
									c.printAccountList();
								} else if (c.getAccounts().size() > 1) {
									System.out.println("Il possède des comptes");
									c.printAccountList();
								}

							}
						} catch (NumberFormatException e) {
							System.err.println("Veuillez saisir les données normalement!");
						}

						choice = 5;
						break;
					default:
						choice = 5;
						break;
					}
				}
				break;
			case '2':
				printMenuChoice(menuTransaction);
				reqLine();
				switch (choice) {
				case '1':
					String[] tab = requestCreateParams(transactionParams);
					try {
						banque.deposit(Integer.parseInt(tab[0]), Float.parseFloat(tab[1]));
					} catch (NumberFormatException e) {
						System.err.println("Veuillez saisir les données normalement!");
					}
					choice = 5;
					break;
				case '2':
					String[] tab1 = requestCreateParams(transactionParams);
					try {
						banque.withdraw(Integer.parseInt(tab1[0]), Float.parseFloat(tab1[1]));
					} catch (NumberFormatException e) {
						System.err.println("Veuillez saisir les données normalement!");
					}
					choice = 5;
					break;
				default:
					choice = 5;
					break;
				}
				break;
			case '3':
				banque.printReport();
				choice = 5;
				break;
			case '4':
				banque.benefitCalcAndMaj();
				banque.printReport();
				System.out.println("Calculer et augmentation selon les taux terminées");
				choice = 5;
				break;
			default:
				printMenuChoice(mainChoice);
				reqLine();
				break;
			}

		}
		System.out.println("Bye");
	}

	public String[] requestCreateParams(String... tab) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String[] tabReturned = new String[tab.length];
		for (int i = 0; i < tab.length; i++) {
			System.out.print("\n\t Veuillez entrer le " + tab[i] + "==> ");
			tabReturned[i] = sc.nextLine();
		}
		return tabReturned;
	}

	public void printMenuChoice(String[] tab) {
		System.out.println("*********************************************************************************\n"
				+ "*********************************************************************************\n"
				+ "********\t\t\t" + tab[0] + "\t\t\t\t ********\n"
				+ "*********************************************************************************\n"
				+ "*********************************************************************************\n"
				+ "********\t\t\t\t\t\t\t\t ********");
		for (int i = 1; i < tab.length; i++) {
			System.out.println("********\t\t\t[" + i + "]" + tab[i].toUpperCase() + "\t\t\t\t ********");

		}
		System.out.println("********\t\t\t[0]EXIT\t\t\t\t\t ********");
		reqResponse();
	}

	public void clear() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}

	public void reqResponse() {
		System.out.print("\n\t\t Votre choix ==> ");
	}

	public void reqLine() {
		try {
			choice = reponse.nextLine().charAt(0);
		} catch (Exception e) {
			System.err.println("Veuillez entrer votre réponse!");
			choice = 5;
		}

	}

}
