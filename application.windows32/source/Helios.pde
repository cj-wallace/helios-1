class Helios extends Ship implements Animated{
  
  int weaponType, unlockedWeapons;
  int credits;
  PImage img;
  
  boolean canMove;
  
  Ability a;
  
  public Helios(float xP, float yP, int wType,int uWeapons,int cr,boolean move){
    super(xP,yP);
    weaponType = wType;
    unlockedWeapons = uWeapons;
    credits = cr;
    img = loadImage("Ship.png");
    canMove = move;
    a = new Ability(wType);
  }
  
  public void move(){
    
  }
  
  public void move(float xP, float yP){
    if(canMove){
      x += xP;
      if(x<38||x>610)x-=xP;
      y += yP;
      if(y<27||y>height-37)y-=yP;
    }
  }
  
  public void changeWeapon(){
    weaponType++;
    if(weaponType>unlockedWeapons)weaponType=1;
    a.mode=weaponType;
  }
  
  public void unlockWeapon(){
    if(unlockedWeapons<5)unlockedWeapons++;
  }
  
  public void addCredits(int cr){
    credits += cr;
  }
  
  public void display(){
    fill(125);
    stroke(25);
    //ellipse(x,y,75,50); 
    image(img,x-37.5,y-25,75,50);
    a.display(x,y);
  }
}