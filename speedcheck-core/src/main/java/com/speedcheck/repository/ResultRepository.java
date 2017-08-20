package com.speedcheck.repository;

import com.speedcheck.model.Result;
import com.speedcheck.transfer.Filters;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ResultRepository extends BaseRepository<Result> {

    public Collection<Result> findFiltered(final Filters filters) {
        Criteria criteria = getCriteria();
        if (filters.getType() != null) {
            criteria = criteria.add(Restrictions.eq("type", filters.getType()));
        }
        return criteria.list();
    }

    @Override
    protected Class<Result> getType() {
        return Result.class;
    }
}
