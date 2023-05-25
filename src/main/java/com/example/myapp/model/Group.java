@Entity
public class Group {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy="group")
    private List<Student> students;

    // Getters and Setters
}