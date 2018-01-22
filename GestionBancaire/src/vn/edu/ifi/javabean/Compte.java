package vn.edu.ifi.javabean;

import java.io.Serializable;

public class Compte implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int accNumber;
	private int userId;
	private String userName;
	private float accBalance;
	private float benefitRate;

	public Compte() {

	}

	public int getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(int accNumber) {
		this.accNumber = accNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(accBalance);
		result = prime * result + accNumber;
		result = prime * result + Float.floatToIntBits(benefitRate);
		result = prime * result + userId;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		Compte other = (Compte) obj;
		if (Float.floatToIntBits(accBalance) != Float.floatToIntBits(other.accBalance))
			return false;
		if (accNumber != other.accNumber)
			return false;
		if (Float.floatToIntBits(benefitRate) != Float.floatToIntBits(other.benefitRate))
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Compte [accNumber=" + accNumber + ", userId=" + userId + ", userName=" + userName + ", accBalance="
				+ accBalance + ", benefitRate=" + benefitRate + "]";
	}
	

}
