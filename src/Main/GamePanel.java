package Main;

import Imputs.KeyboardImputs;
import Imputs.MouseImputs;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
  private float xDelta = 180.0F;
  
  private float yDelta = 180.0F;
  
  private int frames = 0, width = 25, heigth = 25, indX, indY, index, PosX = 0, PosY = 0;
  
  private int MaxIndX = 17;
  
  private int MaxIndY = 15;
  
  private int[] CaselleX = new int[this.MaxIndX];
  
  private int[] CaselleY = new int[this.MaxIndY];
  
  public float xDir = 1.0F;
  
  public float yDir = 0.0F;
  
  private int[] AxDelta = new int[this.MaxIndX * this.MaxIndY], AyDelta = new int[this.MaxIndX * this.MaxIndY], AxDir = new int[this.MaxIndX * this.MaxIndY], AyDir = new int[this.MaxIndX * this.MaxIndY];
  
  private int[] prevX = new int[this.MaxIndX * this.MaxIndY];
  
  private int[] prevY = new int[this.MaxIndX * this.MaxIndY];
  
  private int[] AindX = new int[this.MaxIndX * this.MaxIndY];
  
  private int[] AindY = new int[this.MaxIndX * this.MaxIndY];
  
  private int indice = 2;
  
  private int Fps = 0;
  
  public boolean GameStart = false;
  
  public boolean turn = false;
  
  public boolean j = true;
  
  private long LastCheck = 0L;
  
  private BufferedImage pieceSprite;
  
  private BufferedImage headSprite;
  
  private BufferedImage apple;
  
  private BufferedImage terrainSprite;
  
  private BufferedImage[] piece = new BufferedImage[4];
  
  private BufferedImage[] head = new BufferedImage[4];
  
  MouseImputs mouseImputs = new MouseImputs(this);
  
  public GamePanel() {
    setFocusable(true);
    addKeyListener((KeyListener)new KeyboardImputs(this));
    addMouseListener((MouseListener)this.mouseImputs);
    addMouseMotionListener((MouseMotionListener)this.mouseImputs);
    this.CaselleX[0] = 50;
    this.CaselleY[0] = 60;
    int i;
    for (i = 1; i < this.MaxIndX; i++)
      this.CaselleX[i] = this.CaselleX[i - 1] + this.width; 
    for (i = 1; i < this.MaxIndY; i++)
      this.CaselleY[i] = this.CaselleY[i - 1] + this.heigth; 
    this.xDelta = this.CaselleX[this.MaxIndX / 2];
    this.yDelta = this.CaselleY[this.MaxIndY / 2];
    this.indX = this.MaxIndX / 2;
    this.indY = this.MaxIndY / 2;
    inizializzazione();
    setPanelSize();
  }
  
  private void setPanelSize() {
    setPreferredSize(new Dimension(this.CaselleX[this.MaxIndX - 1] + this.CaselleX[0] + this.width, this.CaselleY[this.MaxIndY - 1] + this.CaselleY[0] + this.heigth));
  }
  
  private void inizializzazione() {
    for (int i = 0; i < this.indice; i++) {
      this.AxDir[i] = (int)this.xDir;
      this.AyDir[i] = (int)this.yDir;
      if (i == 0) {
        this.AxDelta[i] = (int)(this.xDelta - this.width);
        this.AyDelta[i] = (int)this.yDelta;
        this.AindX[0] = this.indX - 1;
        this.AindY[0] = this.indY;
      } else {
        this.AxDelta[i] = this.AxDelta[i - 1] - this.width;
        this.AyDelta[i] = this.AyDelta[i - 1];
        this.AindX[i] = this.AindX[i - 1] - 1;
        this.AindY[i] = this.AindY[i - 1];
      } 
    } 
    try {
      this.pieceSprite = ImageIO.read(GamePanel.class.getResourceAsStream("/Piece.png"));
    } catch (IOException e) {
      e.printStackTrace();
    } 
    try {
      this.headSprite = ImageIO.read(GamePanel.class.getResourceAsStream("/Head.png"));
    } catch (IOException e) {
      e.printStackTrace();
    } 
    try {
      this.apple = ImageIO.read(GamePanel.class.getResourceAsStream("/Apple.png"));
    } catch (IOException e) {
      e.printStackTrace();
    } 
    try {
      this.terrainSprite = ImageIO.read(GamePanel.class.getResourceAsStream("/Terrain.png"));
    } catch (IOException e) {
      e.printStackTrace();
    } 
    
    getImages();
  }
  
  private void getImages() {
    this.piece[0] = this.pieceSprite.getSubimage(0, 0, 10, 10);
    this.piece[1] = this.pieceSprite.getSubimage(10, 0, 10, 10);
    this.piece[2] = this.pieceSprite.getSubimage(0, 10, 10, 10);
    this.piece[3] = this.pieceSprite.getSubimage(10, 10, 10, 10);
    this.head[0] = this.headSprite.getSubimage(0, 0, 10, 12);
    this.head[1] = this.headSprite.getSubimage(10, 0, 12, 10);
    this.head[2] = this.headSprite.getSubimage(0, 12, 12, 10);
    this.head[3] = this.headSprite.getSubimage(12, 10, 10, 12);
  }
  
  public void paint(Graphics g) {
    paintComponent(g);
    if (!this.GameStart)
      setBackground(new Color(175, 238, 238)); 
    int i;
    g.drawImage(terrainSprite, CaselleX[0], CaselleY[0], width*17, heigth*15, null);
    if (this.index != 0 && 
      this.turn) {
      if (this.index == 1) {
        SetDirs(0, -1);
      } else if (this.index == 2) {
        SetDirs(-1, 0);
      } else if (this.index == 3) {
        SetDirs(0, 1);
      } else {
        SetDirs(1, 0);
      } 
      this.turn = false;
      this.index = 0;
    } 
    updateRectangle();
    for (i = this.indice - 1; i >= 0; i--) {
      if (this.AxDir[i] > 0) {
        g.drawImage(this.piece[2], this.AxDelta[i], this.AyDelta[i], this.width, this.heigth, null);
      } else if (this.AxDir[i] < 0) {
        g.drawImage(this.piece[1], this.AxDelta[i], this.AyDelta[i], this.width, this.heigth, null);
      } else if (this.AyDir[i] > 0) {
        g.drawImage(this.piece[3], this.AxDelta[i], this.AyDelta[i], this.width, this.heigth, null);
      } else if (this.AyDir[i] < 0) {
        g.drawImage(this.piece[0], this.AxDelta[i], this.AyDelta[i], this.width, this.heigth, null);
      } 
    } 
    while (this.j) {
      if (this.indice + 1 != this.MaxIndX * this.MaxIndY) {
        int cont = 0;
        this.PosX = (new Random()).nextInt(0, this.MaxIndX);
        this.PosY = (new Random()).nextInt(0, this.MaxIndY);
        for (int j = 0; j < this.indice; j++) {
          if (this.PosX == this.AindX[j] && this.PosY == this.AindY[j])
            cont++; 
        } 
        if (cont < 1 && this.PosX != this.indX && this.PosY != this.indY)
          this.j = false; 
        continue;
      } 
      this.GameStart = false;
      this.j = false;
    } 
    g.drawImage(this.apple, this.CaselleX[this.PosX], this.CaselleY[this.PosY], this.width, this.heigth, null);
    if (this.xDir > 0.0F) {
      g.drawImage(this.head[2], (int)this.xDelta, (int)this.yDelta, this.width, this.heigth, null);
    } else if (this.xDir < 0.0F) {
      g.drawImage(this.head[1], (int)this.xDelta, (int)this.yDelta, this.width, this.heigth, null);
    } else if (this.yDir > 0.0F) {
      g.drawImage(this.head[3], (int)this.xDelta, (int)this.yDelta, this.width, this.heigth, null);
    } else if (this.yDir < 0.0F) {
      g.drawImage(this.head[0], (int)this.xDelta, (int)this.yDelta, this.width, this.heigth, null);
    } 
    g.drawString("Score:" + Integer.toString((int)(this.indice - 2)), 120, 30);
    
    g.drawString("Press 'space' to Start and 'R' to restart", 200, 30);
    this.frames++;
    if (System.currentTimeMillis() - this.LastCheck >= 1000L) {
      this.LastCheck = System.currentTimeMillis();
      this.Fps = this.frames;
      this.frames = 0;
    } 
  }
  
  private void updateRectangle() {
    if (this.GameStart) {
      if (this.xDelta > this.CaselleX[this.indX] && this.xDir > 0.0F) {
        if (this.indX == this.MaxIndX - 1) {
          this.GameStart = false;
        } else {
          this.indX++;
        } 
      } else if (this.xDelta < this.CaselleX[this.indX] && this.xDir < 0.0F) {
        if (this.indX == 0) {
          this.GameStart = false;
        } else {
          this.indX--;
        } 
      } else if (this.xDelta == this.CaselleX[this.indX] && this.index != 0 && this.xDir != 0.0F) {
        this.turn = true;
      } 
      if (this.yDelta > this.CaselleY[this.indY] && this.yDir > 0.0F) {
        if (this.indY == this.MaxIndY - 1) {
          this.GameStart = false;
        } else {
          this.indY++;
        } 
      } else if (this.yDelta < this.CaselleY[this.indY] && this.yDir < 0.0F) {
        if (this.indY == 0) {
          this.GameStart = false;
        } else {
          this.indY--;
        } 
      } else if (this.yDelta == this.CaselleY[this.indY] && this.index != 0 && this.yDir != 0.0F) {
        this.turn = true;
      } 
      this.xDelta += this.xDir;
      this.yDelta += this.yDir;
      if (this.xDelta == this.CaselleX[this.PosX] && this.yDelta == this.CaselleY[this.PosY]) {
        newPiece(this.indice);
        this.indice++;
        this.j = true;
      } 
      for (int i = 0; i < this.indice; i++) {
        this.AxDelta[i] = this.AxDelta[i] + this.AxDir[i];
        this.AyDelta[i] = this.AyDelta[i] + this.AyDir[i];
        if (this.AxDelta[i] == this.prevX[i] && this.AyDelta[i] == this.prevY[i])
          if (i == 0) {
            this.AxDir[0] = (int)this.xDir;
            this.AyDir[0] = (int)this.yDir;
            this.AxDelta[0] = (int)(this.xDelta - this.width * this.xDir);
            this.AyDelta[0] = (int)(this.yDelta - this.heigth * this.yDir);
            passPosition(1);
          } else {
            this.AxDir[i] = this.AxDir[i - 1];
            this.AyDir[i] = this.AyDir[i - 1];
            this.AxDelta[i] = this.AxDelta[i - 1] - this.width * this.AxDir[i - 1];
            this.AyDelta[i] = this.AyDelta[i - 1] - this.heigth * this.AyDir[i - 1];
            passPosition(i + 1);
          }  
        if (this.AxDelta[i] > this.CaselleX[this.AindX[i]] && this.AxDir[i] > 0) {
          this.AindX[i] = this.AindX[i] + 1;
        } else if (this.AxDelta[i] < this.CaselleX[this.AindX[i]] && this.AxDir[i] < 0) {
          this.AindX[i] = this.AindX[i] - 1;
        } 
        if (this.AyDelta[i] > this.CaselleY[this.AindY[i]] && this.AyDir[i] > 0) {
          this.AindY[i] = this.AindY[i] + 1;
        } else if (this.AyDelta[i] < this.CaselleY[this.AindY[i]] && this.AyDir[i] < 0) {
          this.AindY[i] = this.AindY[i] - 1;
        } 
        if (i > 2)
          if (this.xDir > 0.0f && this.AxDir[i] == 0 && this.AindX[i] == this.indX && this.AindY[i] == this.indY) {
            this.GameStart = false;
          } else if (this.xDir < 0.0f && this.AxDir[i] == 0 && this.AindX[i] == this.indX && this.AindY[i] == this.indY) {
            this.GameStart = false;
          } else if (this.yDir > 0.0f && this.AyDir[i] == 0 && this.AindY[i] == this.indY && this.AindX[i] == this.indX) {
            this.GameStart = false;
          } else if (this.yDir < 0.0f && this.AyDir[i] == 0 && this.AindY[i] == this.indY && this.AindX[i] == this.indX) {
            this.GameStart = false;
          }  
      } 
    } 
  }
  
  public void setRectPosition(int x, int y) {
    this.xDelta = x;
    this.yDelta = y;
  }
  
  public void setXDelta(int n) {
    this.xDelta += n;
  }
  
  public void setYDelta(int n) {
    this.yDelta += n;
  }
  
  public int getXDelta() {
    return (int)this.xDelta;
  }
  
  public int getYDelta() {
    return (int)this.yDelta;
  }
  
  public float getXDir() {
    return this.xDir;
  }
  
  public float getYDir() {
    return this.yDir;
  }
  
  public void SetDirs(int xDir, int yDir) {
    this.xDelta = this.CaselleX[this.indX];
    this.yDelta = this.CaselleY[this.indY];
    passPosition(0);
    this.xDir = xDir;
    this.yDir = yDir;
  }
  
  public void passPosition(int ind) {
    if (ind == 0) {
      this.prevX[0] = (int)this.xDelta;
      this.prevY[0] = (int)this.yDelta;
    } else {
      this.prevX[ind] = this.prevX[ind - 1];
      this.prevY[ind] = this.prevY[ind - 1];
    } 
  }
  
  public void SetIndex(int n) {
    this.index = n;
  }
  
  public void newPiece(int ind) {
    if (this.AindX[ind - 1] == this.MaxIndX - 1 && this.AxDir[ind - 1] < 0) {
      if (this.AindY[ind - 1] == this.MaxIndY - 1) {
        this.AindX[ind] = this.AindX[ind - 1];
        this.AindY[ind] = this.AindY[ind - 1] - 1;
        this.AxDelta[ind] = this.CaselleX[this.AindX[ind]];
        this.AyDelta[ind] = this.CaselleY[this.AindY[ind]];
        this.AxDir[ind] = 0;
        this.AyDir[ind] = 1;
      } else {
        this.AindX[ind] = this.AindX[ind - 1];
        this.AindY[ind] = this.AindY[ind - 1] + 1;
        this.AxDelta[ind] = this.CaselleX[this.AindX[ind]];
        this.AyDelta[ind] = this.CaselleY[this.AindY[ind]];
        this.AxDir[ind] = 0;
        this.AyDir[ind] = -1;
      } 
    } else if (this.AindX[ind - 1] == 0 && this.AxDir[ind - 1] > 0) {
      if (this.AindY[ind - 1] == this.MaxIndY - 1) {
        this.AindX[ind] = this.AindX[ind - 1];
        this.AindY[ind] = this.AindY[ind - 1] - 1;
        this.AxDelta[ind] = this.CaselleX[this.AindX[ind]];
        this.AyDelta[ind] = this.CaselleY[this.AindY[ind]];
        this.AxDir[ind] = 0;
        this.AyDir[ind] = 1;
      } else {
        this.AindX[ind] = this.AindX[ind - 1];
        this.AindY[ind] = this.AindY[ind - 1] + 1;
        this.AxDelta[ind] = this.CaselleX[this.AindX[ind]];
        this.AyDelta[ind] = this.CaselleY[this.AindY[ind]];
        this.AxDir[ind] = 0;
        this.AyDir[ind] = -1;
      } 
    } else if (this.AindY[ind - 1] == this.MaxIndY - 1 && this.AyDir[ind - 1] < 0) {
      if (this.AindX[ind - 1] == this.MaxIndX - 1) {
        this.AindX[ind] = this.AindX[ind - 1] - 1;
        this.AindY[ind] = this.AindY[ind - 1];
        this.AxDelta[ind] = this.CaselleX[this.AindX[ind]];
        this.AyDelta[ind] = this.CaselleY[this.AindY[ind]];
        this.AxDir[ind] = 1;
        this.AyDir[ind] = 0;
      } else {
        this.AindX[ind] = this.AindX[ind - 1] + 1;
        this.AindY[ind] = this.AindY[ind - 1];
        this.AxDelta[ind] = this.CaselleX[this.AindX[ind]];
        this.AyDelta[ind] = this.CaselleY[this.AindY[ind]];
        this.AxDir[ind] = -1;
        this.AyDir[ind] = 0;
      } 
    } else if (this.AindY[ind - 1] == 0 && this.AyDir[ind - 1] > 0) {
      if (this.AindX[ind - 1] == this.MaxIndX - 1) {
        this.AindX[ind] = this.AindX[ind - 1] - 1;
        this.AindY[ind] = this.AindY[ind - 1];
        this.AxDelta[ind] = this.CaselleX[this.AindX[ind]];
        this.AyDelta[ind] = this.CaselleY[this.AindY[ind]];
        this.AxDir[ind] = 1;
        this.AyDir[ind] = 0;
      } else {
        this.AindX[ind] = this.AindX[ind - 1] + 1;
        this.AindY[ind] = this.AindY[ind - 1];
        this.AxDelta[ind] = this.CaselleX[this.AindX[ind]];
        this.AyDelta[ind] = this.CaselleY[this.AindY[ind]];
        this.AxDir[ind] = -1;
        this.AyDir[ind] = 0;
      } 
    } else {
      this.AxDelta[ind] = this.AxDelta[ind - 1] - this.width * this.AxDir[ind - 1];
      this.AyDelta[ind] = this.AyDelta[ind - 1] - this.heigth * this.AyDir[ind - 1];
      this.AindX[ind] = this.AindX[ind - 1] - 1 * this.AxDir[ind - 1];
      this.AindY[ind] = this.AindY[ind - 1] - 1 * this.AyDir[ind - 1];
      this.AxDir[ind] = this.AxDir[ind - 1];
      this.AyDir[ind] = this.AyDir[ind - 1];
    } 
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public void RestartGame() {
    this.xDelta = this.CaselleX[this.MaxIndX / 2];
    this.yDelta = this.CaselleY[this.MaxIndY / 2];
    this.indX = this.MaxIndX / 2;
    this.indY = this.MaxIndY / 2;
    this.xDir = 1.0f;
    this.yDir = 0.0f;
    this.indice = 2;
    for (int i = 0; i < this.indice; i++) {
      this.AxDir[i] = (int)this.xDir;
      this.AyDir[i] = (int)this.yDir;
      if (i == 0) {
        this.AxDelta[i] = (int)(this.xDelta - this.width);
        this.AyDelta[i] = (int)this.yDelta;
        this.AindX[0] = this.indX - 1;
        this.AindY[0] = this.indY;
      } else {
        this.AxDelta[i] = this.AxDelta[i - 1] - this.width;
        this.AyDelta[i] = this.AyDelta[i - 1];
        this.AindX[i] = this.AindX[i - 1] - 1;
        this.AindY[i] = this.AindY[i - 1];
      } 
    } 
    this.j = true;
  }
}