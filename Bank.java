import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.time.LocalDate;

/**
 * This class handles the managing of accounts as well as
 * their input into files.
 * 
 * @author Kamir Walton
 */
public class Bank {
	private ArrayList<Account> accounts = new ArrayList<Account>();
	
	/**
	 * Constant that hold the check fee amount
	 */
	public static final double CHECK_FEE = 0.10;
	
	/**
	 * Constant to hold the interest rate to be applied to
	 * accounts
	 */
	public static final double INTEREST_RATE = 0.04;
	
	/**
	 * Constant to hold the overdraft fee to be applied to
	 * checking accounts
	 */
	public static final double OVERDRAFT_FEE = 15.00;
	

	/**
	 * This is the main method that calls all of the other methods
	 * 
	 * @param args - the args are: [account file] [transactions file] [new accounts file]
	 */
	public static void main(String[] args) {
	   try {
   		Bank bank = new Bank();
   		bank.readAccounts(args[0]);
   		 bank.executeTransactions(args[1]);
   		 bank.endOfMonth();
   		 bank.printStatements();
   		 bank.writeAccounts(args[2]);
	   } catch (Exception e) {
	      e.printStackTrace();
	   }
	}
	
	/**
	 * Retrieves the static date of the statement
	 * 
	 * @return the date of the statement
	 */
	public static LocalDate getStatementDate() {
		return LocalDate.of(2021, 11, 27);
	}
	
	/**
	 * Adds an account to the account list
	 * 
	 * @param a an account
	 */
	public void addAccount(Account a) {
		accounts.add(a);
	}
	
	/**
	 * Retrieves the number of accounts in the list
	 * 
	 * @return the number of accounts
	 */
	public int getNoAccounts() {
		return accounts.size();
	}

	/**
	 * Retrieves a specific account
	 * 
	 * @param inx the index of the account
	 * @return the account at the specified index
	 */
	public Account getAccount(int inx) {
		return accounts.get(inx);
	}
	
	/**
	 * Retrieves a specific account based on its account number
	 * 
	 * @param accountNo the account number of the account
	 * @return the account that matches the account number 
	 */
	public Account lookupAccount(int accountNo) {
		for(Account a : accounts) {
			if(a.getAccountNo() == accountNo) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Pulls account information from a file and creates new accounts from it
	 * 
	 * @param fileName the name of the file to be read from
	 * @throws FileNotFoundException
	 */
	public void readAccounts(String fileName) throws FileNotFoundException {
		AccountScanner scan = new AccountScanner(fileName);
		Account a = scan.readAccount();
		
		while(a != null) {
			addAccount(a);
			a = scan.readAccount();
		}
		
		scan.close();
	}
	
	/**
	 * Reads transaction data from a file and creates new transactions from that data.
	 * Additionally, it applies the transaction to the account specified in the file.
	 * 
	 * @param fileName the name of the file to be read from
	 * @throws FileNotFoundException
	 */
	public void executeTransactions(String fileName) throws FileNotFoundException {
		TransactionScanner scan = new TransactionScanner(fileName);
		Transaction t = scan.readTransaction();
		
		while(t != null) {
			Account a = lookupAccount(t.getAccountNo());
			if(t.getDescription() == "Deposit") {
				a.deposit(t.getDate(), "Deposit", t.getAmount());
				
			} else {
				a.withdraw(t.getDate(), a.getWithdrawLabel(), t.getAmount());
			}
			
			t = scan.readTransaction();
		}
	}
	
	/**
	 * Calls the endOfMonth method on all accounts
	 */
	public void endOfMonth() {
		for(Account a : accounts) {
			a.endOfMonth();
		}
	}
	
	/**
	 * Prints specified information from all accounts
	 */
	public void printStatements() {
		
		for(Account a : accounts) {
			System.out.printf("Account:   %d\n", a.getAccountNo());
			System.out.printf("Type:      %s\n", a.getAccountType());
			System.out.printf("Balance:   $%,8.2f\n", a.getBalance());
			System.out.println();
			System.out.println("    Date          Transaction      Amount      Balance");
			
			for(int i = 0; i < a.getNoTransactions(); i++) {
				Transaction t = a.getTransaction(i);
				int month = t.getDate().getMonthValue();
				int day = t.getDate().getDayOfMonth();
				int year = t.getDate().getYear();
				System.out.printf("  %2d/%2d/%d    %-13s    $%,9.2f  $%,9.2f\n", month, day, year, t.getDescription(), t.getAmount(), t.getBalance());
			}
			
			System.out.println();
		}
	}
	
	/**
	 * Creates a new file where account information is placed into
	 * 
	 * @param fileName the name of the file to be created
	 * @throws FileNotFoundException
	 */
	public void writeAccounts(String fileName) throws FileNotFoundException {
		AccountWriter aWriter = new AccountWriter(fileName);
		
		for(Account a : accounts) {
			aWriter.writeAccount(a);
		}
		
		aWriter.close();
	}
	
	
}