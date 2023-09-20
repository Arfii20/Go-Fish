package base.gofish.saveAndMusic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The {@code SavesLocation} class manages the save location path for game data.
 * It provides methods to load the save location from a text file, retrieve the default save location,
 * and set a new save location.
 *
 * @author Arefin Ahammed
 * @version 1.2
 */
public class SavesLocation {

    /**
     * Loads the save location path from a text file.
     *
     * @return A {@code String} representing the save location path.
     * If the path is not found or an error occurs while reading, this method falls back to returning the default save location.
     */
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

    /**
     * Default save location string.
     *
     * @return the string
     */
    public static String defaultSaveLocation(){
        return getLocation(null);
    }


    /**
     * Retrieves the save location for the game data. If a save location is provided, it sets and saves the new location.
     * If no save location is provided, it retrieves the previously set location or defaults to the user's Documents folder.
     *
     * @param saveLocation The new save location to set (can be null).
     * @return The save location as a String.
     */
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
