/**
 *
 * Garrick Ranck
 * CSCI4311
 * Programming Assignment1
 * SpanningTree_v2.java
 *
 * Calculates the spanning tree algorithm for a set of switches
 * and connections provided or at random.
 *
 **/

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class SpanningTree_v2{

	public static void main(String[] args){
		
		String fileName = args[0];
		String line = null; //line read from buffer
		int lineNum = 1;
		ArrayList<Integer> connectionPairs;

		try{

			//open file
			FileReader fileReader = new FileReader(fileName);

			//place file in buffer
			BufferedReader bufferReader = new BufferedReader(fileReader);

			//read lines so long as buffer has lines to read
			while((line = bufferReader.readLine()) != null){

				int numOfSwitches; 

				//split line read based on white space
				String[] lineArray = line.split("\\s+");
				
				//first String value denotes number of switches
				numOfSwitches = Integer.parseInt(lineArray[0]);
				System.out.println("\n------------------LINE NUMBER: " + lineNum + "------------------");
				System.out.println("\n\nNumber of switches: " + numOfSwitches);

				//if random (connections are not provided) 
				//make set of connections
				if(lineArray[1].equals("R")){

					connectionPairs = randomConfig(numOfSwitches);
					System.out.println("Connection pairs: " + connectionPairs);
				}

				//else not random, connections are provided
				else{

					//parse connections from strings to integer pairs
					int numOfConnections = lineArray.length - 1;
					connectionPairs = new ArrayList<Integer>();

					for(int x = 1; x <= numOfConnections; x++){

						int[] connectionPair = parseStringPair(lineArray[x]);
						connectionPairs.add(connectionPair[0]);
						connectionPairs.add(connectionPair[1]);
					}//end for
					System.out.println("Connection pairs: " + connectionPairs);
				}

				//once connection pairs are made, create SwitchConnection object array
				// which holds switches 1 - x within array as index 0 - (x -1)
				SwitchConnection[] switches = new SwitchConnection[numOfSwitches];
				for(int x = 0; x < numOfSwitches; x++){

					switches[x] = new SwitchConnection(); //initialize all SwitchConnection objects
					switches[x].setRoot(x + 1); //initially set their roots to self
					switches[x].setDistanceToRoot(0);
					switches[x].setConnectedBy(x + 1);
					System.out.println("\nSwitch " + (x + 1) +  "\nRoot:" + switches[x].getRoot() +
							"\nDistance to Root: " + switches[x].getDistance() + "\nConnectedBy: " + switches[x].getConnection());
				}//end for

				//add each connection pair to corresponding switches in array
				for(int x = 0; x < connectionPairs.size(); x+=2){
					switches[connectionPairs.get(x) - 1].addConnection(connectionPairs.get(x + 1));
					switches[connectionPairs.get(x + 1) - 1].addConnection(connectionPairs.get(x));

				}//end for
				
				//make copy of SwitchConnection array to use for comparison
				//	when going through cycles for spanning algorithm
				SwitchConnection[] switchesTemp = new SwitchConnection[numOfSwitches];
				for(int x = 0; x < numOfSwitches; x++){
					switchesTemp[x] = new SwitchConnection();
					switchesTemp[x].copySwitch(switches[x]);
				}
				
				boolean change = true; //denotes whether a switch has altered their root or not
				int iteration = 1;

				//cycle through all switches until no switch finds a smaller root
				//signalling the end of the spanning algorithm
				while(change){
				change = false;

					//cycle through all switches
					for(int x = 0; x < numOfSwitches; x++){
						
						//if current switch doesn't have established route to true root
						//then check if path to true root can be made
						if(switches[x].getRoot() != 1){
							
							//cycle through all switches current switch is connected to
							// if it finds a switch with a smaller root
							// take their root
							for(int y = 0; y < switches[x].getConnectionList().size(); y++){
								
								if(switchesTemp[x].getRoot() > switches[switches[x].getConnectionList().get(y) -1].getRoot()){
									
									switchesTemp[x].setRoot(switches[switches[x].getConnectionList().get(y) -1].getRoot());
									switchesTemp[x].setDistanceToRoot(switches[switches[x].getConnectionList().get(y) -1].getDistance() +1);
									switchesTemp[x].setConnectedBy(switches[x].getConnectionList().get(y));
									change = true;
								}//end inner if	

							} //end inner for
						}//end if
					}//end for
					
					//after cycling through all switches, copy temp array data into
					//real switchConnection array
					// **only if change = true, meaning a change has been made
					if(change == true){
						System.out.println("\n-----Iteration #" + iteration + " of line " + lineNum);
						for(int x = 0; x < numOfSwitches; x++){
							switches[x].copySwitch(switchesTemp[x]);
							System.out.println("\nSwitch " + (x + 1) +  "\nRoot:" + switches[x].getRoot() +
							"\nDistance to Root: " + switches[x].getDistance() + "\nConnectedBy: " + (switches[x].getConnection()));

						}//end for
					}//end if
					
					//if all switches have been connected to root
					//		aka no change has been made
					if(change == false){
						System.out.println("\n******FINAL configuration for line " + lineNum);
						for(int x = 0; x < numOfSwitches; x++){
							
							//gets port number current switch is using for connection to next switch
							int portCurrent = switches[x].getConnectionList().indexOf(switches[x].getConnection()) + 1;
							//gets port number next switch is using for connection to current switch
							int portConnectedTo = switches[switches[x].getConnection() -1].getConnectionList().indexOf(x+1) + 1;
							System.out.println("\nSwitch " + (x + 1) +  "\nRoot:" + switches[x].getRoot() +
								"\nDistance to Root: " + switches[x].getDistance() + "\nConnectedBy: " + (switches[x].getConnection()));
							
							//don't consider root of all switches(it isn't "attached" to self)
							if(x != 0){
								System.out.println("Using p" + portCurrent + " of " + (x+1) + 
									" and p" + portConnectedTo + " of " + switches[x].getConnection());
							}//end if
						}//end for

					}//end if
					iteration++;
				}//end while loop
				

				lineNum++;
			}//end while

			bufferReader.close();

		}//end try

		catch(FileNotFoundException ex){
			System.out.println("No file named: " + fileName);
		}
		catch(IOException ex){
			System.out.println("Can't read file");
		}

	}//end main method

	//returns Integer arraylist of random switch connections
	//	*manually adds connections to switches if they are not randomly chosen
	private static ArrayList<Integer> randomConfig(int numOfSwitches){
		ArrayList<Integer> connections = new ArrayList<Integer>();
		Random random = new Random();
		int randomValue;
		
		//add 16 values, or 8 pairs of connections to arrayList connections
		for(int x = 0; x < 12; x++){
			randomValue = random.nextInt(numOfSwitches -1) + 1;

			//if second value in pair of connections
			//ensure it isn't same as first value
			if(x % 2 == 1){
				while(connections.get(x-1) == randomValue){
					randomValue = random.nextInt(numOfSwitches - 1) + 1;
				}//end while
			}//end if
			
			//System.out.println("Adding " + randomValue + " to connections");
			connections.add(randomValue);
		}//end for

		//check that all switches are used
		//if switch isn't used, add connection pair
		//		with it and random switch
		for(int x = 0; x < numOfSwitches; x++){
			if(connections.contains(x + 1) == false){
				connections.add(x + 1);
				randomValue = random.nextInt(numOfSwitches -1) + 1;
				while(connections.get(connections.size() - 1) == randomValue){
					randomValue = random.nextInt(numOfSwitches - 1) + 1;
				}//end while
				connections.add(randomValue);
			}//end if

		}//end for

		return connections;
	}//end method randomConfig

	//converts String pair representation of connection into two integer values
	private static int[] parseStringPair(String pair){
		String[] pairDivided = pair.split("-");
		int[] connectionPair = {Integer.parseInt(pairDivided[0]), Integer.parseInt(pairDivided[1])};
		return connectionPair;

	}//end method parseStringPair

	private static class SwitchConnection{

		int root; //switch that current switch regards as root
		int distanceToRoot; //number of edges to root
		int connectedBy; //switch connecting current switch to root

		//holds all remote switches that this.switch is connected to
		//		also used for port numbers. Index x = port number x + 1
		ArrayList<Integer> connectedTo;

		private SwitchConnection(){
			connectedTo = new ArrayList<Integer>();	

			root = 0;
			distanceToRoot = 0;
			connectedBy = 0;
		}//end constructor

		//adds connection to switch
		private void addConnection(int value){
			connectedTo.add(value);

		}//end method addConnection

		private void setRoot(int value){
			root = value;
		}

		private void setDistanceToRoot(int value){
			distanceToRoot = value;
		}

		private void setConnectedBy(int value){
			connectedBy = value;
		}

		private int getRoot(){
			return root;
		}

		private int getDistance(){
			return distanceToRoot;
		}

		private int getConnection(){
			return connectedBy;
		}

		private ArrayList<Integer> getConnectionList(){
			return connectedTo;
		}//end method getConnectionList

		//copies all data from passed in SwitchConnection object to this
		private void copySwitch(SwitchConnection switchConnection){
			this.root = switchConnection.getRoot();
			this.distanceToRoot = switchConnection.getDistance();
			this.connectedBy = switchConnection.getConnection();
			this.connectedTo = switchConnection.getConnectionList();

		}//end method copySwitch

	}//end inner private class SwitchConnection
}//end class SpanningTree_v2
