import java.io.*;
public class RunTestSuite {
  public static void main (String[] args) {
    File dir = new File("tests-that-should-succeed");
    String end = "\033[0m\n";
    String green = "\033[92m";
    String red = "\033[91m";
    System.out.println("\033[95m" + "Starting tests that should succeed.. "+end);
    for (File child : dir.listFiles()) {
      for(File child2 : child.listFiles()){
        String filename = child2.toString();
        int dotPosition = filename.lastIndexOf(".");
        String extension = "";
        if (dotPosition != -1) {
            extension = filename.substring(dotPosition);
        }
        if(extension.equals(".tscalr")) {
          String output = "";
          try {
            System.out.println("\033[95m" + child2 +end);
            Process p = Runtime.getRuntime().exec("make -C ../parser run f='../test/"+child2+"'");
            BufferedReader stdInput = new BufferedReader(new 
                   InputStreamReader(p.getInputStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                  output = s;
              }
            System.out.println();
          }
          catch (IOException e) {

          }
          String strLine = null, tmp;
          try {
            FileInputStream in = new FileInputStream(child + "/" + "expected-output");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
            while ((tmp = br.readLine()) != null)
            {
               strLine = tmp;
            }
          }
          catch (IOException e) {

          }
         
          String lastLine = strLine;
         
          if(lastLine.equals(output)) {
            System.out.println(green + "TEST PASSED WOOOOOOOOOOOOOOO" + end);
          }
          else {
            System.out.println(red + "Test failed.");
            System.out.println(red + "Expected output: " + lastLine);
            System.out.println(red + "Got: " + output);
          }
        }
      }
    } 
  }
}