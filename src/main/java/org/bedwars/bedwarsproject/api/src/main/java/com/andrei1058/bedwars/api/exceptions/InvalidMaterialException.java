
public class InvalidMaterialException extends Exception {

    public InvalidMaterialException(String s) {
        super(s + " is not a valid " + VersionSupport.getName() + " material! Using defaults..");
    }
}
