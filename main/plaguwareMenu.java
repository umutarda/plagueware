

package main;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;



public class plaguwareMenu
{
    JFrame frame = new JFrame("PLAGUEWARE");
    JPanel panelCont = new JPanel();
    Icon backIcon = new ImageIcon("main/pngFile/BACK.png");
    
    
    JPanel letsRoll = new JPanel();
    JPanel mainMenu = new JPanel();
    JPanel chooseMode = new JPanel();
    JPanel information = new JPanel();
    JPanel credits = new JPanel();
    JPanel leaderboard = new JPanel();
    JTextArea leaderboardText = new JTextArea();
    ArrayList<User> x = User.LeaderboardToArray();


    
    Icon letsrollicon = new ImageIcon("main/pngFile/letsrollbutton.png");
    JButton letsTOmain = new JButton(letsrollicon);

    Icon playIcon = new ImageIcon("main/pngFile/mainmenuPLAY.png");
    JButton mainTOchooseMode = new JButton(playIcon);
    Icon infoIcon = new ImageIcon("main/pngFile/mainmenuINFOv3.png");
    JButton mainTOInfo = new JButton(infoIcon);
    Icon creditIcon = new ImageIcon("main/pngFile/mainmenuCREDITSv2.png");
    JButton mainTOCredits = new JButton(creditIcon);
    Icon leaderIcon = new ImageIcon("main/pngFile/mainmenuLEADv2.png");
    JButton mainTOLead = new JButton(leaderIcon);
    JButton mainTOlets = new JButton(backIcon);

    
    Icon EndIcon = new ImageIcon("main/pngFile/choosemodeENDv2.png");
    JButton chooseEndBringer = new JButton(EndIcon);
    Icon simIcon = new ImageIcon("main/pngFile/choosemodeSIMUv2.png");
    JButton chooseSimulation = new JButton(simIcon);
    Icon savIcon = new ImageIcon("main/pngFile/choosemodeSAVv2.png");
    JButton chooseSaviour = new JButton(savIcon);
    JButton chooseTOmain = new JButton(backIcon);
    
    
    JButton creditTOmain = new JButton(backIcon);
    JButton leaderTOmain = new JButton(backIcon);
    JButton infoTOmain = new JButton(backIcon);


    CardLayout cl = new CardLayout();

    public plaguwareMenu()
    {
        try 
        {
            panelCont.setLayout(cl);
            
            letsTOmain.setPreferredSize(new Dimension(660,150));
            letsRoll.add(letsTOmain);
            BufferedImage myPicture = ImageIO.read(new File("main/pngFile/letsroll.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            letsRoll.add(picLabel);
            letsRoll.setBackground(Color.BLACK);
            
            mainTOchooseMode.setPreferredSize(new Dimension(350,120));
            mainTOCredits.setPreferredSize(new Dimension(350,120));
            mainTOCredits.setBackground(Color.BLACK);
            mainTOInfo.setPreferredSize(new Dimension(330,120));
            mainTOInfo.setBackground(Color.BLACK);
            mainTOLead.setPreferredSize(new Dimension(570,120));
            mainTOlets.setPreferredSize(new Dimension(130,120));
            mainMenu.add(mainTOchooseMode);
            mainMenu.add(mainTOCredits);
            mainMenu.add(mainTOInfo);
            mainMenu.add(mainTOLead);
            mainMenu.add(mainTOlets);
            BufferedImage myPicture2 = ImageIO.read(new File("main/pngFile/mainmenu.png"));
            JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
            mainMenu.add(picLabel2);
            mainMenu.setBackground(Color.BLACK);

            
            chooseEndBringer.setPreferredSize(new Dimension(600,120));
            chooseEndBringer.setBackground(Color.BLACK);
            chooseSimulation.setPreferredSize(new Dimension(560,120));
            chooseSimulation.setBackground(Color.BLACK);
            chooseSaviour.setPreferredSize(new Dimension(550,120));
            chooseSaviour.setBackground(Color.BLACK);
            chooseTOmain.setPreferredSize(new Dimension(120,120));
            chooseMode.add(chooseEndBringer);
            chooseMode.add(chooseSimulation);
            chooseMode.add(chooseSaviour);
            chooseMode.add(chooseTOmain);
            BufferedImage myPicture3 = ImageIO.read(new File("main/pngFile/choosemodev2.png"));
            JLabel picLabel3 = new JLabel(new ImageIcon(myPicture3));
            chooseMode.add(picLabel3);
            chooseMode.setBackground(Color.BLACK);

            
            infoTOmain.setPreferredSize(new Dimension(800,120));
            infoTOmain.setBackground(Color.BLACK);
            information.add(infoTOmain);
            BufferedImage myPicture4 = ImageIO.read(new File("main/pngFile/info.png"));
            JLabel picLabel4 = new JLabel(new ImageIcon(myPicture4));
            information.add(picLabel4);
            information.setBackground(Color.BLACK);

            
            creditTOmain.setPreferredSize(new Dimension(800,120));
            creditTOmain.setBackground(Color.BLACK);
            credits.add(creditTOmain);
            BufferedImage myPicture5 = ImageIO.read(new File("main/pngFile/credit.png"));
            JLabel picLabel5 = new JLabel(new ImageIcon(myPicture5));
            credits.add(picLabel5);
            credits.setBackground(Color.BLACK);


            leaderTOmain.setPreferredSize(new Dimension(800,120));
            leaderTOmain.setBackground(Color.BLACK);
            leaderboard.add(leaderTOmain);
            BufferedImage myPicture6 = ImageIO.read(new File("main/pngFile/leaderboard.png"));
            JLabel picLabel6 = new JLabel(new ImageIcon(myPicture6));

            for (User user : x) {
                if(user.toString().length()>0)
                    leaderboardText.append(user.toString());
            }
            leaderboardText.setForeground(Color.RED);
            leaderboardText.setBackground(Color.black);
            leaderboardText.setSize(700, 700);
            leaderboardText.setFont(new Font("Serif", Font.ITALIC, 26));
            JScrollPane j = new JScrollPane(leaderboardText); //might be removed
            leaderboard.add(j);
            //leaderboard.add(picLabel6);
            //leaderboard.setBackground(Color.BLACK);


            panelCont.add(letsRoll, "1");
            panelCont.add(mainMenu, "2");
            panelCont.add(chooseMode, "3");
            panelCont.add(information, "4");
            panelCont.add(credits, "5");
            panelCont.add(leaderboard, "6");


            
            cl.show(panelCont,"1");
            letsTOmain.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    cl.show(panelCont, "2");
                }
            });
            
            mainTOchooseMode.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    cl.show(panelCont, "3");
                }
            });
            mainTOInfo.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    cl.show(panelCont, "4");
                }
            });
            mainTOCredits.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    cl.show(panelCont, "5");
                }
            });
            mainTOLead.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    cl.show(panelCont, "6");
                }
            });
            mainTOlets.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    cl.show(panelCont, "1");
                }
            });
            chooseTOmain.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    cl.show(panelCont, "2");
                }
            });
            creditTOmain.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    cl.show(panelCont, "2");
                }
            });
            infoTOmain.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    cl.show(panelCont, "2");
                }
            });
            leaderTOmain.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    cl.show(panelCont, "2");
                }
            });
            chooseSimulation.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    try {
                        new Main().main(new String[0]);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            
            
            frame.add(panelCont);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setResizable(false); 
            frame.setVisible(true);
        } 
        catch (IOException e) {
             System.out.println();}
        

        


    }



    public static void main(String[] args) {
    
        new plaguwareMenu();

    }
}