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
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProductoInsumoRepository productoInsumoRepository;

	@Autowired
	private SucursalProductoRepository sucursalProductoRepository;

	@Autowired
	private RepartoRepository repartoRepository;

	@Autowired
	private EstadoRepartoRepository estadoRepartoRepository;

	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;


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

			tipoUsuarioRepository.save(admin);

			TipoUsuario cliente = TipoUsuario.builder()
					.descripcionTipoUsuario("Acceso a crear pedido y ver mis pedidos")
					.nombreTipoUsuario("CLIENTE")
					.fechaHoraInicioVigenciaTipoUsuario(LocalDate.now())
					.fechaHoraFinVigenciaTipoUsuario(null)
					.build();

			tipoUsuarioRepository.save(cliente);

			TipoUsuario repartidor = TipoUsuario.builder()
					.descripcionTipoUsuario("Acceso a crear reparto y ver mis repartos")
					.nombreTipoUsuario("REPARTIDOR")
					.fechaHoraInicioVigenciaTipoUsuario(LocalDate.now())
					.fechaHoraFinVigenciaTipoUsuario(null)
					.build();

			tipoUsuarioRepository.save(repartidor);



			/*---------Alta Usuario---------*/

			Usuario u1 = Usuario.builder()
					.dni("40987221")
					.username("W21")
					.email("cliente1@gmail.com")
					.direccion("San Martin 298")
					.fechaHoraAltaUsuario(LocalDate.now())
					.fechaHoraBajaUsuario(null)
					.nombreCompletoUsuario("Federico w21")
					.password("123")
					.telefono("413219")
					.build();
			u1.setTipoUsuarioList(List.of(cliente));
			usuarioRepository.save(u1);

			Usuario u2 = Usuario.builder()
					.dni("23453123")
					.username("flavio74")
					.email("admin1@gmail.com")
					.direccion("Ozamis 123")
					.fechaHoraAltaUsuario(LocalDate.now())
					.fechaHoraBajaUsuario(null)
					.nombreCompletoUsuario("Flavio Sabattini")
					.password("123")
					.telefono("423949")
					.build();
			u2.setTipoUsuarioList(List.of(admin));
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
			u3.setTipoUsuarioList(List.of(repartidor));
			usuarioRepository.save(u3);

			Usuario u4 = Usuario.builder()
					.dni("22386759")
					.username("turcokiosco")
					.email("elturco@gmail.com")
					.direccion("Ozamis 566")
					.fechaHoraAltaUsuario(LocalDate.now())
					.fechaHoraBajaUsuario(null)
					.nombreCompletoUsuario("Turco Mucarzel")
					.password("123")
					.telefono("433619")
					.build();

			u4.setTipoUsuarioList(List.of(cliente));
			usuarioRepository.save(u4);

			Usuario u5 = Usuario.builder()
					.dni("5489750")
					.username("Magus")
					.email("marisol@gmail.com")
					.direccion("Barrio Canciller M D C 32")
					.fechaHoraAltaUsuario(LocalDate.now())
					.fechaHoraBajaUsuario(null)
					.nombreCompletoUsuario("Marisol")
					.password("123")
					.telefono("466678")
					.build();

			u4.setTipoUsuarioList(List.of(cliente));
			usuarioRepository.save(u5);

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
					.nombreSucursal("BEBE II")
					.direccionSucursal("Ozamis y San Martin")
					.descripcionSucursal("Erwin boy esquina")
					.fechaHoraAltaSucursal(LocalDate.now())
					.fechaHoraBajaSucursal(null)
					.build();
			s2.setSucursalProductoList(null);

			Sucursal s3 = Sucursal.builder()
					.nombreSucursal("BEBE III")
					.direccionSucursal("Padre Vazquez 435")
					.descripcionSucursal("Erwin boy original casi esquina")
					.fechaHoraAltaSucursal(LocalDate.now())
					.fechaHoraBajaSucursal(null)
					.build();
			s3.setSucursalProductoList(null);

			Sucursal s4 = Sucursal.builder()
					.nombreSucursal("W21 I")
					.direccionSucursal("Av. Vistalba 231")
					.descripcionSucursal("")
					.fechaHoraAltaSucursal(LocalDate.now())
					.fechaHoraBajaSucursal(null)
					.build();
			s4.setSucursalProductoList(null);

			Sucursal s5 = Sucursal.builder()
					.nombreSucursal("W21 II")
					.direccionSucursal("Av. Saenz Peña 786")
					.descripcionSucursal("Sucursal mitad de cuadra")
					.fechaHoraAltaSucursal(LocalDate.now())
					.fechaHoraBajaSucursal(null)
					.build();
			s5.setSucursalProductoList(null);

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
					.nombreCategoria("Sandwich Simple")
					.descripcionCategoria("Escolares/Economicos")
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

			Categoria c3 = Categoria.builder()
					.nombreCategoria("Sandwich Triple")
					.descripcionCategoria("Triple envasado al vacio")
					.fechaHoraAltaCategoria(LocalDate.now())
					.fechaHoraBajaCategoria(null)
					.build();

			categoriaRepository.save(c3);

			Categoria c4 = Categoria.builder()
					.nombreCategoria("Sandwich Caja Premium")
					.descripcionCategoria("x6 Sandwiches dobles en caja")
					.fechaHoraAltaCategoria(LocalDate.now())
					.fechaHoraBajaCategoria(null)
					.build();

			categoriaRepository.save(c4);

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

			Insumo i4 = Insumo.builder()
					.nombreInsumo("Pan de Miga Comun")
					.descripcionInsumo("Paquete de 2kg cortado")
					.precioCompraInsumo(1500.00)
					.fechaHoraAltaInsumo(LocalDate.now())
					.fechaHoraBajaInsumo(null)
					.build();

			Insumo i5 = Insumo.builder()
					.nombreInsumo("Mayonesa")
					.descripcionInsumo("Pouch 3kg p/untar")
					.precioCompraInsumo(6000.00)
					.fechaHoraAltaInsumo(LocalDate.now())
					.fechaHoraBajaInsumo(null)
					.build();

			Insumo i6 = Insumo.builder()
					.nombreInsumo("Pan de Miga Integral")
					.descripcionInsumo("Paquete de 2kg cortado")
					.precioCompraInsumo(1300.00)
					.fechaHoraAltaInsumo(LocalDate.now())
					.fechaHoraBajaInsumo(null)
					.build();

			/*---------Alta ProductoInsumo---------*/

			ProductoInsumo pi1 = ProductoInsumo.builder()
					.cantidadInsumo(2)
					.build();

			pi1.setInsumo(i1);
			productoInsumoRepository.save(pi1);

			ProductoInsumo pi4 = ProductoInsumo.builder()
					.cantidadInsumo(3)
					.build();

			pi4.setInsumo(i4);
			productoInsumoRepository.save(pi4);

			ProductoInsumo pi5 = ProductoInsumo.builder()
					.cantidadInsumo(5)
					.build();

			pi5.setInsumo(i5);
			productoInsumoRepository.save(pi5);

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

			ProductoInsumo pi6 = ProductoInsumo.builder()
					.cantidadInsumo(3)
					.build();

			pi6.setInsumo(i6);
			productoInsumoRepository.save(pi6);


			/*---------Alta Producto---------*/

			Producto triplejyq = Producto.builder()
					.nombreProducto("Triple Jamon Cocido")
					.descripcionProducto("Sandwich triple de jamon cocido y queso")
					.fechaHoraAltaProducto(LocalDate.now())
					.fechaHoraBajaProducto(null)
					.build();

			triplejyq.setProductoInsumoList(List.of(pi1,pi4,pi5));
			triplejyq.setCategoria(c3);
			productoRepository.save(triplejyq);

			Producto trsalame = Producto.builder()
					.nombreProducto("Triple Salame")
					.descripcionProducto("Sandwich triple de salame y queso")
					.fechaHoraAltaProducto(LocalDate.now())
					.fechaHoraBajaProducto(null)
					.build();

			trsalame.setProductoInsumoList(List.of(pi2,pi4,pi5));
			trsalame.setCategoria(c3);
			productoRepository.save(trsalame);

			Producto trcrudo = Producto.builder()
					.nombreProducto("Triple crudo")
					.descripcionProducto("Sandwich triple de Jamon crudo y queso")
					.fechaHoraAltaProducto(LocalDate.now())
					.fechaHoraBajaProducto(null)
					.build();

			trcrudo.setProductoInsumoList(List.of(pi3,pi6));
			trcrudo.setCategoria(c3);
			productoRepository.save(trcrudo);

			Producto simplejyq = Producto.builder()
					.nombreProducto("Escolar JyQ")
					.descripcionProducto("Sandwich simple de Jamon cocido y queso")
					.fechaHoraAltaProducto(LocalDate.now())
					.fechaHoraBajaProducto(null)
					.build();

			simplejyq.setProductoInsumoList(List.of(pi1, pi4, pi5));
			simplejyq.setCategoria(c1);
			productoRepository.save(simplejyq);

			/*---------Alta DetallePedido---------*/

			DetallePedido dp1 = DetallePedido.builder()
					.cantidadDetallePedido(3)
					.build();

			dp1.setProducto(triplejyq);

			DetallePedido dp2 = DetallePedido.builder()
					.cantidadDetallePedido(2)
					.build();

			dp2.setProducto(trsalame);

			DetallePedido dp3 = DetallePedido.builder()
					.cantidadDetallePedido(2)
					.build();

			dp3.setProducto(trcrudo);

			DetallePedido dp4 = DetallePedido.builder()
					.cantidadDetallePedido(25)
					.build();

			dp4.setProducto(simplejyq);

			DetallePedido dp5 = DetallePedido.builder()
					.cantidadDetallePedido(1)
					.build();

			dp5.setProducto(triplejyq);

			DetallePedido dp6 = DetallePedido.builder()
					.cantidadDetallePedido(1)
					.build();
			dp6.setProducto(trcrudo);

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

			Pedido p3 = Pedido.builder()
					.descripcionPedido("Pedido #3")
					.fechaHoraAltaPedido(LocalDate.now())
					.fechaHoraBajaPedido(null)
					.build();

			p3.setSucursal(s3);
			p3.setEstadoPedido(creado);
			p3.setDetallePedidoList(List.of(dp4));

			pedidoRepository.save(p3);

			Pedido p4 = Pedido.builder()
					.descripcionPedido("Pedido #4")
					.fechaHoraAltaPedido(LocalDate.now())
					.fechaHoraBajaPedido(null)
					.build();

			p4.setSucursal(s4);
			p4.setEstadoPedido(creado);
			p4.setDetallePedidoList(List.of(dp5, dp6));

			pedidoRepository.save(p4);

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
