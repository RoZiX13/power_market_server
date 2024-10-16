package roz.power.market.crud.repository.repositories.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import roz.power.market.models.postgres.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
