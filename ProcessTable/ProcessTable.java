import java.util.Map;
import java.util.HashMap;
import java.lang.Runnable;
import java.util.Scanner;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class ProcessTable
{
  //member variables
  private CPU cpu;
  private int idcount;
  private int currentpid;
  private Process currentProcess;
  private Map<Integer, Process> processes;
  private static Map<String, Runnable> commands;
  private String[] words;

  public static void main(String[] args)
  {
    ProcessTable table = new ProcessTable();
    Scanner s = new Scanner(System.in);
    String line;
    while(true)
    {
      System.out.print("> ");
      line = s.nextLine();
      String[] words = line.split("\\s");
      table.setWords(words);
      if(commands.get(words[0])!=null)
        commands.get(words[0]).run();
      else if(line.equals("quit()"))
        System.exit(0);
      else
        System.out.println("Invalid command.");
    }
  }
  public ProcessTable()
  {
    idcount = 1;
    cpu = new CPU();
    processes = new HashMap<>();
    commands = new HashMap<>();
    words = null;
    init();
    fillCommands();
  }
  private void init()
  {
    currentProcess = new Process(1, "root", "init", 0);
    processes.put(1, currentProcess);
    updateCPUFromCurrentProcess();
  }
  public void setWords(String[] words)
  {
    this.words = words;
  }
  private Process randomProcess()
  {
    Process p;
    Random r = new Random();
    List<Integer> keysAsArray = new ArrayList<Integer>(processes.keySet());
    do
    {
      p = processes.get(keysAsArray.get(r.nextInt(keysAsArray.size())));
    } while(p.getStatus()!=1);
    return p;
  }
  private void fillCommands()
  {
    commands.put("fork", () -> {
      ++idcount;
      processes.put(idcount , new Process(currentProcess, idcount));
    });
    commands.put("kill", () -> {
      int pid = Integer.valueOf(words[1]).intValue();
      Process p = processes.get(pid);
      if(currentProcess == p)
      {
        currentProcess = randomProcess();
        currentProcess.setStatus(0);
        updateCPUFromCurrentProcess();
        processes.remove(pid);
      }
      else if(currentProcess.getUser()=="root" || currentProcess.getUser()==p.getUser())
        processes.remove(pid);
    });
    commands.put("execve", () -> {
      if(currentProcess.getUser().equals("root")
        || currentProcess.getUser().equals(words[2]))
      {
        currentProcess.setUser(words[2]);
        currentProcess.setProgram(words[1]);
        currentProcess.randomizeRegisters();
        updateCPUFromCurrentProcess();
      }
    });
    commands.put("block", () -> {
      updateCurrentProcessFromCPU();
      currentProcess.setStatus(2);
      currentProcess = randomProcess();
      currentProcess.setStatus(0);
      updateCPUFromCurrentProcess();
    });
    commands.put("yield", () -> {
      Process temp = currentProcess;
      updateCurrentProcessFromCPU();
      currentProcess = randomProcess();
      temp.setStatus(1);
      currentProcess.setStatus(0);
      updateCPUFromCurrentProcess();
    });
    commands.put("exit", () -> {
      processes.remove(currentProcess.get_pid());
      currentProcess = randomProcess();
      currentProcess.setStatus(0);
      updateCPUFromCurrentProcess();
    });
    commands.put("print", () -> {
      System.out.print("CPU:\n");
      System.out.printf(" PC = 0x%08X", cpu.get_pc());
      System.out.printf(" SP = 0x%08X\n", cpu.get_sp());
      System.out.printf(" R0 = 0x%08X", cpu.get_r0());
      System.out.printf(" R1 = 0x%08X\n", cpu.get_r1());
      System.out.printf(" R2 = 0x%08X", cpu.get_r2());
      System.out.printf(" R3 = 0x%08X\n", cpu.get_r3());
      System.out.print("Process Table:\n");
      System.out.format(" %s%10s%8s%10s%13s%11s%11s%11s%11s%11s\n", "PID",
        "Program", "User", "Status", "PC", "SP", "R0", "R1", "R2", "R3");
      for(Process v : processes.values())
      {
        System.out.format(" %d%10s%10s%10d   ", v.get_pid(), v.getProgram(),
          v.getUser(), v.getStatus());
        System.out.format("0x%08X 0x%08X 0x%08X 0x%08X 0x%08X 0x%08X\n", v.get_pc(),
          v.get_sp(), v.get_r0(), v.get_r1(), v.get_r2(), v.get_r3());
      }
      System.out.print("\n");
    });
    commands.put("unblock", () -> {
      int pid = Integer.valueOf(words[1]).intValue();
      processes.get(pid).setStatus(1);
    });
  }
  private void updateCurrentProcessFromCPU()
  {
    currentProcess.set_pc(cpu.get_pc());
    currentProcess.set_sp(cpu.get_sp());
    currentProcess.set_r0(cpu.get_r0());
    currentProcess.set_r1(cpu.get_r1());
    currentProcess.set_r2(cpu.get_r2());
    currentProcess.set_r3(cpu.get_r3());
  }
  private void updateCPUFromCurrentProcess()
  {
    cpu.update(currentProcess.get_pc(), currentProcess.get_sp(),
      currentProcess.get_r0(), currentProcess.get_r1(),
      currentProcess.get_r2(), currentProcess.get_r3());
  }
}
