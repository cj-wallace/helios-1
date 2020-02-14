class Cloud implements Animated{
  float x, y;
  float wid, ht;
  color c;
  float speed;
  
  public Cloud(){
    x = random(width-10);
    y = random(-20,height-100);
    wid = random(200,350);
    ht = random(100,200);
    speed = random(2,5);
    c = color(random(.75,.9),random(200,255),random(200),random(150));
  }
  
  public void display(){
    ellipseMode(CORNER);
    noStroke();
    fill(c);
    ellipse(x,y,wid,ht);
    noTint();
  }
  
  public void move(){
    x += speed*-1.5;
    if(x<-wid){
      x = random(650,800);
      y = random(height-100);
      wid = random(200,350);
      ht = random(100,200);
      speed = random(2,5);
      c = color(random(.75,.9),random(200,255),random(255),random(150));
    }
    morph();
  }
  
  public void morph(){
    if(random(5)<2){
      wid += random(-2,0);
      ht += random(-2,0);
    }
    else{
      wid += random(0,2);
      ht += random(0,2);
    }
  }
}