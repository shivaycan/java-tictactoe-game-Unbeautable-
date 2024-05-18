import java.util.Scanner;

public class a {

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
                playerMove(board, sc, player);
            } else {
                computerMove(board, player);
            }
            printBoard(board);
            gameOver = checkWin(board, player) || isBoardFull(board);
            if (gameOver && !checkWin(board, player)) {
                System.out.println("It's a tie!");
            }
            player = (player == 'x') ? 'o' : 'x';
        }
        sc.close();
    }

    public static void playerMove(char[][] board, Scanner sc, char player) {
        boolean validMove = false;
        while (!validMove) {
            System.out.println("Player " + player + " move (row column): ");
            int row = sc.nextInt();
            int column = sc.nextInt();
            if (row >= 0 && row < 3 && column >= 0 && column < 3 && board[row][column] == ' ') {
                board[row][column] = player;
                validMove = true;
            } else {
                System.out.println("Invalid move. Please try again.");
            }
        }
    }

    public static void computerMove(char[][] board, char player) {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestColumn = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimax(board, 0, false, player);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestColumn = j;
                    }
                }
            }
        }
        board[bestRow][bestColumn] = player;
    }

    public static int minimax(char[][] board, int depth, boolean isMax, char player) {
        if (checkWin(board, (player == 'x') ? 'o' : 'x')) {
            return (isMax) ? -1 : 1;
        }
        if (isBoardFull(board)) {
            return 0;
        }

        int bestScore = (isMax) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = (isMax) ? player : (player == 'x') ? 'o' : 'x';
                    int score = minimax(board, depth + 1, !isMax, player);
                    board[i][j] = ' ';
                    bestScore = (isMax) ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    public static void printBoard(char[][] board) {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
