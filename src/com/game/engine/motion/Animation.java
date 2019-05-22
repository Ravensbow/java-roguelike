package com.game.engine.motion;

import com.game.rougelike.GameObject;

import java.util.ArrayList;

public class Animation {

    public ArrayList<Frame> frames = new ArrayList<>();

    private Frame frame;

    private boolean run = false;
    private boolean done = false;

    private int frameIndex=0;

    public Animation(ArrayList<Frame> frames)
    {
        this.frames = frames;
    }

    public void start() {
        run = true;
        done=false;
        frameIndex=0;
        frame = frames.get(frameIndex);
        frame.Start();
    }

    public void proceed(GameObject go,float dt)
    {
        if (run) {
            if (frame.done&&frameIndex+1<frames.size())
            {
                next();
            }
            else if (frame.done&&frameIndex+1>=frames.size())
            {
                end();
            }

            if (run)
            {
                frame.Do(go,dt);
            }
        }
    }


    public void next()
    {
        frame.done=false;
        frameIndex++;
        frame = frames.get(frameIndex);
        frame.Start();
    }

    public void end()
    {
        run=false;
        frameIndex=0;
        frame = frames.get(frameIndex);
        done=true;
    }

    public boolean isRunning(){return run;}
}
