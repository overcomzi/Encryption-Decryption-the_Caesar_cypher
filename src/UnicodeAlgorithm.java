public class UnicodeAlgorithm extends EncDecAlgorithm {
    UnicodeAlgorithm(int key) {
        super(key);
    }

    @Override
    public String encode(String message) {
        StringBuilder encoded = new StringBuilder();
        for (String ch : message.split("")) {
            encoded.append(encodeCharacter(ch));
        }
        return encoded.toString();
    }

    public String encodeCharacter(String ch) {
        if (ch.equals(""))
            return "";
        String encodedCh = ch;
        int codePoint = ch.codePoints().toArray()[0];
        int newCodePoint = (codePoint + key);
        encodedCh = new String(Character.toChars(newCodePoint));
        return encodedCh;
    }

    public String decryptCharacter(String ch) {
        return encodeCharacter(ch);
    }

    public String decrypt(String cipher) {
        key = -key;
        StringBuilder decrypted = new StringBuilder();
        for (String ch : cipher.split("")) {
            decrypted.append(decryptCharacter(ch));
        }
        return decrypted.toString();
    }
}
