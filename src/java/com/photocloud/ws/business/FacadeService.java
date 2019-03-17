/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photocloud.ws.business;

import com.photocloud.ws.dao.PhotoCloudDAO;
import com.photocloud.ws.exceptions.DatabaseException;
import com.photocloud.ws.vo.AlbumCreationInfo;
import com.photocloud.ws.vo.AlbumsDeletionInfo;
import com.photocloud.ws.vo.Photo;
import com.photocloud.ws.vo.ServiceResponse;
import com.photocloud.ws.vo.User;
import java.util.List;

/**
 *
 * @author Ricardo Garcia
 */
public class FacadeService {

    public ServiceResponse createUser(User user) throws DatabaseException {
        PhotoCloudDAO photoCloudDAO = new PhotoCloudDAO();
        return photoCloudDAO.insertUser(user);
    }

    public ServiceResponse login(String email, String password) throws DatabaseException {
        PhotoCloudDAO photoCloudDAO = new PhotoCloudDAO();
        return photoCloudDAO.login(email, password);
    }

    public ServiceResponse createAlbum(AlbumCreationInfo albumCreationInfo) throws DatabaseException {
        PhotoCloudDAO photoCloudDAO = new PhotoCloudDAO();
        return photoCloudDAO.createAlbum(albumCreationInfo);
    }
    
    public ServiceResponse deleteAlbums(AlbumsDeletionInfo albumsDeletionInfo) throws DatabaseException{
        PhotoCloudDAO photoCloudDAO= new PhotoCloudDAO();
        return photoCloudDAO.deleteAlbums(albumsDeletionInfo);
    }

    public ServiceResponse createPhoto(Photo photo) throws DatabaseException {
        PhotoCloudDAO photoCloudDAO = new PhotoCloudDAO();
        return photoCloudDAO.createPhoto(photo);
    }

    public ServiceResponse deletePhotos(List<String> photoIDs) throws DatabaseException {
        PhotoCloudDAO photoCloudDAO = new PhotoCloudDAO();
        return photoCloudDAO.deletePhotos(photoIDs);
    }

}
