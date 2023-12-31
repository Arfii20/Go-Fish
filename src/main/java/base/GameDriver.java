package base;

import base.gofish.GameEngine;
import base.gofish.Player;
import base.gofish.deck.Card;
import base.gofish.saveAndMusic.Music;
import base.gofish.saveAndMusic.SavesLocation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;


/**
 * The GameDriver class serves as the main entry point for the card game application.
 * It manages the game's scenes, user interface elements, and game engine interactions.
 * This class extends the JavaFX Application class and sets up the graphical user interface.
 *
 * @author Arefin Ahammed
 * @version 2.6
 */
public class GameDriver extends Application {
    private static Stage window;
    private GameEngine game;
    private Timeline timeline;
    private BoxBlur blur;
    private String saveLocation;
    private Label deckCardsLeft;
    private Label playerSelected;

    // All the scenes
    private Scene OpeningScene;
    private Scene playerNameScene;
    // private Scene totalPointsScene;
    private Scene difficultyLevelScene;

    private Scene startMenuScene;
    private Scene pauseGameScene;

    private Scene loadGameMenuScene;
    private Scene saveGameMenuScene;
    private Scene rulesScene;
    private Scene settingsScene;
    private Scene preGameScene;

    private Scene displayCard;
    private Scene mainPageScene;

    private Scene leaderboardScene;
    private Scene gameOverScene;

    // <--------------- welcome Scene Elements --------------->
    @FXML
    private Label pressAnyKeyLabel;

    // <--------------------- Max points --------------------->
    // @FXML
    // private ComboBox<Integer> pointsCombobox;
    // @FXML
    // private Button setMaxValueButton;

    // <------------------- Player number -------------------->
    @FXML
    private TextField getPlayerFromInput;
    @FXML
    private Label playerAdded;

    // <------------------- Player number -------------------->
    @FXML
    private RadioButton easyRadio, mediumRadio, hardRadio;

    // <--------------------- Settings ---------------------->
    @FXML
    private Label changeLocLabel;
    @FXML
    private AnchorPane difficultyAnchorPane;
    @FXML
    private Slider volumeSlider, buttonVolumeSlider;
    @FXML
    private RadioButton easyRadioSettings, mediumRadioSettings, hardRadioSettings;

    // <-------------------- Load Game --------------------->
    @FXML
    private VBox loadGameVbox;

    // <-------------------- Save Game --------------------->
    @FXML
    private VBox saveGameVbox;
    @FXML
    private Label saveGameLocation;

    // <------------------- Display Card ------------------->
    @FXML
    private ImageView displayCardImage;

    // <-------------------- Main Page --------------------->
    @FXML
    private Label mainPlayerLabel, playerTurnLabel;
    @FXML
    private Label player2Cards, player3Cards, player4Cards, player5Cards;
    @FXML
    private StackPane mainPlayerCardImages;
    @FXML
    private VBox centerDeck;
    @FXML
    private BorderPane mainBorderPane;

    // <------------------ Leaderboard -------------------->
    @FXML
    private VBox playerPositions;


    // <-------------------- Game Over---------------------->
    @FXML
    private Label winnerLabel;
    @FXML
    private Button restartGameButton;


    // <-----------------------------------  Start Restart ------------------------------------->
    /**
     * The main entry point for launching the Go Fish game application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args){
        launch();
    }


    /**
     * Initializes the JavaFX application and sets up the game's graphical user interface.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If there's an error while loading FXML files.
     */
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;

        blur = new BoxBlur();
        blur.setWidth(10);
        blur.setHeight(10);
        blur.setIterations(1);

