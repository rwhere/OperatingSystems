import java.lang.Process;
import java.lang.ProcessBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ProcessTest
{
  public static void main(String[] args) throws Exception
  {
    Process p = new ProcessBuilder("java").start();
    InputStream is = p.getErrorStream();
    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
    BufferedReader br = new BufferedReader(isr);
    String line;
    line = br.readLine();
    while(line!=null)
    {
      System.out.println(line);
      line = br.readLine();
    }
  }
}
