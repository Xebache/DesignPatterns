package direction;


public enum Direction {
    LEFT("vers la gauche"),
    RIGHT("vers la droite"),
    UP("vers le dessus"),
    DOWN("vers le dessous");

    private final String label;

    Direction(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

}