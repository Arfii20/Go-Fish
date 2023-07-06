package base;

import base.gofish.GameEngine;
import base.gofish.deck.Card;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.*;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GameDriver extends Application {
    private ArrayList<String> names = new ArrayList<String>();
    private static Stage window;
    private GameEngine game;
    private int playerNum;
    private Timeline timeline;
    private boolean phase1;
    private int twoPlayers;
    private Stage pauseStage;
    private BoxBlur blur;
    private String saveLocation;
    private Clip audioClip;
    private Clip buttonClick;
    private double musicVolume;
    private double buttonVolume;

    // All the scenes
    private Scene OpeningScene;
    private Scene startMenuScene;
    private Scene askPlayersScene;
    private Scene enterPlayersScene;
    private Scene loadGameMenuScene;
    private Scene saveGameMenuScene;
    private Scene mainGamePageScene;
    private Scene displayGameCardScene;
    private Scene displaySpaceCardScene;
    private Scene showPlayersForSwapScene;
    private Scene rulesActionCardsScene;
    private Scene rulesSpaceCardsScene;
    private Scene rulesMoreInfoScene;
    private Scene pauseGameScene;
    private Scene settingsScene;
    private Scene showCardScene;
    private Scene gameOverScene;

    // welcome Scene Elements
    @FXML
    private VBox WelcomeVbox;
    @FXML
    private Label pressAnyKeyLabel;

    // Add player number elements
    @FXML
    private ComboBox<Integer> playerNoCombobox;
    @FXML
    private Button setPlayerNumbers;

    // Add Player Elements
    @FXML
    private TextField getPlayerFromInput;
    @FXML
    private Label playerAdded;

    // Main Body Elements
    private Label topLeftInstructions;
    @FXML
    private Label currentPlayerTopLabel;
    @FXML
    private HBox mainHBox;
    @FXML
    private VBox mainPageLeftVBox;
    @FXML
    private VBox mainPageRightVBox;
    @FXML
    private Line mainPageLine;

    // Show Deck scene elements
    private ToggleGroup group;
    @FXML
    private HBox addCardHBox;
    @FXML
    private HBox addNamesHBox;
    @FXML
    private Label addNamesLabel;
    @FXML
    private HBox addBreathTravel;
    @FXML
    private ScrollPane scrollPaneOfShowCardDeck;

    // Display Space Card
    @FXML
    private Label displaySpaceCardLabel;
    @FXML
    private Label displaySpaceCardDescriptionLabel;

    // Display Game Card
    @FXML
    private Label displayGameCardLabel;
    @FXML
    private Label displayGameCardDescriptionLabel;

    // Game Over Scene elements
    @FXML
    private Label winnerLabel;
    @FXML
    private Button restartGameButton;

    // Rules Scene
    @FXML
    private Label actionCardsLabel;
    @FXML
    private Label spaceCardsLabel;
    @FXML
    private Label moreInfoLabel;

    // Swap with player
    @FXML
    private HBox playerNamesForSwap;

    // Load game elements
    @FXML
    private VBox loadGameVbox;

    // Save game elements
    @FXML
    private VBox saveGameVbox;
    @FXML
    private Label saveGameLocation;

    // Settings elements
    @FXML
    private Label changeLocLabel;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider buttonVolumeSlider;

    public static void main(String[] args){
        launch();
    }

    // Start and Restart
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
//        phase1 = false;
//        twoPlayers = 0;

//        blur = new BoxBlur();
//        blur.setWidth(10);
//        blur.setHeight(10);
//        blur.setIterations(1);

        Random random = new Random();
        long seed = random.nextLong();
//        try {
//            game = new GameEngine(seed, "io" + File.separator + "ActionCards.txt", "io" + File.separator + "SpaceCards.txt");
//        }
//        catch (GameException e){
//            System.out.println(e.getMessage());
//        }

        // Load the FXML files and set the controller to this one. Also create scenes for the fxml files.
        FXMLLoader openingXML = new FXMLLoader(getClass().getResource("/PreGame/1openingScene.fxml"));
        openingXML.setController(this);
        OpeningScene = new Scene(openingXML.load(), 1280, 720);

//        FXMLLoader startMenuXML = new FXMLLoader(getClass().getResource("/resources/startMenu.fxml"));
//        startMenuXML.setController(this);
//        startMenuScene = new Scene(startMenuXML.load());
//
//        FXMLLoader loadGameMenuXML = new FXMLLoader(getClass().getResource("/resources/loadGame.fxml"));
//        loadGameMenuXML.setController(this);
//        loadGameMenuScene = new Scene(loadGameMenuXML.load());
//
//        FXMLLoader saveGameMenuXML = new FXMLLoader(getClass().getResource("/resources/saveGame.fxml"));
//        saveGameMenuXML.setController(this);
//        saveGameMenuScene = new Scene(saveGameMenuXML.load(), 1200, 665);
//
//        FXMLLoader settingsXML = new FXMLLoader(getClass().getResource("/resources/settings.fxml"));
//        settingsXML.setController(this);
//        settingsScene = new Scene(settingsXML.load());
//
//        FXMLLoader askPlayersXML = new FXMLLoader(getClass().getResource("/resources/AskPlayers.fxml"));
//        askPlayersXML.setController(this);
//        askPlayersScene = new Scene(askPlayersXML.load());
//
//        FXMLLoader enterPlayersXML = new FXMLLoader(getClass().getResource("/resources/2playerName.fxml"));
//        enterPlayersXML.setController(this);
//        enterPlayersScene = new Scene(enterPlayersXML.load());
//
//        FXMLLoader mainGamePageXML = new FXMLLoader(getClass().getResource("/resources/MainGame.fxml"));
//        mainGamePageXML.setController(this);
//        mainGamePageScene = new Scene(mainGamePageXML.load());
//
//        FXMLLoader showCardXML = new FXMLLoader(getClass().getResource("/resources/showCard.fxml"));
//        showCardXML.setController(this);
//        showCardScene = new Scene(showCardXML.load(), 1200, 665);
//
//        FXMLLoader displaySpaceCardXML = new FXMLLoader(getClass().getResource("/resources/displaySpaceCard.fxml"));
//        displaySpaceCardXML.setController(this);
//        displaySpaceCardScene = new Scene(displaySpaceCardXML.load(), 320, 200);
//
//        FXMLLoader displayGameCardXML = new FXMLLoader(getClass().getResource("/resources/displayGameCard.fxml"));
//        displayGameCardXML.setController(this);
//        displayGameCardScene = new Scene(displayGameCardXML.load(), 200, 320);
//
//        FXMLLoader gameOverXML = new FXMLLoader(getClass().getResource("/resources/gameOverScene.fxml"));
//        gameOverXML.setController(this);
//        gameOverScene = new Scene(gameOverXML.load());
//
//        FXMLLoader pauseGameXML = new FXMLLoader(getClass().getResource("/resources/pauseMenu.fxml"));
//        pauseGameXML.setController(this);
//        pauseGameScene = new Scene(pauseGameXML.load(), 1200, 665);
//
//        FXMLLoader rulesMoreInfoXML = new FXMLLoader(getClass().getResource("/resources/Rules.fxml"));
//        rulesMoreInfoXML.setController(this);
//        rulesMoreInfoScene = new Scene(rulesMoreInfoXML.load());
//
//        FXMLLoader showPlayersForSwapXML = new FXMLLoader(getClass().getResource("/resources/showPlayersForSwap.fxml"));
//        showPlayersForSwapXML.setController(this);
//        showPlayersForSwapScene = new Scene(showPlayersForSwapXML.load(), 1200, 665);

//        // Add button click
//        addListeners();

        // Set title and first scene of the game
        window.setTitle("Go Fish");
        if (window.getScene() != null){
            double sceneWidth = window.getScene().getWidth();
            double sceneHeight = window.getScene().getHeight();
            ((VBox) OpeningScene.getRoot()).setPrefSize(sceneWidth, sceneHeight);
        }
        window.setScene(OpeningScene);
        window.setMinWidth(1280);
        window.setMinHeight(720);

        // Load the save location
//        loadSaveLocation();

        // Fade Animation for the press any key to start
        FadeTransition fade = new FadeTransition();
        fade.setNode(pressAnyKeyLabel);
        fade.setDuration(Duration.millis(2000));
        fade.setCycleCount((TranslateTransition.INDEFINITE));
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

//        // Binding the line length to main window size
//        BorderPane mainPageRoot = ((BorderPane) mainGamePageScene.getRoot());
//        mainPageLine.startXProperty().bind(mainPageRoot.widthProperty().multiply(0.05));
//        mainPageLine.endXProperty().bind(mainPageRoot.widthProperty().multiply(0.95));

        // Press any key to set scene to get number of players from players
        OpeningScene.setOnKeyPressed(event -> {
            OpeningScene.setOnKeyPressed(null);
            double width = window.getScene().getWidth();
            double height = window.getScene().getHeight();
            ((VBox) startMenuScene.getRoot()).setPrefSize(width, height);
            window.setScene(startMenuScene);
        });

//        // Add listener to the enter key so that player is added when enter pressed
//        getPlayerFromInput.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER){
//                addPlayers();
//            }
//        });

//        // Get number of players and set scene to get player names from players
//        setPlayerNumbers.setOnAction(actionEvent -> {
//            playerNum = playerNoCombobox.getValue();
//            double width = window.getScene().getWidth();
//            double height = window.getScene().getHeight();
//            ((VBox) enterPlayersScene.getRoot()).setPrefSize(width, height);
//            window.setScene(enterPlayersScene);
//        });

//        // Add listener on escape key for pausing the game
//        pauseStage = new Stage();
//        pauseStage.initStyle(StageStyle.UNDECORATED);
//        pauseStage.initModality(Modality.APPLICATION_MODAL);
//        pauseStage.initStyle(StageStyle.TRANSPARENT);
//        pauseStage.setScene(pauseGameScene);
//        pauseStage.setOpacity(0.9);

//        mainGamePageScene.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ESCAPE){
//                mainGamePageScene.getRoot().setEffect(blur);
//                pauseStage.show();
//            }
//        });
//
//        pauseGameScene.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ESCAPE){
//                mainGamePageScene.getRoot().setEffect(null);
//                pauseStage.hide();
//            }
//        });
//
//        showCardScene.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ESCAPE){
//                Stage overlayStage = (Stage) showCardScene.getWindow();
//                mainGamePageScene.getRoot().setEffect(null);
//                overlayStage.close();
//            }
//        });
//
//        // Start Music
//        getMusic();
//        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
//            musicVolume = newValue.doubleValue() / 100;
//            setMusicVolume(musicVolume);
//        });
//
//        buttonVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
//            buttonVolume = newValue.doubleValue() / 100;
//            setButtonVolume(buttonVolume);
//        });

        window.show();
    }

    public void restartGame() throws IOException {
        this.start(window);
    }

    // Add players to the game
