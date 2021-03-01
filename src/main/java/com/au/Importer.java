package com.au;
import com.au.model.Products;
import com.au.validation.Validator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.fasterxml.jackson.databind.*;
import org.json.JSONArray;
import org.json.JSONObject;


public class Importer {
    static final Logger logger = Logger.getLogger(Importer.class.getName());

    static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        Validator validator = new Validator();

        List<File> filesInFolder = null;
        try {
            String url = "jdbc:postgresql://postgres:5432/";
            String user = "postgres";
            String password = "postgres";

            Files.walk(Paths.get(args[0]))
                     .filter(Files::isRegularFile)
                     .map(Path::toFile)
                     .collect(Collectors.toList()).forEach(file -> {
                try {

                    Products products = objectMapper.readValue(file, Products.class);
                    JSONObject  jsonObject = new JSONObject (products);

                    if(!validator.isValidQty(products) || !validator.isValidRecordCount(products)) {
                        logger.info("Discarding the file "+ file  + " with product id " + products.getTransmissionsummary().getId() + " as recordcount or qtysum does not add up");
                        products.getProducts().forEach(product -> {
                            String[] categoryArr = product.getCategory().split(">");
                            logger.info(categoryArr[3] + "  -  " + product.getLocation() +"  -  " +  product.getQty() + "\n");
                        });
                    }else {

                        logger.info("Continue processing the file "+ file  +"  with product Id " + products.getTransmissionsummary().getId());
                        Connection con = Importer.getConnection(url,user,password);

                        String query = "INSERT INTO Products (transmission_id,recordcount,qtysum,json_col)\n" +
                                "VALUES(?,?,?,to_json(?::json));";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1, products.getTransmissionsummary().getId());
                        pst.setInt(2, products.getTransmissionsummary().getRecordcount());
                        pst.setInt(3, products.getTransmissionsummary().getQtysum());
                        pst.setObject(4,jsonObject.toString());
                        pst.executeUpdate();

                        logger.info("Completed processing the file "+ file  +"  with product Id " + products.getTransmissionsummary().getId());

                        products.getProducts().forEach(product -> {
                            String[] categoryArr = product.getCategory().split(">");
                            logger.info(categoryArr[2] + "  -  " + product.getLocation() +"  -  " +  product.getQty() + "\n");
                        });

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }catch (SQLException sqlLException) {
                    logger.info("SQL exception");
                    sqlLException.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                finally {
                    file.getAbsoluteFile().renameTo(new File(file.getName()+".COMPLETED"));
                    file.getAbsoluteFile().delete();

                    logger.info("Moved the file to output location");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String url, String user,String password) throws SQLException, ClassNotFoundException {
        logger.info("Establising DB connection");
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }
}
