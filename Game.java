package Battleship;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;
import java.lang.Character;


class Game {

	private  Board board = new Board();
	private  Board boardTwo = new Board();
	private  Board trackingBoard = new Board();
	char[][] displayBoard;
        char[][] displayTrackingBoard;
	ArrayList CPUTargets = new ArrayList();
	


	Scanner sc = new Scanner(System.in);

	private void gameSetup() {
		board.clearBoard();
		boardTwo.clearBoard();
		trackingBoard.clearBoard();
	}

	private void getShipInput() { //gets coordinates to set ships at in Board.setShips
		char[] startCoordinate = new char[2];
		char[] endCoordinate = new char[2];
		int Sx, Sy, Ex, Ey;
		boolean validInput, validSubInput;
		board.printBoard();
		for (int shipSpaces = 5; shipSpaces > 1; shipSpaces--) {
			validInput = validSubInput = false;
			while (!validInput) {
				System.out.println("Please enter the starting and ending coordinates you'd like to place your " + board.getShip(shipSpaces) + " (" + shipSpaces + " spaces long)");
				startCoordinate = sc.nextLine().toCharArray();
				endCoordinate = sc.nextLine().toCharArray();
				Sy = Character.getNumericValue(startCoordinate[1]);
				Ey = Character.getNumericValue(endCoordinate[1]);
				Sx = letterInterpreter(startCoordinate[0]);
				Ex = letterInterpreter(endCoordinate[0]);
				if ((Math.max(Sx,Ex) - Math.min(Sx,Ex) != shipSpaces) || (Math.max(Sy,Ey) - Math.min(Sy,Ey) != shipSpaces)) { //coordinates must be the size of current ship apart
					if (!board.isOutOfBounds(Sx) && !board.isOutOfBounds(Sy) && !board.isOutOfBounds(Ex) && !board.isOutOfBounds(Ey)) {
						if (!board.isSettingCollision(Sx,Sy,Ex,Ey)) { //coordinates must not overlap another placed boat SOME BUG HERE!!!!!!!!!!!!
							board.setShip(Sx,Sy,Ex,Ey,board.getShip(shipSpaces));
							validInput = true;
						}
					}
				}
				else {
					System.out.println("Coordinates are either out of bounds, overlapping with another ship, or invalid input. Please try again.");
				}
			}
			if (shipSpaces == 3) { //Since two ships share 3 spaces, we need a bit of a detour in the loop if we want to still have the ship coordinates automated sequentially
				validSubInput = false;
                        	while (!validSubInput) {
                                	System.out.println("Please enter the starting and ending coordinates you'd like to place your submarine (3 spaces long)");
                                	startCoordinate = sc.nextLine().toCharArray();
                                	endCoordinate = sc.nextLine().toCharArray();
                                	Sy = Character.getNumericValue(startCoordinate[1]);
                                	Ey = Character.getNumericValue(endCoordinate[1]);
                                	Sx = letterInterpreter(startCoordinate[0]);
                                	Ex = letterInterpreter(endCoordinate[0]);
                                	if ((Math.max(Sx,Ex) - Math.min(Sx,Ex) != 3) || (Math.max(Sy,Ey) - Math.min(Sy,Ey) != 3)) { //coordinates must be the size of current ship apart
                                        	if (!board.isOutOfBounds(Sx) && !board.isOutOfBounds(Sy) && !board.isOutOfBounds(Ex) && !board.isOutOfBounds(Ey)) { //must not be OoB
							if (!board.isSettingCollision(Sx,Sy,Ex,Ey)) { //coordinates must not overlap another placed boat
                                                        	board.setShip(Sx,Sy,Ex,Ey,"submarine");
                                                        	validSubInput = true;
                                                	}
                                        	}
                                	}
                                	else {
                                        	System.out.println("Coordinates are either out of bounds, overlapping with another ship, or invalid input. Please try again.");
                                	}
				}
			}
		}
	}

