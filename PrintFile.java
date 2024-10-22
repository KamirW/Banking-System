import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Print the contents of an text file.
 * 
 * @author dtsmith
 *
 */
public class PrintFile {
	public static void main(String[] args) {
		try {

			Scanner scnr = new Scanner(new File(args[0]));

			while (scnr.hasNextLine()) {
				String line = scnr.nextLine();
				System.out.println(line);
			}
		} catch (FileNotFoundException e) {
		   System.out.printf("File %s not found\n", args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