//    public void addPlayers(){
//        if(getPlayerFromInput.getText().isEmpty()){
//            getPlayerFromInput.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
//            return;
//        }
//        else if (this.names.contains(getPlayerFromInput.getText())){
//            getPlayerFromInput.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
//            playerAdded.setText(getPlayerFromInput.getText() + " already added");
//            playerAdded.setVisible(true);
//            playerAdded.setTextFill(Color.RED);
//            getPlayerFromInput.clear();
//            FadeTransition fadeIn = new FadeTransition(Duration.millis(600), playerAdded);
//            fadeIn.setFromValue(0);
//            fadeIn.setToValue(1);
//
//            FadeTransition fadeOut = new FadeTransition(Duration.millis(600), playerAdded);
//            fadeOut.setFromValue(1);
//            fadeOut.setToValue(0);
//
//            SequentialTransition fadeSequence = new SequentialTransition(fadeIn, fadeOut);
//            fadeSequence.play();
//            getPlayerFromInput.requestFocus();
//            playerAdded.setTextFill(Color.WHITE);
//            return;
//        }
//        else {
//            getPlayerFromInput.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px;");
//            if (playerNum != 1){
//                game.addPlayer(getPlayerFromInput.getText());
//                names.add(getPlayerFromInput.getText());
//                playerNum -= 1;
//                playerAdded.setText(getPlayerFromInput.getText() + " Joined");
//                playerAdded.setVisible(true);
//                getPlayerFromInput.clear();
//                FadeTransition fadeIn = new FadeTransition(Duration.millis(600), playerAdded);
//                fadeIn.setFromValue(0);
//                fadeIn.setToValue(1);
//
//                FadeTransition fadeOut = new FadeTransition(Duration.millis(600), playerAdded);
//                fadeOut.setFromValue(1);
//                fadeOut.setToValue(0);
//
//                SequentialTransition fadeSequence = new SequentialTransition(fadeIn, fadeOut);
//                fadeSequence.play();
//                getPlayerFromInput.requestFocus();
//            }
//            else {
//                getPlayerFromInput.setDisable(true);
//                game.addPlayer(getPlayerFromInput.getText());
//                playerAdded.setText(getPlayerFromInput.getText() + " Joined");
//                getPlayerFromInput.clear();
//                playerAdded.setVisible(true);
//
//                FadeTransition fadeIn = new FadeTransition(Duration.millis(400), playerAdded);
//                fadeIn.setFromValue(0);
//                fadeIn.setToValue(1);
//
//                FadeTransition fadeOut = new FadeTransition(Duration.millis(400), playerAdded);
//                fadeOut.setFromValue(1);
//                fadeOut.setToValue(0);
//
//                SequentialTransition fadeSequence = new SequentialTransition(fadeIn, fadeOut);
//                fadeSequence.setOnFinished(event -> {
//                    game.startGame();
//                    System.out.println(game.getAllPlayers());
//                    game.startTurn();
//                    showPopupMessage("First player to reach the spaceship wins. Unless everyone dies of course", Color.CYAN, 3);
//                    addToBorderPaneLeftVBox();
//                    addToBorderPaneRightVBox();
//                    addColumnsDependingOnPlayer();
//                    topLeftInstructions.setText("Pick a card from the Game Deck by clicking the deck below");
//                    double width = window.getScene().getWidth();
//                    double height = window.getScene().getHeight();
//                    ((BorderPane) mainGamePageScene.getRoot()).setPrefSize(width, height);
//                    window.setScene(mainGamePageScene);
//                    currentPlayerTopLabel.setTextFill(Color.CYAN);
//                    currentPlayerTopLabel.setText("Current Player: " + game.getCurrentPlayer().toString());
//                });
//                fadeSequence.play();
//            }
//        }
//    }
//
//    // Load and Change locations
    private void loadSaveLocation(){
        try (
                FileReader savedLocFile = new FileReader("./settings/saveLocation.txt");
                BufferedReader savedLocStream = new BufferedReader(savedLocFile)
        ){
            saveLocation = savedLocStream.readLine();
        }
        catch (IOException e){

            System.out.println("There was an error reading to the file");
        }
        if (saveLocation == null){
            try (
                    FileWriter saveFile = new FileWriter("./settings/saveLocation.txt");
                    PrintWriter saveWriter = new PrintWriter(saveFile)
            )
            {
                String userHome = System.getProperty("user.home");
                String documentsFolder = userHome + File.separator + "Documents" + File.separator + "Selfish" + File.separator;
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
        }
    }

    public void changeLoc(){
        playButtonSoundEffect();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        directoryChooser.setInitialDirectory(new File(saveLocation));
        File selectedDirectory;
        try{
            selectedDirectory = directoryChooser.showDialog(new Stage());
        }
        catch (Exception e) {
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            selectedDirectory = directoryChooser.showDialog(new Stage());
        }

        if (selectedDirectory != null){
            System.out.println("Selected folder: " + selectedDirectory.getAbsolutePath());
            changeLocLabel.setText(selectedDirectory.getAbsolutePath() + File.separator);
            saveGameLocation.setText(selectedDirectory.getAbsolutePath() + File.separator);
            try (
                    FileWriter saveFile = new FileWriter("./settings/saveLocation.txt");
                    PrintWriter saveWriter = new PrintWriter(saveFile)
            )
            {
                saveWriter.println(selectedDirectory.getAbsolutePath() + File.separator);
            }
            catch (IOException e){
                System.out.println("There was an error writing to the file");
            }
            saveLocation = selectedDirectory.getAbsolutePath() + File.separator;
        } else {
            System.out.println("No folder selected.");
        }
    }

//    // Add Button clicks
//    private void addListeners(){
//        List<Node> allButtons = new ArrayList<>();
//        allButtons.addAll(OpeningScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(startMenuScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(askPlayersScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(enterPlayersScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(loadGameMenuScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(saveGameMenuScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(mainGamePageScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(displayGameCardScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(displaySpaceCardScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(showPlayersForSwapScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(rulesActionCardsScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(rulesSpaceCardsScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(rulesMoreInfoScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(pauseGameScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(settingsScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(showCardScene.getRoot().lookupAll(".button"));
//        allButtons.addAll(gameOverScene.getRoot().lookupAll(".button"));
//        for (Node button : allButtons) {
//            addButtonSoundEffect((Button) button);
//        }
//    }
//
//    private void addButtonSoundEffect(Button button) {
//        if (buttonClick == null) {
//            File file = new File("./bin/resources/click1.wav");
//            try {
//                buttonClick = AudioSystem.getClip();
//                buttonClick.open(AudioSystem.getAudioInputStream(file));
//            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | IllegalArgumentException e) {
//                System.out.println("Cannot Play sound");
//                buttonClick = null;
//            }
//        }
//        if (buttonClick != null) {
//            button.setOnAction(event -> buttonClick.start());
//        }
//    }
//
    private void playButtonSoundEffect() {
        if (buttonClick == null) {
            File file = new File("./bin/resources/click1.wav");
            try {
                buttonClick = AudioSystem.getClip();
                buttonClick.open(AudioSystem.getAudioInputStream(file));
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | IllegalArgumentException e) {
                System.out.println("Cannot Play sound");
                buttonClick = null;
            }
        }
        if (buttonClick != null) {
            setButtonVolume(buttonVolume);
            buttonClick.setFramePosition(0);
            buttonClick.start();
        }
    }

    public void setButtonVolume(double percent){
        if (buttonClick != null) {
            FloatControl volumeClick = (FloatControl) buttonClick.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(percent) / Math.log(10.0) * 20.0);
            volumeClick.setValue(dB);
        }
    }

    // Playing and Handling music
    public void getMusic() {
        loadVolume();
        if (audioClip == null){
            File file = new File("./bin/resources/track.wav");
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioStream);
                audioClip.loop(audioClip.LOOP_CONTINUOUSLY);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
                System.out.println("Cannot Play sound");
                audioClip = null;
            }
        }
        else {
            if (musicVolume > 0) {
                audioClip.start();
            }
        }
        if (audioClip != null) {
            setMusicVolume(musicVolume);
        }
    }

    public void saveVolume() {
        try (
                FileWriter saveMusicFile = new FileWriter("./settings/musicVolume.txt");
                PrintWriter saveMusicWriter = new PrintWriter(saveMusicFile)
        )
        {
            saveMusicWriter.println(musicVolume);
        }
        catch (IOException e){
            System.out.println("There was an error writing to the file");
        }
        try (
                FileWriter saveButtonFile = new FileWriter("./settings/buttonVolume.txt");
                PrintWriter saveButtonWriter = new PrintWriter(saveButtonFile)
        )
        {
            saveButtonWriter.println(buttonVolume);
        }
        catch (IOException e){
            System.out.println("There was an error writing to the file");
        }
    }

    public void loadVolume() {
        try (
                FileReader savedLocFile = new FileReader("./settings/musicVolume.txt");
                BufferedReader savedLocStream = new BufferedReader(savedLocFile)
        ){
            musicVolume = Double.parseDouble(savedLocStream.readLine());
        }
        catch (IOException e){
            System.out.println("There was an error reading to the music volume file");
            musicVolume = 1.0;
        }
        try (
                FileReader savedLocFile = new FileReader("./settings/buttonVolume.txt");
                BufferedReader savedLocStream = new BufferedReader(savedLocFile)
        ){
            buttonVolume = Double.parseDouble(savedLocStream.readLine());
        }
        catch (IOException e){
            System.out.println("There was an error reading to the button volume file");
            buttonVolume = 1.0;
        }
        volumeSlider.setValue(musicVolume*100);
        buttonVolumeSlider.setValue(buttonVolume*100);
    }

    public void setMusicVolume(double percent){
        if (audioClip != null) {
            FloatControl volume = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(percent) / Math.log(10.0) * 20.0);
            volume.setValue(dB);
            if (musicVolume > 0) {
                audioClip.start();
            } else {
                audioClip.stop();
            }
        }
    }

    // The rotating display of space and action cards
