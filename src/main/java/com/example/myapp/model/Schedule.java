@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDateTime meetingTime;

    @NotNull
    @ManyToOne
    private Group group;
    
    // Getters and Setters
}