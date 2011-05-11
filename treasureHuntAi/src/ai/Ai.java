
package ai;

import game.Api;
import game.TreasureHuntAi;

public class Ai implements TreasureHuntAi {
	private int w;
	private int h;
	private double map[][];
	private int x;
	private int y;
	private int lastMove;
	private boolean coordFound;
	
	@Override
	public void init() {
		int[] size = Api.getMapSize();
		w = size[0];
		h = size[1];
		map = new double[w][h];
		
		int[] position = Api.getPosition();
		x = position[0];
		y = position[1];
		
		initMap();
	}
	
	@Override
	public void stop() {
		// Do nothing
	}
	
	@Override
	public void reInit() {
		initMap();
	}
	
	@Override
	public void play() {
		if (map[x][y] < 0) {
			map[x][y] = Api.peek();
		} else {
			if (!coordFound) {
				coordFound = findX();
				
				if (coordFound) {
					lastMove = -1;
				}
			} else {
				findY();
			}
		}
	}
	
	private void initMap() {
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				map[i][j] = -1;
			}
		}
		
		lastMove = -1;
		coordFound = false;
	}
	
	private boolean findX() {
		boolean xFound = false;
		
		// First move
		if (lastMove == -1) {
			if (x > w / 2) {
				moveLeft();
			} else {
				moveRight();
			}
		}
		// Moving left
		else if (lastMove == Api.LEFT) {
			// Closer
			if (map[x][y] <= map[x + 1][y]) {
				if (x > 0) {
					moveLeft();
				} else {
					xFound = true;
				}
			}
			// Farther
			else {
				// Wrong way
				if (x < w - 1 || map[x + 2][y] == -1) {
					moveRight();
				}
				// Found x coordinate
				else {
					xFound = true;
					
					if (map[x + 1][y] < map[x][y]) {
						moveRight();
					}
				}
			}
		}
		// Moving right
		else {
			// Closer
			if (map[x][y] <= map[x - 1][y]) {
				if (x < w - 1) {
					moveRight();
				} else {
					xFound = true;
				}
			}
			// Farther
			else {
				// Wrong way
				if (x < 2 || map[x - 2][y] == -1) {
					moveLeft();
				}
				// Found x coordinate
				else {
					xFound = true;
					
					if (map[x - 1][y] < map[x][y]) {
						moveLeft();
					}
				}
			}
		}
		
		return xFound;
	}
	
	private boolean findY() {
		boolean yFound = false;
		
		// First move
		if (lastMove == -1) {
			if (y > h / 2) {
				moveDown();
			} else {
				moveUp();
			}
		}
		// Moving down
		else if (lastMove == Api.DOWN) {
			// Closer
			if (map[x][y] < map[x][y + 1]) {
				if (y > 0) {
					moveDown();
				} else {
					yFound = true;
				}
			}
			// Farther
			else {
				// Wrong way
				if (y < h - 1 || map[x][y + 2] == -1) {
					moveUp();
				}
				// Found y coordinate
				else {
					yFound = true;
					
					if (map[x][y + 1] < map[x][y]) {
						moveUp();
					}
				}
			}
		}
		// Moving up
		else {
			// Closer
			if (map[x][y] < map[x][y - 1]) {
				if (y < h - 1) {
					moveUp();
				} else {
					yFound = true;
				}
			}
			// Farther
			else {
				// Wrong way
				if (y < 2 || map[x][y - 2] == -1) {
					moveDown();
				}
				// Found y coordinate
				else {
					yFound = true;
					
					if (map[x][y - 1] < map[x][y]) {
						moveDown();
					}
				}
			}
		}
		
		return yFound;
	}
	
	private void moveLeft() {
		Api.move(Api.LEFT);
		lastMove = Api.LEFT;
		x--;
	}
	
	private void moveRight() {
		Api.move(Api.RIGHT);
		lastMove = Api.RIGHT;
		x++;
	}
	
	private void moveUp() {
		Api.move(Api.UP);
		lastMove = Api.UP;
		y++;
	}
	
	private void moveDown() {
		Api.move(Api.DOWN);
		lastMove = Api.DOWN;
		y--;
	}
}
