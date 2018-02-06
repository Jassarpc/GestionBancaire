package vn.edu.ifi.javabean;

import java.io.Serializable;
import java.util.Date;

import vn.edu.ifi.utilities.DateUtilities;

public class Transaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idTransaction;
	private TransactionType transactionType;
	private Date transactionDate;
	private float amount;
	@SuppressWarnings("unused")
	private int accNumber;

	public Transaction(TransactionType transactionType, Date transactionDate, float amount, int accNumber) {
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.accNumber = accNumber;
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

	public int getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(int accNumber) {
		this.accNumber = accNumber;
	}

	public int getIdTransaction() {
		return idTransaction;
	}

	public void setIdTransaction(int idTransaction) {
		this.idTransaction = idTransaction;
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
				+ " sur le compte numero CP" + getAccNumber() + " ";
	}

}
