package vn.edu.ifi.javabean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import vn.edu.ifi.utilities.DateUtilities;
import vn.edu.ifi.utilities.StorageUtilities;

/**
 * Cette class Banque est une implementation d'une banque réelle,
 * elle implémente l'interface Serializable pour pouvoir être sérialisé.
 * @author DAOUDA Kadri Saïdi - HAMIDULLAH Yasser
 * @version 1.0
 */
public class Banque implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Integer, Compte> accounts;
	private Map<Integer, Client> clients;
	private List<Transaction> transactions;
	private int lastAccountNumber = 10, lastClientId = 900;
	
	/**
	 * Le constructeur de notre class Banque
	 */
	public Banque() {
		initialize();
	}

	/**
	 * Initialise banque, teste s'il existe déjà un stockage de données / 
	 * vide (ne comportant pas des données bancaires) et qu'il 
	 * faut en créer un nouveau avec les données initiales
	 */
	private void initialize() {
		Banque banque = null;
		if (StorageUtilities.fileStorageExists() && StorageUtilities.containsBankData()) {
			banque = StorageUtilities.readFile();
			setAccounts(banque.getAccounts());
			setClients(banque.getClients());
			setTransactions(banque.getTransactions());
			setLastAccountNumber(banque.getLastAccountNumber());
			setLastClientId(banque.getLastClientId());
			updateData();
		} else {
			setUpNewBankAndStorage();

		}
	}

	/**
	 * Cette methode est appélée lorsqu'aucun stockage de données 
	 * de la banque n'a pas été trouvée ou que les données sont corrompus 
	 * ou ne possedant plus des données bancaires
	 */
	private void setUpNewBankAndStorage() {
		accounts = new HashMap<>();
		clients = new HashMap<>();
		transactions = new ArrayList<>();
		this.signUp("Yasser", 0f, 5);
		this.signUp("Daouda", 0f, 5);
		if (updateData()) {
			System.out.println(
					"******************[INFO] La banque est initialisé avec les données initiales******************");
		}
	}
	/**
	 * 
	 * @return
	 */
	private Map<Integer, Compte> getAccounts() {
		return accounts;
	}

	private void setAccounts(Map<Integer, Compte> accounts) {
		this.accounts = accounts;
	}

	public Map<Integer, Client> getClients() {
		return clients;
	}

	private void setClients(Map<Integer, Client> clients) {
		this.clients = clients;
	}

	private List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	private boolean saveNewAccount(Compte compte) {
		return this.getAccounts().put(compte.getAccNumber(), compte) != null;

	}

	private boolean saveNewClient(Client client) {
		return this.getClients().put(client.getClientId(), client) != null;
	}

	public boolean deleteAnAccount(Compte compte) {
		return this.getAccounts().remove(compte.getAccNumber()) != null;

	}

	public boolean deposit(int accNumber, float amount) {
		Compte compte = findAccountByAccNumber(accNumber);
		Transaction transaction = new Transaction(TransactionType.DEPOT, DateUtilities.getCurrent(), amount, compte);
		return startTransaction(transaction);
	}

	public boolean withdraw(int accNumber, float amount) {
		Transaction transaction = new Transaction(TransactionType.RETRAIT, DateUtilities.getCurrent(), amount,
				findAccountByAccNumber(accNumber));
		accounts.get(accNumber).setAccBalance(accounts.get(accNumber).getAccBalance() - amount);
		return startTransaction(transaction);
	}

	private int getLastAccountNumber() {
		return lastAccountNumber;
	}

	private int getNewAccountNumber() {
		lastAccountNumber++;
		return lastAccountNumber;
	}

	private void setLastAccountNumber(int lastAccountNumber) {
		this.lastAccountNumber = lastAccountNumber;
	}

	private int getLastClientId() {
		return lastClientId;
	}

	private int getNewClientId() {
		lastClientId++;
		return lastClientId;
	}

	public void setLastClientId(int lastClientId) {
		this.lastClientId = lastClientId;
	}

	private boolean startTransaction(Transaction transaction) {
		transactions.add(transaction);
		Compte compte = transaction.getCompte();
		if ((compte.getAccBalance() > transaction.getAmount()
				&& transaction.getTransactionType() == TransactionType.RETRAIT)
				|| (transaction.getTransactionType() == TransactionType.DEPOT)) {
			compte.setAccBalance(compte.getAccBalance() + transaction.getAmount());
			accounts.put(compte.getAccNumber(), compte);
			if (updateData()) {
				// we'll return here soon!!
				System.out.println("******************[TRANSACTION SUCCEDED] " + transaction.toString()
				+ " a bien été enregistré");
				return true;
			} else {
				System.out.println(
						"******************[ERROR] Impossible d'enregistrer cette transaction******************");
				return false;
			}
		} else {
			System.out.println("******************[TRANSACTION FAILED]" + transaction.toString()
			+ " : SOLDE INSUFISANT******************");
			return false;
		}
	}

	public void signUp(String userName, float accBalance, float benefitRate) {
		Client client = new Client(userName, getNewClientId());
		Compte compte = new Compte(getNewAccountNumber(), accBalance, benefitRate, client);
		accounts.put(compte.getAccNumber(), compte);
		clients.put(client.getClientId(), client);
		updateData();
	}

	public void signUp(int clientId, float benefitRate) {
		Client client = findAccountByClientId(clientId);
		if (client != null) {
			Compte c = new Compte(getNewAccountNumber(), 0f, benefitRate, client);
			if (saveNewAccount(c))
				updateData();
		} else {
			System.out.println(
					"******************[ERROR]User not registered or Bad User Identification******************");
		}

	}

	private boolean updateData() {
		return StorageUtilities.saveData(this);
	}

	private Compte findAccountByAccNumber(int accNumber) {
		return accounts.get(accNumber);
	}

	private Compte findAccountByClientId(int clientId) {
		Set<Entry<Integer, Compte>> setHm = accounts.entrySet();
		Iterator<Entry<Integer, Compte>> it = setHm.iterator();
		while (it.hasNext()) {
			Entry<Integer, Compte> e = it.next();
			System.out.println(e.getKey() + " : " + e.getValue());
			if (e.getValue().getAccNumber() == clientId) {
				return e.getValue();
			}
		}
		return null;
	}

	public void getAccountList() {

		accounts.forEach((key, comtpe) -> {
			System.out.println("Compte N°" + comtpe.getAccNumber() + " de " + comtpe.getUserName()
			+ " portant le numéro d'identification ID" + comtpe.getClientId() + " solde "
			+ comtpe.getAccBalance() + "VND " + " avec taux de " + comtpe.getBenefitRate() + "%");
		});
	}

}

