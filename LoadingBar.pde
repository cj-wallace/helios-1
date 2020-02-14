abstract class LoadingBar{
  public boolean done;
  
  float time;
  float start;
  
  int x,y;
  int ht,wid;
  
  color c;
  
  public LoadingBar(int xP, int yP, float timeP, int wP, int hP, color cP){
    x = xP;
    y = yP;
    time = timeP;
    start = millis();
    wid = wP;
    ht = hP;
    c = cP;
    
    checkDone();
  }
  public float updateProgress(){
    checkDone();
    float i = (millis()-start)/(time*1000);
    if(i<1) return i;
    else return 1;
  }
  
  public void checkDone(){
    if((millis()-start)/(time*1000)>1)done = true;
    else done = false;
  }
  
  public void resetProgress(){
    start = millis();
    checkDone();
  }
  
  public abstract void display();
}