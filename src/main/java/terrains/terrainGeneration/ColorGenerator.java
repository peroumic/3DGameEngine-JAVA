package terrains.terrainGeneration;

import toolbox.Color;
import toolbox.Maths;

public class ColorGenerator {

    private final float spread;
    private final float halfSpread;

    private final Color[] biomeColors;
    private final float part;

    public ColorGenerator(float spread) {
        this.biomeColors = new Color[] { new Color(201, 178, 99, true),
                new Color(135, 184, 82, true), new Color(80, 171, 93, true), new Color(120, 120, 120, true),
                new Color(200, 200, 210, true) };;
        this.spread = spread;
        this.halfSpread = spread / 2f;
        this.part = 1f / (biomeColors.length - 1);
    }

    public Color[][] generateColors(float[][] heights, float amplitude) {
        Color[][] colors = new Color[heights.length][heights.length];
        for (int z = 0; z < heights.length; z++) {
            for (int x = 0; x < heights[z].length; x++) {
                colors[z][x] = calculateColor(heights[z][x], amplitude);
            }
        }
        return colors;
    }


    private Color calculateColor(float height, float amplitude) {
        float value = (height + amplitude) / (amplitude * 2);
        value = Maths.clamp((value - halfSpread) * (1f / spread), 0f, 0.9999f);
        int firstBiome = (int) Math.floor(value / part);
        float blend = (value - (firstBiome * part)) / part;
        return Color.interpolateColors(biomeColors[firstBiome], biomeColors[firstBiome + 1], blend, null);
    }

}