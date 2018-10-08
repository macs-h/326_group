import java.util.*;
import java.io.*;

public class Smokers extends Node{
  private static int heuristic;
  private static LinkedList<Node> queue = new LinkedList<Node>();
  private static int x, y, path;
  private static ArrayList<Integer> totalDist = new ArrayList<Integer>();
  private static ArrayList<Node> nonSmokers = new ArrayList<Node>();
    private static ArrayList<Boolean> checkList = new ArrayList<Boolean>();
    private static ArrayList<Node> closed = new ArrayList<Node>();
  
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
        totalDist.clear();
        nonSmokers.clear();
      while (scan.hasNextLine()){
        String line = scan.nextLine();
        if (!line.isEmpty()){
          String[] coords = line.split("\\s+");
          int i = Integer.parseInt(coords[0]);
          int j = Integer.parseInt(coords[1]);
          for (Node node : smokers){
            if (node.getX() == i && node.getY() == j){
              node.setBlocked(true);
              checkList.add(true);
              queue.add(node);
              nonSmokers.add(node);
            }
          }
        } else {
          break;
        }
      }
      printWorld(smokers);
      voronoi(smokers);
      printWorld(smokers);
      pathfinder(smokers);
      ArrayList<Integer> minimums = new ArrayList<Integer>();
      int total = 0;
      int finalMin = Integer.MAX_VALUE;
      for (Node n : nonSmokers){
          int min = Integer.MAX_VALUE;
          for (Node node : closed){
              if (Math.abs(node.getX() - n.getX()) + Math.abs(node.getY() - n.getY()) < min){
                  min = Math.abs(node.getX() - n.getX()) + Math.abs(node.getY() - n.getY());
              }
          }
          total += min;
          if (min < finalMin){
              finalMin = min;
          }
          minimums.add(min);
      }
      System.out.println("Minimum distance: " + finalMin);
      System.out.println("Total dist: " + total);
      checkList.clear();
      if (!scan.hasNextLine()){
        scan.close();
        return;
      }
    }
  }
  
    public static void voronoi(ArrayList<Node> smokers){
        ArrayList<Node> temp = smokers;
        ArrayList<Node> nodes = new ArrayList<Node>();
        ArrayList<LinkedList<Node>> queues = new ArrayList<LinkedList<Node>>();
        for (Node n : nonSmokers){
            LinkedList <Node> l = new LinkedList<Node>();
            l.add(n);
            queues.add(l);
        }
        boolean hasChanged = true;
        while (hasChanged){
            for (int i = 0; i < queues.size(); i++){
                ArrayList<Node> tempList = new ArrayList<Node>();
                boolean rollback = false;
                while (checkList.get(i) && queues.get(i).size() > 0){
                   
                    Node currentNode = queues.get(i).remove();
                    for (Node node : smokers){
                        if (currentNode.getX()+1 == node.getX() && currentNode.getY() == node.getY() && !node.getBlocked()){
                            tempList.add(node);
                            node.setBlocked(true);
                        }
                        if (currentNode.getX()-1 == node.getX() && currentNode.getY() == node.getY() && !node.getBlocked()){
                            tempList.add(node);
                            node.setBlocked(true);
                        }
                        if (currentNode.getX() == node.getX() && currentNode.getY()+1 == node.getY() && !node.getBlocked()){
                            tempList.add(node);
                            node.setBlocked(true);
                        }
                        if (currentNode.getX() == node.getX() && currentNode.getY()-1 == node.getY() && !node.getBlocked()){
                            tempList.add(node);
                            node.setBlocked(true);
                        }
                    }
                    if (!pathfinder(smokers)){
                        for (Node node : tempList){
                            node.setBlocked(false);
                        }
                        checkList.set(i, false);
                    } else {
                        rollback = true;
                    }
                }
                if( rollback){
                    queues.get(i).addAll(tempList);
                }
            }
            for (boolean b : checkList){
                if (b){
                    hasChanged = true;
                    break;
                }
                hasChanged = false;
            }
        }
    /*while (queue.size() > 0){
      Node current = queue.remove();
      int i = -1;
      for (Node node : smokers){
          i++;
        if (node.getX() == current.getX()+1 && node.getY() == current.getY()){
          if (current.getX()+1 < x && !node.getBlocked()){
            node.setBlocked(true);
            nodes.add(node);
            }
          }
        if (node.getX() == current.getX()-1 && node.getY() == current.getY()){
          if (current.getX()-1 > -1 && !node.getBlocked()){
            node.setBlocked(true);
              nodes.add(node);
            
          }
        }
        if (node.getX() == current.getX() && node.getY() == current.getY()+1){
          if (current.getY()+1 < y && !node.getBlocked()){
            node.setBlocked(true);
              nodes.add(node);
            
          }
        }
        if (node.getX() == current.getX() && node.getY() == current.getY()-1){
          if (current.getY()-1 > -1 && !node.getBlocked()){
            node.setBlocked(true);
              nodes.add(node);
            
          }
        }
      }
      if (!pathfinder(smokers)){
        for (Node node : nodes){
          if (node.getX() == current.getX()+1 && node.getY() == current.getY() && node.getBlocked()){
            node.setBlocked(false);
          }
        }
        for (Node node : nodes){
          if (node.getX() == current.getX()-1 && node.getY() == current.getY() && node.getBlocked()){
            node.setBlocked(false);
          }
        }
        for (Node node : nodes){
          if (node.getX() == current.getX() && node.getY() == current.getY()+1 && node.getBlocked()){
            node.setBlocked(false);
          }
        }
        for (Node node : nodes){
          if (node.getX() == current.getX() && node.getY() == current.getY()-1 && node.getBlocked()){
            node.setBlocked(false);
          }
        }
      } else {
          //System.out.print(current.getX() + " " + current.getY() + ": ");
        while (nodes.size() > 0){
          queue.add(nodes.remove(0));
        }
      }
      nodes.clear();
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
      }*/
  }
  
      
  
  public static boolean pathfinder(ArrayList<Node> smokers){
    LinkedList<Node> open = new LinkedList<Node>();
    ArrayList<Node> successors = new ArrayList<Node>();
    closed.clear();
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
          if (heuristic - (current.getX() + current.getY()) >= heuristic - (open.get(i).getX() + open.get(i).getY())){
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
        /*for (Node o : open){
          if (heuristic - (o.getX() + o.getY()) < heuristic - (successor.getX() + successor.getY())){
            found = true;
            break;
          }
          }*/
        for (Node c : closed){
            if (c.getX() == successor.getX() && c.getY() == successor.getY()){
                //if (heuristic - (c.getX() + c.getY()) < heuristic - (successor.getX() + successor.getY())){
            found = true;
            break;
          }
        }
        
        if (found == false){
          open.add(successor);
        }
      }
      boolean found = false;
      for (Node c : closed){
        if (c.getX() == current.getX() && c.getY() == current.getY()){
          found = true;
        }
      }
      if (found == false){
          closed.add(current);
      }
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
  
  public static void findDist(ArrayList<Node> smokers){
    
      for (Node nonSmoker : nonSmokers){
          int min = Integer.MAX_VALUE;
          for (Node smoker : smokers){
              if (!smoker.getBlocked() && Math.abs(smoker.getX() - nonSmoker.getX()) + Math.abs(smoker.getY() - nonSmoker.getY()) < min){
                  min = Math.abs(smoker.getX() - nonSmoker.getX()) + Math.abs(smoker.getY() - nonSmoker.getY());
              }
          }
          totalDist.add(min);
      }
      Collections.sort(totalDist);
      System.out.println("Minimum distance: " + totalDist.get(0));
      int total = 0;
      for (int t : totalDist){
          total += t;
      }
      System.out.println("Total distance: " + total);
  }
}
