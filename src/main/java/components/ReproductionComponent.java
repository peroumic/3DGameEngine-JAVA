package components;

import componentArchitecture.Component;

public class ReproductionComponent implements Component {
    //how many offspring can entity have in a reproduction tick
    private int numberOfOffsprings;
    //how often is the reproduction tick
    private int reproductionRate;
    //whe was the last reproduction tick
    private long lastReproductionTick;
    //how far from current position can the offspring be spawned
    private float reproductionRadius;

    public int getNumberOfOffsprings() {
        return numberOfOffsprings;
    }

    public void setNumberOfOffsprings(int numberOfOffsprings) {
        this.numberOfOffsprings = numberOfOffsprings;
    }

    public int getReproductionRate() {
        return reproductionRate;
    }

    public void setReproductionRate(int reproductionRate) {
        this.reproductionRate = reproductionRate;
    }

    public long getLastReproductionTick() {
        return lastReproductionTick;
    }

    public void setLastReproductionTick(long lastReproductionTick) {
        this.lastReproductionTick = lastReproductionTick;
    }

    public float getReproductionRadius() {
        return reproductionRadius;
    }

    public void setReproductionRadius(float reproductionRadius) {
        this.reproductionRadius = reproductionRadius;
    }
}
