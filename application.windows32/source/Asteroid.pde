class Asteroid implements Animated{
  float x, y;
  float wid,ht;
  
  float angle;
  float rotateSpeed;
  float speed;
  
  public Asteroid(){
    x = random(700,750);
    y = random(50,550);
    
    speed = random(3,7);
    
    wid = random(50,125);
    ht = random(50,125);
    
    angle = random(0,360);
    rotateSpeed = random(-.1,.1);
  }
  
  public void move(){
    angle+=rotateSpeed;
    x-=speed;
  }
  
  public void display(){
    ellipseMode(CENTER);
    stroke(0);
    strokeWeight(1);
    fill(125);
    translate(x,y);
    rotate(angle);
    ellipse(0,0,wid,ht);
    rotate(-angle);
    translate(-x,-y);
  }
  
  public boolean offScreen(){
    return x<-125;
  }
}