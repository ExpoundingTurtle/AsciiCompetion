package Vision;

import World.*;

public class FieldofView {
	private World world;
	private int getWorldFloors;
	
	private boolean[][] visible;
	public boolean isVisible(int x, int y, int z){
		return z == getWorldFloors && x >= 0 && y >= 0 && x < visible.length && y < visible[0].length && visible[x][y];
	}
	
	private Tile[][][] tiles;
	public Tile tile(int x, int y, int z){
		return tiles[x][y][z];
	}
	
	public FieldofView(World world){
		this.world = world;
		this.visible = new boolean[world.getWorldWidth()][world.getWorldHeight()];
		this.tiles = new Tile[world.getWorldWidth()][world.getWorldHeight()][world.getWorldFloors()];
		
		for (int x = 0; x < world.getWorldWidth(); x++){
			for (int y = 0; y < world.getWorldHeight(); y++){
				for (int z = 0; z < world.getWorldFloors(); z++){
					tiles[x][y][z] = Tile.UNKNOWN;
				}
			}
		}
	}
	
	public void update(int wx, int wy, int wz, int r){
		getWorldFloors = wz;
		visible = new boolean[world.getWorldWidth()][world.getWorldHeight()];
		
		for (int x = -r; x < r; x++){
			for (int y = -r; y < r; y++){
				if (x*x + y*y > r*r)
					continue;
				
				if (wx + x < 0 || wx + x >= world.getWorldWidth() || wy + y < 0 || wy + y >= world.getWorldHeight())
					continue;
				
				for (Point p : new Line(wx, wy, wx + x, wy + y)){
					Tile tile = world.tile(p.x, p.y, wz);
					visible[p.x][p.y] = true;
					tiles[p.x][p.y][wz] = tile;
					
					if (!tile.isGround())
						break;
				}
			}
		}
	}
}
