/*

Ari Porat

sem 1 2022-2023 school year

this contains different methods for calculating the best move from a given position

 */


import java.util.HashMap;
public class Engine {
    HashMap<String, Double> evals = new HashMap<>();
    ChessBackEnd game = new ChessBackEnd();
    int maxD = 4;
    Integer[] bestMove = {-1,-1};
    double BestEval = 0;


    double eval(String fen){

        if(evals.containsKey(fen)) return evals.get(fen);

        if(game.mate(fen)){

            if(fen.charAt(0) == 'w') {

                evals.put(fen, -100000.0);
                return -100000;
            }
            else{
                evals.put(fen, 100000.0);
                return 100000;
            }

        }

        double eval = 0;

        int Windex = 0, Bindex = 0;

        char t = fen.charAt(0);
        fen = 'w' + fen.substring(1);
        eval += game.PosMoves(fen).length * 0.003;
        fen = 'b' + fen.substring(1);
        eval -= game.PosMoves(fen).length * 0.003;
        fen = t + fen.substring(1);

        if(fen.charAt(0) == 'w') {

            eval += 0.1;

        }
        else {
            eval -= 0.1;
        }

        int index = 0;
        for(int i = 7; i < fen.length(); i++){

            if (fen.charAt(i) == '/') index += 8;
            else if (fen.charAt(i) == '1') index += 1;
            else if (fen.charAt(i) == '2') index += 2;
            else if (fen.charAt(i) == '3') index += 3;
            else if (fen.charAt(i) == '4') index += 4;
            else if (fen.charAt(i) == '5') index += 5;
            else if (fen.charAt(i) == '6') index += 6;
            else if(fen.charAt(i) == '7') index += 7;
            else {

                if(fen.charAt(i) == 'k') Bindex = i;
                else if(fen.charAt(i) == 'K') Windex = i;
                int row = index/8;
                int col = index%8;
                if (fen.charAt(i) == 'p'){


                    eval -= 1;
                    eval -= 0.1 * (7-row);
                }
                if (fen.charAt(i) == 'q') eval -= 9;
                if (fen.charAt(i) == 'r') eval -= 5;
                if (fen.charAt(i) == 'n') eval -= 3;
                if (fen.charAt(i) == 'b') eval -= 3;

                if (fen.charAt(i) == 'P') {

                    eval += 0.1 * row;
                    eval += 1;
                }
                if (fen.charAt(i) == 'Q') eval += 9;
                if (fen.charAt(i) == 'R') eval += 5;
                if (fen.charAt(i) == 'N') eval += 3;
                if (fen.charAt(i) == 'B') eval += 3;
            }
            index++;

        }


        if(eval > 0){

            if(Bindex/8 == 0 || Bindex/8 == 7) eval += 0.015;
            else if(Bindex%8 == 0 || Bindex%8 == 7) eval += 0.015;
            else if(Bindex/8 == 1 || Bindex/8 == 6) eval += 0.01;
            else if(Bindex%8 == 1 || Bindex%8 == 6) eval += 0.01;
            eval += (14 - (Math.abs(Bindex/8 - Windex/8) + Math.abs(Bindex%8 - Windex%8))) / 25;

        }
        else{

            if(Windex/8 == 0 || Windex/8 == 7) eval -= 0.15;
            else if(Windex%8 == 0 || Windex%8 == 7) eval -= 0.15;
            else if(Windex/8 == 1 || Windex/8 == 6) eval -= 0.1;
            else if(Windex%8 == 1 || Windex%8 == 6) eval -= 0.1;
            eval -= (14 - (Math.abs(Bindex/8 - Windex/8) + Math.abs(Bindex%8 - Windex%8))) / 14;

        }

        eval += Math.random()/100;
        evals.put(fen, eval);
        return eval;

    }
    int newPositions = 0;
    int positionsSearched = 0;



