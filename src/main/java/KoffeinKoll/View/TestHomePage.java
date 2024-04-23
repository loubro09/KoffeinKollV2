    package KoffeinKoll.View;

    import com.jfoenix.controls.JFXButton;
    import de.jensd.fx.glyphs.GlyphsDude;
    import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
    import javafx.geometry.Pos;
    import javafx.scene.Scene;
    import javafx.scene.control.Label;
    import javafx.scene.layout.BorderPane;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.VBox;
    import javafx.scene.paint.Color;
    import javafx.scene.text.Font;
    import javafx.scene.text.FontPosture;
    import javafx.scene.text.FontWeight;
    import javafx.scene.text.Text;
    import javafx.geometry.Insets;
    import javafx.stage.Stage;

    public class TestHomePage extends A_Page {

        private Label titleLabel;
        private JFXButton statisticsButton;
        private JFXButton infoButton;
        private JFXButton accountButton;
        private JFXButton logoutButton;
        private JFXButton logBeverageButton;
        private CustomGauge customGauge;

        @Override
        public void initializeUI() {
            stage = new Stage();
            setUpStage();
            titleLabel = new Label("KoffeinKoll");
            titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 46));
            titleLabel.setTextFill(Color.rgb(0, 70, 0));
            customGauge = new CustomGauge();

            createButtons();
        }

        public void createButtons() {
            // Creating buttons
            statisticsButton = new JFXButton("Statistics");
            infoButton = new JFXButton("Info");
            accountButton = new JFXButton("Profile");
            logoutButton = new JFXButton("Log Out");

            statisticsButton.setStyle(setButtonStyle());
            infoButton.setStyle(setButtonStyle());
            accountButton.setStyle(setButtonStyle());
            logoutButton.setStyle(setButtonStyle());

            Text chart = GlyphsDude.createIcon(FontAwesomeIcon.AREA_CHART, "2em");
            chart.setFill(Color.WHITE);
            statisticsButton.setGraphic(chart);

            Text info = GlyphsDude.createIcon(FontAwesomeIcon.INFO_CIRCLE, "2em");
            info.setFill(Color.WHITE);
            infoButton.setGraphic(info);

            Text account = GlyphsDude.createIcon(FontAwesomeIcon.USER, "2em");
            account.setFill(Color.WHITE);
            accountButton.setGraphic(account);

            Text log = GlyphsDude.createIcon(FontAwesomeIcon.SIGN_OUT, "2em");
            log.setFill(Color.WHITE);
            logoutButton.setGraphic(log);

            logBeverageButton = new JFXButton("Log Beverage");
            logBeverageButton.setStyle("-fx-background-color:\n" +
                    "            #090a0c,\n" +
                    "            linear-gradient(#8fbc8f 0%, #8fbc8f 20%, #8fbc8f 100%),\n" +
                    "            linear-gradient(#8fbc8f, #8fbc8f),\n" +
                    "            radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                    "    -fx-background-radius: 5,4,3,5;\n" +
                    "    -fx-background-insets: 0,1,2,0;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                    "    -fx-font-family: \"Arial\";\n" +
                    "    -fx-text-fill: linear-gradient(black, black);\n" +
                    "    -fx-font-size: 30px;\n" +
                    "    -fx-padding: 10 20 10 20;");

            // Creating icon for Log Beverage button
            Text coffeeIcon = GlyphsDude.createIcon(FontAwesomeIcon.COFFEE, "2em");
            coffeeIcon.setFill(Color.WHITE);
            logBeverageButton.setGraphic(coffeeIcon);
        }

        @Override
        public void createScene(BorderPane borderPane) {
        /*    // Creating a HBox for buttons
            HBox buttonHBox = new HBox(40);
            buttonHBox.setAlignment(Pos.CENTER);
            buttonHBox.getChildren().addAll(accountButton, statisticsButton, infoButton, logoutButton);
            buttonHBox.setPadding(new Insets(20));
            borderPane.setBottom(buttonHBox);

            // Applying style to button panel
            buttonHBox.setStyle("-fx-background-color:\n" +
                    "            #090a0c,\n" +
                    "            linear-gradient(#8fbc8f 0%, #8fbc8f 20%, #8fbc8f 100%),\n" +
                    "            linear-gradient(#8fbc8f, #8fbc8f),\n" +
                    "            radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));");

            // Adding the custom gauge to the center of the layout
            BorderPane.setAlignment(customGauge, Pos.TOP_CENTER); // Center the gauge
            Insets gaugeMargins = new Insets(100, 200, 100, 200); // Top, Right, Bottom, Left
            BorderPane.setMargin(customGauge, gaugeMargins);
            borderPane.setCenter(customGauge);

            // Creating a VBox for main page
            HBox topHBox = new HBox();
            topHBox.getChildren().add(titleLabel);
            topHBox.setAlignment(Pos.CENTER);
            borderPane.setTop(topHBox);

            // Creating an HBox for the "Log Beverage" button
            HBox logBeverageButtonHBox = new HBox(logBeverageButton);
            logBeverageButtonHBox.setAlignment(Pos.CENTER);
            logBeverageButtonHBox.setSpacing(20);

            // Creating a VBox to contain the Log Beverage button and the button panel
            VBox combinedButtonVBox = new VBox();
            combinedButtonVBox.setAlignment(Pos.CENTER);
            combinedButtonVBox.setSpacing(100);
            combinedButtonVBox.getChildren().addAll(logBeverageButtonHBox, buttonHBox);

            // Placing the combined VBox at the bottom of the BorderPane
            borderPane.setBottom(combinedButtonVBox);*/

            /*HBox buttonHBox = new HBox(40);
            buttonHBox.setAlignment(Pos.CENTER);
            buttonHBox.getChildren().addAll(accountButton, statisticsButton, infoButton, logoutButton);
            buttonHBox.setPadding(new Insets(20));
            borderPane.setBottom(buttonHBox);

            // Applying style to button panel
            buttonHBox.setStyle("-fx-background-color:\n" +
                    "            #090a0c,\n" +
                    "            linear-gradient(#8fbc8f 0%, #8fbc8f 20%, #8fbc8f 100%),\n" +
                    "            linear-gradient(#8fbc8f, #8fbc8f),\n" +
                    "            radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));");

            // Adding the custom gauge to the center of the layout
            BorderPane.setAlignment(customGauge, Pos.CENTER); // Center the gauge
            borderPane.setCenter(customGauge);

            // Creating a VBox for main page
            HBox topHBox = new HBox();
            topHBox.getChildren().add(titleLabel);
            topHBox.setAlignment(Pos.CENTER);
            borderPane.setTop(topHBox);

            // Creating an HBox for the "Log Beverage" button
            HBox logBeverageButtonHBox = new HBox(logBeverageButton);
            logBeverageButtonHBox.setAlignment(Pos.CENTER);
            logBeverageButtonHBox.setSpacing(20);

            // Creating a VBox to contain the Log Beverage button and the button panel
            VBox combinedButtonVBox = new VBox();
            combinedButtonVBox.setAlignment(Pos.CENTER);
            combinedButtonVBox.setSpacing(100);
            combinedButtonVBox.getChildren().addAll(logBeverageButtonHBox, buttonHBox);

            // Placing the combined VBox at the bottom of the BorderPane
            borderPane.setBottom(combinedButtonVBox);*/

            VBox mainContent = new VBox();
            mainContent.setAlignment(Pos.CENTER);
            mainContent.setSpacing(20);

            // Adding the custom gauge to the main content
            mainContent.getChildren().addAll(titleLabel, customGauge);

            // Creating an HBox for the buttons
            HBox buttonBox = new HBox();
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setSpacing(20);
            buttonBox.getChildren().addAll(accountButton, statisticsButton, infoButton, logoutButton);

            // Creating a VBox for the Log Beverage button and the button box
            VBox buttonVBox = new VBox();
            buttonVBox.setAlignment(Pos.CENTER);
            buttonVBox.setSpacing(20);
            buttonVBox.getChildren().addAll(logBeverageButton, buttonBox);

            // Setting margins for the main content and the button VBox
            BorderPane.setMargin(mainContent, new Insets(50));
            BorderPane.setMargin(buttonVBox, new Insets(20));

            // Placing the main content in the center and the button VBox at the bottom
            borderPane.setCenter(mainContent);
            borderPane.setBottom(buttonVBox);
        }

    }
