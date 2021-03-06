package poe.jsf.rest;

import poe.jsf.dao.TrackDao;
import poe.jsf.domain.Track;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("track")
public class TrackService {

    @EJB
    private TrackDao userDao;

    @EJB
    private TrackDao trackDao;


    @GET
    @Produces("application/json")
    @Path("{id}")
    public Track show(@PathParam("id") Long trackId) {
        Track track = trackDao.get(trackId);
        System.out.println("the track to show " + track.getId());
        return track;
    }

    @POST
    @Path("/{title}/{artist}")
    public Response add(@PathParam("title") String title, @PathParam("artist") String artist, @Context UriInfo uriInfo) {
        Track track = new Track();
        track.setTitle(title);
        track.setArtist(artist);
        Long trackId = trackDao.add(track);
        return Response.created(uriInfo.getBaseUriBuilder().path(TrackService.class).path(Long.toString(trackId)).build()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addJson(Track track, @Context UriInfo uriInfo) {
        Long trackId = trackDao.add(track);
        return Response.created(uriInfo.getBaseUriBuilder().path(TrackService.class).path(Long.toString(trackId)).build()).build();
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long trackId) {
        trackDao.delete(trackId);
    }

    @GET
    public List<Track> list() {
        return trackDao.list();
    }

}
