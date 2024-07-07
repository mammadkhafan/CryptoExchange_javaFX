package MainPackage;

import javafx.scene.image.Image;

public class CoinsInfo {
    private Image coinsImage;
    private String name, change;
    private double price, changeNumber, maxPrice, minPrice;

    public CoinsInfo(String name, String imagePath) {
        setName(name);
        try {
            setCoinsImage(new Image(getClass().getResourceAsStream(customizePath(imagePath))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    private String customizePath(String originalPath) {
        for (int i = 0; i < originalPath.length(); i++) {
            if (originalPath.charAt(i) == '/' && originalPath.substring(i, i + 17).equals("/Image/coinIcons/")) {
                return ".." + originalPath.substring(i, originalPath.length());
            }
        }
        return null;//not happening
    } 



    public Image getCoinsImage() {
        return this.coinsImage;
    }

    public void setCoinsImage(Image coinsImage) {
        this.coinsImage = coinsImage;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChange() {
        return "" + changeNumber + "%";
    }

    public void setChange(String change) {
        this.change = change;
    }

    public double getChangeNumber() {
        return this.changeNumber;
    }

    public void setChangeNumber(double changeNumber) {
        this.changeNumber = changeNumber;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }
    

}
