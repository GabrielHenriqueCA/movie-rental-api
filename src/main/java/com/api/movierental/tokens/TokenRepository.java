package com.api.movierental.tokens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenModel, UUID> {

    @Query(value = """
            select t from TokenModel t inner join t.user u\s
            where u.id = :id and (t.expired = false or t.revoked = false)\s
            """)
    List<TokenModel> findAllValidTokenByUser(UUID id);

    Optional<TokenModel> findByToken(String token);
}
