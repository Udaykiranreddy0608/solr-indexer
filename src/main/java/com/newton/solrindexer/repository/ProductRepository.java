package com.newton.solrindexer.repository;

import com.newton.solrindexer.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends SolrCrudRepository<Product, String> {

  public List<Product> findByFileName(String name);

  public Optional<Product> findById(String name);

  public List<Product> findAllByTitle(String name);

  @Override
  Iterable<Product> findAllById(Iterable<String> strings);

  @Override
  Iterable<Product> findAll();

  @Query("title:(?0) OR subject:(?0) OR content:(?0) OR author:(?0)")
  public Page<Product> findByCustomQuery(String searchTerm, Pageable pageable);

  @Query(name = "Product.findByNamedQuery")
  public Page<Product> findByNamedQuery(String searchTerm, Pageable pageable);
}
