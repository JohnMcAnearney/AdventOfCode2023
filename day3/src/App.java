import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {
        //final String fileName = "day3/sample_day3_pt1.txt";
        final String fileName = "day3/input_day3.txt";

        try {
            TextFileReader reader = new TextFileReader(new BufferedReader(new FileReader(fileName)));
            ArrayList<String> test = reader.readTextFileToArrayList();
            //reader.printTextFileToConsole();
            Brain brain = new Brain(test);

            brain.make2dArray(test);

        } catch (FileNotFoundException e){
            System.err.println("file not found: " + e);
        }
    }
}

//so for going backwards I can just flip the array and match the regex
class TextFileReader {

    BufferedReader reader;

    public TextFileReader(BufferedReader reader){
        this.reader = reader;
    }
    
    public ArrayList<String> readTextFileToArrayList() {

        ArrayList<String> arrayListToReturn = new ArrayList<>();

        try {
			String line = this.reader.readLine();

			while (line != null) {
				arrayListToReturn.add(line);
				// read next line
				line = reader.readLine();
			}

			reader.close();
            return arrayListToReturn;

		} catch (IOException e) {
			e.printStackTrace();
            return arrayListToReturn;
		}
    }

    public void printTextFileToConsole(){

        try {
			String line = this.reader.readLine();

			while (line != null) {
				System.out.println(line);
				// read next line
				line = reader.readLine();
			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
}

class Brain {
    ArrayList<String> linesFromInputAsArray;

    public Brain(ArrayList<String> lineArrayList){
        this.linesFromInputAsArray = lineArrayList;
    }

    // public ArrayList<HashMap<String, String>> doStuff(ArrayList<String> arrayList){
    //     //convert the array of strings into something like this
    //     ArrayList<HashMap<String, String>> logicList = new ArrayList<>();
        
    //     /*
    //      * [
    //      *  [4,0],[6,1],[7,2],[1,5],[1,6],[4,7]
    //      *  [S,3]
    //      *  [3,2],[5,3],[6,6],[3,7],[3,8]
    //      *  [6,0],[1,1],[7,2],[S,3]
    //      * ]
    //      */

    //     //I want to go through each line, each character, if it is a number, then store it and it's index

    //     HashMap<String, String> map = new HashMap<>();
    //     for (int i = 0; i < arrayList.size(); i++) {
    //         for (int j = 0; j < arrayList.get(i).length(); j++) {
    //             if(Character.isDigit(arrayList.get(i).charAt(j))){
    //                 map.put(Character.toString(arrayList.get(i).charAt(j)), String.valueOf(j));
    //                 logicList.add(map);
    //             }
    //         }
    //     }
    //     return 
    // }


    private char[][] popualteArr(ArrayList<String> inputArrayList){
        
        char[][] arr = new char[inputArrayList.size()][inputArrayList.get(0).length()];
        
        for (int i = 0; i < inputArrayList.size(); i++) {
            for (int j = 0; j < inputArrayList.get(0).length(); j++) {
                arr[i][j] = inputArrayList.get(i).charAt(j);
            }
        }

        return arr;
    }

    //create a 2d array 
    public void make2dArray(ArrayList<String> inputArrayList){
        char[][] arr = popualteArr(inputArrayList);

        int width = arr.length;
        int height = arr[0].length;

        //int i_pointer = -1;   //pointer that will move around the eight possible directions
        //int j_pointer = -1;

        int totalCount = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.println("int i = " + i);
                System.out.println("int j = " + j);
                //if char is a special character
                try {
                    if(Character.isDigit(arr[i][j])){
                    //then check to -1 & +1 for j and -1 & +1 for i, if either is possible (NOTE: the data is nice and this consideration is not necessary - yay)

                    //Character.isDigit(arr[i][j]) && arr[i][j] != '.'

                    //if the number is 1 digit
                    if(!Character.isDigit(arr[i][j+1])){
                        //go around the digit
                        for (int i_pointer = -1; i_pointer < 2; i_pointer++) {
                            for (int j_pointer = -1; j_pointer < 2; j_pointer++) {
                                try{
                                    //if the character is a symbol
                                    if(!Character.isDigit(arr[i+i_pointer][j+j_pointer]) && arr[i+i_pointer][j+j_pointer] != '.'){
                                        totalCount += Integer.parseInt(Character.toString(arr[i][j]));
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    //do nothing, keep searching boy
                                }
                            }
                        }
                    }

                    //if the number is 2 digits
                    if(Character.isDigit(arr[i][j+1]) && !Character.isDigit(arr[i][j+2])){
                        //go around the first digit
                        for (int i_pointer = -1; i_pointer < 2; i_pointer++) {
                            for (int j_pointer = -1; j_pointer < 2; j_pointer++) {
                                try{
                                    //if the character is a symbol
                                    if(!Character.isDigit(arr[i+i_pointer][j+j_pointer]) && arr[i+i_pointer][j+j_pointer] != '.'){
                                        String twoDigitNumberConcat = Character.toString(arr[i][j]) + Character.toString(arr[i][j+1]);
                                        if(!twoDigitNumberConcat.equalsIgnoreCase("JJ")){
                                            totalCount += Integer.parseInt(twoDigitNumberConcat);

                                            //Shouldnt matter here as it is just to stop numbers after each other, but just for completeness
                                            //set the digits to '.' once theyve been added so that on next pointer search it doesnt re-add
                                            arr[i][j] = 'J';
                                            arr[i][j+1] = 'J';
                                        }
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    //do nothing, keep searching boy
                                }
                            }
                        }
                        
                        //go around the second digit
                        for (int i_pointer = -1; i_pointer < 2; i_pointer++) {
                            for (int j_pointer = -1; j_pointer < 2; j_pointer++) {
                                try{
                                    //if the character is a symbol
                                    if(!Character.isDigit(arr[i+i_pointer][j+1+j_pointer]) && arr[i+i_pointer][j+1+j_pointer] != '.'){
                                        String twoDigitNumberConcat = Character.toString(arr[i][j]) + Character.toString(arr[i][j+1]);

                                        if(!twoDigitNumberConcat.equalsIgnoreCase("JJ")){
                                            totalCount += Integer.parseInt(twoDigitNumberConcat);

                                            //Shouldnt matter here as it is just to stop numbers after each other, but just for completeness
                                            //set the digits to '.' once theyve been added so that on next pointer search it doesnt re-add
                                            arr[i][j] = 'J';
                                            arr[i][j+1] = 'J';
                                        }
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    //do nothing, keep searching boy
                                }
                            }
                        }
                    }
                    
                    //if the number is 3 digits 
                    if(Character.isDigit(arr[i][j+1]) && Character.isDigit(arr[i][j+2])){
                        //go around the first digit
                        for (int i_pointer = -1; i_pointer < 2; i_pointer++) {
                            for (int j_pointer = -1; j_pointer < 2; j_pointer++) {
                                //if the character is a symbol
                                try {
                                    if(!Character.isDigit(arr[i+i_pointer][j+j_pointer]) && arr[i+i_pointer][j+j_pointer] != '.'){
                                    String threeDigitNumberConcat = Character.toString(arr[i][j]) + Character.toString(arr[i][j+1]) + Character.toString(arr[i][j+2]);
                                    if(!threeDigitNumberConcat.equalsIgnoreCase("JJJ")){
                                            totalCount += Integer.parseInt(threeDigitNumberConcat);

                                            //Shouldnt matter here as it is just to stop numbers after each other, but just for completeness
                                            //set the digits to '.' once theyve been added so that on next pointer search it doesnt re-add
                                            arr[i][j] = 'J';
                                            arr[i][j+1] = 'J';
                                            arr[i][j+2] = 'J';
                                        }
                                }   
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    //do nothing, keep searching boy
                                }
                            }
                        }
                        
                        //go around the second digit
                        for (int i_pointer = -1; i_pointer < 2; i_pointer++) {
                            for (int j_pointer = -1; j_pointer < 2; j_pointer++) {
                                //if the character is a symbol
                                try {
                                    if(!Character.isDigit(arr[i+i_pointer][j+1+j_pointer]) && arr[i+i_pointer][j+1+j_pointer] != '.'){
                                        String threeDigitNumberConcat = Character.toString(arr[i][j]) + Character.toString(arr[i][j+1]) + Character.toString(arr[i][j+2]);

                                        if(!threeDigitNumberConcat.equalsIgnoreCase("JJJ")){
                                            totalCount += Integer.parseInt(threeDigitNumberConcat);

                                            //Shouldnt matter here as it is just to stop numbers after each other, but just for completeness
                                            //set the digits to '.' once theyve been added so that on next pointer search it doesnt re-add
                                            arr[i][j] = 'J';
                                            arr[i][j+1] = 'J';
                                            arr[i][j+2] = 'J';
                                        }
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    //do nothing, keep searching boy
                                }
                            }
                        }  
                        
                        //go around the third digit
                        for (int i_pointer = -1; i_pointer < 2; i_pointer++) {
                            for (int j_pointer = -1; j_pointer < 2; j_pointer++) {
                                try {
                                    //if the character is a symbol
                                    if(!Character.isDigit(arr[i+i_pointer][j+2+j_pointer]) && arr[i+i_pointer][j+2+j_pointer] != '.'){
                                        String threeDigitNumberConcat = Character.toString(arr[i][j]) + Character.toString(arr[i][j+1]) + Character.toString(arr[i][j+2]);
                                        
                                        if(!threeDigitNumberConcat.equalsIgnoreCase("JJJ")){
                                            totalCount += Integer.parseInt(threeDigitNumberConcat);

                                            //Shouldnt matter here as it is just to stop numbers after each other, but just for completeness
                                            //set the digits to '.' once theyve been added so that on next pointer search it doesnt re-add
                                            arr[i][j] = 'J';
                                            arr[i][j+1] = 'J';
                                            arr[i][j+2] = 'J';
                                        }
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    //do nothing, keep searching boy
                                }
                            }
                        }  
                    }
                }
                } catch (IndexOutOfBoundsException e) {
                    // Do nahin
                }
                
            }
        }

        System.out.println(totalCount);
    }
}

