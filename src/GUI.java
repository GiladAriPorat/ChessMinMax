/*

Ari Porat

sem 1 2022-2023 school year

this presents the game board to the screen and also allows for player input

 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
public class GUI extends JPanel {

    static inputter input = new inputter();
    Image BQ = getToolkit().getImage("imgs/BQ.png");
    Image WQ = getToolkit().getImage("imgs/WQ.png");
    Image BK = getToolkit().getImage("imgs/BK.png");
    Image WK = getToolkit().getImage("imgs/WK.png");
    Image BR = getToolkit().getImage("imgs/BR.png");
    Image WR = getToolkit().getImage("imgs/WR.png");
    Image BN = getToolkit().getImage("imgs/BN.png");
    Image WN = getToolkit().getImage("imgs/WN.png");
    Image BB = getToolkit().getImage("imgs/BB.png");
    Image WB = getToolkit().getImage("imgs/WB.png");
    Image BP = getToolkit().getImage("imgs/BP.png");
    Image WP = getToolkit().getImage("imgs/WP.png");


    //Boilerplate code, AKA not worth talking about.
    public GUI(){

        enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
        requestFocus();

        java.util.Timer t = new Timer(true);
        t.schedule(new java.util.TimerTask() {
            public void run() {
                repaint();
            }
        }, 10, 100);

    }
    public static void main(String[] args){

        JFrame win = new JFrame("chess");
        win.setSize(1000, 900);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI gui = new GUI();
        gui.addMouseListener(input);
        win.add(gui);
        win.setVisible(true);
        win.setResizable(false);

    }




    int counter = 0;
    public void paint(Graphics g){

        g.setColor(Color.WHITE);
        g.fillRect(0,0,1000,900);
        g.setColor(Color.BLACK);

        g.fillRect(825, 150, 75, 75);
        g.setColor(Color.WHITE);
        g.drawString("cycle", 830, 160);
        g.setColor(Color.BLACK);

        g.drawString("current position evaluation:", 825, 300);
        g.drawString(input.curEval + " ", 825, 325);
        g.drawString("positions searched: " + input.engine.positionsSearched + " ", 825, 350);

        if(input.piecePromotion == 0) g.drawString("Promotion to Queen", 825, 100);
        if(input.piecePromotion == 1) g.drawString("Promotion to Rook", 825, 100);
        if(input.piecePromotion == 2) g.drawString("Promotion to Knight", 825, 100);
        if(input.piecePromotion == 3) g.drawString("Promotion to Bishop", 825, 100);

        g.fillRect(825, 500, 175, 75);
        g.fillRect(825, 600, 175, 75);
        g.fillRect(825, 700, 175, 75);

        g.setColor(Color.WHITE);
        g.drawString("Regular MinMax Algorithm",835, 515);
        g.drawString("Sorted Alpha Beta",835, 615);
        g.drawString("auto play: " + input.autoPlay,835, 715);
        g.setColor(Color.BLACK);
        g.drawString("Best Move: \n" + input.engine.bestMove[0] + " --> " + input.engine.bestMove[1], 825, 375);
        g.drawString("Best Eval " + input.engine.BestEval, 825, 400);

        for(int row = 0; row < 8; row++){

            for(int col = 0; col < 8; col++){

                if(row % 2 == 0){

                    if(col % 2 == 0) g.setColor(Color.BLUE);
                    else g.setColor(Color.WHITE);

                }
                else{

                    if(col % 2 != 0) g.setColor(Color.BLUE);
                    else g.setColor(Color.WHITE);

                }

                for(int i = 1; i < input.highlights.length; i+=2){

                    if(row * 8 + col == input.highlights[i]) g.setColor(Color.GREEN);

                }

                if(row*8 + col == input.engine.bestMove[0] || row*8 + col == input.engine.bestMove[1]) g.setColor(Color.RED);

                g.fillRect(col * 100, (7 - row) * 100, 100, 100);

                if(input.engine.game.Current_Position[row * 8 + col] == 'q') g.drawImage(BQ, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'Q') g.drawImage(WQ, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'r') g.drawImage(BR, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'R') g.drawImage(WR, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'b') g.drawImage(BB, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'B') g.drawImage(WB, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'n') g.drawImage(BN, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'N') g.drawImage(WN, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'k') g.drawImage(BK, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'K') g.drawImage(WK, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'p') g.drawImage(BP, col * 100 + 15, (7 - row) * 100 + 15, this);
                if(input.engine.game.Current_Position[row * 8 + col] == 'P') g.drawImage(WP, col * 100 + 15, (7 - row) * 100 + 15, this);

            }

        }



        if(input.go){

            if(counter == 1) {
                counter = 0;
                input.go = false;
                input.engine.positionsSearched = 0;
                input.engine.newPositions = 0;
                input.engine.bestMove[0] = -1;
                input.engine.bestMove[1] = -1;


                System.out.println("Start");
                input.engine.BestEval = input.engine.Sortedalphabeta(input.engine.game.fen, -100000, 100000, input.engine.maxD);
                System.out.println("End");
                input.engine.game.fen = input.engine.game.PlayMove(input.engine.bestMove[0], input.engine.bestMove[1], input.engine.game.fen, 0);
                input.engine.game.Current_Position = input.engine.game.FenToArr(input.engine.game.fen);
                //if (input.engine.positionsSearched > 300000) input.engine.maxD--;
                //if (input.engine.positionsSearched > 600000) input.engine.maxD--;
                //if (input.engine.positionsSearched > 1000000) input.engine.maxD--;
                if (input.engine.positionsSearched < 100000 && input.engine.maxD < 5) input.engine.maxD++;

                input.curEval = input.engine.eval(input.engine.game.fen);
                System.out.println(input.engine.positionsSearched + " new depth: " + input.engine.maxD);
            }
            else counter = 1;
        }

    }




}
class inputter implements MouseListener{

    Engine engine = new Engine();
    int piece1 = -1;
    double curEval = 0;
    int piecePromotion = 0;
    Integer[] highlights = new Integer[0];
    boolean go =  false;
    boolean autoPlay = false;
    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getX() > 800){

            if(e.getX() > 825 && e.getX() < 900 && e.getY() > 150 && e.getY() < 225){

                piecePromotion++;
                if(piecePromotion > 3) piecePromotion = 0;

            }
            if(e.getX() > 825 && e.getX() < 1000 && e.getY() > 700 && e.getY() < 775){

                autoPlay = !autoPlay;

            }
            if(e.getX() > 825 && e.getX() < 1000 && e.getY() > 500 && e.getY() < 575){

                engine.positionsSearched = 0;
                engine.newPositions = 0;
                engine.bestMove[0] = -1;
                engine.bestMove[1] = -1;
                System.out.println("Start");
                engine.BestEval = engine.MiniMax();
                System.out.println("End");
            }
            if(e.getX() > 825 && e.getX() < 1000 && e.getY() > 600 && e.getY() < 675){
                engine.positionsSearched = 0;
                engine.newPositions = 0;
                engine.bestMove[0] = -1;
                engine.bestMove[1] = -1;
                System.out.println("Start");
                engine.BestEval = engine.Sortedalphabeta(engine.game.fen, -100000, 100000, engine.maxD);
                System.out.println("End");

            }

        }
        else {

            int row = (7 - e.getY() / 100);
            int col = e.getX() / 100;

            int index = row * 8 + col;

            if (piece1 == -1) {
                if ((Character.isUpperCase(engine.game.Current_Position[index]) && engine.game.fen.charAt(0) == 'w') || (!Character.isUpperCase(engine.game.Current_Position[index]) && engine.game.fen.charAt(0) == 'b')) {
                    piece1 = index;
                    highlights = engine.game.GetPieceMoves(piece1, engine.game.Current_Position);
                }
            } else {

                for (int i = 1; i < highlights.length; i += 2) {

                    if (highlights[i] == index) {

                        String temp = engine.game.PlayMove(piece1, index, engine.game.fen, piecePromotion);
                        if (!temp.equals("Error")) {
                            highlights = new Integer[0];
                            engine.positionsSearched = 0;
                            engine.newPositions = 0;
                            engine.bestMove[0] = -1;
                            engine.bestMove[1] = -1;
                            engine.game.fen = temp;
                            engine.game.Current_Position = engine.game.FenToArr(engine.game.fen);
                            piece1 = -1;
                            highlights = new Integer[0];
                            curEval = engine.eval(engine.game.fen);

                            if(autoPlay) go = true;


                        }
                        break;

                    }

                    if (i == highlights.length - 1) {

                        piece1 = -1;
                        highlights = new Integer[0];

                    }

                }
                if(highlights.length == 0) piece1 = -1;


            }
        }



    }
    @Override
    public void mousePressed(MouseEvent e) {


    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }


}