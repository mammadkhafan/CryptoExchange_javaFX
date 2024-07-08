package BookPackage;

public enum DealsRespond {
    FOUNDANDDONE("A pending exchange found and your deal have welded"),
    NOTFOUND("Your exchange transfared to pending exchanges"),
    FOUNDANDSTAY("Part of your deal welded but the other part is pending");

    private String dealsResult;

    DealsRespond(String dealsResult) {
        this.dealsResult = dealsResult;
    }

    public String getDealsResult() {
        return this.dealsResult;
    }
}
