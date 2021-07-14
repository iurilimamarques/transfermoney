package com.iurilima.transfermoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository<User> extends JpaRepository<com.iurilima.transfermoney.model.User, Long>, QuerydslPredicateExecutor<com.iurilima.transfermoney.model.User> {

}
