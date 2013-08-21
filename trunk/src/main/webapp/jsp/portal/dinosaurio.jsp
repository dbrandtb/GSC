<%-- 
    Document   : dinosaurio
    Created on : 15/08/2013, 12:44:12 PM
    Author     : Jair
--%>
<%@ include file="/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <script type="text/javascript" src="${ctx}/resources/extjs4/ext-all-debug.js"></script>
        <script>
            var Carrito=function()
            {
                this.id="";
                this.marca="";
                this.anio="";
            };
            var Dinosaurio=function()
            {
                this.id="";
                this.nombre="";
                this.edad="";
                this.carritos=[];
            };
            /*Ext.define('Carrito',
            /*
             private Long id;
            private String marca;
            private int anio;
             *
            {
                extend   : 'Ext.data.Model',
                fields   :
                [
                    {name:'id',     type: 'long'    },
                    {name:'marca',  type: 'string'  },
                    {name:'anio',   type: 'int'     }
                ]
            });
            Ext.define('Dinosaurio',
            /*
            private Long id;
            private String nombre;
            private int edad;
            private List<Carrito> carritos;
             *
            {
                fields   :
                [
                    {name:'id',      type: 'long'    },
                    {name:'nombre',  type: 'string'  },
                    {name:'edad',    type: 'int'     }
                ],
                hasMany:[
                {
                    //foreignKey: 'carritos',          /* rule 3, 5 *
                    //associationKey: 'carritos',    /* rule 4, 5 *
                    name: 'carritos',              /* rule 6 *
                    model: 'Carrito'   /* rule 7 *
                }]
            });*/
            
            var dinosaurio=new Dinosaurio();
            console.log(dinosaurio);
            dinosaurio.id=25;
            dinosaurio.nombre="Dino";
            dinosaurio.edad=600;
            console.log(dinosaurio);
            
            var c1=new Carrito();
            c1.id=1;
            c1.marca="Nissan";
            c1.anio=2012;
            console.log(c1);
            dinosaurio.carritos.push(c1);
            console.log(dinosaurio);
            
            var c2=new Carrito();
            c2.id=2;
            c2.marca="Chevrolet";
            c2.anio=2014;
            console.log(c2);
            dinosaurio.carritos.push(c2);
            console.log(dinosaurio);
            
            Ext.Ajax.request(
            {
                url: "<s:url action="dinosaurio2" namespace="/" />",
                jsonData:{dino:dinosaurio,llave:'yave'},
                success:function(response,opts)
                {
                    var jsonResp = Ext.decode(response.responseText);
                    console.log(jsonResp);
                },
                failure:function(response,opts)
                {
                    console.log("error");
                    Ext.Msg.show({
                        title:'Error',
                        msg: 'Error de comunicaci&oacute;n',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
                }
            });
            
        </script>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>RAWR!</h1>
    </body>
</html>
