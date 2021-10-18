import java.util.ArrayList;
import java.util.Random;

public class game {
    int numPlayers;

    public enum blackBags{
        bagX,
        bagY,
        bagZ;
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        Random rand = new Random();
        blackBags() {}
        public ArrayList<Integer> getPebbles() {return pebbles;}
        public void setPebbles(ArrayList<Integer> pebbles) {this.pebbles = pebbles;}
        public int drawPebble() {
            if (this.getPebbles().size() == 0) {
                //return -1 if empty list
                return -1;
            } else {
                int index = rand.nextInt(this.getPebbles().size());
                int pebbleWeight = this.getPebbles().get(index);
                this.getPebbles().remove(index);
                return pebbleWeight;
            }
        }

    }
    public enum whiteBags{
        bagA,
        bagB,
        bagC;
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        Random rand = new Random();
        whiteBags(){}
        public ArrayList<Integer> getPebbles() {return pebbles;}
        public void setPebbles(ArrayList<Integer> pebbles) {this.pebbles = pebbles;}
        //public discardPebble(whiteBags thisBag)

    }



}