    //ENGINES
    double Min(int Depth, String fen){

        if(Depth == 0){
            positionsSearched++;
            return eval(fen);
        }

        double BestEval = 100000;

        Integer[] moves = game.PosMoves(fen);

        for(int i = 0; i < moves.length; i += 2){

            String temp = game.PlayMove(moves[i], moves[i+1], fen,0);
            if(temp.equals("Error")) continue;
            double eval = Max(Depth - 1, temp);
            if(eval < BestEval){
                BestEval = eval;
                if(Depth == maxD) {

                    bestMove[0] = moves[i];
                    bestMove[1] = moves[i+1];

                }
            }


        }

        positionsSearched++;
        return BestEval;

    }
    double Max(int Depth, String fen){

        if(Depth == 0) {

            positionsSearched++;

            return eval(fen);
        }

        double BestEval = - 100000;

        Integer[] moves = game.PosMoves(fen);

        for(int i = 0; i < moves.length; i += 2){

            String temp = game.PlayMove(moves[i], moves[i+1], fen,0);
            if(temp.equals("Error")) continue;
            double eval = Min(Depth - 1, temp);
            if(eval > BestEval){
                BestEval = eval;
                if(Depth == maxD) {

                    bestMove[0] = moves[i];
                    bestMove[1] = moves[i+1];

                }
            }


        }
        positionsSearched++;

        return BestEval;

    }
    double MiniMax(){

        if(game.fen.charAt(0) == 'w') return Max(maxD, game.fen);
        else return Min(maxD, game.fen);

    }



    double alphabeta(String fen, double alpha, double beta, int depth){

        if(depth == 0) {
            positionsSearched++;
            return eval(fen);
        }

        if(fen.charAt(0) == 'w'){

            double BestEval = - 100000;

            Integer[] moves = game.PosMoves(fen);

            for(int i = 0; i < moves.length; i += 2){

                String temp = game.PlayMove(moves[i], moves[i+1], fen,0);
                if(temp.equals("Error")) continue;
                double eval = alphabeta(temp, alpha, beta, depth - 1);
                if(eval > BestEval){
                    BestEval = eval;
                    if(depth == maxD) {

                        bestMove[0] = moves[i];
                        bestMove[1] = moves[i+1];

                    }
                }

                if(BestEval >= beta) {

                    break;
                }
                alpha = Math.max(alpha, BestEval);


            }
            positionsSearched++;

            return BestEval;

        }
        else{

            double BestEval = 100000;

            Integer[] moves = game.PosMoves(fen);

            for(int i = 0; i < moves.length; i += 2){



                String temp = game.PlayMove(moves[i], moves[i+1], fen,0);
                if(temp.equals("Error")) continue;
                double eval = alphabeta(temp, alpha, beta, depth - 1);
                if(eval < BestEval){
                    BestEval = eval;
                    if(depth == maxD) {

                        bestMove[0] = moves[i];
                        bestMove[1] = moves[i+1];

                    }
                }

                if(BestEval <= alpha){
                    break;
                }
                beta = Math.min(beta, BestEval);


            }
            positionsSearched++;

            return BestEval;


        }

    }
    double Sortedalphabeta(String fen, double alpha, double beta, int depth){

        if(depth == 0) {
            positionsSearched++;
            return eval(fen);
        }

        if(fen.charAt(0) == 'w'){

            double BestEval = - 100000;

            Integer[] moves = game.PosMovesOrdered(fen);

            for(int i = 0; i < moves.length; i += 2){

                if(bestMove[0] == -1){

                    bestMove[0] = moves[i];

                    bestMove[1] = moves[i+1];

                }

                String temp = game.PlayMove(moves[i], moves[i+1], fen,0);
                if(temp.equals("Error")) continue;
                double eval = Sortedalphabeta(temp, alpha, beta, depth - 1);
                if(eval > BestEval){
                    BestEval = eval;
                    if(depth == maxD) {

                        bestMove[0] = moves[i];
                        bestMove[1] = moves[i+1];

                    }
                }

                if(BestEval >= beta) {

                    break;
                }
                alpha = Math.max(alpha, BestEval);


            }
            positionsSearched++;

            return BestEval;

        }
        else{

            double BestEval = 100000;

            Integer[] moves = game.PosMovesOrdered(fen);

            for(int i = 0; i < moves.length; i += 2){

                if(bestMove[0] == -1){

                    bestMove[0] = moves[i];

                    bestMove[1] = moves[i+1];

                }
                String temp = game.PlayMove(moves[i], moves[i+1], fen,0);
                if(temp.equals("Error")) continue;
                double eval = Sortedalphabeta(temp, alpha, beta, depth - 1);
                if(eval < BestEval){
                    BestEval = eval;
                    if(depth == maxD) {

                        bestMove[0] = moves[i];
                        bestMove[1] = moves[i+1];

                    }
                }

                if(BestEval <= alpha){
                    break;
                }
                beta = Math.min(beta, BestEval);


            }
            positionsSearched++;

            return BestEval;


        }

    }



}