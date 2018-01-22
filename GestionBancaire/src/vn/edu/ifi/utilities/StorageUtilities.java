package vn.edu.ifi.utilities;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import vn.edu.ifi.javabean.Banque;
import vn.edu.ifi.javabean.Compte;
import vn.edu.ifi.javabean.Transaction;

public class StorageUtilities {
	private static final String FILENAME="bank.ifi";
	private ObjectOutputStream saver;
	private ObjectInputStream retriever;
	
	
	
	public StorageUtilities() {
		initializeData();
		try {
			saver = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(FILENAME))));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Unable to find the file "+FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("An error was encountourned while opening "+FILENAME);
		}
	}
	public Banque retrieveData()
	{
		
		try {
			return (Banque)retriever.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Unable to find suitable class for the retrieved object");
			return newBankWithInitialData();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Unable to retrieve data from file");
			return newBankWithInitialData();
		}
	}
	public void saveData(Banque bank)
	{
		try {
			saver.writeObject(bank);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to write data on the file");
		}
	}
	public void initializeData()
	{
		File f = new File(FILENAME);
		if(!f.exists() || !f.isFile())
		{
			try {
				f.createNewFile();
				this.saveData(newBankWithInitialData());
				System.out.println("Storage Utilities is initiliazed");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Impossible de créer le fichier"+FILENAME);
			}
		}else {
			System.out.println("Storage Utilities is initiliazed");
		}
	}
	public Banque newBankWithInitialData()
	{
		Set<Compte> accounts =  new HashSet<>();
		Set<Transaction> transaction =  new HashSet<>();
		Banque b = new Banque(this);
		b.setAccounts(accounts);
		b.setTransactions(transaction);
		return b;
	}
}
