package server;

import controllers.Home;
import controllers.UsuarioController;

import org.apache.commons.lang3.StringUtils;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import controllers.BandejaDeEntradaController;
import controllers.Compras;
import controllers.MenuComprasPendientesController;
import controllers.CrearCategoriaDefault;
import controllers.MenuCompraPendiente;
import controllers.MenuCompras;
import controllers.CrearEmpresa;
import controllers.CrearEntidadBase;
import controllers.CrearOrganizacionSocial;
import controllers.CrearProveedor;
import controllers.EditarCategoriaDefault;
import controllers.EditarCategoriasDeEntidad;
import controllers.EditarDatosCompraPendiente;
import controllers.EditarEntidadesBaseDeEntidad;
import controllers.EditarItemDeCompraPendiente;
import controllers.EditarItemDePresupuesto;
import controllers.EditarDocumentoComercialDePresupuesto;
import controllers.EditarPresupuestoDeCompraPendiente;
import controllers.Presupuestos;
import controllers.SeleccionarEntidadDeCompraPendiente;
import controllers.SeleccionarProveedorDeCompraPendiente;
import controllers.SeleccionarProveedorDePresupuesto;
import controllers.MenuEntidadesController;
import controllers.MenuProveedores;
import controllers.LoginController;
import controllers.MenuCategorias;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.build();
		
		Spark.staticFiles.location("/public");
		
		UsuarioController usuario = new UsuarioController();
		
		// Compras Pendientes
		MenuComprasPendientesController menuComprasPendientes = new MenuComprasPendientesController();
		Compras compras = new Compras();
		Presupuestos presupuestos = new Presupuestos();
		MenuCompraPendiente menuCompraPendiente = new MenuCompraPendiente();
		
		SeleccionarEntidadDeCompraPendiente seleccionarEntidadDeCompraPendiente = new SeleccionarEntidadDeCompraPendiente();
		SeleccionarProveedorDeCompraPendiente seleccionarProveedorDeCompraPendiente = new SeleccionarProveedorDeCompraPendiente();
		EditarDatosCompraPendiente editarDatosCompraPendiente = new EditarDatosCompraPendiente();
		EditarItemDeCompraPendiente editarItemDeCompraPendiente = new EditarItemDeCompraPendiente();
		EditarPresupuestoDeCompraPendiente editarPresupuestoDeCompraPendiente = new EditarPresupuestoDeCompraPendiente();
		
		// Login
		LoginController loginController = new LoginController();
		
		// Bandeja de Entrada
		BandejaDeEntradaController bandejaDeEntrada = new BandejaDeEntradaController();
		
		// Entidades
		MenuEntidadesController entidad = new MenuEntidadesController();
		CrearEntidadBase crearEntidadBase = new CrearEntidadBase();
		CrearOrganizacionSocial crearOrganizacionSocial = new CrearOrganizacionSocial();
		CrearEmpresa crearEmpresa = new CrearEmpresa();
		EditarCategoriasDeEntidad editarCategoriasDeEntidad = new EditarCategoriasDeEntidad();
		EditarEntidadesBaseDeEntidad editarEntidadesBaseDeEntidad = new EditarEntidadesBaseDeEntidad();
		
		// Categorias
		MenuCategorias menuCategorias = new MenuCategorias();
		CrearCategoriaDefault crearCategoriaDefault = new CrearCategoriaDefault();
		EditarCategoriaDefault editarCategoriaDefault = new EditarCategoriaDefault();
		
		// Proveedores
		MenuProveedores menuProveedores = new MenuProveedores();
		CrearProveedor crearProveedor = new CrearProveedor();
		
		// Presupuestos
		EditarItemDePresupuesto editarItemDePresupuesto = new EditarItemDePresupuesto();
		EditarDocumentoComercialDePresupuesto editarMedioPagoDePresupuesto = new EditarDocumentoComercialDePresupuesto();
		SeleccionarProveedorDePresupuesto seleccionarProveedorDePresupuesto = new SeleccionarProveedorDePresupuesto();
		
		// Compras
		MenuCompras menuCompras = new MenuCompras();
		
		Spark.before((request, response)-> {
			if(!request.pathInfo().equals("/login") &&
					StringUtils.isEmpty(request.cookie("usuario_logueado"))) {
				response.redirect("/login");
			}
			//PerThreadEntityManagers.getEntityManager();
		});
		
		Spark.after((request, response) -> {
            PerThreadEntityManagers.getEntityManager();
            PerThreadEntityManagers.closeEntityManager();
		});
		
		Spark.get("/", Home::home, engine);
		
		Spark.get("/login", loginController::show, engine);
		Spark.post("/login", loginController::login, engine);
		Spark.get("logout", loginController::logout, engine);
		
		Spark.get("/entidades", entidad::show, engine);
		Spark.get("/bandeja_de_entrada", bandejaDeEntrada::bandejaDeEntrada,engine);
		Spark.post("/bandeja_de_entrada/limpiar", bandejaDeEntrada::limpiar_bandeja);
		
		// Menu Compras Pendientes
		
		Spark.get("/compras_pendientes/:id_compra_pendiente/usuarios/:id_usuario/suscribir",menuComprasPendientes::suscribirUsuario,engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/usuarios/:id_usuario/desuscribir",menuComprasPendientes::desuscribirUsuario,engine);

		Spark.post("/compras_pendientes/validar", menuComprasPendientes::validar_compras,engine);
		Spark.get("/compras_pendientes", menuComprasPendientes::show,engine);
		Spark.get("/compras_pendientes/crear", menuComprasPendientes::crearCompra);
		
		// Menu de una Compra Pendiente
		Spark.get("/compras_pendientes/:id_compra_pendiente/seleccionar_entidad", seleccionarEntidadDeCompraPendiente::show,engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/seleccionar_entidad/:id_entidad", seleccionarEntidadDeCompraPendiente::seleccionar,engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/seleccionar_proveedor", seleccionarProveedorDeCompraPendiente::show,engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/seleccionar_proveedor/:id_proveedor", seleccionarProveedorDeCompraPendiente::seleccionar,engine);
		
		Spark.get("/compras_pendientes/:id_compra_pendiente/editar", editarDatosCompraPendiente::show, engine);
		Spark.post("/compras_pendientes/:id_compra_pendiente/editar", editarDatosCompraPendiente::editar, engine);
		
		Spark.get("/compras_pendientes/:idCompra", menuCompraPendiente::menuCompra,engine);
		
		//Spark.get("/compras_pendientes/:idCompra/presupuestos", menuCompraPendiente::editarPresupuesto,engine);
		//Spark.post("/compras_pendientes/:idCompra/presupuestos", menuCompraPendiente::agregarPresupuesto);
		//Spark.post("/compras_pendientes/:idCompra/presupuestos/:idPresup/borrar", menuCompraPendiente::borrarPresupuesto);
		Spark.get("/compras_pendientes/:idBorrado/borrar", menuCompraPendiente::borrarCompra, engine);
		
		Spark.get("/presupuesto/editar", presupuestos::editarPresupuesto, engine);
		
		// Item de compra pendiente
		Spark.get("/compras_pendientes/:id_compra_pendiente/items/crear", editarItemDeCompraPendiente::crearItem, engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/items/:id_item/editar", editarItemDeCompraPendiente::show, engine);
		Spark.post("/compras_pendientes/:id_compra_pendiente/items/:id_item/editar", editarItemDeCompraPendiente::editarItem, engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/items/:id_item/quitar", editarItemDeCompraPendiente::quitarItem, engine);
		
		// Presupuesto de compra pendiente
		Spark.get("/compras_pendientes/:id_compra_pendiente/presupuestos/crear", editarPresupuestoDeCompraPendiente::crearPresupuesto, engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/presupuestos/:id_presupuesto", editarPresupuestoDeCompraPendiente::show, engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/presupuestos/:id_presupuesto/borrar", editarPresupuestoDeCompraPendiente::borrarPresupuesto, engine);
		
		
		Spark.get("/compras_pendientes/:id_compra_pendiente/presupuestos/:id_presupuesto/items/crear", editarItemDePresupuesto::crearItem, engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/presupuestos/:id_presupuesto/items/:id_item/editar", editarItemDePresupuesto::show, engine);
		Spark.post("/compras_pendientes/:id_compra_pendiente/presupuestos/:id_presupuesto/items/:id_item/editar", editarItemDePresupuesto::editarItem, engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/presupuestos/:id_presupuesto/items/:id_item/quitar", editarItemDePresupuesto::quitarItem, engine);		
		
		Spark.get("/compras_pendientes/:id_compra_pendiente/presupuestos/:id_presupuesto/documento_comercial", editarMedioPagoDePresupuesto::show, engine);
		Spark.post("/compras_pendientes/:id_compra_pendiente/presupuestos/:id_presupuesto/documento_comercial", editarMedioPagoDePresupuesto::editar, engine);
		
		Spark.get("/compras_pendientes/:id_compra_pendiente/presupuestos/:id_presupuesto/seleccionar_proveedor", seleccionarProveedorDePresupuesto::show,engine);
		Spark.get("/compras_pendientes/:id_compra_pendiente/presupuestos/:id_presupuesto/seleccionar_proveedor/:id_proveedor", seleccionarProveedorDePresupuesto::seleccionar,engine);
		
		// Menu compras
		Spark.get("/compras", menuCompras::show, engine);
		Spark.get("/compras/:id_compra", menuCompras::mostrarCompra, engine);
		
		
		// Menu login
		Spark.get("/usuario/crear", usuario::crear,engine);
		Spark.post("/usuario/crear", usuario::creacion);
		
		// Menu de proveedores
		Spark.get("/proveedores/registrar_proveedor", crearProveedor::show, engine);
		Spark.post("/proveedores/registrar_proveedor", crearProveedor::crear, engine);
		
		Spark.get("/proveedores", menuProveedores::show, engine);
		Spark.get("proveedores/:id", menuProveedores::mostrarProveedor, engine);
		Spark.get("/proveedores/:id/borrar", menuProveedores::borrarProveedor, engine);
		
		// Menu entidades
		Spark.get("/entidades/crear_empresa", crearEmpresa::show,engine);
		Spark.get("/entidades/crear_organizacion_social", crearOrganizacionSocial::show,engine);
		Spark.get("/entidades/crear_entidad_base", crearEntidadBase::show,engine);
		Spark.post("/entidades/crear_empresa", crearEmpresa::crear,engine);
		Spark.post("/entidades/crear_organizacion_social", crearOrganizacionSocial::crear,engine);
		Spark.post("/entidades/crear_entidad_base", crearEntidadBase::crear,engine);
		
		Spark.get("/entidades/:id/delete", entidad::borrarEntidad, engine);
		
		Spark.get("/entidades/organizaciones_sociales/:id/", entidad::mostrarOrganizacionSocial, engine);
		Spark.get("/entidades/empresas/:id/", entidad::mostrarEmpresa,engine);
		Spark.get("/entidades/entidades_base/:id/", entidad::mostrarEntidadBase,engine);
		
		// Menu categorias
		Spark.get("/categorias", menuCategorias::show, engine);
		Spark.get("/categorias/:id/delete", menuCategorias::borrarCategoria, engine);
		Spark.get("categorias/categorias_default/:id", menuCategorias::mostrarCategoriaDefault, engine);
		
		Spark.get("/categorias/categorias_default/:id/editar", editarCategoriaDefault::show, engine);
		Spark.post("/categorias/categorias_default/:id/editar", editarCategoriaDefault::editar, engine);
		
		Spark.get("/categorias/crear_categoria_default", crearCategoriaDefault::show, engine);
		Spark.post("/categorias/crear_categoria_default", crearCategoriaDefault::crear, engine);
		
		
		Spark.get("/entidades/:tipo_entidad/:id_entidad/editar_categorias", editarCategoriasDeEntidad::show, engine);
		Spark.get("/entidades/:tipo_entidad/:id_entidad/agregar_categoria/:id_categoria", editarCategoriasDeEntidad::agregarCategoria, engine);
		Spark.get("/entidades/:tipo_entidad/:id_entidad/quitar_categoria/:id_categoria", editarCategoriasDeEntidad::quitarCategoria, engine);
		
		Spark.get("/entidades/:tipo_entidad/:id_entidad/editar_entidades_base", editarEntidadesBaseDeEntidad::show, engine);
		Spark.get("/entidades/:tipo_entidad/:id_entidad/agregar_entidad_base/:id_entidad_base", editarEntidadesBaseDeEntidad::agregarEntidadBase, engine);
		Spark.get("/entidades/:tipo_entidad/:id_entidad/quitar_entidad_base/:id_entidad_base", editarEntidadesBaseDeEntidad::quitarEntidadBase, engine);
		
		
		Spark.get("/usuario/crear/erroritem", (request, response) -> {
			return "no existe detalle con ese ID";
		});
		Spark.get("/usuario/crear/errordp", (request, response) -> {
			return "no existe proveedor con ese ID";
		});
		Spark.get("/compras/error/errordetalle", (request, response) -> {
			return "no existe detalle con ese ID";
		});
		Spark.get("/compras/error/errorproveedor", (request, response) -> {
			return "no existe proveedor con ese ID";
		});
		Spark.get("/compras/error/errormediopago", (request, response) -> {
			return "no existe medioDePago con ese ID";
		});
		Spark.get("/compras/error/errorentidad", (request, response) -> {
			return "no existe entidad con ese ID";
		});
		
		Spark.get("/presupuestos/error/errordetalle", (request, response) -> {
			return "no existe detalle con ese ID";
		});
		Spark.get("/presupuestos/error/errorproveedor", (request, response) -> {
			return "no existe proveedor con ese ID";
		});
		Spark.get("/presupuestos/error/errordocumento", (request, response) -> {
			return "no existe documento con ese ID";
		});
		Spark.get("/error/existente", (request, response) -> {
			return "ya existe usuario con ese nombre";
		});		
	}
}