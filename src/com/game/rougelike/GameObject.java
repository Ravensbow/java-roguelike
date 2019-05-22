package com.game.rougelike;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;

public abstract class GameObject
{
    public float posX,posY;
    protected int width,height;
    protected String tag;

    protected boolean dead=false;

    public abstract void update(GameContainer gc,float dt);

    public abstract void render(GameContainer gc, Renderer r);

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
