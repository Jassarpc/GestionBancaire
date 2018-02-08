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
 * Cette class Banque est une implementation d'une banque r�elle, elle
 * impl�mente l'interface Serializable pour pouvoir �tre s�rialis�.
 * 
 * @author DAOUDA Kadri Sa�di - HAMIDULLAH Yasser
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
	 * Initialise banque, teste s'il existe d�j� un stockage de donn�es / vide (ne
	 * comportant pas des donn�es bancaires) et qu'il faut en cr�er un nouveau avec
	 * les donn�es initiales
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
	 * Cette methode est app�l�e lorsqu'aucun stockage de donn�es de la banque n'a
	 * pas �t� trouv�e ou que les donn�es sont corrompus ou ne possedant plus des
	 * donn�es bancaires
	 */
	private void setUpNewBankAndStorage() {
		clients = new HashMap<>();
		this.signUp("Yasser", 5);
		this.signUp("Daouda", 5);
		if (updateData()) {
			System.out.println(
					"******************[INFO] La banque est initialis� avec les donn�es initiales******************");
		}
	}

	/**
	 * getter de la liste des clients
	 * @return clients
	 */
	public Map<Integer, Client> getClients() {
		return clients;
	}
	/**
	 * setter de la liste des clients
	 * @param clients
	 */
	public void setClients(Map<Integer, Client> clients) {
		this.clients = clients;
	}
	/**
	 * M�thode appel� lors du d�p�t
	 * @param accNumber
	 * @param amount
	 * @return resultat
	 */
	public boolean deposit(int accNumber, float amount) {
		Transaction transaction = new Transaction(TransactionType.DEPOT, DateUtilities.getCurrent(), amount, accNumber);
		return startTransaction(transaction);
	}
	/**
	 * M�thode appel� lors du retrait
	 * @param accNumber
	 * @param amount
	 * @return
	 */
	public boolean withdraw(int accNumber, float amount) {
		Transaction transaction = new Transaction(TransactionType.RETRAIT, DateUtilities.getCurrent(), amount,accNumber);
		return startTransaction(transaction);
	}
	/**
	 * Getter
	 * @return dernier Numero de Compte
	 */
	public int getLastAccountNumber() {
		return lastAccountNumber;
	}
	/**
	 * Getter
	 * @return nouveau num�ro de compte disponible
	 */
	public int getNewAccountNumber() {
		lastAccountNumber++;
		return lastAccountNumber;
	}
	/**
	 * Setter
	 * @param lastAccountNumber
	 */
	public void setLastAccountNumber(int lastAccountNumber) {
		this.lastAccountNumber = lastAccountNumber;
	}
	/**
	 * Getter
	 * @return lastTransactionId
	 */
	public int getLastTransactionId() {
		return lastTransactionId;
	}
	/**
	 * Setter
	 * @param lastTransactionId
	 */
	public void setLastTransactionId(int lastTransactionId) {
		this.lastTransactionId = lastTransactionId;
	}
	/**
	 * Getter
	 * @return nouveau TransactionId disponible
	 */
	public int getNewIdTransaction() {
		lastTransactionId++;
		return lastTransactionId;
	}
	/**
	 * Getter
	 * @return lastClientId
	 */
	public int getLastClientId() {
		return lastClientId;
	}
	/**
	 * Getter
	 * @return nouveau id client disponible
	 */
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
						+ " a bien �t� enregistr�");
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
	/**
	 * Methode pour faire l'inscription d'un nouveau client
	 * @param userName
	 * @param benefitRate
	 * @return numero de compte cr�� derni�rement
	 */
	public int signUp(String userName, float benefitRate) {
		int id = 0;
		Client client = new Client(userName, getNewClientId());
		Compte compte = new Compte(getNewAccountNumber(), 0f, benefitRate, client.getClientId());
		client.getAccounts().put(compte.getAccNumber(), compte);
		clients.put(client.getClientId(), client);
		updateData();
		System.out.println("Inscription r�ussi => " + compte.toString());
		id = getLastAccountNumber();
		return id;
	}
	/**
	 * Methode pour faire l'inscription d'un ancien client
	 * @param clientId
	 * @param benefitRate
	 * @return
	 */
	public int signUp(int clientId, float benefitRate) {
		int id = 0;
		Client client = clients.get(clientId);
		if (client != null) {
			Compte c = new Compte(getNewAccountNumber(), 0f, benefitRate, client.getClientId());
			client.addAccount(c);
			clients.put(client.getClientId(), client);
			updateData();
			id=getLastAccountNumber();
			System.out.println("Inscription r�ussi => " + c.toString());
		} else {
			System.err.println(
					"******************[ERROR]User not registered or Bad User Identification******************");
		}
		return id;
	}
	/**
	 * Mise � jour des donn�es
	 * @return succes de l'op�ration
	 */
	private boolean updateData() {
		return StorageUtilities.saveData(this);
	}
	/**
	 * Recherche d'un compte par num�ro de compte
	 * @param accNumber
	 * @return
	 */
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


	/**
	 * Recherche d'un client
	 * @param clientId
	 * @return
	 */
	public Client findClient(int clientId) {
		return clients.get(clientId);
	}
	/**
	 * Affichage de rapport
	 */
	public void printReport() {
		String leftAlignFormat = "| %-10d | %-10d | %-10s |%n";

		System.out.format("+------------+------------+------------+%n");
		System.out.format("| Compte N�  | Nb. Trans  | Solde      |%n");
		System.out.format("+------------+------------+------------+%n");

		clients.forEach((clKey, client) -> {
			client.getAccounts().forEach((key, compte) -> {

				System.out.format(leftAlignFormat, compte.getAccNumber(), compte.getTransactions().size(),""+compte.getAccBalance());
				System.out.format("+------------+------------+------------+%n");

			});
		});

	}
	
	/**
	 * Affichage de la liste des comptes
	 */
	public void printAccountList() {
		String leftAlignFormat = "| %-10d | %-10d | %-10s | %-11s| %-10s |%n";

		System.out.format("+------------+------------+------------+------------+------------+%n");
		System.out.format("| Compte N�  |Client N�   |Taux        | Nom        | Solde      |%n");
		System.out.format("+------------+------------+------------+------------+------------+%n");

		clients.forEach((clKey, client) -> {
			client.getAccounts().forEach((key, compte) -> {

				System.out.format(leftAlignFormat, compte.getAccNumber(),compte.getClientId(),""+compte.getBenefitRate(),client.getUserName(),""+compte.getAccBalance());
				System.out.format("+------------+------------+------------+------------+------------+%n");

			});
		});
	}
	/**
	 * Methode qui calcule les int�r�ts et mets � jour les soldes
	 */
	public void benefitCalcAndMaj() {
		clients.forEach((key,value)->{
			value.benefitCalcAndMaj();
		});
	}

	
}
