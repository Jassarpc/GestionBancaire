package vn.edu.ifi.utilities;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import vn.edu.ifi.javabean.Banque;

public class StorageUtilities {
	private static final String FILENAME = "./bank.ifi";

	public static boolean containsBankData() {
		Banque banque = null;
		try (ObjectInputStream b = new ObjectInputStream(new FileInputStream(new File(FILENAME)))) {
			banque = (Banque) b.readObject();
		} catch (EOFException e) {
			System.out.println(
					"******************[WARN]Le stockage est présent mais ne contient aucune donnée******************");
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return banque != null;
	}

	public static boolean saveData(Banque banque) {
		try (ObjectOutputStream saver = new ObjectOutputStream((new FileOutputStream(new File(FILENAME))))) {
			saver.writeObject(banque);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to write data on the file");
		}
		return false;
	}

	public static Banque readFile() {
		Banque par = null;
		try (ObjectInputStream retriever = new ObjectInputStream(new FileInputStream(new File(FILENAME)))) {
			par = (Banque) retriever.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return par;
	}

	public static boolean fileStorageExists() {
		File f = new File(FILENAME);
		return f.exists();
	}
}
