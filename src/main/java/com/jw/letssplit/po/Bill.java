package com.jw.letssplit.po;

import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    private Long id;

    private Integer uid;

    private Double balance;

    private Integer paidByUid;

    private Date createTime;

    private Boolean status;

    private Date doneTime;

    private String comment;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getPaidByUid() {
        return paidByUid;
    }

    public void setPaidByUid(Integer paidByUid) {
        this.paidByUid = paidByUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", balance=").append(balance);
        sb.append(", paidByUid=").append(paidByUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", doneTime=").append(doneTime);
        sb.append(", comment=").append(comment);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}