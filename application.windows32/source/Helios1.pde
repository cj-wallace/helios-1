import ddf.minim.*;

Minim minim;
AudioPlayer titleMusic, backgroundMusic, dockMusic, nebulaMusic, shot, lightning,power,win, alert;

LevelMode level;

boolean[] keys = new boolean[4];
PFont font;

int levelsToWin;

void setup(){
  size(1000,600);
  background(0);
  font = loadFont("Bitsumishi-48.vlw");
  textFont(font);  
  levelsToWin = 10;
  
  //LevelMode(int lType, int eType, int lTime, int hWT,int uW, int cr, int crToAdd){
  level = new LevelMode(4,4,60,1,5,0,0,0);
  
  minim = new Minim(this);
  titleMusic = minim.loadFile("opening_background.wav");
  backgroundMusic = minim.loadFile("level_music.wav");
  dockMusic = minim.loadFile("ship_idle.wav");
  nebulaMusic = minim.loadFile("nebula_music.wav");
  
  lightning = minim.loadFile("lightning.mp3");
  shot = minim.loadFile("shot.wav");
  power = minim.loadFile("powerup.mp3");
  win = minim.loadFile("win.mp3");
  alert = minim.loadFile("alert.mp3");
  titleMusic.loop();
}

void draw(){
  background(0);
  
  if(level.levelType<3)checkCollision();
  
  level.display();
  level.runThroughLevel();
  
  traverseKeys();
  
  for(int i = 0; i < level.enemies.size(); i++){
    level.enemies.get(i).setAngle(level.helios.x,level.helios.y);
  }
  
  if(level.levelType!=3 && level.pB.done){
    level = new LevelMode(3,4,0,level.helios.weaponType,level.helios.unlockedWeapons,level.helios.credits,0,level.c.sB.cells);
    nebulaMusic.mute();
    backgroundMusic.mute();
    titleMusic.mute();
    dockMusic.loop();
    levelsToWin--;
    
    if(levelsToWin==0){
      nebulaMusic.mute();
      backgroundMusic.mute();
      win.play();
      noLoop();
      background(0);
      textSize(48);
      textAlign(CENTER,CENTER);
      fill(255);
      text("GAME OVER",width/2,height/2);
      textSize(32);
      text("Congratulations! You have successfully travelled the galaxy and survived",width/2-250,height/2+50,500,150);
    }
  }
  
  else if(level.levelType==3){
    fill(255);
    textAlign(LEFT,CENTER);
    textSize(16);
    text(levelsToWin+" Levels to win",700,175);
  }
}

void keyPressed(){
  if(key=='w'){
    keys[0] = true;
  }
  else if(key=='s'){
    keys[1] = true;
  }
  else if(key=='a'){
    keys[2] = true;
  }
  else if(key=='d'){
    keys[3] = true;
  }
  else if(key=='1'||key=='2'||key=='3'||key=='4'||key=='5'||key=='6'||key=='7'||key=='8'||key=='9'){
    int n = Character.getNumericValue(key);
    level.c.b[n-1].pressed(true);
    level.c.enterChar(key);
  }
  /*
  else if(key=='b'){
    level = new LevelMode(1,2,15,1,level.helios.unlockedWeapons,level.helios.credits,0,level.c.sB.cells);
  }
  else if(key=='n'){
    level = new LevelMode(2,3,15,1,level.helios.unlockedWeapons,level.helios.credits,0,level.c.sB.cells);
  }
  else if(key=='m'){
    level = new LevelMode(3,4,15,1,level.helios.unlockedWeapons,level.helios.credits,0,level.c.sB.cells);
  }*/
  else if(key==' '){
    if(level.levelType==4){
      level = new LevelMode(3,4,100,1,1,0,1000,30);
      titleMusic.mute();
      dockMusic.loop();
    }
    if(level.c.wB.done&&level.helios.canMove){
      level.c.wB.fire();
      level.helios.a.activate(level.helios.x,level.helios.y);
      if(level.helios.a.mode==1||level.helios.a.mode==2){
        shot.rewind();
        shot.play();
      }
      else{
        power.rewind();
        power.play();
      }
    }
    else if(level.c.wB.done && level.levelType==3 && level.c.selected!=0){
      switch(level.c.selected){
        case 1: if(level.helios.credits>=30 && level.c.sB.cells<30){
                  level.c.sB.changeStatus(1,false);
                  level.helios.addCredits(-30);
                }
                break;
        case 2: if(level.helios.credits>=200+50*level.helios.unlockedWeapons && level.helios.unlockedWeapons<5){
                  level.helios.addCredits(-200-50*level.helios.unlockedWeapons);
                  level.helios.unlockWeapon();
                }
        
                break;
        case 3: level.helios.changeWeapon();
        
                break;
        case 4: int lType = 2;
                if(random(10)<1)lType = 1;
                int eType = 4;
                if(lType==1){
                  float r = random(3);
                  if(r<1)eType=1;
                  else if(r<2)eType=2;
                  else eType=3;
                }
                level = new LevelMode(lType,eType,(int)random(30,60),level.helios.weaponType,level.helios.unlockedWeapons,level.helios.credits,(int)random(200,300),level.c.sB.cells);
                nebulaMusic.loop();
                dockMusic.pause();
                break;
        case 5: lType = 1;
                if(random(10)<1)lType = 2;
                eType = 1;
                if(random(10)<4)eType=3;
                if(lType==2)eType=4;
                level = new LevelMode(lType,eType,(int)random(30,60),level.helios.weaponType,level.helios.unlockedWeapons,level.helios.credits,(int)random(300,400),level.c.sB.cells);
                backgroundMusic.loop();
                dockMusic.pause();
                break;
        case 6: lType = 1;
                if(random(10)<1)lType = 3;
                eType = 2;
                if(random(10)<4)eType=3;
                if(lType==2)eType=4;
                level = new LevelMode(lType,eType,(int)random(30,60),level.helios.weaponType,level.helios.unlockedWeapons,level.helios.credits,(int)random(300,450),level.c.sB.cells);
                backgroundMusic.loop();
                dockMusic.pause();
                break;
      }
    }
  }
}

