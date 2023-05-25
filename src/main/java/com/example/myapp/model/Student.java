@Entity
public class Student {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Column(unique = true)
    private String indexNumber;

    @ManyToOne
    private Group group;
    
    // Getters and Setters
}
Pamiętaj, że to są tylko propozycje. Przed zastosowaniem zmian powinieneś ocenić, czy są one odpowiednie dla Twojego przypadku.






