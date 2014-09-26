/*************************************
* @author Dmitry Polyanskyy
* Title: The Connect Four Game
* Description: The user drops a yellow colored chip on a 6 x 7 game board. The objective is so that the user connects four yellow
* chips in a row, whether it be vertically, horizontally or diagonally. The opposing player(the computer) will also be
* trying to connect four of his (red) chips in the same way. Both players will try to stop each other from connecting
* four.
*************************************/
import java.awt.*;
import hsa.Console;

public class ConnectFour
{
    static Console c;
    static final int OVAL_LENGTH = 75;              // Value of Oval Length
    static final int OVAL_WIDTH = 75;               // Value of Oval Width
    static final int X_OFFSET = 100;                // x Spacing between each Oval
    static final int Y_OFFSET = 100;                // y Spacing between each Oval
    // Declaring 2-D array to store chips
    static int[] [] connectFour = new int [7] [8];              // The row and columns 0 are not used to avoid dealing with 0 and the offset
    // 0 is an unoccupied slot, 1 is player occupied slot
    // 2 is a computer occupied slot
    // Declaring 2-D helper array to keep X coordinate
    static int[] [] slotLocationX = new int [7] [8];    // The row and columns 0 are not used to avoid dealing with 0 and the offset
    // Declaring 2-D helper array to keep Y coordinate
    static int[] [] slotLocationY = new int [7] [8];    // The row and columns 0 are not used to avoid dealing with 0 and the offset

    /**
     * Method for introduction screen
     */
    static void introDisplay ()
    {
        //Color myIntro = new Color (94,213,130);               // Creating custom color for introduction
        Color myIntro = new Color (22, 143, 233);
        c.setColor (myIntro);
        c.fillRect (0, 0, 2000, 2000);
        c.setColor (Color.green);
        Font lucidaHand = new Font ("Lucida Handwriting", Font.BOLD, 50);       // Declaring new (local) font for intro
        Font SNAPITC = new Font ("SNAP ITC", Font.BOLD, 50);
        c.setFont (lucidaHand);
        c.drawString ("Welcome to the ", 345, 100);
        c.drawString ("Connect Four Game!", 280, 225);
        Font choiceFont = new Font ("Arial", Font.PLAIN, 30);
        Color myIntroFont = new Color (232, 251, 0);
        c.setColor (myIntroFont);
        c.setFont (SNAPITC);                                                    // Declaring new font for user options
        char choice;
        c.drawString ("A) Play Now", 380, 400);
        c.drawString ("B) Instructions", 380, 460);
        c.drawString ("C) Quit", 380, 520);
        c.fillOval (1000, 70, 100, 100);
        c.setColor (Color.red);
        c.fillOval (1010, 110, 100, 100);

        // Does not let user enter a different choice other than a, b or c
        do
        {
            choice = c.getChar ();                                           // Obtaining users choice
        }
        while ((choice != 'a') && (choice != 'b') && (choice != 'c'));

        // Determining the users choice

        // Continuing to game
        if (choice == 'a')
        {
            c.clear ();
        }
        //Instructions on how to play game
        else if (choice == 'b')
        {
            c.clear ();                                                       // Clearing introduction screen
            c.setColor (myIntro);
            c.fillRect (0, 0, 2000, 2000);
            c.setFont (lucidaHand);
            c.setColor (Color.red);
            c.drawString ("Connect Four Instructions", 200, 90);
            c.setColor (Color.orange);
            c.setFont (choiceFont);
            c.drawString ("1) Both players take turns dropping one of their chips down any slot of the board", 100, 200);
            c.drawString ("2) First player to get four chips in a row horizontally, vertically or diagonally wins", 100, 300);
            c.drawString ("3) If all the slots are filled and nobody gets 4 in a row it is a draw", 100, 400);
            c.drawString ("4) Player uses the yellow chip, computer uses the red chip", 100, 500);
            c.setColor (Color.green);
            c.drawString ("Press any key to start playing game!", 100, 700);
            c.setColor (Color.yellow);
            c.fillOval (850, 600, 100, 100);
            c.setColor (Color.red);
            c.fillOval (1000, 600, 100, 100);
            c.getChar ();                                                  // User must enter a key on their keyboard in order to continue
            c.clear ();
        }
        // Exiting the program if the user chooses to
        else if (choice == 'c')
        {
            System.exit (0);
        }

        c.setColor (Color.orange);
    }


