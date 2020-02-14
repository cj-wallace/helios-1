import ddf.minim.*;

class Lightning implements Animated{
  
  float xPos;
  float startTime;
  
  public Lightning(float x){
    xPos = x;
    startTime = millis();
    
  }
  
  public void move(){
    
  }
  
  public void display(){
    if(getTime()<1){
      
      noStroke();
      ellipseMode(CORNER);
      ellipse(xPos-75,-25,150,50);
      ellipse(xPos-75,-25,150,50*getTime());
    }
    else{
      float y = 0;
      float x = xPos;
      float leftAng = 4.19;
      float rightAng = 5.23;
      noStroke();
      ellipse(xPos-75,-25,150,50);
      stroke(255);
      strokeWeight(4);
      while(y<height){
        float angle = random(leftAng,rightAng);
        float newX = x-25*cos(angle);
        if(newX > xPos+25)newX=xPos+25;
        if(newX < xPos-25)newX = xPos-25;
        float newY = y-25*sin(angle);
        line(x,y,newX,newY);
        x = newX;
        y = newY;
      }
    }
  }
  
  public float getTime(){
    return (millis()-startTime)/1000;
  }
  
}