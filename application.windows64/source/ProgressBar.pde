class ProgressBar extends LoadingBar{
  
  public ProgressBar(float timeP){
    super(0,height-10,timeP,650,10,color(125,255,125));
  }
  
  public void display(){
    noStroke();
    fill(CORNER);
    fill(125);
    rect(x,y,wid,ht);
    fill(c);
    rect(x,y,wid*updateProgress(),ht);
  }
  
  
}