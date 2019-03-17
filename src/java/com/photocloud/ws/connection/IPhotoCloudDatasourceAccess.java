/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photocloud.ws.connection;

import com.mongodb.MongoClient;
import com.photocloud.ws.exceptions.DatabaseException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Ricardo Garcia
 */
public class IPhotoCloudDatasourceAccess{

    MongoClient connection;
    
    public IPhotoCloudDatasourceAccess() {
    }

    public MongoClient establishConnection() throws DatabaseException{
        connection= new MongoClient("localhost", 27017);
        return connection;
    }
    
    
    
}
