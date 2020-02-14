import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Helios1 extends PApplet {



Minim minim;
AudioPlayer titleMusic, backgroundMusic, dockMusic, nebulaMusic, shot, lightning,power,win, alert;

LevelMode level;

boolean[] keys = new boolean[4];
PFont font;

int levelsToWin;

public void setup(){
  
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

public void draw(){
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

public void keyPressed(){
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

public void keyReleased(){
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

public void traverseKeys(){
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
interface Animated{
  public void move();
  public void display();
  
}
class Asteroid implements Animated{
  float x, y;
  float wid,ht;
  
  float angle;
  float rotateSpeed;
  float speed;
  
  public Asteroid(){
    x = random(700,750);
    y = random(50,550);
    
    speed = random(3,7);
    
    wid = random(50,125);
    ht = random(50,125);
    
    angle = random(0,360);
    rotateSpeed = random(-.1f,.1f);
  }
  
  public void move(){
    angle+=rotateSpeed;
    x-=speed;
  }
  
  public void display(){
    ellipseMode(CENTER);
    stroke(0);
    strokeWeight(1);
    fill(125);
    translate(x,y);
    rotate(angle);
    ellipse(0,0,wid,ht);
    rotate(-angle);
    translate(-x,-y);
  }
  
  public boolean offScreen(){
    return x<-125;
  }
}
class Bullet implements Animated{
  float x,y;
  
  float speed;
  
  public Bullet(float xP, float yP, float sp){
    x = xP;
    y = yP;
    speed = sp;
  }
  
  public boolean checkCollision(){
    int xN = (int)x;
    int yN = (int)y;
    if(get(xN,yN)!=color(0,220,255))return true;
    else if(get(xN+20,yN)!=color(0,220,255)||get(xN,yN+10)!=color(0,220,255)||get(xN+20,yN+10)!=color(0,220,255)){
      return true;
    }
    else return false;
  }
  
  public void move(){
    x += speed;
  }
  
  public void display(){
    rectMode(CORNER);
    fill(0,220,255);
    noStroke();
    rect(x,y,20,10);
  }
}
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
class Cloud implements Animated{
  float x, y;
  float wid, ht;
  int c;
  float speed;
  
  public Cloud(){
    x = random(width-10);
    y = random(-20,height-100);
    wid = random(200,350);
    ht = random(100,200);
    speed = random(2,5);
    c = color(random(.75f,.9f),random(200,255),random(200),random(150));
  }
  
  public void display(){
    ellipseMode(CORNER);
    noStroke();
    fill(c);
    ellipse(x,y,wid,ht);
    noTint();
  }
  
  public void move(){
    x += speed*-1.5f;
    if(x<-wid){
      x = random(650,800);
      y = random(height-100);
      wid = random(200,350);
      ht = random(100,200);
      speed = random(2,5);
      c = color(random(.75f,.9f),random(200,255),random(255),random(150));
    }
    morph();
  }
  
  public void morph(){
    if(random(5)<2){
      wid += random(-2,0);
      ht += random(-2,0);
    }
    else{
      wid += random(0,2);
      ht += random(0,2);
    }
  }
}
interface Collision{
  public boolean checkCollision();
}
class Console {
  char[] text;
  int[] status;
  float x, y;
  int pos;
  int selected = 0;
  boolean dockMode;
  String message;
  Button[] b = new Button[10];

  StatusBar sB;

  WeaponBar wB;

  public Console(boolean dockM,int h) {
    pos = 0;
    text = new char[6];
    status = new int[text.length];

    message = "";
    dockMode = dockM;

    for (int i = 0; i < text.length; i++) {
      text[i] = Integer.toString((int)random(1, 9)).charAt(0);
      status[i] = 0;
    }

    x = 775;
    y = 125;


    b[0] = new Button("1", 690, 410, 80, 65);
    b[1] = new Button("2", 785, 410, 80, 65);
    b[2] = new Button("3", 880, 410, 80, 65);

    b[3] = new Button("4", 690, 330, 80, 65);
    b[4] = new Button("5", 785, 330, 80, 65);
    b[5] = new Button("6", 880, 330, 80, 65);

    b[6] = new Button("7", 690, 250, 80, 65);
    b[7] = new Button("8", 785, 250, 80, 65);
    b[8] = new Button("9", 880, 250, 80, 65);

    if (dockMode) wB = new WeaponBar("Select", 3, color(255, 15, 15));
    else wB = new WeaponBar("Activate", 5, color(255, 15, 15));
    sB = new StatusBar(700, 215, 950, 235, h);
  }

  public void display() {

    //Draw Console
    rectMode(CORNERS);
    fill(125);
    stroke(175);
    strokeWeight(1);
    rect(650, 0, width, height);
    fill(25);
    stroke(25);
    rect(675, 50, 975, 200);
    stroke(200);
    strokeWeight(3);
    line(650, 0, 650, height);
    fill(0);
    textAlign(CENTER, CENTER);
    textSize(32);
    //textFont(font,32);
    text("CONSOLE", 825, 25);
    for (int i =0; i < 9; i++) {
      b[i].display();
    }

    textSize(32);
    if (dockMode) {
      fill(255);
      textAlign(CENTER, CENTER);
      text(message, x-50, y-25, 200, 50);
    } else {
      for (int i = 0; i < text.length; i++) {
        if (status[i]==0)fill(125);
        else if (status[i]==1)fill(255);
        else fill(255, 0, 0);
        text(text[i], x+i*20, y);
      }
    }

    wB.display();
    sB.display();
  }

  public void enterChar(char c) {
    if (dockMode) {
      text = new char[0]; 
      selected = Character.getNumericValue(c);
      if (selected<=6){
        message = "Confirm Selection: "+c;
        wB = new WeaponBar("Confirm",0.01f, color(255, 15, 15));
      }
    } else {
      if (text[pos]==c) {
        status[pos]=1;
      } else {
        status[pos]=2;
      }
      pos++;
    }
    if (pos>=text.length) {
      float correct = 0;
      for(int i = 0; i < text.length; i++){
        if(status[i]==1)correct++;
      }
      
      correct = (correct/text.length)*4;
      
      sB.changeStatus((int)correct,false);
      
      pos = 0;
      text = new char[0];
      status = new int[text.length];
      for (int i = 0; i < text.length; i++) {
        text[i] = Integer.toString((int)random(1, 9)).charAt(0);
        status[i] = 0;
      }
    }
  }
}
class EnemyShip extends Ship implements Animated{
  float goToX, goToY;
  
  float angle;
  float goToAngle;
  int c;
  
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
          goToY = height/2.5f;
        }
        if(numShip ==2){
          goToY = height-height/2.5f;
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
    image(img,x-37.5f,y-25,75,50);
    a.display(x,y);
  }
}
class LevelMode{
  ProgressBar pB;
  
  ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
  ArrayList<EnemyShip> enemies = new ArrayList<EnemyShip>();
  ArrayList<Lightning> lightning = new ArrayList<Lightning>();
  ArrayList<Bullet> bullets = new ArrayList<Bullet>();
  Minim minim;
  
  Helios helios;
  
  Console c;
  
  ArrayList<Animated> projectiles;
  
  int levelType;
  int enemyType;
  
  public LevelMode(int lType, int eType, int lTime, int hWT,int uW, int cr, int crToAdd, int health){
    levelType = lType;
    enemyType = eType;
    if(levelType<3){helios = new Helios(60,300,hWT,uW,cr,true);}
    else{ helios = new Helios(492,188,hWT,uW,cr,false);}
    
    projectiles = new ArrayList<Animated>();
    
    if(lType == 1||lType==4){
      for(int i = 0; i < 300; i++){
        projectiles.add(new Star());
      }
    }
    else if(lType == 2){
      for(int i = 0; i < 30; i++){
        projectiles.add(new Cloud());
      }
    }
    
    
    c = new Console(lType==3,health);
    pB = new ProgressBar(lTime);
    
    helios.addCredits(crToAdd);
  }
  
  public void display(){
    if(levelType==1){//Space mode
      //Draw Stars
      for(int i = 0; i < projectiles.size(); i++){
        projectiles.get(i).move();
        projectiles.get(i).display();
      }
      for(int i = 0; i < asteroids.size(); i++){
        asteroids.get(i).display();
      }
      for(int i = 0; i < enemies.size();i++){
        enemies.get(i).display();
      }
      pB.display();
    }
    else if(levelType==2){//Nebula mode
      //image(img, 0, 0,700,600);
      background(220,11,70);
      for(int i = 0; i < projectiles.size(); i++){
        projectiles.get(i).move();
        projectiles.get(i).display();
      }
      for(int i = 0; i< lightning.size(); i++){
        lightning.get(i).display();
      }
      pB.display();
    }
    else if(levelType==3){//dock
      background(125);
      fill(0);
      textSize(64);
      textAlign(CENTER);
      text("DOCK",325,75);
      fill(140);
      rectMode(CORNERS);
      rect(25,100,350,275);      
      rect(325,285,625,575);
      rect(25,285,315,575);
      
      fill(0);
      rect(360,100,625,275);
      
      fill(120);
      rectMode(CORNER);
      rect(250,320,45,45);
      rect(250,407.5f,45,45);
      rect(250,495,45,45);
      fill(0);
      textSize(30);
      textAlign(LEFT,CENTER);
      
      textSize(24);
      
      text("Repair Ship\n30c",45,310,205,65);
      if(helios.unlockedWeapons>=5) text("All Weapons Purchased",45,397.5f,205,65);
      else text("Purchase New Weapon\n"+(200+50*helios.unlockedWeapons),45,397.5f,205,65);
      text("Change Weapon",45,485,205,65);

      text("Possible Nebula\n200-300c",350,310,205,65);
      text("Possible Asteroid field\n300-400c",350,397.5f,205,65);
      text("Enemy Space\n350-450c",350,485,205,65);
      
      fill(120);
      rectMode(CORNER);
      rect(555,320,45,45);
      rect(555,407.5f,45,45);
      rect(555,495,45,45);
      
      textSize(30);
      fill(0);
      textAlign(CENTER,CENTER);
      text("4",555,320,45,45);
      text("5",555,407.5f,45,45);
      text("6",555,495,45,45);
      text("1",250,320,45,45);
      text("2",250,407.5f,45,45);
      text("3",250,495,45,45);
      
      textSize(20);
      text("Status",187.5f,120);
      textAlign(LEFT,CENTER);
      text("Health:",40,165);
      text("Credits:",40,200);
      text("Weapon:",40,235);
      
      textAlign(RIGHT,CENTER);
      text(c.sB.cells+"/30",230,152.5f,100,25);
      text(""+helios.credits,230,187.5f,100,25);
      text(helios.a.mode+"/"+helios.unlockedWeapons+"\n"+helios.a,120,222.5f,210,25);
      
    }
    
    helios.display();
    c.display();
    
    if(levelType==4){
      background(0);
      textSize(128);
      fill(255);
      text("Helios 1",width/2,200);
      for(int i = 0; i < projectiles.size(); i++){
        projectiles.get(i).display();
        projectiles.get(i).move();
      }
      textSize(48);
      text("Press 'SPACE' To Start",width/2,500);
    }
  }
  public void changeLevel(int x){
    levelType = x;
  }
  public void changeEnemy(int x){
    enemyType = x;
  }
  public void runThroughLevel(){
    if(enemyType==0){//asteroids
      for(int i = 0; i < asteroids.size(); i++){
        asteroids.get(i).move();
        if(asteroids.get(i).offScreen()){
          asteroids.remove(i);
        }
      }
      if(random(100)<2){
        asteroids.add(new Asteroid());
      }
    }
    else if(enemyType==1){//enemy ships
      //move enemies
      for(int i = 0; i < enemies.size();i++){
        enemies.get(i).move();
        if(enemies.get(i).x<-50)enemies.remove(i);
      }
      //Randomly add new enemy
      if(enemies.size()<5&&random(200)<1){
          for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).setY(i+1,enemies.size()+1);
          }
          enemies.add(new EnemyShip(575,enemies.size()+1));
          
      }
    }
    else if(enemyType==2){//asteroids
      //move asteroids
      for(int i = 0; i < asteroids.size(); i++){
        asteroids.get(i).move();
        if(asteroids.get(i).offScreen()){
          asteroids.remove(i);
        }
      }
      //Randomly add new Asteroid
      if(random(100)<2){
        asteroids.add(new Asteroid());
      }
    }
    else if(enemyType==3){//enemy ships & asteroids
      //move asteroids
      for(int i = 0; i < asteroids.size(); i++){
        asteroids.get(i).move();
        if(asteroids.get(i).offScreen()){
          asteroids.remove(i);
        }
      }
      for(int i = 0; i < enemies.size(); i++){
        enemies.get(i).setY(i+1,enemies.size()+1);
      }
      //Randomly add new Asteroid
      if(random(100)<2){
        asteroids.add(new Asteroid());
      }
      //move enemies
      for(int i = 0; i < enemies.size();i++){
        enemies.get(i).move();
        if(enemies.get(i).x<-50)enemies.remove(i);
      }
      //Randomly add new enemy
      if(enemies.size()<5&&random(200)<1){
        
          enemies.add(new EnemyShip(575,enemies.size()+1));
          for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).setY(i+1,enemies.size()+1);
          }
      }
      
    }
    else if(enemyType==4){//storm clouds
      if(random(100)<1){
        lightning.add(new Lightning(random(50,550)));
      }
      for(int i = 0; i < lightning.size(); i++){
        if(lightning.get(i).getTime()>3)lightning.remove(i);
      }
    }
    else if(enemyType==4){//none
      
    }
    
  }
}


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
      float leftAng = 4.19f;
      float rightAng = 5.23f;
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
abstract class LoadingBar{
  public boolean done;
  
  float time;
  float start;
  
  int x,y;
  int ht,wid;
  
  int c;
  
  public LoadingBar(int xP, int yP, float timeP, int wP, int hP, int cP){
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
abstract class Ship{
  float x,y;
  
  public Ship(float xP, float yP){
    x = xP;
    y = yP;
  }
}
class Star implements Animated{
  
  float x,y;
  float size;
  
  public Star(){
    x = random(width-10)+10;
    y = random(height-10)+10;
    size = random(.5f,2);
  }
  
  public Star(float xP, float yP){
    x=xP;
    y=yP;
    size = random(2);
  }
  
  
  public void move(){
    x += -1.5f*size;
    
    if(x < 0){
      x = random(40)+width;
      y = random(height-10)+10;
      size = random(2);
    }
  }
  
  public void display(){
    stroke(255);
    fill(255);
    noStroke();
    ellipse(x,y,size,size);
  }
  
}
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
    strokeWeight(.5f);
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
class WeaponBar extends LoadingBar{
  
  String text;
  
  public WeaponBar(String s,float timeP,int c){
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
  public void settings() {  size(1000,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Helios1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
