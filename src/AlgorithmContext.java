public class AlgorithmContext {
    EncDecAlgorithm alg;

    public void setAlgorithm(EncDecAlgorithm alg) {
        this.alg = alg;
    }

    public String encode(String message) {
        return alg.encode(message);
    }

    public String decrypt(String message) {
        return alg.decrypt(message);
    }
}
