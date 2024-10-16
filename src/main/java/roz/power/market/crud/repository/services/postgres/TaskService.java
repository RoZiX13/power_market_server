package roz.power.market.crud.repository.services.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.power.market.crud.repository.repositories.postgres.TaskRepository;
import roz.power.market.models.postgres.Task;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	
	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	@Transactional(readOnly = false)
	public void save(Task task) {
		taskRepository.save(task);
	}
	
	
}
