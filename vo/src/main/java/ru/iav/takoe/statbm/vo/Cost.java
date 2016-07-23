package ru.iav.takoe.statbm.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Cost implements Serializable {

    private UUID uuid;

    private Date timestamp;

    private BigDecimal amount;

    private String comment;

    private Cost(BigDecimal amount, String comment) {
        this.uuid = UUID.randomUUID();
        this.timestamp = now();
        this.amount = amount;
        this.comment = comment;
    }

    public static Cost create(BigDecimal amount, String comment) throws IllegalArgumentException {
        try {
            validate(amount);
            validate(comment);
            return new Cost(amount, comment);
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(iae.getMessage() + " Cost instance was not constructed.");
        }
    }

    private static void validate(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount must be non-null!");
        }
        if (BigDecimal.ZERO.equals(amount)) {
            throw new IllegalArgumentException("Amount must be non-zero!");
        }
    }

    private static void validate(String comment) {
        if (comment == null || comment.trim().length() == 0) {
            throw new IllegalArgumentException("Comment most be not empty!");
        }
    }

    private static Date now() {
        return new Date();
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

        if (uuid != null ? !uuid.equals(cost.uuid) : cost.uuid != null) return false;
        return amount != null ? amount.equals(cost.amount) : cost.amount == null;

    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
