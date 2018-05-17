package components;

import componentArchitecture.Component;

public class EatingComponent implements Component {

    private String whatDoIEat;
    private float eatingSpeed;

    public float getEatingSpeed() {
        return eatingSpeed;
    }

    public void setEatingSpeed(float eatingSpeed) {
        this.eatingSpeed = eatingSpeed;
    }

    public String getWhatDoIEat() {
        return whatDoIEat;
    }

    public void setWhatDoIEat(String whatDoIEat) {
        this.whatDoIEat = whatDoIEat;
    }

}
