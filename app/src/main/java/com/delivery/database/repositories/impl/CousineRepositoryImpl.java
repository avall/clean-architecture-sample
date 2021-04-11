package com.delivery.database.repositories.impl;

import com.delivery.core.domain.Cousine;
import com.delivery.core.mappers.CousineDomainDbMapper;
import com.delivery.core.repositories.ICousineRepository;
import com.delivery.database.repositories.DbCousineRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CousineRepositoryImpl implements ICousineRepository {

    private final DbCousineRepository dbCousineRepository;
    private final CousineDomainDbMapper cousineDomainDbMapper;

    @Override
    public List<Cousine> getAll() {
        return dbCousineRepository
                .findAll()
                .parallelStream()
                .map(c -> cousineDomainDbMapper.mapToDomain(c))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cousine> searchByName(String search) {
        return dbCousineRepository
                .findByNameContainingIgnoreCase(search)
                .parallelStream()
                .map(c -> cousineDomainDbMapper.mapToDomain(c))
                .collect(Collectors.toList());
    }
}
