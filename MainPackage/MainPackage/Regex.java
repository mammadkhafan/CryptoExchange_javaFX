package MainPackage;

public enum Regex {
    emailRegex("^[a-zA-Z]{1,1}[a-zA-Z0-9-_.]{4,63}@[a-zA-Z]+\\.[a-zA-z]+$"),
    usernameRegex("[a-zA-Z]{1,1}[a-zA-Z0-9_]{2,12}"),
    passwordRegex("^[a-zA-Z0-9]{5,15}$"),
    nameRegex("^[a-zA-Z ]{2,20}$"),
    phoneNumberRegex("^[0-9]{4,10}$");

    public String regexStr;

    Regex(String regex) {
        this.regexStr = regex;
    }
}