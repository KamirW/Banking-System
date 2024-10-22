import java.time.LocalDate;

/**
 * Transaction. Holds information for a transction to be posted against an
 * Account.  Transaction is addtionaly used to record a posted transaction.
 * 
 * @author David T. Smith
 *
 */
public class Transaction {
	private int accountNo;
	private LocalDate date;
	private String description;
	private double amount;
	private double balance;

	/**
	 * This is the constructor for a Transaction on input.  Balanace is
	 * not used for input transactions.
	 * 
	 * @param accountNo   - the number of the bank account
	 * @param date        - the date of the transaction happened
	 * @param description - on input description is either "W" or "D"
	 * @param amount      - the amount of money involved in the transaction
	 */
	public Transaction(int accountNo, LocalDate date, String description, double amount) {
		this.accountNo = accountNo;
		this.date = date;
		this.description = description;
		this.amount = amount;
	}

	/**
	 * This is the constructor for a the Transaction posted to an account.  Account number is 
	 * not used for posted transactions as these transactions are added to the account.
	 * 
	 * @param date        - the date of the transaction happened
	 * @param description - transction description (e.g. "Deposit", "Withdraw", ...)
	 * @param amount      - the amount of money involved in the transaction
	 * @param balance     - the account balance after processing the transaction
	 */
	public Transaction(LocalDate date, String description, double amount, double balance) {
		this.date = date;
		this.description = description;
		this.amount = amount;
		this.balance = balance;
	}

	/**
	 * This method retrieves the account number
	 * 
	 * @return - the account number
	 */
	public int getAccountNo() {
		return accountNo;
	}

	/**
	 * This method retrieves the transaction date
	 * 
	 * @return - the date of transaction
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * This method returns the description of the transaction
	 * 
	 * @return - the description of the transaction
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This method updates the description of the transaction
	 * 
	 * @param description - the updated description of the transaction
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * This method returns the amount being used in the transaction
	 * 
	 * @return - the transaction amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * This method returns the balance that is recorded upon executing the
	 * transaction.
	 * 
	 * @return - the recorded balance after transaction was executed.
	 */
	public double getBalance() {
		return balance;
	}
}