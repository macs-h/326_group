public class Node{
  private Node parent;
  private int x, y;
  private boolean blocked;
  
  public Node(int x, int y, boolean blocked){
    this.x = x;
    this.y = y;
    this.blocked = blocked;
  }
  
  public Node(){
  }
  
  public void setX(int x){
    this.x = x;
  }
  
  public void setY(int y){
    this.y = y;
  }
  
  public void setBlocked(){
    this.blocked = !this.blocked;
  }
  
  public void setParent(Node parent){
    this.parent = parent;
  }
  
  public int getX(){
    return this.x;
  }
  
  public int getY(){
    return this.y;
  }
  
  public Node getParent(){
    return this.parent;
  }
  
  public boolean getBlocked(){
    return this.blocked;
  }
}