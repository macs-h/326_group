import java.util.*;

/** 
 * etude02 (Sage and Trewlawney)
 * 
 * @author Tom Adams
 * @author Max Huang
 * @author Mitchie Maluschnig
 * @author Asher Statham
 *
 * @since 16 July 2018
 */

public class Golden{
    static int n;
    public static void main(String[]args){
        Scanner scan = new Scanner(System.in);
        while(true){
            int d = scan.nextInt();
            n = 0;
            System.out.println("> "+(golden(d)+n)%12);
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
            n++;
            if(n>12) n = n%12;
            return silver(b, c-1);
        } else if (c > b){
            //System.out.println("b: " + b + " c: " + c);
            return golden(b);
            //return silver(b,b);
        } else if (b > c){
            return silver(b-c, c) + silver(b, c-1);
        }
        return 0;
    }
}
