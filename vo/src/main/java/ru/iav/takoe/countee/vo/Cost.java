package ru.iav.takoe.countee.vo;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static ru.iav.takoe.countee.utils.DateUtils.now;

public class Cost implements Serializable {

    private UUID uuid;

    private Date timestamp;

    private BigDecimal amount;

    private String comment;

    Cost(BigDecimal amount, String comment) {
        this.uuid = UUID.randomUUID();
        this.timestamp = now();
        this.amount = amount;
        this.comment = comment;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cost cost = (Cost) o;
        return Objects.equal(uuid, cost.uuid) &&
                Objects.equal(amount, cost.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, amount);
    }
}
