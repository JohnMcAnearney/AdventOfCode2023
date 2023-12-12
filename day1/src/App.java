import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        //final String fileName = "day1/sample_day1_pt1.txt";
        final String fileName = "day1/input_day1.txt";
        int totalCount = 0;

        try {
            TextFileReader reader = new TextFileReader(new BufferedReader(new FileReader(fileName)));

            ArrayList<String> arrayOfStrings = reader.readTextFileToArrayList(fileName);
            System.out.println(arrayOfStrings.size());

            for (String string : arrayOfStrings) {
                int concatted = reader.concatCharsIntoOneNumber(string);
                totalCount += concatted;
                System.out.println("total Count so far: " + totalCount);
            }

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
    
    public ArrayList<String> readTextFileToArrayList(String fileName) {

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

    private String getFirstFoundNumberStartingFromFront(String stringToCheck){
        for (int i = 0; i < stringToCheck.length(); i++) {
            if(Character.isDigit(stringToCheck.charAt(i))){
                return String.valueOf(stringToCheck.charAt(i));
            }
        }
        return "N";
    }

    private String getFirstFoundNumberStartingFromBack(String stringToCheck){
        for (int i = stringToCheck.length()-1; i >= 0; i--) {
            if(Character.isDigit(stringToCheck.charAt(i))){
                return String.valueOf(stringToCheck.charAt(i));
            }
        }
        return "N";
    }

    public int concatCharsIntoOneNumber(String string){

        String firstCharFound = getFirstFoundNumberStartingFromFront(string);
        String secondCharFound = getFirstFoundNumberStartingFromBack(string);

        if(firstCharFound.equalsIgnoreCase("N") || secondCharFound.equalsIgnoreCase("N")){
            if(firstCharFound.equalsIgnoreCase("N")) {
                return Integer.parseInt(secondCharFound);
            } else if (secondCharFound.equalsIgnoreCase("N")) {
                return Integer.parseInt(firstCharFound);
            }
        } 

        String charsAsString = firstCharFound + secondCharFound; 
        return Integer.parseInt(charsAsString); 
    }
    
    // private char 
    //nine
    //enin
}

//regex patterns are 
// one 
// two
// three
// four 
// five 
// six 
// seven 
// eight 
// nine

