package ru.geekbrains.todolist.persist.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.todolist.persist.entity.ToDo;
import ru.geekbrains.todolist.repr.TodoRepr;

import java.util.List;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

    @Query( "select new ru.geekbrains.todolist.repr.TodoRepr(t) from ToDo t " +
            "where t.user.id = :userId")
    List<TodoRepr> findToDosByUserId(@Param("userId") Long userId);

}
