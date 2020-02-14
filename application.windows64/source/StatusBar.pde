class StatusBar{
  
  float x1,x2,y1,y2;
  float wid, ht;
  
  int cells;
  float cellWid;
  
  public StatusBar(float x1P, float y1P, float x2P, float y2P, int cellsP){
    x1=x1P;
    x2=x2P;
    y1=y1P;
    y2=y2P;
    
    wid = x2-x1;
    ht = y2-y1;
    cells = cellsP;
    cellWid = wid/30;
  }
  
  public void display(){
    rectMode(CORNERS);
    fill(0);
    rect(x1,y1,x2,y2);
    fill(255,0,0);
    rectMode(CORNER);
    stroke(0);
    strokeWeight(.5);
    for(int i = 0; i < cells; i++){
      rect(x1+i*cellWid,y1,cellWid,ht);
    }
  }
  
  public void changeStatus(int x,boolean shielded){
    if(!shielded){
      cells += x;
      if(cells>30)cells = 30;
      if(cells<=0){
        cells = 0;
        println("GAME OVER");
        
        background(0);
        fill(255,0,0);
        text("GAME OVER",width/3,height/2);
        stop();
        noLoop();
      }
    }
    
  }
}