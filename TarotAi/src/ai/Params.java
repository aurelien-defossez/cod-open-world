package ai;

public class Params {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// Contracts Threshold
	public static final int THRESHOLD_GARDE = 48;
	public static final int THRESHOLD_GARDE_SANS = 70;
	public static final int THRESHOLD_GARDE_CONTRE = 80;
	
	// Hand estimation points
	public static final int POINTS_LAST_POSITION = 5;
	public static final int POINTS_PREVIOUS_PRISE = -4;
	public static final int POINTS_PREVIOUS_GARDE = -8;
	public static final int POINTS_PREVIOUS_GARDE_SANS = -50;
	public static final int POINTS_EXCUSE = 10;
	public static final int POINTS_21 = 12;
	public static final int POINTS_PETIT_5 = 6;
	public static final int POINTS_PETIT_6 = 8;
	public static final int POINTS_PETIT_7 = 10;
	public static final int POINTS_PETIT_8 = 11;
	public static final int POINTS_ATOUT = 2;
	public static final int POINTS_GROS_ATOUT = 2;
	public static final int POINTS_SUITE_GROS_ATOUT = 1;
	public static final int POINTS_ROI = 6;
	public static final int POINTS_DAME = 3;
	public static final int POINTS_CAVALIER = 2;
	public static final int POINTS_VALET = 1;
	public static final int POINTS_BONUS_MARIAGE = 1;
	public static final int POINTS_LONGUE = 5;
	public static final int POINTS_LONGUE_CARTE_SUPPL = 2;
	public static final int POINTS_COUPE = 6;
	public static final int POINTS_SINGLETTE = 3;
	
	// Strategies
	public static final int MIN_LONGUE_SIZE = 5;
	public static final int PETIT_HUNT_MIN_ATOUTS_DEFENSE = 7;
	public static final double PETIT_HUNT_MAX_RATIO_DIRECT = 0.28;
	public static final double PETIT_HUNT_MAX_RATIO_INDIRECT = 0.25;
	public static final double PETIT_HUNT_MAX_RATIO_INDIRECT_2 = 0.20;
	public static final double ATTACK_ENTAME_DEFAULT_CUT_TRESHOLD = 0.60;
	public static final double ATTACK_FOLLOW_COLOR_PLAY_ROI_MIN_SCORE = 0.0;
	public static final double ATTACK_FOLLOW_COLOR_PLAY_DAME_MIN_SCORE = -0.15;
	public static final double ATTACK_FOLLOW_COLOR_PLAY_CAVALIER_MIN_SCORE = -0.3;
	public static final double ATTACK_FOLLOW_COLOR_PLAY_VALET_MIN_SCORE = -0.5;
	public static final double ATTACK_SAVE_PETIT_MAX_CUT_TRESHOLD_SECOND = 0.3;
	public static final double ATTACK_SAVE_PETIT_MAX_CUT_TRESHOLD_THIRD = 0.2;
	public static final double ATTACK_BUY_DAME_MIN_CUT_PROBABILITY = 0.5;
	public static final double ATTACK_BUY_ROI_MIN_CUT_PROBABILITY = 0.4;
	public static final double ATTACK_BUY_PETIT_MIN_CUT_PROBABILITY = 0.2;
	public static final double ATTACK_PLAY_EXCUSE_SACRIFIABLE_POINTS = 1.5;
	public static final double ATTACK_PLAY_EXCUSE_SACRIFIABLE_POINTS_END = 2.5;
	public static final double ATTACK_PLAY_EXCUSE_END_BEGINNING_TURN = 12;
}
