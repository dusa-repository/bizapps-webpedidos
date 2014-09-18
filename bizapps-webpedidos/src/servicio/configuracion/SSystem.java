package servicio.configuracion;

import interfacedao.configuracion.ISystemDAO;

import modelo.configuracion.System;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SSystem")
public class SSystem {

	@Autowired
	private ISystemDAO systemDAO;

	public void guardar(System system) {
		systemDAO.save(system);
	}

	public System buscar(int i) {
		return systemDAO.findOne(i);
	}
}
