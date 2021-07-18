package {PACKAGE}.repository;

import {PACKAGE}.domain.{DOMAIN};
import org.springframework.data.jpa.repository.JpaRepository;

public interface {DOMAIN}Repository extends JpaRepository<{DOMAIN}, Long> {
}
