@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private LocalDateTime meetingTime;

    @ManyToOne
    private Group group;
    
    // Getters and Setters
}