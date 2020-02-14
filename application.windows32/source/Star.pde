class Star implements Animated{
  
  float x,y;
  float size;
  
  public Star(){
    x = random(width-10)+10;
    y = random(height-10)+10;
    size = random(.5,2);
  }
  
  public Star(float xP, float yP){
    x=xP;
    y=yP;
    size = random(2);
  }
  
  
  public void move(){
    x += -1.5*size;
    
    if(x < 0){
      x = random(40)+width;
      y = random(height-10)+10;
      size = random(2);
    }
  }
  
  public void display(){
    stroke(255);
    fill(255);
    noStroke();
    ellipse(x,y,size,size);
  }
  
}