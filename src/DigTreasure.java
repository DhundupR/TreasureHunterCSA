public class DigTreasure {
    private boolean hasDug;
    private Hunter digger;


    public DigTreasure(Hunter digger){
        this.hasDug = false;
        this.digger = null;
    }

    public int randAmt() {
        int x = (int) (Math.random() * 20) + 1;
        return x;
    }
    public boolean hasShovel(Hunter digger){
        return digger.hasItemInKit("shovel");
    }



    public String tryDig(Hunter digger){
        if(!hasShovel(digger)){
            return "You do not have a shovel to dig with! ";
        }
         if(!hasDug) {
            digger = digger;
            int y = randAmt();
            double x = (int) (Math.random() * 100) + 1;
            if (x < 50) {
                digger.changeGold(y);
                hasDug = true;
                return "you dug up " + y + " gold!";

            } else {
                hasDug = true;
                return "You dug but only found dirt!";

            }
        }else{
            return "You have already dug for gold in this town!";
        }

    }

    public void setHasDug(){
        hasDug = false;
    }







}
