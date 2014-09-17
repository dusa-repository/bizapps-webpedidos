package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IProductoDAO;
import modelo.maestros.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SProducto")
public class SProducto {

	@Autowired
	private IProductoDAO productoDAO;

	public Product buscar(String producto) {
		return productoDAO.findByProductId(producto);
	}

	public List<String> buscarMarcas() {
		return productoDAO.findMarcas();
		
	}

	public List<Product> buscarTodos() {
		// TODO Auto-generated method stub
		return productoDAO.findAll();
	}

	public List<Product> buscarPorMarca(String marca) {
		return productoDAO.findByBrand(marca);
	}

	public Product buscarPorIdYMarca(String productoRef, String marcaRef) {
		return productoDAO.findByProductIdAndBrand(productoRef, marcaRef);
	}

	public List<Product> filtroCodigo(String valor) {
		return productoDAO.findByProductIdStartingWithAllIgnoreCase(valor);
	}

}
