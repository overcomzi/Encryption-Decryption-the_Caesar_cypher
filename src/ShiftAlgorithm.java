public class ShiftAlgorithm extends EncDecAlgorithm {
    private char[] engUpperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private char[] engLowerAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    ShiftAlgorithm(int key) {
        super(key);
    }

    @Override
    public String encode(String msg) {
        StringBuilder encoded = new StringBuilder();
        for (String ch : msg.split("")) {
            encoded.append(encodeCharacter(ch));
        }
        return encoded.toString();
    }

    private String encodeCharacter(String ch) {
        if (ch.equals(""))
            return "";
        int characterPoint = ch.codePointAt(0);
        String encoded = ch;
        int extraAlphabetSize = 0;
        if (characterPoint >= 'a' && characterPoint <= 'z') {
            characterPoint = characterPoint - 'a';
            if (characterPoint + key < 0) {
                extraAlphabetSize = engLowerAlphabet.length;
            }
            encoded = Character.toString(
                    engLowerAlphabet[
                            (extraAlphabetSize +
                                    (characterPoint + key) % 26) % 26
                            ]);
        }
        if (characterPoint >= 'A' && characterPoint <= 'Z') {
            characterPoint = characterPoint - 'A';
            if (characterPoint + key < 0) {
                extraAlphabetSize = engUpperAlphabet.length ;
            }
            encoded = Character.toString(
                    engUpperAlphabet[
                            (extraAlphabetSize +
                                    (characterPoint + key) % 26) % 26
                            ]);
        }
        return encoded;
    }

    @Override
    public String decrypt(String cipher) {
        key = -key;
        StringBuilder decrypted = new StringBuilder();
        for (String ch : cipher.split("")) {
            decrypted.append(decryptCharacter(ch));
        }
        return decrypted.toString();
    }

    public String decryptCharacter(String ch) {
        return encodeCharacter(ch);
    }
}
