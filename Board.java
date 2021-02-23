package Battleship;
import java.util.Scanner;
import java.lang.Math;

class Board {

	int[][] board = new int[10][10];
	int[][] carrier = new int[5][2];
	int[][] battleship = new int[4][2]; 
	int[][] destroyer = new int[3][2];
	int[][] submarine = new int[3][2];
	int[][] patrolBoat = new int[2][2]; 

	Scanner sc = new Scanner(System.in);

	public void clearBoard() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				board[x][y] = 0;
			
			}
		}
	}

	public static boolean isOutOfBounds(int space) { //playable area is 10x10
		if (space > 9 || space < 0) {
			return true;
		}
		return false;
	}

	public boolean isSettingCollision(int Sx, int Sy, int Ex, int Ey) {
		int x = 0; 
		int y = 0; 
                if (Sx == Ex) { //if ship is horizontal
                        int xCord = Sx;
                        for (int yCord = Math.min(Sy,Ey); yCord < Math.max(Sy,Ey); yCord++) {
				if (board[xCord][yCord] != 0) {
					return true;
				}
				y++;
				if (board[xCord][yCord] != 0) {
					return true;
				}
				x++;
			}
                }       
                else { //if ship is vertical
                        int yCord = Sy;
                        for (int xCord = Math.min(Sx,Ex); xCord < Math.max(Sx,Sy); xCord++) {
                                if (board[xCord][yCord] != 0 ) {
					return true;
				}
				y++;
				if (board[xCord][yCord] != 0) {
					return true;
				}
				x++;
			}
                }
		return false;
	}



	public void setShip(int Sx, int Sy, int Ex, int Ey, String ship) { //Populates coordinates that a specific ship will be placed
		int x = 0;
		int y = 0;
		System.out.println(ship);	
		if (Sx == Ex) { //If ship is horizontally alligned
			int xCord = Sx;
			for (int yCord = Math.min(Sy,Ey); yCord <= Math.max(Sy,Ey); yCord++) {
				y = 0;
				getShip(ship)[x][y] = xCord;
				y++;
				getShip(ship)[x][y] = yCord;
				x++;
			}
		}	
		else { //If ship is vertically alligned
			int yCord = Sy;
			for (int xCord = Math.min(Sx,Ex); xCord <= Math.max(Sx,Sy); xCord++) {
				y = 0;
				getShip(ship)[x][y] = xCord;
				y++;
				getShip(ship)[x][y] = yCord;
				x++;
			}
		}
	}
	

	public void placeShips() {
		//1 == carrier
		//2 == battleship
		//3 == destroyer
		//4 == submarine
		//5 == patrol boat
	
		//carrier
		for (int x = 0; x < 5; x++) {
			board[carrier[x][0]][carrier[x][1]] = 1;
		}
		//battleship
		for (int x = 0; x < 4; x++) {
                        board[battleship[x][0]][battleship[x][1]] = 2;
                }
		//destroyer
		for (int x = 0; x < 3; x++) {
                        board[destroyer[x][0]][destroyer[x][1]] = 3;
                }
		//submarine
		for (int x = 0; x < 3; x++) {
                        board[submarine[x][0]][submarine[x][1]] = 4;
                }
		//patrol boat
		for (int x = 0; x < 2; x++) {
                        board[patrolBoat[x][0]][patrolBoat[x][1]] = 5;
                }
	}

	private int[][] getShip(String ship) {
		if (ship == "carrier") {
			return carrier;
		}
		else if (ship == "battleship") {
			return battleship;
		}
		else if (ship == "destroyer") {
			return destroyer;
		}
		else if (ship == "submarine") {
			return submarine;
		}
		else if (ship == "patrolboat") {
			return patrolBoat;
		}
		else {
			return null;
		}
	}

	public String getShip(int shipSpaces) {
		if (shipSpaces == 5) {
			return "carrier";
		}
		else if (shipSpaces == 4) {
			return "battleship";
		}
		else if (shipSpaces == 3) {
			return "destroyer";
		}/*
		else if ((shipSpaces == 3) && 3ID == "sub") {
			return "submarine";
		}*/
		else if (shipSpaces == 2) {
			return "patrolboat";
		}
		else {
			return null;
		}
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int x, int y, int value) {
		board[x][y] = value;
	}

	public char[][] toDisplayBoard() { //creates a new column and row at the top and left side for the coordinate keys
		char[][] displayBoard = new char[11][11];
		char boardLetterKey = 'A';
		char boardNumberKey = '1';
		displayBoard[0][0] = ' ';
		for (int x = 1; x < 11; x++) {
			boardNumberKey = '1';
			for (int y = 1; y < 11; y++) {
				displayBoard[x][y] = (char)board[x][y];
				displayBoard[0][y] = boardNumberKey; //redundancy. overwrites elements with same data multiple times
				boardNumberKey++;
				if (displayBoard[x][y] == '0') { //square that are not filled converted to empty char for better board visibility
					displayBoard[x][y] = ' ';
				}
			}
			displayBoard[x][0] = boardLetterKey;
			boardLetterKey++;
		}
		return displayBoard;
	}

	public void printBoard(char[][] board) {
		int y = 0;
		System.out.println("┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐");
		for (int x = 0; x < 11; x++) {
			for (int columns = 0; columns < 11; columns++); {
                                System.out.print("│" + board[x][y] + "│");
                        }
		       	System.out.println("├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤");
			y++;
		}
		System.out.println("└─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘");
	}

	public void printBoard() { //haha, should probably just make "display" boards baseline to avoid this extra hassle. Prints a default display for users to plan where to put their ships
		int counter = 1;
		int y = 0;
		System.out.println("┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐");
		System.out.println("│   │ A │ B │ C │ D │ E │ F │ G │ H │ I │ J │");
                for (int x = 0; x < 10; x++) {
			System.out.println("├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤");
			if (counter < 10) {
				System.out.print("│ " + counter + " │");
			}
			else { 
				System.out.print("│" + counter + " │");
			}
			for (int columns = 0; columns < 10; columns++) {
				System.out.print("   │");  //fills empty spaces after key
			}
			System.out.println("");
                        y++;
			counter++;
                }
                System.out.println("└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘");
	}


}
