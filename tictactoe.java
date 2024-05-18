import java.util.Scanner;

public class tictactoe {

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        char player = 'x';
        boolean gameOver = false;
        Scanner sc = new Scanner(System.in);
        printBoard(board);

        while (!gameOver) {
            if (player == 'x') {
                boolean validMove = false;
                while (!validMove) {
                    System.out.println("Player " + player + " move (row column): ");
                    String input = sc.nextLine();
                    String[] inputs = input.split(" ");
                    int row = Integer.parseInt(inputs[0]);
                    int column = Integer.parseInt(inputs[1]);
                    if (board[row][column] == ' ') {
                        board[row][column] = player;
                        validMove = true;
                    } else {
                        System.out.println("Invalid move. Please try again.");
                    }
                }
                printBoard(board);
                gameOver = checkWin(board, player) || isBoardFull(board);
                if (checkWin(board, player)) {
                    System.out.println("Player " + player + " wins the game!");
                } else if (isBoardFull(board)) {
                    System.out.println("The game is a draw!");
                    gameOver = true;
                }
                player = (player == 'x') ? 'o' : 'x';
            } else {
                aimove(board, player);
                printBoard(board);
                gameOver = checkWin(board, player) || isBoardFull(board);
                if (checkWin(board, player)) {
                    System.out.println("Player " + player + " wins the game!");
                } else if (isBoardFull(board)) {
                    System.out.println("The game is a draw!");
                    gameOver = true;
                }
                player = 'x';
            }
        }
        sc.close();
    }

    public static void aimove(char[][] board, char player) {
        int bestScore = Integer.MIN_VALUE;
        String bestMove = "";

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimax(board, 0, false); // Change to 'false' because AI is 'o'
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i + " " + j;
                    }
                    board[i][j] = ' ';
                }
            }
        }

        String[] bestMoves = bestMove.split(" ");
        board[Integer.parseInt(bestMoves[0])][Integer.parseInt(bestMoves[1])] = player;
    }

    public static int minimax(char[][] board, int depth, boolean isMax) {
        if (checkWin(board, 'o')) {
            return 10 - depth;
        }
        if (checkWin(board, 'x')) {
            return depth - 10;
        }
        if (isBoardFull(board)) {
            return 0;
        }

        if (isMax) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'o';
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'x';
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public static void printBoard(char[][] board) {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
        }
    }

    public static boolean checkWin(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    public static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