//    private void displayCard(Card card){
//        mainGamePageScene.getRoot().setEffect(blur);
//        displaySpaceCardLabel.setText(card.toString());
//        displaySpaceCardDescriptionLabel.setText(card.getDescription());
//
//        Stage overlayStage = new Stage();
//        overlayStage.initStyle(StageStyle.UNDECORATED);
//        overlayStage.initModality(Modality.APPLICATION_MODAL);
//        overlayStage.initStyle(StageStyle.TRANSPARENT);
//        overlayStage.setScene(displaySpaceCardScene);
//        overlayStage.setOpacity(0.95);
//        overlayStage.show();
//
//        displaySpaceCardScene.setFill(null);
//        ParallelTransition parallel = getParallelTransition(displaySpaceCardScene);
//        parallel.setOnFinished(e -> {
//            PauseTransition delay = new PauseTransition(Duration.seconds(3));
//            delay.setOnFinished(ev -> {
//                window.requestFocus();
//                mainGamePageScene.getRoot().setEffect(null);
//                overlayStage.close();
//            });
//            delay.play();
//        });
//        parallel.play();
//    }

    private ParallelTransition getParallelTransition(Scene scene){
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(scene.getRoot());
        rotate.setDuration(Duration.millis(750));
        rotate.setCycleCount(1);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setFromAngle(360);
        rotate.setToAngle(360);
        rotate.setAxis(Rotate.Y_AXIS);

        ScaleTransition scale = new ScaleTransition();
        scale.setNode(scene.getRoot());
        scale.setDuration(Duration.millis(750));
        scale.setCycleCount(1);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1);
        scale.setToY(1);

        return new ParallelTransition(rotate, scale);
    }


