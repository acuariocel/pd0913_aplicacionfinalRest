/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import modelo.Usuarios;

/**
 *
 * @author CEL1
 */
@Stateless
@Path("modelo.usuarios")
public class UsuariosFacadeREST extends AbstractFacade<Usuarios> {

    @PersistenceContext(unitName = "pd0913_aplicacionfinalRestPU")
    private EntityManager em;

    public UsuariosFacadeREST() {
        super(Usuarios.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Usuarios entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Usuarios entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Usuarios find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Usuarios> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Usuarios> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("login/cedula={cedula},clave={clave}")
    @Produces({"application/json; charset=utf-8", "application/json"})
    public Usuarios login(@PathParam("cedula") String cedula, @PathParam("clave") String clave) {
        TypedQuery<Usuarios> tqu;
        tqu = getEntityManager().createQuery("SELECT u FROM Usuarios u WHERE u.cedula=:cedula AND u.clave=:clave", Usuarios.class);
        tqu.setParameter("cedula", cedula);
        tqu.setParameter("clave", clave);
        try {
            Usuarios u = tqu.getSingleResult();
            return u;
        } catch (Exception e) {
            return null;
        }
    }

}
