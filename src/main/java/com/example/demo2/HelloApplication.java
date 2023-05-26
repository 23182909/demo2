package com.example.demo2;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

class Account {
    private String accountNumber;
    private String pin;
    private double balance;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. Remaining balance: $" + balance);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit successful. Current balance: $" + balance);
    }
}

class ATM {
    private Account account;

    public ATM(Account account) {
        this.account = account;
    }
    public boolean authenticateUser(String accountNumber, String pin) {
        return account.getAccountNumber().equals(accountNumber) && account.validatePin(pin);
    }

    public double getBalance() {
        return account.getBalance();
    }

    public void withdraw(double amount) {
        account.withdraw(amount);
    }

    public void deposit(double amount) {
        account.deposit(amount);
    }
}

public class HelloApplication extends Application {
    private Stage primaryStage;
    private ATM atm;
    private ImageView backgroundImageView;
    private ImageView logoImageView;
    private PasswordField pinField;
    private TextField accountNumberField;
    private boolean pinEntered;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("ATM");

        Image backgroundImage = new Image("https://tse2.mm.bing.net/th?id=OIP.6lzUX0zPsQ6gZUOXbYpW7AHaIO&pid=Api&P=0&h=180");
        backgroundImageView = new ImageView(backgroundImage);

        Image logoImage = new Image("https://tse1.mm.bing.net/th?id=OIP.JmDsmd991q_MTSHWDADY0wAAAA&pid=Api&P=0&h=180");
        logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(45);
        logoImageView.setPreserveRatio(true);

        Account account = new Account("123456789", "1234", 1000.0);

        atm = new ATM(account);

        showLoginScene();
    }

    private void showLoginScene() {
        Label accountNumberLabel = new Label("Account Number:");
        accountNumberField = new TextField();

        Label pinLabel = new Label("PIN:");
        pinField = new PasswordField();
        pinField.setEditable(false);

        GridPane gridPane = createNumberGrid();
        Button clearButton = createClearButton();
        Button enterButton = createEnterButton(accountNumberField, pinField);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(accountNumberLabel, accountNumberField, pinLabel, pinField, gridPane, clearButton, enterButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        StackPane root = new StackPane();

        StackPane.setAlignment(logoImageView, Pos.TOP_LEFT);
        StackPane.setMargin(logoImageView, new Insets(10));

        StackPane.setAlignment(backgroundImageView, Pos.TOP_RIGHT);
        StackPane.setMargin(backgroundImageView, new Insets(10));

        backgroundImageView.setFitWidth(50);
        backgroundImageView.setFitHeight(50);

        root.getChildren().addAll(backgroundImageView, logoImageView, layout);

        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMenuScene() {
        Label balanceLabel = new Label("Balance: $" + atm.getBalance());

        Label withdrawalLabel = new Label("Withdrawal Amount:");
        TextField withdrawalField = new TextField();

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(e -> {
            double amount = Double.parseDouble(withdrawalField.getText());
            atm.withdraw(amount);
            balanceLabel.setText("Balance: $" + atm.getBalance());
            showAlert("Withdrawal successful. Remaining balance: $" + atm.getBalance());
            withdrawalField.clear();
        });

        Label depositLabel = new Label("Deposit Amount:");
        TextField depositField = new TextField();

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(e -> {
            double amount = Double.parseDouble(depositField.getText());
            atm.deposit(amount);
            balanceLabel.setText("Balance: $" + atm.getBalance());
            showAlert("Deposit successful. Current balance: $" + atm.getBalance());
            depositField.clear();
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            pinField.clear();
            pinEntered = false;
            showLoginScene();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(balanceLabel, withdrawalLabel, withdrawalField, withdrawButton,
                depositLabel, depositField, depositButton, logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, logoImageView, layout);

        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    private GridPane createNumberGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));

        int number = 1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = createNumberButton(String.valueOf(number), pinField);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                gridPane.add(button, col, row);
                GridPane.setHalignment(button, HPos.CENTER);
                GridPane.setValignment(button, VPos.CENTER);
                number++;
            }
        }

        Button zeroButton = createNumberButton("0", pinField);
        zeroButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Button deleteButton = createDeleteButton(pinField);
        deleteButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


        GridPane.setHalignment(zeroButton, HPos.CENTER);
        GridPane.setValignment(zeroButton, VPos.CENTER);
        GridPane.setHalignment(deleteButton, HPos.CENTER);
        GridPane.setValignment(deleteButton, VPos.CENTER);

        gridPane.add(zeroButton, 1, 3);
        gridPane.add(deleteButton, 2, 3);

        return gridPane;
    }

    private Button createNumberButton(String number, PasswordField pinField) {
        Button button = new Button(number);
        button.setOnAction(e -> this.pinField.setText(this.pinField.getText() + number));
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return button;
    }


    private Button createClearButton() {
        Button button = new Button("Clear");
        button.setOnAction(e -> pinField.clear());
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return button;
    }

    private Button createDeleteButton(PasswordField pinField) {
        Button button = new Button("Delete");
        button.setOnAction(e -> {
            String currentPin = pinField.getText();
            if (currentPin.length() > 0) {
                pinField.setText(currentPin.substring(0, currentPin.length() - 1));
            }
        });
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return button;
    }

    private Button createEnterButton(TextField accountNumberField, PasswordField pinField) {
        Button button = new Button("Enter");
        button.setOnAction(e -> {
            String accountNumber = accountNumberField.getText();
            String pin = pinField.getText();

            if (atm.authenticateUser(accountNumber, pin)) {
                pinField.clear();
                pinEntered = true;
                showMenuScene();
            } else {
                showAlert("Invalid account number or PIN. Please try again.");
            }
        });
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.disableProperty().bind(accountNumberField.textProperty().isEmpty());
        return button;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ATM");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
