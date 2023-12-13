import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) throws Exception {
        //final String fileName = "day4/sample_day4_pt1.txt";
        final String fileName = "day4/input_day4.txt";

        try {

            //Ensure read in correctly
            TextFileReader reader = new TextFileReader(new BufferedReader(new FileReader(fileName)));
            //reader.printTextFileToConsole();
            
            //Actual code stuff
            ArrayList<String> test = reader.readTextFileToArrayList();
            //Brain brain = new Brain(test);
            //brain.make2dArray(test);

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
    
    private ArrayList<String> getLineAsArrayList(String line){
        String[] splitLine = line.substring(line.lastIndexOf(":")+1).strip().split(" ");
        ArrayList<String> splitAsList = new ArrayList<String>(Arrays.asList(splitLine));
        splitAsList.removeIf(x -> x.isEmpty());

        return splitAsList;
    }

    private ArrayList<Integer> getEachSideArrayList(List<String> lineArrayList) {
        ArrayList<Integer> eachArrayList = new ArrayList<>();

        for (int i = 0; i < lineArrayList.size(); i++) {
            if(lineArrayList.get(i).equalsIgnoreCase("|")){
                break;
            } 
            else {
                eachArrayList.add(Integer.parseInt(lineArrayList.get(i)));
            }
        }
        
        return eachArrayList;
    }


    public ArrayList<String> readTextFileToArrayList() {

        ArrayList<String> yrma = new ArrayList<>();
        double total = 0;

        try {
			String line = this.reader.readLine();

			while (line != null) {
                ArrayList<String> fullOriginalLine = getLineAsArrayList(line);
				ArrayList<Integer> lhsArrayList = getEachSideArrayList(fullOriginalLine);
                ArrayList<Integer> rhsArrayList = getEachSideArrayList(fullOriginalLine.reversed());

                
                double dupeCounter = 0;

                for (int i = 0; i < lhsArrayList.size(); i++) {
                    for (int j = 0; j < rhsArrayList.size(); j++) {
                        if(lhsArrayList.get(i).intValue() == rhsArrayList.get(j).intValue()){
                            dupeCounter++;
                        }
                    }
                }

                //PART 1
                if(dupeCounter >= 1){
                    dupeCounter -= 1;
                    total += Math.pow(2, dupeCounter);
                } 

				// read next line
				line = reader.readLine();
			}

			reader.close();
            System.out.println(total);
            return yrma;

		} catch (IOException e) {
			e.printStackTrace();
            return yrma;
		}
    }

    private String convertObjectArrayToString(Object[] arr, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : arr)
			sb.append(obj.toString()).append(delimiter);
		return sb.substring(0, sb.length() - 1);

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

