package com.example.ot.otusers.service;

import com.example.ot.otusers.model.RdfFormat;
import com.example.ot.otusers.model.UserDTO;
import com.example.ot.otusers.util.UserRDF;
import org.springframework.stereotype.Service;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

import java.io.OutputStream;
import java.util.List;

@Service
public class RdfService {

    public void generateRdf(List<UserDTO> userList, OutputStream outputStream, RdfFormat format) {
        Model model = ModelFactory.createDefaultModel();
        Resource resource = ResourceFactory.createProperty(UserDTO.class.getCanonicalName());

        for (UserDTO user : userList) {
            model.createResource()
                    .addProperty(RDF.type, resource)
                    .addProperty(UserRDF.FIRST_NAME, user.getFirstName())
                    .addProperty(UserRDF.LAST_NAME, user.getLastName())
                    .addProperty(UserRDF.EMAIL, user.getEmail());
        }

        model.write(outputStream, format.getFormat());
    }

}
