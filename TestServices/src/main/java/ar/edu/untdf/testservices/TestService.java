/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.untdf.testservices;

import ar.edu.untdf.model.Money;
import ar.edu.untdf.model.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.String;
import java.util.Iterator;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author matiasgel
 */
public class TestService {

    public static EntityManagerFactory f = Persistence.createEntityManagerFactory("money");

    public static void loadMoney() {
        try {

            EntityManager em = TestService.f.createEntityManager();
            String request = "http://openexchangerates.org/currencies.json";
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


            //imprime el cambio con respecto al dolar de todas las monedas
            //en el string json el cambio se encuentra con la clave "rates"
            //dentro de rates tiene un conjunto de claves, cada una corresponde
            //a una moneda, el valor de cada clae es el cambio

            System.out.println("Cambio con respecto al dolar");
            em.getTransaction().begin();
            for (Iterator<String> i = json.keys(); i.hasNext();) {
                String key = i.next();
                Money m = new Money();
                m.setSiglas(key);
                m.setDescripcion(json.getString(key));
                em.persist(m);
            }
            em.getTransaction().commit();
            em.close();
        } catch (IOException ex) {
            Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TestService.loadMoney();
        EntityManager em = f.createEntityManager();

        Query q = em.createQuery("Select m from Money m where m.siglas=:sigla");
        q.setParameter("sigla", "LBP");
        List<Money> monedas = q.getResultList();
        Usuario u = new Usuario();
        u.setNombre("heremias");
        u.setPasswd("1234");
        for (Money money : monedas) {
            u.getMonedas().add(money);
        }
        em.persist(u);
        //TestService.loadMoney();

        
         q=em.createQuery("Select u from Usuario u");
         System.out.println(q.getResultList().size());
      /*   List<Usuario> users = q.getResultList();
         System.out.println(users.size());
         for(Usuario u:users){
         System.out.println(u.getNombre());
         for(Money m:u.getMonedas())
         System.out.println(m.getDescripcion());
         }*/
    }
}