        Random random = new Random();
        long seed = random.nextLong();
        try {
            game = new GameEngine(seed);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Load the FXML files and set the controller to this one. Also create scenes for the fxml files.
        FXMLLoader openingXML = new FXMLLoader(getClass().getResource("/PreGame/1openingScene.fxml"));
        openingXML.setController(this);
        OpeningScene = new Scene(openingXML.load(), 1280, 720);

        FXMLLoader startMenuXML = new FXMLLoader(getClass().getResource("/Menus/startMenu.fxml"));
        startMenuXML.setController(this);
        startMenuScene = new Scene(startMenuXML.load());

        FXMLLoader pauseGameXML = new FXMLLoader(getClass().getResource("/Menus/pauseMenu.fxml"));
        pauseGameXML.setController(this);
        pauseGameScene = new Scene(pauseGameXML.load());

        FXMLLoader playerNameXML = new FXMLLoader(getClass().getResource("/PreGame/2playerName.fxml"));
        playerNameXML.setController(this);
        playerNameScene = new Scene(playerNameXML.load());

        // FXMLLoader totalPointsXML = new FXMLLoader(getClass().getResource("/PreGame/3totalPoints.fxml"));
        // totalPointsXML.setController(this);
        // totalPointsScene = new Scene(totalPointsXML.load());

        FXMLLoader difficultyLevelXML = new FXMLLoader(getClass().getResource("/PreGame/4difficultyLevel.fxml"));
        difficultyLevelXML.setController(this);
        difficultyLevelScene = new Scene(difficultyLevelXML.load());

        FXMLLoader preGameXML = new FXMLLoader(getClass().getResource("/PreGame/5preGame.fxml"));
        preGameXML.setController(this);
        preGameScene = new Scene(preGameXML.load());

        // Start and Pause Menu items
        FXMLLoader loadGameMenuXML = new FXMLLoader(getClass().getResource("/Menus/MenuItems/loadGame.fxml"));
        loadGameMenuXML.setController(this);
        loadGameMenuScene = new Scene(loadGameMenuXML.load());

        FXMLLoader saveGameMenuXML = new FXMLLoader(getClass().getResource("/Menus/MenuItems/saveGame.fxml"));
        saveGameMenuXML.setController(this);
        saveGameMenuScene = new Scene(saveGameMenuXML.load());

        FXMLLoader settingsXML = new FXMLLoader(getClass().getResource("/Menus/MenuItems/settings.fxml"));
        settingsXML.setController(this);
        settingsScene = new Scene(settingsXML.load());

        FXMLLoader rulesXML = new FXMLLoader(getClass().getResource("/Menus/MenuItems/rules.fxml"));
        rulesXML.setController(this);
        rulesScene = new Scene(rulesXML.load());

        // MidGame
        FXMLLoader displayCardXML = new FXMLLoader(getClass().getResource("/MidGame/displayCard.fxml"));
        displayCardXML.setController(this);
        displayCard = new Scene(displayCardXML.load());

        FXMLLoader mainPageXML = new FXMLLoader(getClass().getResource("/MidGame/mainPage.fxml"));
        mainPageXML.setController(this);
        mainPageScene = new Scene(mainPageXML.load());

        // PostGame
        FXMLLoader leaderboardXML = new FXMLLoader(getClass().getResource("/PostGame/1leaderboard.fxml"));
        leaderboardXML.setController(this);
        leaderboardScene = new Scene(leaderboardXML.load());

        FXMLLoader gameOverXML = new FXMLLoader(getClass().getResource("/PostGame/2gameOver.fxml"));
        gameOverXML.setController(this);
        gameOverScene = new Scene(gameOverXML.load());

        // Add logo to the application
        window.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/base/logo.png"))));

        // Set title and first scene of the game
        window.setTitle("Go Fish");
        if (window.getScene() != null) sceneChanger(OpeningScene);
        else window.setScene(OpeningScene);

        window.setMinWidth(1280);
        window.setMinHeight(720);

        // Load the save location
        saveLocation = SavesLocation.loadSaveLocation();

        // Fade Animation for the press any key to start
        FadeTransition fade = new FadeTransition();
        fade.setNode(pressAnyKeyLabel);
        fade.setDuration(Duration.millis(2000));
        fade.setCycleCount((TranslateTransition.INDEFINITE));
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        // Press any key to set scene to get number of players from players
        OpeningScene.setOnKeyPressed(event -> {
            OpeningScene.setOnKeyPressed(null);
            sceneChanger(startMenuScene);
        });

        // Add listener to the enter key so that player is added when enter pressed
        getPlayerFromInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) addPlayers();
        });

        mainPageScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) sceneChanger(pauseGameScene);
        });

        pauseGameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) sceneChanger(game.isRoundOn() ? mainPageScene : leaderboardScene);
        });

        // ToggleGroup of Difficulty
        ToggleGroup difficultyPageToggle = new ToggleGroup();
        easyRadio.setToggleGroup(difficultyPageToggle);
        mediumRadio.setToggleGroup(difficultyPageToggle);
        hardRadio.setToggleGroup(difficultyPageToggle);
        easyRadio.setOnAction(e -> handleDifficultySelection(difficultyPageToggle));
        mediumRadio.setOnAction(e -> handleDifficultySelection(difficultyPageToggle));
        hardRadio.setOnAction(e -> handleDifficultySelection(difficultyPageToggle));

        // ToggleGroup of Difficulty
        ToggleGroup settingsPageToggle = new ToggleGroup();
        easyRadioSettings.setToggleGroup(settingsPageToggle);
        mediumRadioSettings.setToggleGroup(settingsPageToggle);
        hardRadioSettings.setToggleGroup(settingsPageToggle);
        easyRadioSettings.setOnAction(e -> handleDifficultySelectionForSettings(settingsPageToggle));
        mediumRadioSettings.setOnAction(e -> handleDifficultySelectionForSettings(settingsPageToggle));
        hardRadioSettings.setOnAction(e -> handleDifficultySelectionForSettings(settingsPageToggle));

        // Music and Button clicks
        Music.getMusic(volumeSlider, buttonVolumeSlider);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> Music.setMusicVolume(newValue.doubleValue() / 100));
        buttonVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> Music.setButtonVolume(newValue.doubleValue() / 100));

        window.show();
    }


    /**
     * Restarts the Go Fish game by reinitializing the game state and graphical user interface.
     *
     * @throws IOException If there's an error during the game restart.
     */
    public void restartGame() throws IOException {
        this.start(window);
    }


    // <-------------------------------------  Main Game--------------------------------------->
    /**
     * Adds a new player to the Go Fish game based on the input provided by the user.
     * This method is called when the user enters their name in the input field and confirms it.
     */
    public void addPlayers(){
        if(getPlayerFromInput.getText().isEmpty()){
            getPlayerFromInput.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
        else {
            String name = getPlayerFromInput.getText();
            getPlayerFromInput.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px;");
            getPlayerFromInput.setDisable(true);
            game.addPlayers(name);
            mainPlayerLabel.setText(name);

            playerAdded.setText("Welcome to the game " + name);
            getPlayerFromInput.clear();
            playerAdded.setVisible(true);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), playerAdded);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(800), playerAdded);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            SequentialTransition fadeSequence = new SequentialTransition(fadeIn, fadeOut);