//    private void showErrorInShowCard(String message){
//        addNamesLabel.setText(message);
//        addNamesLabel.setStyle("-fx-text-fill: red; -fx-font-size: 22");
//
//        PauseTransition delay = new PauseTransition(Duration.seconds(1));
//        delay.setOnFinished(e -> {
//            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), addNamesLabel);
//            fadeIn.setFromValue(1);
//            fadeIn.setToValue(0);
//            fadeIn.setOnFinished(ev -> {
//                addNamesLabel.setText("If it applies to any player, select the player");
//                addNamesLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22");
//            });
//
//            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), addNamesLabel);
//            fadeOut.setFromValue(0);
//            fadeOut.setToValue(1);
//            fadeOut.setDelay(Duration.millis(10));
//
//            SequentialTransition fadeSequence = new SequentialTransition(fadeIn, fadeOut);
//            fadeSequence.play();
//        });
//        delay.play();
//    }

    // Start and Pause Menus
    public void startMenu(MouseEvent event){
        playButtonSoundEffect();
        Button pressedButton = (Button) event.getSource();
        if (pressedButton.getText().equals("START")){
            double width = window.getScene().getWidth();
            double height = window.getScene().getHeight();
            ((VBox) askPlayersScene.getRoot()).setPrefSize(width, height);
            window.setScene(askPlayersScene);
        }
        else if (pressedButton.getText().equals("LOAD")){
            loadSaveFiles();
        }
        else if (pressedButton.getText().equals("OPTIONS")){
            changeLocLabel.setText(saveLocation);
            double width = window.getScene().getWidth();
            double height = window.getScene().getHeight();
            ((VBox) settingsScene.getRoot()).setPrefSize(width, height);
            window.setScene(settingsScene);
        }
        else if (pressedButton.getText().equals("RULES")){
            double width = window.getScene().getWidth();
            double height = window.getScene().getHeight();
            ((VBox) rulesActionCardsScene.getRoot()).setPrefSize(width, height);
            window.setScene(rulesActionCardsScene);
        }
        else if (pressedButton.getText().equals("QUIT")){
            Platform.exit();
        }
        event.consume();
    }

    public void pauseMenu(MouseEvent event) throws IOException {
        playButtonSoundEffect();
        Button pressedButton = (Button) event.getSource();
        Stage overlayStage = (Stage) pressedButton.getScene().getWindow();
        if (pressedButton.getText().equals("RESUME")){
            mainGamePageScene.getRoot().requestFocus();
            mainGamePageScene.getRoot().setEffect(null);
            overlayStage.close();
        }
        if (pressedButton.getText().equals("RESTART")){
            mainGamePageScene.getRoot().requestFocus();
            mainGamePageScene.getRoot().setEffect(null);
            overlayStage.close();
            this.start(window);
        }
        if (pressedButton.getText().equals("SAVE")){
            saveGame();
            overlayStage.requestFocus();
        }
        else if (pressedButton.getText().equals("LOAD")){
            loadSaveFiles();
        }
        else if (pressedButton.getText().equals("RULES")){
            double width = overlayStage.getScene().getWidth();
            double height = overlayStage.getScene().getHeight();
            ((VBox) rulesActionCardsScene.getRoot()).setPrefSize(width, height);
            overlayStage.setScene(rulesActionCardsScene);
        }
        else if (pressedButton.getText().equals("OPTIONS")){
            ((VBox) settingsScene.getRoot()).setPrefSize(1200, 665);
            overlayStage.setScene(settingsScene);
        }
        else if (pressedButton.getText().equals("QUIT")){
            try {
                game.saveState(saveLocation + "save.obj");
                showPopupMessage("Game Saved");
            }
            catch (Exception e){
                System.out.println("There was an error saving the game");
            }
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> Platform.exit());
            delay.play();
        }
        event.consume();
    }

    // Loading and saving
    private void loadSaveFiles(){
        File folder = new File(saveLocation);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null){
            Arrays.sort(listOfFiles, Comparator.comparing(File::getName));
        }
        loadGameVbox.getChildren().clear();

        String count;
        boolean foundSomething = false;
        try {
            for (File file : listOfFiles){
                String fileName = file.getName();
                if (file.isFile() && (fileName.equals("save1.obj") || fileName.equals("save2.obj") || fileName.equals("save3.obj") || fileName.equals("save.obj"))){
                    HBox hbox = new HBox();
                    count = String.valueOf(file.getName().charAt(4));
                    hbox.setId(count);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setPrefWidth(200);
                    hbox.setPrefHeight(120);

                    Button loadGameButton;
                    if (fileName.equals("save.obj")){
                        loadGameButton = new Button("AutoSave");
                    }
                    else {
                        loadGameButton = new Button("Save " + count);
                        loadGameButton.setPrefWidth(120);
                    }
                    loadGameButton.setMnemonicParsing(false);
                    loadGameButton.setOnMousePressed(this::loadGame);

                    Region region1 = new Region();
                    region1.setPrefSize(25, 20);

                    long lastModifiedTime = file.lastModified();
                    Date lastModifiedDate = new Date(lastModifiedTime);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = formatter.format(lastModifiedDate);

                    Label label = new Label(formattedDate);
                    label.setStyle("-fx-text-fill: cyan; -fx-font-size: 18;");

                    Region region2 = new Region();
                    region2.setPrefSize(25, 20);

                    Button deleteSavedGameButton = new Button("Delete");
                    deleteSavedGameButton.setMnemonicParsing(false);
                    deleteSavedGameButton.setOnMousePressed(this::deleteSavedGame);
                    deleteSavedGameButton.setPrefSize(110, 47);
                    deleteSavedGameButton.setStyle("-fx-text-fill: red");
                    deleteSavedGameButton.setId(file.getName());

                    if (fileName.equals("save.obj")){
                        hbox.getChildren().addAll(loadGameButton, region1, label);
                    }
                    else {
                        hbox.getChildren().addAll(loadGameButton, region1, label, region2, deleteSavedGameButton);
                    }

                    loadGameVbox.getChildren().add(hbox);
                    foundSomething = true;
                }
            }
        }
        catch (Exception e){
            //     Region region1 = new Region();
            //     region1.setPrefSize(53, 300);

            //     Label label = new Label("No Saved Games Available");
            //     label.setStyle("-fx-font-size: 18; -fx-text-fill: red");

            //     Region region2 = new Region();
            //     region2.setPrefSize(53, 300);

            //     loadGameVbox.getChildren().addAll(region1, label, region2);
            System.out.println("There was error reading the files");
        }
        if (!foundSomething){
            Region region1 = new Region();
            region1.setPrefSize(53, 300);

            Label label = new Label("No Saved Games Available");
            label.setStyle("-fx-font-size: 18; -fx-text-fill: red");

            Region region2 = new Region();
            region2.setPrefSize(53, 300);

            loadGameVbox.getChildren().addAll(region1, label, region2);
            System.out.println("There was no saved file");
        }
        double width = window.getScene().getWidth();
        double height = window.getScene().getHeight();
        ((VBox) loadGameMenuScene.getRoot()).setPrefSize(width, height);
        window.setScene(loadGameMenuScene);
    }

    public void loadGame(MouseEvent event){}
