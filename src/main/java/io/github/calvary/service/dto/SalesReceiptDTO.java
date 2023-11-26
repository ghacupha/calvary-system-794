package io.github.calvary.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.calvary.domain.SalesReceipt} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesReceiptDTO implements Serializable {

    private Long id;

    private String salesReceiptTitle;

    private String description;

    private TransactionClassDTO transactionClass;

    private DealerDTO dealer;

    private Set<TransactionItemAmountDTO> transactionItemAmounts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesReceiptTitle() {
        return salesReceiptTitle;
    }

    public void setSalesReceiptTitle(String salesReceiptTitle) {
        this.salesReceiptTitle = salesReceiptTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionClassDTO getTransactionClass() {
        return transactionClass;
    }

    public void setTransactionClass(TransactionClassDTO transactionClass) {
        this.transactionClass = transactionClass;
    }

    public DealerDTO getDealer() {
        return dealer;
    }

    public void setDealer(DealerDTO dealer) {
        this.dealer = dealer;
    }

    public Set<TransactionItemAmountDTO> getTransactionItemAmounts() {
        return transactionItemAmounts;
    }

    public void setTransactionItemAmounts(Set<TransactionItemAmountDTO> transactionItemAmounts) {
        this.transactionItemAmounts = transactionItemAmounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesReceiptDTO)) {
            return false;
        }

        SalesReceiptDTO salesReceiptDTO = (SalesReceiptDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salesReceiptDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesReceiptDTO{" +
            "id=" + getId() +
            ", salesReceiptTitle='" + getSalesReceiptTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", transactionClass=" + getTransactionClass() +
            ", dealer=" + getDealer() +
            ", transactionItemAmounts=" + getTransactionItemAmounts() +
            "}";
    }
}