package com.game.rougelike.Items;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;
import com.game.rougelike.Player;

public class Wepon extends AbstractItem {


    public Wepon()
    {
        offx = Player.RightHandX;
        offy = Player.RightHandY;
    }

    @Override
    public void render(GameContainer gc, Renderer r)
    {
        super.render(gc,r);

        if(equiped)
        {
            r.drawImage(imOnPlayer,(int)player.posX+offx,(int)player.posY+offy);
        }
    }

    @Override
    public void update(GameContainer gc, float dt)
    {
        super.update(gc,dt);
    }
}
