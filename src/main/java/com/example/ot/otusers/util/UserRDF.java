package com.example.ot.otusers.util;

import org.apache.jena.rdf.model.*;

public class UserRDF {

    private static Model model = ModelFactory.createDefaultModel();

    public static final Property FIRST_NAME = model.createProperty("FIRST_NAME:" );

    public static final Property LAST_NAME = model.createProperty("LAST_NAME:" );

    public static final Property EMAIL = model.createProperty("EMAIL:" );

}
