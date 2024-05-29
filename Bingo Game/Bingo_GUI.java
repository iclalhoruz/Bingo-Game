package tombala;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Bingo_GUI {
    
    private static final int ROWS = 3;
    private static final int COLUMNS = 9; 
    private static final int GAP = 70; 
    public static JPanel cardPanel;
    private static JLabel numLabel;
    private static JButton numButton;
    private static JPanel buttonPanel;

    public static void main(int[][] card1, int[][] card2, LinkedList<Integer> list1, LinkedList<Integer> list2, LinkedList<Integer> permList) {
        JFrame frame = new JFrame("Bingo Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(186, 204, 200));
        frame.getContentPane().add(mainPanel);
        mainPanel.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
        mainPanel.add(Box.createHorizontalStrut(20), BorderLayout.EAST);
        mainPanel.add(Box.createHorizontalStrut(20), BorderLayout.WEST);
        
        //kart paneli
        cardPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBingoCard(g, getWidth(), getHeight(), card1, card2, list1, list2);
            }
        };
        cardPanel.setBackground(new Color(186, 204, 200));
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        
        //buton ve text paneli
        buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        buttonPanel.setOpaque(false);
        
        
        //çekilen sayılar
        numLabel = new JLabel("Oyuna Başlayınız", SwingConstants.CENTER);
        numLabel.setFont(new Font("Phosphate", Font.BOLD, 60));
        numLabel.setForeground(Color.BLACK);
        buttonPanel.add(numLabel);
        
        //sayı çekme butonu
        numButton = new JButton("Sayı Çek");
        numButton.setBackground(new Color(186, 204, 200)); 
        numButton.setFont(new Font("Arial", Font.BOLD, 40));
        numButton.setOpaque(true);
        numButton.setBorderPainted(false);      
        numButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int permNum = permList.getPerm();
                numLabel.setText(String.valueOf(permNum));
                Main.bingoControl(list1, list2, permList);
                System.out.println("Sayı Çekildi");
                cardPanel.repaint();
            }
        });
        buttonPanel.add(numButton);
        
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        
        frame.setSize(1700, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
        frame.setResizable(false);
        
    }
    
    private static void drawBingoCard(Graphics g, int width, int height, int[][] card1, int[][] card2, LinkedList<Integer> list1, LinkedList<Integer> list2){  
        int cellSize = Math.min((width - 2 - 2 * GAP) / (COLUMNS * 2), height / ROWS);
        int cardWidth = COLUMNS * cellSize;
        int bingoLblHeight = height - cellSize * 2;
        int zinc1 = Main.zinc1;
        int zinc2 = Main.zinc2;
        
        cardPanel.removeAll();
        
        // Player 1, Player 2 yazısı
        JLabel player1Lbl = new JLabel("Player 1", SwingConstants.LEFT);
        player1Lbl.setFont(new Font("Arial", Font.BOLD, 20));
        player1Lbl.setForeground(Color.BLACK);
        player1Lbl.setBounds(0, 0, cardWidth, cellSize);
        cardPanel.add(player1Lbl);
        
        JLabel player2Lbl = new JLabel("Player 2", SwingConstants.LEFT);
        player2Lbl.setFont(new Font("Arial", Font.BOLD, 20));
        player2Lbl.setForeground(Color.BLACK);
        player2Lbl.setBounds(cardWidth + 2 * GAP, 0, cardWidth, cellSize);
        cardPanel.add(player2Lbl);
        
        // Çinko ve Bingo yazıları
        JLabel bingoLbl1 = new JLabel("Bingo", SwingConstants.CENTER);
        bingoLbl1.setFont(new Font("Arial", Font.BOLD, 20));
        bingoLbl1.setForeground(Color.BLACK);
        bingoLbl1.setBounds(0, bingoLblHeight, cardWidth, cellSize);
        cardPanel.add(bingoLbl1);

        JLabel bingoLbl2 = new JLabel("Bingo", SwingConstants.CENTER);
        bingoLbl2.setFont(new Font("Arial", Font.BOLD, 20));
        bingoLbl2.setForeground(Color.BLACK);
        bingoLbl2.setBounds(cardWidth + 2 * GAP, bingoLblHeight, cardWidth, cellSize);
        cardPanel.add(bingoLbl2);
        
        // Birinci kart
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                
                int xPos = x * cellSize;
                int yPos = cellSize + y * cellSize;
                
                g.setColor(new Color(0, 182, 148)); 
                g.fillRect(xPos, yPos, cellSize, cellSize);
                
                //arrayden sayıları yazdırma
                if(card1[y][x] != -1){
                    boolean found = list1.search(card1[y][x]);
                    if(found == false){
                        g.setColor(new Color(0, 182, 148));
                        g.fillRect(xPos, yPos, cellSize, cellSize);
                    
                        JLabel lbl = new JLabel(String.valueOf(card1[y][x]), SwingConstants.CENTER);
                        lbl.setBounds(xPos, yPos, cellSize, cellSize);
                        lbl.setFont(new Font("Phosphate", Font.BOLD, 45));
                        lbl.setForeground(Color.DARK_GRAY);
                        cardPanel.add(lbl);
                    }
                    else{
                        g.setColor(Color.white);
                        g.fillRect(xPos, yPos, cellSize, cellSize);
                    
                        JLabel lbl = new JLabel(String.valueOf(card1[y][x]), SwingConstants.CENTER);
                        lbl.setBounds(xPos, yPos, cellSize, cellSize);
                        lbl.setFont(new Font("Arial", Font.BOLD, 20));
                        lbl.setForeground(Color.BLACK);
                        cardPanel.add(lbl);
                    }
                }
                
                if(zinc1 == 0){
                    bingoLbl1.setText(" ");
                }
                
                else if(zinc1 == 3){
                    bingoLbl1.setText("Bingo");
                }
                
                else{
                    bingoLbl1.setText(zinc1 + ". Çinko");
                }
                
                g.setColor(Color.BLACK);
                g.drawRect(xPos, yPos, cellSize, cellSize);
            }
        }
        
        int secondCard = cardWidth + 2 * GAP;
        
        // İkinci kart
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                
                int xPos = secondCard + x * cellSize;
                int yPos = y * cellSize + cellSize;
                
                g.setColor(new Color(222, 49, 99)); 
                g.fillRect(xPos, yPos, cellSize, cellSize);
                
                //arrayden sayıları yazdırma
                if(card2[y][x] != -1){
                    boolean found = list2.search(card2[y][x]);
                    
                    if(found == false){
                        g.setColor(new Color(222, 49, 99));
                        g.fillRect(xPos, yPos, cellSize, cellSize);
                    
                        JLabel lbl = new JLabel(String.valueOf(card2[y][x]), SwingConstants.CENTER);
                        lbl.setBounds(xPos, yPos, cellSize, cellSize);
                        lbl.setFont(new Font("Phosphate", Font.BOLD, 45));
                        lbl.setForeground(Color.DARK_GRAY);
                        cardPanel.add(lbl);
                    }
                    else{
                        g.setColor(Color.white);
                        g.fillRect(xPos, yPos, cellSize, cellSize);
                    
                        JLabel lbl = new JLabel(String.valueOf(card2[y][x]), SwingConstants.CENTER);
                        lbl.setBounds(xPos, yPos, cellSize, cellSize);
                        lbl.setFont(new Font("Arial", Font.BOLD, 20));
                        lbl.setForeground(Color.BLACK);
                        cardPanel.add(lbl);
                    }
                }   
                
                if(zinc2 == 0){
                    bingoLbl2.setText(" ");
                }
                
                else if(zinc2 == 3){
                    bingoLbl2.setText("Bingo");
                }
                
                else{
                    bingoLbl2.setText(zinc2 + ". Çinko");
                }
                
                g.setColor(Color.BLACK);
                g.drawRect(xPos, yPos, cellSize, cellSize);
            }        
        }
        
        if(zinc1 == 3 || zinc2 == 3){
            endGame();
        }
        
        
    }

    private static void endGame() {
        buttonPanel.removeAll();
  
        if(Main.zinc1 == 3 && Main.zinc2 == 3){
            numLabel.setText("ALL PLAYERS WON");
            numLabel.setFont(new Font("Arial", Font.BOLD, 50));
            numLabel.setForeground(Color.BLUE);
        }
        
        else if(Main.zinc2 == 3){
           numLabel.setText("PLAYER 2 WON"); 
           numLabel.setFont(new Font("Arial", Font.BOLD, 50));
           numLabel.setForeground(new Color(222, 49, 99)); 
        }
        
        else if(Main.zinc1 == 3){
           numLabel.setText("PLAYER 1 WON"); 
           numLabel.setFont(new Font("Arial", Font.BOLD, 50));
           numLabel.setForeground(new Color(0, 182, 148)); 
        }
        
        buttonPanel.add(numLabel);
        buttonPanel.repaint();
    }
    
}