//        playButtonSoundEffect();
//        Button pressedButton = (Button) event.getSource();
//        HBox parentBox = (HBox) pressedButton.getParent();
//
//        if (parentBox.getId().equals("1")){
//            try {
//                game = GameEngine.loadState(saveLocation + "save1.obj");
//            }
//            catch (GameException e){
//                System.out.println("There was an error loading the game");
//                return;
//            }
//        }
//        else if (parentBox.getId().equals("2")){
//            try {
//                game = GameEngine.loadState(saveLocation + "save2.obj");
//            }
//            catch (GameException e){
//                System.out.println("There was an error loading the game");
//                return;
//            }
//        }
//        else if (parentBox.getId().equals("3")){
//            try {
//                game = GameEngine.loadState(saveLocation + "save3.obj");
//            }
//            catch (GameException e){
//                System.out.println("There was an error loading the game");
//                return;
//            }
//        }
//        else {
//            try {
//                game = GameEngine.loadState(saveLocation + "save.obj");
//            }
//            catch (GameException e){
//                System.out.println("There was an error loading the game");
//                return;
//            }
//        }
//        mainPageLeftVBox.getChildren().clear();
//        mainPageRightVBox.getChildren().clear();
//        mainHBox.getChildren().clear();
//        addToBorderPaneLeftVBox();
//        addToBorderPaneRightVBox();
//        addColumnsDependingOnPlayer();
//        for (Astronaut player: game.getAllPlayers()){
//            for (Card card: player.getTrack()){
//                addCardToPlayerColumn(card, player);
//            }
//        }
//        currentPlayerTopLabel.setText("Current Player: " + game.getCurrentPlayer().toString());
//        double width = window.getScene().getWidth();
//        double height = window.getScene().getHeight();
//        ((BorderPane) mainGamePageScene.getRoot()).setPrefSize(width, height);
//        window.setScene(mainGamePageScene);
//        if (game.getStarted()) {
//            pauseStage.setScene(pauseGameScene);
//            showPopupMessage("Game Loaded");
//        }
//        event.consume();
//    }

    public void deleteSavedGame(MouseEvent event){
        playButtonSoundEffect();
        Button pressedButton = (Button) event.getSource();
        HBox parentBox = (HBox) pressedButton.getParent();
        String fileName = pressedButton.getId();
        File file = new File(saveLocation + fileName);
        if(file.delete()){
            System.out.println("File deleted successfully.");
            loadGameVbox.getChildren().remove(parentBox);
        } else {
            System.out.println("Failed to delete file.");
        }
        event.consume();
    }

    private void saveGame(){
        loadSaveLocation();
        File folder = new File(saveLocation);
        File[] listOfFiles = folder.listFiles();
        saveGameVbox.getChildren().clear();
        saveGameLocation.setText(saveLocation);

        boolean found = false;
        for (int i = 1; i < 4; i++){
            try {
                for (File file : listOfFiles){
                    if (file.isFile() && file.getName().equals("save" + i + ".obj")){
                        found = true;
                        HBox hbox = new HBox();
                        hbox.setAlignment(Pos.CENTER);
                        hbox.setPrefSize(1281, 45);
                        hbox.setSpacing(100);

                        final int index = i;
                        Button button = new Button("Save " + i);
                        button.setPrefSize(200, 47);
                        button.setMnemonicParsing(false);
                        button.setOnMouseClicked(event -> {
                            try {
                                game.saveState(saveLocation + "save" + index + ".obj");
                                saveGame();
                                System.out.println("Game Saved");
                                showPopupMessage("Game Saved");
                            }
                            catch (Exception e){
                                System.out.println("There was an error saving the game");
                            }
                        });

                        long lastModifiedTime = file.lastModified();
                        Date lastModifiedDate = new Date(lastModifiedTime);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = formatter.format(lastModifiedDate);

                        Label label = new Label(formattedDate);
                        label.setStyle("-fx-text-fill: cyan; -fx-font-size: 18;");

                        hbox.getChildren().addAll(button, label);

                        saveGameVbox.getChildren().add(hbox);
                    }
                }
                if (!found){
                    final int index = i;
                    Button button = new Button("Empty Slot");
                    button.setPrefSize(200, 47);
                    button.setMnemonicParsing(false);
                    button.setOnMouseClicked(event -> {
                        try {
                            game.saveState(saveLocation + "save" + index + ".obj");
                            saveGame();
                            System.out.println("Game Saved");
                            showPopupMessage("Game Saved");
                        }
                        catch (Exception e){
                            System.out.println("There was an error saving the game");
                        }
                    });
                    saveGameVbox.getChildren().add(button);
                }
                found = false;
            }
            catch (Exception e){
                Region region1 = new Region();
                region1.setPrefSize(53, 300);

                Label label = new Label("No Saved Games Available");
                label.setStyle("-fx-font-size: 18; -fx-text-fill: red");

                Region region2 = new Region();
                region2.setPrefSize(53, 300);

                saveGameVbox.getChildren().addAll(region1, label, region2);
                System.out.println("There was no saved file");
            }

            pauseStage.setScene(saveGameMenuScene);
        }
    }

    // Rules and tutorial
    public void youtubeLink(){
        playButtonSoundEffect();
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=hRpXLSMdve0"));
        } catch (IOException | URISyntaxException e){
            e.printStackTrace();
        }
    }
