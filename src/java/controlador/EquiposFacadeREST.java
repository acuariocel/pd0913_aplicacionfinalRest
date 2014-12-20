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
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import modelo.Equipos;

/**
 *
 * @author CEL1
 */
@Stateless
@Path("modelo.equipos")
public class EquiposFacadeREST extends AbstractFacade<Equipos> {

    @PersistenceContext(unitName = "pd0913_aplicacionfinalRestPU")
    private EntityManager em;

    public EquiposFacadeREST() {
        super(Equipos.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Equipos entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Equipos entity) {
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
    public Equipos find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Equipos> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Equipos> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @POST
    @Path("editar")
    @Produces({"text/plain", "application/json"})
    public String editarEquipo(
            @FormParam("idEquipo") Integer idEquipo,
            @FormParam("estado") int estado) {
        try {
            TypedQuery<Equipos> qry;
            qry = getEntityManager().createNamedQuery("Equipos.findByIdEquipo", Equipos.class);
            qry.setParameter("idEquipo", idEquipo);
            Equipos eqp = qry.getSingleResult();
            eqp.setEstado(estado);
            super.edit(eqp);
            return "true";

        } catch (Exception e) {
            return "false";
        }
    }

    @GET
    @Path("ip={id}")
    @Produces({"application/json", "application/json"})
    public Equipos buscarPorIp(@PathParam("id") String ip) {
        TypedQuery<Equipos> qry;
        qry = getEntityManager().createQuery("SELECT u FROM Equipos u WHERE u.ip = :ip", Equipos.class);
        qry.setParameter("ip", ip);
        try {
            Equipos u = qry.getSingleResult();
            return u;
        } catch (Exception e) {
            return null;
        }
    }
}