	private void getCPUShipInput() { //randomly generates placement coordinates for AI ships
		int[] startCoordinate = new int[2];
	        int[] endCoordinate = new int[2];
                int Sx, Sy, Ex, Ey, direction;
                boolean validInput, validSubInput;
                for (int shipSpaces = 5; shipSpaces > 1; shipSpaces--) {
                        validInput = validSubInput = false;
                        while (!validInput) {
				Sx = (int)(Math.random() * 10);
				Sy = (int)(Math.random() * 10);
				direction = (int)(Math.round(Math.random() * 3)); //decides where the direction the ship will face if one end starts on the starting coordinates
				if (direction == 0) { //up
					endCoordinate[0] = Sx;
					endCoordinate[1] = Sy-shipSpaces+1;
				}
				else if (direction == 1) { //down
					endCoordinate[0] = Sx;
					endCoordinate[1] = Sy+shipSpaces-1;
				}
				else if (direction == 2) { //left
					endCoordinate[0] = Sx-shipSpaces+1;
					endCoordinate[1] = Sy;
				}
				else if (direction == 3) { //right
					endCoordinate[0] = Sx+shipSpaces-1;
					endCoordinate[1] = Sy;
				}
                                Ey = endCoordinate[1];
                                Ex = endCoordinate[0];
                                if ((Math.max(Sx,Ex) - Math.min(Sx,Ex) != shipSpaces) || (Math.max(Sy,Ey) - Math.min(Sy,Ey) != shipSpaces)) { //coordinates must be the size of current ship apart
					if (!boardTwo.isOutOfBounds(Sx) && !boardTwo.isOutOfBounds(Sy) && !boardTwo.isOutOfBounds(Ex) && !boardTwo.isOutOfBounds(Ey)) { //must be within 0-9
						if (!boardTwo.isSettingCollision(Sx,Sy,Ex,Ey)) { //coordinates must not overlap another placed boat
                                                        boardTwo.setShip(Sx,Sy,Ex,Ey,boardTwo.getShip(shipSpaces));
                                                        validInput = true;
                                                }
                                        }
                                }
                        }
                        if (shipSpaces == 3) { //Since two ships share 3 spaces, we need a bit of a detour in the loop if we want to still have the ship coordinates automated sequentially
                                validSubInput = false;
                                while (!validSubInput) {
					Sx = (int)(Math.random() * 10);
                                	Sy = (int)(Math.random() * 10);
                                	direction = (int)(Math.round(Math.random() * 3)); //decides where the direction the ship will face if one end starts on the starting coordinates
                                	if (direction == 0) { //up
                                        	endCoordinate[0] = Sx;
						endCoordinate[1] = Sy-shipSpaces+1;
                                	}
                                	else if (direction == 1) { //down
                                        	endCoordinate[0] = Sx;
						endCoordinate[1] = Sy+shipSpaces-1;
                                	}
                                	else if (direction == 2) { //left
                                        	endCoordinate[0] = Sx-shipSpaces+1;
						endCoordinate[1] = Sy;
                                	}
                                	else if (direction == 3) { //right
                                        	endCoordinate[0] = Sx+shipSpaces-1;
						endCoordinate[1] = Sy;
                                	}
                                	Ey = endCoordinate[1];
                                	Ex = endCoordinate[0];
                                	if ((Math.max(Sx,Ex) - Math.min(Sx,Ex) != shipSpaces) || (Math.max(Sy,Ey) - Math.min(Sy,Ey) != shipSpaces)) { //coordinates must be the size of current ship apart
						if (!boardTwo.isOutOfBounds(Sx) && !boardTwo.isOutOfBounds(Sy) && !boardTwo.isOutOfBounds(Ex) && !boardTwo.isOutOfBounds(Ey)) {
							if (!boardTwo.isSettingCollision(Sx,Sy,Ex,Ey)) { //coordinates must not overlap another placed boat
                                                        	boardTwo.setShip(Sx,Sy,Ex,Ey,"submarine");
                                                        	validInput = true;
                                                	}
                                        	}
                                	}
                        	}
                	}
		}
	}

	private int letterInterpreter(char coord) { //Simply converts X axis letter input to numbers for easy interfacing with the array system the boards use
                if (coord == 'A' || coord == 'a') {
                        return 0;
                }
                else if (coord == 'B' || coord == 'b') {
                        return 1;
                }
                else if (coord == 'C' || coord == 'c') {
                        return 2;
                }
                else if (coord == 'D' || coord == 'd') {
                        return 3;
                }
                else if (coord == 'E' || coord == 'e') {
                        return 4;
                }
                else if (coord == 'F' || coord == 'f') {
                        return 5;
                }
                else if (coord == 'G' || coord == 'g') {
                        return 6;
                }
                else if (coord == 'H' || coord == 'h') {
                        return 7;
                }
                else if (coord == 'I' || coord == 'i') {
                        return 8;
                }
                else if (coord == 'J' || coord == 'j') {
                        return 9;
                }
                else {
                        return 10;
                }
        }

