package com.game.rougelike;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;
import com.game.engine.gfx.Light;

public class Lantern extends GameObject {

    public Light light;
    int posX,posY;

    public Lantern(int posX,int posY,int radius,int color)
    {
        light= new Light(radius,color);
        this.posX=posX;
        this.posY=posY;
    }

    @Override
    public void update(GameContainer gc, float dt) {

    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawLight(light,posX,posY);
    }

    public void updatePosition(int posX,int posY)
    {
        this.posY= posX;
        this.posY=posY;
    }
}
