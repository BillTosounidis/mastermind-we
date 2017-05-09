package we.software.gui;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 * Created by Bill on 30-Mar-17.
 *
 * MenuButton is a general class for all the menu buttons that share the same usability.
 */
class MenuButton extends JButton {

    private ImageIcon image;
    private ImageIcon imageHover;
    private AudioClip hover, pressed;
    private boolean isOn = false;

    public MenuButton(String imagePath, int xPos, int yPos, int displacement){
        //The displacement variable is used when a hovered button changes size so it has to be placed differently.

        image = new ImageIcon(LoadAssets.load("Buttons/"+imagePath));
        imageHover = new ImageIcon(LoadAssets.load("Buttons/"+"h" +imagePath));

        setIcon(image);
        setBounds(xPos, yPos, image.getIconWidth(), image.getIconHeight());

        this.addMouseListener(new MouseAdapter(){

            public void mouseEntered(MouseEvent e){

                setIcon(imageHover);
                setBounds(xPos-displacement, yPos, imageHover.getIconWidth(), imageHover.getIconHeight());

                if(MainMenu.soundfxOn){
                    URL hoverUrl = MenuButton.class.getResource("/Hover.wav");
                    hover = Applet.newAudioClip(hoverUrl);
                    hover.play();
                    isOn = true;
                }
            }

            public void mouseExited(MouseEvent e){

                setIcon(image);
                setBounds(xPos, yPos, image.getIconWidth(), image.getIconHeight());
                if(isOn){
                    hover.stop();
                    isOn = false;
                }
            }
        });

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    public void playSound(){

        URL pressedUrl = MenuButton.class.getResource("/Select.wav");
        pressed = Applet.newAudioClip(pressedUrl);
        pressed.play();
    }

    public void setIcon(String path){

        ImageIcon newImage = new ImageIcon(LoadAssets.load(path));
        setIcon(newImage);
    }
}