	private void populateDisplayBoards() { //wraps the boards with an extra column and row for coordinate keys (A-J, 1-10)
		displayBoard = board.toDisplayBoard();
		displayTrackingBoard = trackingBoard.toDisplayBoard();
	}

	private void playerAction() { //Handles player attempting to attack CPU ships
		char[] input;
		int[] target = new int[2];
		boolean successfulAttempt = false;	
		while(!successfulAttempt) {
			System.out.println("Please enter a coordinate you'd like to fire at");
                	input = sc.nextLine().toCharArray();
                	target[0] = Character.valueOf(input[0]);
                	target[1] = letterInterpreter(input[1]);
			if (!Board.isOutOfBounds(target[0]) && !Board.isOutOfBounds(target[1])) { //make sure coordinates are within the game board area
				if (boardTwo.getBoard()[target[0]][target[1]] != 0 &&
				(displayTrackingBoard[target[0]+1][target[1]+1] != 'X' || displayTrackingBoard[target[0]+1][target[1]+1] != 'O')) { //Does target hit and is not duplicate location
					System.out.println("HIT!");
					displayTrackingBoard[target[0]+1][target[1]+1] = 'X';
					boardTwo.setBoard(target[0],target[1],0);
					successfulAttempt = true;
				}
				else if (boardTwo.getBoard()[target[0]][target[1]] == 0 && (displayTrackingBoard[target[0]+1][target[1]+1] != 'X' || displayTrackingBoard[target[0]+1][target[1]+1] != 'O')) { //miss
					System.out.println("MISS!");
					displayTrackingBoard[target[0]+1][target[1]+1] = 'O';
					successfulAttempt = true;
				}
				else {
					System.out.println("You already fired at these coordinates, try another target");
				}
			}
			else {
				System.out.println("Invalid target coordinates, try again");
			}
		}
	}

	private void CPUAction() { //Handles CPU attempting to attack player ships
		int[] target = new int[2];
		boolean successfulAttempt = false;
                while(!successfulAttempt) {
			target[0] = (int)(Math.random() * 10);
                	target[1] = (int)(Math.random() * 10);
                	if (board.getBoard()[target[0]][target[1]] != 0 && !CPUTargets.contains(Integer.parseInt(target[0] + "" + target[1]))) { //hit and not dupe
                        	System.out.println("The computer fires at " + target[0] + "," + target[1] + " and hits!");
                                displayBoard[target[0]+1][target[1]+1] = 'X';
				CPUTargets.add(Integer.parseInt(target[0] + "" + target[1]));
                                successfulAttempt = true;
                        }
                        else if (board.getBoard()[target[0]][target[1]] == 0 && !CPUTargets.contains(Integer.parseInt(target[0] + "" + target[1]))) { //miss not dupe
                        	System.out.println("The computer fires at " + target[0] + "," + target[1] + " and misses!");
                                displayBoard[target[0]+1][target[1]+1] = 'O';
				CPUTargets.add(Integer.parseInt(target[0] + "" + target[1]));
                                successfulAttempt = true;
                        }
                }
        }

	private boolean gameStateCheck(char[][] board) { //Makes sure no win or lose states have occured
		int xCount = 0;
		for (int x = 1; x < 11; x++) {
			for (int y = 1; y < 11; y++) {
				if (board[x][y] == 'X') {
					xCount++;
				}
			}
		}
		if (xCount == 17) { //all of this board's ships have been sunk
			return true;
		}
		return false;
	}

	public void play() {
		boolean gameOver;
		int win = 0;
		gameOver = false;
		while(!gameOver) {
			gameSetup();
			getShipInput();
			getCPUShipInput();
			board.placeShips();
			boardTwo.placeShips();
			System.out.println("Enemy Ships");
			trackingBoard.printBoard(displayTrackingBoard);
			System.out.println("Your Ships");
			board.printBoard(displayBoard);
			while(win == 0) {
				playerAction();
				CPUAction();
				if (gameStateCheck(displayTrackingBoard)) {
					win = 1;
				}
				if (gameStateCheck(displayBoard)) {
					win = -1;
				}
			}
			if (win == 1) {
				System.out.println("Congratulations! You've sunk all the enemy ships!");
				gameOver = true;
			}
			else if (win == -1) {
				System.out.println("Too bad! The enemy has sunk all your ships!");
				gameOver = true;
			}
		}
		System.out.print("\n\n\nGAME OVER");
	}
}	
