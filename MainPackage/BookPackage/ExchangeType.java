package BookPackage;

public enum ExchangeType {
    SELL("SELL"),
    BUY("BUY");

    private String str;

    ExchangeType(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
