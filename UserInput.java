import java.io.*;

public class UserInput{
    public String getUserInput(){
        String inputLine = null;
        System.out.print("Please enter in shot: ");
        try{
            BufferedReader is = new BufferedReader(
                new InputStreamReader(System.in));
                inputLine = is.readLine();
                if(inputLine.length() == 0)  return null;
        } catch (IOException e){
            System.out.println("IOException: " + e);
        }
        return inputLine;
    }
}