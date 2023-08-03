package base;

import base.gofish.GameEngine;
import base.gofish.Player;
import base.gofish.deck.Card;
import base.gofish.saveAndMusic.Music;
import base.gofish.saveAndMusic.SavesLocation;
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
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.util.Pair;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class GameDriver extends Application {
    private ArrayList<String> names = new ArrayList<String>();
    private static Stage window;
    private GameEngine game;
    private Timeline timeline;
    private Stage pauseStage;
    private BoxBlur blur;
    private String saveLocation;
    private Label deckCardsLeft;
    private Label playerSelected;
    private final Duration MESSAGE_DELAY = Duration.seconds(2);
    private final Duration TRANSITION_DELAY = Duration.seconds(2.5);
    private final Duration POPUP_MESSAGE_DURATION = Duration.seconds(2);


    // All the scenes
    private Scene OpeningScene;
    private Scene playerNameScene;
    private Scene totalPointsScene;
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
    @FXML
    private ComboBox<Integer> pointsCombobox;
    @FXML
    private Button setMaxValueButton;

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
    public static void main(String[] args){
        launch();
    }

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

        FXMLLoader totalPointsXML = new FXMLLoader(getClass().getResource("/PreGame/3totalPoints.fxml"));
        totalPointsXML.setController(this);
        totalPointsScene = new Scene(totalPointsXML.load());

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

        // Get number of players and set scene to get player names from players
        setMaxValueButton.setOnAction(actionEvent -> {
            Music.playButtonSoundEffect();
            game.setMaxPoints(this.pointsCombobox.getValue());
            sceneChanger(difficultyLevelScene);
        });

//        // Add listener on escape key for pausing the game
//        pauseStage = new Stage();
//        pauseStage.initStyle(StageStyle.UNDECORATED);
//        pauseStage.initModality(Modality.APPLICATION_MODAL);
//        pauseStage.initStyle(StageStyle.TRANSPARENT);
//        pauseStage.setScene(pauseGameScene);
//        pauseStage.setOpacity(0.9);

        mainPageScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) sceneChanger(pauseGameScene);
        });

        pauseGameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) sceneChanger(game.isRoundOn() ? mainPageScene : leaderboardScene);
        });

