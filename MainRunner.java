
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * MainRunner provides emulation of command line execution of Java programs.
 * Each line is processed as if entered into a command prompt.  The second
 * Argument is the name of a class run.  Reflection is used to call the
 * main method of the class passed the remaining command line arguments.
 * 
 * @author dtsmith

 */
public class MainRunner {
   private static Scanner scnr;
   
   public static void main(String[] args) {
      scnr = new Scanner(System.in);
      String priorProgram = "";

      while (scnr.hasNext()) {
         String line = scnr.nextLine().trim();
         if (line.length() == 0) {
            System.out.println();
            continue;
         }
         String[] fields = line.split(" ");
         
         if (!fields[0].equals("java")) {
            System.out.println("Invalid MainRunner command " + fields[0] + " - input may be out of sync due to execution of " + priorProgram);
            return;
         }
         
         if (fields.length < 2) { 
            System.out.println("Invalid MainRunner command missing class name - input may be out of sync due to execution of " + priorProgram);
            return;
         }
            
         Object[] newArgs = new String[fields.length - 2];
         System.arraycopy(fields, 2, newArgs, 0, newArgs.length);
         try {
            Class<?> cls = Class.forName(fields[1]);
            if (cls == null) {
               System.out.println("Invalid MainRunner command class " + fields[1] + " - input may be out of sync due to execution of " + priorProgram);
               return;
            }
            Method method = cls.getMethod("main",String[].class);
            method.invoke(null, new Object[] { newArgs});
         } catch (Exception e) {
            e.printStackTrace();
            return;
         }
      }
   }
   
   public static Scanner getScanner() {
      return scnr;
   }
}