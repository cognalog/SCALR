package scalr.misc;


public class ScalrLogger
{
  int mode = 0;
  public ScalrLogger()
  {

  }


  //Log mode == 1 verbose
  //Log mode == 2 silent
  public void log(String statement)
  {
    if(mode == 1) {
      System.out.println(statement);
    }
  }

  public void setLogMode(int level)
  {
    mode = level;
  }
}