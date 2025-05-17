import java.util.ArrayList;
import java.util.List;

// Starter Code:
public interface Observer<T> {
    void update(Notification<T> notification);
}

public abstract class Notification<T> {
    private T content;
    public Notification(T content) { this.content = content; }
    public T getContent() { return content; }
}

public interface NotificationFactory<T> {
    Notification<T> createNotification(T content);
}

// Example usage:
// Notification<String> email = new EmailNotification("Welcome to MarketBridge!");

class EmailNotification extends Notification<String> {
    public EmailNotification(String content) {
        super(content);
    }
}

class SMSNotification extends Notification<String> {
    public SMSNotification(String content) {
        super(content);
    }
}

class EmailObserver implements Observer<Notification<?>> {
    @Override
    public void update(Notification<?> notification) {
        System.out.println(notification.getContent());
    }
}

class SMSObserver implements Observer<Notification<?>> {
    @Override
    public void update(Notification<?> notification) {
        System.out.println(notification.getContent());
    }
}

class EmailNotificationFactory implements NotificationFactory<String> {
    @Override
    public Notification<String> createNotification(String content) {
        return new EmailNotification(content);
    }
}

class SMSNotificationFactory implements NotificationFactory<String> {
    @Override
    public Notification<String> createNotification(String content) {
        return new SMSNotification(content);
    }
}

class NotificationBuilder<T> {
    private T content;
    public NotificationBuilder<T> setContent(T content) {
        this.content = content;
        return this;
    }
    public Notification<T> buildEmailNotification() {
        return new EmailNotification((String) content);
    }
    public Notification<T> buildSMSNotification() {
        return new SMSNotification((String) content);
    }
}

class NotificationManager<T> {
    private final List<Observer<? super Notification<T>>> observers = new ArrayList<>();

    public void registerObserver(Observer<? super Notification<T>> observer) {
        observers.add(observer);
    }

    public void notifyObservers(Notification<T> notification) {
        for (Observer<? super Notification<T>> observer : observers) {
            observer.update(notification);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        NotificationFactory<String> emailFactory = new EmailNotificationFactory();
        NotificationFactory<String> smsFactory = new SMSNotificationFactory();

        Notification<String> email = emailFactory.createNotification("You've successfully joined MarketBridge via Email.");
        Notification<String> sms = smsFactory.createNotification("Thanks for joining MarketBridge! SMS alerts enabled.");

        NotificationManager<String> manager = new NotificationManager<>();
        manager.registerObserver(new EmailObserver());
        manager.registerObserver(new SMSObserver());

        manager.notifyObservers(email);
        manager.notifyObservers(sms);

        NotificationBuilder<String> builder = new NotificationBuilder<>();
        Notification<String> builtEmail = builder.setContent("Builder Email Alert").buildEmailNotification();
        manager.notifyObservers(builtEmail);
    }
}
