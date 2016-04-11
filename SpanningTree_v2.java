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

 public class SpanningTree_v2{

	public static void main(String[] args){
		
		String fileName = "input.txt";
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
				
				numOfSwitches = Integer.parseInt(lineArray[0]);
				System.out.println("------------------------------\nLine number: " + lineNum);
				System.out.println("\n\nNumber of switches: " + numOfSwitches);

				//if random (connections are not provided) 
				//make set of connections
				if(lineArray[1].equals("R")){

					connectionPairs = randomConfig(numOfSwitches);
					System.out.println(connectionPairs);
				}
				
				//else not random, connections are provided
				else{

					//parse connections from strings to integer pairs
					int numOfConnections = lineArray.length - 1;
					System.out.println("Number of connectin pairs: " +  numOfConnections);
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
					switches[connectionPairs.getIndex(x)].addConnection(connectionPairs.getIndex(x+1));
					switches[connectionPairs.getIndex(x+1)].addConnection(connectionPairs.getIndex(x));


				}//end for
					
				lineNum++;
			}//end while

		}//end try

		catch(FileNotFoundException ex){
			System.out.println("No file named: " + fileName);
		}
		catch(IOException ex){
			System.out.println("Can't read file");
		}
	
	}//end main method
	
	//returns Integer arraylist of switch connections;
	private static ArrayList<Integer> randomConfig(int numOfSwitches){
		ArrayList<Integer> connections = new ArrayList<Integer>();
		return connections;
	}//end method randomConfig
	
	//converts String pair representation of connection into two integer values
	private static int[] parseStringPair(String pair){
		String[] pairDivided = pair.split("-");
		int[] connectionPair = {Integer.parseInt(pairDivided[0]), Integer.parseInt(pairDivided[1])};
		return connectionPair;

	}//end method parseStringPair
	
	//returns string arraylist of switch connections
	//** with additional connections if a switch was unused
	private static ArrayList<Integer> allSwitchCheck(ArrayList<Integer> connections, int numOfConnections){
		ArrayList<Boolean> connectionUsed = new ArrayList<Boolean>(numOfConnections);

		while(connectionUsed.contains(false)){
				

		}//end while

		return connections;
	}//end method allSwitchCheck

	 private static class SwitchConnection{
		
		int root;
		int distanceToRoot;
		int connectedBy;
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
		
		//returns port number remote value is attached to
		private int getPortNum(int value){
			return connectedTo.indexOf(value) + 1;
		}//end method getPortNum

	 }//end inner private class SwitchConnection
 }//end class SpanningTree_v2
