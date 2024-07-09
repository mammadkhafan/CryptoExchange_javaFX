package MainPackage;

public enum ErrorMessage {
    emailErrorMessage("valid chars: a-Z(one at least) 0-9 - _ ."),
    usernameErrorMessage("valid chars: (start with)a-Z 0-9 _"),
    passwordErrorMessage("valid chars: a-Z 0-9 (length: min 6-max 15)"),
    nameErrorMessage("valid chars: a-Z space"),
    phoneNumberErrorMessage("valid chars: 0-9 (length: min 4-max 10)"),
    captchaErrorMessage("your Input doesn't mach"),
    emailNotFoundErrorMessage("Email not found in our users emails"),
    emailSentInLastMinuteErrorMessage("you have to wait one minute"),
    interCodeBeforeSetPassword("inter your generated code befor set new password"),
    amountEmptyErrorMessage("you didn't set the amount (most be more than 0)"),
    lackOfAmount("you have lack of coin amount"),
    lackOfPrice("you have lack of money"),
    priceEmptyErrorMessage("you didn't set the price (most be more than 0.0)"),
    choseYourCoinEmptyErrorMessage("chose that coin you want to exchange with"),
    typeOfExchangeDoesNotSelectedErrorMessage("you have to select your exchange type"),
    selectOriginAndDestinationErrorMessage("select origin and destinatoin coins first");

    public String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
