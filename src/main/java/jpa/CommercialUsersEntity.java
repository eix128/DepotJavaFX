package jpa;

import javax.persistence.*;

/**
 * Created by kadir.basol on 1.4.2016.
 */
@Entity
@Table(name = "commercialUsers", schema = "dbo", catalog = "jdepo")
public class CommercialUsersEntity {
    private int id;
    private String ntlmName;
    private String userName;
    private String userSurname;
    private Long tcKimlikNo;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ntlmName", nullable = true, length = 64)
    public String getNtlmName() {
        return ntlmName;
    }

    public void setNtlmName(String ntlmName) {
        this.ntlmName = ntlmName;
    }

    @Basic
    @Column(name = "userName", nullable = true, length = 64)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "userSurname", nullable = true, length = 64)
    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    @Basic
    @Column(name = "tcKimlikNo", nullable = true)
    public Long getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(Long tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommercialUsersEntity that = (CommercialUsersEntity) o;

        if (id != that.id) return false;
        if (ntlmName != null ? !ntlmName.equals(that.ntlmName) : that.ntlmName != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userSurname != null ? !userSurname.equals(that.userSurname) : that.userSurname != null) return false;
        if (tcKimlikNo != null ? !tcKimlikNo.equals(that.tcKimlikNo) : that.tcKimlikNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ntlmName != null ? ntlmName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userSurname != null ? userSurname.hashCode() : 0);
        result = 31 * result + (tcKimlikNo != null ? tcKimlikNo.hashCode() : 0);
        return result;
    }
}
