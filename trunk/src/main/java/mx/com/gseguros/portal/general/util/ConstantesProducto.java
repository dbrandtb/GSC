package mx.com.gseguros.portal.general.util;

import org.apache.log4j.Logger;

public class ConstantesProducto
{
	private final static Logger logger  = Logger.getLogger(ConstantesProducto.class);
	private String cdramo               = null;
	private String[] cdtipsit           = null;
	private String cdatribuParentesco   = null;
	private String cdatribuSexo         = null;
	private String cdatribuCodigoPostal = null;
	private String cdatribuFechaNacimi  = null;
	
	public static ConstantesProducto ramo(String cdramo) throws Exception
	{
		logger.info("###### ConstantesProducto por ramo "+cdramo+" ######");
		ConstantesProducto producto = new ConstantesProducto();
		if(cdramo.equals("2"))
		{
			producto.setCdramo("2");
			producto.setCdtipsit(new String[]{"SL","SN"});
			producto.setCdatribuCodigoPostal("3");
			producto.setCdatribuParentesco("16");
			producto.setCdatribuSexo("1");
			producto.setCdatribuFechaNacimi("2");
		}
		else if(cdramo.equals("4"))
		{
			producto.setCdramo("4");
			producto.setCdtipsit(new String[]{"MS"});
			producto.setCdatribuCodigoPostal("");
			producto.setCdatribuParentesco("3");
			producto.setCdatribuSexo("2");
			producto.setCdatribuFechaNacimi("1");
		}
		else
		{
			throw new Exception("No existe el ramo "+cdramo);
		}
		return producto;
	}
	
	public static ConstantesProducto situacion(String cdtipsit) throws Exception
	{
		logger.info("###### ConstantesProducto por situacion "+cdtipsit+" ######");
		ConstantesProducto producto = new ConstantesProducto();
		if(cdtipsit.equalsIgnoreCase("SL")||cdtipsit.equalsIgnoreCase("SN"))
		{
			producto.setCdramo("2");
			producto.setCdtipsit(new String[]{cdtipsit});
			producto.setCdatribuCodigoPostal("3");
			producto.setCdatribuParentesco("16");
			producto.setCdatribuSexo("1");
			producto.setCdatribuFechaNacimi("2");
		}
		else if(cdtipsit.equalsIgnoreCase("MS"))
		{
			producto.setCdramo("4");
			producto.setCdtipsit(new String[]{"MS"});
			producto.setCdatribuCodigoPostal("");
			producto.setCdatribuParentesco("3");
			producto.setCdatribuSexo("2");
			producto.setCdatribuFechaNacimi("1");
		}
		else
		{
			throw new Exception("No existe la situacion "+cdtipsit);
		}
		return producto;
	}

	public String getCdramo() throws Exception {
		if(cdramo==null)
		{
			throw new Exception("cdramo no existe");
		}
		return cdramo;
	}

	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getCdtipsit() throws Exception{
		if(cdtipsit==null)
		{
			throw new Exception("cdtipsit no existe");
		}
		if(cdtipsit.length>1)
		{
			throw new Exception("cdtipsit multiple");
		}
		return cdtipsit[0];
	}

	public void setCdtipsit(String[] cdtipsit) {
		this.cdtipsit = cdtipsit;
	}

	public String getCdatribuParentesco() throws Exception{
		if(cdatribuParentesco==null)
		{
			throw new Exception("cdatribuParentesco no existe");
		}
		return cdatribuParentesco;
	}

	public void setCdatribuParentesco(String cdatribuParentesco) {
		this.cdatribuParentesco = cdatribuParentesco;
	}

	public String getCdatribuSexo() throws Exception{
		if(cdatribuSexo==null)
		{
			throw new Exception("cdatribuSexo no existe");
		}
		return cdatribuSexo;
	}

	public void setCdatribuSexo(String cdatribuSexo) {
		this.cdatribuSexo = cdatribuSexo;
	}

	public String getCdatribuCodigoPostal() throws Exception{
		if(cdatribuCodigoPostal==null)
		{
			throw new Exception("cdatribuCodigoPostal no existe");
		}
		return cdatribuCodigoPostal;
	}

	public void setCdatribuCodigoPostal(String cdatribuCodigoPostal) {
		this.cdatribuCodigoPostal = cdatribuCodigoPostal;
	}

	public String getCdatribuFechaNacimi() throws Exception{
		if(cdatribuFechaNacimi==null)
		{
			throw new Exception("cdatribuFechaNacimi no existe");
		}
		return cdatribuFechaNacimi;
	}

	public void setCdatribuFechaNacimi(String cdatribuFechaNacimi) {
		this.cdatribuFechaNacimi = cdatribuFechaNacimi;
	}
	
}