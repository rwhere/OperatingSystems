import java.util.Random;

public class Process
{
  //member variables
  private int pid;
  private String program;
  private String user;
  private int status;
  /*
  0 indicates the program is running, a 1 indicates the process is ready,
  and a 2 indicates the process is blocked.
  */
  private int pc;
  private int sp;
  private int r0;
  private int r1;
  private int r2;
  private int r3;

  public Process(Process p, int pid)
  {
    this.pid = pid;
    this.program = p.program;
    this.user = p.user;
    this.status = 1;
    this.pc = p.pc;
    this.sp = p.sp;
    this.r0 = p.r0;
    this.r1 = p.r1;
    this.r2 = p.r2;
    this.r3 = p.r3;
  }
  public Process(int pid, String user, String program, int status)
  {
    this.pid = pid;
    this.program = program;
    this.user = user;
    this.status = status;
    randomizeRegisters();
  }
  public String getProgram()
  {
    return program;
  }
  public String getUser()
  {
    return user;
  }
  public int getStatus()
  {
    return status;
  }
  public int get_pid()
  {
    return pid;
  }
  public int get_pc()
  {
    return pc;
  }
  public int get_sp()
  {
    return sp;
  }
  public int get_r0()
  {
    return r0;
  }
  public int get_r1()
  {
    return r1;
  }
  public int get_r2()
  {
    return r2;
  }
  public int get_r3()
  {
    return r3;
  }
  public void set_pc(int pc)
  {
    this.pc = pc;
  }
  public void set_sp(int sp)
  {
    this.sp = sp;
  }
  public void set_r0(int r0)
  {
    this.r0 = r0;
  }
  public void set_r1(int r1)
  {
    this.r1 = r1;
  }
  public void set_r2(int r2)
  {
    this.r2 = r2;
  }
  public void set_r3(int r3)
  {
    this.r3 = r3;
  }
  public void setUser(String user)
  {
    this.user = user;
  }
  public void setProgram(String program)
  {
    this.program = program;
  }
  public void randomizeRegisters()
  {
    Random rand = new Random();
    pc = rand.nextInt();
    sp = rand.nextInt();
    r0 = rand.nextInt();
    r1 = rand.nextInt();
    r2 = rand.nextInt();
    r3 = rand.nextInt();
  }
  public void setStatus(int status)
  {
    this.status = status;
  }
}
