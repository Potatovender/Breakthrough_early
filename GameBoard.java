import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * Example 2D String visualization
 * @author ICS4UE
 * @version Nov 2023
 */
public class GameBoard extends JFrame{
    final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();
    final int GRIDSIZE = MAX_Y/20;

    final int CENTER_COL = (MAX_X/4/GRIDSIZE)/2;
    final int CENTER_ROW = (MAX_Y/2/GRIDSIZE)/2;
    
    
    private Container container;
    
    
    private JLabel whowon;
    private JLabel top= new JLabel("                   White      |   Black");
    
    private JLabel capturedata;
    private String capturestring="captures:          ";
    private JLabel advancedata;
    private String advancestring="advanced:          ";
    private JLabel promotedata;
    private String promotestring="promotions:      ";
    private JLabel finalscores;
    private String finalstring="final score:     ";
    
    private JButton howtoplay;
    
    private JFrame frame;
    private GPanel panel;
    public int[][] board;
    private int rows, cols;
    private int leftCol, topRow;
    private int captures;
    private int[] promotes;
    private int turn;
    private int move;
    private int placepiece;
    private MyMouseListener mouseListener;
    private boolean placing;
    private boolean selectspot;
    private int pointX=15;
    private int pointY=15;
    private int selectX;
    private int selectY;
    private int placerow;
    
    private boolean messagedisplayed;
    
    private boolean winner;
    
    private ArrayList<int[]>availablepoints;
    
