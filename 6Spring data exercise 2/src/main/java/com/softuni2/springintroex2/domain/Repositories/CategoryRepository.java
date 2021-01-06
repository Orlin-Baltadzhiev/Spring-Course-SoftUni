package com.softuni2.springintroex2.domain.Repositories;

import com.softuni2.springintroex2.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
