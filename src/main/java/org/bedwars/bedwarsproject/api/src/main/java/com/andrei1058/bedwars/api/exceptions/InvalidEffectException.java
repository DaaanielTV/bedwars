

public class InvalidEffectException extends Throwable {

    public InvalidEffectException(String message) {
        super(message + " is not a valid " + VersionSupport.getName() + " effect! Using defaults..");
    }
}
