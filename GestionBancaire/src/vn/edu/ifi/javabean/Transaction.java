package vn.edu.ifi.javabean;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable{
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
		return "Transaction [transactionType=" + transactionType + ", transactionDate=" + transactionDate + ", amount="
				+ amount + ", compte=" + compte + "]";
	}
	

}
