import java.util.Scanner;
import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Arrays;
import java.io.File;

/**
 * TestAccount is a class to unit test the CheckingAccount and SavingsAccount classes. TestAccount will
 * read from standard input an account type, account number and balance followed by a series of transactions
 * where each transaction input is a transaction code, transcaction date, and an amount.  If account type is
 * a "C" a CheckingAccount will be instatiated.  Likewise if account type is a "S" a SavingsAccount will 
 * be instantiated.  For each transaction, if the transaction code is a "D" the deposit method will be called
 * on the account passing the transacton date, a description of "deposit", and the amount.  If the transaction 
 * code is a "W" the withdraw method will be called on the account passing the transacton date, a description
 * of "withdraw", and the amount.
 * 
 * Java reflection is used to avoid compile time errors. Missing methods are thus detected at runtime.
 * 
 * TestAccount has three optional command line arguments.
 *    WithdrawLabel - option to include a test of the method getWithdrawLabel.
 *    ShowTransactions - option to show all transaction posted to the account.
 *    EndOfMonth - option to call endOfMonth method prior to showing transaction.
 *    File - option to read from a file instead of MainRunner standard input.  A file name must follow.
 * 
 * @author David T. Smith
 */

public class TestAccount {
   private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

   public static void main(String[] args) {
      List<?> argsList = Arrays.asList(args);
      
      try {
         Scanner s;
      
         if (argsList.contains("File")) {
            int inx = argsList.indexOf("File");
            s = new Scanner(new File(args[inx + 1]));
         } else {
            s = MainRunner.getScanner();
         }
      
         String type = s.next();
         int accountNo = s.nextInt();
         double balance = s.nextDouble();
   
         Class<?> accountClass = null;
      
         if (type.equals("C")) {
            accountClass = CheckingAccount.class;
         } else if (type.equals("S")) {
            accountClass = SavingsAccount.class;
         }
      
         Constructor<?> constructor = accountClass.getConstructor(int.class, double.class);

         // Create the instance of CheckingAccount of Savings account depending on accountClass
         Account account = (Account) constructor.newInstance(accountNo, balance);
      
         System.out.printf("Account Type:  %s\n", accountClass.getMethod("getAccountType").invoke(account));
         if (argsList.contains("WithdrawLabel")){
             System.out.printf("Withdraw Lbl:  %s\n", accountClass.getMethod("getWithdrawLabel").invoke(account));
         }
         System.out.printf("Account No:    %d\n", accountClass.getMethod("getAccountNo").invoke(account));
         System.out.printf("Start Balance: %.2f\n", accountClass.getMethod("getBalance").invoke(account));
         
         // Read and execute transactions
         while (s.hasNext()) {
            type = s.next();
            String dateString = s.next();
            double amount = s.nextDouble();
            
            LocalDate date = LocalDate.parse(dateString, dateTimeFormatter);
            
            if (type.charAt(0) == 'D') {
               accountClass.getMethod("deposit", LocalDate.class, String.class, double.class).invoke(account, date, "deposit", amount);
            } else if (type.charAt(0) == 'W') {
               accountClass.getMethod("withdraw", LocalDate.class, String.class, double.class).invoke(account, date, "withdraw", amount);
            }
         }
         
         if (argsList.contains("EndOfMonth")){
             accountClass.getMethod("endOfMonth").invoke(account);
         }
                 
         System.out.printf("End Balance:   %.2f\n", accountClass.getMethod("getBalance").invoke(account));
         System.out.println();
         
         if (argsList.contains("ShowTransactions")) {
            Class<?> transactionClass = Transaction.class;

            int noTransactions = ((Integer) accountClass.getMethod("getNoTransactions").invoke(account));
         
            System.out.printf("No Trans.:   %d\n", noTransactions);
            System.out.println();
           
            for (int i = 0; i < noTransactions; i++) {
               Transaction transaction = (Transaction) accountClass.getMethod("getTransaction", int.class).invoke(account, i);
               System.out.printf("Description: %s\n", transactionClass.getMethod("getDescription").invoke(transaction));
               System.out.printf("Date:        %s\n", transactionClass.getMethod("getDate").invoke(transaction));
               System.out.printf("Amount:      %.2f\n", transactionClass.getMethod("getAmount").invoke(transaction));
               System.out.printf("Balance:     %.2f\n", transactionClass.getMethod("getBalance").invoke(transaction));
               System.out.println();
            }
         }         
      } catch (InvocationTargetException e) {
    	  System.out.println(e.getTargetException().toString());
    	  e.getTargetException().printStackTrace();
      } catch (NoSuchMethodException e) {
          String msg = e.toString().replace("java.lang.NoSuchMethodException: ", "");
          
          if (msg.contains(".<init>(")) {
                msg = "new " + msg.replace(".<init>(","(");
          }
             
          System.out.println("Missing: " + msg);
      } catch (Exception e) {
            e.printStackTrace();
      }
   }
}
