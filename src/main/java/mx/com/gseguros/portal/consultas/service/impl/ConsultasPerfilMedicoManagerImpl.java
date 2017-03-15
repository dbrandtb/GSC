package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.PerfilAseguradoVO;
import mx.com.gseguros.portal.consultas.service.ConsultasPerfilMedicoManager;
import mx.com.gseguros.portal.general.model.PolizaVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.dao.ConsultasPerfilMedicoDAO;

@Service
public class ConsultasPerfilMedicoManagerImpl implements ConsultasPerfilMedicoManager{
	
	static final Logger logger = LoggerFactory.getLogger(ConsultasPerfilMedicoManagerImpl.class);
		
	@Autowired
	@Qualifier("consultasPerfilMedicoDAOICEImpl")
	private ConsultasPerfilMedicoDAO consultasICEDAO;
	
	@Autowired
	@Qualifier("consultasPerfilMedicoDAOSISAImpl")
	private ConsultasPerfilMedicoDAO consultasSISADAO;
		
	@Value("${consultar.perfil.medico.sisa}")
    private boolean consultarPerfilMedicoSisa;
	
	
	//Consulta en SISA el perfil medico de la lista de asegurados dada 
	private List<Map<String, String>> consultaPerfilAseguradosSISA(Map<String,String> params) throws Exception{
		
		List<Map<String, String>> listaPerfil = new ArrayList<Map<String,String>>();
		String paso = "Realizando consulta en SISA";
		logger.debug(paso);
		
		try {
			List<Map<String, String>> listaICE = new ArrayList<Map<String,String>>();
			listaICE = consultasSISADAO.consultaPerfilAsegurados(params);
		}
		catch (Exception e){
			throw new ApplicationException("Ha ocurrido un error al consultar el perfil del asegurado en SICAPS",e);
		}
		return listaPerfil;
	}
	
	
	//Consulta en SICAPS el perfil medico de la lista de asegurados dada 
	private List<Map<String, String>> consultaPerfilAseguradosICE(Map<String,String> params) throws Exception{
		
		List<Map<String, String>> listaICE = new ArrayList<Map<String,String>>();
		String paso = "Realizando consulta en SICAPS";
		logger.debug(paso);
		
		try {
			listaICE = consultasICEDAO.consultaPerfilAsegurados(params);
		}
		catch (Exception e){
			throw new ApplicationException("Ha ocurrido un error al consultar el perfil del asegurado en SICAPS",e);
		}
		return listaICE;
	}

	
	//Consulta el perfil medico de los asegurados en ambas plataformas
	@Override
	public List<Map<String, String>> consultaPerfilAsegurados(Map<String, String> params) throws Exception{
		
		List<Map<String, String>> listaPerfil = new ArrayList<Map<String,String>>();
		String paso;
		
		try{
			if (consultarPerfilMedicoSisa){//se incorporara propiedad en el global para definir si debe ir a SISA consultar.perfil.medico.sisa = true o false
				try{
					paso = "Estableciendo comunicacion con SISA";
					logger.debug(paso);
					listaPerfil = consultaPerfilAseguradosSISA(params);
				}
				catch (Exception ex){ //en caso de que el llamado a SISA arroje un error, se debe llamar al SP de ICE
					paso = "Error haciendo el llamado al SP de SISA, se hara el llamado a SICAPS";
					logger.debug(paso);
					//llamar a la consulta de ICE
					listaPerfil=consultaPerfilAseguradosICE(params);
				}
			}
			else {
				paso = "La consulta de SISA se encuentra apagada, se consultara en SICAPS";
				logger.debug(paso);
				//llamar a la consulta de ICE
				listaPerfil=consultaPerfilAseguradosICE(params);
			}
		}
		catch (Exception e){
			throw new ApplicationException("Ha ocurrido un error al consultar el perfil de los asegurados",e);
		}
		return listaPerfil;
	};
	
	
	//consulta la lista de ICDs de un asegurado dado
	@Override
	public PerfilAseguradoVO consultaICDSAsegurado(Map<String,String> params) throws Exception{
		PerfilAseguradoVO listaIcds = new PerfilAseguradoVO();
		String paso;
		try{
			paso = "Consultando lista de ICDs del asegurado";
			logger.debug(paso);
			List<Map<String, String>> lista;
			lista = (List<Map<String, String>>) consultasICEDAO.consultaICDSAsegurado(params);
			//se recibe la lista y se arma el objeto VO con el resultado de ICE
			listaIcds.setIcds(lista);
		}
		catch (Exception e){
			throw new ApplicationException("Ha ocurrido un error al consultar los ICDs del asegurado",e);
		}
		return listaIcds;
	};
	
	
}