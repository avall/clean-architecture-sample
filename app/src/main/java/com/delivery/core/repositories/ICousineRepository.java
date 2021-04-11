package com.delivery.core.repositories;

import com.delivery.core.domain.Cousine;
import java.util.List;

public interface ICousineRepository {
    List<Cousine> getAll();

    List<Cousine> searchByName(String search);
}
