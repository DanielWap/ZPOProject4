@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Schedule schedule;

    private Status status; // Obecny, Spóźniony, Nieobecny
    
    // Getters and Setters
}

public enum Status {
    PRESENT,
    LATE,
    ABSENT