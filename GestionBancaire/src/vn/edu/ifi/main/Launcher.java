package vn.edu.ifi.main;

import vn.edu.ifi.javabean.Banque;

public class Launcher {
	public static void main(String... strings) {

		Banque bank = new Banque();
		//bank.signUp("Yasser", 0f, 5);
		bank.deposit(11, 50000);
		bank.getAccountList();
		bank.withdraw(11, 500000);
	}

}
