import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This is a class to create the outline of what an account should be.
 * All the base methods are there but there are abstract methods that 
 * need to be implemented.
 * 
 * @author Kamir Walton
 *
 */
public abstract class Account {

	private int accountNo;
	private double balance;
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	/**
	 * Constructor
	 * 
	 * @param accountNo account number of the account
	 * @param balance balance of the account
	 */
	public Account(int accountNo, double balance) {
		this.accountNo = accountNo;
		this.balance = balance;
	}
	
	/**
	 * Makes balance publicly available to other classes
	 * 
	 * @return account balance
	 */
	public double getBalance() {
		return balance;
	}
	
	/**
	 * Makes account number publicly available to other classes
	 * @return account number
	 */
	public int getAccountNo() {
		return accountNo;
	}
   
	/**
	 * Adds money into the account and creates a new transaction
	 * 
	 * @param date date of the transaction
	 * @param description the type of transaction it is
	 * @param amount amount of money being put in the account
	 */
	public void deposit(LocalDate date, String description, double amount) {
		balance += amount;
		postTransaction(date, description, amount);
	}
	
	/**
	 * Takes money out of the account and creates a new transaction
	 * 
	 * @param date date of the transaction
	 * @param description the type of transaction it is
	 * @param amount amount of money that is being taken out of the account
	 */
	public void withdraw(LocalDate date, String description, double amount) {
		balance -= amount;
		postTransaction(date, description, amount);
	}
	
	/**
	 * Creates a new transaction and adds it to the list of transactions
	 * 
	 * @param date date of the transaction
	 * @param description the type of transaction 
	 * @param amount the amount of money that was exchanged
	 */
	public void postTransaction(LocalDate date, String description, double amount) {
		Transaction t = new Transaction(date, description, amount, balance);
		transactions.add(t);	
	}
	
	/**
	 * Retrieves the number of transactions that were made
	 * 
	 * @return the number of transactions
	 */
	public int getNoTransactions() {
		return transactions.size();
	}
	
	/**
	 * Retrieves a specific transaction
	 * 
	 * @param inx the index of the transaction
	 * @return the transaction
	 */
	public Transaction getTransaction(int inx) {
		return transactions.get(inx);
	}
	
	/**
	 * Abstract method to retrieve the type of account it is
	 * 
	 * @return the type of account it is
	 */
   public abstract String getAccountType();
   
   /**
    * Abstract method to retrieve the account type of a withdrawal
    * 
    * @return the type of account
    */
   public abstract String getWithdrawLabel();
   
   /**
    * Abstract method to perform calculations on accounts depending
    * on the account type
    */
   public abstract void endOfMonth();



}