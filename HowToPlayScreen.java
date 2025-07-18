import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Example 2D String visualization
 * @author ICS4UE
 * @version Nov 2023
 */

public class HowToPlayScreen extends JFrame{
    final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();
    final int GRIDSIZE = MAX_Y/20;
    final int CENTER_COL = (MAX_X/4/GRIDSIZE)/2;
    final int CENTER_ROW = (MAX_Y/2/GRIDSIZE)/2;
    private Container container;
    private JFrame frame;
    private GPanel panel;
    private MyMouseListener mouseListener;
    
    
    //additional items go here
    private JButton previousslide;
    private JButton nextslide;
    private JButton skiptutorial;
    private int slideNum=1;
    
    //how moves look
    private BufferedImage s1i1b;
    private JLabel s1i1;
    //starting board
    private BufferedImage s3i1b;
    private JLabel s3i1;
    //dead board state
    private BufferedImage s4i1b;
    private JLabel s4i1;
    //before a turn, highlighting where board will be filled with 3+
    private BufferedImage s5i1b;
    private JLabel s5i1;
    //kiki's opening
    private BufferedImage s6i1b;
    private JLabel s6i1;
    //capturing a piece
    private BufferedImage s7i1b;
    private JLabel s7i1;
    //capturing a piece, uncapturing opponent's
    private BufferedImage s7i2b;
    private JLabel s7i2;
    //breakthrough
    private BufferedImage s8i1b;
    private JLabel s8i1;
    //full advance
    private BufferedImage s9i1b;
    private JLabel s9i1;
    //point calculations
    private BufferedImage s10i1b;
    private JLabel s10i1;
    
    
    public void initialize () {
    	
    	this.frame=new JFrame();
        this.panel = new GPanel();
        this.panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);
        container=this.getContentPane();
        this.getContentPane().add(BorderLayout.CENTER, panel);
        mouseListener=new MyMouseListener();
        container.addMouseListener(mouseListener);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_X/2, MAX_Y/2);
        
       //Additional setup goes here
        previousslide=new JButton("Previous");
        previousslide.setFont(new Font("among us", Font.PLAIN, MAX_X/90));
        previousslide.setBounds(MAX_X/32,MAX_Y*4/10,MAX_X/8,MAX_Y/30);
        previousslide.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
        		if (slideNum==11){
    				panel.add(nextslide);
    			} 
        		if (slideNum>1) {
        			slideNum-=1;
        			if(slideNum==2) {
        				s3i1.setVisible(false);
        				slideNum-=1;
        			}
        			if (slideNum==1){
        				panel.remove(previousslide);
        			}
        			
        		}
        	}
        });
        
        nextslide=new JButton("Next");
        nextslide.setFont(new Font("among us", Font.PLAIN, MAX_X/90));
        nextslide.setBounds(MAX_X*11/32,MAX_Y*4/10,MAX_X/8,MAX_Y/30);
        nextslide.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
        		if (slideNum==1){
    				panel.add(previousslide);
    			} 
        		if (slideNum<11) {
        			slideNum+=1;
        			if (slideNum==11){
        				panel.remove(nextslide);
        			}
        			if(slideNum==2) {
        				s1i1.setVisible(false);
        				slideNum+=1;
        			}
        			
        		}
        	}
        });
        
        skiptutorial=new JButton("Quit tutorial");
        skiptutorial.setFont(new Font("among us", Font.PLAIN, MAX_X/90));
        skiptutorial.setBounds(MAX_X*3/16,MAX_Y*4/10,MAX_X/8,MAX_Y/30);
        skiptutorial.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
        		//close the panel
        	}
        });
    
        panel.add(nextslide);
        panel.add(skiptutorial);
        this.setVisible(true);
        
        try {
        	s1i1b = ImageIO.read(new File("src/s1i1.png"));
        	s1i1=new JLabel(new ImageIcon(s1i1b.getScaledInstance(MAX_X/9,MAX_Y/4,Image.SCALE_SMOOTH)));
        	s1i1.setBounds(MAX_X/6,MAX_Y/8,MAX_X/9,MAX_Y/4);
        }catch (Exception e) {System.out.println("s1i1 did not load");};
        panel.add(s1i1);
        s1i1.setVisible(false);
        
        try {
        	s3i1b = ImageIO.read(new File("src/s3i1.png"));
        	s3i1=new JLabel(new ImageIcon(s3i1b.getScaledInstance(MAX_X/7,MAX_Y/4,Image.SCALE_SMOOTH)));
        	s3i1.setBounds(MAX_X*3/16,MAX_Y/8,MAX_X/7,MAX_Y/4);
        }catch (Exception e) {System.out.println("s3i1 did not load");};
        panel.add(s3i1);
        s3i1.setVisible(false);
        
        try {
        	s4i1b = ImageIO.read(new File("src/s4i1.png"));
        	s4i1=new JLabel(new ImageIcon(s4i1b.getScaledInstance(MAX_X/7,MAX_Y/4,Image.SCALE_SMOOTH)));
        	s4i1.setBounds(MAX_X*3/16,MAX_Y/8,MAX_X/7,MAX_Y/4);
        }catch (Exception e) {System.out.println("s4i1 did not load");};
        panel.add(s4i1);
        s4i1.setVisible(false);
        try {
        	s5i1b = ImageIO.read(new File("src/s5i1.png"));
        	s5i1=new JLabel(new ImageIcon(s5i1b.getScaledInstance(MAX_X/5,MAX_Y*3/10,Image.SCALE_SMOOTH)));
        	s5i1.setBounds(MAX_X/8,MAX_Y/11,MAX_X/5,MAX_Y*3/10);
        }catch (Exception e) {System.out.println("s5i1 did not load");};
        panel.add(s5i1);
        s5i1.setVisible(false);
        try {
        	s6i1b = ImageIO.read(new File("src/s6i1.png"));
        	s6i1=new JLabel(new ImageIcon(s6i1b.getScaledInstance(MAX_X/7,MAX_Y/4,Image.SCALE_SMOOTH)));
        	s6i1.setBounds(MAX_X/16,MAX_Y/8,MAX_X/7,MAX_Y/4);
        }catch (Exception e) {System.out.println("s6i1 did not load");};
        panel.add(s6i1);
        s6i1.setVisible(false);
        try {
        	s7i1b = ImageIO.read(new File("src/s7i1.png"));
        	s7i1=new JLabel(new ImageIcon(s7i1b.getScaledInstance(MAX_X/7,MAX_Y/4,Image.SCALE_SMOOTH)));
        	s7i1.setBounds(MAX_X/16,MAX_Y/8,MAX_X/7,MAX_Y/4);
        }catch (Exception e) {System.out.println("s7i1 did not load");};
        panel.add(s7i1);
        s7i1.setVisible(false);
        try {
        	s7i2b = ImageIO.read(new File("src/s7i2.png"));
        	s7i2=new JLabel(new ImageIcon(s7i2b.getScaledInstance(MAX_X/7,MAX_Y/4,Image.SCALE_SMOOTH)));
        	s7i2.setBounds(MAX_X*5/16,MAX_Y/8,MAX_X/7,MAX_Y/4);
        }catch (Exception e) {System.out.println("s7i2 did not load");};
        panel.add(s7i2);
        s7i2.setVisible(false);
        try {
        	s8i1b = ImageIO.read(new File("src/s8i1.png"));
        	s8i1=new JLabel(new ImageIcon(s8i1b.getScaledInstance(MAX_X/3,MAX_Y/4,Image.SCALE_SMOOTH)));
        	s8i1.setBounds(MAX_X/15,MAX_Y/8,MAX_X/3,MAX_Y/4);
        }catch (Exception e) {System.out.println("s8i1 did not load");};
        panel.add(s8i1);
        s8i1.setVisible(false);
        try {
        	s9i1b = ImageIO.read(new File("src/s9i1.png"));
        	s9i1=new JLabel(new ImageIcon(s9i1b.getScaledInstance(MAX_X/3,MAX_Y/4,Image.SCALE_SMOOTH)));
        	s9i1.setBounds(MAX_X/15,MAX_Y/8,MAX_X/3,MAX_Y/4);
        }catch (Exception e) {System.out.println("s9i1 did not load");};
        panel.add(s9i1);
        s9i1.setVisible(false);
        try {
        	s10i1b = ImageIO.read(new File("src/s10i1.png"));
        	s10i1=new JLabel(new ImageIcon(s10i1b.getScaledInstance(MAX_X/7,MAX_Y/4,Image.SCALE_SMOOTH)));
        	s10i1.setBounds(MAX_X*3/16,MAX_Y/8,MAX_X/7,MAX_Y/4);
        }catch (Exception e) {System.out.println("s9i1 did not load");};
        panel.add(s10i1);
        s10i1.setVisible(false);
        
    }
    
    
    
    
    private class GPanel extends JPanel {
      
        @Override
        public void paintComponent(Graphics g) {
          
            super.paintComponent(g);
            g.setFont(new Font("among us", Font.PLAIN, MAX_Y/48));
            if (slideNum==1) {
            	g.drawString("Welcome to Breakthrough!",0,MAX_Y/48);
            	g.drawString("This game is based on the famous Conway's soldiers problem.",0,MAX_Y/24);
            	g.drawString("In this problem, there are pieces in a grid.",0,MAX_Y/16);
            	g.drawString("You can have one piece jump over another, and land on the piece in front of it.",0,MAX_Y/12);
            	s1i1.setVisible(true);
            	
            }else if (slideNum==2) {
            	g.drawString("Try it yourself!",0,MAX_Y/48);
            	g.drawString("Click on a piece, and select where you want it to jump over.",0,MAX_Y/24);
            	s1i1.setVisible(false);
            	s3i1.setVisible(false);
            //2x2 square of light gray pieces in a 4x4 grid
            } else if (slideNum==3) {
            	g.drawString("You play as either White or Black in this game.",0,MAX_Y/48);
            	g.drawString("Like in chess, White goes first.",0,MAX_Y/24);
            	g.drawString("The game is played on a 14x14 grid.",0,MAX_Y/16);
            	g.drawString("To start the game, each player has two filled rows of pieces,placed in the 3rd and 4th rows.",0,MAX_Y/12);
            	s3i1.setVisible(true);
            	s4i1.setVisible(false);
            
            }else if (slideNum==4) {
            	g.drawString("Your turn continues until you can make no more valid moves",0,MAX_Y/48);
            	g.drawString("that is, no more of your pieces can jump over other pieces.",0,MAX_Y/24);
            	s3i1.setVisible(false);
            	s4i1.setVisible(true);
            	s5i1.setVisible(false);
            }else if (slideNum==5) {
            	g.drawString("After your first turn, to start your turn, the row that is",0,MAX_Y/48);
            	g.drawString("two rows behind your furthest advance piece is fully filled.",0,MAX_Y/24);
            	//[diagram]
            	g.drawString("The row behind this can then be filled using three pieces, plus",0,MAX_Y/16);
            	g.drawString("the number of pieces you had in the filled row before.",0,MAX_Y/12);
            	//[diagram]
            	s4i1.setVisible(false);
            	s5i1.setVisible(true);
            	s6i1.setVisible(false);
             
            
            }else if (slideNum==6) {
            	g.drawString("additionally, if the row behind the filled row gets filled, this",0,MAX_Y/48);
            	g.drawString("phase of your turn is over, and you do not fill in more pieces.",0,MAX_Y/24);
            	//[diagram]
            	//g.drawString("Try placing pieces down!",0,MAX_Y/16);
            	//[random start position with some number of placeable pieces]
            	s5i1.setVisible(false);
            	s6i1.setVisible(true);
            	s7i1.setVisible(false);
            	s7i2.setVisible(false);
            }else if (slideNum==7) {
            	g.drawString("When you jump over one of your opponent's pieces, you capture that piece.",0,MAX_Y/48);
            	//[diagram]
            	g.drawString("If your opponent has captured some of your pieces and you now capture",0,MAX_Y/16);
            	g.drawString("one of theirs, you uncapture one of your captured pieces.",0,MAX_Y/12);
            	//[diagram]
            	s6i1.setVisible(false);
            	s7i1.setVisible(true);
            	s7i2.setVisible(true);
            	s8i1.setVisible(false);
            	
            }else if (slideNum==8) {
            	g.drawString("If, on your opponent's turn, you have pieces on or behind the",0,MAX_Y/48);
            	g.drawString("row they're going to fill, that piece has Broken Through.",0,MAX_Y/24);
            	g.drawString("This piece is removed from the board.",0,MAX_Y/16);
            	//[diagram]
            	s7i1.setVisible(false);
            	s7i2.setVisible(false);
            	s8i1.setVisible(true);
            	s9i1.setVisible(false);
            }else if (slideNum==9) {
            	g.drawString("There are two ways a game can end: ",0,MAX_Y/48);
            	g.drawString("1. A player's furthest advanced piece",0,MAX_Y/24);
            	g.drawString("is in their back three rows at the start",0,MAX_Y/16);
            	g.drawString("of their turn. This means they can no ",0,MAX_Y/12);
            	g.drawString("longer place pieces to start their turn.",0,MAX_Y*5/48);
            	
            	//diagram
            	g.drawString("2. A player has three or more Breakthroughs.",MAX_X/4,MAX_Y/24);
            	//diagram
            	s9i1.setVisible(true);
            	s8i1.setVisible(false);
            	s10i1.setVisible(false);
            }else if (slideNum==10) {
            	g.drawString("To win, you want to have the most points when the game ends.",0,MAX_Y/48);
            	g.drawString("You get 1 point for each capture you have.",0,MAX_Y/24);
            	g.drawString("You get 1/2 points for each square you've advanced over the center red line.",0,MAX_Y/16);
            	g.drawString("Your score is then multiplied by 1 plus the number of Breakthroughs you have.",0,MAX_Y/12);
            	s9i1.setVisible(false);
            	s10i1.setVisible(true);
            //[diagrams]
            }else if (slideNum==11) {
            	g.drawString("That's all the rules of the game!",0,MAX_Y/48);
            	g.drawString("Remember that even after you master the rules of the game,",0,MAX_Y/24);
            	g.drawString("the real challenge is getting good at the game itself!",0,MAX_Y/16);
            	g.drawString("So get playing and find what strategies help you win!",0,MAX_Y/12);
            	s10i1.setVisible(false);
            }
            
           
            this.repaint();
        }
    }
    
    class MyMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent e){
          //probably no need for this
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
