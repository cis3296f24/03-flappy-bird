package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.FlappyGame;

public class LoadSave {

    ///***********************************************************************************
    ///                          IMPORTANT NOTES
    ///                          - by Shafiq
    /// - This game uses a pixel level map to load graphics for the background
    /// - Explanation for collisions.
    ///   https://youtu.be/PrAmaeQF4f0?list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&t=70
    ///
    /// - Moving the background images
    ///   https://youtu.be/JmcBRVz2Voo?list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&t=913
    ///

    // public static final String PLAYER_ATLAS = "player_sprites.png";
    // public static final String PLAYER_ATLAS = "UD_FB.png";
    // public static final String PLAYER_ATLAS = "HD_B-NoWM.png";
    public static final String PLAYER_ATLAS = "eagle_Linear_Sheet_Fixed.png";

    // public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites_new.png";

    // public static final String LEVEL_ONE_DATA = "bird_clear_bk_ground.png";
    public static final String LEVEL_ONE_DATA = "gen_red_bars_image.png";

    // public static final String PLAYER_ATLAS = "player_sprites.png";
    // public static final String LEVEL_ATLAS = "outside_sprites.png";
    //	public static final String LEVEL_ONE_DATA = "level_one_data.png";
    // public static final String LEVEL_ONE_DATA = "level_one_data_long.png";

    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "background_menu.png";
    public static final String STATUS_BAR = "health_power_bar.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";

    // Start adding backgrounds in this class.
    // See Playing.java
    public static final String OPTIONS_MENU = "options_background.png";

    public static final String FlappyCity_BG_IMG = "scene_chatGPT.png";  // This is the background created by CHAT_GPT.
    public static final String GROUND_IMG = "seamless_ground.png";  // This is the background created by CHAT_GPT.
    // Halloween theme backgrounds
    public static final String Folder = "HalloweenThemes/";
    public static final String FlappyLayer_1 = Folder + "Layer_1.png";  // This is the background downloaded from free sites.
    public static final String FlappyLayer_2 = Folder + "Layer_2.png";  // This is the background downloaded from free sites.
    public static final String FlappyLayer_3 = Folder + "Layer_3.png";  // This is the background downloaded from free sites.
    static int totalPipeCount = 0;


    public static BufferedImage GetSpriteAtlas(String fileName) {
        // System.out.println(fileName);
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    // This method loads the level and uses getHeight and getWidth of the small pixel board.
    public static int[][] GetLevelData() {
        totalPipeCount = 0;
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        //System.out.println("img height: " + img.getHeight());
        System.out.println("img width: " + img.getWidth());
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
                if (lvlData[j][i] == 15) {
                    totalPipeCount++; // Count total # of pipes.
                    System.out.println("Total Pipes = " + totalPipeCount);

                }
        }
        // System.out.println("PIPE # " + totalPipeCount);
        return lvlData;

    }

}