import java.util.*;

public class BombSquare extends GameSquare {
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;
	private boolean selected = false;

	public BombSquare(int x, int y, GameBoard board) {
		super(x, y, "images/blank.png", board);

		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
	}

	public int getXLocation() {
		return (xLocation);
	}

	public int getYLocation() {
		return (yLocation);
	}

	public boolean getSelected() {
		return selected;
	}

	public boolean getThisSquareHasBomb() {
		return (this.thisSquareHasBomb);
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int bombCounter(BombSquare bombSquare) {
		int bombCount = 0;
		ArrayList<BombSquare> neighbourBombSquares = neighbourChecker(bombSquare);
		if (!bombSquare.getThisSquareHasBomb()) {
			bombSquare.setSelected(true);

			for (BombSquare square : neighbourBombSquares) {
				if (square.getThisSquareHasBomb())
					bombCount++;
			}
			bombSquare.setImage("images/" + bombCount + ".png");
		}
		return bombCount;
	}

	public void clearNextNeighbour(ArrayList<BombSquare> neighbours) {
		if (neighbours.size() == 0)
			return;
		ArrayList<BombSquare> nextNeighbourBombSquares = new ArrayList<BombSquare>();
		for (BombSquare neighbour : neighbours) {
			int bombs = bombCounter(neighbour);
			if (bombs == 0) {
				ArrayList<BombSquare> neighbouringBombSquares = neighbourChecker(neighbour);
				for (BombSquare neighbouringSquare : neighbouringBombSquares)
					if (!nextNeighbourBombSquares.contains(neighbouringSquare) && neighbouringSquare != null
							&& !neighbouringSquare.getSelected()) {
						nextNeighbourBombSquares.add(neighbouringSquare);
					}

			}
		}
		clearNextNeighbour(nextNeighbourBombSquares);
	}

	public ArrayList<BombSquare> neighbourChecker(BombSquare bombSquare) {

		ArrayList<BombSquare> neighbourBombSquares = new ArrayList<BombSquare>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				GameSquare square = this.board.getSquareAt(bombSquare.getXLocation() + j,
						bombSquare.getYLocation() + i);
				if (square != null) {
					neighbourBombSquares.add((BombSquare) square);
				}
			}
		}
		return (neighbourBombSquares);

	}

	public void clicked() {
		setSelected(true);
		if (thisSquareHasBomb) {
			setImage("images/bomb.png");
			return;
		}
		int bombCount = bombCounter(this);
		if (bombCount == 0)
			clearNextNeighbour(neighbourChecker(this));

	}
}
