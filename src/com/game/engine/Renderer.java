package com.game.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.game.engine.gfx.*;

public class Renderer
{
    private Font font = Font.STANDARD;
    private ArrayList<ImageRequest> imageRequests = new ArrayList<>();
    private ArrayList<LightRequest> lightRequests = new ArrayList<>();


    boolean processin = false;

    int ambientColor = 0xff0f0f0f;

    private int pW,pH;
    private int[] p;
    private  int[] zb;
    private int [] lm; //Light map
    private int[] lb; //Light block




    private int zDepth = 0;

    public Renderer(GameContainer gc)
    {
        pW = gc.getWidth();
        pH = gc.getHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zb = new int[p.length];
        lm = new int [p.length];
        lb = new int [p.length];
    }

    public void clear()
    {
        for(int i =0;i<p.length;i++)
        {
            p[i] =0;
            zb[i]=0;
            lm[i]=ambientColor;
            lb[i]=0;
        }
    }

    public void process()
    {
        processin=true;

        Collections.sort(imageRequests, new Comparator<ImageRequest>() {
            @Override
            public int compare(ImageRequest o1, ImageRequest o2) {
                if(o1.zDepth<o2.zDepth)
                    return -1;
                if(o1.zDepth>o2.zDepth)
                    return 1;
                return 0;
            }
        });

        for(int i=0;i<imageRequests.size();i++)
        {
            ImageRequest ir = imageRequests.get(i);
            setzDepth(ir.zDepth);
            drawImage(ir.image,ir.offX,ir.offY);
        }

        for(int i=0;i<lightRequests.size();i++)
        {
            LightRequest lr= lightRequests.get(i);
            drawLightRequest(lr.light, lr.locX,lr.locY);
        }

        for (int i =0;i<p.length;i++)
        {
            float r = ((lm[i] >>16)& 0xff ) /255f;
            float g = ((lm[i] >>8)& 0xff ) /255f;
            float b = (lm[i] & 0xff ) /255f;

            p[i] = ((int)(((p[i]>>16)& 0xff) *r) << 16 | (int)(((p[i]>>8)& 0xff) *g) << 8 | (int)((p[i] & 0xff) *b));
        }
        imageRequests.clear();
        lightRequests.clear();
        processin=false;


    }

    public void setPixel(int x, int y, int value)
    {
        int alpha=((value >> 24) & 0xff);
        if((x<0 || x>=pW || y<0 || y>=pH) || alpha==0){
            return;
        }

        int index = x + y * pW;

        if(zb[index]>zDepth)
            return;

        zb[index] =zDepth;

        if(alpha==255)
        {
            p[index] = value;
        }
        else
        {
            int pixelColor=p[index];

            int red=((pixelColor >>16) & 0xff) -(int)((((pixelColor >>16) & 0xff) - ((value >>16) & 0xff)) * (alpha/255f));
            int green=((pixelColor >>8) & 0xff) -(int)((((pixelColor >>8) & 0xff) - ((value >>8) & 0xff)) * (alpha/255f));
            int blue=(pixelColor & 0xff) -(int)(((pixelColor & 0xff) - (value & 0xff)) * (alpha/255f));

            p[index] = (red << 16 | green << 8 | blue);
        }
    }

    public void setLightMap(int x, int y, int value)
    {
        if(x<0 || x>=pW || y<0 || y>=pH)
        {
            return;
        }
        int index=x+y*pW;
        int baseColor = lm[index];
        lm[index] = 0;

        int maxRed = Math.max((baseColor >>16) & 0xff,(value >>16) & 0xff);
        int maxGreen= Math.max((baseColor >>8) & 0xff,(value >>8) & 0xff);
        int maxBlue= Math.max(baseColor & 0xff,value & 0xff);

        lm[index] = (maxRed<< 16 | maxGreen<< 8 | maxBlue);;
    }

    public void setLightBlock(int x, int y, int value)
    {
        if(x<0 || x>=pW || y<0 || y>=pH)
        {
            return;
        }
        int index=x+y*pW;

        if(zb[index] > zDepth)
            return;
        lb[index] = value;
    }

    public void drawImage(Image image, int offX, int offY)
    {
        if(image.isAlpha() && !processin)
        {
            imageRequests.add(new ImageRequest(image,zDepth,offX,offY));
            return;
        }

        if(offX < -image.getW())return;
        if(offY < -image.getH())return;
        if(offX > pW)return;
        if(offY > pH)return;

        int newX= 0;
        int newY=0;
        int newW =image.getW();
        int newH = image.getH();

        if( newW + offX > pW) { newW=newW - (newW + offX -pW); }
        if( newH + offY > pH) { newH = newH - ( newH + offY -pH); }
        if(offX < 0) { newX -= offX; }
        if(offY < 0) { newY -= offY; }

        for(int y =newY;y<newH;y++)
        {
            for (int x=newX;x<newW ;x++)
            {
                setPixel(x+ offX,y+offY, image.getP()[x + y * image.getW()]);
                if(((image.getP()[x+y*image.getW()] >> 24) & 0xff)!=0) {
                    setLightBlock(x + offX, y + offY, image.getLightBlock());
                }
            }
        }
    }

