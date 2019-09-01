package com.osbornandrew.personalfinance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByName(String name); // TODO: 1/2/2019 Need to limit this to only a single user's categories or those baked-in
}
