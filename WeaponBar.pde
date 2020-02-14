class WeaponBar extends LoadingBar{
  
  String text;
  
  public WeaponBar(String s,float timeP,color c){
    super(750,510,timeP,150,50,c);
    text = s;
  }
  
  public void display(){
    rectMode(CORNER);
    fill(0);
    rect(x,y,wid,ht);
    fill(c);
    rect(x,y,wid*updateProgress(),ht);
    fill(255);
    textAlign(CENTER,CENTER);
    textSize(25);
    if(done)text(text,x,y,wid,ht);
    else text("Loading...",x,y,wid,ht);
  }
  
  public void checkDone(){
    if((millis()-start)/(time*1000)>1){
      c = color(65,200,255);
      done = true;
    }
    else{
      c = color(255,0,0);
      done = false;
    }
  }
  
  public void fire(){
    if(done){
      resetProgress();
    }
  }
}