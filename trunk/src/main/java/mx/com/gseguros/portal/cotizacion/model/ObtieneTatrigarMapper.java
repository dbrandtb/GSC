package mx.com.gseguros.portal.cotizacion.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

public class ObtieneTatrigarMapper implements RowMapper
{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		ComponenteVO result = new ComponenteVO();
		result.setFlagEsAtribu(true);
		result.setType(ComponenteVO.TIPO_TATRIGAR);
		result.setNameCdatribu(rs.getString("CDATRIBU"));
		result.setTipoCampo(rs.getString("SWFORMAT"));

		String sMinlen = rs.getString("NMLMIN");
		int minlen = -1;
		boolean fMinlen = false;
		if (StringUtils.isNotBlank(sMinlen))
		{
			try
			{
				minlen = (Integer) Integer.parseInt(sMinlen);
				fMinlen = true;
			}
			catch (Exception ex)
			{
				minlen = -1;
				fMinlen = false;
			}
		}
		result.setMinLength(minlen);
		result.setFlagMinLength(fMinlen);

		String sMaxlen = rs.getString("NMLMAX");
		int maxlen = -1;
		boolean fMaxlen = false;
		if (StringUtils.isNotBlank(sMaxlen))
		{
			try
			{
				maxlen = (Integer) Integer.parseInt(sMaxlen);
				fMaxlen = true;
			}
			catch (Exception ex)
			{
				maxlen = -1;
				fMaxlen = false;
			}
		}
		result.setMaxLength(maxlen);
		result.setFlagMaxLength(fMaxlen);

		String sObliga = rs.getString("SWOBLIGA");
		boolean isObliga = false;
		if (StringUtils.isNotBlank(sObliga)
				&& sObliga.equalsIgnoreCase(Constantes.SI))
		{
			isObliga = true;
		}
		result.setObligatorio(isObliga);

		result.setLabel(rs.getString("DSATRIBU"));
		result.setCatalogo(rs.getString("OTTABVAL"));

		String sDepend = rs.getString("CDTABLJ1");
		boolean isDepend = false;
		if (StringUtils.isNotBlank(sDepend))
		{
			isDepend = true;
		}
		result.setDependiente(isDepend);

		String valor = rs.getString("VALOR");
		if(StringUtils.isNotBlank(valor))
		{
			if(StringUtils.isNotBlank(result.getCatalogo()))
			{
				result.setValue(Utils.join("'"+valor+"'"));
			}
			else
			{
				result.setValue(valor);
			}
		}

		String minimo = rs.getString("MINIMO");
		if(StringUtils.isNotBlank(minimo))
		{
			result.setMinValue(minimo);
		}
		
		String maximo = rs.getString("MAXIMO");
		if (StringUtils.isNotBlank(maximo))
		{
			result.setMaxValue(maximo);
		}
		
		String valorComple1 = rs.getString("VALOR"); // Valor default para el caso de Porcentaje/Monto, se usa para Copagos
		if(StringUtils.isNotBlank(valorComple1))
		{
			result.setValorAuxiliar1(valorComple1);
		}

		String minimoComple1 = rs.getString("MINIMO");// Valor default minimo para el caso de Porcentaje/Monto, se usa para Copagos
		if(StringUtils.isNotBlank(minimoComple1))
		{
			result.setValorAuxiliar2(minimoComple1);
		}
		
		String maximoComple1 = rs.getString("MAXIMO");// Valor default maximo para el caso de Porcentaje/Monto, se usa para Copagos
		if (StringUtils.isNotBlank(maximoComple1))
		{
			result.setValorAuxiliar3(maximoComple1);
		}
		
		
		String aux = rs.getString("TIPO"); //Para tipo de atributo, se usa C para cuando es Copago
		if(StringUtils.isNotBlank(aux))
		{
			result.setAuxiliar(aux);
		}
		
		String formatoComple = rs.getString("SWFORALT"); // Formato default alterno para el caso de Porcentaje/Monto, se usa para Copagos
		if(StringUtils.isNotBlank(formatoComple))
		{
			result.setFormatoAlterno(formatoComple);
		}
		
		String valorComple2 = rs.getString("VALALT"); // Valor default alterno para el caso de Porcentaje/Monto, se usa para Copagos
		if(StringUtils.isNotBlank(valorComple2))
		{
			result.setValorAuxiliar4(valorComple2);
		}

		String minimoComple2 = rs.getString("MINALT");// Valor default minimo alterno para el caso de Porcentaje/Monto, se usa para Copagos
		if(StringUtils.isNotBlank(minimoComple2))
		{
			result.setValorAuxiliar5(minimoComple2);
		}
		
		String maximoComple2 = rs.getString("MAXALT");// Valor default maximo alterno para el caso de Porcentaje/Monto, se usa para Copagos
		if (StringUtils.isNotBlank(maximoComple2))
		{
			result.setValorAuxiliar6(maximoComple2);
		}

		return result;
	}
}