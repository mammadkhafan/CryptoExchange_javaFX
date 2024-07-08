package CoinPackage;

public enum CoinsNameAndIndex {
    USD("USD", 0),
    EUR("EUR", 1),
    TOMAN("TOMAN", 2),
    YEN("YEN", 3),
    GBP("GBP", 4);

    private int index;
    private String name;

    CoinsNameAndIndex(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return this.name;
    }

    public static CoinsNameAndIndex getCoinsNameAndIndexOfIndex(int index) {
        switch (index) {
            case 0:
                
                return CoinsNameAndIndex.USD;
            case 1:
                
                return CoinsNameAndIndex.EUR;
            case 2:
                
                return CoinsNameAndIndex.TOMAN;
            case 3:
                
                return CoinsNameAndIndex.YEN;
            case 4:
                
                return CoinsNameAndIndex.GBP;
        
            default:
                return null;
        }
    } 

    public static CoinsNameAndIndex getCoinsNameAndIndexOfName(String name) {
        switch (name) {
            case "USD":
                
                return CoinsNameAndIndex.USD;
            case "EUR":
                
                return CoinsNameAndIndex.EUR;
            case "TOMAN":
                
                return CoinsNameAndIndex.TOMAN;
            case "YEN":
                
                return CoinsNameAndIndex.YEN;
            case "GBP":
                
                return CoinsNameAndIndex.GBP;
        
            default:
                return null;
        }
    } 
}
