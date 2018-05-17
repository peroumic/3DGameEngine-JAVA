package components;

import componentArchitecture.Component;

public class GrowthComponent implements Component {
    private float finalScale;
    private float growthRate;

    public float getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(float growthRate) {
        this.growthRate = growthRate;
    }

    public float getFinalScale() {
        return finalScale;
    }

    public void setFinalScale(float finalScale) {
        this.finalScale = finalScale;
    }
}
