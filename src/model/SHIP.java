package model;

public enum SHIP {
    BLUE("view/resources/shipchooser/playerShip3_blue.png", "view/resources/shipchooser/playerLife3_blue.png"),
    GREEN("view/resources/shipchooser/playerShip3_green.png", "view/resources/shipchooser/playerLife3_green.png"),
    ORANGE("view/resources/shipchooser/playerShip3_orange.png", "view/resources/shipchooser/playerLife3_orange.png"),
    RED("view/resources/shipchooser/playerShip3_red.png", "view/resources/shipchooser/playerLife3_red.png");

    private String urlShip;
    private String urlLife;

     SHIP(String urlShip,String urlLife){
        this.urlShip = urlShip;
        this.urlLife = urlLife;
    }

    public String getUrlShip() {
        return urlShip;
    }

    public String getUrlLife() {
        return urlLife;
    }
}
