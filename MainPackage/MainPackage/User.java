package MainPackage;

import CoinPackage.CoinsOfCSV;
import javafx.scene.image.ImageView;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private ImageView profileImage;

    private double moneyWelth;
    private int[] coinWelth;


    public User(String firsName, String lastName, String username, String email, String phoneNumber, String password, ImageView profileImage) {
        this.firstName = firsName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profileImage = profileImage;

        CoinsOfCSV coinsOfCSV = new CoinsOfCSV("C:\\Users\\ASUS\\Desktop\\TermTow\\FinalProject(TermTwo)\\src\\Files\\currency_prices.csv");

        coinWelth = new int[coinsOfCSV.getAllCoins().size()];
        moneyWelth = 0;
        for (int i = 0; i < coinWelth.length; i++) {
            coinWelth[i] = 0;
        }
    }

    public void increaseMoneyWelth(double money) {
        moneyWelth += money;
    }

    public void decreaseMoneyWelth(double money) {
        moneyWelth -= money;
    }

    public void increseCoinWelthAt(int index, int coinAmount) {
        coinWelth[index] += coinAmount;
    }

    public void decreaseCoinWelthAt(int index, int coinAmount) {
        coinWelth[index] -= coinAmount;
    }

    public boolean isUser_byUsername(String username) {
        if (this.username.equals(username)) 
            return true;

        return false;
    }

    public boolean equals(User user1) {
        if (this.username.equals(user1.getUsername())) {
            return true;
        }
        return false;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ImageView getProfileImage() {
        return this.profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }

    public double getMoneyWelth() {
        return moneyWelth;
    }

    public int getCoinWelthAt(int index) {
        return coinWelth[index];
    }

    
}
