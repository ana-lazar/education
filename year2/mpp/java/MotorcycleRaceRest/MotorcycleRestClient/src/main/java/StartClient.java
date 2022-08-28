import domain.Race;

public class StartClient {
    public static void main(String[] args) {
        RaceRestClient client = new RaceRestClient();
        System.out.println("Created client \n");
        try {
            getAllRaces(client);
//            getById(client);
//            add(client);
//            update(client);
//            delete(client);
        } catch (ServiceException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void getAllRaces(RaceRestClient client) {
        System.out.println("Get all races: ");
        client.getAll().forEach(System.out::println);
        System.out.println();
    }

    public static void getById(RaceRestClient client) {
        System.out.println("Get by id:");
        Race race = client.getById(7);
        System.out.println(race.toString());
    }

    public static void add(RaceRestClient client) {
        System.out.println("Add:");
        Race race = new Race(56);
        Race created = client.create(race);
        System.out.println(created.toString());
    }

    public static void update(RaceRestClient client) {
        System.out.println("Update:");
        Race race = new Race(44, 14);
        client.update(race);
        System.out.println("Updated race!");
    }

    public static void delete(RaceRestClient client) {
        System.out.println("Delete:");
        client.delete(14);
        System.out.println("Deleted race!");
    }
}
