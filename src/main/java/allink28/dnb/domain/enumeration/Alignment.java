package allink28.dnb.domain.enumeration;

/**
 * The Alignment enumeration.
 */
public enum Alignment {

    Unaligned, //Animals and other creatures not sapient enough to make a decision based on alignment

    Lawful_Good,    Neutral_Good,   Chaotic_Good,
    Lawful_Neutral, True_Neutral,   Chaotic_Neutral,
    Lawful_Evil,    Neutral_Evil,   Chaotic_Evil;

    public static Alignment[] ALIGNMENTS = {
        Lawful_Good,    Neutral_Good,   Chaotic_Good,
        Lawful_Neutral, True_Neutral,   Chaotic_Neutral,
        Lawful_Evil,    Neutral_Evil,   Chaotic_Evil};

    public static Alignment[][] GRID = {
        {Lawful_Good,       Neutral_Good,   Chaotic_Good},
        {Lawful_Neutral,    True_Neutral,   Chaotic_Neutral},
        {Lawful_Evil,       Neutral_Evil,   Chaotic_Evil}
    };

    /**
     * GRID[EVIL_ROW][x]
     */
    public static final int EVIL_ROW = 2;

    /**
     * GRID[x][LAWFUL_COLUMN] for the Chaotic column
     */
    public static final int LAWFUL_COLUMN = 0;

    /**
     * GRID[x][CHAOTIC_COLUMN] for the Chaotic column
     */
    public static final int CHAOTIC_COLUMN = 2;
}
