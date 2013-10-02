/*
 * AON
 * 
 * Creado el 22/02/2008 06:56:45 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.portal.web;

import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.model.PersonasVO;
import mx.com.aon.portal.service.ConfigurarEstructuraManager;
import mx.com.aon.portal.service.EstructuraManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


/**
 * Action que atiende las peticiones de abm de la pantalla de Configurar Estructura.
 * 
 * @extends ActionSupport
 *
 */
@SuppressWarnings("serial")
public class ConfigurarEstructuraAction extends ActionSupport {

	private static Logger logger = Logger.getLogger(ConfigurarEstructuraAction.class);

	private transient ConfigurarEstructuraManager configurarEstructuraManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	@SuppressWarnings("unchecked")
	private List mEstructuraList;
	private String codigoElemento;
	private String codigoEstructura;
	private String nombre;
	private String vinculoPadre;
	private String tipoNivel;
	private String posicion;
	private String codigoPersona;
    private String nomina;
    private String codigoCliente;
	private boolean success;


	/**
	 *  Metodo que genera la copia de un registro seleccionado en el grid de la pantalla.
	 *  
	 * @return success
	 *  
	 * @throws Exception
	 */
	public String cmdCopiarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = configurarEstructuraManager.copiarConfigurarEstructura(codigoEstructura, codigoElemento);		
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }

	}
	
	/**
	 * Metodo que elimina un registro seleccionado en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = obtenConfigurarEstructuraManager().borrarConfigurarEstructura(getCodigoEstructura(),getCodigoElemento());	
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }

	}


    /**
     * Metodo que inserta un nuevo registro de elemento de estructura.
     * 
     * @return success
     * 
     * @throws Exception
     *
     */
    public String cmdAgregarClick()throws Exception
    {
		String messageResult = "";
        try
        {
            ConfigurarEstructuraVO configurarEstructuraVO = new ConfigurarEstructuraVO();
            configurarEstructuraVO.setCodigoElemento(codigoElemento);
            configurarEstructuraVO.setCodigoEstructura(codigoEstructura);
            configurarEstructuraVO.setCodigoPersona(codigoPersona);
            configurarEstructuraVO.setTipoNivel(tipoNivel);
            configurarEstructuraVO.setNombre(nombre);
            configurarEstructuraVO.setPosicion(posicion);
            configurarEstructuraVO.setVinculoPadre(vinculoPadre);
            String  _nomina  = (nomina!=null && !nomina.equals("") && nomina.equals("on"))?"1":"0";
            configurarEstructuraVO.setNomina(_nomina);

            messageResult = configurarEstructuraManager.agregarConfigurarEstructura(configurarEstructuraVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }


	/**
	 * Metodo que realiza la actualizacion de un registro de datos editato en pantalla.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
        try
        {
            ConfigurarEstructuraVO configurarEstructuraVO = new ConfigurarEstructuraVO();
            configurarEstructuraVO.setCodigoEstructura(codigoEstructura);
            configurarEstructuraVO.setCodigoElemento(codigoElemento);
            configurarEstructuraVO.setCodigoPersona(codigoPersona);
            configurarEstructuraVO.setVinculoPadre(vinculoPadre);
            configurarEstructuraVO.setTipoNivel(tipoNivel);
            configurarEstructuraVO.setNombre(nombre);
            configurarEstructuraVO.setPosicion(posicion);

            String  _nomina  = (nomina!=null && !nomina.equals("") && nomina.equals("on"))?"1":"0";
            configurarEstructuraVO.setNomina(_nomina);

            messageResult = configurarEstructuraManager.guardarConfigurarEstructura(configurarEstructuraVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	/**
	 * Metodo que obtiene un conjunto de registros con la informacion de clientes.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public ArrayList<PersonasVO> obtenerClientes()throws Exception
	{
		ArrayList<PersonasVO> clientesList = new ArrayList<PersonasVO>();
		try
		{
			configurarEstructuraManager.obtenerClientes();
		}catch(Exception e)
		{
			success = false;
			logger.error(e.getMessage(),e);
			return clientesList;
		}
		success = true;
        return clientesList;
	}
	
	/**
	 * Metodo que obtiene un unico registro con la informacion de elementos de estructuras.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{
 		try
		{
			
			mEstructuraList=new ArrayList<ConfigurarEstructuraVO>();
			ConfigurarEstructuraVO configurarEstructuraVO=configurarEstructuraManager.getConfigurarEstructura(codigoElemento);
			mEstructuraList.add(configurarEstructuraVO);
			success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	 public String cmdRegresarEstructura(){
			return "regresarAEstructura";
	 }
	
	@SuppressWarnings("unchecked")
	public List getMEstructuraList() {return mEstructuraList;}
	
	public ConfigurarEstructuraManager obtenConfigurarEstructuraManager() {
		return configurarEstructuraManager;
	}


    public void setConfigurarEstructuraManager(ConfigurarEstructuraManager configurarEstructuraManager) {
        this.configurarEstructuraManager = configurarEstructuraManager;
    }

    public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	

	@SuppressWarnings("unchecked")
	public void setMEstructuraList(List estructuraList) {
		mEstructuraList = estructuraList;
	}

	public void setCodigoEstructura(String codigoEstructura) {
		this.codigoEstructura = codigoEstructura;
	}

	public String getCodigoElemento() {
		return codigoElemento;
	}

	public void setCodigoElemento(String codigoElemento) {
		this.codigoElemento = codigoElemento;
	}

	public String getCodigoEstructura() {
		return codigoEstructura;
	}
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getVinculoPadre() {
		return vinculoPadre;
	}

	public void setVinculoPadre(String vinculoPadre) {
		this.vinculoPadre = vinculoPadre;
	}

	public String getTipoNivel() {
		return tipoNivel;
	}

	public void setTipoNivel(String tipoNivel) {
		this.tipoNivel = tipoNivel;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public String getCodigoPersona() {
		return codigoPersona;
	}

	public void setCodigoPersona(String codigoPersona) {
		this.codigoPersona = codigoPersona;
	}


    public String getNomina() {
        return nomina;
    }

    public void setNomina(String nomina) {
        this.nomina = nomina;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

}