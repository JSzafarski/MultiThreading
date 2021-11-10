import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * This is the main class that Joins all other classes together to run the pebbles game successfully.
 * It contains the main run method so the gamer is able to execute upon startup of the java program.
 * its non-static as it has to contain an inner player class within therefore only certain methods are static.
 * it creates a singleton instance of itself to run correctly which is shown below.
 * @since v1.0
 * @author 690036000
 * @author
 */

public class PebbleGame {

    public static PebbleGame pebbleGame = new PebbleGame();//creates a static instance of PebbleGame
    public PebbleGame(){}
    public static synchronized PebbleGame getPebbleGame(){return pebbleGame;}
    //singleton declaration of the pebbleGame class instance required to run the program

    public static void main(String[] args){
        /**
         * @since v1.1
         * This method creates an instance of the Pebble Game class where the instances of players will be instantiated
         * This will execute upon startup to get things going.
         */
        getPebbleGame().start_game();//calls the method start_game() to proceed with execution
    }
    //insulating arrays responsible for storing players and the threads corresponding to each player
    static Player[] playerList = new Player[100];
    static Thread[] threadList = new Thread[100];

    //instantiating black bags
    static Bag bagX = new Bag();//black bags
    static Bag bagY = new Bag();
    static Bag bagZ = new Bag();

    //instantiating white bags
    static Bag bagA = new Bag();//white bags
    static Bag bagB = new Bag();
    static Bag bagC = new Bag();

    //method for setting bag pairs of bags
    public static void setBagPairs() {
        /**
         * @since v1.0
         *This is a static method that links up the different bags into corresponding pairs.
         *This allows more organised transfer of pebbles when refilling the bag.
         */
        bagX.setBagPair(bagA);//Allows player to discard the pebble into correct bags
        bagY.setBagPair(bagB);
        bagZ.setBagPair(bagC);
        bagA.setBagPair(bagX);
        bagB.setBagPair(bagY);
        bagC.setBagPair(bagZ);
    }