void keyReleased(){
  if(key=='w'){
    keys[0] = false;
  }
  else if(key=='s'){
    keys[1] = false;
  }
  else if(key=='a'){
    keys[2] = false;
  }
  else if(key=='d'){
    keys[3] = false;
  }
  else if(key=='1'||key=='2'||key=='3'||key=='4'||key=='5'||key=='6'||key=='7'||key=='8'||key=='9'){
    int n = Character.getNumericValue(key);
    level.c.b[n-1].pressed(false);
  }
}

void traverseKeys(){
  if(keys[0]){
    level.helios.move(0,-3);
  }
  if(keys[1]){
    level.helios.move(0,3);
  }
  if(keys[2]){
    level.helios.move(-3,0);
  }
  if(keys[3]){
    level.helios.move(3,0);
  }
}

public void checkCollision(){
  if(level.helios.a.b1.checkCollision()){
    for(int i = 0; i < level.asteroids.size(); i++){
      level.asteroids.get(i).display();
    }
    for(int i = 0; i < level.enemies.size(); i++){
      level.enemies.get(i).display();
    }
    int dist = 50,a=-1;
    if(get((int)level.helios.a.b1.x,(int)level.helios.a.b1.y)==color(125)){
        for(int i = 0; i < level.asteroids.size(); i++){
          if(dist(level.helios.a.b1.x,level.helios.a.b1.y,level.asteroids.get(i).x,level.asteroids.get(i).y)<dist){
            dist = (int)dist(level.helios.a.b1.x,level.helios.a.b1.y,level.asteroids.get(i).x,level.asteroids.get(i).y);
            a = i;
          }
        }
        if(a>=0)level.asteroids.remove(a);
      }
      else{
        for(int i = 0; i < level.enemies.size(); i++){
          if(dist(level.helios.a.b1.x,level.helios.a.b1.y,level.enemies.get(i).x,level.enemies.get(i).y)<dist){
              dist = (int)dist(level.helios.a.b1.x,level.helios.a.b1.y,level.enemies.get(i).x,level.enemies.get(i).y);
              a = i;
            }
        }
        if(a>=0&&dist<35)level.enemies.remove(a);
      }
  }
  float dist = 50;
  int a = -1;
  for(int i =0; i < level.asteroids.size(); i++){
    if(dist(level.helios.x,level.helios.y,level.asteroids.get(i).x,level.asteroids.get(i).y)<dist){
      dist = dist(level.helios.x,level.helios.y,level.asteroids.get(i).x,level.asteroids.get(i).y);
      a = i;
    }
  }
  if(dist<35&&a>=0){
    level.asteroids.remove(a);
    level.c.sB.changeStatus(-4,level.helios.a.active&&level.helios.a.mode==3);
    level.c = new Console(false,level.c.sB.cells);
    alert.rewind();
    alert.play();
  }
  
  for(int i =0; i < level.lightning.size(); i++){
    if(dist(level.lightning.get(i).xPos,0,level.helios.x,0)<25&&level.lightning.get(i).getTime()>1){
      level.lightning.remove(i);
      level.c.sB.changeStatus(-4,level.helios.a.active&&level.helios.a.mode==3);
      level.c = new Console(false,level.c.sB.cells);
      alert.rewind();
      alert.play();
    }
  }
  dist = 30;
  a = -1;
  for(int i = 0; i < level.enemies.size(); i++){
    if(dist(level.helios.x,level.helios.y,level.enemies.get(i).x,level.enemies.get(i).y)<dist){
      dist = dist(level.helios.x,level.helios.y,level.enemies.get(i).x,level.enemies.get(i).y);
      a = i;
    }
  }
  if(dist<25 && a>=0){
    level.enemies.remove(a);
    level.c.sB.changeStatus(-4,level.helios.a.active&&level.helios.a.mode==3);
    level.c = new Console(false,level.c.sB.cells);
    alert.rewind();
    alert.play();
  }
  
}