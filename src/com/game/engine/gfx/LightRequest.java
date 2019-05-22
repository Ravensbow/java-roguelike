package com.game.engine.gfx;

public class LightRequest
{
    public Light light;
    public int locX,locY;

    public LightRequest(Light l ,int locX,int locY)
    {
        this.light=l;
        this.locX=locX;
        this.locY=locY;
    }

}
