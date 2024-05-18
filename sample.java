
import java.util.Scanner;
public class sample {

    public static void main(String[] args) {
        
        //creating a board or spaces where moves can be placed
        char board[][]= new char[3][3]; 
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                board[i][j]=' ';
            }
        }
        char player='o';
        boolean gameOver=false;
        Scanner sc= new Scanner(System.in);
        printboard(board);


        while(!gameOver) // until game over becomes true that is someone wins
        {
            if(player=='x')
            {

                boolean validMove=false;
                while(!validMove) // chance to be given to the same player
                {
                    System.out.println("Palyer " + player + " move(row,column)");

                    //taking inputs
                    String input=sc.nextLine(); //taken the input
                    String[]inputs=input.split(" "); //splited into like [1,2];
                    int row=Integer.parseInt(inputs[0]);
                    int column=Integer.parseInt(inputs[1]);

                    //checking postion is valid or not

                    if(board[row][column]==' ')
                    {
                        board[row][column]=player;
                        validMove=true;
                    }
                    else{
                        System.out.println("Please try again");
                    }

                }
                //checking if this move leads to wins
                printboard(board);
                gameOver= checkWin(board,player);

                if(gameOver) 
                {
                    System.out.println("Player " +player+ " have won the game");

                }
                else  player = (player == 'x') ? 'o' : 'x';

            }
            else
            {
                aimove(board,sc,player,gameOver);
                printboard(board);
                gameOver= checkWin(board,player);
                if(gameOver) 
                {
                    System.out.println("Player " +player+ " have won the game");
                }
                player='x';
            }
          
            
        }
    }

    //aimove

    public static void aimove(char[][] board, Scanner sc, char player,boolean gameOver) {
    
        int bestScore = Integer.MIN_VALUE;
        String bestMove = "";
    
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimax(board,0,false,player);
    
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i + " " + j; // Store the best move in "row column" format
                    }
                    board[i][j]=' ';
                }
            }
        }
    
        String[] bestMoves = bestMove.split(" ");
        board[Integer.parseInt(bestMoves[0])][Integer.parseInt(bestMoves[1])] = player;
    
    }
   
    public static int minimax(char[][] board,int depth,boolean isMax,char player) {
        int score=0;
        boolean isWin=checkWin(board, player);
        if(isWin) 
        {
            if(player=='x') 
            {score=+1;
            }
            else if(player=='o'){ 
                score=-1;
            }
            else if (isBoardFull(board)){
                score=0; 
            }

            return score;
        } 

        if(isMax)
        {
            int bestscore= Integer.MIN_VALUE;
            for(int i=0;i<board.length;i++)
            {
                for(int j=0;j<board[i].length;j++)
                {
                    if(board[i][j]==' ')
                    {
                        board[i][j]=player;
                        score=minimax(board, depth+1, false, player);// goes to the 
                        board[i][j]=' ';
                        bestscore=Integer.max(score, bestscore);
                    }
                }
            }
            return bestscore;
        }

        else{
            //minimising turn
            
            int bestscore= Integer.MAX_VALUE;
            for(int i=0;i<board.length;i++)
            {
                for(int j=0;j<board[i].length;j++)
                {
                    if(board[i][j]==' ')
                    {
                        board[i][j]=player;
                        score=minimax(board, depth+1, true, player);// goes to the 
                        board[i][j]=' ';
                        bestscore=Integer.min(score, bestscore);
                    }
                }
            }
            return bestscore;
        }
    }
    
    //print the baord
    public static void printboard(char[][]board)
    {
        System.out.println();
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[i].length;j++)
            {
                System.out.print(board[i][j] +" | ");
            }
            System.out.println();
        }
    }


    // to check if wins
    public static boolean checkWin(char[][]board,char player)
    {
        //true when won or tie, false when none of them

        //side 3 arrangement X X X
        for(int row=0;row<board.length;row++)
        if(board[row][0]==player && board[row][1]==player && board[row][2]==player)
        {
            return true;
        }
        //check vertical

        for(int col=0;col<board[0].length;col++)
        if(board[0][col]==player && board[1][col]==player && board[2][col]==player)
        {
            return true;
        }

        if(board[0][0]==player && board[1][1]==player && board[2][2]==player)
        {
            return true;
        }
        if(board[0][2]==player && board[1][1]==player && board[2][0]==player)
        {
            return true;
        }

        if (isBoardFull(board)) {
            System.out.println("It's a tie!");
            return true;
        }
        return false;
    }
        
    public static boolean isBoardFull(char[][]board)
    {  //true= tie , false= still chance
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[i].length;j++){
                if(board[i][j]==' ')
                return false;
            }
        }
        return true;
    }
}
