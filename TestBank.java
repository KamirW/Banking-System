import java.util.Scanner;
import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Arrays;
import java.io.File;

/**
 * TestBank is a class to unit test the Bank classes. TestAccount will
 * read from standard input commands/methods to be excuted against an instance of a bank. 
 * 
 * Commands/Methods are:
 *    addAccount - instantiates an account and calls addAccount to add the account to the bank. 
 *                 Arguments to follow:
 *                   "C" or "S" for CheckingAccount or SavingsAccount
 *                   accountNo
 *                   balance
 * 
 *    readAccounts - calls readAccounts method
 *                 Arguments to follow:
 *                   fileName
 * 
 *    writeAccounts - calls writeAccounts method
 *                 Arguments to follow:
 *                   fileName
 * 
 *    getAccount - calls getAccount and prints the returned account
 *                 Arguments to follow:
 *                   index
 * 
 *    showAccounts - prints all accounts in the bank
 * 
 *    deposit - looks up an account using lookupAccount and calls deposit on the returned account
 *                 Arguments to follow:
 *                   accountNo
 *                   date
 *                   amount
 * 
 *    withdraw - looks up an account using lookupAccount and calls withdraw on the returned account
 *                 Arguments to follow:
 *                   accountNo
 *                   date
 *                   amount
 *
 *    endOfMonth - call the endOfMonth method
 * 
 *    exit - exits TestBank execution so that MainRunner can proceed
 * 
 * Java reflection is used to avoid compile time errors. Missing methods are thus detected at runtime.
 * 
 * TestBank has an optional command line arguments.
 *    File - option to read from a file instead of MainRunner standard input.  A file name must follow.
 * 
 * @author David T. Smith
 */
public class TestBank {
   private static Class<?> bankClass = Bank.class;
   private static Class<?> accountClass = Account.class;
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
         
           
         Constructor<?> constructor = bankClass.getConstructor();
         
         // Create the instance of Bank
         Bank bank = (Bank) constructor.newInstance();
         
         // Process TestBank methods/commands
         while (s.hasNext()) {
            String action = s.next();

            // exit will terminate execution
            if (action.equals("exit")) { 
               break;
            }
            
            if (action.equals("addAccount")) {
               String type = s.next();
               int accountNo = s.nextInt();
               double balance = s.nextDouble();
               
               System.out.printf("Add Account %s %d %.2f\n", type, accountNo, balance);
               System.out.println();

               Class<?> accountClass = null;
      
               if (type.equals("C")) {
                  accountClass = CheckingAccount.class;
               } else if (type.equals("S")) {
                  accountClass = SavingsAccount.class;
               }
      
               Constructor<?> accountConstructor = accountClass.getConstructor(int.class, double.class);

               // Create the instance of CheckingAccount of Savings account depending on accountClass
               Account account = (Account) accountConstructor.newInstance(accountNo, balance);
               bankClass.getMethod("addAccount", Account.class).invoke(bank, account);
               continue;
            }
            
            if (action.equals("readAccounts")) {   
               String fileName = s.next();
               System.out.printf("Reading Accounts from %s\n", fileName);
               System.out.println();

               bankClass.getMethod("readAccounts", String.class).invoke(bank, fileName);
               continue;
            }

            if (action.equals("getAccount")) {   
               int inx = s.nextInt();
               Account account = (Account) bankClass.getMethod("getAccount", int.class).invoke(bank, inx);
               System.out.printf("Account @inx:  %d\n", inx);

               printAccount(account);
               continue;
            }
            
            if (action.equals("showAccounts")) {
               Class<?> accountClass = Account.class;

               int noAccounts = ((Integer) bankClass.getMethod("getNoAccounts").invoke(bank));
         
               System.out.printf("No Accounts: %d\n", noAccounts);
               System.out.println();
           
               for (int i = 0; i < noAccounts; i++) {
                  Account account = (Account) bankClass.getMethod("getAccount", int.class).invoke(bank, i);
                  printAccount(account);
               }
               continue;
            }
            
            if (action.equals("deposit") || action.equals("withdraw")) {
               int accountNo = s.nextInt();
               String dateString = s.next();
               double amount = s.nextDouble();
               
               System.out.printf("%s %d $ %.2f\n", action.equals("deposit") ? "Deposit to" : "Withdraw from", accountNo, amount);
               System.out.println();
               
               LocalDate date = LocalDate.parse(dateString, dateTimeFormatter);
               
               Method method =  bankClass.getMethod("lookupAccount", int.class);

               Account account = (Account) method.invoke(bank, accountNo);
           
               if (action.equals("deposit")) {
                  accountClass.getMethod("deposit", LocalDate.class, String.class, double.class).invoke(account, date, "deposit", amount);
               } else {
                  accountClass.getMethod("withdraw", LocalDate.class, String.class, double.class).invoke(account, date, "withdraw", amount);
               }
               continue;
            }

            if (action.equals("endOfMonth")){
               System.out.println("End Of Month");
               System.out.println();
             
               bankClass.getMethod("endOfMonth").invoke(bank);
               continue;
            }
            
            if (action.equals("writeAccounts")) {   
               String fileName = s.next();
               System.out.printf("Writing Accounts to %s\n", fileName);
               System.out.println();
               
               bankClass.getMethod("writeAccounts", String.class).invoke(bank, fileName);
               continue;
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
   
   private static void printAccount(Account account) throws Exception {
      System.out.printf("Account Type:  %s\n", accountClass.getMethod("getAccountType").invoke(account));
      System.out.printf("Account No:    %d\n", accountClass.getMethod("getAccountNo").invoke(account));
      System.out.printf("Balance:       %.2f\n", accountClass.getMethod("getBalance").invoke(account));
      System.out.printf("No Trans.:     %d\n", accountClass.getMethod("getNoTransactions").invoke(account));

      System.out.println();
   }     
}
