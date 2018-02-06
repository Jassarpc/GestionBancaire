package vn.edu.ifi.javabean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import vn.edu.ifi.exception.IdException;

public class Client implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private int clientId;
	private Map<Integer, Compte> accounts;

	public Client(String userName, int clientId) {
		try {
			if(clientId<=0)
			{
		this.userName = userName;
		this.clientId = clientId;
		accounts = new HashMap<>();}else {
			throw new IdException("L'identifiant ne peut pas être négatif ou zéro");
		}
			}catch (IdException e) {
			System.err.println(e.getMessage());
		}
	}

	public Map<Integer, Compte> getAccounts() {
		return accounts;
	}

	public void setAccounts(Map<Integer, Compte> accounts) {
		this.accounts = accounts;
	}

	public void addAccount(Compte client) {
		accounts.put(client.getAccNumber(), client);
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

	public Client getClient() {
		return this;
	}
	public void printAccountList() {
		accounts.forEach((k,v)->{
			System.out.println(v.toString());
		});
	}
	
	public void benefitCalcAndMaj() {
		accounts.forEach((key,value)->{
			value.benefitCalcAndMaj();
		});
	}
}