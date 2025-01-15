package org.example.server_mobile.repository;

import org.example.server_mobile.entity.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressesRepo extends JpaRepository<Addresses, Long> {
}