//        showCardScene.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ESCAPE){
//                Stage overlayStage = (Stage) showCardScene.getWindow();
//                mainGamePageScene.getRoot().setEffect(null);
//                overlayStage.close();
//            }
//        });

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
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Music.setMusicVolume(newValue.doubleValue() / 100);
        });

        buttonVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Music.setButtonVolume(newValue.doubleValue() / 100);
        });

        window.show();
    }

    public void restartGame() throws IOException {
        this.start(window);
    }


    // <-------------------------------------  Main Game--------------------------------------->
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
            fadeSequence.setOnFinished(event -> sceneChanger(totalPointsScene));
            fadeSequence.play();
        }
    }

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
                        this.addDeck();
                        this.addCardImages();
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

    private void addDeck() {
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

        deckCardsLeft = new Label("27 Cards Left");
        deckCardsLeft.setAlignment(javafx.geometry.Pos.CENTER);
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

        game.roundStart();
        game.startTurn();
    }

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

    private void playerMove(Card finalCard) {
        if (playerSelected == null) this.showPopupMessage("Please select a Player", 35, Color.CYAN, 1);
        else {
            List<Card> card = this.game.singleTurn(playerSelected.getText(), finalCard.getFullName());
            this.game.getPlayerProbabilities().updateProbabilitiesToOne(finalCard.getValue(), this.game.getCurrentPlayer());
            if (card == null) {
                this.showPopupMessage("WRONG! GO FISH", 35, Color.ORANGERED, 1);
                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                delay.setOnFinished(ev -> {
                    Card card1 = this.game.getDeck().draw();
                    this.deckCardsLeft.setText((Integer.parseInt(deckCardsLeft.getText().split(" ")[0]) - 1) + " Cards Left");
                    this.game.getCurrentPlayer().addToHand(card1);
                    this.displayCard(card1);
                    this.addCardImages();
                });
                delay.play();
            }
            else {
                this.game.getCurrentPlayer().addToHand(card);
                this.showPopupMessage("You received " + card.size() + " Card(s) from " + playerSelected.getText(), 35, Color.CYAN, 3);
                this.cardNumberChanger(Integer.parseInt(String.valueOf(playerSelected.getText().charAt(playerSelected.getText().length()-1))), -card.size());
                this.addCardImages();
            }
            this.playerSelected.setStyle("-fx-text-fill: WHITE; -fx-font-size: 30");
            this.playerSelected = null;
        }
    }

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
                    this.game.endTurn();
                    this.game.startTurn();
                    this.botTurn();
                });
                delay2.play();
            });
            delay.play();
        });
        parallel.play();
    }

    private void botTurn() {
        createAndPlaySplashTransition(playerTurnLabel, game.getCurrentPlayer() + "'s Turn");

        Pair<Player, Card> playerCard;
        try {
            playerCard = this.game.getPlayerCardForBots();
        }
        catch (IllegalArgumentException e) {
            this.game.endTurn();
            this.game.startTurn();
            if (game.getCurrentPlayer() != game.getRealPlayer()) botTurn();
            else playerTurnLabel.setText("Your Turn");
            return;
        }

        Player player = playerCard.getKey();
        Card card = playerCard.getValue();
        this.game.getPlayerProbabilities().updateProbabilitiesToOne(card.getValue(), this.game.getCurrentPlayer());

        this.showPopupMessage(this.game.getCurrentPlayer() + " asked " + card.getName() + " from " + player, 35, Color.ORANGE, 3);
        PauseTransition delay1 = new PauseTransition(Duration.seconds(3.2));
        delay1.setOnFinished(e -> {
            List<Card> cards = this.game.singleTurn(player.toString(), card.getFullName());
            if (cards == null) {
                this.showPopupMessage("WRONG! GO FISH", 35, Color.ORANGERED, 1.5);
                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                delay.setOnFinished(ee -> {
                    Card card1 = this.game.getDeck().draw();
                    if (card1 != null) {
                        this.deckCardsLeft.setText((Integer.parseInt(deckCardsLeft.getText().split(" ")[0]) - 1) + " Cards Left");
                        this.game.getCurrentPlayer().addToHand(card1);
                        this.cardNumberChanger(this.game.getCurrentPlayer());
                    }
                    this.game.endTurn();
                    this.game.startTurn();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(eee -> {
                        if (game.getCurrentPlayer() != game.getRealPlayer()) botTurn();
                        else if (game.getRealPlayer().totalCards() == 0) {
                            this.game.endTurn();
                            this.game.startTurn();
                            botTurn();
                        }
                        else playerTurnLabel.setText("Your Turn");
                    });
                    pause.play();
                });
                delay.play();
            }
            else {
                this.game.getCurrentPlayer().addToHand(cards);
                this.showPopupMessage(this.game.getCurrentPlayer() + " received " + cards.size() + " Card(s) from " + player, 35, Color.CYAN, 3);
                this.cardNumberChanger(player);
                this.cardNumberChanger(game.getCurrentPlayer());
                PauseTransition delay = new PauseTransition(Duration.seconds(4));
                delay.setOnFinished(ee -> {
                    if (game.getCurrentPlayer() != game.getRealPlayer()) botTurn();
                });
                delay.play();
            }
        });
        delay1.play();
    }


    // <----------------------------------  Selected Label ----------------------------------->
    @FXML
    public void playerSelected(MouseEvent event) {
        if (game.getCurrentPlayer() == game.getRealPlayer()){
            if (playerSelected != null) {
                playerSelected.setStyle("-fx-text-fill: WHITE; -fx-font-size: 30; -fx-background-color: rgba(66, 66, 66, 0.3);");
                playerSelected = (Label) event.getSource();
                playerSelected.setStyle("-fx-text-fill: CYAN; -fx-font-size: 30; -fx-background-color: rgba(66, 66, 66, 0.3);");
            }
            else {
                playerSelected = (Label) event.getSource();
                playerSelected.setStyle("-fx-text-fill: CYAN; -fx-font-size: 30; -fx-background-color: rgba(66, 66, 66, 0.3);");
            }
        }
    }

    @FXML
    private void handleMouseEntered(MouseEvent event) {
        if (playerSelected == null && game.getCurrentPlayer() == game.getRealPlayer()) ((Label) event.getSource()).setStyle("-fx-font-size: 30; -fx-text-fill: cyan; -fx-background-color: rgba(66, 66, 66, 0.3);");
    }

    @FXML
    private void handleMouseExited(MouseEvent event) {
        if (playerSelected == null && game.getCurrentPlayer() == game.getRealPlayer()) ((Label) event.getSource()).setStyle("-fx-font-size: 30; -fx-background-color: rgba(66, 66, 66, 0.3);");
    }


    // <---------------------------------------  Menus ---------------------------------------->
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
        }
        event.consume();
    }

    public void leaderboardMenu(MouseEvent event) {
        Music.playButtonSoundEffect();
        Button pressedButton = (Button) event.getSource();
        String text = pressedButton.getText();
        switch (text) {
            case "Start Next Round" -> {
                game.setRoundOn(true);
                sceneChanger(mainPageScene);
            }
            case "End Game" -> {
                Player winner = game.getWinner();
                winnerLabel.setText(winner + " has won");
                sceneChanger(gameOverScene);
            }
            case "Main Menu" -> sceneChanger(pauseGameScene);
        }
        event.consume();
    }

    private void addLeaderboard() {
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
            VBox.setMargin(anchorPane, new Insets(25, 0, 25, 0));
        }
    }

    public void backButton(){
        Music.playButtonSoundEffect();
        sceneChanger(game.isStarted() ? pauseGameScene : startMenuScene);
        Music.saveVolume();
    }


    // <--------------------------------- Loading and saving ---------------------------------->
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

    public void loadGame(MouseEvent event){
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
    }

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

    private void saveGame(){
        saveLocation = SavesLocation.loadSaveLocation();
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


    // <--------------------------------- Rules and Settings ---------------------------------->
    public void youtubeLink(){
        Music.playButtonSoundEffect();
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=hRpXLSMdve0"));
        } catch (IOException | URISyntaxException e){
            e.printStackTrace();
        }
    }

    public void restoreToDefault(MouseEvent event) {
        buttonVolumeSlider.setValue(50);
        volumeSlider.setValue(50);
        Music.saveVolume();

        game.setDifficulty(2);
        switchDifficulty();

        saveLocation = SavesLocation.defaultSaveLocation();
        changeLocLabel.setText(saveLocation);
    }

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
    private void handleDifficultySelection(ToggleGroup toggleGroup) {
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
            showPopupMessage("LET THE GAME BEGIN", 40, 50, Color.CYAN, 2);
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(ev -> {
                game.setStarted(true);
                sceneChanger(mainPageScene);
                distributeCards();
            });
            delay.play();
        }
    }

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

    private void switchDifficulty() {
        switch(game.getDifficulty()) {
            case 1 -> easyRadioSettings.setSelected(true);
            case 2 -> mediumRadioSettings.setSelected(true);
            case 3 -> hardRadioSettings.setSelected(true);
        }
        System.out.println("Difficulty changed to " + game.getDifficulty());
    }


    // <-------------------------------------- Random ---------------------------------------->
    public void sceneChanger(Scene scene) {
        double width = window.getScene().getWidth();
        double height = window.getScene().getHeight();
        ((VBox) scene.getRoot()).setPrefSize(width, height);
        window.setScene(scene);
    }

    private void cardNumberChanger(int player, int cardNumber) {
        switch (player) {
            case 2 -> player2Cards.setText((Integer.parseInt(player2Cards.getText().split(" ")[0]) + cardNumber) + " Cards");
            case 3 -> player3Cards.setText((Integer.parseInt(player3Cards.getText().split(" ")[0]) + cardNumber) + " Cards");
            case 4 -> player4Cards.setText((Integer.parseInt(player4Cards.getText().split(" ")[0]) + cardNumber) + " Cards");
            case 5 -> player5Cards.setText((Integer.parseInt(player5Cards.getText().split(" ")[0]) + cardNumber) + " Cards");
        }
    }

    private void cardNumberChanger(Player player) {
        switch (player.getValue()) {
            case 2 -> player2Cards.setText(player.totalCards() + " Cards");
            case 3 -> player3Cards.setText(player.totalCards() + " Cards");
            case 4 -> player4Cards.setText(player.totalCards() + " Cards");
            case 5 -> player5Cards.setText(player.totalCards() + " Cards");
            default -> addCardImages();
        }
    }

    private void showErrorInLabel(Label label, int fontSize, String prevMessage, String newMessage){
        label.setText(newMessage);
        label.setStyle("-fx-text-fill: red; -fx-font-size: " + fontSize);

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), label);
            fadeIn.setFromValue(1);
            fadeIn.setToValue(0);
            fadeIn.setOnFinished(ev -> {
                label.setText(prevMessage);
                label.setStyle("-fx-text-fill: white; -fx-font-size: " + fontSize);
            });

            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), label);
            fadeOut.setFromValue(0);
            fadeOut.setToValue(1);
            fadeOut.setDelay(Duration.millis(10));

            SequentialTransition fadeSequence = new SequentialTransition(fadeIn, fadeOut);
            fadeSequence.play();
        });
        delay.play();
    }

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

    private void showPopupMessage(String message, int fontSize, int moveY, Color textColor, double duration){
        Label label = new Label(message);
        label.setTextFill(textColor);
        label.setFont(new Font(fontSize));
        label.setPadding(new Insets(10));
        label.setStyle("-fx-font-family: 'MV Boli';");
        label.setTranslateX(0);
        label.setTranslateY(150 + moveY);

        Popup popup = new Popup();
        popup.getContent().add(label);

        popup.setAutoHide(true);
        popup.setOnShown(e -> {
            timeline = new Timeline(new KeyFrame(Duration.seconds(duration), ev -> popup.hide()));
            timeline.play();
        });

        popup.show(window);
    }

    private void showPopupMessage(String message, int fontSize, Color textColor, double duration){
        Label label = new Label(message);
        label.setTextFill(textColor);
        label.setFont(new Font(fontSize));
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

        popup.show(window);
    }
}