//            fadeSequence.setOnFinished(event -> sceneChanger(totalPointsScene));
            fadeSequence.setOnFinished(event -> sceneChanger(difficultyLevelScene));
            fadeSequence.play();
        }
    }


    /**
     * Animates the distribution of cards at the beginning of a Go Fish game round.
     * Cards are distributed to players' hands and the center deck.
     * This method sets up a sequential transition to animate the card distribution process.
     * Once all cards are distributed, it updates the game state to start turns.
     */
    private void distributeCards() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ImageView cardImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/base/cardBack.png"))));
        cardImageView.setFitHeight(180);
        cardImageView.setFitWidth(115);
        centerDeck.getChildren().add(cardImageView);
        playerTurnLabel.setText("Distributing");

        for (int i = 0; i < 25; i++) {
            double targetX, targetY;
            switch (i % 5) {
                case 0 -> {
                    targetX = 0;
                    targetY = 260;
                }
                case 1 -> {
                    targetX = -380;
                    targetY = 190;
                }
                case 2 -> {
                    targetX = -380;
                    targetY = -45;
                }
                case 3 -> {
                    targetX = 380;
                    targetY = -45;
                }
                default -> {
                    targetX = 380;
                    targetY = 190;
                }
            }
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.4), cardImageView);
            transition.setFromX(0);
            transition.setFromY(0);
            transition.setToX(targetX);
            transition.setToY(targetY);
            if (i == 24) {
                transition.setOnFinished(e -> {
                    centerDeck.getChildren().remove(cardImageView);
                    cardNumberChanger(5, 1);
                    playerTurnLabel.setText("Your Turn");
                    PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
                    delay.setOnFinished(ev -> {
                        addDeck();
                        deckCardsLeft.setText("27 Cards Left");
                        game.roundStart();
                        game.startTurn();
                        addCardImages();
                    });
                    delay.play();
                });
            }
            else {
                int finalI = i;
                transition.setOnFinished(e -> {
                    cardNumberChanger(finalI % 5 + 1, 1);
                });
            }
            sequentialTransition.getChildren().add(transition);
        }
        sequentialTransition.play();
    }


    /**
     * Adds a deck of cards to the center of the game interface.
     * This method clears the center deck, which is a visual representation of the game deck.
     * It then creates a new deck with a label indicating the number of cards left in the deck.
     * Seven card backs are displayed face down on the deck.
     */
    private void addDeck() {
        centerDeck.getChildren().clear();

        Region region1 = new Region();
        region1.setPrefSize(222, 200);
        BorderPane.setAlignment(region1, Pos.CENTER);
        mainBorderPane.setLeft(region1);

        Region region2 = new Region();
        region2.setPrefSize(222, 200);
        BorderPane.setAlignment(region2, Pos.CENTER);
        mainBorderPane.setRight(region2);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMaxHeight(Double.POSITIVE_INFINITY);
        anchorPane.setMaxWidth(200);
        anchorPane.setPrefWidth(200);

        deckCardsLeft = new Label();
        deckCardsLeft.setAlignment(Pos.CENTER);
        deckCardsLeft.setLayoutX(-3);
        deckCardsLeft.setLayoutY(-5);
        deckCardsLeft.setPrefHeight(28);
        deckCardsLeft.setPrefWidth(202);
        deckCardsLeft.setStyle("-fx-font-size: 20");

        anchorPane.getChildren().add(deckCardsLeft);

        String imageUrl = "/base/cardBack.png";

        for (int i = 0; i < 7; i++) {
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageUrl))));
            imageView.setFitHeight(180);
            imageView.setFitWidth(115);
            imageView.setRotate(-90);
            imageView.setTranslateX(30 + (i * 3));
            imageView.setTranslateY(5 + (i * 3));

            anchorPane.getChildren().add(imageView);
        }

        centerDeck.getChildren().add(anchorPane);
    }


    /**
     * Adds card images to the main player's card display.
     * This method clears the existing card images from the main player's display and replaces them with the player's current cards.
     * Each card image is represented as an ImageView with mouse interactions for hover and click events.
     * Card images are rotated to reveal the front when displayed.
     * The method also handles card interactions when the real player clicks on a card.
     */
    private void addCardImages() {
        mainPlayerCardImages.getChildren().clear();
        mainPlayerCardImages.setAlignment(Pos.CENTER);

        List<Card> cards = this.game.getRealPlayer().getCards();
        int totalImages = cards.size();

        for (int i = 0; i < totalImages; i++) {
            Card finalCard = cards.get(i);

            ImageView imageView = new ImageView((new Image(Objects.requireNonNull(getClass().getResourceAsStream("/base/cardBack.png")))));
            imageView.setFitHeight(180);
            imageView.setFitWidth(130);
            imageView.setTranslateX((i - (double) (totalImages - 1) / 2) * 25);
            imageView.setOnMouseEntered(e -> {
                if (game.getRealPlayer() == game.getCurrentPlayer()){
                    imageView.setFitHeight(200);
                    imageView.setFitWidth(140);
                }
            });
            imageView.setOnMouseExited(e -> {
                if (game.getRealPlayer() == game.getCurrentPlayer()) {
                    imageView.setFitHeight(180);
                    imageView.setFitWidth(130);
                }
            });
            imageView.setOnMousePressed(e -> {
                if (game.getRealPlayer() == game.getCurrentPlayer()) playerMove(finalCard);
            });

            RotateTransition rotate1 = new RotateTransition(Duration.seconds(0.5), imageView);
            rotate1.setAxis(Rotate.Y_AXIS);
            rotate1.setFromAngle(180);
            rotate1.setToAngle(270);
            rotate1.setInterpolator(Interpolator.LINEAR);
            rotate1.setOnFinished(e -> {
                imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/" + finalCard.getType() + "/" + finalCard.getFullName() + ".png"))));
                RotateTransition rotate2 = new RotateTransition(Duration.seconds(0.5), imageView);
                rotate2.setAxis(Rotate.Y_AXIS);
                rotate2.setFromAngle(270);
                rotate2.setToAngle(360);
                rotate2.setInterpolator(Interpolator.LINEAR);
                rotate2.play();
            });
            rotate1.play();

            mainPlayerCardImages.getChildren().add(imageView);
        }
    }


    /**
     * Handles a player's move when they select a card to play.
     * <p>
     * This method first checks if a player has been selected to ask for a card.
     * If not, it displays a message prompting the player to select a player.
     * If a player is selected, it attempts to play the selected card.
     * - If the move is invalid (no such card with the selected player), it shows a "Go Fish" message.
     * - If a valid move is made, it updates the game state, displays relevant messages, and handles card animations.
     * </p>
     * After the move is processed, it resets the selected player for the next turn.
     *
     * @param finalCard The card the player wants to play.
     *
     */
    private void playerMove(Card finalCard) {
        if (playerSelected == null) this.showPopupMessage("Please select a Player", Color.CYAN);
        else {
            List<Card> card = this.game.singleTurn(playerSelected.getText(), finalCard.getFullName());
            this.game.getPlayerProbabilities().updateProbabilitiesToOne(finalCard.getValue(), this.game.getCurrentPlayer());
            if (card == null) {
                PauseTransition delay = this.showPopupMessage("WRONG! GO FISH", Color.ORANGERED, 1);
                delay.setOnFinished(ev -> {
                    Card card1 = this.game.getDeck().draw();
                    if (card1 != null && card1.getValue() == finalCard.getValue()) {
                        this.deckCardsLeft.setText((Integer.parseInt(deckCardsLeft.getText().split(" ")[0]) - 1) + " Cards Left");
                        this.game.getCurrentPlayer().addToHand(card1);
                        this.displayCard2(card1);
                        PauseTransition delay1 = new PauseTransition(Duration.seconds(4));
                        delay1.setOnFinished(e -> {
                            this.showPopupMessage("You got the card you asked for. Ask again", Color.ORANGERED);
                        });
                        delay1.play();
                        this.addCardImages();
                    }
                    else if (card1 != null) {
                        this.deckCardsLeft.setText((Integer.parseInt(deckCardsLeft.getText().split(" ")[0]) - 1) + " Cards Left");
                        this.game.getCurrentPlayer().addToHand(card1);
                        this.displayCard(card1);
                        this.addCardImages();
                    }
                    else {
                        if (!this.game.allEmpty()) {
                            this.game.endTurn();
                            this.game.startTurn();
                            this.botTurn();
                        }
                        else if (this.game.roundOver()) {
                            this.game.endTurn();
                            this.sceneChanger(leaderboardScene);
                            this.game.setRoundOn(false);
                            this.addLeaderboard();
                        }
                    }
                });
                delay.play();
            }
            else {
                this.game.getCurrentPlayer().addToHand(card);
                PauseTransition delay = this.showPopupMessage("You received " + card.size() + " Card(s) from " + playerSelected.getText(), Color.CYAN, 3);
                this.cardNumberChanger(Integer.parseInt(String.valueOf(playerSelected.getText().charAt(playerSelected.getText().length()-1))), -card.size());
                this.addCardImages();
                delay.setOnFinished(e -> {
                    if (this.game.roundOver()) {
                        this.sceneChanger(leaderboardScene);
                        this.game.setRoundOn(false);
                        this.addLeaderboard();
                    }
                    else if (this.game.getCurrentPlayer().cardFinished()) botTurn();
                });
                delay.play();
            }
            this.playerSelected.setStyle("-fx-text-fill: WHITE; -fx-font-size: 30");
            this.playerSelected = null;
        }
    }


    /**
     * Displays the selected card with a pop-up effect on the game screen.
     * <p>
     * This method creates a pop-up window to display the selected card image with a blur effect on the main game screen.
     * It uses JavaFX animations to create a smooth transition when showing and hiding the card pop-up.
     * </p>
     * After displaying the card for a few seconds, it closes the pop-up and checks the game state:
     * - If the round is over, it transitions to the leaderboard scene.
     * - If not, and there are still cards in the deck, it ends the player's turn and starts the next player's turn (for human and AI players).
     *
     * @param card The card to be displayed.
     */
    private void displayCard(Card card){
        mainPageScene.getRoot().setEffect(blur);
        displayCardImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/" + card.getType() + "/" + card.getFullName() + ".png"))));

        Stage overlayStage = new Stage();
        overlayStage.initStyle(StageStyle.UNDECORATED);
        overlayStage.initModality(Modality.APPLICATION_MODAL);
        overlayStage.initStyle(StageStyle.TRANSPARENT);
        overlayStage.setScene(displayCard);
        overlayStage.setOpacity(0.95);
        overlayStage.show();

        displayCard.setFill(null);
        ParallelTransition parallel = getParallelTransition(displayCard);
        parallel.setOnFinished(e -> {
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(ev -> {
                window.requestFocus();
                mainPageScene.getRoot().setEffect(null);
                overlayStage.close();
                PauseTransition delay2 = new PauseTransition(Duration.seconds(1));
                delay2.setOnFinished(ee -> {
                    if (this.game.roundOver()) {
                        this.sceneChanger(leaderboardScene);
                        this.game.setRoundOn(false);
                        this.addLeaderboard();
                    }
                    else if (!this.game.allEmpty()) {
                        this.game.endTurn();
                        this.game.startTurn();
                        this.botTurn();
                    }
                });
                delay2.play();
            });
            delay.play();
        });
        parallel.play();
    }


    /**
     * Displays the selected card with a pop-up effect on the game screen.
     * <p>
     * This method creates a pop-up window to display the selected card image with a blur effect on the main game screen.
     * It uses JavaFX animations to create a smooth transition when showing and hiding the card pop-up.
     * </p>
     * After displaying the card for a few seconds, it closes the pop-up and restores the main game screen's focus.
     * This method is typically used when the player draws a card from the deck, providing a visual representation of the card.
     *
     * @param card The card to be displayed.
     */
    private void displayCard2(Card card){
        mainPageScene.getRoot().setEffect(blur);
        displayCardImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/" + card.getType() + "/" + card.getFullName() + ".png"))));

        Stage overlayStage = new Stage();
        overlayStage.initStyle(StageStyle.UNDECORATED);
        overlayStage.initModality(Modality.APPLICATION_MODAL);
        overlayStage.initStyle(StageStyle.TRANSPARENT);
        overlayStage.setScene(displayCard);
        overlayStage.setOpacity(0.95);
        overlayStage.show();

        displayCard.setFill(null);
        ParallelTransition parallel = getParallelTransition(displayCard);
        parallel.setOnFinished(e -> {
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(ev -> {
                window.requestFocus();
                mainPageScene.getRoot().setEffect(null);
                overlayStage.close();
            });
            delay.play();
        });
        parallel.play();
    }


    /**
     * Simulates a bot player's turn in the game.
     * <p>
     * This method represents the actions taken by a bot player during their turn in the game.
     * It handles bot player decisions such as asking for a card, responding to card requests, and managing the game state.
     * The bot player's actions are animated and displayed using JavaFX animations and pop-up messages.
     * </p>
     * If the game round is over or the deck is empty, it ends the turn and proceeds to the next phase of the game.
     */
    private void botTurn() {
        if (this.game.allEmpty()) {
            while (!this.game.getDeck().isEmpty()) {
                this.game.getCurrentPlayer().addToHand(this.game.getDeck().draw());
                this.deckCardsLeft.setText(this.game.getDeck().size() + " Cards Left");
            }
            this.cardNumberChanger(this.game.getCurrentPlayer());
            this.game.endTurn();
            this.sceneChanger(leaderboardScene);
            this.game.setRoundOn(false);
            this.addLeaderboard();
            return;
        }

        createAndPlaySplashTransition(playerTurnLabel, game.getCurrentPlayer() + "'s Turn");

        Pair<Player, Card> playerCard;
        try {
            playerCard = this.game.getPlayerCardForBots();
        }
        catch (IllegalArgumentException e) {
            this.game.endTurn();
            this.game.startTurn();
            if (game.getCurrentPlayer() != game.getRealPlayer()) botTurn();
            else addAllCardsToHand();
            return;
        }

        Player player = playerCard.getKey();
        Card card = playerCard.getValue();
        this.game.getPlayerProbabilities().updateProbabilitiesToOne(card.getValue(), this.game.getCurrentPlayer());

        PauseTransition delay1 = this.showPopupMessage(this.game.getCurrentPlayer() + " asked " + card.getName() + " from " + player, Color.ORANGE, 3);
        delay1.setOnFinished(e -> {
            List<Card> cards = this.game.singleTurn(player.toString(), card.getFullName());
            if (cards == null) {
                PauseTransition delay = this.showPopupMessage("WRONG! GO FISH", Color.ORANGERED, 1.5);
                delay.setOnFinished(ee -> {
                    Card card1 = this.game.getDeck().draw();
                    if (card1 != null && card1.getValue() == card.getValue()) {
                        PauseTransition delay2 = this.showPopupMessage(this.game.getCurrentPlayer() + " received the same number. Ask again", Color.CYAN, 3);
                        this.deckCardsLeft.setText(this.game.getDeck().size() + " Cards Left");
                        this.game.getCurrentPlayer().addToHand(card1);
                        this.cardNumberChanger(game.getCurrentPlayer());
                        new PauseTransition(Duration.seconds(4));
                        delay2.setOnFinished(eee -> {
                            if (game.getCurrentPlayer() != game.getRealPlayer()) botTurn();
                        });
                        delay2.play();
                        return;
                    }
                    else if (card1 != null) {
                        this.deckCardsLeft.setText(this.game.getDeck().size() + " Cards Left");
                        this.game.getCurrentPlayer().addToHand(card1);
                        this.cardNumberChanger(this.game.getCurrentPlayer());
                    }
                    this.game.endTurn();
                    if (!this.game.roundOver()) {
                        this.game.startTurn();
                        PauseTransition pause = new PauseTransition(Duration.seconds(2));
                        pause.setOnFinished(eee -> {
                            if (game.getCurrentPlayer() != game.getRealPlayer()) {
                                botTurn();
                            }
                            else if (game.getRealPlayer().totalCards() == 0) {
                                this.game.endTurn();
                                this.game.startTurn();
                                botTurn();
                            }
                            else {
                                addAllCardsToHand();
                            }
                        });
                        pause.play();
                    }
                    else {
                        this.sceneChanger(leaderboardScene);
                        this.game.setRoundOn(false);
                        this.addLeaderboard();
                    }
                });
                delay.play();
            }
            else {
                this.game.getCurrentPlayer().addToHand(cards);
                PauseTransition delay = this.showPopupMessage(this.game.getCurrentPlayer() + " received " + cards.size() + " Card(s) from " + player, Color.CYAN, 3);
                this.cardNumberChanger(player);
                this.cardNumberChanger(game.getCurrentPlayer());
                delay.setOnFinished(ee -> {
                    if (game.getCurrentPlayer() != game.getRealPlayer()) botTurn();
                });
                delay.play();
            }
        });
        delay1.play();
    }


    /**
     * Handles the case where the current player is the real player and there are no more moves for bots.
     * <p>
     * This method is called when it's the real player's turn, and there are no more available moves for the bot players.
     * It transfers all remaining cards from the deck to the real player's hand and proceeds to the next phase of the game.
     * </p>
     * If the game round is over after this action, it triggers the leaderboard display.
     */
    private void addAllCardsToHand() {
        playerTurnLabel.setText("Your Turn");
        if (this.game.allEmpty()) {
            while (!this.game.getDeck().isEmpty()) {
                this.game.getCurrentPlayer().addToHand(this.game.getDeck().draw());
                this.deckCardsLeft.setText(this.game.getDeck().size() + " Cards Left");
            }
            sceneChanger(leaderboardScene);
            this.game.setRoundOn(false);
            addLeaderboard();
        }
    }


    // <----------------------------------  Selected Label ----------------------------------->
    /**
     * Handles the selection of a player label by the real player.
     * <p>
     * This method is called when a player label is clicked by the real player during their turn.
     * It highlights the selected player label and stores it for the player's turn action.
     * </p>
     *
     * @param event The MouseEvent triggered by the player label click.
     */
    @FXML
    public void playerSelected(MouseEvent event) {
        Music.playButtonSoundEffect();
        if (game.getCurrentPlayer() == game.getRealPlayer()){
            if (playerSelected != null) playerSelected.setStyle("-fx-text-fill: WHITE; -fx-font-size: 30; -fx-background-color: rgba(66, 66, 66, 0.3);");
            playerSelected = (Label) event.getSource();
            playerSelected.setStyle("-fx-text-fill: CYAN; -fx-font-size: 30; -fx-background-color: rgba(66, 66, 66, 0.3);");
        }
    }


    /**
     * Handles the mouse entering a player label by the real player.
     * <p>
     * This method is called when the mouse pointer enters a player label during the real player's turn.
     * It provides a visual indication that the player label can be selected for an action.
     *
     * @param event The MouseEvent triggered when the mouse enters the player label area.
     */
    @FXML
    private void handleMouseEntered(MouseEvent event) {
        if (playerSelected == null && game.getCurrentPlayer() == game.getRealPlayer()) ((Label) event.getSource()).setStyle("-fx-font-size: 30; -fx-text-fill: cyan; -fx-background-color: rgba(66, 66, 66, 0.3);");
    }


    /**
     * Handles the mouse exiting a player label by the real player.
     * <p>
     * This method is called when the mouse pointer exits a player label during the real player's turn.
     * It reverts the visual style of the player label to its normal state when not selected.
     * </p>
     *
     * @param event The MouseEvent triggered when the mouse exits the player label area.
     */
    @FXML
    private void handleMouseExited(MouseEvent event) {
        if (playerSelected == null && game.getCurrentPlayer() == game.getRealPlayer()) ((Label) event.getSource()).setStyle("-fx-font-size: 30; -fx-background-color: rgba(66, 66, 66, 0.3);");
    }


    // <---------------------------------------  Menus ---------------------------------------->
    /**
     * Handles events triggered by buttons in the start menu.
     * This method plays a button sound effect and performs actions
     * based on the button clicked, such as changing scenes, loading
     * saved game files, displaying settings, showing rules, or quitting
     * the application.
     *
     * @param event The MouseEvent triggered by the button click.
     */
    @FXML
    public void startMenu(MouseEvent event){
        Music.playButtonSoundEffect();
        Button pressedButton = (Button) event.getSource();
        switch (pressedButton.getText()) {
            case "START" -> sceneChanger(playerNameScene);
            case "LOAD" -> loadSaveFiles();
            case "SETTINGS" -> {
                changeLocLabel.setText(saveLocation);
                difficultyAnchorPane.setVisible(game.isStarted());
                sceneChanger(settingsScene);
            }
            case "RULES" -> sceneChanger(rulesScene);
            case "QUIT" -> Platform.exit();
        }
        event.consume();
    }


    /**
     * Handles events triggered by buttons in the pause menu.
     * This method plays a button sound effect and performs actions
     * based on the button clicked, such as resuming the game,
     * restarting the game, saving the game state, loading saved
     * game files, displaying rules, showing settings, or quitting
     * the game.
     * If quitting, it also attempts to save the game state before exiting.
     *
     * @param event The MouseEvent triggered by the button click.
     * @throws IOException If there is an error related to input/output.
     */
    @FXML
    public void pauseMenu(MouseEvent event) throws IOException {
        Music.playButtonSoundEffect();
        Button pressedButton = (Button) event.getSource();
        switch (pressedButton.getText()) {
            case "RESUME" -> {
                mainPageScene.getRoot().requestFocus();
                sceneChanger(game.isRoundOn() ? mainPageScene : leaderboardScene);
            }
            case "RESTART" -> {
                mainPageScene.getRoot().requestFocus();
                this.start(window);
            }
            case "SAVE" -> {
                saveGame();
            }
            case "LOAD" -> loadSaveFiles();
            case "RULES" -> sceneChanger(rulesScene);
            case "SETTINGS" -> {
                difficultyAnchorPane.setVisible(game.isStarted());
                sceneChanger(settingsScene);
            }
            case "QUIT" -> {
                if (this.game.getCurrentPlayer() != null){
                    try {
                        game.saveState(saveLocation + "save.obj");
                        showPopupMessage("Game Saved");
                    } catch (Exception e) {
                        System.out.println("There was an error saving the game");
                    }
                    PauseTransition delay = new PauseTransition(Duration.seconds(2));
                    delay.setOnFinished(e -> Platform.exit());
                    delay.play();
                }
                else {
                    Platform.exit();
                }
            }
        }
        event.consume();
    }


    /**
     * Handles events triggered by buttons in the leaderboard menu.
     * This method plays a button sound effect and performs actions
     * based on the button clicked.
     * It allows the user to either start the next round of the game or end the entire game,
     * displaying the winner's name if the game ends.
     *
     * @param event The MouseEvent triggered by the button click.
     */
    @FXML
    public void leaderboardMenu(MouseEvent event) {
        Music.playButtonSoundEffect();
        Button pressedButton = (Button) event.getSource();
        String text = pressedButton.getText();
        switch (text) {
            case "Start Next Round" -> {
                this.game.setRoundOn(true);
                sceneChanger(mainPageScene);
                centerDeck.getChildren().clear();
                this.game.getDeck().restockDeck();
                this.game.getPlayerProbabilities().resetProbabilities();
                distributeCards();
            }
            case "End Game" -> {
                Player winner = game.getWinner();
                winnerLabel.setText(winner + " has won");
                sceneChanger(gameOverScene);
            }
        }
        event.consume();
    }


    /**
     * Handles events triggered by buttons in the game over menu.
     * This method plays a button sound effect and performs actions based on the button clicked.
     * It allows the user to either restart the game or return to the main menu.
     *
     * @param event The MouseEvent triggered by the button click.
     * @throws IOException If there is an error related to input/output
     */
    @FXML
    public void gameOverMenu(MouseEvent event) throws IOException {
        Music.playButtonSoundEffect();
        String text = ((Button) event.getSource()).getText();
        switch (text) {
            case "Restart" -> this.restartGame();
            case "Main Menu" -> sceneChanger(startMenuScene);
        }
        event.consume();
    }


    /**
     * Handles events triggered by the back button in various menus.
     * If the game is started, it returns to the pause menu; otherwise, it returns to the start menu.
     * It also saves the current volume settings for the game's background music.
     */
    @FXML
    public void backButton() {
        Music.playButtonSoundEffect();
        sceneChanger(game.isStarted() ? pauseGameScene : startMenuScene);
        Music.saveVolume();
    }


    /**
     * Populates and displays the leaderboard with player rankings and scores.
     * This method clears the existing leaderboard display and creates entries
     * for each player in sorted order based on their scores.
     * Each entry includes the player's name and score.
     * The leaderboard is then updated to reflect the current game state.
     */
    private void addLeaderboard() {
        playerPositions.getChildren().clear();
        List<Player> players = this.game.getSortedPlayers();

        for (Player player : players) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setMaxWidth(800);
            anchorPane.setPrefHeight(44);

            Label nameLabel = new Label(player.toString());
            nameLabel.setLayoutX(446);
            nameLabel.setLayoutY(8);
            nameLabel.setPrefHeight(36);
            nameLabel.setPrefWidth(250);
            nameLabel.setStyle("-fx-font-size: 25");
            AnchorPane.setLeftAnchor(nameLabel, 220.0);

            Label scoreLabel = new Label(String.valueOf(player.getPoints()));
            scoreLabel.setLayoutX(718);
            scoreLabel.setLayoutY(9);
            scoreLabel.setStyle("-fx-font-size: 25");
            AnchorPane.setRightAnchor(scoreLabel, 220.0);

            anchorPane.getChildren().addAll(nameLabel, scoreLabel);
            playerPositions.getChildren().add(anchorPane);
            VBox.setMargin(anchorPane, new Insets(10, 0, 10, 0));
        }
    }


    // <--------------------------------- Loading and saving ---------------------------------->
    /**
     * Loads and displays saved game files from the specified save location.
     * This method scans the save location directory for save files, populates
     * the UI with loadable game options, including the option to delete saved games,
     * and presents them to the player for loading.
     * If no saved games are found, a message is displayed indicating that there
     * are no saved games available.
     */
    private void loadSaveFiles(){
        File folder = new File(saveLocation);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) Arrays.sort(listOfFiles, Comparator.comparing(File::getName));
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
                    if (fileName.equals("save.obj")) loadGameButton = new Button("AutoSave");
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
        sceneChanger(loadGameMenuScene);
    }


    /**
     * Loads a saved game based on the button pressed by the player.
     * The loaded game state replaces the current game state, allowing the player to continue from where they left off.
     *
     * @param event The MouseEvent triggered by clicking a load game button.
     */
    public void loadGame(MouseEvent event){
        Music.playButtonSoundEffect();
        Button pressedButton = (Button) event.getSource();
        HBox parentBox = (HBox) pressedButton.getParent();

        switch (parentBox.getId()) {
            case "1" -> game = GameEngine.loadState(saveLocation + "save1.obj");
            case "2" -> game = GameEngine.loadState(saveLocation + "save2.obj");
            case "3" -> game = GameEngine.loadState(saveLocation + "save3.obj");
            default -> game = GameEngine.loadState(saveLocation + "save.obj");
        }

        if (this.game == null) {
            showPopupMessage("Save file corrupted");
            return;
        }

        mainPlayerLabel.setText(this.game.getRealPlayer().toString());
        this.playerTurnLabel.setText(game.getCurrentPlayer() + "'s Turn");
        createAndPlaySplashTransition(playerTurnLabel, game.getCurrentPlayer() + "'s Turn");

        for (Player player: this.game.getSortedPlayers()) {
            try {
                this.cardNumberChanger(player);
            }
            catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        addDeck();
        deckCardsLeft.setText(this.game.getDeck().size() + " Cards Left");

        sceneChanger(mainPageScene);
        addCardImages();
    }


    /**
     * Deletes a saved game file based on the button pressed by the player.
     *
     * @param event The MouseEvent triggered by clicking delete saved game button.
     */
    public void deleteSavedGame(MouseEvent event){
        Music.playButtonSoundEffect();
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


    /**
     * Saves the current game state to a specified location and displays saved game slots.
     * Allows the player to save their progress or overwrite existing saved games.
     */
    private void saveGame(){
        saveLocation = SavesLocation.loadSaveLocation();
        File folder = new File(saveLocation);
        File[] listOfFiles = folder.listFiles();
        saveGameVbox.getChildren().clear();
        saveGameLocation.setText(saveLocation);

        boolean found = false;
        try{
            for (int i = 1; i < 4; i++) {
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
                            if (this.game.getCurrentPlayer() != null){
                                try {
                                    game.saveState(saveLocation + "save" + index + ".obj");
                                    saveGame();
                                    System.out.println("Game Saved");
                                    showPopupMessage("Game Saved");
                                }
                                catch (Exception e){
                                    System.out.println("There was an error saving the game");
                                }
                            }
                            else {
                                System.out.println("Can't save. Round hasn't started yet");
                                showPopupMessage("Can't save. Round hasn't started yet");
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
        sceneChanger(saveGameMenuScene);
    }


    // <--------------------------------- Rules and Settings ---------------------------------->
    /**
     * Opens a web browser and navigates to a YouTube link.
     * This method is triggered when the player clicks a button to visit a specific YouTube link.
     */
    @FXML
    public void youtubeLink(){
        Music.playButtonSoundEffect();
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=hRpXLSMdve0"));
        } catch (IOException | URISyntaxException ignored){}
    }


    /**
     * Restores various settings to their default values:
     * - Sets the button volume and main volume sliders to 50%.
     * - Sets the game difficulty to the default (2).
     * - Changes the save location to the default location.
     * This method is triggered when the player clicks a button to restore settings to default.
     *
     * @param event The mouse event that triggered this action.
     */
    @FXML
    public void restoreToDefault(MouseEvent event) {
        Music.playButtonSoundEffect();
        buttonVolumeSlider.setValue(50);
        volumeSlider.setValue(50);
        Music.saveVolume();

        game.setDifficulty(2);
        switchDifficulty();

        saveLocation = SavesLocation.defaultSaveLocation();
        changeLocLabel.setText(saveLocation);
    }


    /**
     * Allows the player to change the save location by selecting a folder through a file chooser dialog.
     * This method is triggered when the player clicks a button to change the save location.
     */
    @FXML
    public void changeLoc(){
        Music.playButtonSoundEffect();
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
        } else System.out.println("No folder selected.");
    }


    // <-------------------------------- Difficulty Selection --------------------------------->
    /**
     * Handles the player's selection of the game difficulty during game setup.
     * Sets the game's difficulty level and transitions to the pre-game scene.
     * Shows a popup message briefly to confirm the difficulty selection.
     * Finally, starts the game when the popup message disappears.
     *
     * @param toggleGroup The toggle group containing the difficulty radio buttons.
     */
    private void handleDifficultySelection(ToggleGroup toggleGroup) {
        this.game.setRoundOn(true);
        Music.playButtonSoundEffect();
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            String selectedValue = selectedRadioButton.getText();
            int difficulty = switch (selectedValue) {
                case "Easy" -> 1;
                case "Medium" -> 2;
                case "Hard" -> 3;
                default -> -1;
            };
            game.setDifficulty(difficulty);
            sceneChanger(preGameScene);
            switchDifficulty();
            showPopupMessage();
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(ev -> {
                game.setStarted(true);
                sceneChanger(mainPageScene);
                distributeCards();
            });
            delay.play();
        }
    }


    /**
     * Handles the player's selection of the game difficulty in the settings menu.
     * Sets the game's difficulty level without transitioning to another scene.
     * This method is used specifically for adjusting difficulty settings in the settings menu.
     *
     * @param toggleGroup The toggle group containing the difficulty radio buttons.
     */
    private void handleDifficultySelectionForSettings(ToggleGroup toggleGroup) {
        Music.playButtonSoundEffect();
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            String selectedValue = selectedRadioButton.getText();
            game.setDifficulty(switch (selectedValue) {
                case "Easy" -> 1;
                case "Medium" -> 2;
                case "Hard" -> 3;
                default -> -1;
            });
        }
        switchDifficulty();
    }


    /**
     * Switches the selected radio button in the settings menu to match the current game difficulty.
     * Used to ensure that the correct difficulty level is visually displayed in the settings menu.
     */
    private void switchDifficulty() {
        switch(game.getDifficulty()) {
            case 1 -> easyRadioSettings.setSelected(true);
            case 2 -> mediumRadioSettings.setSelected(true);
            case 3 -> hardRadioSettings.setSelected(true);
        }
        System.out.println("Difficulty changed to " + game.getDifficulty());
    }


    // <-------------------------------------- Random ---------------------------------------->
    /**
     * Changes the current scene in the application's main window.
     * Adjusts the size of the new scene to match the dimensions of the window's current scene.
     *
     * @param scene The scene to set as the new current scene.
     */
    public void sceneChanger(Scene scene) {
        double width = window.getScene().getWidth();
        double height = window.getScene().getHeight();
        ((VBox) scene.getRoot()).setPrefSize(width, height);
        window.setScene(scene);
    }


    /**
     * Updates the displayed card count for a specific player.
     * This method is used to change the card count for computer players during the game.
     *
     * @param player     The player for whom to update the card count.
     * @param cardNumber The new card count to display for the player.
     */
    private void cardNumberChanger(int player, int cardNumber) {
        switch (player) {
            case 2 -> player2Cards.setText((Integer.parseInt(player2Cards.getText().split(" ")[0]) + cardNumber) + " Cards");
            case 3 -> player3Cards.setText((Integer.parseInt(player3Cards.getText().split(" ")[0]) + cardNumber) + " Cards");
            case 4 -> player4Cards.setText((Integer.parseInt(player4Cards.getText().split(" ")[0]) + cardNumber) + " Cards");
            case 5 -> player5Cards.setText((Integer.parseInt(player5Cards.getText().split(" ")[0]) + cardNumber) + " Cards");
        }
    }


    /**
     * Updates the displayed card count for a specific player.
     * This method is used to change the card count for computer players during the game.
     *
     * @param player The player for whom to update the card count.
     */
    private void cardNumberChanger(Player player) {
        switch (player.getValue()) {
            case 2 -> player2Cards.setText(player.totalCards() + " Cards");
            case 3 -> player3Cards.setText(player.totalCards() + " Cards");
            case 4 -> player4Cards.setText(player.totalCards() + " Cards");
            case 5 -> player5Cards.setText(player.totalCards() + " Cards");
            default -> addCardImages();
        }
    }


    /**
     * Creates and plays a splash transition for a label, providing a visual effect by scaling the label and displaying a message.
     *
     * @param label   The label to apply the splash transition to.
     * @param message The message to set in the label after the transition.
     */
    private void createAndPlaySplashTransition(Label label, String message) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), label);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setOnFinished(e -> label.setText(message));

        ScaleTransition scaleTransition1 = new ScaleTransition(Duration.seconds(0.5), label);
        scaleTransition1.setFromX(1.5);
        scaleTransition1.setFromY(1.5);
        scaleTransition1.setToX(1.0);
        scaleTransition1.setToY(1.0);

        SequentialTransition splashTransition = new SequentialTransition(scaleTransition, scaleTransition1);
        splashTransition.setOnFinished(e -> {
            label.setOpacity(1.0);
            label.setScaleX(1.0);
            label.setScaleY(1.0);
        });

        splashTransition.play();
    }


    /**
     * Retrieves a parallel transition that combines rotation and scaling effects for a given scene's root node.
     *
     * @param scene The scene whose root node will undergo the parallel transition.
     * @return A ParallelTransition object combining rotation and scaling effects.
     */
    private ParallelTransition getParallelTransition(Scene scene){
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(scene.getRoot());
        rotate.setDuration(Duration.millis(750));
        rotate.setCycleCount(1);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setFromAngle(0);
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


    // <-------------------------------------- Popups ---------------------------------------->
    /**
     * Displays a popup message that shows let the game begin.
     */
    private void showPopupMessage(){
        Label label = new Label("LET THE GAME BEGIN");
        label.setTextFill(Color.CYAN);
        label.setFont(new Font(40));
        label.setPadding(new Insets(10));
        label.setStyle("-fx-font-family: 'MV Boli';");
        label.setTranslateX(0);
        label.setTranslateY(150 + 50);

        Popup popup = new Popup();
        popup.getContent().add(label);

        popup.setAutoHide(true);
        popup.setOnShown(e -> {
            timeline = new Timeline(new KeyFrame(Duration.seconds(2), ev -> popup.hide()));
            timeline.play();
        });

        popup.show(window);
    }


    /**
     * Displays a popup message with a specified text color.
     *
     * @param message    The message to display in the popup.
     * @param textColor  The color of the text in the popup.
     */
    private void showPopupMessage(String message, Color textColor){
        Label label = new Label(message);
        label.setTextFill(textColor);
        label.setFont(new Font(35));
        label.setPadding(new Insets(10));
        label.setStyle("-fx-font-family: 'MV Boli';");
        label.setTranslateX(0);
        label.setTranslateY(220);

        Popup popup = new Popup();
        popup.getContent().add(label);

        popup.setAutoHide(true);
        popup.setOnShown(e -> {
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> popup.hide()));
            timeline.play();
        });

        popup.show(window);
    }


    /**
     * Displays a popup message with a specified text color and duration.
     *
     * @param message    The message to display in the popup.
     * @param textColor  The color of the text in the popup.
     * @param duration   The duration in seconds for which the popup is displayed.
     * @return A PauseTransition object representing the duration of the popup display.
     */
    private PauseTransition showPopupMessage(String message, Color textColor, double duration){
        Label label = new Label(message);
        label.setTextFill(textColor);
        label.setFont(new Font(35));
        label.setPadding(new Insets(10));
        label.setStyle("-fx-font-family: 'MV Boli';");
        label.setTranslateX(0);
        label.setTranslateY(220);

        Popup popup = new Popup();
        popup.getContent().add(label);

        popup.setAutoHide(true);
        popup.setOnShown(e -> {
            timeline = new Timeline(new KeyFrame(Duration.seconds(duration), ev -> popup.hide()));
            timeline.play();
        });

        popup.show(window);
        return new PauseTransition(Duration.seconds(duration + 0.5));
    }


    /**
     * Displays a popup message with a default style and automatically hides it.
     *
     * @param message The message to display in the popup.
     */
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

        popup.show(window);
    }
}
