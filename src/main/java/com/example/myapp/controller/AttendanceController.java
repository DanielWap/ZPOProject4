@Controller
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/attendances")
    public String listAttendances(Model model) {
        List<Attendance> attendances = attendanceService.findAll();
        model.addAttribute("attendances", attendances);
        return "attendances";
    }

    @GetMapping("/attendances/new")
    public String showAttendanceForm(Model model) {
        model.addAttribute("attendance", new Attendance());
        return "add-attendance";
    }

    @PostMapping("/attendances")
    public String addAttendance(@ModelAttribute("attendance") @Valid Attendance attendance, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-attendance";
        }
        attendanceService.save(attendance);
        return "redirect:/attendances";
    }

    @GetMapping("/attendances/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Attendance attendance = attendanceService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attendance Id:" + id));
        model.addAttribute("attendance", attendance);
        return "update-attendance"; // nazwa szablonu do aktualizacji obecności
    }

    @PostMapping("/attendances/update/{id}")
    public String updateAttendance(@PathVariable("id") long id, @Valid Attendance attendance,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            attendance.setId(id);
            return "update-attendance";
        }
        attendanceService.save(attendance);
        return "redirect:/attendances";
    }

    @GetMapping("/attendances/delete/{id}")
    public String deleteAttendance(@PathVariable("id") long id, Model model) {
        Attendance attendance = attendanceService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attendance Id:" + id));
        attendanceService.delete(id);
        model.addAttribute("attendances", attendanceService.findAll());
        return "attendances";
    }
}