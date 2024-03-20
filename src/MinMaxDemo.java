/*

Ari Porat

sem 1 2022-2023 school year

this presents the game board to the screen and also allows for player input

 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
public class MinMaxDemo extends JPanel {

    Image stage1 = getToolkit().getImage("C:/Users/Admin/Desktop/demo1.png");

    static inputters input = new inputters();
    public MinMaxDemo(){

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

        JFrame win = new JFrame("Demo");
        win.setSize(800, 800);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MinMaxDemo demo = new MinMaxDemo();
        demo.addMouseListener(input);
        win.add(demo);
        win.setVisible(true);
        win.setResizable(false);

    }



    public void paint(Graphics g){


        g.setColor(Color.white);
        g.fillRect(0,0,800,800);
        g.setColor(Color.black);

        g.drawRect(25,25,100,100);
        g.drawString("Reroll", 50, 50);

        g.drawRect(25,600,100,100);
        g.drawString("Left", 50, 625);

        g.drawRect(675,600,100,100);
        g.drawString("Right", 700, 625);

        g.drawRect(350,600,100,100);
        g.drawString("Back", 375, 625);

        //g.drawImage(stage1, 50, 100, 700, 400, this);
        if(input.location.length() == 0){

            g.drawString("Player 1: Max", 350, 20);
            g.drawLine(400, 100, 200, 200);
            g.drawLine(400, 100, 600, 200);

            g.drawLine(600, 200, 650, 300);
            g.drawLine(600, 200, 550, 300);

            g.drawLine(200, 200, 250, 300);
            g.drawLine(200, 200, 150, 300);


            g.drawLine(250,300,275,400);
            g.drawLine(250,300,225,400);


            g.drawLine(150,300,175,400);
            g.drawLine(150,300,125,400);

            g.drawLine(550,300,575,400);
            g.drawLine(550,300,525,400);


            g.drawLine(650,300,675,400);
            g.drawLine(650,300,625,400);

            g.drawString(String.valueOf(input.numbers[0]), 122, 420);
            g.drawString(String.valueOf(input.numbers[1]), 172, 420);
            g.drawString(String.valueOf(input.numbers[2]), 222, 420);
            g.drawString(String.valueOf(input.numbers[3]), 272, 420);
            g.drawString(String.valueOf(input.numbers[4]), 522, 420);
            g.drawString(String.valueOf(input.numbers[5]), 572, 420);
            g.drawString(String.valueOf(input.numbers[6]), 622, 420);
            g.drawString(String.valueOf(input.numbers[7]), 672, 420);
}

        int[] currentArr = input.numbers;

        for(int i = 0; i < input.location.length(); i++){

            int[] temp = new int[0];
            if(input.location.charAt(i) == 'r'){

                temp = new int[currentArr.length/2];
                for(int j = currentArr.length/2; j < currentArr.length; j++){


                    temp[j - currentArr.length/2] = currentArr[j];
                }

            }
            else if(input.location.charAt(i) == 'l'){

                temp = new int[currentArr.length/2];
                for(int j = 0; j < currentArr.length/2; j++){


                    temp[j] = currentArr[j];
                }


            }
            currentArr = temp;

        }

        if(input.location.length() == 3){

            //g.drawString("Player 2: Min", 350, 20);
            g.drawString(String.valueOf(currentArr[0]), 400, 400);


        }
        if(input.location.length() == 2){
            g.drawString("Player 1: Max", 350, 20);
            g.drawLine(400, 100, 200, 200);
            g.drawLine(400, 100, 600, 200);

            g.drawString(String.valueOf(currentArr[0]), 198, 220);

            g.drawString(String.valueOf(currentArr[1]), 598, 220);

        }
        if(input.location.length() == 1){


            g.drawString("Player 2: Min", 350, 20);
            g.drawLine(400, 100, 200, 200);
            g.drawLine(400, 100, 600, 200);

            g.drawLine(600, 200, 650, 300);
            g.drawLine(600, 200, 550, 300);

            g.drawLine(200, 200, 250, 300);
            g.drawLine(200, 200, 150, 300);

            g.drawString(String.valueOf(currentArr[0]), 148, 320);

            g.drawString(String.valueOf(currentArr[1]), 248, 320);
            g.drawString(String.valueOf(currentArr[2]), 548, 320);
            g.drawString(String.valueOf(currentArr[3]), 648, 320);

        }
    }




}
class inputters implements MouseListener{

    int[] numbers = {1,2,3,4,5,6,7,8};

    String location = "";

    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getX() > 25 && e.getX() < 125 && e.getY() > 25 && e.getY() < 125) {
            for (int i = 0; i < numbers.length; i++) {

                numbers[i] = (int) (Math.random() * 8) + 1;

            }
        }

        if(e.getX() > 25 && e.getX() < 125 && e.getY() > 600 && e.getY() < 700) {
            location = location + 'l';
        }

        if(e.getX() > 675 && e.getX() < 775 && e.getY() > 600 && e.getY() < 700) {
            location = location + 'r';
        }

        if(e.getX() > 350 && e.getX() < 450 && e.getY() > 600 && e.getY() < 700) {
            if(location.length() > 0 ) location = location.substring(0,location.length()-1);
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