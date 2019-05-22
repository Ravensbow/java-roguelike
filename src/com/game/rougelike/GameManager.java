package com.game.rougelike;

import com.game.engine.AbstractGame;
import com.game.engine.GameContainer;
import com.game.engine.Renderer;
import com.game.engine.audio.SoundClip;
import com.game.engine.gfx.Image;
import com.game.engine.gfx.ImageTile;
import com.game.engine.gfx.Light;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.spi.LocaleNameProvider;

public class GameManager extends AbstractGame {

    private ArrayList<GameObject> objects = new ArrayList<>();
    private Image bg = new Image("/tlo.png");
    private Image it1 = new Image("/deska.png");
    private Image it2 = new Image("/deska.png");
    private Image it3 = new Image("/deska.png");



    public GameManager()
    {
        objects.add(new Player(2,2));
        it1.setLightBlock(Light.FULL);
        it1.Scale(0.2);
        it3.setLightBlock(Light.FULL);
        it3.Scale(0.2);
        it2.setLightBlock(Light.FULL);
        it2.Scale(0.2);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        for (int i =0;i<objects.size();i++)
        {
            objects.get(i).update(gc,dt);
            if (objects.get(i).dead){
                objects.remove(i);
                i--;
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(bg,0,0);
        r.drawImage(it3,20,20);
        r.drawImage(it2,40,20);
        r.drawImage(it1,60,20);
        for (int i =0;i<objects.size();i++)
        {
            objects.get(i).render(gc,r);
        }
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
