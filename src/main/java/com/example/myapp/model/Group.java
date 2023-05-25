@Entity
public class Group {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy="group", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

    // Getters and Setters
}