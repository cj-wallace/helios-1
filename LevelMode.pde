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
      rect(250,407.5,45,45);
      rect(250,495,45,45);
      fill(0);
      textSize(30);
      textAlign(LEFT,CENTER);
      
      textSize(24);
      
      text("Repair Ship\n30c",45,310,205,65);
      if(helios.unlockedWeapons>=5) text("All Weapons Purchased",45,397.5,205,65);
      else text("Purchase New Weapon\n"+(200+50*helios.unlockedWeapons),45,397.5,205,65);
      text("Change Weapon",45,485,205,65);

      text("Possible Nebula\n200-300c",350,310,205,65);
      text("Possible Asteroid field\n300-400c",350,397.5,205,65);
      text("Enemy Space\n350-450c",350,485,205,65);
      
      fill(120);
      rectMode(CORNER);
      rect(555,320,45,45);
      rect(555,407.5,45,45);
      rect(555,495,45,45);
      
      textSize(30);
      fill(0);
      textAlign(CENTER,CENTER);
      text("4",555,320,45,45);
      text("5",555,407.5,45,45);
      text("6",555,495,45,45);
      text("1",250,320,45,45);
      text("2",250,407.5,45,45);
      text("3",250,495,45,45);
      
      textSize(20);
      text("Status",187.5,120);
      textAlign(LEFT,CENTER);
      text("Health:",40,165);
      text("Credits:",40,200);
      text("Weapon:",40,235);
      
      textAlign(RIGHT,CENTER);
      text(c.sB.cells+"/30",230,152.5,100,25);
      text(""+helios.credits,230,187.5,100,25);
      text(helios.a.mode+"/"+helios.unlockedWeapons+"\n"+helios.a,120,222.5,210,25);
      
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