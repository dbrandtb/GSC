package mx.com.aon.portal.service;

import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

import java.util.List;

/**
 * Interface de servicios para estructura.
 *
 */
public interface UsuarioManager {

    public List<RamaVO> getClientesRoles(String user)throws ApplicationException;

    public List<UserVO> getAttributesUser(String user) throws ApplicationException;

    public boolean isAuthorizedExport(String user, String cdSisrol, String cdElemento)	throws ApplicationException;

}
