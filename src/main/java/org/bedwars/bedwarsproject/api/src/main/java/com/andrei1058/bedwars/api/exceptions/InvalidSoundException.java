

public class InvalidSoundException extends Throwable {


    public InvalidSoundException(String s) {
        super(s + " is not a valid " + VersionSupport.getName() + " sound! Using defaults..");
    }
}
