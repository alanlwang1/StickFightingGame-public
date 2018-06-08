
/**
 * Write a description of class PopUp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class PopUp
{
    // instantiate needed variables
    private static int width;
    private static int height;

    /**
     * Sets the width of the pop up. 
     * @param x the new width of the pop up
     */
    public static void setWidth(int x)
    {
        width = x;
    }
    /**
     * Sets the height of the pop up
     * @param x the new height of the pop up
     */
    public static void setHeight(int x)
    {
        height = x;
    }
    /**
     * Gets the width of the pop up
     * @return the width of the pop up
     */
    public static int getWidth()
    {
        return width;
    }
    /**
     * Gets the height of the pop up
     * @return the height of the pop up
     */
    public static int getHeight()
    {
        return height;
    }
}
