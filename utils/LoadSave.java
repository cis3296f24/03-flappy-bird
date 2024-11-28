package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

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
    // Start adding backgrounds in this class.
    // See Playing.java
    public static final String FlappyCity_BG_IMG = "scene_chatGPT.png";  // This is the background created by CHAT_GPT.
    public static final String GROUND_IMG = "seamless_ground.png";  // This is the background created by CHAT_GPT.
    // Halloween theme backgrounds
    public static final String Folder = "HalloweenThemes/";
    public static final String FlappyLayer_1 = Folder + "Layer_1.png";  // This is the background downloaded from free sites.
    public static final String FlappyLayer_2 = Folder + "Layer_2.png";  // This is the background downloaded from free sites.
    public static final String FlappyLayer_3 = Folder + "Layer_3.png";  // This is the background downloaded from free sites.
    public static final String DEATH_SCREEN = "death_screen.png";
    public static final String OPTIONS_MENU = "options_background.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";

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
    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++)
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png"))
                    filesSorted[i] = files[j];

            }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return imgs;
    }


}