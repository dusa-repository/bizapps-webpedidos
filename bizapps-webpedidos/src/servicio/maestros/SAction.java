package servicio.maestros;

import interfacedao.maestros.IActionDAO;

import java.util.List;

import modelo.maestros.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SAction")
public class SAction {

	@Autowired
	private IActionDAO actionDAO;

	public List<Action> buscarTodos() {
		return actionDAO.findAll();
	}

	public List<Action> buscarAccionesDisponibles(List<Integer> ids) {
		return actionDAO.findByActionIdNotIn(ids);
	}

	public Action buscar(int actionId) {
		// TODO Auto-generated method stub
		return actionDAO.findByActionId(actionId);
	}

}
