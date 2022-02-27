package ocho.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import ocho.entity.OchoConfig;

public interface OchoConfigRepository extends MongoRepository<OchoConfig, BigInteger> {
}
