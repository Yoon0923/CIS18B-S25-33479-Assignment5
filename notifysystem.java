import java.util.ArrayList;
import java.util.List;

// Starter Code:
interface Observer<T> {
    void update(Notification<T> notification);
}

abstract class Notification<T> {
    private T content;
    public Notification(T content) { this.content = content; }
    public T getContent() { return content; }
}

interface NotificationFactory<T> {
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

class EmailObserver implements Observer<String> {
    public void update(Notification<String> notification) {
        System.out.println(notification.getContent());
    }
}

class SMSObserver implements Observer<String> {
    public void update(Notification<String> notification) {
        System.out.println(notification.getContent());
    }
}

class EmailNotificationFactory implements NotificationFactory<String> {
    public Notification<String> createNotification(String content) {
        return new EmailNotification(content);
    }
}

class SMSNotificationFactory implements NotificationFactory<String> {
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
    public Notification<String> buildEmailNotification() {
        return new EmailNotification((String) content);
    }
    public Notification<String> buildSMSNotification() {
        return new SMSNotification((String) content);
    }
}

class NotificationManager<T> {
    private final List<Observer<T>> observers = new ArrayList<>();

    public void registerObserver(Observer<T> observer) {
        observers.add(observer);
    }

    public void notifyObservers(Notification<T> notification) {
        for (Observer<T> observer : observers) {
            observer.update(notification);
        }
    }
}

class Main {
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
