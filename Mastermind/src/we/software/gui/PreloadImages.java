package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Bill on 17-May-17.
 * We use this class to load images that are going to be used multiple times so that they don't load everytime.
 */
public class PreloadImages {

    private static ImageIcon glassRed, glassGreen, glassBlue, glassYellow, glassWhite, glassBlack;
    private static ImageIcon glowRed, glowGreen, glowBlue, glowYellow, glowWhite, glowBlack;
    private static BufferedImage whitePeg, blackPeg, greenPeg, bluePeg, yellowPeg, redPeg, evalLU, evalLD, evalRU, evalRD,
            bevalLU, bevalLD, bevalRU, bevalRD;

    private static ArrayList<ImageIcon> glasses = new ArrayList();
    private static ArrayList<BufferedImage> numbers = new ArrayList();
    private static ArrayList<BufferedImage> pegs = new ArrayList();


    public static void preloadImages(){

        loadGlasses();
        loadNumbers();
        loadPegs();
    }


    private static void loadGlasses(){

        glassRed = new ImageIcon(LoadAssets.load("Buttons/glassRed.png"));
        glasses.add(glassRed);
        glassGreen = new ImageIcon(LoadAssets.load("Buttons/glassGreen.png"));
        glasses.add(glassGreen);
        glassBlue = new ImageIcon(LoadAssets.load("Buttons/glassBlue.png"));
        glasses.add(glassBlue);
        glassYellow = new ImageIcon(LoadAssets.load("Buttons/glassYellow.png"));
        glasses.add(glassYellow);
        glassWhite = new ImageIcon(LoadAssets.load("Buttons/glassWhite.png"));
        glasses.add(glassWhite);
        glassBlack = new ImageIcon(LoadAssets.load("Buttons/glassBlack.png"));
        glasses.add(glassBlack);

        glowRed = new ImageIcon(LoadAssets.load("Buttons/glowRed.png"));
        glasses.add(glowRed);
        glowGreen = new ImageIcon(LoadAssets.load("Buttons/glowGreen.png"));
        glasses.add(glowGreen);
        glowBlue = new ImageIcon(LoadAssets.load("Buttons/glowBlue.png"));
        glasses.add(glowBlue);
        glowYellow = new ImageIcon(LoadAssets.load("Buttons/glowYellow.png"));
        glasses.add(glowYellow);
        glowWhite = new ImageIcon(LoadAssets.load("Buttons/glowWhite.png"));
        glasses.add(glowWhite);
        glowBlack = new ImageIcon(LoadAssets.load("Buttons/glowBlack.png"));
        glasses.add(glowBlack);
    }

    private static void loadNumbers(){

        for(int i=0; i<10; i++){

            String path ="TurnNumbers/"+Integer.toString(i+1)+".png";

            try {
                numbers.add(ImageIO.read(LoadAssets.load(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadPegs(){

        try {

            redPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/red.png"));
            pegs.add(redPeg);
            greenPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/green.png"));
            pegs.add(greenPeg);
            bluePeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/blue.png"));
            pegs.add(bluePeg);
            yellowPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/yellow.png"));
            pegs.add(yellowPeg);
            whitePeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/white.png"));
            pegs.add(whitePeg);
            blackPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/black.png"));
            pegs.add(blackPeg);

            evalLU = ImageIO.read(LoadAssets.load("Pegs_Evaluation/checkleftup.png"));
            pegs.add(evalLU);
            bevalLU = ImageIO.read(LoadAssets.load("Pegs_Evaluation/bcheckleftup.png"));
            pegs.add(bevalLU);
            evalLD = ImageIO.read(LoadAssets.load("Pegs_Evaluation/checkleftdown.png"));
            pegs.add(evalLD);
            bevalLD = ImageIO.read(LoadAssets.load("Pegs_Evaluation/bcheckleftdown.png"));
            pegs.add(bevalLD);
            evalRU = ImageIO.read(LoadAssets.load("Pegs_Evaluation/checkrightup.png"));
            pegs.add(evalRU);
            bevalRU = ImageIO.read(LoadAssets.load("Pegs_Evaluation/bcheckrightup.png"));
            pegs.add(bevalRU);
            evalRD = ImageIO.read(LoadAssets.load("Pegs_Evaluation/checkrightdown.png"));
            pegs.add(evalRD);
            bevalRD = ImageIO.read(LoadAssets.load("Pegs_Evaluation/bcheckrightdown.png"));
            pegs.add(bevalRD);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageIcon> getGlasses(){
        return glasses;
    }

    public static ArrayList<BufferedImage> getNumbers(){
        return numbers;
    }

    public static ArrayList<BufferedImage> getPegs(){
        return pegs;
    }
}
