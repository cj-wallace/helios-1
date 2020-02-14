class EnemyShip extends Ship implements Animated{
  float goToX, goToY;
  
  float angle;
  float goToAngle;
  color c;
  
  float moveX, moveY;
  
  int numShip;
  
  float start, time;
  
  boolean canChange;
  
  public EnemyShip(float gtX, int num){
    
    super(random(700,750),random(0,600));
    
    goToX = gtX;
    numShip = num;
    setY(numShip,1);
    
    angle = 180;
    goToAngle = 0;
    
    moveX = 0;
    moveY = 0;
    
    canChange=true;
    start = millis();
    
    c = color(random(30,225),random(30,225),random(30,225));
  }
  
  public void move(){
    if(canChange){
      if(x<goToX){
        float dist = goToX-x;
        x += dist/25;
      }
      else if(x>goToX){
        float dist = x-goToX;
        x -= dist/25;
      }
      if(y<goToY){
        float dist = goToY-y;
        y += dist/25;
      }
      else if(y>goToY){
        float dist = y-goToY;
        y -= dist/25;
      }
    }
    else{
      translate(x,y);
      rotate(angle);
      x-=moveX;
      y-=moveY;
      rotate(-angle);
      translate(-x,-y);
    }
  }
  
  public void display(){
    strokeWeight(4);
    stroke(255);
    line(x,y,x + 50*cos(angle),y + 50*sin(angle));
    translate(x,y);
    rotate(angle);
    strokeWeight(1);
    stroke(0);
    fill(c);
    ellipseMode(CENTER);
    ellipse(0,0,75,50);
    rotate(-angle);
    translate(-x,-y);
    
    if((millis()-start)/1000>5&&(millis()-start)/1000<6){
      if(canChange){
        moveX = 10*cos(angle);
        moveY = 10*sin(angle);
        canChange=false;
        
      }
    }
  }
  
  public void setAngle(float x2, float y2){
    float x3 = x-x2;
    float y3 = y-y2;
    if(canChange)angle = atan(y3/x3);
  }
  
  public void setY(int num,int count){
    numShip=num;
    if((count+1)%2==1){
      if(numShip==1){
        goToY = height/2;
      }
      else if(numShip==2){
        goToY = height-height/4-height/16;
      }
      else if(numShip==3){
        goToY = height/4+height/16;
      }
      else if(numShip==4){
        goToY = height-height/6+height/24;
      }
      else if(numShip==5){
        goToY = height/6-height/24;
      }
    }
    else{
      if(count<=2){
        if(numShip == 1){
          goToY = height-height/3;
        }
        if(numShip ==2){
          goToY = height/3;
        }
      }
      if(x>2){
        if(numShip == 1){
          goToY = height/2.5;
        }
        if(numShip ==2){
          goToY = height-height/2.5;
        }
        if(numShip == 3){
          goToY = height/5;
        }
        if(numShip == 4){
          goToY = height-height/5;
        }
      }
    }
  }
  
  public void updateNum(int x){
    numShip = x;
  }
}