    public void drawImageTile(ImageTile image,int offX, int offY, int tileX, int tileY)
    {
        if(image.isAlpha() && !processin)
        {
            imageRequests.add(new ImageRequest(image.getTileImage(tileX,tileY),zDepth,offX,offY));
            return;
        }

        if(offX < -image.getTileW())return;
        if(offY < -image.getTileH())return;
        if(offX > pW)return;
        if(offY > pH)return;

        int newX= 0;
        int newY=0;
        int newW =image.getTileW();
        int newH = image.getTileH();

        if( newW + offX > pW) { newW=newW - (newW + offX -pW); }
        if( newH + offY > pH) { newH = newH - ( newH + offY -pH); }
        if(offX < 0) { newX -= offX; }
        if(offY < 0) { newY -= offY; }

        for(int y =newY;y<newH;y++)
        {
            for (int x=newX;x<newW ;x++)
            {
                setPixel(x+ offX,y+offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
                setLightBlock(x +offX,y+offY,image.getLightBlock());
            }
        }
    }

    public void drawText(String text, int offX, int offY, int color,int lightBlock)
    {


        int offset = 0;

        for(int i=0; i < text.length();i++)
        {
            int unicode = text.codePointAt(i);

            for(int y=0; y< font.getFontImage().getH();y++)
            {
                for (int x=0; x< font.getWidths()[unicode];x++) {
                    if (font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getW()] == 0xffffffff) {
                        setPixel(x + offX + offset, y + offY, color);
                        setLightBlock(x +offX,y+offY,lightBlock);
                    }
                }
            }
            offset+=font.getWidths()[unicode];
        }
    }

    public void drawRect(int offX,int offY,int width,int height,int color,boolean empty)
    {
        if(offX < -width)return;
        if(offY < -height)return;
        if(offX > pW)return;
        if(offY > pH)return;

        int newX= 0;
        int newY=0;
        int newW =width;
        int newH = height;

        if( newW + offX > pW) { newW=newW - (newW + offX -pW); }
        if( newH + offY > pH) { newH = newH - ( newH + offY -pH); }
        if(offX < 0) { newX -= offX; }
        if(offY < 0) { newY -= offY; }


        if(empty)
        {
            for(int y = newY; y <newH;y++)
            {
                setPixel(offX,y+offY,color);
                setPixel(offX + width,y+offY,color);
            }
            for (int x = newX; x<newW;x++)
            {
                setPixel(x+offX,offY,color);
                setPixel(x+offX,offY+height,color);
            }
        }
        else
        {
            for(int y=newY;y<newH;y++)
            {
                for(int x=newX;x<newW;x++)
                {
                    setPixel(x+offX,y+offY,color);
                }
            }
        }
    }

    public void drawLight(Light l, int offX,int offY)
    {
        lightRequests.add(new LightRequest(l,offX,offY));
    }

    private void drawLightRequest(Light l,int offX, int offY)
    {
        for (int i=0;i<=l.getDiameter();i++)
        {
            drawLightLine(l,l.getRadius(),l.getRadius(),i,0,offX,offY);
            drawLightLine(l,l.getRadius(),l.getRadius(),i,l.getDiameter(),offX,offY);
            drawLightLine(l,l.getRadius(),l.getRadius(),0,i,offX,offY);
            drawLightLine(l,l.getRadius(),l.getRadius(),l.getDiameter(),i,offX,offY);
        }
    }

    private void drawLightLine(Light l, int x0, int y0,int x1,int y1,int offX,int offY)
    {
        int dx = Math.abs(x1-x0);
        int dy = Math.abs(y1-y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx-dy;
        int e2;

        while(true)
        {
            int screenX= x0 - l.getRadius() + offX;
            int screenY= y0 - l.getRadius() + offY ;



            int lightColor=l.getLightValue(x0,y0);
            if(lightColor==0)
                return;
            if(screenX < 0 || screenX >=pW||screenY<0||screenY>=pH)
                return;

            if(lb[screenX + screenY*pW]==Light.FULL)
                return;

            setLightMap(screenX,screenY,lightColor);

            if(x0 == x1 && y0 == y1)
                break;
            e2 = 2* err;

            if(e2>=-1*dy)
            {
                err-=dy;
                x0+=sx;
            }

            if(e2 <dx)
            {
                err += dx;
                y0+=sy;
            }
        }
    }


    public int getzDepth() {
        return zDepth;
    }

    public void setzDepth(int zDepth) {
        this.zDepth = zDepth;
    }
}
