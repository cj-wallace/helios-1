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
        wB = new WeaponBar("Confirm",0.01, color(255, 15, 15));
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