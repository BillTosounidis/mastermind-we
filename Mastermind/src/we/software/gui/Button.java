package we.software.gui;

import javax.swing.*;

/**
 * Created by Bill on 17-May-17.
 */
public abstract class Button extends JButton{

    protected ImageIcon image, imageHover;

    public Button(String imagePath, int xPos, int yPos){

        image = new ImageIcon(LoadAssets.load("Buttons/"+imagePath));
        try{
            imageHover = new ImageIcon(LoadAssets.load("Buttons/"+"h" +imagePath));
        }catch (Exception e){
            imageHover = null;
        }

        setIcon(image);
        setBounds(xPos, yPos, image.getIconWidth(), image.getIconHeight());

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    public abstract void playSound();

}
