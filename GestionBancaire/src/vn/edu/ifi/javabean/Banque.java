package vn.edu.ifi.javabean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import vn.edu.ifi.utilities.DateUtilities;
import vn.edu.ifi.utilities.StorageUtilities;

/**
 * Cette class Banque est une implementation d'une banque réelle, elle
 * implémente l'interface Serializable pour pouvoir être sérialisé.
 * 
 * @author DAOUDA Kadri Saïdi - HAMIDULLAH Yasser
 * @version 1.0
 */
public class Banque implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Integer, Client> clients;// c'est l'ensemble des comptes
	private int lastAccountNumber = 10, lastClientId = 900, lastTransactionId = 0;

	/**
	 * Le constructeur de notre class Banque
	 */
	public Banque() {
		initialize();
	}

	/**
	 * Initialise banque, teste s'il existe déjà un stockage de données / vide (ne
	 * comportant pas des données bancaires) et qu'il faut en créer un nouveau avec
	 * les données initiales
	 */
	private void initialize() {
		Banque banque = null;
		if (StorageUtilities.fileStorageExists() && StorageUtilities.containsBankData()) {
			banque = StorageUtilities.readFile();
			setClients(banque.getClients());
			setLastAccountNumber(banque.getLastAccountNumber());
			setLastClientId(banque.getLastClientId());
			updateData();
		} else {
			setUpNewBankAndStorage();

		}
	}

	/**
	 * Cette methode est appélée lorsqu'aucun stockage de données de la banque n'a
	 * pas été trouvée ou que les données sont corrompus ou ne possedant plus des
	 * données bancaires
	 */
	private void setUpNewBankAndStorage() {
		clients = new HashMap<>();
		this.signUp("Yasser", 5);
		this.signUp("Daouda", 5);
		if (updateData()) {
			System.out.println(
					"******************[INFO] La banque est initialisé avec les données initiales******************");
		}
	}

	/**
	 * 
	 * @return
	 */
	public Map<Integer, Client> getClients() {
		return clients;
	}

	public void setClients(Map<Integer, Client> clients) {
		this.clients = clients;
	}

	public boolean deposit(int accNumber, float amount) {
		Transaction transaction = new Transaction(TransactionType.DEPOT, DateUtilities.getCurrent(), amount, accNumber);
		return startTransaction(transaction);
	}

	public boolean withdraw(int accNumber, float amount) {
		Transaction transaction = new Transaction(TransactionType.RETRAIT, DateUtilities.getCurrent(), amount,accNumber);
		return startTransaction(transaction);
	}

	public int getLastAccountNumber() {
		return lastAccountNumber;
	}

	public int getNewAccountNumber() {
		lastAccountNumber++;
		return lastAccountNumber;
	}

	public void setLastAccountNumber(int lastAccountNumber) {
		this.lastAccountNumber = lastAccountNumber;
	}

	public int getLastTransactionId() {
		return lastTransactionId;
	}

	public void setLastTransactionId(int lastTransactionId) {
		this.lastTransactionId = lastTransactionId;
	}

	public int getNewIdTransaction() {
		lastTransactionId++;
		return lastTransactionId;
	}

	public int getLastClientId() {
		return lastClientId;
	}

	public int getNewClientId() {
		lastClientId++;
		return lastClientId;
	}

	public void setLastClientId(int lastClientId) {
		this.lastClientId = lastClientId;
	}

	private boolean startTransaction(Transaction transaction) {
		Compte compte = findAccountByAccNumber(transaction.getAccNumber());
		if(compte!=null) {
		if ((compte.getAccBalance() > transaction.getAmount()
				&& transaction.getTransactionType() == TransactionType.RETRAIT)
				|| (transaction.getTransactionType() == TransactionType.DEPOT)) {
			float newBalance = transaction.getTransactionType() == TransactionType.DEPOT
					? compte.getAccBalance() + transaction.getAmount()
					: compte.getAccBalance() - transaction.getAmount();
			compte.setAccBalance(newBalance);
			Client c = clients.get(compte.getClientId());
			transaction.setIdTransaction(getNewIdTransaction());
			compte.addTransaction(transaction);
			c.addAccount(compte);
			clients.put(c.getClientId(), c);
			if (updateData()) {
				System.out.println("******************[TRANSACTION SUCCEDED] " + transaction.toString()
						+ " a bien été enregistré");
				return true;
			} else {
				System.err.println(
						"******************[ERROR] Impossible d'enregistrer cette transaction******************");
				return false;
			}
		} else {
			System.err.println("******************[TRANSACTION FAILED]" + transaction.toString()
					+ " : SOLDE INSUFISANT******************");
			return false;
		}}else {
			System.err.println("******************[TRANSACTION FAILED]" + transaction.toString()
			+ " : COMPTE INTROUVABLE******************");
	return false;
		}
	}

	public void signUp(String userName, float benefitRate) {
		Client client = new Client(userName, getNewClientId());
		Compte compte = new Compte(getNewAccountNumber(), 0f, benefitRate, client.getClientId(),client.getUserName());
		client.getAccounts().put(compte.getAccNumber(), compte);
		clients.put(client.getClientId(), client);
		updateData();
		System.out.println("Inscription réussi => " + compte.toString());
	}

	public void signUp(int clientId, float benefitRate) {
		Client client = clients.get(clientId);
		if (client != null) {
			Compte c = new Compte(getNewAccountNumber(), 0f, benefitRate, client.getClientId(),client.getUserName());
			client.addAccount(c);
			clients.put(client.getClientId(), client);
			updateData();
			System.out.println("Inscription réussi => " + c.toString());
		} else {
			System.err.println(
					"******************[ERROR]User not registered or Bad User Identification******************");
		}

	}

	private boolean updateData() {
		return StorageUtilities.saveData(this);
	}

	public Compte findAccountByAccNumber(int accNumber) {
		Compte compte = null;
		Set<Entry<Integer, Client>> setHm = clients.entrySet();
		Iterator<Entry<Integer, Client>> it = setHm.iterator();
		while (it.hasNext() && compte == null) {
			Entry<Integer, Client> e = it.next();
			Set<Entry<Integer, Compte>> setHmap = e.getValue().getAccounts().entrySet();
			Iterator<Entry<Integer, Compte>> it1 = setHmap.iterator();
			while (it1.hasNext() && compte == null) {
				Entry<Integer, Compte> e1 = it1.next();
				if (e1.getValue().getAccNumber() == accNumber)
					compte = e1.getValue();
			}
		}
		return compte;
	}



	public Client findClient(int clientId) {
		return clients.get(clientId);
	}

	public void printReport() {
		String leftAlignFormat = "| %-10d | %-10d | %-10s |%n";

		System.out.format("+------------+------------+------------+%n");
		System.out.format("| Compte N°  | Nb. Trans  | Solde      |%n");
		System.out.format("+------------+------------+------------+%n");

		clients.forEach((clKey, client) -> {
			client.getAccounts().forEach((key, compte) -> {

				System.out.format(leftAlignFormat, compte.getAccNumber(), compte.getTransactions().size(),""+compte.getAccBalance());
				System.out.format("+------------+------------+------------+%n");

			});
		});

	}
	public void printAccountList() {
		String leftAlignFormat = "| %-10d | %-10d | %-10s | %-11s| %-10s |%n";

		System.out.format("+------------+------------+------------+------------+------------+%n");
		System.out.format("| Compte N°  |Client N°   |Taux        | Nom        | Solde      |%n");
		System.out.format("+------------+------------+------------+------------+------------+%n");

		clients.forEach((clKey, client) -> {
			client.getAccounts().forEach((key, compte) -> {

				System.out.format(leftAlignFormat, compte.getAccNumber(),compte.getClientId(),""+compte.getBenefitRate(),compte.getUserName(),""+compte.getAccBalance());
				System.out.format("+------------+------------+------------+------------+------------+%n");

			});
		});
	}

	public void benefitCalcAndMaj() {
		clients.forEach((key,value)->{
			value.benefitCalcAndMaj();
		});
	}

	
}
