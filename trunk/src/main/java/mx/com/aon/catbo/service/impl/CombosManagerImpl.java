package mx.com.aon.catbo.service.impl;

import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.CombosManager;
import mx.com.aon.catbo.model.*;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManager;

import org.apache.log4j.Logger;
import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Clase que implementa los servicios de la interface CombosManager
 *
 */
public class CombosManagerImpl extends AbstractManager implements
        CombosManager {

	public static Logger logger = Logger.getLogger(CombosManagerImpl.class);

    public static String TABLA_TIPO_NIVEL = "MNIVEL";

    public static String TABLA_FRECUENCIA = "CFRECUENCIA";

    public static String TABLA_LINEA_CAPTURA = "TLINEANEGOCIO";
    
    public static String TABLA_ESTADOS_EJECUTIVO = "EDOEJECT";

    public static String TABLA_LINEAS_ATENCION = "TLINEANEGOCIO";
    	
    public static String TABLA_GRUPO_PERSONAS = "MGRUPOPERSONAS";

    public static String TABLA_TIPOS_AGRUPACION = "TTIPOAGRUPACION";

    public static String TABLA_ESTADOS = "TACTIVO";

    public static String TABLA_INDICADOR_NUMERACION = "CINCSUBI";

    public static String TABLA_INDICADOR_SP = "CSUFPRE";

    public static String TABLA_FORMA_CALCULO_FOLIO = "CCALCFOL";
    
    public static String TABLA_ESTADO_CIVIL = "CESTCIVI";

    public static String TABLA_GRUPO_CORPORATIVO = "MGRUPOPERSONAS";
    
    public static String TABLA_INDICADOR_SINO = "UNIVT001";
    
    public static String TABLA_INDICADOR_FORMA_CALCULO = "CFORMACALCULO";
    
    public static String TABLA_INDICADOR_FORMA_ATRIBUTO = "CTABLACALCULO";
    
    public static String TABLA_TIPO_OBJETO = "TTIPOOBJETO";
    
    public static String TABLA_TIPO_GUION = "CTIPGUION";
    
    public static String TABLA_TIPO_METODO_ENVIO = "CMETENVIO";
    
    public static String TABLA_OBTENER_DATOS_CATALOGO = "INDNUMER";
    
    public static String TABLA_DATOS_CATALOGO = "CATBOMODUL";
    
    public static String TABLA_CATALOGO_PRIORIDAD = "CATBOPRIOR";
    
    public static String TABLA_OBTIENE_NIVEL_ATENCION = "CATBONIVAT";
    
    public static String TABLA_OBTIENE_UNIDAD_TIEMPO = "CATBOUNT";
    
    public static String TABLA_OBTIENE_ROL = "CATBOROLMA"; 
    
    public static String TABLA_OBTIENE_TIPOS_ARCHIVOS = "CATBOTIPAR";
    
    public static String TABLA_INDICADOR_AVISO = "TSINOA";

    public static String TABLA_MATRIZ_RESPONSABLES = "CATBOUSUAR";
   
    public static String TABLA_TAREAS_PRIORIDAD = "CATBOPRIOR";
    
    public static String TABLA_TAREAS_ESTATUS = "TACTIVO";
    
    public static String TABLA_ENCUESTAS_COMBO_MODULO = "CATBOMODUL";
    
    public static String TABLA_ENCUESTAS_COMBO_CAMPANIAS = "CATBOCAMEC";

    public static String TABLA_OBTENER_PAIS = "CPAISISO";

    public static String TABLA_OBTENER_MES = "CMESISO";
    
    public static String TABLA_GET_LISTAS = "TSINO";
    
    
    //Métodos de lectura de combos desplegables
	@SuppressWarnings("unchecked")
	public PagedList comboTipoSituacion () throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<TiposSituacionVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("TIPO_SITUACION");
			ArrayList<TiposSituacionVO> invoke = (ArrayList<TiposSituacionVO>)endpoint.invoke(null);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException("No se pudo obtener el Listado de Situaciones");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}
	
	@SuppressWarnings("unchecked")
	public PagedList comboCoberturas () throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<TiposCoberturasVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("TIPOS_COBERTURAS");
			ArrayList<TiposCoberturasVO> invoke = (ArrayList<TiposCoberturasVO>)endpoint.invoke(null);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException ("No se pudo obtener el Listado de Coberturas");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

	@SuppressWarnings("unchecked")
	public PagedList comboAseguradoras() throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<AseguradoraVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("LISTA_ASEGURADORAS");
			ArrayList<AseguradoraVO> invoke = (ArrayList<AseguradoraVO>)endpoint.invoke(null);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException ("No se pudo obtener las Aseguradoras");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

	@SuppressWarnings("unchecked")
	public PagedList getDetallePlanesXCliente(int start, int limit,
			String codigoProducto, String codigoPlan,
			String codigoTipoSituacion, String garantia)
			throws ApplicationException {
		PagedList pagedList = new PagedList ();
		List<DetallePlanXClienteVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("");
			HashMap map = new HashMap();
			map.put("pv_producto_i", codigoProducto);
			map.put("pv_plan_i", codigoPlan);
			map.put("pv_situacion_i", codigoTipoSituacion);
			map.put("pv_garantia_i", garantia);
			ArrayList<DetallePlanXClienteVO> invoke = (ArrayList<DetallePlanXClienteVO>) endpoint.invoke(map);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException("No se pudo obtener planes por cliente");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}


    /*public ArrayList<ElementoComboBoxVO> obtenerProductos() throws ApplicationException {
        return null;  //Todo borrar despues
    }*/
	//TULY - ESTE CODIGO ESTA HARCODEADO PARA LA PANTALLA DE MOVIMIENTOS POR CASO
	//TAMBIEN DEBE USARSE EN LA DE ALTA DE CASOS
	@SuppressWarnings({ "unchecked", "unchecked" })
	public List obtenerProductos() throws ApplicationException {
		ArrayList list = new ArrayList();
		
		ElementoComboBoxVO vo = new ElementoComboBoxVO(); 
		vo.setCodigo("2");
		vo.setDescripcion("Autos");
		
		list.add(vo);
		return list;
    }

	@SuppressWarnings("unchecked")
	public PagedList comboSecciones() throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<SeccionVO> lista = null;
		try {
			Endpoint endpoint = this.endpoints.get("P_SECCION_CHECK");

			ArrayList<SeccionVO> invoke = (ArrayList<SeccionVO>) endpoint.invoke(null);
			lista = invoke;

		} catch (BackboneApplicationException e) {
			logger.error("Se produjo una excepcion al ejecutar obtieneSeccion",
					e);
			throw new ApplicationException(e);
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

	public PagedList comboEstados() throws ApplicationException {
		PagedList pagedList = new PagedList();
		ArrayList<EstadoVO> lista = null;
		try {
			Endpoint endpoint = this.endpoints.get("P_OBTENE_ESTADO");
			ArrayList<EstadoVO> invoke = (ArrayList<EstadoVO>) endpoint.invoke(null);
			lista = invoke;
		} catch (BackboneApplicationException e) {
			logger.error("Se produjo una excepcion al ejecutar obtieneEstado",
					e);
			throw new ApplicationException(e);
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

    @SuppressWarnings("unchecked")
    public PagedList obtenerClientesCorp() throws ApplicationException {
        PagedList pagedList = new PagedList();
        List<ClientesCorpoVO> lista = null;
        try {
            Endpoint endpoint = endpoints.get("OBTENER_CLIENTES_CORPO");
            ArrayList<ClientesCorpoVO> invoke = (ArrayList<ClientesCorpoVO>)endpoint.invoke(null);
            lista = invoke;
        }catch (BackboneApplicationException bae) {
            logger.error(bae.getMessage(), bae);
            throw new ApplicationException("No se ejecuto la busqueda de clientes");
        }
        pagedList.setItemsRangeList(lista);
        return pagedList;
    }

    @SuppressWarnings("unchecked")
    public List comboClientes() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_CLIENTES");
	}
    
    @SuppressWarnings("unchecked")
    public List comboPadres() throws ApplicationException {
            return getAllBackBoneInvoke(null,"OBTIENE_PADRE");
    }


    public List comboTiposNivel() throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pi_tabla", TABLA_TIPO_NIVEL);
        return getAllBackBoneInvoke(map,"OBTIENE_TIPO_NIVEL");
    }
    
    public List comboRamos() throws ApplicationException {
    	
        return getAllBackBoneInvoke(null,"OBTIENE_RAMOS");
    }
    //Cambiar el anterior
    public List comboRamos2(String cdunieco, String cdelemento, String cdramo) throws ApplicationException {
    	 HashMap map = new HashMap();
         map.put("cdunieco", cdunieco);
         map.put("cdelemento", cdelemento);
         map.put("cdramo", cdramo);
        return getAllBackBoneInvoke(map,"OBTIENE_RAMOS_2"); 
    } 
   
    public List comboSubRamos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_SUBRAMOS");
    }
    
    public List comboProductos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_PRODUCTOS_AYUDA");
    }
    
    public List comboIdiomas() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_IDIOMAS_AYUDA");
    }
    
    public List comboUsuarios() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_USUARIOS");
    }
    
    public List comboProcesos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_PROCESOS_ALERTA");
    }
    
    public List comboTemporalidades() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_TEMPORALIDADES");
    }
    
    public List comboRegiones() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_REGIONES");
    }
    
    public List comboEstadosDescuentos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_ESTADOS_DESCUENTOS");
    }
    
    public List comboProductosDescuentos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_PRODUCTOS_DESCUENTOS");
    }
    
    @SuppressWarnings("unchecked")
	public List comboPlanes(String cdElemento,String cdUniEco,String cdRamo,String cdTipSit ) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdElemento", cdElemento);
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdTipSit", cdTipSit);
		return getAllBackBoneInvoke(map,"OBTIENE_PLANES");
	}

	public List comboTiposDscto() throws ApplicationException {
		return getAllBackBoneInvoke(null,"OBTIENE_TIPOS_DSCTO");
	}

	public List comboConfAlertasAutoClientes() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CONF_ALERTAS_AUTO_CLIENTES");
	}
	public List comboConfAlertasAutoProcesos() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CONF_ALERTAS_AUTO_PROCESOS");
	}

	public List comboConfAlertasAutoTemporalidad() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CONF_ALERTAS_AUTO_TEMPORALIDAD");
	}

	public List comboConfAlertasAutoRol() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CONF_ALERTAS_AUTO_ROL");
	}

	public List comboConfAlertasAutoTipoRamo() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CONF_ALERTAS_AUTO_TIPO_RAMO");
	}


    public List comboSiNo() throws ApplicationException {
        return getAllBackBoneInvoke(null, "OBTIENE_SINO");
    }

    @SuppressWarnings("unchecked")
    public List comboFrecuencias() throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pi_tabla", TABLA_FRECUENCIA);
        return getAllBackBoneInvoke(map,"OBTIENE_TIPO_NIVEL");
    }


    public List comboConceptosProducto() throws ApplicationException {
        return getAllBackBoneInvoke(null, "OBTIENE_CONCEPTO_PRODUCTO");
    }

    @SuppressWarnings("unchecked")
	public List comboProductosAyuda(String cdUnieco, String cdRamo, String subRamo) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdunieco_i", cdUnieco);
        map.put("pv_cdtipram_i", cdRamo);
        map.put("pv_cdsubram_i", subRamo);
        return getAllBackBoneInvoke(map,"OBTIENE_PRODUCTOS_C");
    }

    @SuppressWarnings("unchecked")
    public List comboCoberturasProducto(String cdRamo) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdramo_i", cdRamo);
        return getAllBackBoneInvoke(map,"OBTIENE_COBERTURAS_PRODUCTO");
    }

    @SuppressWarnings("unchecked")
    public List comboLineasCaptura() throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pi_tabla", TABLA_LINEA_CAPTURA);
        return getAllBackBoneInvoke(map,"OBTIENE_TIPO_NIVEL");
    }

    @SuppressWarnings("unchecked")
	public List comboGrupoPersonas() throws ApplicationException {
		 HashMap map = new HashMap();
	     map.put("pi_tabla", TABLA_GRUPO_PERSONAS);
	     return getAllBackBoneInvoke(map,"OBTIENE_GRUPO_PERSONAS");
		
	}

	public List comboEjecutivosAon() throws ApplicationException {
		 return getAllBackBoneInvoke(null, "OBTIENE_EJECUTIVOS_AON");
	}

	@SuppressWarnings("unchecked")
	public List comboEstadosEjecutivo() throws ApplicationException {
		 HashMap map = new HashMap();
		    map.put("pi_tabla", TABLA_ESTADOS);
		    return getAllBackBoneInvoke(map,"OBTIENE_ESTADOS_EJECUTIVO");
	}

	@SuppressWarnings("unchecked")
	public List comboLineasAtencion() throws ApplicationException {
		 HashMap map = new HashMap();
	     map.put("pi_tabla", TABLA_LINEAS_ATENCION);
	     return getAllBackBoneInvoke(map,"OBTIENE_LINEAS_ATENCION");
		
	}

	public List comboObtieneCliente(String cdPerson) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List comboGrupoPersonasCliente(String cdElemento) throws ApplicationException {

		        HashMap map = new HashMap();
		        map.put("po_cdelemto", cdElemento);
		        return getAllBackBoneInvoke(map,"OBTIENE_GRUPO_PERSONAS_CLIENTE");

	}

	@SuppressWarnings("unchecked")
	public List comboPolizasPorAseguradora (String cdUniEco) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("cdUniEco", cdUniEco);
		return getAllBackBoneInvoke(map, "OBTIENE_POLIZA_POR_ASEGURADORA");
	}

	@SuppressWarnings("unchecked")
    public List comboReciboPorPolizaPorAseguradora (String cdUniEco, String cdRamo, String nmPoliza) throws ApplicationException {
    	HashMap map = new HashMap ();
    	map.put("cdUniEco", cdUniEco);
    	map.put("cdRamo", cdRamo);
    	map.put("nmPoliza", nmPoliza);
    	return getAllBackBoneInvoke(map, "OBTIENE_RECIBO_POR_POLIZA_POR_ASEGURADORA");
    }

	@SuppressWarnings("unchecked")
    public List comboVinculosPadrePorEstructura (String codigoEstructura) throws ApplicationException {
    	HashMap map = new HashMap ();
    	map.put("codigoEstructura", codigoEstructura);
    	return getAllBackBoneInvoke(map, "OBTIENE_VINCULOS_PADRES_POR_ESTRUCTURA");
    }

	@SuppressWarnings("unchecked")
	public List comboClientesCorporativo(String cdPerson)throws ApplicationException {
		HashMap map = new HashMap ();
    	map.put("cdPerson", cdPerson);
    	return getAllBackBoneInvoke(map, "OBTIENE_CLIENTES_CORPORATIVO");	
    	}

	@SuppressWarnings("unchecked")
	public List comboProductosEjecutivosCuenta(String cdPerson, String cdTipRam)
			throws ApplicationException {
		HashMap map = new HashMap ();
    	map.put("cdPerson", cdPerson);
    	map.put("cdTipRam", cdTipRam);
    	return getAllBackBoneInvoke(map, "OBTIENE_PRODUCTOS_EJECUTIVOS_CUENTA");	
	}

	@SuppressWarnings("unchecked")
    public List comboAseguradorasCliente(String cliente) throws ApplicationException {
        HashMap map = new HashMap ();
        map.put("pv_cdelemento_i", cliente);
        return getAllBackBoneInvoke(map, "OBTENER_ASEGURADORAS_CLIENTE");        
    }

	@SuppressWarnings("unchecked")
    public List comboProductosAseguradoraCliente(String cliente, String aseguradora) throws ApplicationException {
        HashMap map = new HashMap ();
        map.put("pv_cdunieco_i", aseguradora);
        map.put("pv_cdelemento_i", cliente);
        return getAllBackBoneInvoke(map, "OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE");
    }

	@SuppressWarnings("unchecked")
    public List comboRamosCliente(String cliente) throws ApplicationException {
        HashMap map = new HashMap ();
        map.put("pv_cdelement_i", cliente);
        return getAllBackBoneInvoke(map, "OBTENER_RAMOS_CLIENTE");
    }
	@SuppressWarnings("unchecked")
    public List comboTiposAgrupacion() throws ApplicationException {
         HashMap map = new HashMap();
         map.put("pi_tabla", TABLA_TIPOS_AGRUPACION);
         return getAllBackBoneInvoke(map,"OBTIENE_LINEAS_ATENCION");

    }
	@SuppressWarnings("unchecked")
    public List comboEstadosAgrupacionPoliza() throws ApplicationException {
         HashMap map = new HashMap();
         map.put("pi_tabla", TABLA_ESTADOS);
         return getAllBackBoneInvoke(map,"OBTIENE_LINEAS_ATENCION");

    }
	@SuppressWarnings("unchecked")
	public List comboTiposRamoClienteEjecutivoCuenta(String cdElemento)throws ApplicationException {
		 HashMap map = new HashMap();
         map.put("cdElemento", cdElemento);
         return getAllBackBoneInvoke(map,"OBTIENE_TIPOS_RAMO_CLIENTE_EJEC_CUENTA");
	}
	@SuppressWarnings("unchecked")
	public List comboAseguradoraPorElemento(String cdElemento)
			throws ApplicationException {
		//Usado en Agrupacion Por Papel
		HashMap map = new HashMap();
		map.put("cdElemento", cdElemento);
		return getAllBackBoneInvoke(map, "OBTIENE_ASEGURADORA_POR_CLIENTE");
	}
	@SuppressWarnings("unchecked")
	public List comboPapelesPorCliente(String codigoElemento)
			throws ApplicationException {
		//Usado en Agrupacion Por Papel
		HashMap map = new HashMap();
		map.put("codigoElemento", codigoElemento);
		return getAllBackBoneInvoke(map, "OBTIENE_PAPELES_POR_CLIENTE");
	}
	@SuppressWarnings("unchecked")
	public List comboProductosPorAseguradoraYCliente(String codigoAseguradora,
			String codigoElemento, String codigoRamo)
			throws ApplicationException {
		//Usado en Agrupacion Por Papel
		HashMap map = new HashMap();
		map.put("codigoAseguradora", codigoAseguradora);
		map.put("codigoElemento", codigoElemento);
		map.put("codigoRamo", codigoRamo);
		return getAllBackBoneInvoke(map, "OBTIENE_PRODUCTOS_POR_ASEGURADORA_Y_CLIENTE");
	}
	@SuppressWarnings("unchecked")
	public List comboTipoPersonaJ() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPO_PERSONA_J");
	}
    
    public List comboBloques() throws ApplicationException {
        return getAllBackBoneInvoke(null, "OBTIENE_BLOQUES");
    }
    @SuppressWarnings("unchecked")
	public List comboFormaCalculoFolioNroIncisos() throws ApplicationException {
		 HashMap map = new HashMap();
         map.put("pi_tabla", TABLA_FORMA_CALCULO_FOLIO);
         return getAllBackBoneInvoke(map,"OBTENER_COMBO_NUMERO_INCISO");

	}
    @SuppressWarnings("unchecked")
	public List comboIndicadorNumeracionNroIncisos()throws ApplicationException {
		 HashMap map = new HashMap();
         map.put("pi_tabla", TABLA_INDICADOR_NUMERACION);
         return getAllBackBoneInvoke(map,"OBTENER_COMBO_NUMERO_INCISO");

	}
    @SuppressWarnings("unchecked")
	public List comboIndicador_SP_NroIncisos() throws ApplicationException {
		 HashMap map = new HashMap();
         map.put("pi_tabla", TABLA_INDICADOR_SP);
         return getAllBackBoneInvoke(map,"OBTENER_COMBO_NUMERO_INCISO");

	}

	public List comboNacionalidad () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTENER_COMBO_NACIONALIDAD");
	}
	@SuppressWarnings("unchecked")
	public List comboEstadoCivil () throws ApplicationException {
		 HashMap map = new HashMap();
	     map.put("pi_tabla", TABLA_ESTADO_CIVIL);
	     return getAllBackBoneInvoke(map,"OBTENER_ESTADO_CIVIL");
	}
	public List comboTipoEmpresa () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPO_EMPRESA");
	}

	public List comboTipoIdentificador () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPO_IDENTIFICADOR");
	}

	public List comboTipoDomicilio () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPOS_DOMICILIO");
	}
	public List comboPaises () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_PAISES");
	}
	@SuppressWarnings("unchecked")
	public List comboColonias(String codigoPais, String codigoEstado,
			String codigoMunicipio) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoPais", codigoPais);
		map.put("codigoEstado", codigoEstado);
		map.put("codigoMunicipio", codigoMunicipio);

		return getAllBackBoneInvoke(map, "OBTIENE_COLONIAS");
	}
	@SuppressWarnings("unchecked")
	public List comboEstados(String codigoPais) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoPais", codigoPais);

		return getAllBackBoneInvoke(map, "OBTIENE_ESTADOS");
	}
	@SuppressWarnings("unchecked")
	public List comboMunicipios(String codigoPais, String codigoEstado)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoPais", codigoPais);
		map.put("codigoEstado", codigoEstado);

		return getAllBackBoneInvoke(map, "OBTIENE_MUNICIPIOS");
	}
	@SuppressWarnings("unchecked")
	public List comboTiposTelefono  () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPOS_TELEFONO");
	}
	@SuppressWarnings("unchecked")
	public List comboGrupoCorporativo (String cdElemento) throws ApplicationException {
		HashMap map = new HashMap ();
		/*map.put("pi_tabla", TABLA_GRUPO_CORPORATIVO);
		map.put("pi_atrib_desc", "DSGRUPO");*/
		map.put("cdElemento", cdElemento);
		return getAllBackBoneInvoke(map, "OBTIENE_GRUPO_CORPORATIVO");
	}

	public List comboEstadosCorporativos () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_ESTADOS_CORPORATIVO");
	}

	public List comboGrupoNivelCorporativo() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_NIVEL_CORPORATIVO");
	}
	public List comboSexo () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_SEXO");
		
	}
	
	public List comboMetodoCancelacion() throws ApplicationException {
		return getAllBackBoneInvoke(null, "METODO_CANCELACION");
	}

	public List comboRazonCancelacion() throws ApplicationException {
		return getAllBackBoneInvoke(null, "RAZON_CANCELACION");
	}
	

	public List comboPeriodosGracia() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_PERIODOS_GRACIA");
	}
	@SuppressWarnings("unchecked")

	public List comboTiposDocumento(String cdElemento, String cdUniEco, String cdRamo) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("cdElemento", cdElemento);
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		return getAllBackBoneInvoke(map, "OBTIENE_TIPOS_DOCUMENTO");
	} 
	
	public List comboSeccionesOrden() throws ApplicationException {
		return getAllBackBoneInvoke(null, "SECCIONES_ORDEN");
	}

	public List comboTiposObjetos() throws ApplicationException {
		// TODO Auto-generated method stub
		return getAllBackBoneInvoke(null, "TIPO_OBJETO");
	}

	@SuppressWarnings("unchecked")

	public List comboSeccionFormato(String cdFormato) throws ApplicationException {
			HashMap map = new HashMap ();
			map.put("cdFormato", cdFormato);
			return getAllBackBoneInvoke(map, "OBTIENE_SECCION_FORMATO");
		}

		
	@SuppressWarnings("unchecked")
	public List comboBloquesConfiguraAtributosFormatoOrdenTrabajo()
			throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_BLOQUES_CONF_ATRIBUTO_FORMATO_ORDEN_TRABAJO");
	}


	@SuppressWarnings("unchecked")
	public List comboCampoBloques(String otTipo, String cdBloque)throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("otTipo", otTipo);
		map.put("cdBloque", cdBloque);
		return getAllBackBoneInvoke(map, "OBTIENE_CAMPO_BLOQUE");
	}

	public List comboFormatosCampo() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_FORMATO_CAMPO");
	} 
	
	public List comboTipoError () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPO_ERROR");
	}

	//************* Asociar formato de orden de trabajo *******************
	public List comboFormaCalculoFolio() throws ApplicationException {
		return getAllBackBoneInvoke(null, "FORMAS_CALCULO_FOLIO");
	}

	public List comboFormato() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_FORMATOS_ORDEN");
	}

	public List comboProceso() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_PROCESOS");
	}
	@SuppressWarnings("unchecked")
	public List comboClientesCorpPorUsuario (String cdUsuario) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUsuario", cdUsuario);
		return getAllBackBoneInvoke(map, "OBTIENE_CLIENTES_CORP_POR_USUARIO");
	}
	@SuppressWarnings("unchecked")
	public List comboProductosTipoRamoCliente(String cdElemento, String cdTramo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdElemento", cdElemento);
		map.put("cdTramo", cdTramo);
		return getAllBackBoneInvoke(map, "OBTIENE_PRODUCTOS_TRAMO_CLIENTE");
	}
	@SuppressWarnings("unchecked")
	public List comboTipoSituacionProducto(String cdRamo)throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdramo_i", cdRamo);
		return getAllBackBoneInvoke(map, "OBTIENE_TIPO_SITUACION_PRODUCTO");		
	}
	
	public List comboBancos() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_BANCOS");
	}

	public List comboInstrumentosPago() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_INSTRUMENTOS_PAGO");
	}

	public List comboTiposTarjeta() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPOS_TARJETA");
	}
	@SuppressWarnings("unchecked")
	public List comboAseguradoraPorClienteYProducto (String cdElemento, String cdRamo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdElemento", cdElemento);
		map.put("cdRamo", cdRamo);
		return getAllBackBoneInvoke(map, "LISTA_ASEGURADORAS_POR_CLIENTE_Y_PRODUCTO");
	}
	@SuppressWarnings("unchecked")
	public List comboIndicadorSINO () throws ApplicationException{
		 HashMap map = new HashMap();
		map.put("pi_tabla", "TABLA_INDICADOR_SINO");
		return getAllBackBoneInvoke(map,"OBTIENE_ESTADOS_EJECUTIVO");
	}
	@SuppressWarnings("unchecked")
	public List comboTipoSituacionPorProductoYPlan (String cdRamo, String cdPlan) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdRamo", cdRamo);
		map.put("cdPlan", cdPlan);

		return getAllBackBoneInvoke(map, "OBTIENE_SITUACION_POR_PRODUCTO_Y_PLAN");
	}
	@SuppressWarnings("unchecked")
	public List comboTipoGarantiaPorProductoYSituacion(String cdRamo, String cdTipSit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdRamo", cdRamo);
		map.put("cdTipSit", cdTipSit);

		return getAllBackBoneInvoke(map, "OBTIENE_GARANTIA_POR_PRODUCTO_Y_SITUACION");
	}
	@SuppressWarnings("unchecked")
	public List comboPlanesProducto(String cdRamo) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("po_producto", cdRamo);

		return getAllBackBoneInvoke(map, "OBTIENE_PLANES_PRODUCTO");
		//TULY - CODIGO HARCODEADO PAR LA PANTALLA DE ALTA DE CASO
		/*ArrayList list = new ArrayList();
		
		ElementoComboBoxVO vo = new ElementoComboBoxVO(); 
		vo.setCodigo("1");
		vo.setDescripcion("LIMITADO");
		
		list.add(vo);
		return list;*/
	}
	
	public List comboPersonas() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_PERSONAS");
	}
	@SuppressWarnings("unchecked")
	public List comboGenerico(String idTablaLogica) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("TablaLogica", idTablaLogica);
		return getAllBackBoneInvoke(map, "COMBO_DATOS_GENERICOS");
	}
	@SuppressWarnings("unchecked")
	public List comboFormaCalculo() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_INDICADOR_FORMA_CALCULO);
		return getAllBackBoneInvoke(map,"OBTIENE_ESTADOS_EJECUTIVO");
	}
	@SuppressWarnings("unchecked")
	public List comboFormaAtributo() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_INDICADOR_FORMA_ATRIBUTO);
		return getAllBackBoneInvoke(map,"OBTIENE_ESTADOS_EJECUTIVO");
	}
	@SuppressWarnings("unchecked")
	public List comboNroPolizas(String cdUniEco, String cdRamo, String cdPlan, String cdElemento) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_cdplan_i", cdPlan);
		map.put("pv_cdelemento_i", cdElemento);
		return getAllBackBoneInvoke(map,"OBTIENE_NRO_POLIZAS");
	}
	
	public List comboTiposRenovacion() throws ApplicationException{
		return getAllBackBoneInvoke(null, "OBTIENE_TIPOS_RENOVA");
	}
	
	public List comboAccionRenovacionRol() throws ApplicationException{
		return getAllBackBoneInvoke(null, "OBTIENE_ACCION_RENOVACION_ROL");
	}

	public List comboAccionRenovacionPantalla() throws ApplicationException{
		return getAllBackBoneInvoke(null, "OBTIENE_ACCION_RENOVACION_PANTALLA");
	}
	@SuppressWarnings("unchecked")
	public List comboAccionRenovacionCampo(String cdTitulo) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdTitulo", cdTitulo);
		return getAllBackBoneInvoke(map, "OBTIENE_ACCION_RENOVACION_CAMPO");
	}

	public List comboAccionRenovacionAccion() throws ApplicationException{
		return getAllBackBoneInvoke(null, "OBTIENE_ACCION_RENOVACION_ACCION");
	}
	@SuppressWarnings("unchecked")
	public List comboAccionRenovacionRoles(String cdRenova) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdRenova", cdRenova);
		return getAllBackBoneInvoke(map, "OBTIENE_ACCION_RENOVACION_ROLES");
		
	}
	
	@SuppressWarnings("unchecked")
	public List obtenerRamosPorCliente(String cdElemento) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdelement_i", cdElemento);
		return getAllBackBoneInvoke(map, "OBTIENE_RAMOS_CLIENTE");
	}

	@SuppressWarnings("unchecked")
	public List comboTipoObjeto() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_TIPO_OBJETO);
		return getAllBackBoneInvoke(map,"OBTIENE_TIPO_OBJETO");
	}

	@SuppressWarnings("unchecked")
	public List obtenerFormasPago(String otClave1) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("p_cliente", otClave1);
		return getAllBackBoneInvoke(map, "PERXCTE");
	}

	@SuppressWarnings("unchecked")
	public List obtenerInstrumentosPago() throws ApplicationException {
		return getAllBackBoneInvoke(null, "INST_PAGO");
		//TULY - CODIGO HARCODEADO PAR LA PANTALLA DE ALTA DE CASO
		/*InstrumentoPagoVO vo = null;
		ArrayList list = new ArrayList();
		
		vo = new InstrumentoPagoVO();
		vo.setCdForPag("1");	
		vo.setDsForPag("Mensual");
		list.add(vo);
		vo = new InstrumentoPagoVO();
		vo.setCdForPag("2");
		vo.setDsForPag("Bimensual");
		list.add(vo);
		vo = new InstrumentoPagoVO();
		vo.setCdForPag("3");
		vo.setDsForPag("Trimestral");
		list.add(vo);
		vo = new InstrumentoPagoVO();
		vo.setCdForPag("4");
		vo.setDsForPag("Semestral");
		list.add(vo);
		vo = new InstrumentoPagoVO();
		vo.setCdForPag("5");
		vo.setDsForPag("Anual");
		list.add(vo);
		return list;*/
	}

	@SuppressWarnings("unchecked")
	public List obtenerRoles() throws ApplicationException {
		return getAllBackBoneInvoke(null, "ROL");
	}

	@SuppressWarnings("unchecked")
	public List obtenerPersonas(String sesionUsuario)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_usuario_i", sesionUsuario);
		return getAllBackBoneInvoke(map, "OBTIENE_PERSONA");
	}

	@SuppressWarnings("unchecked")
	public List obtenerIncisos(String cdUniEco, String cdRamo, String estado,
			String nmPoliza, String nmSituac) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("p_cliente", estado);
		map.put("p_cliente", nmPoliza);
		map.put("p_cliente", nmSituac);
		
		return getAllBackBoneInvoke(map, "OBTIENE_MPOLISIT");
	}

	@SuppressWarnings("unchecked")
	public List obtenerCoberturasProducto(String cdUniEco, String cdRamo,
			String nmPoliza) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("p_cliente", nmPoliza);
		
		return getAllBackBoneInvoke(map, "COBERTURAS_PRODUCTO");
	}

	@SuppressWarnings("unchecked")
	public List obtenerObjetosRamos(String cdRamo, String cdTipSit)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_cdtipsit_i", cdTipSit);
		
		return getAllBackBoneInvoke(map, "OBJETO_RAMO");
	}

	public List comboTipoGuion() throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_TIPO_GUION);
		return getAllBackBoneInvoke(map,"OBTIENE_TIPO_GUION");
		
	}
	
	@SuppressWarnings("unchecked")
	public List comboNotificacionRegion(String cdNotificacion) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdNotificacion", cdNotificacion);
		return getAllBackBoneInvoke(map, "OBTIENE_NOTIFICACION_REGION");
	}
	
	@SuppressWarnings("unchecked")
	public List comboTipoMetodoEnvio() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_TIPO_METODO_ENVIO);
		return getAllBackBoneInvoke(map,"OBTIENE_TIPO_METODO_ENVIO");	

	}
	public List comboProcesosCat() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_PROCESOS_CAT");
	}
	
	public List comboProcesosCatMatriz() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_PROCESOS_CAT_MATRIZ");
	}
	
	@SuppressWarnings("unchecked")
	public List comboClientesCorpBO() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CLIENTES_CORP_BO");
	}
	
	@SuppressWarnings("unchecked")
	public List obtenerDatosCatalogo(String pv_cdtabla_i) throws ApplicationException{
		HashMap map = new HashMap();
		//map.put("pv_cdtabla_i", "TACTIVO");// pv_cdtabla_i);
		map.put("pv_cdtabla_i", pv_cdtabla_i);
		return getAllBackBoneInvoke(map,"OBTENER_DATOS_CATALOGO_BO");	

	}
	
	@SuppressWarnings("unchecked")
	public List datosCatalogo() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", TABLA_DATOS_CATALOGO);
		return getAllBackBoneInvoke(map,"DATOS_CATALOGO");	

	}
	
	@SuppressWarnings("unchecked")
	public List catalogoPrioridad() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", TABLA_CATALOGO_PRIORIDAD);
		return getAllBackBoneInvoke(map,"CATALOGO_PRIORIDAD");

	}
	
	@SuppressWarnings("unchecked")
	public List obtieneNivelAtencion() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", TABLA_OBTIENE_NIVEL_ATENCION);
		return getAllBackBoneInvoke(map,"OBTIENE_NIVEL_ATENCION");	

	}
	
	@SuppressWarnings("unchecked")
	public List obtieneUnidadTiempo() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i",TABLA_OBTIENE_UNIDAD_TIEMPO);
		return getAllBackBoneInvoke(map,"OBTIENE_UNIDAD_TIEMPO");	
	}
		
	@SuppressWarnings("unchecked")
	public List obtieneRol() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", TABLA_OBTIENE_ROL);
		return getAllBackBoneInvoke(map,"OBTIENE_ROL");	
	}
	
	
	@SuppressWarnings("unchecked")
	public List obtieneTiposArchivos() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPOS_ARCHIVOS");
	}

	@SuppressWarnings("unchecked")
	public List obtieneDatosCatalogo(String pv_cdtabla_i) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", pv_cdtabla_i);
		return getAllBackBoneInvoke(map,"OBTIENE_DATOS_CATALOGO");	
	}


	@SuppressWarnings("unchecked")
		public List matrizObtieneRol() throws ApplicationException{
			HashMap map = new HashMap();
			map.put("pv_cdtabla_i",TABLA_OBTIENE_ROL );
			return getAllBackBoneInvoke(map,"MATRIZ_OBTIENE_ROL");	
		}
	
	@SuppressWarnings("unchecked")
	public List matrizNivelAtencion() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", TABLA_OBTIENE_NIVEL_ATENCION);
		return getAllBackBoneInvoke(map,"MATRIZ_NIVEL_ATENCION");	
	}


	@SuppressWarnings("unchecked")
	public List matrizUnidadTiempo() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", TABLA_OBTIENE_UNIDAD_TIEMPO);
		return getAllBackBoneInvoke(map,"MATRIZ_UNIDAD_TIEMPO");	
	}
		

	@SuppressWarnings("unchecked")
	public List comboIndicadorAviso() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_INDICADOR_AVISO);
		return getAllBackBoneInvoke(map,"OBTIENE_INDICADOR_AVISO");	
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List obtenerAseguradorasProdCorp( String cdProceso, String cdElemento, String cdRamo)throws ApplicationException{
		
		HashMap map = new HashMap();
		map.put("pv_cdelemento_i", cdElemento);
		map.put("pv_cdproceso_i", cdProceso);
		map.put("pv_cdramo_i", cdRamo);
		return getAllBackBoneInvoke(map,"OBTIENE_ASEGURADORAS_PRODUCT_CORP");	
		
	}
		
	@SuppressWarnings("unchecked")
	public List obtenerTareas(String cdElemento, String cdUniEco, String cdRamo)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdelemento_i", cdElemento);
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		return getAllBackBoneInvoke(map,"OBTIENE_CDMATRIZ");	
		
		
		//TULY - CODIGO HARCODEADO PAR LA PANTALLA DE ALTA DE CASO
		/*ArrayList lista = new ArrayList();
		TareasAltaCasoVO vo = null;
		
		vo = new TareasAltaCasoVO();
		//vo.setDescripcion("Compra/Emisi&oacute;n - OFICINA MATRIZ - AUTOS");
		vo.setDescripcion("Compra/Emision - OFICINA MATRIZ - AUTOS");
		vo.setCdmatriz("16");
		vo.setCdformatoorden("11");
		vo.setCdunieco("1");		
		vo.setCdramo("2");
		vo.setCdproceso("11");		
		vo.setDsramo("AUTOS");
		vo.setDsunieco("OFICINA MATRIZ");
		
		
		lista.add(vo);
		
		vo = new TareasAltaCasoVO();
		//vo.setDescripcion("Cotizaci&oacute;n - OFICINA MATRIZ - AUTOS");
		vo.setDescripcion("Cotizacion - OFICINA MATRIZ - AUTOS");
		vo.setCdmatriz("14");
		vo.setCdformatoorden("10");
		vo.setCdunieco("1");		
		vo.setCdramo("2");
		vo.setCdproceso("10");
		vo.setDsramo("AUTOS");
		vo.setDsunieco("OFICINA MATRIZ");
		
		lista.add(vo);
		
		return lista;*/
		
	}

	@SuppressWarnings("unchecked")
	public List obtenerPrioridades() throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", TABLA_CATALOGO_PRIORIDAD);
		return getAllBackBoneInvoke(map,"MATRIZ_OBTIENE_PRIORIDADES");
		
		//TULY - CODIGO HARCODEADO PAR LA PANTALLA DE ALTA DE CASO
		
		/*ArrayList list = new ArrayList();
		
		ElementoComboBoxVO vo = null;
		
		vo = new ElementoComboBoxVO();
		vo.setCodigo("1");
		vo.setDescripcion("Baja");
		list.add(vo);
		vo = new ElementoComboBoxVO();
		vo.setCodigo("2");
		vo.setDescripcion("Normal");
		list.add(vo);
		vo = new ElementoComboBoxVO();
		vo.setCodigo("3");
		vo.setDescripcion("Alta");
		list.add(vo);
		
		return list;*/
	}

	@SuppressWarnings("unchecked")
	public List matrizResponsables(String cdproceso) throws ApplicationException {
		HashMap map = new HashMap();
		//map.put("pi_tabla", TABLA_MATRIZ_RESPONSABLES);
		map.put("pv_cdproceso_i", cdproceso);
		return getAllBackBoneInvoke(map,"MATRIZ_OBTIENE_RESPONSABLES");	
	}
	@SuppressWarnings("unchecked")
	public List obtenerModulos() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_MODULOS");
	}
	

	@SuppressWarnings("unchecked")
	public List obtenerDatosCatalogoDep(String cdTabla, String valor1,
			String valor2) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", cdTabla);
		map.put("pv_valanter_i", valor1);
		map.put("pv_valantant_i", valor2);
		return getAllBackBoneInvoke(map,"OBTIENE_DATOS_CATALOGO_CON_DEPENDIENTES");
		
		//TULY - CODIGO HARCODEADO PAR LA PANTALLA DE ALTA DE CASO
		/*ArrayList list = new ArrayList();
		
		ItemVO vo = null;
		
		if(cdTabla.equals("2MARCAS"))
		{	
			vo = new ItemVO();
			vo.setId("1");
			vo.setTexto("FORD");
			
			list.add(vo);
			vo = new ItemVO();
			vo.setId("2");
			vo.setTexto("NISSAN");
			list.add(vo);
			vo = new ItemVO();
			vo.setId("3");
			vo.setTexto("CHEVROLET");
			list.add(vo);
			vo = new ItemVO();
			vo.setId("4");
			vo.setTexto("TOYOTA");
			list.add(vo);
			
		}
		if(cdTabla.equals("2MODELOS"))
		{	vo = new ItemVO();
			vo.setId("1");
			vo.setTexto("Modelo 1");
			list.add(vo);
			vo = new ItemVO();
			vo.setId("2");
			vo.setTexto("Modelo 2");
			list.add(vo);
			vo = new ItemVO();
			vo.setId("3");
			vo.setTexto("Modelo 3");
			list.add(vo);
			
		}
		if(cdTabla.equals("2SERVICI"))
		{	vo = new ItemVO();
			vo.setId("1");
			vo.setTexto("SI");
			list.add(vo);
			vo = new ItemVO();
			vo.setId("2");
			vo.setTexto("NO");
			list.add(vo);
			
		}
		if(cdTabla.equals("2USOS"))
		{	vo = new ItemVO();
			vo.setId("1");
			vo.setTexto("Particular");
			list.add(vo);
			vo = new ItemVO();
			vo.setId("2");
			vo.setTexto("Comercial");
			list.add(vo);
			
		}
		if(cdTabla.equals("2TIPOS"))
		{	vo = new ItemVO();
			vo.setId("1");
			vo.setTexto("Sedan");
			list.add(vo);
			vo = new ItemVO();
			vo.setId("2");
			vo.setTexto("Cabina");
			list.add(vo);
			
		}
		return list;*/
	}
	
	@SuppressWarnings("unchecked")
	public List obtenerTareaPrioridad() throws ApplicationException{
			HashMap map = new HashMap();
			map.put("pi_tabla", TABLA_TAREAS_PRIORIDAD);
			return getAllBackBoneInvoke(map,"OBTIENE_TAREA_PRIORIDAD");	
	}
	
	
	@SuppressWarnings("unchecked")
	public List obtenerTareaEstatus() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_TAREAS_ESTATUS);
		return getAllBackBoneInvoke(map,"OBTIENE_TAREA_ESTATUS");	
		
	}

	@SuppressWarnings("unchecked")
	public List obtenerMonedas() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_MONEDAS");
	}	
	
	
	@SuppressWarnings("unchecked")
	public List comboNivelAtencionMatriz(String cdMatriz)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdMatriz", cdMatriz);
		return getAllBackBoneInvoke(map,"OBTIENE_NIVEL_ATENCION_MATRIZ");	
		
	}
	
	
	public List obtieneDatosModulo(String pv_cdtabla_i) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", pv_cdtabla_i);
		return getAllBackBoneInvoke(map,"OBTIENE_DATOS_MODULOS");	
	}

	public List obtenerStatusCaso() throws ApplicationException {
		return getAllBackBoneInvoke(null, "ESTATUS_CASO");
	}

	@SuppressWarnings("unchecked")
	public List obtenerUsuariosReemplazantes(String cdmatriz, String cdnivatn) throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdmatriz_i",cdmatriz);
		map.put("pv_cdnivatn_i", cdnivatn);

		String endpointName = "OBTENER_REEMPLAZANTES";
		return  getAllBackBoneInvoke(map, endpointName);
	}
	
	@SuppressWarnings("unchecked")
	public List obtenerRolesUsuarios() throws ApplicationException {	
		return  getAllBackBoneInvoke(null, "OBTIENE_ROL_SEG");
	}

	@SuppressWarnings("unchecked")
	public List obtenerUsuarios(String pv_cdsisrol_i) throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdsisrol_i",pv_cdsisrol_i);

		String endpointName = "OBTENER_USUARIOS";
		return  getAllBackBoneInvoke(map, endpointName);
	}
	
	public List comboEncuestas() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_COMBO_ENCUESTAS");
	}

	@SuppressWarnings("unchecked")
	public List comboModuloConfigEncuestas() throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_ENCUESTAS_COMBO_MODULO);
		return getAllBackBoneInvoke(map,"OBTIENE_MODULOS_ENCUESTAS");	
	}

	@SuppressWarnings("unchecked")
	public List comboUsuariosEncuestas(String cdModulo)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdModulo",cdModulo);
		return getAllBackBoneInvoke(map,"OBTIENE_USUARIOS_ENCUESTAS");	
	}
	
	@SuppressWarnings("unchecked")
	public List comboObtenerProductosAseguradoraEncuesta (String pv_cdunieco_i) throws ApplicationException {
    	HashMap map = new HashMap ();
    	map.put("pv_cdunieco_i", pv_cdunieco_i);
    	
    	return getAllBackBoneInvoke(map, "OBTENER_PRODUCTOS_ASEGURADORA_ENCUESTA");
    }

	@SuppressWarnings("unchecked")
	public List comboCampanEncuestas()	throws ApplicationException {
			HashMap map = new HashMap();
			map.put("pi_tabla", TABLA_ENCUESTAS_COMBO_CAMPANIAS);
			return getAllBackBoneInvoke(map,"OBTIENE_COMBO_ENCUESTAS_CAMPAN");	
   }

	@SuppressWarnings("unchecked")
	public List comboClientesCorpoAseguradoraProducto(String cdUniEco,String cdRamo) throws ApplicationException {
        HashMap map = new HashMap();
		
		map.put("cdUniEco",cdUniEco);
		map.put("cdRamo",cdRamo);
		
		return  getAllBackBoneInvoke(map, "COMBO_CLICORPO_ASEGURADORA_PRODUCTO");
	}

	@SuppressWarnings("unchecked")
	public List obtienePais(String pv_cdtabla_i) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", pv_cdtabla_i);
		return getAllBackBoneInvoke(map,"OBTIENE_PAIS");	
	}

	@SuppressWarnings("unchecked")
	public List obtieneMes(String pv_cdtabla_i) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", pv_cdtabla_i);
		return getAllBackBoneInvoke(map,"OBTIENE_MES");	
	}

	public List getListas() throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", TABLA_GET_LISTAS);
		map.put("pv_valanter_i",0);
		map.put("pv_valantant_i",0);
		
		return getAllBackBoneInvoke(map,"OBTENER_TABLA_GET_LISTAS");	
		
	}
	
	public List getListas(String cdTabla, String valAnter, String valAntAnt) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", cdTabla);
		map.put("pv_valanter_i", valAnter);
		map.put("pv_valantant_i", valAntAnt);
		
		return getAllBackBoneInvoke(map,"OBTENER_TABLA_GET_LISTAS");	
		
	}

	
	
	/* @SuppressWarnings("unchecked")
		public List comboUsuarioResponsable (String pv_nmconfig_i) throws ApplicationException{
	        HashMap map = new HashMap();

	        map.put("pv_nmconfig_i",pv_nmconfig_i);
	        
	        String endpointName = "COMBO_USUARIO_RESPONSABLE";
	        return  getAllBackBoneInvoke(map, endpointName);

	    }*/
	
  /* @SuppressWarnings("unchecked")
	public List comboUsuarioResponsable()	throws ApplicationException {
			HashMap map = new HashMap();
			map.put("pv_nmconfig_i", TABLA_MATRIZ_RESPONSABLES);
			return getAllBackBoneInvoke(map,"COMBO_USUARIO_RESPONSABLE");	
   }*/

	@SuppressWarnings("unchecked")
	public List comboModuloEnc(String cdUniEco, String cdRamo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		return getAllBackBoneInvoke(map, "OBTIENE_COMBO_MODULOS_ENCUESTAS");
	}
	
	@SuppressWarnings("unchecked")
	public List comboCampanhaEnc(String cdUniEco, String cdRamo, String cdModulo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdModulo", cdModulo);
		return getAllBackBoneInvoke(map, "OBTIENE_COMBO_CAMPANHA_ENCUESTAS");
	}	

	@SuppressWarnings("unchecked")
	public List comboTemaEnc(String cdUniEco, String cdRamo, String cdModulo, String cdCampan) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdModulo", cdModulo);
		map.put("cdCampan", cdCampan);
		return getAllBackBoneInvoke(map, "OBTIENE_COMBO_TEMA_ENCUESTAS");
	}	
	
	@SuppressWarnings("unchecked")
	public List comboEncuestaEnc(String cdUniEco, String cdRamo, String cdModulo, String cdCampan, String cdProceso, String nmConfig) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdModulo", cdModulo);
		map.put("cdCampan", cdCampan);
		map.put("cdProceso", cdProceso);
		map.put("nmConfig", nmConfig);
		
		return getAllBackBoneInvoke(map, "OBTIENE_COMBO_ENCUESTA_ENCUESTAS");
	}	

	
}