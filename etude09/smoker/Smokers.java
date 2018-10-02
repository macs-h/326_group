import java.util.*;
import java.io.*;

public class Smokers extends Node{
  private static int heuristic;
  private static LinkedList<Node> queue = new LinkedList<Node>();
  private static int x, y, path;
  
  public static void main(String[]args) throws FileNotFoundException, IOException{
    File file = new File(args[0]);
    Scanner scan = new Scanner(file);
    while (true){
      if (scan.hasNextLine()){
        x = scan.nextInt();
        y = scan.nextInt();
      }
      ArrayList<Node> smokers = new ArrayList<Node>();
      for (int i = 0; i < y; i++){
        for (int j = 0; j < x; j++){
          Node node = new Node(j, i, false);
          smokers.add(node);
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
          for (Node node : smokers){
            if (node.getX() == i && node.getY() == j){
              node.setBlocked();
              queue.add(node);
            }
          }
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
  
  public static void voronoi(ArrayList<Node> smokers){
    ArrayList<Node> temp = smokers;
    while (queue.size() > 0){
      Node current = queue.remove();
      for (Node node : smokers){
        if (node.getX() == current.getX()+1 && node.getY() == current.getY()){
          if (current.getX()+1 < x && !node.getBlocked()){
            node.setBlocked();
            if (!pathfinder(smokers)){
              node.setBlocked();
            } else {
              queue.add(node);
            }
          }
        }
      }
      for (Node node : smokers){
        if (node.getX() == current.getX()-1 && node.getY() == current.getY()){
          if (current.getX()-1 > -1 && !node.getBlocked()){
            node.setBlocked();
            if (!pathfinder(smokers)){
              node.setBlocked();
            } else {
              queue.add(node);
            }
          }
        }
      }
      for (Node node : smokers){
        if (node.getX() == current.getX() && node.getY() == current.getY()+1){
          if (current.getY()+1 < y && !node.getBlocked()){
            node.setBlocked();
            if (!pathfinder(smokers)){
              node.setBlocked();
            } else {
              queue.add(node);
            }
          }
        }
      }
      for (Node node : smokers){
        if (node.getX() == current.getX() && node.getY() == current.getY()-1){
          if (current.getY()-1 > -1 && !node.getBlocked()){
            node.setBlocked();
            if (!pathfinder(smokers)){
              node.setBlocked();
            } else {
              queue.add(node);
            }
          }
        }
      }
    }
    for (Node node : smokers){
      for (Node n : temp){
        if (node.getX() == n.getX() && node.getY() == n.getY()){
        if (node.getBlocked() != n.getBlocked()){
          voronoi(smokers);
          return;
        }
        }
      }
    }
  }
  
  public static boolean pathfinder(ArrayList<Node> smokers){
    path = 0;
    LinkedList<Node> open = new LinkedList<Node>();
    ArrayList<Node> closed = new ArrayList<Node>();
    ArrayList<Node> successors = new ArrayList<Node>();
    for (Node node : smokers){
      if (node.getX() == 0 && node.getY() == 0){
        if (!node.getBlocked()){
          open.add(node);
        }
      }
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
      for (Node node : smokers){
        if (node.getX() == current.getX()+1 && node.getY() == current.getY()){
          if (current.getX()+1 < x && !node.getBlocked()){
            for (Node c : closed){
              if (c.getX() == node.getX() && c.getY() == node.getY()){
                break;
              }
            }
            node.setParent(current);
            successors.add(node);
          }
        }
      }
      for (Node node : smokers){
        if (node.getX() == current.getX()-1 && node.getY() == current.getY()){
          if (current.getX()-1 > -1 && !node.getBlocked()){
            for (Node c : closed){
              if (c.getX() == node.getX() && c.getY() == node.getY()){
                break;
              }
            }
            node.setParent(current);
            successors.add(node);
          }
        }
      }
      for (Node node : smokers){
        if (node.getX() == current.getX() && node.getY() == current.getY()+1){
          if (current.getY()+1 < y && !node.getBlocked()){
            for (Node c : closed){
              if (c.getX() == node.getX() && c.getY() == node.getY()){
                break;
              }
            }
            node.setParent(current);
            successors.add(node);
          }
        }
      }
      for (Node node : smokers){
        if (node.getX() == current.getX() && node.getY() == current.getY()-1){
          if (current.getY()-1 > -1 && !node.getBlocked()){
            for (Node c : closed){
              if (c.getX() == node.getX() && c.getY() == node.getY()){
                break;
              }
            }
            node.setParent(current);
            successors.add(node);
          }
        }
      }
      for (Node successor : successors){
        boolean found = false;
        if (successor.getX() == x-1 && successor.getY() == y-1){
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
  
  public static void printWorld(ArrayList<Node> smokers){
    System.out.println(path);
    for (int i = 0; i < y; i++){
      for (int j = 0; j < x; j++){
        for (Node n : smokers){
          if (n.getX() == j && n.getY() == i){
            System.out.print(n.getBlocked() + "\t");
          }
        }
      }
      System.out.println();
    }
    System.out.println();
  }
}