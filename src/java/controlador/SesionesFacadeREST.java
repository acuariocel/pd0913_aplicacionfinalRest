/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.Date;
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
import modelo.Sesiones;
import modelo.Usuarios;

/**
 *
 * @author CEL1
 */
@Stateless
@Path("modelo.sesiones")
public class SesionesFacadeREST extends AbstractFacade<Sesiones> {

    @PersistenceContext(unitName = "pd0913_aplicacionfinalRestPU")
    private EntityManager em;

    public SesionesFacadeREST() {
        super(Sesiones.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Sesiones entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Sesiones entity) {
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
    public Sesiones find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json; charset=utf-8", "application/json"})
    public List<Sesiones> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Sesiones> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    //Metodo para guardar un equipo

    @POST
    @Path("registro")
    @Produces({"text/plain", "application/json"})
    public String createByParams(
            @FormParam("idEquipo") Integer idEquipo,
            @FormParam("idUsuario") Integer idUsuario
    ) {
        try {
            TypedQuery<Equipos> qEqp;
            qEqp = getEntityManager().createQuery("SELECT u FROM Equipos u WHERE u.idEquipo = :idEquipo", Equipos.class);
            qEqp.setParameter("idEquipo", idEquipo);
            TypedQuery<Usuarios> qUser;
            qUser = getEntityManager().createQuery("SELECT u FROM Usuarios u WHERE u.idUsuario = :idUsuario", Usuarios.class);
            qUser.setParameter("idUsuario", idUsuario);
            Date d = new Date();
            Sesiones e = new Sesiones((short) 0, d, d, qEqp.getSingleResult(), qUser.getSingleResult());
            super.create(e);
            TypedQuery<Sesiones> qry;
            qry = getEntityManager().createQuery("SELECT s FROM Sesiones s WHERE s.idUsuario.idUsuario = :idUsuario and s.idEquipo.idEquipo = :idEquipo", Sesiones.class);
            qry.setParameter("idUsuario", idUsuario);
            qry.setParameter("idEquipo", idEquipo);
            List<Sesiones> lis = qry.getResultList();
            Sesiones sAux = lis.get(lis.size() - 1);
            return sAux.getIdSesion() + "";
        } catch (Exception e) {
            return "false";
        }
    }

    //Metodo para editar una sesion
    @POST
    @Path("finSesion")
    @Produces({"text/plain", "application/json"})
    public String finSesion(@FormParam("idSesion") Integer idSesion) {
        try {
            TypedQuery<Sesiones> qry;
            qry = getEntityManager().createNamedQuery("Sesiones.findByIdSesion", Sesiones.class);
            qry.setParameter("idSesion", idSesion);
            Sesiones sesion = qry.getSingleResult();
            sesion.setFechaHoraFin(new Date());
            sesion.setBloqueado((short) 1);
            super.edit(sesion);
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }
}
