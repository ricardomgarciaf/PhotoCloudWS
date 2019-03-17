/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photocloud.ws.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.photocloud.ws.connection.IPhotoCloudDatasourceAccess;
import com.photocloud.ws.exceptions.DatabaseException;
import com.photocloud.ws.vo.AlbumCreationInfo;
import com.photocloud.ws.vo.AlbumsDeletionInfo;
import com.photocloud.ws.vo.Photo;
import com.photocloud.ws.vo.ServiceResponse;
import com.photocloud.ws.vo.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Ricardo Garcia
 */
public class PhotoCloudDAO {

    private MongoClient databaseAccess;
    private MongoDatabase database;

    public PhotoCloudDAO() throws DatabaseException {
        try {
            databaseAccess = new IPhotoCloudDatasourceAccess().establishConnection();
        } catch (DatabaseException ex) {
            throw new DatabaseException();
        }
    }

    public ServiceResponse insertUser(User user) {
        ServiceResponse response = new ServiceResponse();
        try (MongoClient a = databaseAccess) {
            MongoDatabase database = a.getDatabase("DBPhotoTrunk");
            MongoCollection<Document> userCollection = database.getCollection("user");
            FindIterable<Document> iterDoc = userCollection.find(Filters.eq("email", user.getEmail()));
            Iterator it = iterDoc.iterator();

            if (!it.hasNext()) {
                Document document = new Document("status", "active")
                        .append("email", user.getEmail())
                        .append("firstName", user.getFirstName())
                        .append("lastName", user.getLastName())
                        .append("created", new Date())
                        .append("loginAttempts", 0)
                        .append("pwd", user.getPassword());
                userCollection.insertOne(document);
                response.setCode(1);
                response.setObject(document.getObjectId("_id").toString());
            } else {
                response.setCode(0);
            }
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCode(-1);
            return response;
        }
    }

    public ServiceResponse login(String email, String password) {
        ServiceResponse response = new ServiceResponse();
        try (MongoClient a = databaseAccess) {
            MongoDatabase database = a.getDatabase("DBPhotoTrunk");
            MongoCollection<Document> userCollection = database.getCollection("user");
            FindIterable<Document> iterDoc = userCollection.find(Filters.eq("email", email));

            Iterator it = iterDoc.iterator();
            if (it.hasNext()) {
                Document result = (Document) it.next();
                int loginAttempts = (int) result.get("loginAttempts");
                if (loginAttempts == 3) {
                    userCollection.updateOne(Filters.eq("email", email), Updates.set("status", "blocked"));
                    response.setCode(2);
                } else {
                    if (result.get("pwd").equals(password)) {
                        userCollection.updateOne(Filters.eq("email", email), Updates.set("loginAttempts", 0));
                        response.setCode(1);
                    } else {
                        userCollection.updateOne(Filters.eq("email", email), Updates.inc("loginAttempts", 1));
                        response.setCode(0);
                    }
                }
            } else {
                response.setCode(0);
            }
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCode(-1);
            return response;
        }
    }

    public ServiceResponse createAlbum(AlbumCreationInfo albumCreationInfo) {
        ServiceResponse response = new ServiceResponse();
        try (MongoClient a = databaseAccess) {
            MongoDatabase database = a.getDatabase("DBPhotoTrunk");
            MongoCollection<Document> albumCollection = database.getCollection("album");
            Document document = new Document("name", albumCreationInfo.getAlbumTitle())
                    .append("active", true)
                    .append("created", new Date());
            albumCollection.insertOne(document);

            MongoCollection<Document> userCollection = database.getCollection("user");
            userCollection.updateOne(Filters.eq("_id", new ObjectId(albumCreationInfo.getUserID())), Updates.push("albums", document.getObjectId("_id").toString()));

            response.setCode(1);
            response.setObject(document.getObjectId("_id").toString());
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCode(-1);
            return response;
        }
    }

    public ServiceResponse deleteAlbums(AlbumsDeletionInfo albumsDeletionInfo) {
        ServiceResponse response = new ServiceResponse();
        try (MongoClient a = databaseAccess) {
            MongoDatabase database = a.getDatabase("DBPhotoTrunk");
            MongoCollection<Document> userCollection = database.getCollection("user");
            MongoCollection<Document> albumCollection = database.getCollection("album");
            albumsDeletionInfo.getAlbumIDs().forEach(albumID -> {
                userCollection.updateOne(Filters.eq("_id", new ObjectId(albumsDeletionInfo.getUserID())), Updates.pull("albums", new ObjectId(albumID).toString()));
                //userCollection.updateOne(Filters.eq("_id", new ObjectId(albumsDeletionInfo.getUserID())), Updates.pullByFilter(Filters.eq("_id", new ObjectId(albumID))));
                albumCollection.deleteOne(Filters.eq("_id", new ObjectId(albumID)));
            });
            response.setCode(1);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCode(-1);
            return response;
        }
    }

    public ServiceResponse createPhoto(Photo photo) {
        ServiceResponse response = new ServiceResponse();
        try (MongoClient a = databaseAccess) {
            MongoDatabase database = a.getDatabase("DBPhotoTrunk");
            MongoCollection<Document> photoCollection = database.getCollection("photo");
            Document document = new Document("name", photo.getSource())
                    .append("created", new Date())
                    .append("albumID", photo.getAlbumID())
                    .append("updated", new Date())
                    .append("active", true)
                    .append("geolocation", photo.getGeolocation());
            photoCollection.insertOne(document);
            response.setCode(1);
            response.setObject(document.getObjectId("_id").toString());
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCode(-1);
            return response;
        }
    }

    public ServiceResponse deletePhotos(List<String> photoIDs) {
        ServiceResponse response = new ServiceResponse();
        try (MongoClient a = databaseAccess) {
            MongoDatabase database = a.getDatabase("DBPhotoTrunk");
            MongoCollection<Document> photoCollection = database.getCollection("photo");
            photoIDs.forEach(photoID->photoCollection.deleteOne(Filters.eq("_id", new ObjectId(photoID))));
            response.setCode(1);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCode(-1);
            return response;
        }
    }

}
