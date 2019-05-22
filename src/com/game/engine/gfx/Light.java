package com.game.engine.gfx;

public class Light
{
    public static final int NONE=0;

    public static final int FULL=1;

    private int radius, diameter, color;
    private int[]lm;

    public Light(int radius,int color)
    {
        double degree= 45;

        this.radius = radius;
        diameter=radius*2;
        this.color=color;
        this.lm = new int[diameter*diameter];


        for(int y=0;y< diameter;y++)
        {
            for (int x=0;x<diameter;x++)
            {
                double distance = Math.sqrt((x -radius)*(x-radius)+(y-radius)*(y-radius));
                int relativeX = (x - radius);
                int relativeY = (y- radius);

                if(distance < radius)
                {
                    double power =1 - (distance/radius);

                    lm[x+y*diameter] =((int)(((color>>16)& 0xff) *power) << 16 | (int)(((color>>8)& 0xff) *power) << 8 | (int)((color& 0xff) *power));

                }
                else
                {
                    lm[x+y*diameter]=0;
                }
            }
        }
        /*  for(int y=0;y< diameter;y++)
        {
            for (int x=0;x<diameter;x++)
            {
                double distance = Math.sqrt((x -radius)*(x-radius)+(y-radius)*(y-radius));

                if(distance < radius)
                {
                    double power =1 - (distance/radius);

                    lm[x+y*diameter] =((int)(((color>>16)& 0xff) *power) << 16 | (int)(((color>>8)& 0xff) *power) << 8 | (int)((color& 0xff) *power));

                }
                else
                {
                    lm[x+y*diameter]=0;
                }
            }
        }*/
    }
    private boolean test(int x,int y,double d)
    {
        double plus =0.2;
        if((Math.tan( 0.436332313 )-1/2*Math.tan(0))*(double) x<=(double)y&&Math.tan(1.308996939)*(double)x<=y)
            return true;

        return false;
    }


    public int getLightValue(int x,int y)
    {
        if(x<0||x>=diameter||y<0||y>=diameter)
            return 0;
        return lm[x+y*diameter];
    }


    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        diameter = radius*2;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
        radius = diameter/2;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int[] getLm() {
        return lm;
    }

    public void setLm(int[] lm) {
        this.lm = lm;
    }
}
