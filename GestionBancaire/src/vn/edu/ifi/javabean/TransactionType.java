package vn.edu.ifi.javabean;

public enum TransactionType {
	RETRAIT("RETRAIT"), DEPOT("DEPOT");
	private String type = "";

	TransactionType(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}
}