/**
 * 
 * --------------------------------------------------------------------------------------
 * ***COMPTE***
 * ---------------------------------------------------------------------------------------
 *
 */
class Compte extends Client implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int accNumber;
	private float accBalance;
	private float benefitRate;

	public Compte(int accNumber, float accBalance, float benefitRate, Client c) {
		super(c.getUserName(), c.getClientId());
		this.accNumber = accNumber;
		this.accBalance = accBalance;
		this.benefitRate = benefitRate;
	}

	public int getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(int accNumber) {
		this.accNumber = accNumber;
	}

	public float getAccBalance() {
		return accBalance;
	}

	public void setAccBalance(float accBalance) {
		this.accBalance = accBalance;
	}

	public float getBenefitRate() {
		return benefitRate;
	}

	public void setBenefitRate(float benefitRate) {
		this.benefitRate = benefitRate;
	}

}

/**
 * 
 * --------------------------------------------------------------------------------------
 * ***CLIENT***
 * ---------------------------------------------------------------------------------------
 *
 */
class Client implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private int clientId;

	public Client(String userName, int clientId) {
		this.userName = userName;
		this.clientId = clientId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

}

/**
 * 
 * --------------------------------------------------------------------------------------
 * ***TRANSACTION***
 * ---------------------------------------------------------------------------------------
 *
 */
class Transaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TransactionType transactionType;
	private Date transactionDate;
	private float amount;
	@SuppressWarnings("unused")
	private Compte compte;

	public Transaction(TransactionType transactionType, Date transactionDate, float amount, Compte compte) {
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.compte = compte;

	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(amount);
		result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!transactionDate.equals(other.transactionDate))
			return false;
		if (transactionType != other.transactionType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + DateUtilities.getFormattedDate(getTransactionDate()) + "] " + getTransactionType().toString()
				+ " sur le compte numero CP" + getCompte().getAccNumber() + " " + getCompte().getUserName();
	}

}
