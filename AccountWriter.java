import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This is a helper class that handles the creation of a file as
 * well as the input of information into that file.
 * 
 * @author Kamir Walton
 *
 */
public class AccountWriter {
  
	private PrintWriter out;
	
	/**
	 * Constructor - creates a new file to be written to
	 * 
	 * @param fileName the name of the file to be written to
	 * @throws FileNotFoundException
	 */
	public AccountWriter(String fileName) throws FileNotFoundException {
		this.out = new PrintWriter(new File(fileName));
	}
	
	/**
	 * Using information from an account, it creates a file and inputs the 
	 * data of that account
	 * 
	 * @param a an account for data to be read from
	 */
	public void writeAccount(Account a) {
		char accountType = a.getAccountType().charAt(0);
		int accountNo = a.getAccountNo();
		double balance = a.getBalance();
		out.printf("%c %d %.2f\n", accountType, accountNo, balance);
	}
	
	/**
	 * Closes the file - no more information can be put into the file
	 */
	public void close() {
		out.close();
	}
   
}