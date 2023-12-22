import java.util.Scanner;

/**
 * This class is responsible for controlling the Treasure Hunter game.<p>
 * It handles the display of the menu and the processing of the player's choices.<p>
 * It handles all the display based on the messages it receives from the Town object. <p>
 *
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */

public class TreasureHunter {
    // static variables
    private static final Scanner SCANNER = new Scanner(System.in);

    // instance variables
    private Town currentTown;
    private Hunter hunter;
    private String mode;

    private String item;

    private boolean getItem;

    private boolean searched;

    private String[] treasureList;
    private FindTreasure treasure;



    /**
     * Constructs the Treasure Hunter game.
     */
    public TreasureHunter() {

        // these will be initialized in the play method
        treasureList = new String[4];
        currentTown = null;
        hunter = null;
        mode = null;
        randItem();
        getItem = false;
        searched = false;
        FindTreasure treasure = new FindTreasure();
        this.treasure = treasure;


    }
    public void setMode(String newMode){
        mode=newMode;
    }


    /**
     * Starts the game; this is the only public method
     */

    public void randItem(){
        double x =  (int)(Math.random()*100)+1;
        if ( x < 20){
            item = "Crown";
        }else if ( x < 40){
            item = "Trophy";
        }else if ( x < 60){
            item = "Gem";
        }else {
            item = "Dust";
        }
    }



    public void play() {
        welcomePlayer();
        enterTown();
        showMenu();
    }

    /**
     * Creates a hunter object at the beginning of the game and populates the class member variable with it.
     */
    private void welcomePlayer() {
        System.out.println(Colors.CYAN + "Welcome to TREASURE HUNTER!");
        System.out.println("Going hunting for the"+ Colors.YELLOW+ " big treasure, "+ Colors.CYAN +"eh?");
        System.out.print("What's your name, Hunter? " + Colors.RESET);
        String name = SCANNER.nextLine().toLowerCase();

        // set hunter instance variable

        System.out.print("What mode?(Easy=e,Normal=n,Hard=h: ");
        String mode = SCANNER.nextLine().toLowerCase();
        setMode(mode);
        if (mode.equals("h")) {
            hunter = new Hunter(name, 5);
        }
        else if(mode.equals("e")){
            hunter = new Hunter(name, 20);

        }
        else if(mode.equals("test")){
            hunter = new Hunter(name, 2000006);
            hunter.buyItem("water",1);
            hunter.buyItem("rope",1);
            hunter.buyItem("machete",1);
            hunter.buyItem("horse",1);
            hunter.buyItem("boat",1);
            hunter.buyItem("boots",1);

        }
        else if(mode.equals("test")){
            hunter = new Hunter(name, 10);

        }

        else{
            hunter = new Hunter(name, 10);
        }
    }

    /**
     * Creates a new town and adds the Hunter to it.
     */
    private void enterTown() {


        double markdown;
        double toughness;

        if (mode.equals("h")) {
            // in hard mode, you get less money back when you sell items
            markdown = 0.25;

            // and the town is "tougher"
            toughness = 0.75;
        }
        else if(mode.equals("e")){
            markdown=1;
            toughness=0.10;
        }
        else{
            markdown = 0.5;
            toughness = 0.4;
        }

        // note that we don't need to access the Shop object
        // outside of this method, so it isn't necessary to store it as an instance
        // variable; we can leave it as a local variable
        Shop shop = new Shop(markdown, mode);


        // creating the new Town -- which we need to store as an instance
        // variable in this class, since we need to access the Town
        // object in other methods of this class
        currentTown = new Town(shop, toughness, treasure,mode);

        // calling the hunterArrives method, which takes the Hunter
        // as a parameter; note this also could have been done in the
        // constructor for Town, but this illustrates another way to associate
        // an object with an object of a different class
        currentTown.hunterArrives(hunter);

    }

    /**
     * Displays the menu and receives the choice from the user.<p>
     * The choice is sent to the processChoice() method for parsing.<p>
     * This method will loop until the user chooses to exit.
     */
    private void showMenu() {
        String choice = "";

        while (!choice.equals("x")) {

            System.out.println();
            System.out.println(currentTown.getLatestNews());
            if (hunter.getGold() < 0) {
                System.out.println("GAME OVER");
                break;
            }
            System.out.println("***");
            System.out.println(hunter);
            System.out.println(currentTown);
            System.out.println(Colors.PURPLE + "(B)uy something at the shop.");
            System.out.println("(S)ell something at the shop.");
            System.out.println("(M)ove on to a different town.");
            System.out.println("(L)ook for trouble!");
            System.out.println("(H)unt for treasure!");
            System.out.println("Give up the hunt and e(X)it." + Colors.RESET);
            System.out.println();
            System.out.print("What's your next move? ");
            choice = SCANNER.nextLine().toLowerCase();
            processChoice(choice);
        }
    }

    /**
     * Takes the choice received from the menu and calls the appropriate method to carry out the instructions.
     * @param choice The action to process.
     */
    private void processChoice(String choice) {
        if (choice.equals("b") || choice.equals("s")) {
            currentTown.enterShop(choice);
        } else if (choice.equals("m")) {
            if (currentTown.leaveTown()) {
                // This town is going away so print its news ahead of time.
                System.out.println(currentTown.getLatestNews());
                enterTown();
            }
        } else if (choice.equals("l")) {
            currentTown.lookForTrouble();
        } else if (choice.equals("x")) {
            System.out.println("Fare thee well, " + hunter.getHunterName() + "!");
        } else if (choice.equals("h"))  {
            currentTown.enterTreasure();




        } else {
            System.out.println("Yikes! That's an invalid option! Try again.");
        }
    }

    public boolean checkItems(String x){
        for (int i = 0; i < treasureList.length; i ++){
            if (x.equals(treasureList[i])){
                return true;
            }
        }
        return false;
    }

    private int emptyPositionInTreasurelist() {
        for (int i = 0; i < treasureList.length; i++) {
            if (treasureList[i] == null) {
                return i;
            }
        }

        return -1;
    }

    private void addItem(String item) {

        int idx = emptyPositionInTreasurelist();
        treasureList[idx] = item;

    }
}