package com.game.rougelike.Items;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;
import com.game.engine.gfx.Image;
import com.game.engine.gfx.ImageTile;
import com.game.rougelike.GameObject;
import com.game.rougelike.Player;

import java.awt.event.KeyEvent;

public class AbstractItem extends GameObject {

    Player player;
    int offx,offy;
    Image imAvatar;
    Image imOnPlayer;
    boolean equiped=false;
    boolean onGround;

    @Override
    public void update(GameContainer gc, float dt) {
        Take(gc);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        if (onGround) {
            r.drawImage(imAvatar,(int)posX,(int)posY);
        }
    }

    public boolean isTakeablee()
    {
        if(onGround&&(int)player.posY==(int)posY&&(int)player.posX==(int)posX&&player.lift<player.equipment.size()-1)
            return true;
        return false;
    }

    public void Take(GameContainer gc)
    {
        if(isTakeablee())
        {
            if(gc.getInput().isKeyDown(KeyEvent.VK_E))
            {
                onGround=false;
                player.equipment.add(this);
            }
        }
    }
}
