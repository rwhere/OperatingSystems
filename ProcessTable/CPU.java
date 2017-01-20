import java.util.Random;

public class CPU
{
  private int pc;
  private int sp;
  private int r0;
  private int r1;
  private int r2;
  private int r3;

  public CPU()
  {
    init();
  }
  private void init()
  {
    Random rand = new Random();
    pc = rand.nextInt();
    sp = rand.nextInt();
    r0 = rand.nextInt();
    r1 = rand.nextInt();
    r2 = rand.nextInt();
    r3 = rand.nextInt();
  }
  public void update(int pc, int sp, int r0, int r1, int r2, int r3)
  {
    this.pc = pc;
    this.sp = sp;
    this.r0 = r0;
    this.r1 = r1;
    this.r2 = r2;
    this.r3 = r3;
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
}
