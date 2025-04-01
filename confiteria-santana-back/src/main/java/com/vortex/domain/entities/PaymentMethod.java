package com.vortex.domain.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * The type Payment methods.
 */
@Entity
@Table(name = "PaymentsMethods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String last4;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private int expiryMonth;

    @Column(nullable = false)
    private int expiryYear;

    private Timestamp created_at;

    /**
     * Instantiates a new Payment methods.
     */
    public PaymentMethod() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets provider.
     *
     * @return the provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Sets provider.
     *
     * @param provider the provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets last 4.
     *
     * @return the last 4
     */
    public String getLast4() {
        return last4;
    }

    /**
     * Sets last 4.
     *
     * @param last4 the last 4
     */
    public void setLast4(String last4) {
        this.last4 = last4;
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets brand.
     *
     * @param brand the brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets expiry month.
     *
     * @return the expiry month
     */
    public int getExpiryMonth() {
        return expiryMonth;
    }

    /**
     * Sets expiry month.
     *
     * @param expiryMonth the expiry month
     */
    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    /**
     * Gets expiry year.
     *
     * @return the expiry year
     */
    public int getExpiryYear() {
        return expiryYear;
    }

    /**
     * Sets expiry year.
     *
     * @param expiryYear the expiry year
     */
    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Timestamp getCreated_at() {
        return created_at;
    }

    /**
     * Sets created at.
     *
     * @param created_at the created at
     */
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
