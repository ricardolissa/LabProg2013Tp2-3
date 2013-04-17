Practico 2 aplicaciones desktop
==============================

Realizar una aplicación que muestre el cambio con respecto al dólar de las monedas que ingrese el usuario.  Dentro del repositorio encontrara una pantalla ejemplo pantalla.png .

Para realizarlo debe tener en cuenta lo siguiente:
 
Servicio de cambio:   en el proyecto TestServices hay un ejemplo de conexión a openexchangerates.org donde obtiene el listado de monedas y su cambio con respecto al dólar, 
debe tener un diseño flexible, tendría que soportar diferentes tipos de servicios. Para esto implementar el patrón “factory method” en el modelo de la Jtable. 
Implementar 2 versiones de la aplicación, una con el cambio de  openexchangerates y otra usando el api de cambio de google, en los dos casos usar el listado de monedas soportadas por openexchangerates.


Servicio de google
-----------------------

 Se llama al calculador con la cantidad y las monedas deseadas en el cambio y responde en json: ejemplo de llamada

http://www.google.com/ig/calculator?hl=en&q=1GBP=?USD

OOO= moneda origen
DDD= moneda destino
q= cantidad a cambiar
http://www.google.com/ig/calculator?hl=en&q=montoOOO=?DDD