    public void initialize () {
    	
    	this.frame=new JFrame();
        this.panel = new GPanel();
        this.panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);
        container=this.getContentPane();
        this.getContentPane().add(BorderLayout.CENTER, panel);
        mouseListener=new MyMouseListener();
        container.addMouseListener(mouseListener);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_X*17/25, MAX_Y*22/25);
        
         //JLabel testlabel1= new JLabel("testing");
         //JLabel testlabel2= new JLabel("testing");
       
       
         
    
        this.setVisible(true);
        
        
        
        this.rows = 14;  //number of lines  
        this.cols = 14;       //length of the first line
        this.leftCol = CENTER_COL - cols/2;
        this.topRow  = CENTER_ROW - rows/2;
        
        
        board=new int[14][14];
        for(int x=0;x<14;x++){
          board[2][x]=2;
          board[3][x]=2;
          board[11][x]=1;
          board[10][x]=1;
        }
        
        
        captures=0;
        
        
        promotes=new int[2];
        promotes[0]=0;
        promotes[1]=0;
        
        turn=1;//sets player turn
        move=1;//counts the number of total rounds each player has made
        
        selectspot=false;
        
        
        availablepoints=new ArrayList<int[]>(4);
        
        winner=false;
        
        
        //panel1 = new JPanel();
        //panel1.add(new JLabel("Hello"));
        
        howtoplay=new JButton("How do I play?");
        
        panel.add(howtoplay);
        howtoplay.setFont(new Font("among us", Font.PLAIN, MAX_X/90));
        howtoplay.setBounds(MAX_X/10,MAX_Y*79/100,MAX_X/4,MAX_Y/30);
        
        howtoplay.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
        		//initiate the tutorial screen
        		HowToPlayScreen htps=new HowToPlayScreen();
        		htps.initialize();
        	}
        });
        
    }
    public static boolean elementIn(ArrayList<int[]> points,int[] point){
      for(int x=0;x<points.size();x++){
        if(points.get(x)[0]==point[0] && points.get(x)[1]==point[1]){
          return true;
        }
      }
      return false;
    }
    
    
    
    
    
    public static boolean canMove(int[][] board,int player){
      
      for(int x=0;x<14;x++){
        for(int y=0;y<14;y++){
          if(board[x][y]==player){
            if(x>1){
              //System.out.println("a");
              if(board[x-1][y]!=0 && board[x-2][y]==0){
                //System.out.println("a");
                return true;
              }
            }
            
            if(x<12){
              //System.out.println("b");
              if(board[x+1][y]!=0 && board[x+2][y]==0){
                //System.out.println("b");
                return true;
              }
            }
            
            if(y>1){
              //System.out.println("c");
              if(board[x][y-1]!=0 && board[x][y-2]==0){
                //System.out.println("c");
                return true;
              }
            }
            if(y<12){
              //System.out.println("d");
              if(board[x][y+1]!=0 && board[x][y+2]==0){
                //System.out.println("d");
                return true;
              }
            }
          }
        }
      
      }
      return false;
    }
    
    
    public static int findFarthest(int[][] board,int player){
      if(player==2){
        for(int x=13;x>=0;x--){
          for(int y=0;y<14;y++){
            if(board[x][y]==2){
              return x;
            }
          }
      
        }
      }
      else if(player==1){
        for(int x=0;x<14;x++){
          for(int y=0;y<14;y++){
            if(board[x][y]==1){
              return x;
            }
          }
      
        }
      }
      return -1;
    }
    
    
    public static boolean filledRow(int[][] board,int placerow){
      for(int x=0;x<14;x++){
        if(board[placerow][x]==0){
          return false;
        }
      }
      return true;
    }
    

    
    private class GPanel extends JPanel {
      
        @Override
        public void paintComponent(Graphics g) {
          
            super.paintComponent(g);
            
            
            //draw the board pieces
            if (turn==1) {
	            for (int row=0; row<rows; row++){
	                for (int col=0; col<cols; col++){
	                    if (board[row][col] == 0){
	                        g.setColor(Color.LIGHT_GRAY);
	                        g.fillRect(col*GRIDSIZE, row*GRIDSIZE, GRIDSIZE, GRIDSIZE);      
	                       
	                    }else if (board[row][col] == 1){                       
	                        g.setColor(Color.WHITE);
	                        g.fillOval(col*GRIDSIZE, row*GRIDSIZE, GRIDSIZE, GRIDSIZE);
	                    }else if (board[row][col] == 2){
	                        g.setColor(Color.BLACK);
	                        g.fillOval(col*GRIDSIZE, row*GRIDSIZE, GRIDSIZE, GRIDSIZE);
	                        
	                    }
	                }
	            }    
            } else if (turn==2) {
            	for (int row=0; row<rows; row++){
	                for (int col=0; col<cols; col++){
	                    if (board[13-row][13-col] == 0){
	                        g.setColor(Color.LIGHT_GRAY);
	                        g.fillRect(col*GRIDSIZE, row*GRIDSIZE, GRIDSIZE, GRIDSIZE);      
	                       
	                    }else if (board[13-row][13-col] == 1){                       
	                        g.setColor(Color.WHITE);
	                        g.fillOval(col*GRIDSIZE, row*GRIDSIZE, GRIDSIZE, GRIDSIZE);
	                    }else if (board[13-row][13-col] == 2){
	                        g.setColor(Color.BLACK);
	                        g.fillOval(col*GRIDSIZE, row*GRIDSIZE, GRIDSIZE, GRIDSIZE);
	                        
	                    }
	                }
	            }   
            }
            //draw captures
            if (captures>0){
              for(int x=0;x<captures;x++){
                g.setColor(Color.BLACK);
                g.fillOval((16+x/7)*GRIDSIZE, (7+x%7)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
                
              }
            } else if(captures<0){
              for(int x=0;x>captures;x--){
                g.setColor(Color.WHITE);
                g.fillOval((16+x/7)*GRIDSIZE, (6+x%7)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
                
              }
            }
            //draw promotes
            g.setColor(Color.WHITE);
            for(int x=0;x<promotes[0];x++){
              g.fillOval((15+x/7)*GRIDSIZE, (7+x%7)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
            }
            g.setColor(Color.BLACK);
            for(int x=0;x<promotes[0];x++){
              g.fillOval((int)((15.25+x/7)*GRIDSIZE),(int)((7.25+x%7)*GRIDSIZE), GRIDSIZE/2, GRIDSIZE/2);
            }
            for(int x=0;x<promotes[1];x++){
              g.fillOval((15-x/7)*GRIDSIZE, (6-x%7)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
            }
            g.setColor(Color.WHITE);
            for(int x=0;x<promotes[1];x++){
              g.fillOval((int)((15.25-x/7)*GRIDSIZE),(int)((6.25-x%7)*GRIDSIZE), GRIDSIZE/2, GRIDSIZE/2);
            }
            
            //draw the person who's moving
            
            if(turn==1){
              g.setColor(Color.WHITE);
            }
            else if(turn==2){
              g.setColor(Color.BLACK);
            }
            
            g.fillOval((int)(0.5*GRIDSIZE),(int)((14.5)*GRIDSIZE), (int)(GRIDSIZE*1.5), (int)(GRIDSIZE*1.5));
            
            //draw pieces left to place
            if(placing==true){
              for(int x=3;x<placepiece+3;x++){
                g.fillOval(x*GRIDSIZE,(int)((14.5)*GRIDSIZE), (int)(GRIDSIZE), (int)(GRIDSIZE));
              }
            }
            
            
            //draw the grid lines
            g.setColor(Color.BLACK);
            for (int x=0; x<GRIDSIZE*15; x=x+GRIDSIZE){
                g.drawLine(x,0,x,GRIDSIZE*14);
            }
            for (int y=0; y<GRIDSIZE*15; y=y+GRIDSIZE){
                g.drawLine(0,y,GRIDSIZE*14,y);
            }
            g.setColor(Color.RED);
            g.drawLine(0,GRIDSIZE*7,GRIDSIZE*14,GRIDSIZE*7);
            
            //draw movement options
          if(pointX<=13 && pointY<=13){
            selectX=pointX;
            selectY=pointY;
              if (board[pointX][pointY]==turn){
                g.setColor(Color.GRAY);
                
                if(pointX>1){
                  //System.out.println("a");
                  if(board[pointX-1][pointY]!=0 && board[pointX-2][pointY]==0){
                    
                    g.fillOval((int)((pointY+0.25)*GRIDSIZE),(int)((pointX-1.75)*GRIDSIZE), GRIDSIZE/2, GRIDSIZE/2);
                    selectspot=true;
                    int[] arr={pointX-2,pointY};
                    if(elementIn(availablepoints,arr)==false){
                      availablepoints.add(arr);
                      //System.out.println(availablepoints.size());
                    }
                  }
                }
                if(pointX<12){
                  //System.out.println("b");
                  if(board[pointX+1][pointY]!=0 && board[pointX+2][pointY]==0){
                    
                    g.fillOval((int)((pointY+0.25)*GRIDSIZE),(int)((pointX+2.25)*GRIDSIZE), GRIDSIZE/2, GRIDSIZE/2);
                    selectspot=true;
                    int[] arr={pointX+2,pointY};
                    if(elementIn(availablepoints,arr)==false){
                      availablepoints.add(arr);
                      //System.out.println(availablepoints.size());
                    }
                  }
                }
                if(pointY>1){
                  //System.out.println("c");
                  if(board[pointX][pointY-1]!=0 && board[pointX][pointY-2]==0){
                    g.fillOval((int)((pointY-1.75)*GRIDSIZE),(int)((pointX+0.25)*GRIDSIZE), GRIDSIZE/2, GRIDSIZE/2);
                    selectspot=true;
                    int[] arr={pointX,pointY-2};
                    if(elementIn(availablepoints,arr)==false){
                      availablepoints.add(arr);
                      //System.out.println(availablepoints.size());
                    }
                  }
                }
                if(pointY<12){
                  //System.out.println("d");
                  if(board[pointX][pointY+1]!=0 && board[pointX][pointY+2]==0){
                    g.fillOval((int)((pointY+2.25)*GRIDSIZE),(int)((pointX+0.25)*GRIDSIZE), GRIDSIZE/2, GRIDSIZE/2);
                    selectspot=true;
                    int[] arr={pointX,pointY+2};
                    if(elementIn(availablepoints,arr)==false){
                      availablepoints.add(arr);
                      //System.out.println(availablepoints.size());
                    }
                  }
              }
                //for(int x=0;x<availablepoints.size();x++){
                //  System.out.print(availablepoints.get(x)[0]);
                //  System.out.print(" ");
                //  System.out.println(availablepoints.get(x)[1]);
                //}
                
            }
          }
          
          //drawing winner stuff
          
          if(winner==true && messagedisplayed==false){
            messagedisplayed=true;
            //System.out.println("aaa");
            double playeronepoints=0;
            double playertwopoints=0;
            
            capturestring="captures:          ";
            advancestring="advanced:          ";
            promotestring="promotions:      ";
            finalstring="final score:     ";
            
            int tempsaver=findFarthest(board,1);
            if(tempsaver<7){
              advancestring+=Integer.toString(7-tempsaver)+"     |     ";
              playeronepoints+=(7-tempsaver)/2.0;
            }
            else{
              advancestring+="0     |     ";
            }
            if(captures>0){
              if(captures>=10){
                capturestring+=Integer.toString(captures)+"     |     0";
              }
              else{
                capturestring+=" "+Integer.toString(captures)+"     |     0";
              }
              playeronepoints+=captures;
            }
            else{
              capturestring+=" 0     |     ";
            }
            playeronepoints*=(promotes[0]+1);
            
            tempsaver=findFarthest(board,2);
            if(tempsaver>6){
              advancestring+=Integer.toString(tempsaver-6);
              playertwopoints+=(tempsaver-6)/2.0;
            }
            else{
              advancestring+="0";
            }
            if(captures<0){
              if(captures<=-10){
                capturestring+=Integer.toString(0-captures);
              }
              else{
                capturestring+=" "+Integer.toString(0-captures);
              }
              playertwopoints-=captures;
            }
            else{
              capturestring+=" 0";
            }
            playertwopoints*=(promotes[1]+1);
            if(promotes[0]>=10){
              promotestring+=Integer.toString(promotes[0])+"     |     ";
            }
            else{
              promotestring+=" "+Integer.toString(promotes[0])+"     |     ";
            }
            if(promotes[1]>=10){
              promotestring+=Integer.toString(promotes[1]);
            }
            else{
              promotestring+=" "+Integer.toString(promotes[1]);
            }
            if(playeronepoints>=10){
              finalstring+=Double.toString(playeronepoints)+"     |     ";
            }
            else{
              finalstring+=" "+Double.toString(playeronepoints)+"     |     ";
            }
            if(playertwopoints>=10){
              finalstring+=Double.toString(playertwopoints);
            }
            else{
              finalstring+=" "+Double.toString(playertwopoints);
            }
            capturedata=new JLabel(capturestring);
            advancedata=new JLabel(advancestring);
            promotedata=new JLabel(promotestring);
            finalscores=new JLabel(finalstring);
            
            //display game data
            
            String winneridentity;
            if (playeronepoints>playertwopoints || (playeronepoints==playertwopoints && turn==2)) {
            	winneridentity="White wins!";
            }
            else {
            	winneridentity="Black wins!";
            }
            	
            whowon=new JLabel(winneridentity);
            panel.add(whowon);
            whowon.setFont(new Font("among us", Font.PLAIN, MAX_X/50));
            whowon.setBounds(MAX_X*13/25,0,MAX_X/6,40);
            panel.add(top);
            top.setFont(new Font("among us", Font.PLAIN, MAX_X/100));
            top.setBounds(MAX_X*13/25,MAX_Y/18,MAX_X/6,MAX_Y/18);
            panel.add(capturedata);
            capturedata.setFont(new Font("among us", Font.PLAIN, MAX_X/100));
            capturedata.setBounds(MAX_X*13/25,MAX_Y/9,MAX_X/6,MAX_Y/18);
            panel.add(advancedata);
            advancedata.setFont(new Font("among us", Font.PLAIN, MAX_X/100));
            advancedata.setBounds(MAX_X*13/25,MAX_Y/6,MAX_X/6,MAX_Y/18);
            panel.add(promotedata);
            promotedata.setFont(new Font("among us", Font.PLAIN, MAX_X/100));
            promotedata.setBounds(MAX_X*13/25,MAX_Y*2/9,MAX_X/6,MAX_Y/18);
            panel.add(finalscores);
            finalscores.setFont(new Font("among us", Font.PLAIN, MAX_X/100));
            finalscores.setBounds(MAX_X*13/25,MAX_Y*5/18,MAX_X/6,MAX_Y/18);
            
          
            this.repaint();
        }
    }
    
        
    
    
    }
    public class MyMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent e){
          if(placing==true){
            //System.out.println("aa");
            pointY=e.getX()/GRIDSIZE;
            pointX=e.getY()/GRIDSIZE;//inverted for some reason
            if (turn==2) {
            	pointX=13-pointX;
            	pointY=13-pointY;
            }
            
            if(pointX<14 && pointY<14 && board[pointX][pointY]==0){
              if(turn==1 && pointX==placerow+1){
                board[pointX][pointY]=1;
                placepiece-=1;
                if(filledRow(board,placerow+1)==true){
                    placepiece=0;
                  }
              }
              else if(turn==2 && pointX==placerow-1){
                board[pointX][pointY]=2;
                placepiece-=1;
                if(filledRow(board,placerow-1)==true){
                    placepiece=0;
                }
              }
            }
            if(placepiece==0){
              //System.out.println("aa");
              placing=false;
              //System.out.println(placing);
            }
            panel.repaint();
          }
          else{
              // temp placeholder variables until we make actual buttons
            
            //System.out.println("aa");
            if(pointY==e.getX()/GRIDSIZE && pointX==e.getY()/GRIDSIZE){
              selectspot=false;
            }
            pointY=e.getX()/GRIDSIZE;
            pointX=e.getY()/GRIDSIZE;//inverted for some reason
            //point on graph
            
            if(selectspot==true){
              int[] arr={pointX,pointY};
              //System.out.print(arr[0]);
              //System.out.print(" ");
              //System.out.println(arr[1]);
              //System.out.println(elementIn(availablepoints,arr));
              if(elementIn(availablepoints,arr)==true){
                board[pointX][pointY]=board[selectX][selectY];
                board[selectX][selectY]=0;
              if(board[(selectX+pointX)/2][(selectY+pointY)/2]==3-board[pointX][pointY]){
                captures+=3-turn*2;
              }
              board[(selectX+pointX)/2][(selectY+pointY)/2]=0;
              if(canMove(board,turn)==false){
                turn=3-turn;
                move+=1;
                //System.out.println("a");
                
                //placing the back row and promotes
                if(move>2){
                  placepiece=3;
                  int farthest=findFarthest(board,turn);
                  
                  
                  if(turn==1 && farthest>10){
                    winner=true;
                    
                  }
                  else if(turn==2 && farthest<3){
                    
                    winner=true;
                  }
                  
                  else{
                    //System.out.println(placerow);
                    if(turn==1){
                      placerow=farthest+2;
                    
                      for(int x=placerow;x<14;x++){
                        for(int y=0;y<14;y++){
                          if(board[x][y]==2){
                            promotes[1]+=1;
                            board[x][y]=0;
                          }
                        }
                      }
                      if(promotes[0]>=3 ||promotes[1]>=3){
                        winner = true;
                      }
                      else{
                        for(int y=0;y<14;y++){
                          if(board[placerow][y]==1){
                            placepiece+=1;
                          }
                          board[placerow][y]=1;
                        }
                      }
              
                    }
                    else if(turn==2){
                      placerow=farthest-2;
                      for(int x=placerow;x>=0;x--){
                        for(int y=0;y<14;y++){
                          if(board[x][y]==1){
                            promotes[0]+=1;
                            board[x][y]=0;
                          }
                        }
                      }
                      if(promotes[0]>=3 ||promotes[1]>=3){
                        winner = true;
                      }
                      else{
                        for(int y=0;y<14;y++){
                          if(board[placerow][y]==2){
                            placepiece+=1;
                        
                          }
                          board[placerow][y]=2;
                        }
                      }
                    }
                    placing=true;
            
                  }
                }
              }
            }
          }
          availablepoints.clear();
          
          
          panel.repaint();
          
          }
          
          
        }
        public void mousePressed(MouseEvent e){   // MUST be implemented even if not used!
        }
        public void mouseReleased(MouseEvent e){  // MUST be implemented even if not used!
          
        
        }
        public void mouseEntered(MouseEvent e){   // MUST be implemented even if not used!
        }
        public void mouseExited(MouseEvent e){    // MUST be implemented even if not used!
        }
    }
    
    
}
//           player 1 | player 2
//captures:     ##         ##
//advanced:      #          #
//promotions:   ##         ##
//final score:  ##         ##