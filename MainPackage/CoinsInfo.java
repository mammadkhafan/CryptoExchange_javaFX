package MainPackage;

import javafx.scene.image.Image;

public class CoinsInfo {
    private Image coinsImage;
    private String name, price, change, maxPrice, minPrice;

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

    public void setCoinsImage(Image coinsIcon) {
        this.coinsImage = coinsIcon;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChange() {
        return this.change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

}
