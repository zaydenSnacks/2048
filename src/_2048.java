import java.util.ArrayList;

public class _2048
{
	private final int rows = 4;
	private final int cols = 4;
	private int[][] board;
	private int[][] previousBoard;
	private int score;
	private int previousScore;
	
	/**
	 * Initializes board and previousBoard using rows and cols.
	 * Uses the generateTile method to add two random tiles to board.
	 */
	public _2048()
	{
		board = new int[rows][cols];
		generateTile();
		generateTile();
		previousBoard = new int[rows][cols];

	}
	
	/**
	 * Initializes the board of this object using the specified board.
	 * Initializes previousBoard using rows and cols.
	 * 
	 * Precondition: the specified board is a 4x4 2D Array.
	 * 
	 * @param board
	 */
	public _2048(int[][] board)
	{
		this.board = board;
		previousBoard = new int[rows][cols];
	}
	
	/**
	 * Generates a tile and add it to an empty spot on the board.
	 * 80% chance to generate a 2
	 * 20% chance to generate a 4
	 * 
	 * Does nothing if the board is full.
	 */
	private void generateTile()
	{

		if(!full())									// generated tile if board is not full
		{
			int row = (int) (Math.random() * 4);
			int col = (int) (Math.random() * 4);

			while (board[row][col] != 0)
			{
				row = (int) (Math.random() * 4); // loops through board until it finds a space that is empty
				col = (int) (Math.random() * 4);


			}

			int random = (int) (Math.random() * 100);

			if (random < 80)
				board[row][col] = 2;
			else
				board[row][col] = 4;
		}

	}
	
	/**
	 * Returns false if the board contains a 0, true otherwise.
	 * @return
	 */
	private boolean full()
	{
		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[0].length; c++)
				if(board[r][c] == 0)						// determines if the board is full by checking for zeros in all the spaces
					return false;

