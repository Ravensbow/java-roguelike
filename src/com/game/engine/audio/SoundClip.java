package com.game.engine.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundClip
{
    private Clip clip = null;
    private FloatControl gainControl;
    public SoundClip(String path)
    {
        try
        {
            InputStream audioSrc= SoundClip.class.getResourceAsStream(path);
            InputStream buffredIn = new BufferedInputStream(audioSrc);
            AudioInputStream ais = AudioSystem.getAudioInputStream(buffredIn);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels()*2,
                    baseFormat.getSampleRate(),
                    false);

            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat,ais);

            try{
                clip = AudioSystem.getClip();
                clip.open(dais);

                gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);

            }catch (LineUnavailableException e){
                e.printStackTrace();
            }


        }catch (UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
        }
    }

    public void play()
    {
        if(clip==null)
        {
            return;
        }
        stop();
        clip.setFramePosition(0);
        while(!clip.isRunning())
        {
            clip.start();
        }
    }

    public void stop()
    {
        if(clip.isRunning())
        {
            clip.stop();
        }
    }

    public void close()
    {
        stop();
        clip.drain();
        clip.close();
    }

    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }

    public void setVolume(float value)
    {
        gainControl.setValue(value);
    }

    public boolean isRunning()
    {
        return clip.isRunning();
    }






}
