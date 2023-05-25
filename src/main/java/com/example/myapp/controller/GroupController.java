@Controller
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public String listGroups(Model model) {
        List<Group> groups = groupService.findAll();
        model.addAttribute("groups", groups);
        return "groups";
    }

    @GetMapping("/groups/new")
    public String showGroupForm(Model model) {
        model.addAttribute("group", new Group());
        return "add-group";
    }

    @PostMapping("/groups")
    public String addGroup(@ModelAttribute("group") @Valid Group group, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-group";
        }
        groupService.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/groups/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Group group = groupService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        model.addAttribute("group", group);
        return "update-group"; // nazwa szablonu do aktualizacji grupy
    }

    @PostMapping("/groups/update/{id}")
    public String updateGroup(@PathVariable("id") long id, @Valid Group group,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            group.setId(id);
            return "update-group";
        }
        groupService.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/groups/delete/{id}")
    public String deleteGroup(@PathVariable("id") long id, Model model) {
        Group group = groupService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        groupService.delete(id);
        model.addAttribute("groups", groupService.findAll());
        return "groups";
    }
}