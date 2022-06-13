package bankingsystem.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String accountNo;
    private BigDecimal balance;
    @OneToOne
    @JoinColumn
    private User user;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "account")
    private Set<Transaction> transactionList=new HashSet<>();

    public void addTransaction(Transaction transaction)
    {
        transaction.setAccount(this);
        if(transaction!=null)
            transactionList.add(transaction);
        else {
            transactionList=new HashSet<>();
            transactionList.add(transaction);

        }
    }

}
