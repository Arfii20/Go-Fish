package base.gofish.saveAndMusic;
import java.io.*;

public class SavesLocation {

    public static String loadSaveLocation(){
        String saveLocation = null;
        try (
                FileReader savedLocFile = new FileReader("./settings/saveLocation.txt");
                BufferedReader savedLocStream = new BufferedReader(savedLocFile)
        ){
            saveLocation = savedLocStream.readLine();
        }
        catch (IOException e){

            System.out.println("There was an error reading to the file");
        }
        return (saveLocation == null) ? getLocation(null) : saveLocation;
    }

    public static String defaultSaveLocation(){
        return getLocation(null);
    }

    private static String getLocation(String saveLocation) {
        try (
                FileWriter saveFile = new FileWriter("./settings/saveLocation.txt");
                PrintWriter saveWriter = new PrintWriter(saveFile)
        )
        {
            String userHome = System.getProperty("user.home");
            String documentsFolder = userHome + File.separator + "Documents" + File.separator + "GoFish" + File.separator;
            saveWriter.println(documentsFolder);
            saveLocation = documentsFolder;
            File file = new File(documentsFolder);
            if (!file.exists()){
                boolean success = file.mkdirs();
                if (!success){
                    throw new IOException("Error making the folder");
                }
                else {
                    System.out.println("folder made successfully");
                }
            }
        }
        catch (IOException e){
            System.out.println("There was an error creating the folder");
            try (
                    FileWriter saveFile = new FileWriter("saveLocation.txt");
                    PrintWriter saveWriter = new PrintWriter(saveFile)
            )
            {
                File currentFile = new File("");
                String absolutePath = currentFile.getAbsolutePath() + File.separator;
                saveWriter.println(absolutePath);
                saveLocation = absolutePath;
            }
            catch (IOException ev){
                System.out.println("There was an error writing to the file");
            }
        }
        return saveLocation;
    }
}
