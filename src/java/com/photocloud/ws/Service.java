/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photocloud.ws;

import com.photocloud.ws.exceptions.DatabaseException;
import com.photocloud.ws.vo.AlbumCreationInfo;
import com.photocloud.ws.vo.AlbumsDeletionInfo;
import com.photocloud.ws.vo.Photo;
import com.photocloud.ws.vo.User;
import java.util.List;
import javax.ws.rs.core.Response;
/**
 *
 * @author Ricardo Garcia
 */
public interface Service {
    
    public Response createUser(User user) throws DatabaseException;
    
    public Response login(String email, String password) throws DatabaseException;
    
    public Response createAlbum(AlbumCreationInfo albumCreationInfo) throws DatabaseException;
    
    public Response deleteAlbums(AlbumsDeletionInfo albumsDeletionInfo) throws DatabaseException;
    
    public Response createPhoto(Photo photo) throws DatabaseException;
    
    public Response deletePhotos(List<String> photoID) throws DatabaseException;
}
