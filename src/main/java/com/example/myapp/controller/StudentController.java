@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "students"; // Nazwa szablonu bez rozszerzenia .html
    }

    // i inne endpointy takie jak dodawanie, usuwanie studentów itp.
}