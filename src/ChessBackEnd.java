/*

Ari Porat

sem 1 2022-2023 school year

this runs the chess game logic ensuring the game operates by the correct rules

 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class ChessBackEnd {
    Scanner c = new Scanner(System.in);




    //PART 1 - Representation of the board
     char[] Current_Position =
            {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r', 'w', 'y', 'y', 'y', 'y', 'n', 'n'};
     String fen = "wyyyynnRNBQKBNRPPPPPPPP////pppppppprnbqkbnr";
     // turn, WKC, WQC, BKC, BQC, En-passant row, En-passant col, rest of position
     String ArrToFen(char[] pos) {

        String Fen = "";
        Fen = Fen + pos[64] + pos[65] + pos[66] + pos[67] + pos[68] + pos[69] + pos[70];

        int space = 0;

        for (int i = 0; i < 64; i++) {

            if (pos[i] == ' ') {

                space++;
                if (space > 7) {

                    space = 0;
                    Fen = Fen + '/';

                }

            } else {

                if (space > 0) {

                    Fen = Fen + String.valueOf(space);
                    space = 0;

                }

                Fen = Fen + pos[i];


            }


        }

        return Fen;

    }
     char[] FenToArr(String Fen) {

        char[] pos = new char[71];

        Arrays.fill(pos, ' ');


        pos[70] = Fen.charAt(6);
        pos[69] = Fen.charAt(5);
        pos[68] = Fen.charAt(4);
        pos[67] = Fen.charAt(3);
        pos[66] = Fen.charAt(2);
        pos[65] = Fen.charAt(1);
        pos[64] = Fen.charAt(0);


        int index = 0;

        for (int i = 7; i < Fen.length(); i++) {


            if (Fen.charAt(i) == '/') index += 8;
            else if (Fen.charAt(i) == '1') index += 1;
            else if (Fen.charAt(i) == '2') index += 2;
            else if (Fen.charAt(i) == '3') index += 3;
            else if (Fen.charAt(i) == '4') index += 4;
            else if (Fen.charAt(i) == '5') index += 5;
            else if (Fen.charAt(i) == '6') index += 6;
            else if(Fen.charAt(i) == '7') index += 7;
            else{
                pos[index] = Fen.charAt(i);
                index++;

            }


        }

        return pos;


    }




     //PART 2 - move generation
     Integer[] PosMoves(String Fen){

        ArrayList<Integer> Moves = new ArrayList<>();

        char[] tempPos = FenToArr(Fen);

        boolean White = false;

        if(Fen.charAt(0) == 'w') White = true;


            int index = 0;

            for (int i = 7; i < Fen.length(); i++) {


                if (Fen.charAt(i) == '/') index += 8;
                else if (Fen.charAt(i) == '1') index += 1;
                else if (Fen.charAt(i) == '2') index += 2;
                else if (Fen.charAt(i) == '3') index += 3;
                else if (Fen.charAt(i) == '4') index += 4;
                else if (Fen.charAt(i) == '5') index += 5;
                else if (Fen.charAt(i) == '6') index += 6;
                else if(Fen.charAt(i) == '7') index += 7;
                else{
                    if((White && Character.isUpperCase(Fen.charAt(i))) || (!White && !Character.isUpperCase(Fen.charAt(i)))){

                        Integer[] move = GetPieceMoves(index, tempPos);
                        for(int a = 0; a < move.length; a++){

                            Moves.add(move[a]);

                        }


                    }
                    index++;

                }


            }



        return Moves.toArray(new Integer[] {1});

    }
     Integer[] GetPieceMoves(int index, char[] pos){

        ArrayList<Integer> Moves = new ArrayList<>();
        int row = index / 8 ;
        int col = index % 8 ;
        boolean upperCase = Character.isUpperCase(pos[index]);
        switch(pos[index]){

            case 'n':
            case 'N':

                if(row + 2 <= 7 && col + 1 <= 7) {

                    if(pos[(row + 2) * 8 + (col + 1)] == ' ' || Character.isUpperCase(pos[(row + 2) * 8 + (col + 1)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row + 2) * 8 + (col + 1));
                    }

                }
                if(row - 2 >= 0 && col + 1 <= 7) {

                    if(pos[(row - 2) * 8 + (col + 1)] == ' ' || Character.isUpperCase(pos[(row - 2) * 8 + (col + 1)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row - 2) * 8 + (col + 1));
                    }
                }
                if(row + 2 <= 7 && col - 1 >= 0) {

                    if(pos[(row + 2) * 8 + (col - 1)] == ' ' || Character.isUpperCase(pos[(row + 2) * 8 + (col - 1)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row + 2) * 8 + (col - 1));
                    }

                }
                if(row - 2 >= 0 && col - 1 >= 0) {

                    if(pos[(row - 2) * 8 + (col - 1)] == ' ' || Character.isUpperCase(pos[(row - 2) * 8 + (col - 1)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row - 2) * 8 + (col - 1));
                    }

                }
                if(row + 1 <= 7 && col + 2 <= 7) {

                    if(pos[(row + 1) * 8 + (col + 2)] == ' ' || Character.isUpperCase(pos[(row + 1) * 8 + (col + 2)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row + 1) * 8 + (col + 2));
                    }
                }
                if(row - 1 >= 0 && col + 2 <= 7) {

                    if(pos[(row - 1) * 8 + (col + 2)] == ' ' || Character.isUpperCase(pos[(row - 1) * 8 + (col + 2)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row - 1) * 8 + (col + 2));
                    }

                }
                if(row + 1 <= 7 && col - 2 >= 0) {

                    if(pos[(row + 1) * 8 + (col - 2)] == ' ' || Character.isUpperCase(pos[(row + 1) * 8 + (col - 2)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row + 1) * 8 + (col - 2));
                    }
                }
                if(row - 1 >= 0 && col - 2 >= 0) {
                if(pos[(row  - 1) * 8 + (col  - 2)] == ' ' || Character.isUpperCase(pos[(row - 1) * 8 + (col - 2)]) != upperCase) {
                        Moves.add(index);
                        Moves.add((row - 1) * 8 + (col - 2));
                    }

                }

                break;
            case 'p':

                    if(pos[(row - 1) * 8 + col] == ' '){

                        Moves.add(index);
                        Moves.add((row - 1) * 8 + col);
                        if(row == 6 && pos[32 + col] == ' ' ){

                            Moves.add(index);
                            Moves.add(32 + col);

                        }

                    }
                    if(col < 7 && Character.isUpperCase(pos[(row - 1) * 8 + col + 1])){

                        Moves.add(index);
                        Moves.add((row - 1) * 8 + col + 1);

                    }
                    if (col > 0 && Character.isUpperCase(pos[(row - 1) * 8 + col - 1])){

                        Moves.add(index);
                        Moves.add((row - 1) * 8 + col - 1);

                    }
                    if(pos[69] != 'n' && pos[70] != 'n'){

                        if(pos[69] - '0' == row + 1 && Math.abs((char) (pos[70] - '1') - col) == 1){

                            Moves.add(index);
                            Moves.add((row - 1) * 8 + (char) (pos[70] - '1'));

                        }

                    }

                break;
            case 'P':

                if(pos[(row + 1) * 8 + col] == ' '){

                    Moves.add(index);
                    Moves.add((row + 1) * 8 + col);
                    if(row == 1 && pos[24 + col] == ' ' ){

                        Moves.add(index);
                        Moves.add(24 + col);

                    }

                }
                if(col < 7 && !Character.isUpperCase(pos[(row + 1) * 8 + col + 1]) && pos[(row + 1) * 8 + col + 1] != ' '){

                    Moves.add(index);
                    Moves.add((row + 1) * 8 + col + 1);

                }
                if (col > 0 && !Character.isUpperCase(pos[(row + 1) * 8 + col - 1]) && pos[(row + 1) * 8 + col - 1] != ' '){

                    Moves.add(index);
                    Moves.add((row + 1) * 8 + col - 1);

                }
                if(pos[69] != 'n' && pos[70] != 'n'){


                    if(pos[69] - '0' == row + 1 && Math.abs((char) (pos[70] - '1') - col) == 1){

                        Moves.add(index);
                        Moves.add((row + 1) * 8 + (char) (pos[70] - '1'));


                    }

                }

                break;
            case 'r':
            case 'R':

                for(int i = row+1; i <= 7; i++){

                    if(pos[(i) * 8 + (col)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (col));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (col)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (col));
                        break;

                    }
                    else break;

                }
                for(int i = row-1; i >= 0; i--){

                    if(pos[(i) * 8 + (col)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (col));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (col)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (col));
                        break;

                    }
                    else break;

                }

                for(int i = col+1; i <= 7; i++){

                    if(pos[(row) * 8 + (i)] == ' '){
                        Moves.add(index);
                        Moves.add((row) * 8 + (i));
                    }
                    else if (Character.isUpperCase(pos[(row) * 8 + (i)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row) * 8 + (i));
                        break;

                    }
                    else break;

                }
                for(int i = col-1; i >= 0; i--){

                    if(pos[(row) * 8 + (i)] == ' '){
                        Moves.add(index);
                        Moves.add((row) * 8 + (i));
                    }
                    else if (Character.isUpperCase(pos[(row) * 8 + (i)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row) * 8 + (i));
                        break;

                    }
                    else break;

                }


                break;
            case 'q':
            case 'Q':

                for(int i = row+1, j = col+1; i <= 7 && j <= 7; i++, j++){

                    if(pos[(i) * 8 + (j)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (j)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                        break;

                    }
                    else break;

                }
                for(int i = row-1, j = col+1; i >= 0 && j <= 7; i--, j++){

                    if(pos[(i) * 8 + (j)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (j)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                        break;

                    }
                    else break;

                }
                for(int i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--){

                    if(pos[(i) * 8 + (j)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (j)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                        break;

                    }
                    else break;

                }
                for(int i = row+1, j = col-1; i <= 7 && j >= 0; i++, j--){

                    if(pos[(i) * 8 + (j)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (j)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                        break;

                    }
                    else break;

                }

                for(int i = row+1; i <= 7; i++){

                    if(pos[(i) * 8 + (col)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (col));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (col)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (col));
                        break;

                    }
                    else break;

                }
                for(int i = row-1; i >= 0; i--){

                    if(pos[(i) * 8 + (col)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (col));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (col)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (col));
                        break;

                    }
                    else break;

                }

                for(int i = col+1; i <= 7; i++){

                    if(pos[(row) * 8 + (i)] == ' '){
                        Moves.add(index);
                        Moves.add((row) * 8 + (i));
                    }
                    else if (Character.isUpperCase(pos[(row) * 8 + (i)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row) * 8 + (i));
                        break;

                    }
                    else break;

                }
                for(int i = col-1; i >= 0; i--){

                    if(pos[(row) * 8 + (i)] == ' '){
                        Moves.add(index);
                        Moves.add((row) * 8 + (i));
                    }
                    else if (Character.isUpperCase(pos[(row) * 8 + (i)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((row) * 8 + (i));
                        break;

                    }
                    else break;

                }

                break;
            case 'k':
            case 'K':

                for(int i = -1; i < 2; i++){

                    for(int j = -1; j < 2; j++){

                        if(j == 0 && i == 0) continue;

                        if(row + i <= 7 && col + j <= 7 && row + i >= 0 && col + j >= 0) {

                            int a = (row + i) * 8 + (col + j);
                            if(pos[a] == ' ' || Character.isUpperCase(pos[a]) != upperCase) {

                                Moves.add(index);
                                Moves.add(a);
                            }

                        }

                    }

                }


                if(pos[66] == 'y' && pos[2] == ' ' && pos[3] == ' ' && pos[1] == ' ' && pos[index] == 'K'){

                    Moves.add(index);
                    Moves.add(2);

                }
                if(pos[65] == 'y' && pos[6] == ' ' && pos[5] == ' ' && pos[index] == 'K'){

                    Moves.add(index);
                    Moves.add(6);

                }


                if(pos[67] == 'y' && pos[62] == ' ' && pos[61] == ' ' && pos[index] == 'k'){

                    Moves.add(index);
                    Moves.add(62);

                }
                if(pos[68] == 'y' && pos[57] == ' ' && pos[58] == ' ' && pos[59] == ' ' && pos[index] == 'k'){

                    Moves.add(index);
                    Moves.add(58);

                }




                break;
            case 'b':
            case 'B':

                for(int i = row+1, j = col+1; i <= 7 && j <= 7; i++, j++){

                    if(pos[(i) * 8 + (j)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (j)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                        break;

                    }
                    else break;

                }
                for(int i = row-1, j = col+1; i >= 0 && j <= 7; i--, j++){

                    if(pos[(i) * 8 + (j)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (j)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                        break;

                    }
                    else break;

                }
                for(int i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--){

                    if(pos[(i) * 8 + (j)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (j)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                        break;

                    }
                    else break;

                }
                for(int i = row+1, j = col-1; i <= 7 && j >= 0; i++, j--){

                    if(pos[(i) * 8 + (j)] == ' '){
                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                    }
                    else if (Character.isUpperCase(pos[(i) * 8 + (j)]) != upperCase) {

                        Moves.add(index);
                        Moves.add((i) * 8 + (j));
                        break;

                    }
                    else break;

                }

                break;


        }

        return Moves.toArray(new Integer[] {});


    }


     //PART 3 - rule set
     String PlayMove(int from, int to, String position, int promotion){

        char[] pos = FenToArr(position);

        boolean reset = true;
        if(pos[from] == 'p' || pos[from] == 'P'){

            if(Math.abs((from/8) - (to / 8)) == 2){



                pos[69] = (char) (to/8 + '1');
                pos[70] = (char) (to%8 + '1');

                reset = false;

            }
            if(from%8 != to%8 && pos[to] == ' '){

                pos[(pos[69] - '1') * 8 + (pos[70] - '1')] = ' ';

            }

        }

        if(pos[from] == 'k'){

            pos[67] = 'n';
            pos[68] = 'n';

        }
        if(pos[from] == 'K'){

            pos[66] = 'n';
            pos[65] = 'n';

        }
        if(pos[0] != 'R'){

            pos[66] = 'n';

        }
        if(pos[7] != 'R') pos[65] = 'n';
        if(pos[63] != 'r'){

            pos[67] = 'n';

        }
        if(pos[56] != 'r') pos[68] = 'n';

        if(Math.abs((from % 8) - (to % 8)) > 1 && (pos[from] == 'k' || pos[from] == 'K')){

            if(to == 2 && pos[from] == 'K'){

                pos[0] = ' ';
                pos[2] = 'K';
                pos[3] = 'R';
                pos[4] = ' ';
                pos[66] = 'n';
                pos[65] = 'n';

            }
            if(to == 6 && pos[from] == 'K'){

                pos[7] = ' ';
                pos[6] = 'K';
                pos[5] = 'R';
                pos[4] = ' ';
                pos[66] = 'n';
                pos[65] = 'n';

            }

            if(to == 58 && pos[from] == 'k'){

                pos[56] = ' ';
                pos[58] = 'k';
                pos[59] = 'r';
                pos[60] = ' ';
                pos[67] = 'n';
                pos[68] = 'n';

            }
            if(to == 62 && pos[from] == 'k'){

                pos[63] = ' ';
                pos[62] = 'k';
                pos[61] = 'r';
                pos[60] = ' ';
                pos[67] = 'n';
                pos[68] = 'n';

            }


        }
        else {

            pos[to] = pos[from];
            pos[from] = ' ';

        }
        if(reset){

            pos[69] = 'n';
            pos[70] = 'n';

        }
        if(pos[64] == 'w') pos[64] = 'b';
        else pos[64] = 'w';

        if(pos[to] == 'p' && to/8 == 0) {

            if(promotion == 0) pos[to] = 'q';
            if(promotion == 1) pos[to] = 'r';
            if(promotion == 2) pos[to] = 'n';
            if(promotion == 3) pos[to] = 'b';
        }

         if(pos[to] == 'P' && to/8 == 7){

             if(promotion == 0) pos[to] = 'Q';
             if(promotion == 1) pos[to] = 'R';
             if(promotion == 2) pos[to] = 'N';
             if(promotion == 3) pos[to] = 'B';
         }


        String newPos = ArrToFen(pos);

        if(!check(newPos)) return newPos;

        else return "Error";

    }
     boolean check(String fen){

        Integer[] Ms = PosMoves(fen);

        char[] pos = FenToArr(fen);

        if(pos[64] == 'w'){

            for(int i = 1; i < Ms.length; i += 2){

                if(pos[Ms[i]] == 'k') return true;

            }

        }
        if(pos[64] == 'b'){

            for(int i = 1; i < Ms.length; i += 2){

                if(pos[Ms[i]] == 'K') return true;

            }

        }

        return false;

    }
     boolean mate(String fen){

        Integer[] Moves = PosMoves(fen);

        for(int i = 0; i < Moves.length; i+=2){

            if(Moves[i] == null || Moves[i+1] == null) continue;
            if(!PlayMove(Moves[i], Moves[i+1], fen, 0).equals("Error")) return false;

        }


        return true;

    }



     //AI stuff
     Integer[] PosMovesOrdered(String Fen){

         ArrayList<Integer> Moves = new ArrayList<>();
         ArrayList<Integer> Captures = new ArrayList<>();
         ArrayList<Integer> Checks = new ArrayList<>();

         char[] tempPos = FenToArr(Fen);

         boolean White = false;

         if(Fen.charAt(0) == 'w') White = true;


         int index = 0;

         for (int i = 7; i < Fen.length(); i++) {


             if (Fen.charAt(i) == '/') index += 8;
             else if (Fen.charAt(i) == '1') index += 1;
             else if (Fen.charAt(i) == '2') index += 2;
             else if (Fen.charAt(i) == '3') index += 3;
             else if (Fen.charAt(i) == '4') index += 4;
             else if (Fen.charAt(i) == '5') index += 5;
             else if (Fen.charAt(i) == '6') index += 6;
             else if(Fen.charAt(i) == '7') index += 7;
             else{
                 if((White && Character.isUpperCase(Fen.charAt(i))) || (!White && !Character.isUpperCase(Fen.charAt(i)))){

                     Integer[] move = GetPieceMoves(index, tempPos);


                     for(int a = 0; a < move.length; a+=2){

                         String temp = PlayMove(move[a], move[a+1], Fen, 0);
                         if(temp.equals("Error")) continue;

                         if(Character.isUpperCase(tempPos[move[a+1]]) != Character.isUpperCase(tempPos[move[a]]) && tempPos[move[a+1]] != ' '){
                             Captures.add(move[a]);
                             Captures.add(move[a+1]);
                         }
                         else{

                             if(temp.charAt(0) == 'b') temp = 'w' + temp.substring(1);
                             else temp = 'b' + temp.substring(1);

                             if(check(temp)) {

                                Checks.add(move[a]);
                                Checks.add(move[a+1]);

                             }
                             else{

                                 Moves.add(move[a]);
                                 Moves.add(move[a+1]);


                             }


                         }

                     }


                 }
                 index++;

             }


         }


         Integer[] moves = new Integer[Moves.size() + Checks.size() + Captures.size()];
         int a = 0;
         for(int i = 0; i < Captures.size(); i++){

             moves[a] = Captures.get(i);
             a++;

         }
         for(int i = 0; i < Checks.size(); i++){

             moves[a] = Checks.get(i);
             a++;

         }
         for(int i = 0; i < Moves.size(); i++){

             moves[a] = Moves.get(i);
             a++;

         }

         return moves;



     }

}