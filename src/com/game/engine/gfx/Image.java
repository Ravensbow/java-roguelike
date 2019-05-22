package com.game.engine.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image {

    private int w,h;
    private  int p[];
    private boolean alpha = false;
    private int lightBlock = Light.NONE;

    public Image(String path) {

        BufferedImage image = null;
        try
        {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        w = image.getWidth();
        h = image.getHeight();

        p= image.getRGB(0,0,w,h,null,0, w);

        image.flush();
    }
    public Image(int[] p ,int w , int h)
    {
        this.p = p;
        this.w=w;
        this.h=h;
    }
    public void Scale(double scale)
    {
        int newW = (int)(w*scale);
        int newH = (int)(h*scale);
        int newP [] = new int [newW*newH];

        for(int y=0;y<newH;y++)
        {
            for(int x=0;x<newW;x++)
            {
                int index = x + y*newW;

                double srcX = (((double) x/newW)*w);
                double srcY = (((double) y/newH)*h);

                newP[index]=p[(int)srcX+(int)srcY*w];
            }
        }
        w= newW;
        h=newH;
        p=newP;

    }
    public void Rotate(double angle)
    {

    }



    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int[] getP() {
        return p;
    }

    public void setP(int[] p) {
        this.p = p;
    }

    public boolean isAlpha() {
        return alpha;
    }

    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }

    public int getLightBlock() {
        return lightBlock;
    }

    public void setLightBlock(int lightBlock) {
        this.lightBlock = lightBlock;
    }
}