    public void start_game(){//if the under enters e then the program must exit.
        //not tested by junit4
        /**
         * @since v1.0
         *Fetches user input of players and files necessary to populate the corresponding bags
         *User is able to enter E or X at any time in the process to either exit the program or Start over
         *Uses console input.
         * doesn't contain parameters or return values as this is uses inputs and outputs of functions within itself
         */
        int numPlayers;//number of players
        ArrayList<Integer> pebblesFromFile = new ArrayList<Integer>();
        setBagPairs();//calls a method that manages the corresponding bag pairs for example bag an is paired with bag z
        Scanner Scanner1 = new Scanner(System.in);
        System.out.println("you will be asked to enter the number of players and then for the location");
        System.out.println("of three files in turn containing comma separated integer values for the pebble weights.");
        System.out.println("the integer must be strictly positive.");
        System.out.println("the game will then be simulated,and output written to file in the directory.");
        System.out.println("type: X to exit and start over or E to to exit the system completely");
        System.out.println("");
        while (true){//this is the most outer loop which repeats until the user exits or no errors occur
            try {
                boolean exeption = false;//for passing information to a conditional that some errors have occurred
                int totalPebbles = 0;//this value gets updated to the sum of pebbles found in all 3 files
                System.out.println("Please enter the number of players:");
                String input = Scanner1.nextLine();//uses the imported java scanner to read console input from the user
                if (Objects.equals(input, "X")||(Objects.equals(input, "E"))){
                    //those are the only characters that can help the user exit/restart the program from scratch
                    if((Objects.equals(input, "E"))){
                        System.exit(0);//exits the application fully
                    }
                }else{
                    numPlayers = Integer.parseInt(input);//attempts to convert the input to an integer which can throw exception so its enclosed ina try/catch block
                    if (numPlayers > 1 && numPlayers <=100) {//verify range of players
                        String file_input;
                        for (int i = 1; i <= 3; i++) {//will iterate 3 time since we have 3 bags to populate
                            System.out.println("Please enter location of bag number " + i + " to load:");
                            file_input = Scanner1.nextLine();//this time it scans for file name that the user provides in console
                            if (Objects.equals(file_input, "X") || (Objects.equals(file_input, "E"))) {
                                if ((Objects.equals(file_input, "E"))) {
                                    System.exit(0);
                                }
                                exeption = true;
                                break;//exit the loop
                            }
                            System.out.println("Reading file...");//help the user be patient and not thin the program has crashed since large file take time to process
                            try {
                                int csvArrayPos = 0;
                                while (csvArrayPos <= (read_csv(file_input)).size() - 1) {//reads the scv or text file
                                    //populates the pebbles temp array
                                    pebblesFromFile.add(read_csv(file_input).get(csvArrayPos));
                                    csvArrayPos++;
                                }
                                if (pebblesFromFile.size() < 10) {//if some files contains less than 10 pebbles each then throw error as its below spec
                                    throw new InvalidfileExeption("file number" + i + " has to have at least 10 pebbles");
                                } else {//uses a switch to populate each bag for each iteration
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
                            } catch (InvalidfileExeption | IOException e) {//catches posible exeptions related to file reading
                                exeption = true;
                                System.out.println(e.getMessage());
                                break;
                            }
                        }
                        if(!exeption){
                            if (totalPebbles < calculate_minPebbles(numPlayers)) {//not enough total pebbles to execute the game
                                System.out.println("there is not enough pebbles in total from each csv file,please increase that amount");
                            } else {
                                runPlayers(numPlayers);//creates players for the game to execute(the execution may proceed as no issues have been found)
                                break;
                            }
                        }
                    } else {
                        throw new NumberFormatException("player value out of range :(2-100)");
                    }
                }
            } catch (NumberFormatException  e) {
                System.out.println("input invalid ; reason: " + e.getMessage());
                //shows error
            }
        }
    }

    public ArrayList<Integer> read_csv (String filename) throws IOException,InvalidfileExeption {
        /**
         *@since v1.0
         *Accepts csv or txt files to read player data
         *returns an array list of the whole file that is being read
         *minimum length of file has to be 27 for it to contain 10 items(mini-max)
         *@return Returns an Integer array list of all the pebble weights that where read from the csv/txt file provided
         *@param  Receives a txt file in the form of a String which it then located and reads
         *@throws IOException since the may be issues finding/parsing the file
         *@throws InvalidfileExeption as there may be issues within the file structure that need to be addressed by the user to run correctly
         */
        ArrayList<Integer> pebbles = new ArrayList<Integer>();//array list that will store the pebbles read from the file
        BufferedReader reader;
        String stringOfNumbers;
        int errorCount = 0;//helps the user resolve issues when errors arise
        int temporaryInt;
        int y_axis = 0;//helps the user resolve issues when errors arise
        String errorString ="We have detected problems inside the file as follows: ";
        try {
            reader = new BufferedReader(new FileReader(filename));//grabs the text file specified
            StringBuilder currentString = new StringBuilder();
            while((stringOfNumbers = reader.readLine()) != null){//this loop is going to be used to read a text file (each line)
                int positionInArray = 0;//used to move along the x-axis of the file so alon the line of text
                while(positionInArray <= stringOfNumbers.length()-1) {//loops until the ned of line is reached
                    if(!String.valueOf(stringOfNumbers.charAt(positionInArray)).equals(",") || (positionInArray == stringOfNumbers.length()-1)){
                        //comma separates each number so if its hit then we know we have finished reading current number between two commas
                        if(!String.valueOf(stringOfNumbers.charAt(positionInArray)).equals(" ")){//spaces are allowed in csv files between the first comma and the next integer
                            if (Character.isDigit(stringOfNumbers.charAt(positionInArray))) {//check if it's a digit
                                currentString.append(stringOfNumbers.charAt(positionInArray));//concatenates the integers together by treating them like strings initially
                                if (positionInArray == stringOfNumbers.length() - 1) {
                                    if (currentString.toString().equals("")) {
                                        errorString = errorString.concat("empty entry on line: " + y_axis + " and index: " + positionInArray + " ,");
                                        errorCount++;
                                    } else {
                                        temporaryInt = Integer.parseInt(currentString.toString());//converts the string of integers to the integer type
                                        if (temporaryInt > 0) {//has to be a positive value
                                            pebbles.add(temporaryInt);//adds the item to the list
                                        } else {//throws type error
                                            errorString = errorString.concat("range error on line : " + positionInArray + " ,");
                                            errorCount++;
                                        }
                                    }
                                    break;
                                }
                            }else{
                                errorCount++;
                                errorString = errorString.concat("type error on line: " + positionInArray + " ,");//helps the user fix issues within the file
                            }
                        }else{
                            boolean legalSpaceFormat = true;
                            if(positionInArray-1>0 && positionInArray+1<stringOfNumbers.length()-1) {//check if the space resides in the legal boundaries in the csv file
                                if (!Character.isDigit(stringOfNumbers.charAt(positionInArray + 1)) && !String.valueOf(stringOfNumbers.charAt(positionInArray - 1)).equals(",")){
                                    legalSpaceFormat = false;
                                }
                            }else{
                                legalSpaceFormat = false;
                            }
                            if(!legalSpaceFormat){
                                errorCount++;
                                errorString=errorString.concat("Invalid spacing on line: "+ y_axis +" and index: "+ positionInArray +" ,");
                            }
                        }
                    }else{
                        if(currentString.toString().equals("")){
                            errorString=errorString.concat("empty entry on line: "+ y_axis +" and index: "+ positionInArray +" ," );
                            errorCount++;
                        }else{
                            temporaryInt = Integer.parseInt(currentString.toString());
                            if(temporaryInt>0){
                                pebbles.add(temporaryInt);//adds the item to the list
                            }else{
                                errorString=errorString.concat("range error on line : " + positionInArray +" ," );
                                errorCount++;
                            }
                            currentString.setLength(0);
                        }
                        //save the number into an array and proceed gathering more numbers
                    }
                    positionInArray++;
                }
                y_axis++;
            }
            if(errorCount>0){
                throw new InvalidfileExeption(errorString.concat("total errors:" + errorCount));
            }else{
                return pebbles;
            }
        } catch (IOException e) {
            throw new InvalidfileExeption("We Could not find the file and : " + e);
        }
    }

    public static int calculate_minPebbles(int players){
        /**
         * @since v1.1
         *determines the minimum number of pebbles required for the game to run error free
         * @param players : passes a player amount
         * @return a int value of the minimum number of pebbles required for the players
         */
        return 11*players;//needs at least 11 time the players to avoid mathematical problems with the game
    }



    public static synchronized void drawAndDiscardFromBagX(Player thisPlayer, boolean justDrawTen) {//the 3 bag methods are very similar except that they access different bags(thread safe)
        /**
         *@since v1.8
         * This method handles player draw and discard actions for the BagX and BagA respectively
         * the actions are preformed on static bag objects that also reside within this class (above)
         *this class preforms atomic actions where it draws and discards a pebble and draws a pebble simultaneously
         *If a bag is empty then the bag is refilled and player chooses another randoms bag which can tun out to be the same bag as originally
         *This class is for picking and discarding  a pebble from bag X and bag A respectively.
         *We have split the 3 bag drawAndDiscardFromBag methods after v1.8 since we wanted to improve the efficient of the program and eliminate the need for atomic integers
         * now we have each method for each bag synchronised so its up to 3x faster as before we had a single method with all 3 bag which was less efficient
         * @param thisPlayer of Player type is a player passed into the method which allows the method to access that players methods so pebble discarding and drawing can take place
         * @param justDrawTen ,boolean which tell the method if the player wants to pick first 10 pebbles which only happens at the start of the game
         */
        int replacementpebble = -1;
        int numberOfIterations = 0;
        int[] tenPebbles = new int[10];
        if (justDrawTen) {//check if just 10 pebbles want to be drawn from the bag or a draw discard action is to be performed
            numberOfIterations = 10;
        } else {
            numberOfIterations = 1;
        }
            for (int i = 0; i <= numberOfIterations-1; i++) {
                //iterates the amount depending on the amount pebbles to be taken out so 1 or 10
                replacementpebble = bagX.drawPebble();//takes a pebble out bag X
                if (replacementpebble == -1) {//when bag is empty
                    bagX.refillBag();//refills the bag instantly
                    thisPlayer.generateRandomChoice();//randomly selects a new bag to draw from
                    //calls the bag methods until a pebble is drawn successfully
                    if(thisPlayer.getRandomBag()==0){//attempt to draw from bag X
                        PebbleGame.drawAndDiscardFromBagX(thisPlayer,false);
                    }else if(thisPlayer.getRandomBag()==1){//attempt to draw from bag Y
                        PebbleGame.drawAndDiscardFromBagY(thisPlayer,false);
                    }else{//attempt to draw from bag Z
                        PebbleGame.drawAndDiscardFromBagZ(thisPlayer,false);
                    }
                } else {//the drawPebble method was successful
                    if (justDrawTen) {//creates a list of 10 pebbles
                        tenPebbles[i] = bagX.drawPebble();
                    } else {//takes the drawn pebble and replaces a pebble in the player's hand which is discarded into the corresponding bag
                        bagX.getBagPair().discardPebble(thisPlayer.replacePebble(replacementpebble));
                        thisPlayer.setLastBagDrawn("X");
                    }
                }
            }
            if(justDrawTen){
                //sets the player's hand
                thisPlayer.setPebbles(tenPebbles);
            }
    }

    public static synchronized void drawAndDiscardFromBagY(Player thisPlayer, boolean justDrawTen){//thread safe method
        /**
         *@since v1.8
         * This method handles player draw and discard actions for the BagY and BagB respectively
         * the actions are preformed on static bag objects that also reside within this class (above)
         *this class preforms atomic actions where it draws and discards a pebble and draws a pebble simultaneously
         *If a bag is empty then the bag is refilled and player chooses another randoms bag which can tun out to be the same bag as originally
         *This class is for picking and discarding  a pebble from bag X and bag A respectively.
         *We have split the 3 bag drawAndDiscardFromBag methods after v1.8 since we wanted to improve the efficient of the program and eliminate the need for atomic integers
         * now we have each method for each bag synchronised so its up to 3x faster as before we had a single method with all 3 bag which was less efficient
         * @param thisPlayer of Player type is a player passed into the method which allows the method to access that players methods so pebble discarding and drawing can take place
         * @param justDrawTen ,boolean which tell the method if the player wants to pick first 10 pebbles which only happens at the start of the game
         */
        int replacementpebble = -1;
        int numberOfIterations = 0;
        int[] tenPebbles = new int[10];
        if (justDrawTen) {
            numberOfIterations = 10;
        } else {
            numberOfIterations = 1;
        }
            for (int i = 0; i <= numberOfIterations-1; i++) {
                replacementpebble = bagY.drawPebble();
                if (replacementpebble == -1) {//when bag is empty
                    bagY.refillBag();
                    thisPlayer.generateRandomChoice();//randomly selects a new bag to draw from
                    //calls the bag methods until a pebble is drawn successfully
                    if(thisPlayer.getRandomBag()==0){//attempt to draw from bag X
                        PebbleGame.drawAndDiscardFromBagX(thisPlayer,false);
                    }else if(thisPlayer.getRandomBag()==1){//attempt to draw from bag Y
                        PebbleGame.drawAndDiscardFromBagY(thisPlayer,false);
                    }else{//attempt to draw from bag Z
                        PebbleGame.drawAndDiscardFromBagZ(thisPlayer,false);
                    }
                } else {//the drawPebble method was successful
                    if (justDrawTen) {//creates a list of 10 pebbles
                        tenPebbles[i] = bagY.drawPebble();
                    } else {//takes the drawn pebble and replaces a pebble in the player's hand which is discarded into the corresponding bag
                        bagY.getBagPair().discardPebble(thisPlayer.replacePebble(replacementpebble));
                        thisPlayer.setLastBagDrawn("Y");
                    }
                }
            }
            if(justDrawTen){//sets the player's hand
                thisPlayer.setPebbles(tenPebbles);
            }
    }
    public static synchronized void drawAndDiscardFromBagZ(Player thisPlayer, boolean justDrawTen){//thread safe method
        /**
         *@since v1.8
         * This method handles player draw and discard actions for the BagZ and Bag C respectively
         * the actions are preformed on static bag objects that also reside within this class (above)
         *this class preforms atomic actions where it draws and discards a pebble and draws a pebble simultaneously
         *If a bag is empty then the bag is refilled and player chooses another randoms bag which can tun out to be the same bag as originally
         *This class is for picking and discarding  a pebble from bag X and bag A respectively.
         *We have split the 3 bag drawAndDiscardFromBag methods after v1.8 since we wanted to improve the efficient of the program and eliminate the need for atomic integers
         * now we have each method for each bag synchronised so its up to 3x faster as before we had a single method with all 3 bag which was less efficient
         * @param thisPlayer of Player type is a player passed into the method which allows the method to access that players methods so pebble discarding and drawing can take place
         * @param justDrawTen ,boolean which tell the method if the player wants to pick first 10 pebbles which only happens at the start of the game
         */
        int replacementpebble = -1;
        int numberOfIterations = 0;
        int[] tenPebbles = new int[10];

        if (justDrawTen) {
            numberOfIterations = 10;
        } else {
            numberOfIterations = 1;
        }
            for (int i = 0; i <= numberOfIterations-1; i++) {
                replacementpebble = bagZ.drawPebble();
                if (replacementpebble == -1) {//when bag is empty
                    bagZ.refillBag();
                    thisPlayer.generateRandomChoice();//randomly selects a new bag to draw from
                    //calls the bag methods until a pebble is drawn successfully
                    if(thisPlayer.getRandomBag()==0){//attempt to draw from bag X
                        drawAndDiscardFromBagX(thisPlayer,false);
                    }else if(thisPlayer.getRandomBag()==1){//attempt to draw from bag Y
                        drawAndDiscardFromBagY(thisPlayer,false);
                    }else{//attempt to draw from bag Z
                        drawAndDiscardFromBagZ(thisPlayer,false);
                    }
                } else {//the drawPebble method was successful
                    if (justDrawTen) {//creates a list of 10 pebbles
                        tenPebbles[i] = bagZ.drawPebble();
                    } else {//takes the drawn pebble and replaces a pebble in the player's hand which is then discarded into the corresponding bag
                        bagZ.getBagPair().discardPebble(thisPlayer.replacePebble(replacementpebble));
                        thisPlayer.setLastBagDrawn("Z");
                    }
                }
            }
            if(justDrawTen){//sets the player's hand
                thisPlayer.setPebbles(tenPebbles);
            }
    }

    public static synchronized void draw10(Player thisPlayer){//method fills a player's hand
        /**
         *@since V1.7
         *@param thisPlayer ,The player object is passed into this method, and it then checks players random bag
         *       then it calls the corresponding one of the 3 draw/discard bag methods to pick first 10 pebbles
         */
        if(thisPlayer.getRandomBag()==1){//draw 10 from bag X
            drawAndDiscardFromBagX(thisPlayer,true);
        }else if(thisPlayer.getRandomBag()==2){//draw 10 from bag Y
            drawAndDiscardFromBagY(thisPlayer,true);
        }else{//draw 10 from bag Z
            drawAndDiscardFromBagZ(thisPlayer,true);
        }
    }

    public void runPlayers(int numPlayers){//not tested by junit4
        /**
         *This method created thread objects which contain the run() methods and each thread is paired to a player instance by passing it into a thread
         *playerThread.start() is in another loop to reduce the overhead since by the time some players get to play the game we can get winning threads
         *@since v1.9
         *@param numPlayers is the number of player that the user has previous entered which will help to define the amount of threads too
         */
        //creates each player object and thread for the specified number of players
        threadList = new Thread[numPlayers];//threads are created depending on the number of player the user has chosen
        for(int i = 0; i <= numPlayers-1; i++){
            playerList[i]  = getPebbleGame().new Player(1000+i);//create an instance eof a player in the instance of the pebble game class
            threadList[i] = new PlayerThread(playerList[i]);//pass the instance of the pebbles game class containing the instance of the player into the thread
        }
        System.out.println("Running the game please check output files after a player/s win...");//informs the user
        for (Thread playerThread : threadList) {
            //second for loop created to start threads separately from their creation with less overhead between each thread start (overhead involved in creating threads) in order to lessen starvation
            playerThread.start();
        }
    }

     class Player {
         /**
          *This is a nested class which is an inner class, and it's a non-static nested class.
          *Allows to an instance of the player within the PebbleGame class.
          *uses getters and setters to access private class variables.
          *It used to be a separate class, but we have integrated it as an inner class as the specification requires this
          *@since V2.0
          */
        private int randomBag;
        private int  playerID;
        private int[] pebbles = new int[10];
        Random rand = new Random();
        private int totalWeight;
        private String lastBagDrawn;

        public int getRandomBag() {
            /**
             * @since v2.0
             * @return int which is the corresponding random bag the player has picked at a given point in time
             */
            return this.randomBag;
        }

        public int getTotalWeight() {
            /**
             * @since v2.0
             * @return int which is the total weight of the players pebbles hand
             */
            return this.totalWeight;}

        public void generateRandomChoice(){
            /**
             * @since v2.0
             * //generates a random integer value from 0-2 which is interpreted as either bagX,Y,Z
             * @retun int random value
             */
            this.randomBag = rand.nextInt(3);
        }

        private void setTotalWeight(int totalWeight){
            /**
             * @since v2.0
             * @param totalWeight take the total calculated weight of the player hand and stores it in a private variable
             */
            this.totalWeight = totalWeight;
        }

        public int getPlayerID() {
            /**
             * @since v2.0
             * @return int ,returns integer value representing the ID of each player instance
             */
            return this.playerID;
        }

        public int[] getPebbles() {
            /**
             * @since v2.0
             * @return int[],an array of pebbles that stores the 10 pebbles representing the players hand
             */
            return this.pebbles;
        }

        public void setLastBagDrawn(String Bag){
            /**
             * @since v2.0
             * @param Bag is a string that represent the bag letter which was the last bag the player picked which is used in the file outputs to keep track of the game
             */
            this.lastBagDrawn = Bag;
        }

        public void setPebbles(int[] pebbles) {//populates the array representing players hand with the items of array passed to that player
            /**
             * @since v2.0
             * @param pebbles is the pebble array for initially setting up the 10 pebbles
             */
            for (int i = 0;i<=9;i++){//iterates throughout the array as since assignment would result in by reference assignment not by value.
                this.pebbles[i] = pebbles[i];
            }
            this.calculateTotalWeight();
        }

        private void updateWeight(int newPebble, int oldPebble){//private as it only used by the player during run-time
            /**
             * @since v2.0
             * @param newPebble ,is an integer that tells the method which pebble has been ust picked up
             * @param oldPebble ,is an integer that tells the method which pebble has been discarded
             */
            this.setTotalWeight(this.getTotalWeight() - oldPebble + newPebble);
            //much more efficient than iterating the whole array each time ; its time complexity is 0(1) instead of O(K)
        }

        public Player(int playerID) {
            /**
             * @since v2.0
             * @param playerID ,is an intger that is used for constructing the instance eof the player class
             */
            this.playerID = playerID;
        }

        public int replacePebble(int replacementPebble) {//takes a new pebble randomly adds it to the players hand in place of another pebble and returns the old pebble
            /**
             * @since v2.0
             * @param replacementPebble ,is an int that hold the pebble weight hat the player just had picked up
             * @return int ,which is a discard pebble that the user discarded atomically
             */
            int index = rand.nextInt(this.getPebbles().length-1);//random value
            int discardPebble = this.getPebbles()[index];
            this.getPebbles()[index] = replacementPebble;
            this.updateWeight(replacementPebble, discardPebble);
            return discardPebble;//return the pebble a player chose to discard into a white bag
        }

        private void calculateTotalWeight(){//calculates the total weight of a player's hand
            /**
             * @since v2.0
             * used at the start of instantiating the player when the player is given 10 initial pebbles
             */
            int totalWeight = 0;
            for (int i = 0;i<=9;i++){//iterates over the array and makes a cumulative sum ,this is only used once as then we use the efficient method updateWeight()
                totalWeight += this.pebbles[i] ;
            }
            this.setTotalWeight(totalWeight);
        }

        public String getLastBagDrawn() {
            /**
             * @since v2.0
             * stores the bag which has been last drawn for file output purposes
             */
            return lastBagDrawn;
        }

    }
}
