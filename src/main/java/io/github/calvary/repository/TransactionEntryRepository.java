package io.github.calvary.repository;

import io.github.calvary.domain.TransactionEntry;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TransactionEntry entity.
 */
@Repository
public interface TransactionEntryRepository extends JpaRepository<TransactionEntry, Long>, JpaSpecificationExecutor<TransactionEntry> {
    default Optional<TransactionEntry> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TransactionEntry> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TransactionEntry> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct transactionEntry from TransactionEntry transactionEntry left join fetch transactionEntry.transactionAccount left join fetch transactionEntry.accountTransaction",
        countQuery = "select count(distinct transactionEntry) from TransactionEntry transactionEntry"
    )
    Page<TransactionEntry> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct transactionEntry from TransactionEntry transactionEntry left join fetch transactionEntry.transactionAccount left join fetch transactionEntry.accountTransaction"
    )
    List<TransactionEntry> findAllWithToOneRelationships();

    @Query(
        "select transactionEntry from TransactionEntry transactionEntry left join fetch transactionEntry.transactionAccount left join fetch transactionEntry.accountTransaction where transactionEntry.id =:id"
    )
    Optional<TransactionEntry> findOneWithToOneRelationships(@Param("id") Long id);
}
