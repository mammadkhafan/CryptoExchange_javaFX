package MainPackage;

import CoinPackage.CoinsOfCSV;
import javafx.scene.image.ImageView;
import java.util.Random;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String countryStartingNumber;
    private String phoneNumberWithoutCountry;
    private String password;
    private ImageView profileImage;
    private String walletId;

    private double moneyWelth;
    private int[] coinWelth;

    private final String validCharsInWalletId = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int walletIdLength = 7;


    public User(String firsName, String lastName, String username, String email,String countryStartingNumber, String phoneNumberWithoutCountry, String password, ImageView profileImage) {
        this.firstName = firsName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumberWithoutCountry = phoneNumberWithoutCountry;
        this.countryStartingNumber = countryStartingNumber;
        this.phoneNumber = countryStartingNumber + phoneNumberWithoutCountry;
        this.password = password;
        this.profileImage = profileImage;

        CoinsOfCSV coinsOfCSV = new CoinsOfCSV("C:\\Users\\ASUS\\Desktop\\TermTow\\FinalProject(TermTwo)\\src\\Files\\currency_prices.csv");

        coinWelth = new int[coinsOfCSV.getAllCoins().size()];
        moneyWelth = 0;
        for (int i = 0; i < coinWelth.length; i++) {
            coinWelth[i] = 0;
        }

        generateWalletID();
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

    public String getCountryStartingNumber() {
        return this.countryStartingNumber;
    }

    public void setCountryStartingNumber(String countryStartingNumber) {
        this.countryStartingNumber = countryStartingNumber;
    }

    public String getPhoneNumberWithoutCountry() {
        return this.phoneNumberWithoutCountry;
    }

    public void setPhoneNumberWithoutCountry(String phoneNumberWithoutCountry) {
        this.phoneNumberWithoutCountry = phoneNumberWithoutCountry;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }
    
    public void setMoneyWelth(double moneyWelth) {
        this.moneyWelth = moneyWelth;
    }

    public int[] getCoinWelth() {
        return this.coinWelth;
    }

    public void setCoinWelth(int[] coinWelth) {
        this.coinWelth = coinWelth;
    }

    public String getValidCharsInWalletId() {
        return this.validCharsInWalletId;
    }


    public int getWalletIdLength() {
        return this.walletIdLength;
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


    public String getWalletId() {
        return this.walletId;
    }

    private void generateWalletID() {
        StringBuilder walletId = new StringBuilder(walletIdLength);
        for (int i = 0; i < walletIdLength; i++) {
            walletId.append(validCharsInWalletId.charAt(new Random().nextInt(validCharsInWalletId.length())));
        }
        this.walletId = walletId.toString();
    }

    
}
