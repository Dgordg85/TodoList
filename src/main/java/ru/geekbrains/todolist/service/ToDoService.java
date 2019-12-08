package ru.geekbrains.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.todolist.persist.entity.ToDo;
import ru.geekbrains.todolist.persist.entity.User;
import ru.geekbrains.todolist.persist.repo.ToDoRepository;
import ru.geekbrains.todolist.persist.repo.UserRepository;
import ru.geekbrains.todolist.repr.TodoRepr;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ru.geekbrains.todolist.service.UserService.getCurrentUser;

@Service
@Transactional
public class ToDoService {

    private ToDoRepository toDoRepository;
    private UserRepository userRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    public Optional<TodoRepr> findById (Long id){
        return toDoRepository.findById(id)
                .map(TodoRepr::new);
    }

    public List<TodoRepr> findToDosByUserId(Long userId){
        return toDoRepository.findToDosByUserId(userId);
    }

    public void save(TodoRepr todoRepr) {
        Optional<String> currentUser = getCurrentUser();
        if (currentUser.isPresent()){
            Optional<User> optUser = userRepository.getUserByUsername(currentUser.get());
            if (optUser.isPresent()){
                ToDo todo = new ToDo();
                todo.setId(todoRepr.getId());
                todo.setDescription(todoRepr.getDescription());
                todo.setTargetDate(todoRepr.getTargetDate());
                todo.setUser(optUser.get());
                toDoRepository.save(todo);
            }
        }
    }

    public void delete(Long id) {
        toDoRepository.findById(id)
            .ifPresent(toDo -> toDoRepository.delete(toDo));
    }
}
