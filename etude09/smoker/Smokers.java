import java.util.*;
import java.io.*;

public class Smokers extends Node{
  private static int heuristic;
  private static LinkedList<Node> queue = new LinkedList<Node>();
  
  public static void main(String[]args) throws FileNotFoundException, IOException{
    File file = new File(args[0]);
    Scanner scan = new Scanner(file);
    while (true){
      int x = 0;
      int y = 0;
      if (scan.hasNextLine()){
        x = scan.nextInt();
        y = scan.nextInt();
      }
      Node[][] smokers = new Node[x][y];
      for (int i = 0; i < y; i++){
        for (int j = 0; j < x; j++){
          smokers[i][j] = new Node(i, j, false);
        }
      }
      heuristic = x+y;
      scan.nextLine();
      while (scan.hasNextLine()){
        String line = scan.nextLine();
        if (!line.isEmpty()){
          String[] coords = line.split("\\s+");
          int i = Integer.parseInt(coords[0]);
          int j = Integer.parseInt(coords[1]);
          smokers[j][i].setX(i);
          smokers[j][i].setY(j);
          smokers[j][i].setBlocked();
          queue.add(smokers[j][i]);
        } else {
          break;
        }
      }
      voronoi(smokers);
      printWorld(smokers);
      if (!scan.hasNextLine()){
        scan.close();
        return;
      }
    }
  }
  
  public static void voronoi(Node[][] smokers){
    Node[][] temp = smokers;
    while (queue.size() > 0){
      Node current = queue.remove();
      if (current.getX()+1 < smokers[0].length &&!smokers[current.getY()][current.getX()+1].getBlocked()){
        smokers[current.getY()][current.getX()+1].setBlocked();
        if (!pathfinder(smokers)){
          smokers[current.getY()][current.getX()+1].setBlocked();
        } else {
          queue.add(smokers[current.getY()][current.getX()+1]);
        }
      }
      if (current.getX()-1 > -1 && !smokers[current.getY()][current.getX()-1].getBlocked()){
        smokers[current.getY()][current.getX()-1].setBlocked();
        if (!pathfinder(smokers)){
          smokers[current.getY()][current.getX()-1].setBlocked();
        } else {
          queue.add(smokers[current.getY()][current.getX()-1]);
        }
      }
      if (current.getY()+1 < smokers.length && !smokers[current.getY()+1][current.getX()].getBlocked()){
        smokers[current.getY()+1][current.getX()].setBlocked();
        if (!pathfinder(smokers)){
          smokers[current.getY()+1][current.getX()].setBlocked();
        } else {
          queue.add(smokers[current.getY()+1][current.getX()]);
        }
      }
      if (current.getY()-1 > -1 && !smokers[current.getY()-1][current.getX()].getBlocked()){
        smokers[current.getY()-1][current.getX()].setBlocked();
        if (!pathfinder(smokers)){
          smokers[current.getY()-1][current.getX()].setBlocked();
        } else {
          queue.add(smokers[current.getY()-1][current.getX()]);
        }
      }
    }
    for (int x = 0; x < smokers[0].length; x++){
      for (int y = 0; y < smokers.length; y++){
        if (smokers[y][x].getBlocked() != temp[y][x].getBlocked()){
          voronoi(smokers);
          return;
        }
      }
    }
  }
  
  public static boolean pathfinder(Node[][] smokers){
    LinkedList<Node> open = new LinkedList<Node>();
    ArrayList<Node> closed = new ArrayList<Node>();
    ArrayList<Node> successors = new ArrayList<Node>();
    if (!smokers[0][0].getBlocked()){
      open.add(smokers[0][0]);
    }
    while (!open.isEmpty()){
      Node current;
      successors.clear();
      if (open.size() == 1){
        current = open.remove();
      } else {
        current = open.get(0);
        int j = 0;
        for (int i = 1; i < open.size(); i++){
          if (heuristic - (current.getX() + current.getY()) > heuristic - (open.get(i).getX() + open.get(i).getY())){
            current = open.get(i);
            j = i;
          }
        }
        current = open.remove(j);
      }
      if (current.getX()+1 < smokers[0].length && !smokers[current.getX()+1][current.getY()].getBlocked()){
        for (Node c : closed){
          if (c.getX() == smokers[current.getX()+1][current.getY()].getX() && c.getY() == smokers[current.getX()+1][current.getY()].getY()){
            break;
          }
        }
        smokers[current.getX()+1][current.getY()].setParent(current);
        successors.add(smokers[current.getX()+1][current.getY()]);
      }
      if (current.getX()-1 > -1 && !smokers[current.getX()-1][current.getY()].getBlocked()){
        for (Node c : closed){
          if (c.getX() == smokers[current.getX()-1][current.getY()].getX() && c.getY() == smokers[current.getX()-1][current.getY()].getY()){
            break;
          }
        }
        smokers[current.getX()-1][current.getY()].setParent(current);
        successors.add(smokers[current.getX()-1][current.getY()]);
      }
      if (current.getY()+1 < smokers.length && !smokers[current.getX()][current.getY()+1].getBlocked()){
        for (Node c : closed){
          if (c.getX() == smokers[current.getX()][current.getY()+1].getX() && c.getY() == smokers[current.getX()][current.getY()+1].getY()){
            break;
          }
        }
        smokers[current.getX()][current.getY()+1].setParent(current);
        successors.add(smokers[current.getX()][current.getY()+1]);
      }
      if (current.getY()-1 > -1 && !smokers[current.getX()][current.getY()-1].getBlocked()){
        for (Node c : closed){
          if (c.getX() == smokers[current.getX()][current.getY()-1].getX() && c.getY() == smokers[current.getX()][current.getY()-1].getY()){
            break;
          }
        }
        smokers[current.getX()][current.getY()-1].setParent(current);
        successors.add(smokers[current.getX()][current.getY()-1]);
      }
      for (Node successor : successors){
        boolean found = false;
        if (successor.getX() == smokers[0].length-1 && successor.getY() == smokers.length-1){
          return true;
        }
        for (Node o : open){
          if (heuristic - (o.getX() + o.getY()) < heuristic - (successor.getX() + successor.getY())){
            found = true;
            break;
          }
        }
        for (Node c : closed){
          if (heuristic - (c.getX() + c.getY()) < heuristic - (successor.getX() + successor.getY())){
            found = true;
            break;
          }
        }
        if (found == false){
          open.add(successor);
        }
      }
      closed.add(current);
    }
    return false;
  }
  
  public static void printWorld(Node[][] smokers){
    for (Node[] row : smokers){
      for (Node n : row){
        System.out.print(n.getBlocked() + " ");
      }
      System.out.println();
    }
    System.out.println();
  }
}