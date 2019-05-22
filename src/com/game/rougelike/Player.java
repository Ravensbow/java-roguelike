package com.game.rougelike;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;
import com.game.engine.audio.SoundClip;
import com.game.engine.gfx.Image;
import com.game.engine.gfx.Light;
import com.game.engine.motion.Animation;
import com.game.engine.motion.Frame;
import com.game.rougelike.Items.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends GameObject {


    //

    //Chodzenie i Animacja
    boolean isWalking= false;
    ArrayList<Frame> animation = new ArrayList<>();
    Animation anim;

    //Wygla
    Image imPlayer;

    //Items
    public static int RightHandX=15;
    public static int RightHandY=5;

    public int lift;
    public ArrayList<AbstractItem> equipment= new ArrayList<>();
    public Wepon Wepon;

    SoundClip scTup = new SoundClip("/audio/tup.wav");


    public Player(int posX,int posY)
    {
        imPlayer= new Image("/player.png");
        imPlayer.setLightBlock(Light.NONE);
        this.tag="player";
        this.posX=posX*16;
        this.posY=posY*16;
        height=16;
        width=16;
        animation.add(new Frame(16,2,70));
        animation.add(new Frame(16,-2,70));
        animation.add(new Frame(16,-1,70));
        animation.add(new Frame(16,1,70));
        ArrayList<Frame> aaa = new ArrayList<Frame>();
        aaa.add(new Frame(16,2,70));
        aaa.add(new Frame(16,1,70));
        aaa.add(new Frame(16,-2,70));
        aaa.add(new Frame(16,-1,70));
        anim = new Animation(aaa);

    }


    @Override
    public void update(GameContainer gc, float dt) {

        controll(gc,dt);
        anim.proceed(this,dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        //r.drawRect((int)posX,(int)posY,width,height,0xff00ff00,false);
        r.drawImage(imPlayer,(int)posX,(int)posY);


    }

    private void controll(GameContainer gc, float dt)
    {
        if(gc.getInput().isKeyDown(KeyEvent.VK_D)&&!isWalking)
        {
            isWalking=true;
            animation.get(0).Start();
            scTup.play();
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_A)&&!isWalking)
        {
            isWalking=true;
            animation.get(1).Start();
            scTup.play();
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_S)&&!isWalking)
        {
            isWalking=true;
            animation.get(2).Start();
            scTup.play();
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_W)&&!isWalking)
        {
            isWalking=true;
            animation.get(3).Start();
            scTup.play();
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_P)&&!isWalking&&!anim.isRunning())
        {
            anim.start();
        }
        if(isWalking)
        {
            for (Frame f:animation) {
                f.Do(this,dt);
            }
            if(animation.stream().anyMatch( frame ->  frame.done)) {
                for (Frame f:animation) {
                    f.done=false;
                }
                isWalking = false;
            }
        }


    }


}
