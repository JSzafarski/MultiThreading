import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

//we need to counteract starvation
public class PebbleGame {

    public static void main(String[] args){//this can be confusing as its instance of a instance of pebble game that creates instance of players
        PebbleGame game = new PebbleGame();
        game.start_game();
    }

     Player[] playerList = new Player[100];
     Thread[] threadList = new Thread[100];

    //instantiating black bags
    static Bag bagX = new Bag("BLACK");
    static Bag bagY = new Bag("BLACK");
    static Bag bagZ = new Bag("BLACK");

    //instantiating white bags

    static Bag bagA = new Bag("WHITE");
    static Bag bagB = new Bag("WHITE");
    static Bag bagC = new Bag("WHITE");

    //method for setting bag pairs of bags
    public static void setBagPairs() {
        bagX.setBagPair(bagA);
        bagY.setBagPair(bagB);
        bagZ.setBagPair(bagC);
        bagA.setBagPair(bagX);
        bagB.setBagPair(bagY);
        bagC.setBagPair(bagZ);
    }

    public void start_game(){//if the under enters e then the program must exit.
        int numPlayers;//number of players
        ArrayList<Integer> pebblesFromFile = new ArrayList<Integer>();
        setBagPairs();
        Scanner Scanner1 = new Scanner(System.in);
        System.out.println("you will be asked to enter the number of players and then for the location");
        System.out.println("of three files in turn containing comma separated integer values for the pebble weights.");
        System.out.println("the integer must be strictly positive.");
        System.out.println("the game will then be simulated,and output written to file in the directory.");
        System.out.println("type: X to exit and start over or E to to exit the system completely");
        System.out.println("");
        while (true){//loops until use exits or enters correct information so the program can proceed
            System.out.println("Please enter the number of players:");
            String input = Scanner1.nextLine();
            numPlayers = Integer.parseInt(input);
            int totalPebbles = 0;
            try {
                if (numPlayers > 0) {
                    String file_input;
                    for (int i = 1; i <= 3; i++) {
                        System.out.println("Please enter location of bag number " + i + " to load:");
                        file_input = Scanner1.nextLine();
                        if (Objects.equals(file_input, "X")||(Objects.equals(file_input, "E"))){
                            if((Objects.equals(file_input, "E"))){
                                System.exit(0);
                            }
                            break;//exit the loop
                        } else { //check if the input is valid using try catch block
                            try{
                                int s= 0;
                                while(s<=(read_csv(file_input)).size()-1){
                                    //populates the pebbles temp array
                                    pebblesFromFile.add(read_csv(file_input).get(s));
                                    s++;
                                }
                                if(pebblesFromFile.size()<10){//if some files contains less than 10 pebbles each then throw error as its below spec
                                    throw new InvalidfileExeption("file number" + i + " has to have at least 10 pebbles");
                                }else{
                                    switch (i) {//uses the for loop to identify which bag is being added to
                                        case 1 -> {
                                            totalPebbles = totalPebbles + pebblesFromFile.size();
                                            bagX.setPebbles(pebblesFromFile);
                                            }
                                        case 2 -> {
                                            totalPebbles = totalPebbles + pebblesFromFile.size();
                                            bagY.setPebbles(pebblesFromFile);
                                            }
                                        case 3 -> {
                                            totalPebbles = totalPebbles + pebblesFromFile.size();
                                            bagZ.setPebbles(pebblesFromFile);
                                        }
                                    }
                                    pebblesFromFile.clear();
                                }
                            } catch (InvalidfileExeption e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    if (totalPebbles < calculate_minPebbles(numPlayers)) {//not enough total pebbles to execute the game
                        System.out.println("there is not enough pebbles in total form each csv file,please increase that amount");
                    } else {
                        RunPlayers(numPlayers);//creates players for the game to execute
                        break;//all conditions have been met so the program my proceed
                    }
                } else {
                    System.out.println("invalid input ,enter again!");//shows error
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("w");//fix
                //shows error
            }
        }
    }

    public ArrayList<Integer> read_csv (String filename) throws IOException,InvalidfileExeption {//validate file name when calling the method
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        BufferedReader reader;
        String StringOfNumbers;
        int errorCount = 0;
        int temporaryint  = 0;
        int y_axis = 0;
        String ErrorString ="We have detected problems inside the file as follows: ";
        try {
            reader = new BufferedReader(new FileReader(filename));//grabs the text file specified
            //we may need to read  files and text and read accordingly(if statement)
            while((StringOfNumbers = reader.readLine()) != null){//this loop is going to be used to read a text file (each line)
                int x = 0;
                y_axis++;
                StringBuilder CurrentString = new StringBuilder();
                while(x <= StringOfNumbers.length()-1) {
                    if(!String.valueOf(StringOfNumbers.charAt(x)).equals(",") || !(x+1 > StringOfNumbers.length()-1)){
                        if(Character.isDigit(StringOfNumbers.charAt(x))){//check if it's a digit
                            if (x== StringOfNumbers.length()-1){//for the end of line
                                CurrentString.append(StringOfNumbers.charAt(x));

                            }else {
                                CurrentString.append(StringOfNumbers.charAt(x));
                            }
                        }else{
                            errorCount++;
                            ErrorString=ErrorString.concat("type error on line: " + x +" ," );
                            //throw error as it's not a character
                        }
                    }else{
                        if(CurrentString.toString().equals("")){
                            ErrorString=ErrorString.concat("empty entry on line: "+ y_axis +" and index: "+ x +" ," );
                            errorCount++;
                        }else{
                            temporaryint = Integer.parseInt(CurrentString.toString());
                            if(temporaryint>0){
                                pebbles.add(temporaryint);//adds the item to the list
                            }else{
                                ErrorString=ErrorString.concat("range error on line : " + x +" ," );
                                errorCount++;
                            }
                            CurrentString.setLength(0);
                        }
                        //save the number into an array and proceed gathering more numbers
                    }
                    //CurrentString.append(StringOfNumbers.charAt(x));
                    x++;
                }
            }
            if(errorCount>0){
                //throw an exception
                throw new InvalidfileExeption(ErrorString.concat("total errors:" + errorCount));
            }else{
                return pebbles;
            }
        } catch (IOException e) {
            throw new InvalidfileExeption("We Could not find the file and : " + e);
        }
    }

    public static int calculate_minPebbles(int players){
        return 11*players;
    }

    //add a queue for threads to avoid starvation.(or idk something to avoid starvation issues)

    public static synchronized void drawAndDiscardFromBagX(Player thisPlayer, boolean JustDrawTen) {//method that draws a pebble and then discards the pebble into the next bag in the discard queue will also refill a bag if found to be empty
        int replacementpebble = -1;
        int NumberOfIterations = 0;
        int[] TenPebbles = new int[10];
        if (JustDrawTen) {
            NumberOfIterations = 10;
        } else {
            NumberOfIterations = 1;
        }
            for (int i = 0; i <= NumberOfIterations-1; i++) {
                replacementpebble = bagX.drawPebble();
                if (replacementpebble == -1) {//when bag is empty
                    bagX.refillBag();
                    thisPlayer.GenerateRandomChoice();//randomly selects a new bag to draw from
                    if(thisPlayer.getRandomBag()==0){//attempt to draw from bag X
                        PebbleGame.drawAndDiscardFromBagX(thisPlayer,false);
                    }else if(thisPlayer.getRandomBag()==1){//attempt to draw from bag Y
                        PebbleGame.drawAndDiscardFromBagY(thisPlayer,false);
                    }else{//attempt to draw from bag Z
                        PebbleGame.drawAndDiscardFromBagZ(thisPlayer,false);
                    }
                } else {//the drawPebble method was successful
                    if (JustDrawTen) {//creates a list of 10 pebbles
                        TenPebbles[i] = bagX.drawPebble();
                    } else {//takes the drawn pebble and replaces a pebble in the player's hand which is discarded into the corresponding bag
                        bagX.getBagPair().discardPebble(thisPlayer.replacePebble(replacementpebble));
                        thisPlayer.lastBagDrawn("X");
                    }
                }
            }
            if(JustDrawTen){
                //sets the player's hand
                thisPlayer.setPebbles(TenPebbles);
            }
    }

    public static synchronized void drawAndDiscardFromBagY(Player thisPlayer, boolean JustDrawTen){
        int replacementpebble = -1;
        int NumberOfIterations = 0;
        int[] TenPebbles = new int[10];
        if (JustDrawTen) {
            NumberOfIterations = 10;
        } else {
            NumberOfIterations = 1;
        }
            for (int i = 0; i <= NumberOfIterations-1; i++) {
                replacementpebble = bagY.drawPebble();
                if (replacementpebble == -1) {//when bag is empty
                    bagY.refillBag();
                    thisPlayer.GenerateRandomChoice();//randomly selects a new bag to draw from
                    if(thisPlayer.getRandomBag()==0){//attempt to draw from bag X
                        PebbleGame.drawAndDiscardFromBagX(thisPlayer,false);
                    }else if(thisPlayer.getRandomBag()==1){//attempt to draw from bag Y
                        PebbleGame.drawAndDiscardFromBagY(thisPlayer,false);
                    }else{//attempt to draw from bag Z
                        PebbleGame.drawAndDiscardFromBagZ(thisPlayer,false);
                    }
                } else {//the drawPebble method was successful
                    if (JustDrawTen) {//creates a list of 10 pebbles
                        TenPebbles[i] = bagY.drawPebble();
                    } else {//takes the drawn pebble and replaces a pebble in the player's hand which is discarded into the corresponding bag
                        bagY.getBagPair().discardPebble(thisPlayer.replacePebble(replacementpebble));
                        thisPlayer.lastBagDrawn("Y");
                    }
                }
            }
            if(JustDrawTen){//sets the player's hand
                thisPlayer.setPebbles(TenPebbles);
            }
    }
    public static synchronized void drawAndDiscardFromBagZ(Player thisPlayer, boolean JustDrawTen){
        int replacementpebble = -1;
        int NumberOfIterations = 0;
        int[] TenPebbles = new int[10];

        if (JustDrawTen) {
            NumberOfIterations = 10;
        } else {
            NumberOfIterations = 1;
        }
            for (int i = 0; i <= NumberOfIterations-1; i++) {
                replacementpebble = bagZ.drawPebble();
                if (replacementpebble == -1) {//when bag is empty
                    bagZ.refillBag();
                    thisPlayer.GenerateRandomChoice();//randomly selects a new bag to draw from
                    if(thisPlayer.getRandomBag()==0){//attempt to draw from bag X
                        drawAndDiscardFromBagX(thisPlayer,false);
                    }else if(thisPlayer.getRandomBag()==1){//attempt to draw from bag Y
                        drawAndDiscardFromBagY(thisPlayer,false);
                    }else{//attempt to draw from bag Z
                        drawAndDiscardFromBagZ(thisPlayer,false);
                    }
                } else {//the drawPebble method was successful
                    if (JustDrawTen) {//creates a list of 10 pebbles
                        TenPebbles[i] = bagZ.drawPebble();
                    } else {//takes the drawn pebble and replaces a pebble in the player's hand which is then discarded into the corresponding bag
                        bagZ.getBagPair().discardPebble(thisPlayer.replacePebble(replacementpebble));
                        thisPlayer.lastBagDrawn("Z");
                    }
                }
            }
            if(JustDrawTen){//sets the player's hand
                thisPlayer.setPebbles(TenPebbles);
            }
    }

    public static synchronized void draw10(Player thisPlayer){//method fills a player's hand
        if(thisPlayer.RandomBag==1){//draw 10 from bag X
            PebbleGame.drawAndDiscardFromBagX(thisPlayer,true);
        }else if(thisPlayer.RandomBag==2){//draw 10 from bag Y
            PebbleGame.drawAndDiscardFromBagY(thisPlayer,true);
        }else{//draw 10 from bag Z
            PebbleGame.drawAndDiscardFromBagZ(thisPlayer,true);
        }
    }

    public void RunPlayers(int numPlayers){
        threadList = new Thread[numPlayers];
        //creates each player object and thread for the specified number of players
        PebbleGame DeafultPebblegame = new PebbleGame();//create a instance of a pebblegame class
        for(int i = 0; i <= numPlayers-1; i++){
            //DeafultPebblegame.Players.add(DeafultPebblegame.new Player(1000+i));
            DeafultPebblegame.playerList[i]  = DeafultPebblegame.new Player(1000+i);//create a instanc eof a player in the instance of the pebbel game class
            threadList[i] = new Playerthread(DeafultPebblegame.playerList[i]);//pass the instance of the pebblegame game class countaining the instance eof the player into the thread
            threadList[i].start();//pass the whole
        }
    }

     class Player {

        int RandomBag;
        int  playerID;
        int[] pebbles = new int[10];
        Random rand = new Random();
        int totalWeight;
        String LastBagDrawn;

        public int getRandomBag() {
            return this.RandomBag;
        }

        public int getTotalWeight() {return this.totalWeight;}

        public void GenerateRandomChoice(){
            this.RandomBag = rand.nextInt(3);
        }

        public void setTotalWeight(int totalWeight) {this.totalWeight = totalWeight;}

        public int getPlayerID() {
            return this.playerID;
        }

        public int[] getPebbles() {
            return this.pebbles;
        }

        public void lastBagDrawn(String Bag){
            this.LastBagDrawn = Bag;
        }

        public void setPebbles(int[] pebbles) {
            for (int i = 0;i<=9;i++){
                this.pebbles[i] = pebbles[i];
            }
        }

        public void updateWeight(int newPebble, int oldPebble){//true = add,false = remove
            this.setTotalWeight(this.getTotalWeight() - oldPebble + newPebble);
        }

        public Player(int playerID) {
            this.playerID = playerID;
        }

        //takes a new pebble randomly adds it to the players hand in place of another pebble and returns the old pebble

        public int replacePebble(int replacementPebble) {
            int index = rand.nextInt(this.getPebbles().length-1);
            int discardPebble = this.getPebbles()[index];
            this.getPebbles()[index] = replacementPebble;
            this.updateWeight(replacementPebble, discardPebble);
            return discardPebble;
        }

        //calculates the total weight of a player's hand
        public void calculateTotalWeight(){
            int totalWeight = 0;
            for (int i : this.getPebbles()) {
                totalWeight = totalWeight + i;
            }
            this.setTotalWeight(totalWeight);
        }

        public String getLastBagDrawn() {
            return LastBagDrawn;
        }

    }
}
