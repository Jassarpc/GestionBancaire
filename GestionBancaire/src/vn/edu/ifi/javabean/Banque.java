package vn.edu.ifi.javabean;

import java.io.Serializable;
import java.util.Set;

import vn.edu.ifi.utilities.StorageUtilities;

public class Banque implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<Compte> accounts;
	private Set<Transaction> transactions;
	private StorageUtilities storageUtilities;
	public Banque(StorageUtilities storageUtilities) {
		initialize();
	}

	private void initialize() {
		Banque b = storageUtilities.retrieveData();	
		this.setAccounts(b.getAccounts());
		this.setTransactions(b.getTransactions());
	}

	public Set<Compte> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Compte> accounts) {
		this.accounts = accounts;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	public boolean createNewAccount(Compte compte)
	{
		return this.getAccounts().add(compte);
		
	}
	public boolean deleteAnAccount(Compte compte)
	{
		return this.getAccounts().remove(compte);
		
	}
	public boolean deposit()
	{
		//i'll back here soon
		return false;
	}
	public boolean withdraw()
	{
		//i'll back here soon
				return false;
	}
	
	

}
