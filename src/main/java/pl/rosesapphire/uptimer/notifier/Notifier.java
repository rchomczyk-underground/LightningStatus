package pl.rosesapphire.uptimer.notifier;

public interface Notifier<T, S> {

    void configure(T config);

    void notifyError(S subject);

    void notifyUnreachable(S subject);

    default void sendMessage(String message) {
        // This method is empty by default and should be implemented by extending class if it uses that.
    }

    default void sendMessage(S subject, String message) {
        // This method is empty by default and should be implemented by extending class if it uses that.
    }
}
