import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;
/**
 * This is a helper class that handles the reading of a file
 * containing transaction data.
 * 
 * @author Kamir Walton
 *
 */
public class TransactionScanner {
   
	private Scanner in;
	
	/**
	 * Constructor - opens a file
	 * 
	 * @param fileName the name of the file to be opened
	 * @throws FileNotFoundException
	 */
	public TransactionScanner(String fileName) throws FileNotFoundException {
		this.in = new Scanner(new File(fileName));
	}
	
	/**
	 * Reads the data from the file that was opened and creates a new transaction 
	 * based on that data
	 * 
	 * @return the newly created transaction
	 */
	public Transaction readTransaction() {
		if(in.hasNextLine()) {
			String line = in.nextLine();
			String data[] = line.split("[ ]+");
			String date[] = data[1].split("/");
			int year = Integer.parseInt(date[2]);
			int month = Integer.parseInt(date[0]);
			int day = Integer.parseInt(date[1]);
			String description = data[2].equals("W") ? "Withdraw" : "Deposit";
			Transaction t = new Transaction(Integer.parseInt(data[0]), LocalDate.of(year, month, day), description, Double.parseDouble(data[3]));
			return t;
		}
		return null;
	}
	
	/**
	 * Closes the file - no more information can be read from it
	 */
	public void close() {
		in.close();
	}
}