abstract class EncDecAlgorithm {
    protected int key;

    EncDecAlgorithm(int key) {
        this.key = key;
    }

    public abstract String encode(String msg);

    public abstract String decrypt(String msg);
}