    /**
    * Method introduces delay (Taken from //X: drive)
    * @param int millisecs, timeout
    */
    static void delay (int millisecs)
    {
        try
        {
            Thread.currentThread ().sleep (millisecs);
        }
        catch (InterruptedException e)
        {
        }
    }


    /**
    * Method places white box in specified place
    */
    static void whiteBox ()
    {
        c.setColor (Color.white);
        c.fillRect (830, 30, 1000, 1000);
    }


    /**
     * Method prompts user for his turn
     */
    static void usersTurn ()
    {
        int userColumn;
        Font promptFont = new Font ("Arial", Font.PLAIN, 14); // Local font declaration
        c.setFont (promptFont);
        c.setColor (Color.black);
        // Checking if the column is full
        do
        {
            do
            {
                c.drawString ("Which column do you want to place your chip in?", 830, 60);
                c.drawString ("Numbers may only be 1 to 7 unless the whole column is full", 830, 80);
                c.setCursor (1, 1000);                          // Sets cursor for user to input which column he wants to put his chip in
                userColumn = c.readInt ();

                if ((userColumn < 1) || (userColumn > 7))       // Checks to see if the users input is valid
                {
                    c.setColor (Color.magenta);
                    c.drawString ("INVALID ENTRY!, enter a different number", 830, 150);
                    c.drawString ("or refer to instructions on how to play game", 830, 170);
                    c.setColor (Color.black);
                }
            }
            while ((userColumn < 1) || (userColumn > 7));

            whiteBox ();
        }
        // Placeholder for wrong column
        while (connectFour [1] [userColumn] != 0);      // The row in the selected in the selected column is occupied

        for (int x = 6 ; x >= 1 ; x--)                          // Starting from the bottom of the column to check if it is empty
        {
            if (connectFour [x] [userColumn] == 0)          // Checking to see if column is empty (0 means empty)
            {
                connectFour [x] [userColumn] = 1;       // Occupy column by players chip
                // test
                c.setColor (Color.yellow);
                c.fillOval (slotLocationX [x] [userColumn], slotLocationY [x] [userColumn], OVAL_LENGTH, OVAL_WIDTH);
                break;
            }
        }
    }


    /**
     * Method for computer's turn (randomising)
     */
    static void computersTurn ()
    {
        c.setColor (Color.red);
        Font promptFont = new Font ("Arial", Font.PLAIN, 14); // Local font declaration
        c.setFont (promptFont);
        c.drawString ("Computers Turn!", 830, 60);
        delay (500);
        whiteBox ();


        int selectedColumn = (int) ((Math.random () * 7) + 1);  // Generates random number (1-7)

        do
        {
            selectedColumn = (int) ((Math.random () * 7) + 1);
        }
        while (connectFour [1] [selectedColumn] != 0);          // If loop is not empty a different number must be generated


        for (int x = 6 ; x >= 1 ; x--)                                  // Starting from the bottom of the column to check if it is empty
        {
            if (connectFour [x] [selectedColumn] == 0)      // Checking to see if column is empty (0 means empty)
            {
                connectFour [x] [selectedColumn] = 2;   // Column occupied by computers chip
                // test
                c.setColor (Color.red);
                c.fillOval (slotLocationX [x] [selectedColumn], slotLocationY [x] [selectedColumn], OVAL_LENGTH, OVAL_WIDTH);
                break;
            }
        }
    }


    /**
     * Method draws chip slot
     * @param x initial x coordinate
     * @param y initial y coordinate
     */
    static void drawChipSlot (int x, int y)
    {
        c.setColor (Color.white);
        for (int v = 1 ; v <= 6 ; v++)          // Controlling amount of vertical positions (6 rows)
        {
            int xOval = x;
            int yOval = y;
            for (int h = 1 ; h <= 7 ; h++)  // Controlling amount of horizontal positions (7 columns)
            {
                c.fillOval (xOval, yOval, OVAL_LENGTH, OVAL_WIDTH);
                slotLocationX [v] [h] = xOval;
                slotLocationY [v] [h] = yOval;
                xOval += X_OFFSET;
            }
            y += Y_OFFSET;
        }
        c.setColor(Color.red);
        c.drawString("1" , 85,775);
        c.drawString("2" , 185,775);
        c.drawString("3" , 285,775);
        c.drawString("4" , 385,775);
        c.drawString("5" , 485,775);
        c.drawString("6" , 585,775);
        c.drawString("7" , 685,775);
    }


