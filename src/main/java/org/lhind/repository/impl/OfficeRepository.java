package org.lhind.repository.impl;
import org.lhind.mapper.*;
import org.lhind.model.entity.*;
import org.lhind.util.JdbcConnection;
import org.lhind.util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class OfficeRepository extends BaseRepository<Office, Integer>{
    public OfficeRepository() {
        super(new OfficeMapper());
    }

    @Override
    public List<Office> findAll() {
        List<Office> offices = new ArrayList<>();
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.FIND_ALL_OFFICES)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Office office = getMapper().map(result);
                offices.add(office);
            }
        } catch (SQLException e) {
            System.err.println("Error");
        }
        return offices;
    }

    @Override
    public Office findById(Integer id) {
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.FIND_OFFICE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return getMapper().map(result);
            }
        } catch (SQLException e) {
            System.err.println("Error");
        }
        return null;
    }

    public Integer getMaxId(){
        Integer maxId = 2001;
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.ID_MAX_OFF)) {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                maxId = result.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("ErrorMaxID");
        }
        return maxId;
    }
    @Override
    public Boolean exists(Integer id) {
        try {
            Office off = this.findById(id);
            return off != null;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean save(Office office) {
        if (this.exists(office.getId())){
            update(office);
            return true;
        }
        else {
            int rows=0;
            try(Connection connection = JdbcConnection.connect();
                PreparedStatement statement = connection.prepareStatement(Queries.ADD_OFFICE)){
                if (office.getId()>this.getMaxId())
                {
                    statement.setInt(1,office.getId());
                }
                else{
                    statement.setInt(1,this.getMaxId()+1);
                }
                statement.setString(2, office.getCity());
                statement.setString(3, office.getPhone());
                statement.setString(4, office.getAddressLine1());
                statement.setString(5, office.getAddressLine2());
                statement.setString(6, office.getState());
                statement.setString(7, office.getCountry());
                statement.setString(8, office.getPostalCode());
                statement.setString(9, office.getTerritory());
                rows = statement.executeUpdate();
                if (rows == 1){
                    System.out.println("New office added to the offices table");
                }
                System.out.println("Rows updated: "+rows);
            }
            catch (SQLException e) {
                System.err.println("ErrorAdd");
            }
            return rows>=1;
        }
    }

    @Override
    public Integer update(Office office) {
        Integer rows = 0;
        if (this.exists(office.getId())){
            try (Connection connection = JdbcConnection.connect();
                 PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_OFFICE)) {
                statement.setString(1, office.getCity());
                statement.setString(2, office.getPhone());
                statement.setString(3, office.getAddressLine1());
                statement.setString(4, office.getAddressLine2());
                statement.setString(5, office.getState());
                statement.setString(6, office.getCountry());
                statement.setString(7, office.getPostalCode());
                statement.setString(8, office.getTerritory());
                statement.setInt(9, office.getId());
                rows = statement.executeUpdate();
                if (rows == 1){
                    System.out.println("Office with ID: " +office.getId() + " is updated");
                }
                System.out.println("Rows updated: "+rows);
            } catch (SQLException e) {
                System.err.println("ErrorUpdate");
            }
        }
        else {
            System.out.println("Couldnt update. Office not found\nRows affected: "+rows);
        }
        return rows;
    }
}