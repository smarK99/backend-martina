package com.example.inicial1;

import com.example.inicial1.entities.*;
import com.example.inicial1.repositories.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Inicial1Application {
	private static final Logger logger = LoggerFactory.getLogger(Inicial1Application.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EstadoPedidoRepository estadoPedidoRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ProductoInsumoRepository productoInsumoRepository;

	@Autowired
	SucursalProductoRepository sucursalProductoRepository;

	@Autowired
	RepartoRepository repartoRepository;

	@Autowired
	EstadoRepartoRepository estadoRepartoRepository;


	public static void main(String[] args) {
		SpringApplication.run(Inicial1Application.class, args);

		System.out.println("Funcionando");
	}


	@Bean
	@Transactional
	CommandLineRunner init() {
		return args -> {

		/*---------Alta Tipo Usuario---------*/

			TipoUsuario admin = TipoUsuario.builder()
					.descripcionTipoUsuario("Acceso a todas las funcionalidades")
					.nombreTipoUsuario("ADMIN")
					.fechaHoraInicioVigenciaTipoUsuario(LocalDate.now())
					.fechaHoraFinVigenciaTipoUsuario(null)
					.build();
			TipoUsuario cliente = TipoUsuario.builder()
					.descripcionTipoUsuario("Acceso a crear pedido y ver mis pedidos")
					.nombreTipoUsuario("CLIENTE")
					.fechaHoraInicioVigenciaTipoUsuario(LocalDate.now())
					.fechaHoraFinVigenciaTipoUsuario(null)
					.build();
			TipoUsuario repartidor = TipoUsuario.builder()
					.descripcionTipoUsuario("Acceso a crear reparto y ver mis repartos")
					.nombreTipoUsuario("REPARTIDOR")
					.fechaHoraInicioVigenciaTipoUsuario(LocalDate.now())
					.fechaHoraFinVigenciaTipoUsuario(null)
					.build();



			/*---------Alta Usuario---------*/

			Usuario u1 = Usuario.builder()
					.dni("40987221")
					.username("w21 I")
					.email("cliente1@gmail.com")
					.direccion("San Martin 298")
					.fechaHoraAltaUsuario(LocalDate.now())
					.fechaHoraBajaUsuario(null)
					.nombreCompletoUsuario("")
					.password("123")
					.telefono("413219")
					.build();
			u1.setTipoUsuario(cliente);
			usuarioRepository.save(u1);

			Usuario u2 = Usuario.builder()
					.dni("23453123")
					.username("flavio72")
					.email("admin1@gmail.com")
					.direccion("Ozamis 123")
					.fechaHoraAltaUsuario(LocalDate.now())
					.fechaHoraBajaUsuario(null)
					.nombreCompletoUsuario("")
					.password("123")
					.telefono("423949")
					.build();
			u2.setTipoUsuario(admin);
			usuarioRepository.save(u2);

			Usuario u3 = Usuario.builder()
					.dni("41755355")
					.username("sm99")
					.email("repartidor1@gmail.com")
					.direccion("B° 24sept 5")
					.fechaHoraAltaUsuario(LocalDate.now())
					.fechaHoraBajaUsuario(null)
					.nombreCompletoUsuario("Santiago Marquez")
					.password("123")
					.telefono("413219")
					.build();
			u3.setTipoUsuario(repartidor);
			usuarioRepository.save(u3);

			/*---------Alta Sucursal---------*/

			Sucursal s1 = Sucursal.builder()
					.nombreSucursal("BEBE I")
					.direccionSucursal("Sarmiento 432")
					.descripcionSucursal("Erwin boy mitad de cuadra")
					.fechaHoraAltaSucursal(LocalDate.now())
					.fechaHoraBajaSucursal(null)
					.build();
			s1.setSucursalProductoList(null);

			Sucursal s2 = Sucursal.builder()
					.nombreSucursal("W21 I")
					.direccionSucursal("Av. Vistalba 231")
					.descripcionSucursal("")
					.fechaHoraAltaSucursal(LocalDate.now())
					.fechaHoraBajaSucursal(null)
					.build();
			s2.setSucursalProductoList(null);

			/*---------Alta Estado Pedido---------*/

			EstadoPedido creado = EstadoPedido.builder()
					.nombreEstadoPedido("CREADO")
					.descripcionEstadoPedido("El cliente/dueño da de alta un pedido y este toma el estado creado")
					.fechaHoraAltaEstadoPedido(LocalDate.now())
					.fechaHoraBajaEstadoPedido(null)
					.build();

			estadoPedidoRepository.save(creado);

			EstadoPedido enproceso = EstadoPedido.builder()
					.nombreEstadoPedido("EN_PROCESO")
					.descripcionEstadoPedido("El dueño da la orden para empezar a elaborar un pedido y este toma el estado en proceso")
					.fechaHoraAltaEstadoPedido(LocalDate.now())
					.fechaHoraBajaEstadoPedido(null)
					.build();

			estadoPedidoRepository.save(enproceso);

			EstadoPedido listo = EstadoPedido.builder()
					.nombreEstadoPedido("LISTO")
					.descripcionEstadoPedido("El pedido ya esta preparado en la camara de frio")
					.fechaHoraAltaEstadoPedido(LocalDate.now())
					.fechaHoraBajaEstadoPedido(null)
					.build();

			estadoPedidoRepository.save(listo);

			/*---------Alta Categoria---------*/

			Categoria c1 = Categoria.builder()
					.nombreCategoria("Sandwich")
					.descripcionCategoria("Todas las variedades de sandwiches")
					.fechaHoraAltaCategoria(LocalDate.now())
					.fechaHoraBajaCategoria(null)
					.build();

			categoriaRepository.save(c1);

			Categoria c2 = Categoria.builder()
					.nombreCategoria("Postre")
					.descripcionCategoria("Postres en potes de 300gr")
					.fechaHoraAltaCategoria(LocalDate.now())
					.fechaHoraBajaCategoria(null)
					.build();

			categoriaRepository.save(c2);

			/*---------Alta Insumo---------*/

			Insumo i1 = Insumo.builder()
					.nombreInsumo("Jamon Cocido")
					.descripcionInsumo("Pieza de 5kg p/cortar 80% cerdo")
					.precioCompraInsumo(5000.00)
					.fechaHoraAltaInsumo(LocalDate.now())
					.fechaHoraBajaInsumo(null)
					.build();

			Insumo i2 = Insumo.builder()
					.nombreInsumo("Jamon Crudo")
					.descripcionInsumo("Pieza de 4kg p/cortar")
					.precioCompraInsumo(7000.00)
					.fechaHoraAltaInsumo(LocalDate.now())
					.fechaHoraBajaInsumo(null)
					.build();

			Insumo i3 = Insumo.builder()
					.nombreInsumo("Salame picado grueso")
					.descripcionInsumo("Pieza de 3kg p/cortar")
					.precioCompraInsumo(4000.00)
					.fechaHoraAltaInsumo(LocalDate.now())
					.fechaHoraBajaInsumo(null)
					.build();

			/*---------Alta ProductoInsumo---------*/

			ProductoInsumo pi1 = ProductoInsumo.builder()
					.cantidadInsumo(2)
					.build();

			pi1.setInsumo(i1);
			productoInsumoRepository.save(pi1);

			ProductoInsumo pi2 = ProductoInsumo.builder()
					.cantidadInsumo(3)
					.build();

			pi2.setInsumo(i2);
			productoInsumoRepository.save(pi2);

			ProductoInsumo pi3 = ProductoInsumo.builder()
					.cantidadInsumo(1)
					.build();

			pi3.setInsumo(i3);
			productoInsumoRepository.save(pi3);


			/*---------Alta Producto---------*/

			Producto triplejyq = Producto.builder()
					.nombreProducto("Triple Jamon Cocido")
					.descripcionProducto("Sandwich triple de jamon cocido y queso")
					.fechaHoraAltaProducto(LocalDate.now())
					.fechaHoraBajaProducto(null)
					.build();

			triplejyq.setProductoInsumoList(List.of(pi1));
			triplejyq.setCategoria(c1);
			productoRepository.save(triplejyq);

			Producto trsalame = Producto.builder()
					.nombreProducto("Triple Salame")
					.descripcionProducto("Sandwich triple de salame y queso")
					.fechaHoraAltaProducto(LocalDate.now())
					.fechaHoraBajaProducto(null)
					.build();

			trsalame.setProductoInsumoList(List.of(pi2));
			trsalame.setCategoria(c1);
			productoRepository.save(trsalame);

			Producto trcrudo = Producto.builder()
					.nombreProducto("Triple crudo")
					.descripcionProducto("Sandwich triple de Jamon crudo y queso")
					.fechaHoraAltaProducto(LocalDate.now())
					.fechaHoraBajaProducto(null)
					.build();

			trcrudo.setProductoInsumoList(List.of(pi3));
			trcrudo.setCategoria(c1);
			productoRepository.save(trcrudo);

			/*---------Alta DetallePedido---------*/

			DetallePedido dp1 = DetallePedido.builder()
					.cantidadDetallePedido(2)
					.build();

			DetallePedido dp2 = DetallePedido.builder()
					.cantidadDetallePedido(4)
					.build();

			DetallePedido dp3 = DetallePedido.builder()
					.cantidadDetallePedido(1)
					.build();

			/*---------Alta Pedido---------*/

			Pedido p1 = Pedido.builder()
					.descripcionPedido("Pedido #1")
					.fechaHoraAltaPedido(LocalDate.now())
					.fechaHoraBajaPedido(null)
					.build();

			p1.setSucursal(s1);
			p1.setEstadoPedido(creado);
			p1.setDetallePedidoList(List.of(dp2));

			pedidoRepository.save(p1);

			Pedido p2 = Pedido.builder()
					.descripcionPedido("Pedido #2")
					.fechaHoraAltaPedido(LocalDate.now())
					.fechaHoraBajaPedido(null)
					.build();

			p2.setSucursal(s2);
			p2.setEstadoPedido(creado);
			p2.setDetallePedidoList(List.of(dp1,dp3));

			pedidoRepository.save(p2);

			/*---------Alta EstadoReparto---------*/

			EstadoReparto er1 = EstadoReparto.builder()
					.nombreEstadoReparto("EN_CURSO")
					.descripcionEstadoReparto("El recorrido se encuentra siendo realizado")
					.fechaHoraAltaEstadoReparto(LocalDate.now())
					.fechaHoraBajaEstadoReparto(null)
					.build();

			estadoRepartoRepository.save(er1);

			EstadoReparto er2 = EstadoReparto.builder()
					.nombreEstadoReparto("TERMINADO")
					.descripcionEstadoReparto("El recorrido ha terminado")
					.fechaHoraAltaEstadoReparto(LocalDate.now())
					.fechaHoraBajaEstadoReparto(null)
					.build();

			estadoRepartoRepository.save(er2);

			/*---------Alta Reparto---------*/

			Reparto r1 = Reparto.builder()
					.nombreReparto("Tunuyan/San Carlos/E. Bustos")
					.descripcionReparto("Recorrido por el sur, aprox. 6hs")
					.fechaHoraAltaReparto(LocalDate.now())
					.fechaHoraBajaReparto(null)
					.build();

			r1.setPedidosList(List.of(p1));
			r1.setEstadoReparto(er1);
			r1.setUsuario(u3);
			repartoRepository.save(r1);

			Reparto r2 = Reparto.builder()
					.nombreReparto("Lujan/Vistalba")
					.descripcionReparto("Recorrido semanal, aprox. 3hs")
					.fechaHoraAltaReparto(LocalDate.now())
					.fechaHoraBajaReparto(null)
					.build();

			r2.setPedidosList(List.of(p2));
			r2.setEstadoReparto(er2);
			r2.setUsuario(u3);
			repartoRepository.save(r2);


		};
	};




}
