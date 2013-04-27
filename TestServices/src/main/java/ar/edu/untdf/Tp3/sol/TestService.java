/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.untdf.testservices.sol;

import ar.edu.untdf.testservices.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.String;
import java.util.Iterator;
import java.util.Iterator;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author matiasgel
 */
public class TestService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        try {

            //realiza una llamada al servicio y transforma el contenido
            //de la respuesta en un string.
            //para la llamada utiliza commons-http de apache y para la conversión
            //utiliza commmons-io (la clase IOUtils)
            String request = "http://openexchangerates.org/api/latest.json?app_id=25fb0f55591b47abb1b86d90926679a4";
            HttpClient client = new DefaultHttpClient();
            HttpGet method = new HttpGet(request);
            HttpResponse response = client.execute(method);
            HttpEntity entity = response.getEntity();
            InputStream rstream = entity.getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(rstream, writer);
            String theString = writer.toString();
            JSONObject json = (JSONObject) JSONSerializer.toJSON(theString);
            
            //busca todas las claves que tiene el objeto json
            for (Iterator<String> i = json.keys(); i.hasNext();) {
			String key = i.next();
                        System.out.println(key);
			
		}
            
            //imprime el cambio con respecto al dolar de todas las monedas
            //en el string json el cambio se encuentra con la clave "rates"
            //dentro de rates tiene un conjunto de claves, cada una corresponde
            //a una moneda, el valor de cada clae es el cambio
            JSONObject a=json.getJSONObject("rates");             
            System.out.println("Cambio con respecto al dolar");
            for (Iterator<String> i = a.keys(); i.hasNext();) {
			String key = i.next();
                        System.out.print("Moneda: "+key+"   Cambio: "+a.getString(key));
                        System.out.println();
                        
                        
			
		}
            
            // JSONObject jo = new JSONObject(rstream);            
            /*     String diclamer=json.getString("diclaimer");
             System.out.println(diclamer);*/
        } catch (ClientProtocolException ex) {
            Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
