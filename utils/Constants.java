package utils;

import main.FlappyGame;

public class Constants {

    public static final float GRAVITY = 0.04f * FlappyGame.SCALE;
    public static final int ANI_SPEED = 25;

    public static class Dialogue {
        public static final int QUESTION = 0;
        public static final int EXCLAMATION = 1;

        public static final int DIALOGUE_WIDTH = (int) (14 * FlappyGame.SCALE);
        public static final int DIALOGUE_HEIGHT = (int) (12 * FlappyGame.SCALE);
      //  public static class ObjectConstants {

        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
//        public static final int BARREL = 2;
//        public static final int BOX = 3;
  //      public static final int SPIKE = 4;
//        public static final int CANNON_LEFT = 5;
//        public static final int CANNON_RIGHT = 6;
        public static final int TREE_ONE = 7;
        public static final int TREE_TWO = 8;
        public static final int TREE_THREE = 9;

        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (FlappyGame.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int) (FlappyGame.SCALE * CONTAINER_HEIGHT_DEFAULT);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (FlappyGame.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (FlappyGame.SCALE * POTION_HEIGHT_DEFAULT);

        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (FlappyGame.SCALE * SPIKE_WIDTH_DEFAULT);
        public static final int SPIKE_HEIGHT = (int) (FlappyGame.SCALE * SPIKE_HEIGHT_DEFAULT);

        public static final int CANNON_WIDTH_DEFAULT = 40;
        public static final int CANNON_HEIGHT_DEFAULT = 26;
        public static final int CANNON_WIDTH = (int) (CANNON_WIDTH_DEFAULT * FlappyGame.SCALE);
        public static final int CANNON_HEIGHT = (int) (CANNON_HEIGHT_DEFAULT * FlappyGame.SCALE);

        public static int GetSpriteAmount(int type) {
            switch (type) {
                case QUESTION, EXCLAMATION:
                    return 5;
            }

            return 0;
        }
    }
    // Background and other scenery constants for Flappy Bird
    public static class FlappyWorldConstants {

        public static final int GROUND_HEIGHT_DEFAULT = 320; // Length of the grass or floor picture.
        public static final int GROUND_WIDTH_DEFAULT = 2048; // Height or width of the grass or floor picture.

        // The constants need to be scaled to the Flappy Game scale defined in FlappyGame.java
        // See the usage in Playing.java
        public static final int GROUND_HEIGHT = (int) (GROUND_HEIGHT_DEFAULT * FlappyGame.SCALE);
        public static final int GROUND_WIDTH = (int) (GROUND_WIDTH_DEFAULT * FlappyGame.SCALE);
    }

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * FlappyGame.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * FlappyGame.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * FlappyGame.SCALE);
        }

        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * FlappyGame.SCALE);

        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * FlappyGame.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * FlappyGame.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * FlappyGame.SCALE);
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        public static final int DEAD = 4;
        public static final int ANI_SPEED = 25;
        public static final int COLLIDED = 0;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case JUMP:
                case ATTACK_1:
                case ATTACK_JUMP_1:
                case ATTACK_JUMP_2:
                    return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }
    }

}