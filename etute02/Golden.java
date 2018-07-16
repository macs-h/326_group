import java.util.*;

public class Golden{
  public static void main(String[]args){
    Scanner scan = new Scanner(System.in);
    while(true){
      int d = scan.nextInt();
      System.out.println(golden(d));
    }
  }
  
  public static int golden(int d){
    int h = silver(d, d);
    if (h % 12 == 0){
      return 12;
    }
    return h % 12;
  }
  
  public static int silver(int b, int c){
    if (c == 1){
      return 1;
    } else if (b == c){
      return silver(b, c-1) + 1;
    } else if (c > b){
      return golden(b);
    } else if (b > c){
      int x = silver(b-c, c);
      x += silver(b, c-1);
      return  x;
    }
    return 0;
  }
}