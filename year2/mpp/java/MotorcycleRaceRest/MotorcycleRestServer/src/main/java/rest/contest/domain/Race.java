package rest.contest.domain;

public class Race extends Entity<Integer> {
    private Integer capacity;
    private Integer id;

    public Race() {
        capacity = null;
    }

    public Race(Integer capacity) {
        this.capacity = capacity;
    }

    public Race(Integer capacity, Integer id) {
        this.capacity = capacity;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Race " + getId() + ", capacity " + capacity + "; ";
    }
}
