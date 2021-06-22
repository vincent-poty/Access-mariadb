package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/user")

public class UserController {
    @Autowired
    private UserRepository userRepository;
    private Integer id;

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewUser(@RequestParam String name, @RequestParam String email,@RequestParam String phone) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        n.setPhone(phone);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody
    User getUser(@PathVariable Integer id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public User updateUser(@RequestBody User user, @PathVariable Integer id)  {
        return userRepository.findById(id)
                .map(u -> {
                    u.setName(user.getName());
                    u.setEmail(user.getEmail());
                    u.setPhone(user.getPhone());
                    return userRepository.save(u);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return userRepository.save(user);
                });
    }
}
