package servicio.transacciones;

import interfacedao.transacciones.IDetalleOrdenDAO;

import java.util.List;

import modelo.maestros.OrdersDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SDetalleOrden")
public class SDetalleOrden {
	
	@Autowired
	private IDetalleOrdenDAO detalleOrdenDAO;

	public List<OrdersDetail> buscarPorOrden(int orderId) {
		// TODO Auto-generated method stub
		return detalleOrdenDAO.findByIdOrderId(orderId);
	}

}
