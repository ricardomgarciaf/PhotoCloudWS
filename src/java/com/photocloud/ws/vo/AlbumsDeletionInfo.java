/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photocloud.ws.vo;

import java.util.List;

/**
 *
 * @author Ricardo Garcia
 */
public class AlbumsDeletionInfo {
    private List<String> albumIDs;
    private String userID;

    public List<String> getAlbumIDs() {
        return albumIDs;
    }

    public void setAlbumIDs(List<String> albumIDs) {
        this.albumIDs = albumIDs;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    
    
}
