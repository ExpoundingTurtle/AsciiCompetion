package Screens;

import Creature.Creature;
import Items.Item;
import World.Tile;
import Vision.*;

public class LookScreen extends TargetBasedScreen {
	
	public LookScreen(Creature player, String caption, int sx, int sy) {
		super(player, caption, sx, sy);
	}

	public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
		Creature creature = player.creature(x,  y,  player.z);
		if (creature != null) {
			caption = creature.symbol + " " + creature.name + creature.details();
			return;
		}
		
		Item item = player.item(x, y, player.z);
		if (item != null) {
			caption = item.glyph() + " " + item.getDisplayName() + item.details();
			return;
		}
		
		Tile tile = player.tile(x, y, player.z);
		caption = tile.getSymbol() + " " + tile.getDisplayName() + tile.details();
	}
	
	public boolean isAcceptable(int x, int y) {
        if (!player.canSee(x, y, player.z))
            return false;
    
        for (Point p : new Line(player.x, player.y, x, y)){
            if (!player.realTile(p.x, p.y, player.z).isGround())
                return false;
        }
    
        return true;
    }
}
