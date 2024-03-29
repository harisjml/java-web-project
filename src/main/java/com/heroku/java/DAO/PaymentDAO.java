package com.heroku.java.DAO;

import java.sql.Connection;
import java.sql.Date;
// import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.sql.DataSource;

// import com.heroku.java.bean.Customer;
import com.heroku.java.bean.Payment;
// import com.heroku.java.bean.Rental;

public class PaymentDAO {
    private DataSource dataSource;

    public PaymentDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int addPayment(Payment payment) throws SQLException {
        int paymentId = 0;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "INSERT INTO payment (payamount, paymentmethod,paymentreceipt,rentid) VALUES (?, ?, ?, ?) RETURNING paymentid;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, payment.getPayamount());
            statement.setString(2, payment.getPaymentmethod());
            statement.setBytes(3, payment.getPaymentbyte());
            statement.setInt(4,payment.getRentid());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                paymentId = resultSet.getInt("paymentid");
            }
            connection.close();
        } catch (SQLException e) {
            throw new SQLException("Error adding payment: " + e.getMessage());
        }
        return paymentId;
    }

    public int addCashPayment(Payment payment) throws SQLException {
        System.out.println("masuk DAO");
        int paymentId = 0;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "INSERT INTO payment (payamount, paymentmethod,rentid) VALUES ( ?, ?, ?) RETURNING paymentid;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, payment.getPayamount());
            statement.setString(2, payment.getPaymentmethod());
            statement.setInt(3,payment.getRentid());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                paymentId = resultSet.getInt("paymentid");
            }

            String sql2 = "INSERT INTO cashpayment (paymentid) VALUES (?)";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1,paymentId);
            statement2.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new SQLException("Error adding payment: " + e.getMessage());
        }
        return paymentId;
    }

    public Payment getPaymentbyPaymentId(int rentid) throws SQLException{
        Payment payment = null;
        try{
            Connection connection = dataSource.getConnection();
            String sql = "SELECT * FROM payment WHERE rentid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, rentid);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int paymentid = resultSet.getInt("paymentid");
                Double payamount = resultSet.getDouble("payamount");
                String paymentmethod = resultSet.getString("paymentmethod");
                byte[] paymentbyte = resultSet.getBytes("paymentreceipt");
                String imageSrc = null;  // Default to null if paymentreceipt is null
                if (paymentbyte != null) {
                String base64Image = Base64.getEncoder().encodeToString(paymentbyte);
                imageSrc = "data:image/jpeg;base64," + base64Image;
                }
                String paystatus = resultSet.getString("paystatus");
                int rentpid = resultSet.getInt("rentid");
                
                payment = new Payment(paymentid,payamount,paymentmethod,imageSrc,paystatus,rentpid);
            }
            
            connection.close();

        }catch (SQLException e) {
            throw new SQLException("Error adding payment: " + e.getMessage());
        }
        return payment;
    }

    public Payment updatePayment(String paystatus, int paymentid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Payment payment = new Payment();
            String sql = "UPDATE payment SET paystatus=? WHERE paymentid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, paystatus);
            statement.setInt(2, paymentid);
    
            statement.executeUpdate();
            connection.close();
            return payment;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Payment updateCash(Date cashreceivedate, int paymentid) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Payment payment = new Payment();
            String sql = "UPDATE cashpayment SET cashreceivedate=? WHERE paymentid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, cashreceivedate);
            statement.setInt(2, paymentid);
    
            statement.executeUpdate();
            connection.close();
            return payment;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Payment getCashbyPaymentId(int paymentid) throws SQLException{
        Payment payment = null;
        try{
            Connection connection = dataSource.getConnection();
            String sql = "SELECT * FROM cashpayment WHERE paymentid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, paymentid);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int paymentid2 = resultSet.getInt("paymentid");
                Date cashreceivedate = resultSet.getDate("cashreceivedate");
                
                payment = new Payment(paymentid2,cashreceivedate);
            }
            
            connection.close();

        }catch (SQLException e) {
            throw new SQLException("Error adding payment: " + e.getMessage());
        }
        return payment;
    }


    public void deleteCashPayment(int paymentid){
        try {
            Connection connection = dataSource.getConnection();
            String sql = "DELETE FROM cashpayment WHERE paymentid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, paymentid);
            statement.executeUpdate();
    
            System.out.println("success delete cash payment");
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        }
    }

    public void deletePayment(int paymentid){
        try {
            Connection connection = dataSource.getConnection();
            String sql = "DELETE FROM payment WHERE paymentid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, paymentid);
            statement.executeUpdate();
    
            System.out.println("success delete payment");
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        }
    }
}
