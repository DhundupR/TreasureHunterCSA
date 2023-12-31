/**
 * The Town Class is where it all happens.
 * The Town is designed to manage all the things a Hunter can do in town.
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */

public class Town {
    // instance variables
    private Hunter hunter;
    private Shop shop;
    private Terrain terrain;
    private String printMessage;
    private boolean toughTown;

    private FindTreasure treasure;
    private String mode;

    private DigTreasure dig;


    /**
     * The Town Constructor takes in a shop and the surrounding terrain, but leaves the hunter as null until one arrives.
     *
     * @param shop The town's shoppe.
     * @param toughness The surrounding terrain.
     */
    public Town(Shop shop, double toughness, FindTreasure treasure,String mode) {
        this.treasure = treasure;
        this.shop = shop;
        this.terrain = getNewTerrain();
        this.mode=mode;
        DigTreasure dig = new DigTreasure(hunter);
        this.dig = dig;




        // the hunter gets set using the hunterArrives method, which
        // gets called from a client class
        hunter = null;

        printMessage = "";

        // higher toughness = more likely to be a tough town
        toughTown = (Math.random() < toughness);
    }

    public String getLatestNews() {
        return printMessage;
    }

    /**
     * Assigns an object to the Hunter in town.
     *
     * @param hunter The arriving Hunter.
     */
    public void hunterArrives(Hunter hunter) {
        this.hunter = hunter;
        printMessage = Colors.CYAN + "Welcome to town, " + Colors.BLUE + hunter.getHunterName() +   Colors.CYAN + "." + Colors.RESET ;

        if (toughTown) {
            printMessage += Colors.RED + "\nIt's pretty rough around here, so watch yourself." + Colors.RESET;
        } else {
            printMessage += Colors.GREEN + "\nWe're just a sleepy little town with mild mannered folk." + Colors.RESET;
        }
    }

    /**
     * Handles the action of the Hunter leaving the town.
     *
     * @return true if the Hunter was able to leave town.
     */
    public boolean leaveTown() {
        boolean canLeaveTown = terrain.canCrossTerrain(hunter);


        if (canLeaveTown) {
            treasure.setSearched();
            treasure.setGetItem();
            treasure.randItem();
            dig.setHasDug();

            String item = terrain.getNeededItem();
            printMessage = "You used your " + item + " to cross the " + terrain.getTerrainName() + ".";
            if (checkItemBreak()) {
                hunter.removeItemFromKit(item);
                if (item.equals("horse")) {
                    printMessage += Colors.RED + "\nUnfortunately, your " + item + " decided to fire you." + Colors.RESET;
                } else if (item.equals("water")) {
                    printMessage += Colors.RED + "\nUnfortunately, you ran out of " + item + "." + Colors.RESET;
                } else if (item.equals("boots")) {
                    printMessage += Colors.RED + "\nUnfortunately, your " + item + " fell apart." + Colors.RESET;
                } else if (item.equals("rope")) {
                    printMessage += Colors.RED + "\nUnfortunately, your " + item + " ripped in half." + Colors.RESET;
                } else if (item.equals("boat")) {
                    printMessage += Colors.RED + "\nUnfortunately, your " + item + " has a leakage." + Colors.RESET;
                } else {
                    printMessage += Colors.RED + "\nUnfortunately, your " + item + " broke." + Colors.RESET;
                }
            }



            return true;
        }


        printMessage = "You can't leave town, " + hunter.getHunterName() + ". You don't have a " + terrain.getNeededItem() + ".";
        return false;
    }

    /**
     * Handles calling the enter method on shop whenever the user wants to access the shop.
     *
     * @param choice If the user wants to buy or sell items at the shop.
     */
    public void enterShop(String choice) {
        shop.enter(hunter, choice);
        printMessage = "You left the shop";
    }



    public void enterTreasure() {
        printMessage =  treasure.enterTreasure();
        treasure.addStuff();
        treasure.setSearchedTrue();



    }

    public void digTreasure() {
        printMessage =  dig.tryDig(hunter);




    }

    /**
     * Gives the hunter a chance to fight for some gold.<p>
     * The chances of finding a fight and winning the gold are based on the toughness of the town.<p>
     * The tougher the town, the easier it is to find a fight, and the harder it is to win one.
     */
    public void lookForTrouble() {
        double noTroubleChance;
        if (toughTown) {
            noTroubleChance = 0.66;
        } else {
            noTroubleChance = 0.33;
        }

        if (Math.random() > noTroubleChance) {
            printMessage = "You couldn't find any trouble";
        } else {
            printMessage = Colors.RED + "You want trouble, stranger!  You got it!\nOof! Umph! Ow!\n"+Colors.RESET;
            int goldDiff = (int) (Math.random() * 10) + 1;

            if(hunter.hasItemInKit("sword")) {
                printMessage += "After seeing your sword, your opponent decide to surrender";
                printMessage += "\nYou won the brawl and receive " + Colors.YELLOW + goldDiff + Colors.RESET + " gold.";
                hunter.changeGold(goldDiff);
            }

            else if (Math.random() > noTroubleChance) {
                printMessage += "Okay, stranger! You proved yer mettle. Here, take my gold.";
                printMessage += "\nYou won the brawl and receive " + Colors.YELLOW + goldDiff + Colors.RESET + " gold.";
                hunter.changeGold(goldDiff);
            }
            else {
                printMessage += Colors.RED +"That'll teach you to go lookin' fer trouble in MY town! Now pay up!"+Colors.RESET;;
                printMessage += Colors.RED + "\nYou lost the brawl and pay " + goldDiff + " gold." + Colors.RESET ;

                hunter.changeGold(-goldDiff);


            }
        }
    }

    public String toString() {
        return  Colors.CYAN +"This nice little town is surrounded by " + terrain.getTerrainName() + "." + Colors.RESET;
    }

    /**
     * Determines the surrounding terrain for a town, and the item needed in order to cross that terrain.
     *
     * @return A Terrain object.
     */
    private Terrain getNewTerrain() {
        int rnd = (int)(Math.random()*14)+1;
        if (rnd < 2) {
            return new Terrain("Mountains", "Rope");
        } else if (rnd < 4) {
            return new Terrain("Ocean", "Boat");
        } else if (rnd < 8) {
            return new Terrain("Plains", "Horse");
        } else if (rnd < 10) {
            return new Terrain("Desert", "Water");
        } else if(rnd<.12) {
            return new Terrain("Jungle", "Machete");
        } else {
            return new Terrain("Marsh", "Boots");
        }
    }

    /**
     * Determines whether a used item has broken.
     *
     * @return true if the item broke.
     */
    private boolean checkItemBreak() {
        if (mode.equals("e")){
            return false;
        }
        double rand = Math.random();
        return (rand < 0.5);
    }
}