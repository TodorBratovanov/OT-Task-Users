package com.example.ot.otusers.service;

import com.example.ot.otusers.model.RdfFormat;
import com.example.ot.otusers.model.UserDTO;
import com.example.ot.otusers.util.UserRDF;
import org.springframework.stereotype.Service;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

import java.io.OutputStream;

@Service
public class RdfService {

    public void generateRdf(UserDTO user, OutputStream outputStream, RdfFormat format) {
        Model model = ModelFactory.createDefaultModel();
        Resource resource = ResourceFactory.createProperty(UserDTO.class.getCanonicalName());

        model.createResource()
                .addProperty(RDF.type, resource)
                .addProperty(UserRDF.FIRST_NAME, user.getFirstName())
                .addProperty(UserRDF.LAST_NAME, user.getLastName())
                .addProperty(UserRDF.EMAIL, user.getEmail());

        model.write(outputStream, format.getFormat());
    }

}
