package vn.edu.ifi.javabean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import vn.edu.ifi.exception.BenefitRateException;
import vn.edu.ifi.exception.InvalidIdException;

public class Compte implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int accNumber;
	private float accBalance;
	private float benefitRate;
	private int clientId;
	private Map<Integer, Transaction> transactions;

	public Compte(int accNumber, float accBalance, float benefitRate, int clientId) {
		try {
			if (clientId <= 0 || accNumber <= 0) {
				throw new InvalidIdException("L'identifiant ne peut pas être négatif ou zéro");
			}
			if (benefitRate < 0 || benefitRate > 100) {
				throw new BenefitRateException("Impossible de donner une % négative ou <100");
			} else {
				this.accNumber = accNumber;
				this.accBalance = accBalance;
				this.benefitRate = benefitRate;
				this.clientId = clientId;
				transactions = new HashMap<>();
			}
		} catch (BenefitRateException | InvalidIdException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
	}

	public Map<Integer, Transaction> getTransactions() {
		return transactions;
	}

	public void addTransaction(Transaction transaction) {
		transactions.put(transaction.getIdTransaction(), transaction);
	}

	public void setTransactions(Map<Integer, Transaction> transactions) {
		this.transactions = transactions;
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

	public void benefitCalcAndMaj() {
		setAccBalance(accBalance + (((accBalance) * benefitRate) / 100));
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[Compte N°" + getAccNumber() + "] ClientId : " + getClientId()
				+ " Solde : " + accBalance;
	}

}