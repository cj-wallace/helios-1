class Button{
  int x;
  int y;
  int wid;
  int h;
  
  String text;
  
  boolean pressed;
  
  public Button(String s, int xP, int yP, int wP, int hP){
    text = s;
    x=xP;
    y=yP;
    wid = wP;
    h = hP;
    
  }
  
  public void display(){
    if(pressed)fill(150);
    else fill(100);
    stroke(0);
    rectMode(CORNER);
    rect(x,y,wid,h);
    fill(0);
    //textSize(textSize);
    textAlign(CENTER,CENTER);
    text(text,x+wid/2,y+h/2);
  }
  
  public void pressed(boolean pressedP){
    pressed = pressedP;
  }
}