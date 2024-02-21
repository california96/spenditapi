package com.cmd.spenditapi.services

import com.cmd.spenditapi.models.User
import groovy.util.logging.Slf4j
import com.cmd.spenditapi.config.ConfigDataSource
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

import javax.sql.DataSource
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import org.springframework.http.HttpStatus

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.function.Supplier
import java.util.concurrent.Executors
@Slf4j
@Service
class UserService {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    static final String COL_NAME = "users"

    /*User getUserbyId(int id){
        DataSource dataSource = null
        Connection connection = null;
        PreparedStatement prepStatement = null;
        User user = null
        ResultSet result = null
        try{
            dataSource = ConfigDataSource.source()
            connection = dataSource.getConnection()
            prepStatement = connection.prepareStatement("SELECT userID, username, email, firstName, lastName, image from ${COL_NAME} where userID = ?")
            prepStatement.setInt(1, id)
            result = prepStatement.executeQuery()
            if(result.next()) {
                user = new User(result.getInt("userId"), result.getString("userName"), result.getString("email")
                        , result.getString("firstName"), result.getString("lastName"), result.getString("image"))
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
            }
        }catch(SQLException sqlException){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, sqlException.getMessage())
        }
        user
    }*/
    CompletableFuture<User> getUserById(int id) {
        return CompletableFuture.supplyAsync(new Supplier<User>() {
            @Override
            User get() {
                DataSource dataSource = null;
                Connection connection = null;
                PreparedStatement prepStatement = null;
                User user = null;
                ResultSet result = null;
                try {
                    dataSource = ConfigDataSource.source()
                    connection = dataSource.getConnection();
                    prepStatement = connection.prepareStatement("SELECT userID, username, email, firstName, lastName, image from ${COL_NAME} where userID = ?");
                    prepStatement.setInt(1, id);
                    result = prepStatement.executeQuery();
                    if (result.next()) {
                        user = new User(result.getInt("userId"), result.getString("userName"), result.getString("email"),
                                result.getString("firstName"), result.getString("lastName"), result.getString("image"))
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                    }
                } catch (SQLException sqlException) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, sqlException.getMessage());
                } finally {
                    try {
                        if (result != null) result.close()
                        if (prepStatement != null) prepStatement.close()
                        if (connection != null) connection.close()
                    } catch (SQLException e) {

                    }
                }
                user;
            }
        }, executorService);
    }
}

