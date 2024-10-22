import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is a helper class that handles the reading of a file
 * and the processing of that information.
 * 
 * @author Kamir Walton
 *
 */
public class AccountScanner {

	private Scanner in;
	
	/**
	 * Constructor - creates a new scanner to read the file
	 * 
	 * @param fileName the name of the file to be read
	 * @throws FileNotFoundException 
	 */
	public AccountScanner(String fileName) throws FileNotFoundException {
		this.in = new Scanner(new File(fileName));
	}
	
	/**
	 * Reads the file and creates either a checking account or savings account
	 * based on the information in the file
	 * 
	 * @return the new account that was created
	 */
	public Account readAccount() {
		if(in.hasNextLine()) {
			String[] line = in.nextLine().split("[ ]+");
			String type = line[0];
			int accountNo = Integer.parseInt(line[1]);
			double balance = Double.parseDouble(line[2]);
			Account a = type.equals("C") ? new CheckingAccount(accountNo, balance) : 
										new SavingsAccount(accountNo, balance);
			return a;
			
		}
		return null;
	}
	
	/**
	 * Closes the file - no more information can be read from it now
	 */
	public void close() {
		in.close();
	}
}