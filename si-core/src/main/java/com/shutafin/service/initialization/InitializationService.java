package com.shutafin.service.initialization;

import java.util.List;

public interface InitializationService {
    List findAllLanguages();
    List findAllGenders();
    List findAllCountries();
    List findAllCities();
}
