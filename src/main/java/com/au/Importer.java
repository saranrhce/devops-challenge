package com.au;
import com.au.model.Products;
import com.au.validation.Validator;
import org.omg.Messaging.SyncScopeHelper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.fasterxml.jackson.databind.*;


public class Importer {
    static final Logger logger = Logger.getLogger(Importer.class.getName());

    static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        Validator validator = new Validator();

        List<File> filesInFolder = null;
        try {
            Files.walk(Paths.get(args[0]))
                     .filter(Files::isRegularFile)
                     .map(Path::toFile)
                     .collect(Collectors.toList()).forEach(file -> {
                try {

                    Products products = objectMapper.readValue(file, Products.class);
                    System.out.println("products" + products.getTransmissionsummary().getId());

                    if(!validator.isValidQty(products) || !validator.isValidRecordCount(products)) {
                        System.out.println(validator.isValidQty(products));
                        logger.info("Discarding the file as recordcount or qtysum does not add up");
                    }else {
                        logger.info("Continue processing the file");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
