@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Student student;

    @NotNull
    @ManyToOne
    private Schedule schedule;

    @NotNull
    private Status status; // Obecny, Spóźniony, Nieobecny
    
    // Getters and Setters
}