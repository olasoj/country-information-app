package com.example.countryinformationapplication.client.service.rest.resttemplate;

import com.example.countryinformationapplication.client.service.library.HttpLibrary;
import com.example.countryinformationapplication.client.service.rest.AbstractRestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RestTemplateRestService extends AbstractRestService {

    protected RestTemplateRestService(@Qualifier("restTemplateHttpLibrary") HttpLibrary httpLibrary) {
        super(httpLibrary);
    }

}
