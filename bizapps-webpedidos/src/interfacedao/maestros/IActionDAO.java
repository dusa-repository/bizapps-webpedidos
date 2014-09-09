package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Action;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IActionDAO extends JpaRepository<Action, String> {

	List<Action> findByActionIdNotIn(List<Integer> ids);

	Action findByActionId(int actionId);

}
