package com.game.engine.motion;

import com.game.rougelike.GameObject;

public class Frame
{
    private int speed;
    private float startValue;
    private float value;
    private int direction;


    boolean start=false;
    public boolean done = false;

    public Frame(int value,int direction,int speed)
    {
        this.value=value;
        this.direction=direction;
        this.startValue=0;
        this.speed=speed;
    }


    public void Start(){start=true;done=false;}

    public void Do(GameObject go,float dt)
    {
        if(start)
        {

            if(direction==2&&startValue<value)
            {
                go.setPosX(go.getPosX()+(dt*speed));
                startValue = startValue + dt*speed;

                if (startValue>value&&go.getPosX()%16!=0)
                {
                    go.setPosX(go.getPosX()-(go.getPosX()%16));
                    start=false;
                    startValue=0;
                    done=true;
                }
                if (startValue==value)
                {
                    start=false;
                    done=true;
                    startValue=0;
                }
            }

            if(direction==-2&&startValue<value)
            {

                go.setPosX(go.getPosX()-(dt*speed));
                startValue = startValue + dt*speed;

                if (startValue>value&&go.getPosX()%16!=0)
                {
                    go.setPosX(go.getPosX()-((go.getPosX()%16))+16);
                    start=false;
                    startValue=0;
                    done=true;
                }
                if (startValue==value)
                {
                    start=false;
                    done=true;
                    startValue=0;
                }
            }

            if(direction==-1&&startValue<value)
            {
                go.setPosY(go.getPosY()+(dt*speed));
                startValue = startValue + dt*speed;

                if (startValue>value&&go.getPosY()%16!=0)
                {
                    go.setPosY(go.getPosY()-(go.getPosY()%16));
                    start=false;
                    startValue=0;
                    done=true;
                }
                if (startValue==value)
                {
                    start=false;
                    done=true;
                    startValue=0;
                }
            }

            if(direction==1&&startValue<value)
            {

                go.setPosY(go.getPosY()-(dt*speed));
                startValue = startValue + dt*speed;

                if (startValue>value&&go.getPosY()%16!=0)
                {
                    go.setPosY(go.getPosY()-((go.getPosY()%16))+16);
                    start=false;
                    startValue=0;
                    done=true;
                }
                if (startValue==value)
                {
                    start=false;
                    done=true;
                    startValue=0;
                }
            }



        }

    }

}