				return true;

	}
	
	/**
	 * Returns the board.
	 * @return
	 */
	public int[][] getBoard()
	{
		return board;
	}
	
	/**
	 * Returns the score.
	 * @return
	 */
	public int getScore()
	{

		return score;
	}
	
	/**
	 * Saves board into previousBoard and score into previousScore
	 * then performs a move based on the specified direction:
	 * 
	 * Valid directions (not case sensitive):
	 *  up
	 *  down
	 *  left
	 *  right
	 *  
	 * Adds a new tile to the board using the generateTile method.
	 * 
	 * @param direction
	 */
	public void move(String direction)
	{
		for (int c = 0; c < board.length; c++) 		//sets previous board equal to board cant do this previousBoard = board;
			for (int r = 0; r < board.length; r++)
				previousBoard[r][c] = board[r][c];

		previousScore = score;
		if (direction.equals("up"))
			moveUp();
		else if(direction.equals("down"))
			moveDown();
		else if(direction.equals("u"))
			undo();
		else if (direction.equals("left"))
			moveLeft();
		else
			moveRight();

		generateTile();
		System.out.println(score);
	}
	
	/**
	 * Shifts all the tiles up, combines like tiles that collide.
	 */
	private void moveUp()
	{
		int temp;

		for (int c = 0; c < board.length; c++) 		// loops through column major order and adds elements that are equal
		{
			ArrayList<Integer> cols = new ArrayList<>();
			for (int r = 0; r < board.length; r++)
				if(board[r][c] != 0)
					cols.add(board[r][c]);

			for (int i = 0; i < cols.size() - 1; i++)
			{
				if(cols.get(i).equals(cols.get(i + 1)))
				{
					temp = cols.get(i);
					cols.set(i, cols.get(i + 1) + cols.get(i));
					score += cols.remove(i + 1) + temp;		// sets score whenever to elements are added together
				}
			}

			while(cols.size() != 4)
				cols.add(0);				// adds the zeros back to the front of the array

			for (int i = 0; i < cols.size(); i++)
				board[i][c] = cols.get(i);
		}
	}
	
	/**
	 * Shifts all the tiles down, combines like tiles that collide.
	 */
	private void moveDown()
	{
		int temp;
		for (int c = 0; c < board.length; c++)
		{
			ArrayList<Integer> cols = new ArrayList<>();
			for (int r = 0; r < board.length; r++)
				if(board[r][c] != 0)
					cols.add(board[r][c]);

			for (int i = 0; i < cols.size() - 1; i++)
			{
				if(cols.get(i).equals(cols.get(i + 1)))
				{
					temp = cols.get(i);
					cols.set(i, cols.get(i + 1) + cols.get(i));
					score += cols.remove(i + 1) + temp;
				}
			}

			while(cols.size() != 4)
				cols.add(0, 0);

			for (int i = 0; i < cols.size(); i++)
				board[i][c] = cols.get(i);
		}
	}
	
	/**
	 * Shifts all the tiles left, combines like tiles that collide.
	 */
	private void moveLeft()
	{

		int temp;
		for (int r = 0; r < board.length; r++)				// loops through row major order
		{
			ArrayList<Integer> cols = new ArrayList<>();

			for (int c = 0; c < board[r].length ; c++)
			{
				if(board[r][c] != 0)
					cols.add(board[r][c]);
				board[r][c] = 0;
			}

			for (int i = 0; i < cols.size() - 1; i++)
			{
				if(cols.get(i).equals(cols.get(i + 1)))
				{
					temp = cols.get(i);
					cols.set(i, cols.get(i) + cols.get(i + 1));
					score += cols.remove(i + 1) + temp;
				}
			}

			for (int i = 0; i < cols.size(); i++)
			{
				board[r][i] = cols.get(i);
			}
		}
	}
	
	/**
	 * Shifts all the tiles right, combines like tiles that collide.
	 */
	private void moveRight()
	{
		int temp;
		for (int r = 0; r < board.length; r++)
		{
			ArrayList<Integer> cols = new ArrayList<>();
			for (int c = 0; c < board[r].length; c++)
				if(board[r][c] != 0)
					cols.add(board[r][c]);

			for (int i = 0; i < cols.size() - 1; i++)
			{
				if(cols.get(i).equals(cols.get(i + 1)))
				{
					temp = cols.get(i);
					cols.set(i, cols.get(i + 1) + cols.get(i));
					score += cols.remove(i + 1) + temp;
				}
			}

			while(cols.size() != 4)
				cols.add(0, 0);

			for (int i = 0; i < cols.size(); i++)
				board[r][i] = cols.get(i);
		}
	}
	
	/**
	 * Sets board to previousBoard and score to previousScore
	 */
	public void undo()
	{
		for (int c = 0; c < previousBoard.length; c++)			// sets board equal previousBoard
			for (int r = 0; r < previousBoard.length; r++)
				board[r][c] = previousBoard[r][c];

		score = previousScore;
	}
	
	/**
	 * Returns true if the game is over, false otherwise.
	 * @return
	 */
	public boolean gameOver()
	{

		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[0].length - 1; c++)
				if(board[r][c] == board[r][c + 1])
					return false;
																//loops through the board array column major and row major order to check if anything
																// is equal to another element
		for (int c = 0; c < board.length; c++)
			for (int r = 0; r < board.length - 1; r++)
				if(board[r][c] == board[r + 1][c])
					return false;

		if(full())
			return true;

		return false;
	}
	
	/**
	 * Returns a String representation of this object.
	 */
	public String toString()
	{
		String rtn = "";
		
		for(int[] row : board)
		{
			rtn += "|";
			for(int num : row)
				if(num != 0)
				{
					String str = num + "";
					while(str.length() < 4)
						str = " " + str;
					rtn += str;
					
				}
				else
					rtn += "    ";
			rtn += "|\n";
		}
		
		rtn += "Score: " + score + "\n";
		
		return rtn;
	}
}
