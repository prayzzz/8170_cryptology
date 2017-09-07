package presentation;

import javafx.stage.*;

/**
 * This is our stage for the different scenes.
 * We need only one stage, so this is a Singleton.
 *
 * @author Romy Spangenberg
 */
public class ProgramStage extends Stage
{

    private static ProgramStage instance;

    /**
     * This is the private constructor for the Singleton.
     */
    private ProgramStage()
    {

    }

    /**
     * Get the instance.
     *
     * @return instance the {@link ProgramStage} to show the different scenes
     */
    public static ProgramStage getInstance()
    {
        if (instance == null) instance = new ProgramStage();
        return instance;
    }
}
