/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photocloud.ws;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.photocloud.ws.business.FacadeService;
import com.photocloud.ws.exceptions.DatabaseException;
import com.photocloud.ws.vo.AlbumCreationInfo;
import com.photocloud.ws.vo.AlbumsDeletionInfo;
import com.photocloud.ws.vo.Photo;
import com.photocloud.ws.vo.User;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ricardo Garcia
 */
@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceImpl implements Service{

    @POST
    @Path("/user/create")
    @Override
    public Response createUser(User user) throws DatabaseException{
        return Response.ok().entity(new Gson().toJson(new FacadeService().createUser(user))).build();
    }

    @GET
    @Path("/login")
    @Override
    public Response login(@QueryParam("email") String email,@QueryParam("password") String password) throws DatabaseException{
        return Response.ok().entity(new Gson().toJson(new FacadeService().login(email, password))).build();
    }

    @POST
    @Path("/album/create")
    @Override
    public Response createAlbum(AlbumCreationInfo albumCreationInfo) throws DatabaseException{
        return Response.ok().entity(new Gson().toJson(new FacadeService().createAlbum(albumCreationInfo))).build();
    }
    
    @POST
    @Path("/album/delete")
    @Override
    public Response deleteAlbums(AlbumsDeletionInfo albumsDeletionInfo) throws DatabaseException{
        return Response.ok().entity(new Gson().toJson(new FacadeService().deleteAlbums(albumsDeletionInfo))).build();
    }

    @POST
    @Path("/photo/create")
    @Override
    public Response createPhoto(Photo photo) throws DatabaseException{
        return Response.ok().entity(new Gson().toJson(new FacadeService().createPhoto(photo))).build();
    }

    @POST
    @Path("/photo/delete")
    @Override
    public Response deletePhotos(List<String> photoID) throws DatabaseException{
        return Response.ok().entity(new Gson().toJson(new FacadeService().deletePhotos(photoID))).build();
    }
    
}
