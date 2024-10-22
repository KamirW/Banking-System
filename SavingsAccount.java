import java.time.LocalDate;

/**
 * This is a child class of the account class that implements the 
 * properties that a savings account should have.
 * 
 * @author Kamir Walton
 *
 */
public class SavingsAccount extends Account{

	/**
	 * Constructor
	 * 
	 * @param accountNo account number of the account
	 * @param balance balance of the account
	 */
	public SavingsAccount(int accountNo, double balance) {
		super(accountNo, balance);
	}

	/**
	 * Retrieves the type of account it is
	 * 
	 * @return type of account it is
	 */
	@Override
	public String getAccountType() {
		return "Savings";
	}
   
	/**
	 * Retrieves the type of withdrawal it is 
	 * 
	 * @return the type of withdrawal
	 */
	public String getWithdrawLabel() {
		return "Withdrawal";
	}
	
	/**
	 * Implements specific functionality for a savings account withdrawal
	 */
	@Override
	public void withdraw(LocalDate date, String description, double amount) {
		
		super.withdraw(date, description, amount >= super.getBalance() ? super.getBalance() : amount);
	}

	/**
	 * Implements the calculations to be done on a 
	 * savings account
	 */
	@Override
	public void endOfMonth() {
		if(super.getBalance() != 0) {
			super.deposit(Bank.getStatementDate(), "Interest", super.getBalance() * Bank.INTEREST_RATE);
		}
		
		
	}
   
}