public class FindTreasure {
    private boolean getItem;

    private boolean searched;

    private String[] treasureList = new String[4];;

    private String item = "jim" ;

    public FindTreasure(){

        getItem = false;
        searched = false;
        randItem();
    }

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

    public void setSearched(){
        this.searched = false;
    }
    public void setGetItem(){this.getItem = false;}

    public void setItem(){
        randItem();
    }

    public boolean checkItems(String x){
        for (int i = 0; i < treasureList.length; i ++){
            if (x.equals(treasureList[i])){
                return true;
            }
        }
        return false;
    }


    public boolean hasItemInTreasureList(String item) {
        for (String tmpItem : treasureList) {
            if (item.equals(tmpItem)) {
                // early return
                return true;
            }
        }

        return false;
    }

    public void addStuff(){

        if (searched == false){
            if(!(item.equals("Dust"))){
                addItem(item);}
        }
        for (int i = 0; i < treasureList.length; i++){
            System.out.println(treasureList[i]);
        }


    }
    private int emptyPositionInTreasurelist() {
        for (int i = 0; i < treasureList.length; i++) {
            if (treasureList[i] == (null)) {
                System.out.println(i);
                return i;

            }
        }

        return -1;
    }

    private void addItem(String item) {

        int idx = emptyPositionInTreasurelist();
        treasureList[idx] = item;

    }


    public String enterTreasure(){
        getItem = hasItemInTreasureList(item);


        if (searched == true){
            return "You have already searched for treasure!";
        }
        else if (getItem == true){
            searched = true;
            return "You already found this item previously";

        } else if( item.equals("Dust")){
            searched = true;
            return "Your found useless dust!";
        } else{
            searched = true;
            return "You found " + item;

        }
    }
}

