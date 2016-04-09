package utah.codecamp;

public class Main {

    public static void main(String[] args){
        RabbitConnectionSettings settings = new RabbitConnectionSettings();
        settings.host = "localhost";
        settings.userName = "guest";
        settings.password = "guest";

        try {
            BasicRabbitClient client = new BasicRabbitClient(settings);
        } catch(Exception e) {
            System.out.println("Unhandled exception! " + e.getMessage());
        }
    }
}
