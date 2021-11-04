import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

//we need to counteract starvation
public class PebbleGame {

        Player[] playerList;
        Thread[] threadList;

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

    public static void  start_game(){//if the under eneters e then the program must exit.
        int numPlayers;//number of players
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        setBagPairs();
        System.out.println("you will be asked to enter the number of players and then for the location");
        System.out.println("of three files in turn containing comma separated inger values for the pebble weights.");
        System.out.println("the integer must be strictly positive.");
        System.out.println("the game will then be simulated,and output written to file in the directory.");
        System.out.println("type: X to exit and start over.");
        System.out.println("");
        while (true){//loops until use exits or enters correct information so the program can proceed
            System.out.println("Please enter the number of players:");
            numPlayers = Integer.parseInt(System.console().readLine());
            int totalPebbles = 0;
            try {
                if (numPlayers > 0) {
                    String file_input;
                    for (int i = 1; i <= 3; i++) {
                        System.out.println("Please enter location of bag number " + i + " to load:");
                        file_input = System.console().readLine();//verify data type
                        if (Objects.equals(file_input, "X")||(Objects.equals(file_input, "E"))){
                            if((Objects.equals(file_input, "E"))){
                                System.exit(0);
                            }
                            break;//exit the loop
                        } else { //check if the input is valid using try catch block
                            try{
                                pebbles.addAll(read_csv(file_input));//populates the pebbles temp array
                                if(pebbles.size()<10){//if some files contains less than 10 pebbles each then throw error as its below spec
                                    throw new InvalidfileExeption("file number" + i + " has to have at least 10 pebbles");
                                }else{
                                    switch (i) {//uses the for loop to identify which bag is being added to
                                        case 1 -> {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagX.setPebbles(pebbles);
                                            }
                                        case 2 -> {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagY.setPebbles(pebbles);
                                            }
                                        case 3 -> {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagZ.setPebbles(pebbles);
                                        }
                                        default -> {
                                            pebbles.clear();
                                        }
                                    }
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
                System.out.println(e.getMessage());//shows error
            }
        }
    }

    public static ArrayList<Integer> read_csv (String filename) throws IOException,InvalidfileExeption {//validate file name when calling the method
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        String StringOfNumbers;
        Scanner scanner;
        int errorCount = 0;
        int temporaryint  = 0;
        String text = "";
        String ErrorString ="We have detected problems inside the file as follows: ";
        try {
            File file = new File(filename); // java.io.File(source file with text)
            scanner = new Scanner(file);    // java.util.Scanner
            while(scanner.hasNextLine()){//this loop is going to be used to read a text file (each line)
                StringOfNumbers = scanner.nextLine();
                int x = 0;
                StringBuilder CurrentString = new StringBuilder();
                while(x < StringOfNumbers.length() + 1) {//might need fixing
                    if(!String.valueOf(StringOfNumbers.charAt(x)).equals(",")){//fix
                        if(Character.isDigit(StringOfNumbers.charAt(x))){//check if it's a digit
                            CurrentString.append(StringOfNumbers.charAt(x));
                        }else{
                            errorCount++;
                            ErrorString=ErrorString.concat("type error on line: " + x +" ," );
                            //throw error as it's not a character
                        }
                    }else{
                        if(CurrentString.toString().equals("")){
                            ErrorString=ErrorString.concat("empty entry on line: " + x +" ," );
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
                    CurrentString.append(StringOfNumbers.charAt(x));
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
            System.out.println("We Could not find the file and : " + e);
            throw new InvalidfileExeption("we could not find the file at this file path");
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
            for (int i = 1; i <= NumberOfIterations; i++) {
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
            if(JustDrawTen){//sets the player's hand
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
            for (int i = 1; i <= NumberOfIterations; i++) {
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
                        TenPebbles[i] = bagX.drawPebble();
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
            for (int i = 1; i <= NumberOfIterations; i++) {
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
                        TenPebbles[i] = bagX.drawPebble();
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
        threadList = new Thread[numPlayers-1];
        //creates each player object and thread for the specified number of players
        PebbleGame DeafultPebblegame = new PebbleGame();//create a instance of a pebblegame class
        for(int i = 0; i <= numPlayers-1; i++){
            DeafultPebblegame.playerList[i] = new Player(1000+i);//create a instanc eof a player in the instance of the pebbel game class
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
            return RandomBag;
        }

        public int getTotalWeight() {return totalWeight;}

        public void GenerateRandomChoice(){
            this.RandomBag = rand.nextInt(3);
        }

        public void setTotalWeight(int totalWeight) {this.totalWeight = totalWeight;}

        public int getPlayerID() {
            return playerID;
        }

        public int[] getPebbles() {
            return pebbles;
        }

        public void lastBagDrawn(String Bag){
            this.LastBagDrawn = Bag;
        }

        public void setPebbles(int[] pebbles) {
            this.pebbles = pebbles;
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
