package io.github.calvary.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SalesReceiptTitle.
 */
@Entity
@Table(name = "sales_receipt_title")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "salesreceipttitle")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesReceiptTitle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "receipt_title", nullable = false, unique = true)
    private String receiptTitle;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SalesReceiptTitle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiptTitle() {
        return this.receiptTitle;
    }

    public SalesReceiptTitle receiptTitle(String receiptTitle) {
        this.setReceiptTitle(receiptTitle);
        return this;
    }

    public void setReceiptTitle(String receiptTitle) {
        this.receiptTitle = receiptTitle;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesReceiptTitle)) {
            return false;
        }
        return id != null && id.equals(((SalesReceiptTitle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesReceiptTitle{" +
            "id=" + getId() +
            ", receiptTitle='" + getReceiptTitle() + "'" +
            "}";
    }
}
