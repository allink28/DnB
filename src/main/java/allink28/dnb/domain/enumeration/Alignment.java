package allink28.dnb.domain.enumeration;

/**
 * The Alignment enumeration.
 */
public enum Alignment {

    Unaligned, //Animals and other creatures not sapient enough to make a decision based on alignment

    Lawful_Good,    Neutral_Good,   Chaotic_Good,
    Lawful_Neutral, True_Neutral,   Chaotic_Neutral,
    Lawful_Evil,    Neutral_Evil,   Chaotic_evil;

    public static Alignment[] ALIGNMENTS = {
        Lawful_Good,    Neutral_Good,   Chaotic_Good,
        Lawful_Neutral, True_Neutral,   Chaotic_Neutral,
        Lawful_Evil,    Neutral_Evil,   Chaotic_evil};

    public static Alignment[][] GRID = {
        {Lawful_Good,       Neutral_Good,   Chaotic_Good},
        {Lawful_Neutral,    True_Neutral,   Chaotic_Neutral},
        {Lawful_Evil,       Neutral_Evil,   Chaotic_evil}
    };
}
