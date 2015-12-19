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
package models;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Provides CRUD functionality for accessing people. Spring Data auto-magically takes care of many standard
 * operations here.
 */
@Named
@Singleton
public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findByUserName(String userName);
	User findByEmail(String email);
	List<User> findByFirstName(String firstName);
	List<User> findByLastName(String lastName);

    @Query(value = "select * from User where firstName = ?1 or lastName = ?2 or email = ?3 or affiliation = ?4 or title = ?5", nativeQuery = true)
    List<User> getUsersByCriteria(String firstName, String lastName, String email, String affiliation, String title);

    @Query(value = "select * from User where SOUNDEX(firstName) like SOUNDEX(?1) or SOUNDEX(lastName) like SOUNDEX(?1) or SOUNDEX(email) like SOUNDEX(?1) or SOUNDEX(affiliation) like SOUNDEX(?1) or SOUNDEX(title) like SOUNDEX(?1)", nativeQuery = true)
    List<User> getUsersByFuzzySearch(String partial);

    @Query(value = "select * from User where firstName like ?1", nativeQuery = true)
    List<User> getUsersByFirstNameForAutoComp(String query);

    @Query(value = "select * from User where lastName like ?1", nativeQuery = true)
    List<User> getUsersByLastNameForAutoComp(String query);

    @Query(value = "select * from User where email like ?1", nativeQuery = true)
    List<User> getUsersByEmailForAutoComp(String query);

    @Query(value = "select * from User where firstName like ?1 and lastName like ?2 and email like ?3", nativeQuery = true)
    List<User> getUsersByExactMatchForAutoComp(String firstNameQuery, String lastNameQuery, String emailQuery);

}