//
//    public void prevButtons(MouseEvent event){
//        playButtonSoundEffect();
//        Scene scene = ((Node) event.getTarget()).getScene();
//        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        if (scene == actionCardsLabel.getScene()){
//            double width = currentStage.getScene().getWidth();
//            double height = currentStage.getScene().getHeight();
//            ((VBox) rulesMoreInfoScene.getRoot()).setPrefSize(width, height);
//            currentStage.setScene(rulesMoreInfoScene);
//        }
//        if (scene == spaceCardsLabel.getScene()){
//            double width = currentStage.getScene().getWidth();
//            double height = currentStage.getScene().getHeight();
//            ((VBox) rulesActionCardsScene.getRoot()).setPrefSize(width, height);
//            currentStage.setScene(rulesActionCardsScene);
//        }
//        if (scene == moreInfoLabel.getScene()){
//            double width = currentStage.getScene().getWidth();
//            double height = currentStage.getScene().getHeight();
//            ((VBox) rulesSpaceCardsScene.getRoot()).setPrefSize(width, height);
//            currentStage.setScene(rulesSpaceCardsScene);
//        }
//        event.consume();
//    }
//
//    public void nextButtons(MouseEvent event){
//        playButtonSoundEffect();
//        Scene scene = ((Node) event.getTarget()).getScene();
//        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        if (scene == actionCardsLabel.getScene()){
//            double width = currentStage.getScene().getWidth();
//            double height = currentStage.getScene().getHeight();
//            ((VBox) rulesSpaceCardsScene.getRoot()).setPrefSize(width, height);
//            currentStage.setScene(rulesSpaceCardsScene);
//        }
//        if (scene == spaceCardsLabel.getScene()){
//            double width = currentStage.getScene().getWidth();
//            double height = currentStage.getScene().getHeight();
//            ((VBox) rulesMoreInfoScene.getRoot()).setPrefSize(width, height);
//            currentStage.setScene(rulesMoreInfoScene);
//        }
//        if (scene == moreInfoLabel.getScene()){
//            double width = currentStage.getScene().getWidth();
//            double height = currentStage.getScene().getHeight();
//            ((VBox) rulesActionCardsScene.getRoot()).setPrefSize(width, height);
//            currentStage.setScene(rulesActionCardsScene);
//        }
//        event.consume();
//    }
//
    // Back Buttons
    public void backButtonOfRules(MouseEvent event){
        playButtonSoundEffect();
        if (game.isStarted()){
            double width = window.getScene().getWidth();
            double height = window.getScene().getHeight();
            ((VBox) startMenuScene.getRoot()).setPrefSize(width, height);
            window.setScene(pauseGameScene);
        }
        else {
            double width = window.getScene().getWidth();
            double height = window.getScene().getHeight();
            ((VBox) startMenuScene.getRoot()).setPrefSize(width, height);
            window.setScene(startMenuScene);
        }
        event.consume();
    }

    public void backButtonOfLoad(){
        playButtonSoundEffect();
        if (game.isStarted()){
            pauseStage.setScene(pauseGameScene);
        }
        else {
            double width = window.getScene().getWidth();
            double height = window.getScene().getHeight();
            ((VBox) startMenuScene.getRoot()).setPrefSize(width, height);
            window.setScene(startMenuScene);
        }
        saveVolume();
    }
//
//    public void backButtonOfSave(){
//        playButtonSoundEffect();
//        pauseStage.setScene(pauseGameScene);
//    }

    // Pop up messages
    private void showPopupMessage(String message, Color textColor, double duration){
        Label label = new Label(message);
        label.setTextFill(textColor);
        label.setFont(new Font(25));
        label.setPadding(new Insets(10));
        label.setStyle("-fx-font-family: 'MV Boli';");
        label.setTranslateX(0);
        label.setTranslateY(150);

        Popup popup = new Popup();
        popup.getContent().add(label);

        popup.setAutoHide(true);
        popup.setOnShown(e -> {
            timeline = new Timeline(new KeyFrame(Duration.seconds(duration), ev -> popup.hide()));
            timeline.play();
        });

        popup.show(window);
    }

    private void showPopupMessage(String message){
        Label label = new Label(message);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(14));
        label.setPadding(new Insets(10));
        label.setStyle("-fx-font-family: 'MV Boli';");
        label.setTranslateX(800);
        label.setTranslateY(500);

        Popup popup = new Popup();
        popup.getContent().add(label);

        popup.setAutoHide(true);
        popup.setOnShown(e -> {
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> popup.hide()));
            timeline.play();
        });

        popup.show(pauseStage);
    }
}
