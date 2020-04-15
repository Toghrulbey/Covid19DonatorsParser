import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;


@Entity
@Table(name = "donators")
public class Donator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "name", unique = false, nullable = false, length = 100)
    private String name;

    @Column(name = "amount", unique = false, nullable = false, length = 100)
    private BigDecimal amount;

    @Column(name = "currency", unique = false, nullable = false, length = 100)
    private String currency;
    
    @Column(name = "person_type", unique = false, nullable = false, length = 100)
    private int types;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isLocal() {
        return this.currency == "AZN";
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Donator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", types=" + types +
                '}';
    }
}
