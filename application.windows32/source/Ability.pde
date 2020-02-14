class Ability{

  Bullet b1,b2;

  int mode;
  boolean active;
  
  float start,time;
  
  public Ability(int m){
    mode = m;
    active = false;
    start = millis();
    time = 10;
    if(mode==3)time = 3;
    
    b1 = new Bullet(-50,-50,0);
    b2 = new Bullet(-50,-50,0);
  }
  
  public void activate(float x, float y){
    active = true;
    
    start = millis();
    
    if(mode==1){//Laser
      b1 = new Bullet(x,y,10);
    }
    else if(mode==2){//Double Laser
      b1 = new Bullet(x,y-15,10);
      b2 = new Bullet(x,y+15,10);
    }
    else if(mode==3){//Shield
      
    }
    else if(mode==4){//Faster Mobility
      
    }
    else if(mode==5){//Slow Down Time
        frameRate(30);
    }
    switch(mode){
      case 1: b1 = new Bullet(x,y,10);
              break;
      case 2: b1 = new Bullet(x,y-15,10);
              b2 = new Bullet(x,y+15,10);
              break;
      case 3:
              break;
      case 4:
              break;
      case 5:
              break;
      
    }
  }
  
  public void display(float x, float y){
    if(active){
      if(mode==1){//Laser
        b1.move();
        b1.display();
      }
      else if(mode==2){//Double Laser
        b1.move();
        b2.move();
        b1.display();
        b2.display();
      }
      else if(mode==3){//Shield
        fill(125,255,255,125);
        ellipseMode(CENTER);
        noStroke();
        ellipse(x,y,130,75);
      }
      else if(mode==4){//Faster Mobility
        
      }
      else if(mode==5){//Slow Down Time
      }
    }
    if((millis()-start)/1000>time){
      active = false;
      frameRate(60);
    }
    
  }
  
  public String toString(){
    if(mode==1){//Laser
      return "Laser";
    }
    else if(mode==2){//Double Laser
      return "Dual Laser";
    }
    else if(mode==3){//Shield
      return "Shield";
    }
    else if(mode==4){//Faster Mobility
      return "Speed Boost";
    }
    else if(mode==5){//Slow Down Time
      return "Time Crawl";
    }
    else return "";
  }
  
  /*public float updateSpeed(){
    if(mode==4&&active){
      return 
    }
  }*/
}