/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.*;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import util.Common;

/**
 * The main set of web services.
 */
@Named
@Singleton
public class PostController extends Controller {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // We are using constructor injection to receive a repository to support our
    // desire for immutability.
    @Inject
    public PostController(final PostRepository postRepository, final UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Result addPost() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out
                    .println("Post not added, expecting Json data");
            return badRequest("Post not added, expecting Json data");
        }

        // Parse JSON file
        String content = json.findPath("content").asText();
        int likes = json.findPath("likes").asInt();
        Date createTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat(Common.DATE_PATTERN);
        try {
            createTime = format.parse(json.findPath("createTime").asText());
        } catch (ParseException e) {
            System.out.println("No creation date specified, set to current time");
        }
        String email = json.findPath("email").asText();

        try {
            User user = userRepository.findByEmail(email);
            Post newPost = new Post(user, content, likes, createTime);
            Post savedPost = postRepository.save(newPost);
            System.out.println("Climate Service saved: "
                    + savedPost.getContent());
            return created(new Gson().toJson(savedPost.getId()));
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            System.out.println("Post not saved: " + content);
            return badRequest("Post not saved: " + content);
        }
    }

    public Result addPostTest() {
        try {
            Post newPost = new Post(null, "Test comment", 100, new Date());
            Post savedPost = postRepository.save(newPost);
            return created(new Gson().toJson(savedPost.getId()));
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            System.out.println("Post not saved!");
            return badRequest("Post not saved!");
        }
    }

//    public Result deleteUser(Long id) {
//        User deleteUser = userRepository.findOne(id);
//        if (deleteUser == null) {
//            System.out.println("User not found with id: " + id);
//            return notFound("User not found with id: " + id);
//        }
//
//        userRepository.delete(deleteUser);
//        System.out.println("User is deleted: " + id);
//        return ok("User is deleted: " + id);
//    }
//
//    public Result updateUser(long id) {
//        JsonNode json = request().body().asJson();
//        if (json == null) {
//            System.out.println("User not saved, expecting Json data");
//            return badRequest("User not saved, expecting Json data");
//        }
//
//        // Parse JSON file
//        String firstName = json.path("firstName").asText();
//        String lastName = json.path("lastName").asText();
//        String middleInitial = json.path("middleInitial").asText();
//        String affiliation = json.path("affiliation").asText();
//        String title = json.path("title").asText();
//        String email = json.path("email").asText();
//        String mailingAddress = json.path("mailingAddress").asText();
//        String phoneNumber = json.path("phoneNumber").asText();
//        String faxNumber = json.path("faxNumber").asText();
//        String researchFields = json.path("researchFields").asText();
//        String highestDegree = json.path("highestDegree").asText();
//        try {
//            User updateUser = userRepository.findOne(id);
//
//            updateUser.setFirstName(firstName);
//            updateUser.setLastName(lastName);
//            updateUser.setAffiliation(affiliation);
//            updateUser.setEmail(email);
//            updateUser.setFaxNumber(faxNumber);
//            updateUser.setHighestDegree(highestDegree);
//            updateUser.setMailingAddress(mailingAddress);
//            updateUser.setMiddleInitial(middleInitial);
//            updateUser.setPhoneNumber(phoneNumber);
//            updateUser.setResearchFields(researchFields);
//            updateUser.setTitle(title);
//
//            User savedUser = userRepository.save(updateUser);
//            System.out.println("User updated: " + savedUser.getFirstName()
//                    + " " + savedUser.getLastName());
//            return created("User updated: " + savedUser.getFirstName() + " "
//                    + savedUser.getLastName());
//        } catch (PersistenceException pe) {
//            pe.printStackTrace();
//            System.out.println("User not updated: " + firstName + " "
//                    + lastName);
//            return badRequest("User not updated: " + firstName + " " + lastName);
//        }
//    }
//
    // get a post by post id
    public Result getPost(Long id, String format) {
        if (id == null) {
            System.out.println("Post id is null or empty!");
            return badRequest("Post id is null or empty!");
        }

        Post post = postRepository.findOne(id);

        if (post == null) {
            System.out.println("Post not found with with id: " + id);
            return notFound("Post not found with with id: " + id);
        }
        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(post);
        }

        return ok(result);
    }

    // get all posts by a user id
    public Result getAllPostsByUserId(Long id, String format) {
        if (id == null) {
            System.out.println("User id is null or empty!");
            return badRequest("User id is null or empty!");
        }

        User user = userRepository.findOne(id);
        List<Post> posts = postRepository.findAllByUser(user);
        if (posts == null) {
            System.out.println("Posts not found with user id: " + id);
            return notFound("Posts service not found with user id: " + id);
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(posts);
        }

        return ok(result);
    }

//    // get all posts shared by a user id
//    public Result getSharedPost(Long id, String format) {
//
//    }
//
//    public Result getAllUsers(String format) {
//        Iterable<User> userIterable = userRepository.findAll();
//        List<User> userList = new ArrayList<User>();
//        for (User user : userIterable) {
//            userList.add(user);
//        }
//        String result = new String();
//        if (format.equals("json")) {
//            result = new Gson().toJson(userList);
//        }
//        return ok(result);
//    }
//
//    public Result isUserValid() {
//        JsonNode json = request().body().asJson();
//        if (json == null) {
//            System.out.println("Cannot check user, expecting Json data");
//            return badRequest("Cannot check user, expecting Json data");
//        }
//        String email = json.path("email").asText();
//        String password = json.path("password").asText();
//        User user = userRepository.findByEmail(email);
//        if (user.getPassword().equals(password)) {
//            System.out.println("User is valid");
//            return ok("User is valid");
//        } else {
//            System.out.println("User is not valid");
//            return badRequest("User is not valid");
//        }
//    }
//
//    public Result deleteUserByUserNameandPassword(String userName, String password) {
//        try {
//            List<User> users = userRepository.findByUserName(userName);
//            if (users.size()==0) {
//                System.out.println("User is not existed");
//                return badRequest("User is not existed");
//            }
//            User user = users.get(0);
//            if (user.getPassword().equals(password)) {
//                System.out.println("User is deleted: "+user.getId());
//                userRepository.delete(user);
//                return ok("User is deleted");
//            }
//            else {
//                System.out.println("User is not deleted for wrong password");
//                return badRequest("User is not deleted for wrong password");
//            }
//        }
//        catch (PersistenceException pe) {
//            pe.printStackTrace();
//            System.out.println("User is not deleted");
//            return badRequest("User is not deleted");
//        }
//
//    }

}
