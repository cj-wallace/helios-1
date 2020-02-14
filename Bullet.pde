class Bullet implements Animated{
  float x,y;
  
  float speed;
  
  public Bullet(float xP, float yP, float sp){
    x = xP;
    y = yP;
    speed = sp;
  }
  
  public boolean checkCollision(){
    int xN = (int)x;
    int yN = (int)y;
    if(get(xN,yN)!=color(0,220,255))return true;
    else if(get(xN+20,yN)!=color(0,220,255)||get(xN,yN+10)!=color(0,220,255)||get(xN+20,yN+10)!=color(0,220,255)){
      return true;
    }
    else return false;
  }
  
  public void move(){
    x += speed;
  }
  
  public void display(){
    rectMode(CORNER);
    fill(0,220,255);
    noStroke();
    rect(x,y,20,10);
  }
}