    /**
     * Method checks if there is a winner
     * @param userType "1" is the player, "2" is the computer
     * @return true if there is a winner, false if not
     */
    static boolean isWinner (int userType)
    {
        //
        // Checking all 7 *VERTICAL* columns and determining if there is a winner ('d' is down)
        //
        for (int d = 1 ; d <= 7 ; d++)
        {
            // Checking all three groups of four adjacent *VERTICAL* cells are winners ('a' is across), by shifting them down by one
            for (int a = 1 ; a <= 3 ; a++)
            {
                // Checking four adjacent vertical cells
                if ((connectFour [a] [d] == userType) && ((connectFour [a + 1] [d] == userType)) &&
                        ((connectFour [a + 2] [d] == userType)) && ((connectFour [a + 3] [d] == userType)))
                {
                    return true;
                }
            }
        }

        //
        // Checking all 6 *HORIZONTAL* columns and determining if there is a winner ('d' is down)
        //
        for (int a = 1 ; a <= 6 ; a++)
        {
            // Checking all four groups of four adjacent *HORIZONTAL* cells are winners ('a' is across), by shifting them down by one
            for (int d = 1 ; d <= 4 ; d++)
            {
                // Checking four adjacent horizontal cells
                if ((connectFour [a] [d] == userType) && ((connectFour [a] [d + 1] == userType)) &&
                        ((connectFour [a] [d + 2] == userType)) && ((connectFour [a] [d + 3] == userType)))
                {
                    return true;
                }
            }
        }

        //
        // Checking all 3 "diagonal" columns that are at their maximum height in their left part and lowest height in the right part
        //
        for (int h = 1 ; h <= 3 ; h++)
        {
            // Checking all groups to see if they are diagonally winners
            for (int v = 1 ; v <= 4 ; v++)
            {
                // Checking if four adjacent diagonal cells are winners
                if ((connectFour [h] [v] == userType) && ((connectFour [h + 1] [v + 1] == userType)) &&
                        ((connectFour [h + 2] [v + 2] == userType)) && ((connectFour [h + 3] [v + 3] == userType)))
                {
                    // Determining who the winner was: (User or Computer)
                    return true;
                }
            }
        }

        //
        // Checking all 3 "diagonal" columns that are at their minimum height in their left part and maximum height in the right part
        //
        for (int h = 6 ; h >= 4 ; h--)
        {
            // Checking all groups to see if they are diagonally winners
            for (int v = 1 ; v <= 4 ; v++)
            {
                // Checking if four adjacent diagonal cells are winners
                if ((connectFour [h] [v] == userType) && ((connectFour [h - 1] [v + 1] == userType)) &&
                        ((connectFour [h - 2] [v + 2] == userType)) && ((connectFour [h - 3] [v + 3] == userType)))
                {
                    return true;
                }
            }
        }
        return false;                   // No winner found
    }


    public static void main (String[] args)
    {
        c = new Console (40, 150);

        // Calling method for introductory screen
        introDisplay ();

        // Drawing Connect-Four Board
        c.setColor (Color.blue);
        c.fillRoundRect (50, 50, 770, 660, 70, 70);

        // Drawing slots for chip placement
        c.setColor (Color.white);
        drawChipSlot (90, 90);

        for (int i = 0 ; i < 21 ; i++)  // 42 times to drop the chip in the slots, alternating between user and computer
        {
            // User's Play
            usersTurn ();
            if (isWinner (1))               // Checking if winner for the player
            {
                // Announce user as a winner
                c.setColor (Color.magenta);
                c.drawString ("Congratulations! You have won!", 850, 70);
                c.drawString ("Please play again soon!", 850, 90);
                break;
            }

            // Computer's Play
            computersTurn ();
            if (isWinner (2))               // Checking if winner for the computer
            {
                // Announce computer as a winner
                c.setColor (Color.magenta);
                c.drawString ("Game Over!, the computer has achieved victory", 850, 70);
                c.drawString ("Please play again soon!", 850, 90);
                break;
            }
        }
        if (isWinner (1) == false && isWinner (2) == false)             // All the slots are filled and no winner, game is a draw
        {
            c.drawString ("Game is a draw!", 850, 70);
        }
    }
}


