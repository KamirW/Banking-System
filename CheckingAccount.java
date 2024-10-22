import java.time.LocalDate;

/**
 * This class is a child of the account class and gives the 
 * properties of what a checking account should have.
 * 
 * @author Kamir Walton
 *
 */
public class CheckingAccount extends Account{
   
	/**
	 * Constructor
	 * 
	 * @param accountNo account number of the account
	 * @param balance balance of the account
	 */
	public CheckingAccount(int accountNo, double balance) {
		super(accountNo, balance);
	}

	/**
	 * Retrieves the type of account it is
	 * 
	 * @return the type of account
	 */
	@Override
	public String getAccountType() {
		return "Checking";
	}

	/**
	 * Retrieves the type of withdrawal it is
	 * 
	 * @return the type of withdrawal it is
	 */
	@Override
	public String getWithdrawLabel() {
		return "Check";
	}
	
	/**
	 * Implements how the withdraw method should be handled for 
	 * checking accounts
	 * 
	 * @param date the date of the transaction
	 * @param description the type of withdrawal it is
	 * @param amount the amount of money that was removed from the account
	 */
	@Override 
	public void withdraw(LocalDate date, String description, double amount) {
		
		if (this.getBalance() - amount < 0) {
			super.withdraw(date, description, amount);
			super.withdraw(date, "Overdraft fee", 15);
			
		}
		else {
			super.withdraw(date, description, amount);
		}
	}

	/**
	 * Implements the calculations to be done on a checking account 
	 * when it is the end of the month
	 */
	@Override
	public void endOfMonth() {
		int checks = 0;
		for(int i = 0; i < super.getNoTransactions(); i++) {
			if(super.getTransaction(i).getDescription() == "Check" || super.getTransaction(i).getDescription() == "withdraw") {
				checks++;
			}
		}
		if (checks > 0) {
			super.withdraw(Bank.getStatementDate(), "Check fees", Bank.CHECK_FEE * checks);
		}
	}
	
   
}